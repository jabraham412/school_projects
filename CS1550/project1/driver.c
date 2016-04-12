/**
* Author: John Abraham
* CS1550: Operating Systems
* Project1: Graphics Library
*/

#include <stdio.h>

typedef unsigned short color_t;

void init_graphics();
void exit_graphics();
void clear_screen();
char getkey();
void sleep_ms(long ms);
void draw_pixel(int x, int y, color_t color);
void draw_rect(int x1, int y1, int width, int height, color_t c);
void fill_circle(int x, int y, int r, color_t c);
void draw_text(int x, int y, const char *text, color_t c);

int main (int argc, char** argv)
{
   printf("\nPress \"1\" to draw a non-filled rectangle.\n");
   printf("Press \"2\" to draw a filled circle.\n");
   printf("Press \"3\" to display a string.\n");
   printf("**Select shape. Use the \"WASD\" keys to move the shape around.\n");
   printf("**Press the \"q\" at any point to termiante the program.\n");

   char key;
   int x = (640-20)/2;
   int y = (480-20)/2;
   int choice;
   scanf("%d", &choice);

   /**
   * Draw a non-filled rectangle.
   * Move around with 'WASD' keys.
   * Terminate with 'q' key.
   */
   {
   if(choice == 1)
      init_graphics();
      clear_screen();
      draw_rect(x, y, 200, 100, 20);
      do
      {
         key = getkey();
         if(key == 'w') y-=10;
         else if(key == 's') y+=10;
         else if(key == 'a') x-=10;
         else if(key == 'd') x+=10;
         clear_screen();
         draw_rect(x, y, 200, 100, 20);
         sleep_ms(20);
      } while(key != 'q');
      clear_screen();
      exit_graphics();
   }

   /**
   * Draw a filled circle with the midpoint circle algorithm.
   * Move around with 'WASD' keys.
   * Terminate with 'q' key.
   */
   if(choice == 2)
   {
      init_graphics();
      clear_screen();
      fill_circle(x, y, 75, 20);
      do
      {
         key = getkey();
         if(key == 'w') y-=10;
         else if(key == 's') y+=10;
         else if(key == 'a') x-=10;
         else if(key == 'd') x+=10;
         clear_screen();
         fill_circle(x, y, 75, 20);
         sleep_ms(20);
      } while(key != 'q');
         clear_screen();
         exit_graphics();
   }

   /**
   * Write a string usign the iso_font.h header file.
   * Move around with 'WASD' keys.
   * Terminate with 'q' key.
   */
   if(choice == 3)
   {
      const char *text_input = "Hello World!";
      init_graphics();
      clear_screen();
      draw_text(x, y, text_input, 20);
   do
   {
      key = getkey();
      if(key == 'w') x-=10;
      else if(key == 's') x+=10;
      else if(key == 'a') y-=10;
      else if(key == 'd') y+=10;
      clear_screen();
      draw_text(x, y, text_input, 20);
      sleep_ms(20);
   } while(key != 'q');
      clear_screen();
      exit_graphics();
   }

   return 0;
}