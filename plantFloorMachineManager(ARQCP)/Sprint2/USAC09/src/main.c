#include <stdio.h>
#include "../include/asm.h"


int main () {
		
		int buffer[] = {2,32,5,23,4,6,19,29,43,27,7,43,55,32,3,12,2,5,7,9,12,16,18};
		int lenght = sizeof(buffer) / sizeof(int);
		char c = 0;
		
		printf("Buffer: [ ");
		for(int* i = buffer; i < buffer + lenght; i++) {
			printf("%d ", *i);
		}
		printf(" ]\n");
		
		int res = sort_array(buffer, lenght, c);
		
		printf("Return: %d\n", res);
		
		if (c == 1) {
		printf("Sorted Buffer (ascending): [ ");
		for(int* i = buffer; i < buffer + lenght; i++) {
			printf("%d ", *i);
		}
		printf(" ]\n");
		} else {	
			printf("Sorted Buffer (descending): [ ");
		for(int* i = buffer; i < buffer + lenght; i++) {
			printf("%d ", *i);
		}
		printf(" ]\n");
		}
	
	return 0;
}
