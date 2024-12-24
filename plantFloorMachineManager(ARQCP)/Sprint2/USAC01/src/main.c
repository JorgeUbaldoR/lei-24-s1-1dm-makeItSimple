#include <stdio.h>
#include "../include/asm.h"


int main(){
    char str[] = "TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80";
    char token [] = "temp";
    char unit[20];
    int value ;

    int res = extract_data (str,token,unit,&value) ;
    printf ("%d:%s ,%d\n",res,unit,value ); // 1: celsius ,20
    
    char str2[] = "HUM&unit:percentage&value:80#TEMP&unit:celsius&value:20";
    char unit2[20];
    char token2 [] = "hum";
    res = extract_data (str2,token2,unit2,&value) ;
    printf ("%d:%s ,%d\n",res,unit2,value); // 1: celsius ,20


    char unit3[20] = {};
    char token3 [] = "AAA" ;
    res = extract_data (str,token3,unit3,&value) ;
    printf ("%d:%s ,%d\n",res,unit3,value); // 0: ,0
}