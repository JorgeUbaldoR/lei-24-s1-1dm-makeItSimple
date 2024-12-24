#include "../../unity_folder/unity.h"
#include "../include/asm.h"

void setUp(void) {
}

void tearDown(void) {
}

// Teste básico com head antes do tail
void test_1(void) {
    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 9;
    int array[length];
    int n = 5;

    int expected_output = 1;
    int expected_tail = 9;
    int expected_head = 5;
    int expected_array[] = {0, 1, 2, 3, 4};
    
    int output = move_n_to_array(buffer, length, &tail, &head, n, array);
        
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, array, n);
}

// Teste com head após o tail
void test_head_after_tail(void) {
    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 7;
    int tail = 3;
    int array[length];
    int n = 6;

    int expected_output = 1;
    int expected_tail = 3;
    int expected_head = 3;
    int expected_array[] = {7, 8, 9, 0, 1, 2};
    
    int output = move_n_to_array(buffer, length, &tail, &head, n, array);
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, array, n);
}

// Teste onde o número de elementos a mover excede os disponíveis
void test_insufficient_elements(void) {
    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 3;
    int tail = 5;
    int array[length];
    int n = 5;

    int expected_output = 0; // Falha
    int expected_tail = 5;
    int expected_head = 3;

    int output = move_n_to_array(buffer, length, &tail, &head, n, array);
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
}

// Teste com buffer inicialmente vazio
void test_empty_buffer(void) {
    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 0;
    int array[length];
    int n = 3;

    int expected_output = 0; // Falha
    int expected_tail = 0;
    int expected_head = 0;

    int output = move_n_to_array(buffer, length, &tail, &head, n, array);
    
    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
}

// Teste onde todos os elementos são movidos
void test_move_all_elements(void) {
    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 9;
    int array[length];
    int n = 10;

    int expected_output = 1; // Sucesso
    int expected_tail = 9;
    int expected_head = 9; // Buffer vazio após mover todos
    int expected_array[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    int output = move_n_to_array(buffer, length, &tail, &head, n, array);

    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, array, n);
}

// Teste onde n é zero (nada deve ser movido)
void test_n_is_zero(void) {
    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 2;
    int tail = 7;
    int array[length];
    int n = 0;

    int expected_output = 1; // Sucesso, mesmo sem mover nada
    int expected_tail = 7;
    int expected_head = 2;

    int output = move_n_to_array(buffer, length, &tail, &head, n, array);

    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
}

// Teste com buffer de tamanho 1 (head == tail)
void test_single_element_buffer(void) {
    int buffer[] = {42}; // Apenas um elemento
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 0;
    int array[length];
    int n = 1;

    int expected_output = 1; // Sucesso, movendo o único elemento
    int expected_tail = 0;
    int expected_head = 0; // Fica vazio
    int expected_array[] = {42};

    int output = move_n_to_array(buffer, length, &tail, &head, n, array);

    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, array, n);
}

// Teste com buffer circular "cheio" (head é exatamente após tail)
void test_full_circular_buffer(void) {
    int buffer[] = {10,11,12,13,14,15,16,17,18,19};
    int length = sizeof(buffer) / sizeof(int);
    int head = 2;
    int tail = 1; // head > tail -> buffer circular cheio
    int array[length];
    int n = 5;

    int expected_output = 1; // Sucesso
    int expected_tail = 1;
    int expected_head = 7; // Avança o head após mover 5 elementos
    int expected_array[] = {12, 13, 14, 15, 16};

    int output = move_n_to_array(buffer, length, &tail, &head, n, array);

    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_array, array, n);
}

// Teste onde n é maior que o tamanho do buffer (inválido)
void test_n_exceeds_buffer_size(void) {
    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 9;
    int array[length];
    int n = 15; // Maior que o número total de elementos no buffer

    int expected_output = 0; // Falha
    int expected_tail = 9;
    int expected_head = 0;

    int output = move_n_to_array(buffer, length, &tail, &head, n, array);

    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
}

// Teste onde n é negativo (inválido)
void test_negative_n(void) {
    int buffer[] = {0,1,2,3,4,5,6,7,8,9};
    int length = sizeof(buffer) / sizeof(int);
    int head = 0;
    int tail = 9;
    int array[length];
    int n = -3; // Inválido

    int expected_output = 0; // Falha
    int expected_tail = 9;
    int expected_head = 0;

    int output = move_n_to_array(buffer, length, &tail, &head, n, array);

    TEST_ASSERT_EQUAL_INT(expected_output, output);
    TEST_ASSERT_EQUAL_INT(expected_tail, tail);
    TEST_ASSERT_EQUAL_INT(expected_head, head);
}


int main(void) {
    UNITY_BEGIN();
    RUN_TEST(test_1);
    RUN_TEST(test_head_after_tail);
    RUN_TEST(test_insufficient_elements);
    RUN_TEST(test_empty_buffer);
    RUN_TEST(test_move_all_elements);
    RUN_TEST(test_n_is_zero);
    RUN_TEST(test_single_element_buffer);
    RUN_TEST(test_full_circular_buffer);
    RUN_TEST(test_n_exceeds_buffer_size);
    RUN_TEST(test_negative_n);
    
    return UNITY_END();
}
