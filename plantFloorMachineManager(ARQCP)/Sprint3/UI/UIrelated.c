#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "../MachManager/header.h"
#include "UIrelated.h"

char in_operation_key[] = "OP";

void print_machines_all_info(Machine* machines) {
    for (Machine* machine = machines; machine < machines + NUMBER_OF_MACHINES; machine++) {
        printf("--------------------------\n");
        printf("  Name: %s\n", machine->name);
        printf("  Identifier: %hhd\n", machine->identifier);
        printf("  Min Temperature: %hhd\n", machine->min_temp);
        printf("  Max Temperature: %hhd\n", machine->max_temp);
        printf("  Min Humidity: %hhd\n", machine->min_hum);
        printf("  Max Humidity: %hhd\n", machine->max_hum);
        printf("  Buffer Length: %d\n", machine->buffer_length);
        printf("  Median Window: %d\n\n", machine->median_window);
    
    for (Operation* op = machine->operations; op < machine->operations + machine->n_operations; op++) {
        char time_buffer[100];
        struct tm* time_info = localtime(&op->reading_values.beginning);
        strftime(time_buffer, sizeof(time_buffer), "%Y-%m-%d %H:%M:%S", time_info);
        printf("Operation Details:\n");
        printf("  Operation Designation: %s\n", op->op_designation);
        printf("  Operation Number: %hhd\n", op->op_number);
        printf("  Beginning Time: %s\n", time_buffer);
        printf("  Temperature: %d\n", op->reading_values.temperature);
        printf("  Humidity: %d\n", op->reading_values.humidity);
        printf("  State: %s\n\n", op->reading_values.state);
        
    }
    }
    
}

void print_machines_name_id(Machine* machines) {
    printf("ID || Name\n");
    for (Machine* machine = machines; machine < machines + NUMBER_OF_MACHINES; machine++) {
        printf("--------------\n");
        printf("%hd: %s\n",machine->identifier, machine->name);
    }
    printf("--------------\n");
}

void remove_machine_ui(Machine* machines) {
    short machine_id;
    printf("Enter the machine id you wish to remove: ");
    scanf("%hd", &machine_id);
    int removed_machine = remove_machine(&machines, machine_id);
    

    if (removed_machine == 1) {
        printf("Machine with id %hd removed!\n", machine_id);
    } else {
        printf("Unable to remove machine with id: %hd\n", machine_id);
        printf("-> Machine not found or in operation!\n");
    }

}

 int insert_machine_info(Machine** machines) {
   
    short identifier;
    char name[31];
    unsigned char min_temp, max_temp, min_hum, max_hum;
    int buffer_length, median_window;

    printf("Machine addition: \n");

    printf("Enter its id: ");
    if (scanf("%hd", &identifier) != 1) {
        printf("Error reading machine id.\n");
        return 0;
    }

    printf("Enter its name: ");
    if (scanf("%30s", name) != 1) {
        printf("Error reading machine name.\n");
        return 0;
    }

    printf("Enter minimum temperature: ");
    if (scanf("%hhd", &min_temp) != 1) {
        printf("Error reading minimum temperature.\n");
        return 0;
    }

    printf("Enter maximum temperature: ");
    if (scanf("%hhd", &max_temp) != 1) {
        printf("Error reading maximum temperature.\n");
        return 0;
    }

    printf("Enter minimum humidity: ");
    if (scanf("%hhd", &min_hum) != 1) {
        printf("Error reading minimum humidity.\n");
        return 0;
    }

    printf("Enter maximum humidity: ");
    if (scanf("%hhd", &max_hum) != 1) {
        printf("Error reading maximum humidity.\n");
        return 0;
    }

    printf("Enter the buffer length: ");
    if (scanf("%d", &buffer_length) != 1) {
        printf("Error reading buffer length.\n");
        return 0;
    }

    printf("Enter the median window: ");
    if (scanf("%d", &median_window) != 1) {
        printf("Error reading median window.\n");
        return 0;
    }

    int res = add_machine_manually(machines, identifier, name, min_temp, max_temp, min_hum, max_hum, buffer_length, median_window);
    if (res) {
        return 1;
    } else {
        printf("\nDuplicate machine ID!\n");
    }
    
    return 0; 
}

