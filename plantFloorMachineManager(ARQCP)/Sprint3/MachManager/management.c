#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "header.h"
#include "../../Sprint2/UI/include/asm.h"
#include "../Machine/machineRelated.h"


int NUMBER_OF_MACHINES = 0;
int NUMBER_OF_OPERATIONS = 0;
short* ALL_MACHINE_IDS = NULL;
char* ALL_OPERATION_IDS = NULL;
char in_operation[] = "OP";
char available[] = "ON";
char unavailable[] = "OFF";


int head_temp = 0;
int tail_temp = 0;
int head_hum = 0;
int tail_hum = 0;
int* array_temp;
int* array_hum;

void extend_operations(Machine* m);
void extend_machine_ids_container(void);
void extend_operation_ids_container(void);
int machine_container_has_id(short machine_id);
void extend_machines(Machine** m);
void compress_machines(Machine** m);
void init_states(State* state);
int machine_has_operation(Machine* current_machine, char operation_id);
int machine_exists(Machine* machines, short machine_id);



int add_machine_manually(Machine** machines, short identifier, char name[30],
  unsigned char min_temp, unsigned char max_temp, unsigned char min_hum,
  unsigned char max_hum, int buffer_length, int median_window) {
    
   
    int duplicate = machine_container_has_id(identifier);
    if (!duplicate) {
    extend_machines(machines);
    

    Machine* new_machine = *machines + NUMBER_OF_MACHINES - 1;
    new_machine->identifier = identifier;
    strncpy(new_machine->name, name, sizeof(new_machine->name) - 1);
    new_machine->name[sizeof(new_machine->name) - 1] = '\0';
    new_machine->min_temp = min_temp;
    new_machine->max_temp = max_temp;
    new_machine->min_hum = min_hum;
    new_machine->max_hum = max_hum;
    new_machine->buffer_length = buffer_length;
    new_machine->median_window = median_window;

    new_machine->n_operations = 0;
    new_machine->operations = NULL;
    new_machine->operation_sequence = NULL;
    ALL_MACHINE_IDS[NUMBER_OF_MACHINES] = identifier;

    return 1;
    }
    return 0;
}

int add_operation_to_machine(Machine* machines, Operation* op, short machine_id) {
    int flag = 0;
    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        if(m->identifier == machine_id) {
            extend_operations(m);
            m->operations[m->n_operations] = *op;
            m->n_operations++;
            
            flag = 1;
            break;
        }
    }
    return flag;

}

int alter_in_operation_state(Machine* machines, short id, char op_number) {
    if (!machines) {
        printf("Error: Machines list is NULL.\n");
        return 0; 
    }

    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        if(m->identifier == id) {
            for(Operation* o = m->operations; o < m->operations + m->n_operations; o++) {
                if(o->op_number == op_number){
                    if(strcmp(o->reading_values.state, in_operation) == 0){
                        strncpy(o->reading_values.state, available, sizeof(o->reading_values.state) - 1);
                        o->reading_values.state[sizeof(o->reading_values.state) - 1] = '\0';
                        return 1;
                    }
                }
            }
        }
    }

    return 0;
}

int put_machine_to_work(Machine* machines, short id, char op_number) {
    if(!machines) {
        printf("Error: Machine list is NULL!\n");
        return 0;
    }

    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        if(m->identifier == id) {
            for(Operation* o = m->operations; o < m->operations + m->n_operations; o++) {
                if(o->op_number == op_number){
                    if(strcmp(o->reading_values.state, in_operation) != 0){
                        strncpy(o->reading_values.state, in_operation, sizeof(o->reading_values.state) - 1);
                        o->reading_values.state[sizeof(o->reading_values.state) - 1] = '\0';
                        extend_operation_sequence(m, &m->operation_sequence);
                        m->operation_sequence[m->n_operations_performed - 1] = o->op_number;
                        return 1;
                    }
                }
            }
        }
    }

    return 0;
}

