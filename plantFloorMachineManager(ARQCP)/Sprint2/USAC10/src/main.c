#include <stdio.h>
#include "../include/asm.h"


int main(){
    int vec1[] = {6,3,2,5,1,4,7,8}; // 1 2 3 4 5 6
    int length1 = sizeof(vec1)/sizeof(int);
    int me1 = 0;

    int res = median(vec1,length1,&me1);
    printf("%d: %d\n", res,me1);

    int vec2[] = {1,2,3,4,5};
    int length2 = sizeof(vec2)/sizeof(int);
    int me2 = 0;

    res = median(vec2,length2,&me2);
    printf("%d: %d\n", res,me2);
    
    int vec3[] = {};
    int length3 = sizeof(vec3)/sizeof(int);
    int me3 = 0;

    res = median(vec3,length3,&me3);
    printf("%d: %d\n", res,me3);


    int* vec4 = NULL;
    int length4 = sizeof(vec4)/sizeof(int*);
    int me4 = 0;
    res = median(vec4,length4,&me4);
    printf("%d: %d\n", res,me4);
}
