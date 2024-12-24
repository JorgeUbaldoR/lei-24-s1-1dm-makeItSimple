#include <stdio.h>
#include "../include/asm.h"


int main () {
		
	int value;
	char str[] = "123ola";
	int res = get_number(str,&value);
	printf("%d: %d\n", res, value);
	
	
	
	return 0;
}
