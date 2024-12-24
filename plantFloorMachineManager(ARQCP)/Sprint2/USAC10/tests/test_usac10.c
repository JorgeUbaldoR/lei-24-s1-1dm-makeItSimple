#include "../../unity_folder/unity.h"
#include "../include/asm.h"  

void setUp(void) {
}

void tearDown(void) {
}

void test_median_even_length(void) {
    int arr[] = {3, 1, 2, 4};
    int length = 4;
    int median_value;

    int result = median(arr, length,&median_value);
    int expected_median = 2;                    
    int expected_result = 1;                    
    TEST_ASSERT_EQUAL_INT(expected_median,median_value);
    TEST_ASSERT_EQUAL_INT(expected_result,result);
}

void test_median_even_length_2(void) {
    int arr[] = {10, 20, 30, 40};
    int length = 4;
    int median_value;

    int result = median(arr, length,&median_value);
    int expected_median = 25;                    
    int expected_result = 1;                    
    TEST_ASSERT_EQUAL_INT(expected_median,median_value);
    TEST_ASSERT_EQUAL_INT(expected_result,result);
}


void test_median_odd_length(void) {
    int vec[] = {3, 1, 2};
    int length = 3;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 2;
    int expected_result = 1;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_odd_length_2(void){
    int vec[] = {3, 1, 4, 1, 5};
    int length = 5;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 3;
    int expected_result = 1;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_equal_elements(void){
    int vec[] = {5, 5, 5, 5, 5};
    int length = 5;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 5;
    int expected_result = 1;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_length_zero(void){
    int vec[] = {};
    int length = 0;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 0;
    int expected_result = 0;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}


void test_median_negative_elements_odd(void){
    int vec[] = {-10, -20, -30, -40, -50};
    int length = 5;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = -30;
    int expected_result = 1;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_negative_elements_even(void){
    int vec[] = {-10, -20, -30, -40, -50,-60};
    int length = 6;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = -35;
    int expected_result = 1;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_mix_elements_odd(void){
    int vec[] = {-3, 2, -1, 4, 1,2};
    int length = 6;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 1;
    int expected_result = 1;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_mix_elements_odd_2(void){
    int vec[] = {-3, 2, -1, 4, 1,-2};
    int length = 6;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 0;
    int expected_result = 1;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_negative_length(void){
    int vec[] = {1,2};
    int length = -1;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 0;
    int expected_result = 0;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_length_one(void){
    int vec[] = {1};
    int length = 1;
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 1;
    int expected_result = 1;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_null_vec(void){
    int vec[] = {0,0,0};
    int length = sizeof(vec)/sizeof(int);
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 0;
    int expected_result = 1;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}

void test_median_null_vec_2(void){
    int* vec = NULL;
    int length = sizeof(vec)/sizeof(int*);
    int median_value;
    int result = median(vec,length,&median_value);

    int expected_median = 0;
    int expected_result = 0;

    TEST_ASSERT_EQUAL_INT(expected_median,median_value); 
    TEST_ASSERT_EQUAL_INT(expected_result,result); 
}


int main(void) {
    UNITY_BEGIN();
    RUN_TEST(test_median_even_length);
    RUN_TEST(test_median_even_length_2);
    RUN_TEST(test_median_odd_length);
    RUN_TEST(test_median_odd_length_2);
    RUN_TEST(test_median_equal_elements);
    RUN_TEST(test_median_length_zero);
    RUN_TEST(test_median_negative_elements_odd);
    RUN_TEST(test_median_negative_elements_even);
    RUN_TEST(test_median_mix_elements_odd);
    RUN_TEST(test_median_mix_elements_odd_2);
    RUN_TEST(test_median_negative_length);
    RUN_TEST(test_median_length_one);
    RUN_TEST(test_median_null_vec);
    RUN_TEST(test_median_null_vec_2);
    return UNITY_END();
}
