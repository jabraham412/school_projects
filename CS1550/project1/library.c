/**
* Author: John Abraham
* CS1550: Operating Systems
* Project1: Graphics Library
*/

/* C standard library functions are not allowed. Only linux syscalls. */
#include <sys/stat.h>
#include <sys/mman.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <linux/fb.h>
#include <fcntl.h>
#include <termios.h>
#include <unistd.h>
//unistd.h is only used in this library for the STDIN_FILENO const. No functions used.
#include "iso_font.h"

/* Global variables */
int fd;
unsigned short *fb_ptr;
unsigned long size;
unsigned long x_virtual_len;
unsigned long y_virtual_len;
typedef unsigned short color_t;

/* Prototypes for our nine library functions. */
void init_graphics();
void exit_graphics();
void clear_screen();
char getkey();
void sleep_ms(long ms);
void draw_pixel(int x, int y, color_t color);
void draw_rect(int x1, int y1, int width, int height, color_t c);
void fill_circle(int x, int y, int r, color_t c);
void draw_text(int x, int y, const char *text, color_t c);
void draw_character(int x, int y, color_t c, int ascii_val);

/**
 * In this function all necessary work to initialize the graphics library is done:
 * There are four steps.
 */
void init_graphics()
{
   /* 1. Open the graphics device /dev/fb0 (frame buffer) using the open syscall. */
   /* 2. Screen resolution detection using the ioctl suyscall. */
   /* 3. Memory mapping using the mmap syscall. */
   /* 4. Disable display from keyboard. */
   struct fb_var_screeninfo var_info;
   struct fb_fix_screeninfo fix_info;
   struct termios term;

   fd = open("/dev/fb0", O_RDWR);
   ioctl(fd, FBIOGET_VSCREENINFO, &var_info);
   ioctl(fd, FBIOGET_FSCREENINFO, &fix_info);
   x_virtual_len = var_info.xres_virtual;
   y_virtual_len = var_info.yres_virtual;
   size = fix_info.line_length;

   fb_ptr = (unsigned short *)mmap(NULL, x_virtual_len * size,
   	                        PROT_WRITE, MAP_SHARED, fd, 0);

   ioctl(STDIN_FILENO, TCGETS, &term);
   term.c_lflag &= ~ICANON; //disable canonical mode
   term.c_lflag &= ~ECHO; //disable ECHO
   ioctl(STDIN_FILENO, TCSETS, &term);
}

/* Undo whatever it is that needs to be cleaned up before the program exists. */
void exit_graphics()
{
   /* Unmap file from address space memory. */
   munmap(fb_ptr, y_virtual_len * size);

   /* Activate key display by re-enabling canonical mode and ECHO. */
   /* '1' for the file descriptor parameter is reserved for stdout. */
   struct termios term;
   ioctl(STDIN_FILENO, TCGETS, &term);
   term.c_lflag |= ICANON;
   term.c_lflag |= ECHO;
   ioctl(STDIN_FILENO, TCSETS, &term);

   /* Close the framebuffer file. */
   close(fd);
}

/* Clear the screen using the write syscall. */
void clear_screen()
{
   /* Using the ANSI escape code "\033[2J". */
   write(1, "\033[2J", 8);
}

/* Read character inputs from user. */
char getkey()
{
   /**
    * The read() syscall is blocking and will cause our program
    * to not draw unless the user has typed something. So we need
    * to use the non-blocking SELECT syscall to monitor
    * when read() is ready, then we perform read().
    */
   char input_key;
   fd_set rfds;
   struct timeval tv;

   /* Watch stdin (fd 0) to see when it has input. */
   FD_ZERO(&rfds);
   FD_SET(0, &rfds);

   /* Wait up to five seconds. */
   tv.tv_sec = 5;
   tv.tv_usec = 0;
   int select_r = select(STDIN_FILENO+1, &rfds, NULL, NULL, &tv);
   if (select_r > 0)
   {
   	read(0, &input_key, sizeof(input_key));
   }
   return input_key;
}

/**
 * Using the syscall nanosleep() make the program sleep between frames
 * of graphics being drawn. We will sleep for a specified number of ms.
 */
