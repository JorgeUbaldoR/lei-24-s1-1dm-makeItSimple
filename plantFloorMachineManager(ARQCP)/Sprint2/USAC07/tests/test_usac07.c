#include "../../unity_folder/unity.h"
#include "../include/asm.h"  

void setUp(void) {
}

void tearDown(void) {
}


void test_full_buffer_v1(void) {
    int buffer[] = {2,32,5,23,4,6,19,29,43,27,7,43,55,32,3,12,2,5,7,9,12,16,18,21};   
	int lenght = sizeof(buffer) / sizeof(int);
		
    int head = 0;
	int tail = 23;
		
	int expected_output = 24;
	int result = get_n_element(buffer, lenght, &tail, &head);
	
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_full_buffer_v2(void) {
    int buffer[] = {2,32,5,23,4,6,19,29,43,27,7,43,55,32,3,12,2,5,7,9,12,16,18,21};   
	int lenght = sizeof(buffer) / sizeof(int);
		
    int head = 12;
	int tail = 11;
		
	int expected_output = 24;
	int result = get_n_element(buffer, lenght, &tail, &head);
	
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_empty_buffer(void) {
    int buffer[] = {2,32,5,23,4,6,19,29,43,27,7,43,55,32,3,12,2,5,7,9,12,16,18,21};   
	int lenght = sizeof(buffer) / sizeof(int);
		
    int head = 12;
	int tail = 12;
		
	int expected_output = 0;
	int result = get_n_element(buffer, lenght, &tail, &head);
	
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_not_full_buffer(void) {
    int buffer[] = {2,32,5,23,4,6,19,29,43,27,7,43,55,32,3,12,2,5,7,9,12,16,18,21};   
	int lenght = sizeof(buffer) / sizeof(int);
		
    int head = 10;
	int tail = 12;
		
	int expected_output = 3;
	int result = get_n_element(buffer, lenght, &tail, &head);
	
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_invalid_tail_buffer(void) {
    int buffer[] = {2,32,5,23,4,6,19,29,43,27,7,43,55,32,3,12,2,5,7,9,12,16,18,21};   
	int lenght = sizeof(buffer) / sizeof(int);
		
    int head = 10;
	int tail = 25;
		
	int expected_output = -1;
	int result = get_n_element(buffer, lenght, &tail, &head);
	
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_invalid_head_buffer(void) {
    int buffer[] = {2,32,5,23,4,6,19,29,43,27,7,43,55,32,3,12,2,5,7,9,12,16,18,21};   
	int lenght = sizeof(buffer) / sizeof(int);
		
    int head = 25;
	int tail = 12;
		
	int expected_output = -1;
	int result = get_n_element(buffer, lenght, &tail, &head);
	
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_zero_length_buffer(void) {
    int buffer[] = {};
    int lenght = 0;
    int head = 0;
    int tail = 0;

    int expected_output = 0; 
    int result = get_n_element(buffer, lenght, &tail, &head);

    TEST_ASSERT_EQUAL_INT(expected_output, result);
}

void test_single_element_buffer(void) {
    int buffer[] = {42};
    int lenght = 1;
    int head = 0;
    int tail = 0;

    int expected_output = 1; 
    int result = get_n_element(buffer, lenght, &tail, &head);

    TEST_ASSERT_EQUAL_INT(expected_output, result);
}

void test_negative_head_tail(void) {
    int buffer[] = {10, 20, 30, 40};
    int lenght = sizeof(buffer) / sizeof(int);

    int head = -1; 
    int tail = -2;

    int expected_output = -1; 
    int result = get_n_element(buffer, lenght, &tail, &head);

    TEST_ASSERT_EQUAL_INT(expected_output, result);
}


int main(void) {
    UNITY_BEGIN();
    RUN_TEST(test_full_buffer_v1);
    RUN_TEST(test_full_buffer_v2);
    RUN_TEST(test_empty_buffer);   
    RUN_TEST(test_not_full_buffer); 
    RUN_TEST(test_invalid_head_buffer);
    RUN_TEST(test_invalid_tail_buffer);
    RUN_TEST(test_zero_length_buffer);
    RUN_TEST(test_single_element_buffer);
    RUN_TEST(test_negative_head_tail);
    
    
    return UNITY_END();
}
