#include <stdio.h>
#include "../include/asm.h"

int main() {
    int buffer[] = {0,1,2,3,4,5};
    int length = sizeof(buffer) / sizeof(int);
    int head;            
    int tail;   		
    int value;          

    
    printf("Buffer: [ ");
    for (int* i = buffer; i < buffer + length; i++) {
        printf("%d ", *i);
    }
    printf("]\n");

    printf("Choose tail: ");
    scanf("%d", &tail);
    printf("Choose head: ");
    scanf("%d", &head);
    printf("Choose the value to insert: ");
    scanf("%d", &value);
    

    int ret = enqueue_value(buffer, length, &tail, &head, value);

    printf("Tail: %d\n", tail);
    printf("Head: %d\n", head);
    printf("After addition: [ ");
    for(int i = 0; i < length; i++) {
        printf("%d ", buffer[i]);
    }
    printf("]\n");
    printf("Result: %d\n", ret);

    

    return 0;
}
