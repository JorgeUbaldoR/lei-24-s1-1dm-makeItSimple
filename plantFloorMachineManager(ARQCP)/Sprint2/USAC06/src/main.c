#include <stdio.h>
#include "../include/asm.h"

int main () {
    int buffer[] = {6,7,8,9,1};
    int length = sizeof(buffer) / sizeof(int);
    int head;
    int tail;
    int value;  
	

    printf("Buffer: [ ");
    for(int* i = buffer; i < buffer + length; i++) {
        printf("%d ", *i);
    }
    printf("]\n");

    printf("Choose Tail: ");
    scanf("%d", &tail);
    printf("Choose Head: ");
    scanf("%d", &head);


    int ret = dequeue_value(buffer, length, &tail, &head, &value); 

    printf("Tail: %d\n", tail);
    printf("Head: %d\n", head);
    printf("Value removed: %d\n", value);
    printf("Result: %d\n", ret);


    
    return 0;
}
