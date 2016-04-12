/**
* author: John Abraham
* CS449: Systems Software
* Project3: A Custom Malloc()
*/

#include "mymalloc.h"
#include <unistd.h>
#include <stdio.h>

/* Global variable "base" to keep track of the beginning position of the linked list */
/* Global variable "curr_pos" to keep track of the position where search left off */
void *base = NULL;
void *curr_pos = NULL;

/* This structure represents the doubly linked list that I will be using to represent
 * the meta-data. The meta-data is additional space added to each update of brk. And
 * it contains: the size of this chunk of memory, whether it is free or not, a pointer
 * to the next node, and a pointer to the previous node.
*/

typedef struct metadata_node *block;

struct metadata_node {
	int chunk_size;	//stores the size in bytes of the malloc'ed region
	int free_flag;	//space is free if free_flag != 0, and occupied otherwise
	block next;		//stores the next node
	block prev;		//stores the previous node
	char data_ptr[1];	//stores the pointer to the block that stores the metadata
	void *ptr;
};

/* To find a chunck using the next fit algorithm we begin where the last search left off
 * wrapping back to the beginning of the heap if necessary. We test the first free blcok
 * we encounter, if it can accommodate our request we just return its address, otherwise
 * we continue to the next free block until we ﬁnd a ﬁtting block or the end of the head.
*/

block find_block ( int size )
{
	block b = curr_pos;
	/* loop through the linked list until a region is found where the flag is set to free,
	 * (0) and the chunk's size is greater than or equal to the size requested.
	*/
	while ( b && !(b->free_flag) && (b->chunk_size <= size) )
	{
		/* (1) if the next pointer is not NULL (at the end) go to next node
		 * (2) if we reached end of the linked list start back from start b = base
		 * (3) if the pointer went through the whole list and back to the where search left
		 *     off without finding appropriate space it return a NULL pointer and stops loop.
		 */
		if (b != NULL) {
			b = b->next;
		}else {
			b = base;

		if(b == curr_pos)
			return NULL;
		}
	}
	curr_pos = sbrk(0);

	//return that address if appropriate space was found for requested size
	return b;
}

/* If no empty space is big enough we have to allocate more space by extending the heap
 * via sbrk(). We move the break and initialize a new block.
*/

block extend_heap ( block last, int s )
{
	block b;
	b = sbrk(0);

	if( sbrk( sizeof(struct metadata_node) +s ) == (void*)-1 )
		return (NULL);

	b->chunk_size = s;
	b->next = NULL;

	if( last != NULL )
		last->next = b;
	
	b->free_flag = 0;
	return (b);
}

/* my_nextfit_malloc takes a size in bytes from the user (expected to be unsigned int since we are
 * using int instead of size_t) and returns a pointer to the malloc'ed region with the appropriate
 * space. First, the global variable "base" (beginning of LL) is checked. If it's NULL then this is the
 * first time my_nextfit_malloc is being accessed and extend_heap is called with size. Then "base" is set
 * to the pointer returned from the extend_heap function. If "base" is not NULL then this is not the first
 * time my_next_fit is being called and find_block is called with curr_pos and size as its parameters.
 * curr_pos is the global variable that stores the position of where the search left off. If space is
 * found (b!=NULL) flag of the node is set to 0 (free) and malloc return the pointer. And if b is at
 * the end of the heap and no space was found extend_heap is called and more memory is generated. If
 * b is still NULL my_nextfit_malloc return NULL indicating there's no more space on the heap.
*/

void *my_nextfit_malloc( int size )
{
	block b;
	block last;

	if( base != NULL )
	{
		last = curr_pos;
		b = find_block(size);

		if(b != NULL )
		{
			// if "find_block" finds a region then b != NULL and we set the flag in metadata to free
			b->free_flag = 0;

		}else{
			b = extend_heap (last ,size);

			if ( b == NULL )
				return(NULL);
		}
	}
	else
	{
		//first time my_nextfit_malloc function is called
		b = extend_heap(NULL, size);
		if( b == NULL )
			return NULL;
		//use "base" global pointer o keep track of the beginning of the linked list
		//set the initial position of current node to the base first time my_nextfirst_malloc is called
		base = b;
		curr_pos = b;
	}

	return (b->data_ptr);
}

/***********************************************************************************************************************/

block free_block( void *p )
{
	char *tmp;
	tmp = p;
	return (p = tmp -= sizeof(struct metadata_node));
}

/* validates if a pointers is malloced by checking if the pointer in the heap */

int valid( void *p )
{
	if (base)
		if(p>base && p<sbrk(0))
			return (p == (free_block(p)) -> ptr);

	return (0);
}

/* coalescing chucks of memory eliminates fragmentation */

block coalesce( block b)
{
	if (b->next && b->next->free_flag){
		b->chunk_size += sizeof(struct metadata_node) +b->next->chunk_size;
		b->next = b->next->next;
		if(b->next)
			b->next->prev = b;
	}
	return (b);
}

/* my free function: only performed on valid pointers (malloced memory)
/* changes the value of free_flag in the linked list (0 = not free).
/* merges surring block using the coalesce function. Does it for prev
/* and next blocks
*/

void my_free( void *ptr )
{
	block b;
	if ( valid(ptr) )
	{
		b = free_block(ptr);
		b->free_flag = 1;

		if(b->prev && b->prev->free_flag)
			b = coalesce(b->prev);

		if(b->next)
			coalesce(b);
		else
		{
			if(b->prev)
				b->prev->next = NULL;
			else
				base = NULL;
			brk(b);
		}
	}
}
