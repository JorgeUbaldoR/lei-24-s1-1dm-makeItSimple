#include <stdio.h>
#include "../include/asm.h"

int main(){
	int value = 26;
	char str[] = " oN ";
	char cmd[20];
	int res = format_command(str, value, cmd) ;
	printf("%d: %s\n", res, cmd); // 1: ON ,1 ,1 ,0 ,1 ,0
	char str2[] = " aaa ";
	int result = format_command(str2, value, cmd) ;
	printf("%d: %s\n", result, cmd); // 0:
}
