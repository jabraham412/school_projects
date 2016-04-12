/**
* author: John Abraham
* CS449: Systems Software
* Project3: A Custom Malloc()
*/

#include <stdio.h>
#include "mymalloc.h"

int main()
{
		int *ptr_one;
		ptr_one = (int *)my_nextfit_malloc(sizeof(int));
		*ptr_one = 10;
		printf("%d\n", *ptr_one);


		int *ptr_two;
		ptr_two = (int *)my_nextfit_malloc(sizeof(int)*2);
		*ptr_two = 20;
		printf("%d\n", *ptr_two);

		int *ptr_three;
		ptr_three = (int *)my_nextfit_malloc(sizeof(int)*4);
		*ptr_three = 30;
		printf("%d\n", *ptr_three);

		int *ptr_four;
		ptr_four = (int *)my_nextfit_malloc(sizeof(int));
		*ptr_four = 40;
		printf("%d\n", *ptr_four);

		my_free(ptr_one);
		my_free(ptr_two);
		my_free(ptr_three);
		my_free(ptr_four);

		printf("\n%d\n", *ptr_one);
		printf("%d\n",   *ptr_two);
		printf("%d\n",   *ptr_three);
		printf("%d\n",   *ptr_four);

		return 0;
}
