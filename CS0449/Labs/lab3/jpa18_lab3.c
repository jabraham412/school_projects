//John Abraham
//jpa18@pitt.edu
// CS 449: Lab 3 - Malloc and Free

#include <stdio.h>
#include <stdlib.h>

struct Node {
	int grade;
	struct Node *next;
};


int main(){
	typedef struct Node item;

	item *curr;
	item *root;
	root = NULL;
	int x = 0;

	while(x != -1){
		printf("Please enter a grade (enter -1 to stop): ");
		scanf("%d", &x);
		if (x == -1)
			break;
		curr = (item*)malloc(sizeof(item));
		curr->grade = x;
		curr->next = root;
		root = curr;
	}

	//find the average
	int total = 0;
	int num_items = 0;
	root = curr;
	while(curr != NULL){
		total += curr->grade;
		num_items++;
		curr = curr->next;
	}

	printf("\nThe average grade is: %2.2f \n", (float)total/(float)num_items);

	//free memory from elements
	root = curr;
	while(curr != NULL){
		curr = curr->next;
		free(curr);
	}

	//the following does not print anything because all Node elements have been freed
	while(root != NULL){
		printf("%d \n", root->grade);
		root = root->next;
	}

}
