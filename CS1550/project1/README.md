#Project1: Graphics Library

https://people.cs.pitt.edu/~jmisurda/teaching/cs1550/2161/cs1550-2161-project1.htm <br />

The goal of this project is to write a small graphics library that can set a pixel to a particular color, draw some basic shapes, and read keypresses using only linux syscalls. C standard library functions are not allowed in the library anywhere.

This program was build and tested on QEMU, a free and open-source hosted hypervisor that performs hardware virtualization. The environment used was a virtual ARM linux environment.<br /><br />


| Library Call                                                       | System Call(s) used |
|:------------------------------------------------------------------ |:--------------------|
| void init_graphics()                                               | open, ioctl, mmap   |
| void exit_graphics()                                               | ioctl               |
| void clear_screen()                                                | write               |
| char getkey()                                                      | select, read        |
| void sleep_ms(long ms)                                             | nanosleep           |
| void draw_pixel(int x, int y, color_t color)                       |                     |
| void draw_rect(int x1, int y1, int width, int height, color_t c)   |                     |
| void fill_circle(int x, int y, int r, color_t c)                   |                     |
| void draw_text(int x, int y, const char *text, color_t  c)         |                     |
