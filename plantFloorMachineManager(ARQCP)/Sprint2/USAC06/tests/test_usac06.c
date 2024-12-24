#include "../../unity_folder/unity.h"
#include "../include/asm.h"  

void setUp(void) {
}

void tearDown(void) {
}


void test_dequeue_from_full_buffer(void)  {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 9;
    int value;  

    int expected_output = 1;
    int expected_tail = 9;
    int expected_head = 1;
    int expected_value = 0;
	
    int output = dequeue_value(buffer, length, &tail, &head, &value);
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value, value);

}

void test_dequeue_from_partial_buffer(void)  {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 3;
    int tail = 9;
    int value;  

    int expected_output = 1;
    int expected_tail = 9;
    int expected_head = 4;
    int expected_value = 3;
	
    int output = dequeue_value(buffer, length, &tail, &head, &value);
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value, value);

}

void test_dequeue_head_to_equal_tail(void)  {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 4;
    int tail = 5;
    int value;  

    int expected_output = 1;
    int expected_tail = 5;
    int expected_head = 5;
    int expected_value = 4;
	
    int output = dequeue_value(buffer, length, &tail, &head, &value);
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value, value);

}

void test_dequeue_head_at_buffer_end(void)  {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 9;
    int tail = 5;
    int value;  

    int expected_output = 1;
    int expected_tail = 5;
    int expected_head = 0;
    int expected_value = 9;
	
    int output = dequeue_value(buffer, length, &tail, &head, &value);
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value, value);

}

void test_dequeue_empty_buffer(void)  {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 5;
    int tail = 5;
    int value;  

    int expected_output = 0;
    
	
    int output = dequeue_value(buffer, length, &tail, &head, &value);
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
   
}

void test_dequeue_from_partial_buffer_tail_first(void)  {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 5;
    int tail = 2;
    int value;  

    int expected_output = 1;
    int expected_tail = 2;
    int expected_head = 6;
    int expected_value = 5;
	
    int output = dequeue_value(buffer, length, &tail, &head, &value);
		
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value, value);

}




int main(void) {
    UNITY_BEGIN();
    RUN_TEST(test_dequeue_from_full_buffer);
    RUN_TEST(test_dequeue_from_partial_buffer);
    RUN_TEST(test_dequeue_head_to_equal_tail);
    RUN_TEST(test_dequeue_head_at_buffer_end);
    RUN_TEST(test_dequeue_empty_buffer);
    RUN_TEST(test_dequeue_from_partial_buffer_tail_first);
   
    
    
    return UNITY_END();
}