int insert_operation_info(Machine* machines, short machine_id){
    char designation[100];
    char op_number;

    printf("\nOperation addition: \n");

    printf("Enter its name: ");
    if (scanf("%100s", designation) != 1) {
        printf("Error reading machine name.\n");
        return 0;
    }

    printf("Enter its id: ");
    if (scanf("%hhd", &op_number) != 1) {
        printf("Error reading machine id.\n");
        return 0;
    }

    int res = add_operation_manually(machines, machine_id, designation, op_number);
     if (res) {
        return 1;
    } else {
        printf("\nDuplicate operation ID!\n");
    }
    
    return 0; 

}

void show_machine_add_result(int output) {
    if(output == 1) {
        printf("Machine added successfully.\n");
    } else {
        printf("Machine not added.\n");
    }
}

void show_op_add_result(short machine_id, int added_operation) {
    if (added_operation == 1) {
        printf("Operation added to machine with id %hd!\n", machine_id);

    } else {
        printf("Nothing changed in machine %hd!\n", machine_id);
    }

}

short show_and_select_machine(Machine* machines) {
    int options = 1;
    short chosen;
    int valid = 0;

    printf("\nAvailable machines: \n");
    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        printf("(%d) -> %s\n", options, m->name);
        options++;
    }
     printf("\n(0) -> exit\n");

        do {
        printf("\nChoose a machine: ");
        if (scanf("%hd", &chosen) != 1) { 
            printf("Invalid input! Please enter a valid number!\n");
            while (getchar() != '\n'); 
            continue;
        }
        if (chosen >= 0 && chosen <= NUMBER_OF_MACHINES) {
            valid = 1; 
        } else {
            printf("Invalid choice! Please select a number between 0 and %d.\n", NUMBER_OF_MACHINES);
        }
    } while (!valid);

    return chosen;
}

short show_and_select_in_operation_machine(Machine* machines) {
    short chosen;
    int valid = 0;

    short* in_operation_machines = calloc(NUMBER_OF_MACHINES, sizeof(short));
    if (in_operation_machines == NULL) {
        printf("Error: Memory allocation failed for 'in_operation_machines'.\n");
        return -1; 
    }
    int index = 0;

    printf("\nAvailable machines (IDs): \n");
    for (Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        for (Operation* op = m->operations; op < m->operations + m->n_operations; op++) {
            if (strncmp(op->reading_values.state, in_operation_key, sizeof(op->reading_values.state)) == 0) {
                printf("(%hd) -> %s\n", m->identifier, m->name);
                in_operation_machines[index] = m->identifier;
                index++;
                break; 
            }
        }
    }

    if (index == 0) {
        printf("No machines operating!\n");
        free(in_operation_machines);
        return 0;
    }

    printf("\n(0) -> exit\n");

    do {
        printf("Choose a machine: ");
        if (scanf("%hd", &chosen) != 1) {
            printf("Invalid input. Please enter a valid number!\n");
            while (getchar() != '\n');
            continue;
        }

        if (chosen == 0) { 
            valid = 1;
        } else {
            for (short* i = in_operation_machines; i < in_operation_machines + index; i++) {
                if (chosen == *i) {
                    valid = 1;
                    break;
                }
            }

            if (!valid) {
                printf("Invalid choice! Please select a valid machine ID.\n");
            }
        }
    } while (!valid);

    free(in_operation_machines);
    return chosen;
}