int remove_machine(Machine** machines, short id) {  
    int flag_output = 0;
    int flag_verification = 0;
    

    for (Machine* m = *machines; m < *machines + NUMBER_OF_MACHINES; m++) {
        if (m->identifier == id) {
            for (int i = 0; i < m->n_operations; i++) {
                if (strcmp(m->operations[i].reading_values.state, in_operation) == 0) {
                    flag_verification = 1;
                    break;
                }
            }

            if (!flag_verification) {

                for (int i = 0; i < m->n_operations; i++) {
                    for (int j = 0; j < NUMBER_OF_OPERATIONS; j++) {
                        if (ALL_OPERATION_IDS[j] == m->operations[i].op_number) {
                            for (int k = j; k < NUMBER_OF_OPERATIONS - 1; k++) {
                                ALL_OPERATION_IDS[k] = ALL_OPERATION_IDS[k + 1];
                            }
                            NUMBER_OF_OPERATIONS--; 
                            break;
                        }
                    }
                }


                for (int i = 0; i < NUMBER_OF_MACHINES; i++) {
                    if (ALL_MACHINE_IDS[i] == id) {
                        for (int j = i; j < NUMBER_OF_MACHINES - 1; j++) {
                            ALL_MACHINE_IDS[j] = ALL_MACHINE_IDS[j + 1];
                        }
                        break;
                    }
                }


                free(m->operations);
                free(m->operation_sequence);

                for (Machine* next = m; next < *machines + NUMBER_OF_MACHINES - 1; next++) {
                    *next = *(next + 1);
                }


                NUMBER_OF_MACHINES--;
                
              

                compress_machines(machines);
                flag_output = 1;
            }
            break;
        }
    }

    return flag_output;
}

Machine* get_machines_from_file(void) {
    Machine* machines = NULL;   

    FILE* file = fopen("MachManager/files/machine_info.txt", "r");
    if (file == NULL) {
        printf("Machines file not found!\n");
        exit(1);
    }

    char line[300];
    int index = 0;

    if (fgets(line, sizeof(line), file) == NULL) {
        printf("Machines file is empty or only contains header!\n");
        fclose(file);
        exit(1);
    }

    while (fgets(line, sizeof(line), file) != NULL) {
        Machine* temp = realloc(machines, (index + 1) * sizeof(Machine));
        if (temp == NULL) {
            printf("Error allocating memory for machines!\n");
            free(machines);
            fclose(file);
            exit(1);
        }
        machines = temp;

        char* split_token = strtok(line, ",");
        extend_machine_ids_container();
        int duplicate = machine_container_has_id((short)atoi(split_token));

        if(!duplicate) {
        ALL_MACHINE_IDS[NUMBER_OF_MACHINES] = (short)atoi(split_token);
        machines[index].identifier = (short)atoi(split_token);
        
        split_token = strtok(NULL, ",");
        strncpy(machines[index].name, split_token, sizeof(machines[index].name) - 1);
        machines[index].name[sizeof(machines[index].name) - 1] = '\0';

        split_token = strtok(NULL, ",");
        machines[index].min_temp = (unsigned char)atoi(split_token);

        split_token = strtok(NULL, ",");
        machines[index].max_temp = (unsigned char)atoi(split_token);

        split_token = strtok(NULL, ",");
        machines[index].min_hum = (unsigned char)atoi(split_token);

        split_token = strtok(NULL, ",");
        machines[index].max_hum = (unsigned char)atoi(split_token);

        split_token = strtok(NULL, ",");
        machines[index].buffer_length = atoi(split_token);

        split_token = strtok(NULL, ",");
        machines[index].median_window = atoi(split_token);

        machines[index].n_operations = 0;
        machines[index].operations = NULL;

        NUMBER_OF_MACHINES++;
        index++;
        
        } else {
            continue;
        }
    }

    fclose(file);
    return machines;
}

