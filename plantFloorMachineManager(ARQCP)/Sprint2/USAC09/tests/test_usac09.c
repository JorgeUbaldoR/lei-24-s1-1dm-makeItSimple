#include "../../unity_folder/unity.h"
#include "../include/asm.h"  

void setUp(void) {
}

void tearDown(void) {
}


void test_ascending_order_positive_vec(void) {
    int arr[] = {4,65,9,2,25,6};
    int lenght = 6;
    char order = 1;

    int result = sort_array(arr,lenght,order);

    int expected_array[] = {2,4,6,9,25,65};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(arr, expected_array, lenght);
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_descending_order_positive_vec(void) {
    int arr[] = {4,65,9,2,25,6};
    int lenght = 6;
    char order = 0;

    int result = sort_array(arr,lenght,order);

    int expected_array[] = {65,25,9,6,4,2};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(arr, expected_array, lenght);
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_ascending_order_negative_vec(void) {
    int arr[] = {-4,-65,-9,-2,-25,-6};
    int lenght = 6;
    char order = 1;

    int result = sort_array(arr,lenght,order);

    int expected_array[] = {-65,-25,-9,-6,-4,-2};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(arr, expected_array, lenght);
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_descending_order_negative_vec(void) {
    int arr[] = {-4,-65,-9,-2,-25,-6};
    int lenght = 6;
    char order = 0;

    int result = sort_array(arr,lenght,order);

    int expected_array[] = {-2,-4,-6,-9,-25,-65};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(arr, expected_array, lenght);
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_descending_order_negative_and_positive_vec(void) {
    int arr[] = {-4,-65,9,-2,25,-6};
    int lenght = 6;
    char order = 0;

    int result = sort_array(arr,lenght,order);

    int expected_array[] = {25,9,-2,-4,-6,-65};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(arr, expected_array, lenght);
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_ascending_order_negative_and_positive_vec(void) {
    int arr[] = {-4,-65,9,-2,25,-6};
    int lenght = 6;
    char order = 1;

    int result = sort_array(arr,lenght,order);

    int expected_array[] = {-65,-6,-4,-2,9,25};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(arr, expected_array, lenght);
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_ascending_order_empty_array(void) {
    int arr[] = {};
    int lenght = 0;
    char order = 1;

    int result = sort_array(arr,lenght,order);

    int expected_output = 0;
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_descending_order_empty_array(void) {
    int arr[] = {};
    int lenght = 0;
    char order = 0;

    int result = sort_array(arr,lenght,order);

    int expected_output = 0;
    TEST_ASSERT_EQUAL_INT(expected_output, result);

}

void test_single_element_array_ascending(void) {
    int arr[] = {42};
    int length = 1;
    char order = 1;

    int result = sort_array(arr, length, order);

    int expected_array[] = {42};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, arr, length);
    TEST_ASSERT_EQUAL_INT(expected_output, result);
}

void test_single_element_array_descending(void) {
    int arr[] = {42};
    int length = 1;
    char order = 0;

    int result = sort_array(arr, length, order);

    int expected_array[] = {42};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, arr, length);
    TEST_ASSERT_EQUAL_INT(expected_output, result);
}

void test_duplicate_elements_array_ascending(void) {
    int arr[] = {3, 3, 3, 3, 3};
    int length = 5;
    char order = 1;

    int result = sort_array(arr, length, order);

    int expected_array[] = {3, 3, 3, 3, 3};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, arr, length);
    TEST_ASSERT_EQUAL_INT(expected_output, result);
}

void test_duplicate_elements_array_descending(void) {
    int arr[] = {3, 3, 3, 3, 3};
    int length = 5;
    char order = 0;

    int result = sort_array(arr, length, order);

    int expected_array[] = {3, 3, 3, 3, 3};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, arr, length);
    TEST_ASSERT_EQUAL_INT(expected_output, result);
}

void test_already_sorted_array_ascending(void) {
    int arr[] = {1, 2, 3, 4, 5};
    int length = 5;
    char order = 1;

    int result = sort_array(arr, length, order);

    int expected_array[] = {1, 2, 3, 4, 5};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, arr, length);
    TEST_ASSERT_EQUAL_INT(expected_output, result);
}

void test_already_sorted_array_descending(void) {
    int arr[] = {5,4,3,2,1};
    int length = 5;
    char order = 0;

    int result = sort_array(arr, length, order);

    int expected_array[] = {5,4,3,2,1};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, arr, length);
    TEST_ASSERT_EQUAL_INT(expected_output, result);
}


void test_reverse_sorted_array_ascending(void) {
    int arr[] = {5, 4, 3, 2, 1};
    int length = 5;
    char order = 1;

    int result = sort_array(arr, length, order);

    int expected_array[] = {1, 2, 3, 4, 5};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, arr, length);
    TEST_ASSERT_EQUAL_INT(expected_output, result);
}

void test_reverse_sorted_array_descending(void) {
    int arr[] = {1,2,3,4,5};
    int length = 5;
    char order = 0;

    int result = sort_array(arr, length, order);

    int expected_array[] = {5,4,3,2,1};
    int expected_output = 1;
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, arr, length);
    TEST_ASSERT_EQUAL_INT(expected_output, result);
}

int main(void) {
    UNITY_BEGIN();
    RUN_TEST(test_ascending_order_positive_vec);
    RUN_TEST(test_descending_order_positive_vec);
    RUN_TEST(test_ascending_order_negative_vec);
    RUN_TEST(test_descending_order_negative_vec);
    RUN_TEST(test_descending_order_negative_and_positive_vec);
    RUN_TEST(test_ascending_order_negative_and_positive_vec);
    RUN_TEST(test_ascending_order_empty_array);
    RUN_TEST(test_descending_order_empty_array);
    RUN_TEST(test_single_element_array_ascending);
    RUN_TEST(test_single_element_array_descending);
    RUN_TEST(test_duplicate_elements_array_ascending);
    RUN_TEST(test_duplicate_elements_array_descending);
    RUN_TEST(test_already_sorted_array_ascending);
    RUN_TEST(test_already_sorted_array_descending);
    RUN_TEST(test_reverse_sorted_array_ascending);
    RUN_TEST(test_reverse_sorted_array_descending);
    return UNITY_END();
}
