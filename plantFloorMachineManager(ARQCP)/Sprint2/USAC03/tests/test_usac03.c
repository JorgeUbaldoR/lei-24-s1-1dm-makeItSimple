#include "../../unity_folder/unity.h"
#include "../include/asm.h"  

void setUp(void) {
}

void tearDown(void) {
}


void test_trimmed_str_only_numbers() {
    char str[] = " 89";
    int value;

    int res = get_number(str, &value);

    int expected_res = 1;
    int expected_value = 89;

    TEST_ASSERT_EQUAL_INT(res, expected_res);
    TEST_ASSERT_EQUAL_INT(expected_value, value);
}

void test_not_trimmed_str_only_numbers() {
    char str[] = "	89	";
    int value;

    int res = get_number(str, &value);

    int expected_res = 1;
    int expected_value = 89;

    TEST_ASSERT_EQUAL_INT(expected_res, res);
    TEST_ASSERT_EQUAL_INT(expected_value, value);
}

void test_trimmed_separated_str_only_numbers() {
    char str[] = "8 9 9 2";
    int value;

    int res = get_number(str, &value);
    
    int expected_res = 1;
    int expected_value = 8992;

    TEST_ASSERT_EQUAL_INT(expected_res, res);
    TEST_ASSERT_EQUAL_INT(expected_value, value);
}

void test_not_trimmed_separated_str_only_numbers() {
    char str[] = "	8	9	9	2	";
    int value;

    int res = get_number(str, &value);

    int expected_res = 1;
    int expected_value = 8992;

    TEST_ASSERT_EQUAL_INT(expected_res, res);
    TEST_ASSERT_EQUAL_INT(expected_value, value);
}

void test_invalid_str_with_numbers() {
    char str[] = "8--9";
    int value;

    int res = get_number(str, &value);

    int expected_res = 0;
    

    TEST_ASSERT_EQUAL_INT(expected_res, res);
}

void test_invalid_str_without_numbers() {
    char str[] = "####--##";
    int value;

    int res = get_number(str, &value);

    int expected_res = 0;
    

    TEST_ASSERT_EQUAL_INT(expected_res, res);
}

void test_empty_str() {
    char str[] = {};
    int value;
    
    int res = get_number(str, &value);

    int expected_res = 0;
    

    TEST_ASSERT_EQUAL_INT(expected_res, res);
}

void test_null_str() {
    char str[] = {0};
    int value;
    
    int res = get_number(str, &value);

    int expected_res = 0;
    

    TEST_ASSERT_EQUAL_INT(expected_res, res);
}

void test_neg_value_str() {
    char str[] = "-89";
    int value;
    
    int res = get_number(str, &value);

    int expected_res = 0;
    

    TEST_ASSERT_EQUAL_INT(expected_res, res);
}

void test_single_value_str() {
    char str[] = " 8 ";
    int value;
    
    int res = get_number(str, &value);
	int expected_value = 8;
    int expected_res = 0;
    
	TEST_ASSERT_EQUAL_INT(expected_value, value);
    TEST_ASSERT_EQUAL_INT(expected_res, res);
}

void test_decimal_value_str() {
    char str[] = "	8.9		";
    int value;
    
    int res = get_number(str, &value);

    int expected_res = 0;
    

    TEST_ASSERT_EQUAL_INT(expected_res, res);
}

void test_mixed_valid_and_invalid_values_str() {
    char str[] = "	8a9		";
    int value;
    
    int res = get_number(str, &value);

    int expected_res = 0;
    

    TEST_ASSERT_EQUAL_INT(expected_res, res);
}


int main(void) {
    UNITY_BEGIN();
    RUN_TEST(test_trimmed_str_only_numbers);
    RUN_TEST(test_not_trimmed_str_only_numbers);
    RUN_TEST(test_trimmed_separated_str_only_numbers);
    RUN_TEST(test_not_trimmed_separated_str_only_numbers);
    RUN_TEST(test_invalid_str_with_numbers);
    RUN_TEST(test_invalid_str_without_numbers);
    RUN_TEST(test_empty_str);
    RUN_TEST(test_null_str);
    RUN_TEST(test_neg_value_str);
    RUN_TEST(test_decimal_value_str);
    RUN_TEST(test_mixed_valid_and_invalid_values_str);

    return UNITY_END();
}