int perform_instructions_file(Machine* machines) {
    FILE* file = fopen("MachManager/files/instructions_file.txt", "r");
    if (file == NULL) {
        printf("Machines file not found!\n");
        exit(1);
    }

    char line[300];

    if (fgets(line, sizeof(line), file) == NULL) {
        printf("Instructions file is empty or only contains header!\n");
        fclose(file);
        return 0;
    }

    while (fgets(line, sizeof(line), file) != NULL) {
        char* split_token = strtok(line, ",");
        char state[4];
        strncpy(state, split_token, sizeof(state) - 1);
        state[sizeof(state) - 1] = '\0';

        split_token = strtok(NULL, ",");
        short machine_id = (short)atoi(split_token);

        split_token = strtok(NULL, ",");
        char operation_id = (char)atoi(split_token);


        if(machine_exists(machines, machine_id)) {
            Machine* current_machine = get_machine_by_id(machines, machine_id);
    
            if(machine_has_operation(current_machine, operation_id)) {
                Operation* current_operation = get_operation_by_id(current_machine, operation_id);

                if(strcmp(state, unavailable) == 0) {
                    strncpy(current_operation->reading_values.state, state, sizeof(current_operation->reading_values.state));
                    current_operation->reading_values.state[sizeof(current_operation->reading_values.state) - 1] = '\0';
                    current_operation->reading_values.humidity = 0;
                    current_operation->reading_values.temperature = 0;
                    printf("\nOperation %hhd from %s shutted down!\n",current_operation->op_number, current_machine->name);
                    continue;
                }

                if(strcmp(state, available) == 0) {
                    strncpy(current_operation->reading_values.state, state, sizeof(current_operation->reading_values.state));
                    current_operation->reading_values.state[sizeof(current_operation->reading_values.state) - 1] = '\0';
                } 
   
                    if(!machine_in_operation(current_machine)) {
                        strncpy(current_operation->reading_values.state, state, sizeof(current_operation->reading_values.state));
                        current_operation->reading_values.state[sizeof(current_operation->reading_values.state) - 1] = '\0';
                        char* cmd = command_from_mach_manager(state, operation_id);
                        if(strcmp(state, in_operation) == 0) {
                            extend_operation_sequence(current_machine, &current_machine->operation_sequence);
                            current_machine->operation_sequence[current_machine->n_operations_performed - 1] = operation_id;
                        }

                        printf("\nOperation %hhd from %s now being turned %s!\n", operation_id, current_machine->name, state);
                        printf("%s\n", cmd);
                        send_data(cmd);
                        temperature_and_humidity_readings(current_machine, current_operation);
                        usleep(1400000);
            
                    } else {
                        printf("\n%s already working!\n", current_machine->name);
                        printf("Next instruction...\n");
                    }
             
            } else {
                printf("\n%s does not support operation %hhd!\n", current_machine->name, operation_id);
                printf("Next instruction...\n");
            }
        } else {
            printf("\nMachine with id %hd is not available in the system!\n", machine_id);
            printf("Next instruction...\n");
        }
    }

    fclose(file);
    return 1;

}

void fill_machines_operations(Machine* machines) {
    FILE* file = fopen("MachManager/files/operations_info.txt", "r");
    State state;
    init_states(&state);

    if (file == NULL) {
        printf("Operations file not found!\n");
        exit(1);
    }

    char line[300];
    

     if (fgets(line, sizeof(line), file) == NULL) {
        printf("Operations file is empty or only contains header!\n");
        free(machines);
        fclose(file);
        exit(1);
    }

    while (fgets(line, sizeof(line), file) != NULL) {
       
        char* split_token = strtok(line, ",");
        char f_op_designation[100];
        strncpy(f_op_designation, split_token, sizeof(f_op_designation) - 1);
        f_op_designation[sizeof(f_op_designation) - 1] = '\0';

        split_token = strtok(NULL, ",");
        char f_op_number = (char)atoi(split_token);
        extend_operation_ids_container();
        ALL_OPERATION_IDS[NUMBER_OF_OPERATIONS] = f_op_number;
        

        split_token = strtok(NULL, ",");
        short m_id = (short)atoi(split_token);


        for (Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
            if (m->identifier == m_id) {

                extend_operations(m);

                Operation* new_operation = &m->operations[m->n_operations];

                strncpy(new_operation->op_designation, f_op_designation, sizeof(new_operation->op_designation) - 1);
                new_operation->op_designation[sizeof(new_operation->op_designation) - 1] = '\0';

                new_operation->op_number = f_op_number;
                new_operation->reading_values.beginning = 0; 
                new_operation->reading_values.temperature = 0; 
                new_operation->reading_values.humidity = 0; 
                strncpy(new_operation->reading_values.state, state.unavailable, sizeof(new_operation->reading_values.state));

        
                m->n_operations++;
            }
        }

        NUMBER_OF_OPERATIONS++;
        
        
    }
    fclose(file);

}

int add_operation_manually(Machine* machines, short machine_id, char designation[100], char op_number) {
    State state;
    init_states(&state);

    for(int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
        if(op_number == ALL_OPERATION_IDS[i]) {
            return 0;
        }
    }

    for (Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        if (m->identifier == machine_id) {
            extend_operations(m); 

            Operation* op = &m->operations[m->n_operations]; 

            strncpy(op->op_designation, designation, sizeof(op->op_designation) - 1);
            op->op_designation[sizeof(op->op_designation) - 1] = '\0'; 
            op->op_number = op_number;
            op->reading_values.beginning = 0;
            op->reading_values.temperature = 0;
            op->reading_values.humidity = 0;
            strncpy(op->reading_values.state, state.unavailable, sizeof(op->reading_values.state) - 1);

            m->n_operations++; 
            NUMBER_OF_OPERATIONS++;
            ALL_OPERATION_IDS[NUMBER_OF_OPERATIONS] = op_number;
            return 1; 
        }
    }
    return 0; 
}

