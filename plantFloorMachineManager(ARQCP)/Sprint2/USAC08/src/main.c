#include <stdio.h>
#include "../include/asm.h"

int main() {
    int buffer[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    int length = sizeof(buffer) / sizeof(int); 
    int array[length]; 

    int head;
    int tail;
    int n; 

    printf("Buffer: [ ");
    for(int* i = buffer; i < buffer + length; i++) {
        printf("%d ", *i);
    }
    printf("]\n");
    
    printf("Choose tail: ");
    scanf("%d",&tail);
    printf("Choose head: ");
    scanf("%d",&head);
    printf("Choose how many numbers to remove: ");
    scanf("%d",&n);

    int ret = move_n_to_array(buffer, length, &tail, &head, n, array);

    printf("\nTail: %d\n", tail);
    printf("Head: %d\n", head);
    printf("Values removed: [");
    for(int* i = array; i < array + n; i++) {
        printf("%d ", *i);
    }
    printf("]\n");
    printf("Result: %d\n", ret);

    return 0;
}
