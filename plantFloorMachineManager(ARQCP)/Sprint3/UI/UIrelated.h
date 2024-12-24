#ifndef UIRELATED_H
#define UIRELATED_H


void print_machines_all_info(Machine* machines);
void print_machines_name_id(Machine* machines);
void remove_machine_ui(Machine* machines);
int insert_machine_info(Machine** machine);
void show_machine_add_result(int i);
void show_op_add_result(short machine_id, int j);
short show_and_select_machine(Machine* machines);
char show_and_select_operation(Machine* machines, short id);
short get_machine_id_by_option(Machine* machines, short machine_option);
char get_operation_id_by_option(Machine* machine, char operation_option);
short show_and_select_in_operation_machine(Machine* machines);
char show_and_select_in_operation_operation(Machine* machines, short id);
char show_and_select_available_operations(Machine* machines, short machine_id);
int insert_operation_info(Machine* machines, short machine_id);

#endif