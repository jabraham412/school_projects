#include <stdio.h>
#include <pthread.h>

void *do_stuff(void *p) {
	while(1) {
		printf("Hello from thread %d - A\n", *(int *)p);
		pthread_yield();
		printf("Hello from thread %d - B\n", *(int *)p);
	}
}

int main() {
	pthread_t thread1, thread2;
	int id1, id2, arg1, arg2;
	
	arg1 = 1;
	arg2 = 2;
	id1 = pthread_create(&thread1, NULL, do_stuff, (void *)&arg1);
	id2 = pthread_create(&thread2, NULL, do_stuff, (void *)&arg2);

	pthread_join(thread1, NULL);
	pthread_join(thread2, NULL);

	return 0;
}