int write_machine_operations_file(Machine* machine) {
    int flag = 0;

    FILE* file = fopen("MachManager/files/machine_operations.csv", "w");
    if (file == NULL) {
        printf("Error: Could not open file for writing.\n");
        return 0; 
    }
    if(machine->n_operations_performed > 0) {
        flag = 1;
    }

    fprintf(file, "%s operations\n", machine->name);
    Operation* operation = NULL;

    for(char* c = machine->operation_sequence; c < machine->operation_sequence + machine->n_operations_performed; c++) {
        operation = get_operation_by_id(machine, *c);
        fprintf(file, "%hhd,%s\n", *c, operation->op_designation);
    }


    

    
    if(!flag) {
        fprintf(file, "No operations were performed!\n");
    }
    fclose(file);
    return 1;
}

void extend_operations(Machine* m) {
    Operation* temp = realloc(m->operations, (m->n_operations + 1) * sizeof(Operation));
    if (temp == NULL) {
      printf("Error allocationg memory!\n");
      free(m->operations);
      m->operations = NULL;
      m->operation_sequence = NULL;
      exit(1);
    }
    m->operations = temp;
    
}

void extend_machine_ids_container(void) {
    short* temp = realloc(ALL_MACHINE_IDS, (NUMBER_OF_MACHINES + 1) * sizeof(short));
    if (temp == NULL) {
        printf("Error allocationg memory!\n");
        free(ALL_MACHINE_IDS);
        ALL_MACHINE_IDS = NULL;
        exit(1);
    }
    ALL_MACHINE_IDS = temp;
    
}

void extend_operation_ids_container(void) {
    char* temp = realloc(ALL_OPERATION_IDS, (NUMBER_OF_OPERATIONS + 1) * sizeof(char));
    if (temp == NULL) {
        printf("Error allocationg memory!\n");
        free(ALL_OPERATION_IDS);
        ALL_OPERATION_IDS = NULL;
        exit(1);
    }
    ALL_OPERATION_IDS = temp;
    
}

int machine_container_has_id(short machine_id) {
    for(short* s = ALL_MACHINE_IDS; s < ALL_MACHINE_IDS + NUMBER_OF_MACHINES; s++) {
        if(*s == machine_id) {
            return 1;
        }
    }
    return 0;
}

void extend_machines(Machine** m) {
    Machine* temp = realloc(*m, (NUMBER_OF_MACHINES + 1) * sizeof(Machine)); 
    if (temp == NULL) {
        printf("Error allocationg memory!\n");
        free(*m);
        exit(1);
    }
    *m = temp;
    NUMBER_OF_MACHINES++;
    
}

void extend_operation_sequence(Machine* selected_machine, char** operation_sequence) {
    char* temp = realloc(*operation_sequence, (selected_machine->n_operations_performed + 1) * sizeof(char));
    if(temp == NULL) {
        printf("Error allocationg memory!\n");
        free(*operation_sequence);
        exit(1);
    }
    *operation_sequence = temp;
    selected_machine->n_operations_performed++;
}

void compress_machines(Machine** machines) {
    if (NUMBER_OF_MACHINES == 0) {
        free(*machines);
        *machines = NULL; 
        return;
    }

    Machine* temp = realloc(*machines, NUMBER_OF_MACHINES * sizeof(Machine));
    if (temp == NULL) {
        printf("Error reallocating memory!\n");
        free(*machines);
        exit(1);
    }
    *machines = temp;
}

void init_operations(Machine* machines){
    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        m->n_operations = 0;
        m->n_operations_performed = 0;
        m->operations = NULL;
        m->operation_sequence = NULL;
    }
}

void init_states(State* state) {

    strncpy(state->in_operation, "OP", sizeof(state->in_operation) - 1);
    state->in_operation[sizeof(state->in_operation) - 1] = '\0';

    strncpy(state->available, "ON", sizeof(state->available) - 1);
    state->available[sizeof(state->available) - 1] = '\0';
    
    strncpy(state->unavailable, "OFF", sizeof(state->unavailable) - 1);
    state->unavailable[sizeof(state->unavailable) - 1] = '\0';
}

Machine* get_machine_by_id(Machine* machines, short machine_id) {
    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        if(m->identifier == machine_id) {
            return m;
        }
    }
}

Operation* get_operation_by_id(Machine* machines, char operation_id) {
    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        for(Operation* op = m->operations; op < m->operations + m->n_operations; op++) {
            if(op->op_number == operation_id) {
                return op;
            }
        }
    }
}