void sleep_ms(long ms)
{
   /**
    * We do not need to worry about the call being interrupted
    * and so the second parameter to nanosleep() is NULL.
    */
   struct timespec ts;
   ts.tv_sec = 0;
   ts.tv_nsec = ms * 1000000;
   nanosleep(&ts, NULL);
}

/**
 * This is the main drawing code; where the work is actually done.
 * We want to set a pixel at coordinate (x, y) to the specified color.
 * The frame buffer will be stored in row-major order, measuring the
 * first row starts at offset 0 and then that is followed by the second
 * row of pixels, and so on.
 */
void draw_pixel(int x, int y, color_t color)
{
   /**
    * fb_ptr points to the beginning of a one-dimensional array,
    * while the screen is a two-dimensional array. We need to jump
    * to (x, y) so the program needs to know the length of each line
    * - regarding the x-axis. And the y-offset.
    * E.g (x, y) = (1, 2), index = ?
    */
   if (x < 0 || x >= x_virtual_len || y < 0 || y >= y_virtual_len)
   {
      /* Out of bounds exception handling. */
      return;
   }

   /* Use pointer arithmetic to set the pixel to the specified color. */
   unsigned long vertical = (size/2) * y;
   unsigned long horizontal = x;
   unsigned short *ptr = ( fb_ptr + vertical + horizontal);
   *ptr = color;
}

/**
 * Using draw_pixel(), make a non-filled rectangle with corners
 * (x1, y1), (x1+width, y1), (x1+width, y1+height), (x1, y1+height).
 */
void draw_rect(int x1, int y1, int width, int height, color_t c)
{
   int x, y;
   for (x = x1; x < x1+width; x++)
   {
      for (y = y1; y < y1+height; y++)
      {
         if (x == x1 || x == x1+width-1)
         {
            draw_pixel(x, y, c);
         }
         if (y == y1 || y == y1+height-1)
         {
            draw_pixel(x, y, c);
         }
      }
   }
}

/**
 * Use the midpoint circle algorithm to draw a circle at (x, y)
 * with radius r filled in with the specified color.
 */
void fill_circle(int x, int y, int r, color_t c)
{
   /**
    * The mid point circle algorithm only draws the circumference
    * of a circle given a length of the radius, r. So we modify the
    * algorithm to run multiple times while iterating over the radius
    * length decreasing the radius length by 1 on every iteration.
    */
   int i;
   for (i = r; i > 0; i--)
   {
      int xx = i;
      int yy = 0;
      int x0 = x;
      int y0 = y;
      int decision_over_2 = 1 - xx;
      /* Draw the circumference of the circle for radius length, r. */
      while (yy <= xx)
      {
         draw_pixel( xx+x0, yy+y0, c);
         draw_pixel( yy+x0, xx+y0, c);
         draw_pixel( -xx+x0, yy+y0, c);
         draw_pixel( -yy+x0, xx+y0, c);
         draw_pixel( -xx+x0, -yy+y0, c);
         draw_pixel( -yy+x0, -xx+y0, c);
         draw_pixel( xx+x0, -yy+y0, c);
         draw_pixel( yy+x0, -xx+y0, c);
         yy++;
         if (decision_over_2 <= 0)
         {
            decision_over_2 += 2 * yy + 1;
         }
         else
         {
            xx--;
            decision_over_2 += 2 * (yy - xx) +1;
         }
      }
   }
}

/**
 * Draw the string with the specified color at the starting location
 * (x, y) -- this is the upper left corner of the first letter.
 * This function simply loops over all the characters in the string.
 */
void draw_text(int x, int y, const char *text, color_t c)
{
   const char *temp_ptr;
   int offset = 0;
   for (temp_ptr = text; *temp_ptr != '\0'; temp_ptr++)
   {
      draw_character(x, y+offset, c, *temp_ptr);
      offset += 8;
   }
}

/**
 * This function does the actual work of painting over the pixels for
 * a given character. It gets executed for as many times as our string
 * length (number of characters) using the iso_font.h header file and
 * and bit shifting and masking.
 */
void draw_character(int x, int y, color_t c, int ascii_val)
{
   int i, j, b;
   for (i = 0; i < 16; i++)
   {
      for (j = 0; j < 16; j++)
      {
         b =  ((iso_font[ascii_val*16+i] & 1 << j) >> j);
         if (b == 1)
         {
            draw_pixel(y+j, x+i, c);
         }
      }
   }
}