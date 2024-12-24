#include <stdio.h>
#include "../include/asm.h"


int main () {
		
		int buffer[] = {0,1,2,3};   
		int length = sizeof(buffer) / sizeof(int);
		int head;
		int tail;
		
		printf("Buffer: [ ");
		for(int* i = buffer; i < buffer + length; i++) {
			printf("%d ", *i);
		}
		printf("]\n");

		printf("Choose tail: ");
		scanf("%d", &tail);
		printf("Choose head: ");
		scanf("%d", &head);
		
		int n = get_n_element(buffer, length, &tail, &head);
		printf("Number of elements: %d\n", n);
		
	
	return 0;
}
