#Project 3: A Custom malloc()#

The objective of this assignment is to implement a custom malloc() that supports two functions:

1. A malloc() replacement called void *my_nextfit_malloc(int size) that allocates memory using the next- fit algorithm. If no empty space is big enough, allocate more via sbrk().
2. A free() called void my_free(void *ptr) that deallocates a pointer that was originally allocated by the malloc you wrote above.

The "free" function should coalesce adjacent free blocks as described in class. If the block that touches brk is free, you should use sbrk() with a negative offset to reduce the size of the heap.
