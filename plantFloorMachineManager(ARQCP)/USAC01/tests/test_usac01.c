#include "../../unity_folder/unity.h"
#include "../include/asm.h"  

void setUp(void) {
}

void tearDown(void) {
}

void test_extract_data_with_valid_temp_token(void) {
    char str[] = "TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80";
    char token[] = "temp";                    
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(1, res);                
    TEST_ASSERT_EQUAL_STRING("celsius", unit);
    TEST_ASSERT_EQUAL(20, value);             
}

void test_extract_data_with_valid_hum_token(void) {
    char str[] = "TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80";
    char token[] = "HUM";
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(1, res);                
    TEST_ASSERT_EQUAL_STRING("percentage", unit); 
    TEST_ASSERT_EQUAL(80, value);             
}

void test_extract_data_with_invalid_token(void) {
    char str[] = "TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80";
    char token[] = "PRES";                    
    char unit[20] = "INVALID";                
    int value = -1;                           

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(0, res);                
    TEST_ASSERT_EQUAL_STRING("", unit);       
    TEST_ASSERT_EQUAL(0, value);              
}

void test_extract_data_with_no_value(void) {
    char str[] = "TEMP&unit:celsius&value:#HUM&unit:percentage&value:80"; 
    char token[] = "TEMP";
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(1, res);                
    TEST_ASSERT_EQUAL_STRING("celsius", unit);       
    TEST_ASSERT_EQUAL(0, value);              
}

void test_extract_data_with_invalid_format(void) {
    char str[] = "TEMP unit celsius value 20"; 
    char token[] = "TEMP";
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(0, res);                
    TEST_ASSERT_EQUAL_STRING("", unit);       
    TEST_ASSERT_EQUAL(0, value);              
}

void test_extract_data_with_mixed_case_token(void) {
    char str[] = "TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80";
    char token[] = "tEmP";                    
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(1, res);                
    TEST_ASSERT_EQUAL_STRING("celsius", unit);
    TEST_ASSERT_EQUAL(20, value);             
}

void test_extract_data_with_extra_characters_in_string(void) {
    char str[] = "TEMP&unit:celsius&value:20extra#HUM&unit:percentage&value:80";
    char token[] = "TEMP";
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(1, res);                
    TEST_ASSERT_EQUAL_STRING("celsius", unit);
    TEST_ASSERT_EQUAL(20, value);             
}

void test_extract_data_with_large_value(void) {
    char str[] = "TEMP&unit:celsius&value:99999#HUM&unit:percentage&value:80";
    char token[] = "TEMP";
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(1, res);                
    TEST_ASSERT_EQUAL_STRING("celsius", unit);
    TEST_ASSERT_EQUAL(99999, value);          
}

void test_extract_data_with_no_unit(void) {
    char str[] = "TEMP&value:25#HUM&unit:percentage&value:80"; 
    char token[] = "TEMP";
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(0, res);                
    TEST_ASSERT_EQUAL_STRING("", unit);       
    TEST_ASSERT_EQUAL(0, value);              
}

void test_extract_data_with_special_characters_in_unit(void) {
    char str[] = "TEMP&unit:%cel!sius&value:20#HUM&unit:percentage&value:80";
    char token[] = "TEMP";
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(1, res);                
    TEST_ASSERT_EQUAL_STRING("%cel!sius", unit); 
    TEST_ASSERT_EQUAL(20, value);             
}

void test_extract_data_with_partial_match_in_string(void) {
    char str[] = "TEMPERATURE&unit:celsius&value:30#HUM&unit:percentage&value:80";
    char token[] = "TEMP";                    
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(0, res);                
    TEST_ASSERT_EQUAL_STRING("", unit);       
    TEST_ASSERT_EQUAL(0, value);              
}

void test_extract_data_with_multiple_tokens_of_same_type(void) {
    char str[] = "TEMP&unit:celsius&value:20#TEMP&unit:kelvin&value:273";
    char token[] = "TEMP";
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(1, res);                
    TEST_ASSERT_EQUAL_STRING("celsius", unit);
    TEST_ASSERT_EQUAL(20, value);            
}

void test_extract_data_with_empty_string(void) {
    char str[] = "";                          
    char token[] = "TEMP";
    char unit[20];
    int value;

    int res = extract_data(str, token, unit, &value);

    TEST_ASSERT_EQUAL(0, res);                
    TEST_ASSERT_EQUAL_STRING("", unit);       
    TEST_ASSERT_EQUAL(0, value);              
}



void run_test(char* str, char* tok, int exp_res, char* exp_unit, int exp_value){
    char unit[20];
    int value;

    int res = extract_data(str,tok, unit, &value);


    TEST_ASSERT_EQUAL(exp_res, res);                
    TEST_ASSERT_EQUAL_STRING(exp_unit, unit);       
    TEST_ASSERT_EQUAL(exp_value, value);  
}

//Testes PI Sprint 2
void test_Null()
{ 
    run_test("","",0,"",0); 
}
void test_One()
{ 
    run_test("TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80","TEMP",1,"celsius",20); 
}
void test_Zero()
{ 
    run_test("TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80","HUM",1,"percentage",80); 
}
void test_Three()
{ 
    run_test("TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80","LEN",0,"",0); 
}
void test_Four()
{ 
    run_test("TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80","EMP",0,"",0); 
}
void test_Five()
{ 
    run_test("TEMP&unit:celsius&value:20#HUM&unit:percentage&value:80","UM",0,"",0); 
}


int main(void) {
    UNITY_BEGIN();

    RUN_TEST(test_extract_data_with_valid_temp_token);
    RUN_TEST(test_extract_data_with_valid_hum_token);
    RUN_TEST(test_extract_data_with_invalid_token);
    RUN_TEST(test_extract_data_with_no_value);
    RUN_TEST(test_extract_data_with_invalid_format);
    RUN_TEST(test_extract_data_with_mixed_case_token);
    RUN_TEST(test_extract_data_with_extra_characters_in_string);
    RUN_TEST(test_extract_data_with_large_value);
    RUN_TEST(test_extract_data_with_no_unit);
    RUN_TEST(test_extract_data_with_special_characters_in_unit);
    RUN_TEST(test_extract_data_with_partial_match_in_string);
    RUN_TEST(test_extract_data_with_multiple_tokens_of_same_type);
    RUN_TEST(test_extract_data_with_empty_string);

    RUN_TEST(test_Null);
    RUN_TEST(test_One);
    RUN_TEST(test_Zero);
    RUN_TEST(test_Three);
    RUN_TEST(test_Four);
    RUN_TEST(test_Five);

    return UNITY_END();
}