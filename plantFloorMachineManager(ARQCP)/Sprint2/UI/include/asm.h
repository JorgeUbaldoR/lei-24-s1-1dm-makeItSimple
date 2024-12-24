#ifndef ASM_H
#define ASM_H
int extract_data(char* str, char* token, char*unit, int* value);
int get_number_binary(int n, char* bits);
int get_number (char* a, int* b);
int dequeue_value (int* a, int b, int* c, int* d, int* e);
int get_n_element (int* a, int b, int* c, int* d);
int sort_array (int* a, int b, char c);
int median(int* vec, int length, int* me);
int format_command(char* op, int n, char *cmd);
int enqueue_value(int* buffer, int length, int*tail, int* head, int value);
int move_n_to_array(int* buffer, int length, int *tail, int *head, int n, int* array);
#endif