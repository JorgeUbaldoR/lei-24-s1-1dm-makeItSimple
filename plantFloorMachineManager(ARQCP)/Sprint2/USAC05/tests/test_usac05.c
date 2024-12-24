#include "../../unity_folder/unity.h"
#include "../include/asm.h"  

void setUp(void) {
}

void tearDown(void) {
}

void test_enqueue_from_full_buffer(void) {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 9;
    int value = 10;

    int expected_output = 1;
    int expected_tail = 0;
    int expected_head = 1;
    int expected_value_in_tail = 10;

    int output = enqueue_value(buffer, length, &tail, &head, value);
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value_in_tail, buffer[tail]);
}

void test_enqueue_from_partial_buffer(void) {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 3;
    int tail = 9;
    int value = 10;

    int expected_output = 0;
    int expected_tail = 0;
    int expected_head = 3;
    int expected_value_in_tail = 10;

    int output = enqueue_value(buffer, length, &tail, &head, value);
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value_in_tail, buffer[tail]);
}

void test_enqueue_tail_at_buffer_end(void) {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 9;
    int value = 10;

    int expected_output = 1;
    int expected_tail = 0;
    int expected_head = 1;
    int expected_value_in_tail = 10;

    int output = enqueue_value(buffer, length, &tail, &head, value);
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value_in_tail, buffer[tail]);
}

void test_enqueue_to_full_buffer(void) {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 5;
    int tail = 4;
    int value = 10;

    int expected_output = 1;
    int expected_tail = 5;
    int expected_head = 6;
    int expected_value_in_tail = 10;

    int output = enqueue_value(buffer, length, &tail, &head, value);
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value_in_tail, buffer[tail]);
}

void test_enqueue_into_partial_buffer_tail_first(void) {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 5;
    int tail = 2;
    int value = 10;

    int expected_output = 0;
    int expected_tail = 3;
    int expected_head = 5;

    int output = enqueue_value(buffer, length, &tail, &head, value);
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
}

void test_enqueue_empty_buffer(void) {

    int buffer[] = {0, 0, 0, 0, 0};
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 0;
    int value = 10;

    int expected_output = 0;
    int expected_tail = 1;
    int expected_head = 0;
    int expected_value_in_tail = 10;

    int output = enqueue_value(buffer, length, &tail, &head, value);

    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value_in_tail, buffer[tail]);
    
}

void test_enqueue_overwrite_when_full(void) {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 1;
    int tail = 0;
    int value = 10;

    int expected_output = 1;
    int expected_tail = 1;
    int expected_head = 2;
    int expected_value_in_tail = 10;

    int output = enqueue_value(buffer, length, &tail, &head, value);

    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value_in_tail, buffer[tail]);
}

void test_enqueue_from_partial_to_full(void) {

    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 5;
    int tail = 3;
    int value = 10;

    int expected_output = 1;
    int expected_tail = 4;
    int expected_head = 5;
    int expected_value_in_tail = 10;

    int output = enqueue_value(buffer, length, &tail, &head, value);
    
    printf("\n---After method---\n");
    printf("Buffer: [ ");
    for (int i = 0; i < length; i++) {
        printf("%d ", buffer[i]);
    }
    printf("]\n");
    printf("Head: %d (index)\n", head);
    printf("Tail: %d (index)\n", tail);

    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT(expected_value_in_tail, buffer[tail]);
}

int main(void) {
    UNITY_BEGIN();
    RUN_TEST(test_enqueue_from_full_buffer);
    RUN_TEST(test_enqueue_from_partial_buffer);
    RUN_TEST(test_enqueue_tail_at_buffer_end);
    RUN_TEST(test_enqueue_to_full_buffer);
    RUN_TEST(test_enqueue_into_partial_buffer_tail_first);
    RUN_TEST(test_enqueue_empty_buffer);
	RUN_TEST(test_enqueue_overwrite_when_full);
	RUN_TEST(test_enqueue_from_partial_to_full);
    return UNITY_END();
}
