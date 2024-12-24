#include <stdio.h>
#include "../include/asm.h"


int main(){
    int value = 26; 
    char bits [5];
    
    int res = get_number_binary (value,bits);
    printf ("%d : [%c,%c,%c,%c,%c]\n",res,bits[0],bits[1],bits[2],bits[3],bits[4]); // 1: 1 ,1 ,0 ,1 ,0

    value = 0;
    res = get_number_binary (value,bits);
    printf ("%d : [%d,%d,%d,%d,%d]\n",res,bits[0],bits[1],bits[2],bits[3],bits[4]); // 1: 1 ,1 ,0 ,1 ,0

    value = 1;
    res = get_number_binary (value,bits);
    printf ("%d : [%d,%d,%d,%d,%d]\n",res,bits[0],bits[1],bits[2],bits[3],bits[4]); // 1: 1 ,1 ,0 ,1 ,0
    
    value = 15;
    res = get_number_binary (value,bits);
    printf ("%d : [%d,%d,%d,%d,%d]\n",res,bits[0],bits[1],bits[2],bits[3],bits[4]); // 1: 1 ,1 ,0 ,1 ,0
    
    value = 31;
    res = get_number_binary (value,bits);
    printf ("%d : [%d,%d,%d,%d,%d]\n",res,bits[0],bits[1],bits[2],bits[3],bits[4]); // 1: 1 ,1 ,0 ,1 ,0
    
    value = 32;
    res = get_number_binary (value,bits);
    printf ("%d : [%d,%d,%d,%d,%d]\n",res,bits[0],bits[1],bits[2],bits[3],bits[4]); // 1: 1 ,1 ,0 ,1 ,0
    
    value = -1;
    res = get_number_binary (value,bits);
    printf ("%d : [%d,%d,%d,%d,%d]\n",res,bits[0],bits[1],bits[2],bits[3],bits[4]); // 1: 1 ,1 ,0 ,1 ,0
    
}