char* get_operation_name_by_id(Machine* machines, char operation_id) {
    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        for(Operation* op = m->operations; op < m->operations + m->n_operations; op++) {
            if(op->op_number == operation_id) {
                return op->op_designation;
            }
        }
    }
}

char* command_from_mach_manager(char* state, char op_number) {
    char* cmd = calloc(9, sizeof(char));
    if(cmd == NULL) {
        printf("Error allocating memory!\n");
        exit(1);
    }

    int res = format_command(state, (int)op_number, cmd);
    if (res) {
        return cmd;
    }
    return NULL;
}

int check_levels(int* array, int median_window) {
    int med;
    int res = median(array, median_window, &med);
    if (res) {
        return med;
    }
    return -1;
}

int machine_in_operation(Machine* selected_machine) {
    if (selected_machine == NULL || selected_machine->operations == NULL) {
        return 0; 
        
    }

    for (Operation* op = selected_machine->operations; op < selected_machine->operations + selected_machine->n_operations; op++) {
        if (strcmp(op->reading_values.state, in_operation) == 0) {
            return 1; 
        }
    }
    return 0;
}

int machine_has_operation(Machine* current_machine, char operation_id) {
    if (current_machine == NULL || current_machine->operations == NULL) {
        return 0; 
    }

    for(Operation* op = current_machine->operations; op < current_machine->operations + current_machine->n_operations; op++) {
        if(op->op_number == operation_id) {
            return 1;
        }
    }
    return 0;
}

int machine_exists(Machine* machines, short machine_id) {
    if(machines == NULL) {
        return 0;
    }

    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        if(m->identifier == machine_id) {
            return 1;
        }
    }
    return 0;
}

void check_for_alerts(int median_temp, int median_hum, Machine* selected_machine) {
        if(median_temp > selected_machine->max_temp || median_temp < selected_machine->min_temp) {
            printf("Temperature out of specified range!\n");
        }

        if(median_hum > selected_machine->max_hum || median_hum < selected_machine->min_hum) {
            printf("Humidity out of specified range!\n");
        }
    
}

void reset_heads_tails(int* head_temp, int* tail_temp, int* head_hum, int* tail_hum) {
    *head_temp = 0;
    *tail_temp = 0;
    *head_hum = 0;
    *tail_hum = 0;
}

void temperature_and_humidity_readings(Machine* selected_machine, Operation* selected_operation) {
     int* temp_buffer = (int*)calloc(selected_machine->buffer_length, sizeof(int));
     int* hum_buffer = (int*)calloc(selected_machine->buffer_length, sizeof(int));
                        

    int data = get_data(temp_buffer, hum_buffer, selected_machine->buffer_length, &head_temp, &tail_temp, &head_hum, &tail_hum);
    if(data) {
        
        selected_operation->reading_values.beginning = time(NULL);

        int number_of_temp_elements = 0;
        int number_of_hum_elements = 0;
        array_temp = (int*)calloc(selected_machine->median_window, sizeof(int));
        array_hum = (int*)calloc(selected_machine->median_window, sizeof(int));

        number_of_temp_elements = get_n_element(temp_buffer, selected_machine->buffer_length, &tail_temp, &head_temp);
        number_of_hum_elements = get_n_element(hum_buffer, selected_machine->buffer_length, &tail_hum, &head_hum);
                           
        if(number_of_temp_elements >= selected_machine->median_window || number_of_hum_elements >= selected_machine->median_window) {
            int movement_temp = move_n_to_array(temp_buffer, selected_machine->buffer_length, &tail_temp, &head_temp, selected_machine->median_window, array_temp);
            int movement_hum = move_n_to_array(hum_buffer, selected_machine->buffer_length, &tail_hum, &head_hum, selected_machine->median_window, array_hum);
            int median_temp = 0;
            int median_hum = 0;

            if(movement_temp) {
                median_temp = check_levels(array_temp, selected_machine->median_window);
                selected_operation->reading_values.temperature = median_temp;
                                
                }

            if (movement_hum) {
                median_hum = check_levels(array_hum, selected_machine->median_window);
                selected_operation->reading_values.humidity = median_hum;
                }
                check_for_alerts(median_temp, median_hum, selected_machine);
            }
        } 

        free(temp_buffer);
        free(hum_buffer);
        free(array_temp);
        free(array_hum);
        reset_heads_tails(&head_temp, &tail_temp, &head_hum, &tail_hum);
}