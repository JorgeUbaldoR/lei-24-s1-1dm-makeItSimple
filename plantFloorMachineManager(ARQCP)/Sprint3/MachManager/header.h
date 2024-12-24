#include <time.h>
#ifndef HEADER_H
#define HEADER_H

extern int NUMBER_OF_MACHINES;
extern int NUMBER_OF_OPERATIONS;
extern short* ALL_MACHINE_IDS;
extern char* ALL_OPERATION_IDS;


typedef struct {
    char in_operation[3];
    char available[3];
    char unavailable[4];
} State;

typedef struct {
    time_t beginning;
    int temperature;
    int humidity;
    char state[4];

} Read_Values;

typedef struct {
    char op_designation[100];
    char op_number;
    Read_Values reading_values;
    
} Operation;

typedef struct {
    Operation* operations;
    short identifier;
    char name[30];
    unsigned char min_temp;
    unsigned char max_temp;
    unsigned char min_hum;
    unsigned char max_hum;
    int buffer_length;
    int median_window;
    int n_operations;
    int n_operations_performed;
    char* operation_sequence;   
    
} Machine;


void init_states(State* state);
int add_machine_manually(Machine** m, short identifier, char name[30], unsigned char min_temp, unsigned char max_temp, unsigned char min_hum, unsigned char max_hum, int buffer_length, int median_window);
int add_operation_manually(Machine* machines, short machine_id, char designation[100], char op_number);
Machine* get_machines_from_file(void);
int write_machine_operations_file(Machine* machine);
void extend_machines(Machine** m);
void extend_machine_ids_container(void);
void extend_operation_ids_container(void);
void extend_operation_sequence(Machine* selected_machine, char** operation_sequence);
void fill_machines_operations(Machine* m);
void init_operations(Machine* machines);
int remove_machine(Machine** machines, short id);
int add_operation_to_machine(Machine* machines, Operation* op, short id);
int alter_in_operation_state(Machine* machines, short id, char op_number);
int put_machine_to_work(Machine* machines, short id, char op_number);
Machine* get_machine_by_id(Machine* machines, short machine_id);
Operation* get_operation_by_id(Machine* machines, char operation_id);
Operation* get_operation_by_id_duplicate(Machine* machines, char operation_id);
char* get_operation_name_by_id(Machine* machines, char operation_id);
char* command_from_mach_manager(char* state, char op_number);
int check_levels(int* array, int median_window);
int machine_in_operation(Machine* selected_machine);
int perform_instructions_file(Machine* machines);
void temperature_and_humidity_readings(Machine* selected_machine, Operation* selected_operation);

#endif