char show_and_select_in_operation_operation(Machine* machines, short id) {
    char chosen;
    int valid = 0;
    char* in_operation_operations = NULL;
    int index = 0;
    
    

    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        if(m->identifier == id) {
            in_operation_operations = calloc(m->n_operations, sizeof(char));
            if (in_operation_operations == NULL) {
                printf("Error: Memory allocation failed for 'in_operation_operations'.\n");
                return -1; 
            }

            for(Operation* op = m->operations; op < m->operations + m->n_operations; op++) {
                if(strncmp(op->reading_values.state, in_operation_key, sizeof(op->reading_values.state)) == 0) {
                    printf("(%hhd) -> %s\n", op->op_number, op->op_designation);
                    in_operation_operations[index] = op->op_number;
                    index++;
                }  
            }
            break;
        }
    }

      do {
        printf("Choose an operation (ID): ");
        if (scanf("%hhd", &chosen) != 1) { 
            printf("Invalid input! Please enter a valid number: ");
            while (getchar() != '\n'); 
            continue;
        }
        for(char* c = in_operation_operations; c < in_operation_operations + index; c++) {
            if(chosen == *c) {
                valid = 1;
                break;
            }
        }
        
        if(!valid) {
            printf("Invalid choice. Please select a valid operation ID!\n");
        }
    } while (!valid);

    free(in_operation_operations);
    return chosen;

}

char show_and_select_operation(Machine* machines, short id) {
    int options = 1;
    char chosen;
    int valid = 0;
    Machine machine_selected;
    

    for(Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        if(m->identifier == id) {
            machine_selected = *m;
            for(Operation* op = m->operations; op < m->operations + m->n_operations; op++) {
                printf("(%d) -> %s\n", options, op->op_designation);
                options++;
            }
            break;
        }
    }

      do {
        printf("Choose an operation: ");
        if (scanf("%hhd", &chosen) != 1) { 
            printf("Invalid input. Please enter a valid number!\n");
            while (getchar() != '\n'); 
            continue;
        }
        if (chosen >= 1 && chosen <= machine_selected.n_operations) {
            valid = 1; 
        } else {
            printf("Invalid choice! Please select a number between 1 and %d.\n", machine_selected.n_operations);
        }
    } while (!valid);

    return chosen;

}

char show_and_select_available_operations(Machine* machines, short machine_id) {
    char chosen;
    int valid = 0;
    Machine* machine_selected = NULL;


    for (Machine* m = machines; m < machines + NUMBER_OF_MACHINES; m++) {
        if (m->identifier == machine_id) {
            machine_selected = m;
            break;
        }
    }

    if (machine_selected == NULL) {
        printf("Machine with ID %d not found!\n", machine_id);
        return -1;
    }

    printf("Available operations:\n");

    int listed_operations = 0;
    for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
        int already_assigned = 0;

        for (Operation* op = machine_selected->operations; op < machine_selected->operations + machine_selected->n_operations; op++) {
            if (ALL_OPERATION_IDS[i] == op->op_number) {
                already_assigned = 1;
                break;
            }
        }

        if (!already_assigned) {
            char* name = get_operation_name_by_id(machines, ALL_OPERATION_IDS[i]);
            printf("(%hhd) %s\n", ALL_OPERATION_IDS[i], name);
            listed_operations++;
        }
    }

    if (listed_operations == 0) {
        printf("No available operations to select.\n");
        return -1;
    }

    do {
        printf("Choose an operation: ");
        if (scanf("%hhd", &chosen) != 1) {
            printf("Invalid input. Please enter a valid number!\n");
            while (getchar() != '\n'); 
            continue;
        }

        for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
            int already_assigned = 0;

            for (Operation* op = machine_selected->operations; op < machine_selected->operations + machine_selected->n_operations; op++) {
                if (ALL_OPERATION_IDS[i] == op->op_number) {
                    already_assigned = 1;
                    break;
                }
            }

            if (!already_assigned && chosen == ALL_OPERATION_IDS[i]) {
                valid = 1;
                break;
            }
        }

        if (!valid) {
            printf("Invalid choice! Please select a valid number.\n");
        }
    } while (!valid);

    return chosen;
}

short get_machine_id_by_option(Machine* machines, short machine_option) {
    return machines[machine_option - 1].identifier;
}

char get_operation_id_by_option(Machine* machine, char operation_option) {
    return machine->operations[operation_option - 1].op_number;
}


