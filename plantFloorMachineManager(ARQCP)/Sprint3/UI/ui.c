#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "../MachManager/header.h"
#include "UIrelated.h"
#include "../Machine/machineRelated.h"
#include "../../Sprint2/UI/include/asm.h"



int main() {
    State state;
    int option;

    init_states(&state);
    Machine* machines = get_machines_from_file();
    
    init_operations(machines);
    fill_machines_operations(machines);
    
    
    while (1) {
        printf("\n═════════════════════════════════════\n");
        printf("           MachManager UI\n\n");
        printf(" 1.  All machines info\n");
        printf(" 2.  Partial Machines info\n");
        printf(" 3.  Send machine's operations to file\n");
        printf(" 4.  Add machine\n");
        printf(" 5.  Remove machine\n");
        printf(" 6.  Assign operation to machine\n");
        printf(" 7.  Put machine to work\n");
        printf(" 8.  End working operation\n");
        printf(" 9.  Show operation state\n");
        printf(" 10. Execute instructions file\n");
        printf(" 0.  Exit\n");
        printf("═════════════════════════════════════\n");
        
        printf("Select an option: ");

         if (scanf("%d", &option) != 1) {
             printf("Invalid input. Please enter a number.\n");
              while (getchar() != '\n');
              continue;
         }

        switch (option) {
            case 1: 
                print_machines_all_info(machines);
                break;

            case 2: 
                print_machines_name_id(machines);
                break;
            
            case 3:
                short machine_option = show_and_select_machine(machines);
                if (machine_option != 0) {
                 short machine_id = get_machine_id_by_option(machines, machine_option);
                 Machine* selected_machine = get_machine_by_id(machines, machine_id);
                 int file_write = write_machine_operations_file(selected_machine);

                 if (file_write == 1) {
                    printf("\n%s operations sucessfuly loaded in a file!\n", selected_machine->name);
                 }
                  
                } else {
                 printf("\nCanceled...\n");
                }
                break;

            case 4:
                int output = insert_machine_info(&machines);
                show_machine_add_result(output);
                break;

            case 5:    
                remove_machine_ui(machines);
                break;

           case 6:
                machine_option = show_and_select_machine(machines);
                if (machine_option != 0) {
                    short machine_id = get_machine_id_by_option(machines, machine_option);
                    int selection;
                    int added_operation;

                    printf(" 1.  Choose existing operation\n");
                    printf(" 2.  Create a new operation\n\n");
                    printf("Select an option: ");
                    if (scanf("%d", &selection) != 1) {
                        printf("Invalid input. Please enter a number.\n");
                        while (getchar() != '\n');
                        continue;
                    }              

                    switch(selection) {
                        case 1:
                            char operation_id = show_and_select_available_operations(machines, machine_id);
                            Operation* operation = get_operation_by_id(machines, operation_id);
                            added_operation = add_operation_to_machine(machines, operation, machine_id);
                            show_op_add_result(machine_id, added_operation);
                            break;

                        case 2:
                            added_operation = insert_operation_info(machines, machine_id);
                            show_op_add_result(machine_id, added_operation);
                            break;
                            
                        default:
                            printf("Invalid choice. Returning to the main menu...\n");
                    }
                    

                } else {
                    printf("\nCanceled...\n");
                }
                break;
            
            case 7:
                machine_option = show_and_select_machine(machines);

                if (machine_option != 0) {
                    short machine_id = get_machine_id_by_option(machines, machine_option);
                    char operation_option = show_and_select_operation(machines, machine_id);
                    Machine* selected_machine = get_machine_by_id(machines, machine_id);
                    char op_id = get_operation_id_by_option(selected_machine, operation_option);
                    Operation* selected_operation = get_operation_by_id(selected_machine, op_id);

                    if(!machine_in_operation(selected_machine)) {
                    int work = put_machine_to_work(machines, machine_id, op_id);

                    if (work) {
                        char* cmd = command_from_mach_manager(state.in_operation, op_id);
                        printf("\nOperation %hhd now being performed!\n", op_id);
                        printf("%s\n", cmd);
                        send_data(cmd);
                        temperature_and_humidity_readings(selected_machine, selected_operation);
                        
                    } else {
                        printf("Unable to put operation %hhd to be performed!\n", op_id);
                    }

                    } else {
                        printf("\n%s already operating!\n", selected_machine->name);
                        printf("Consider ending current operation to start a new one.\n");
                    }
                } else {
                    printf("\nCanceled...\n");
                }
                
                break;

            case 8:
                short machine_id = show_and_select_in_operation_machine(machines);
                if (machine_id != 0) {
                    char op_id = show_and_select_in_operation_operation(machines, machine_id);
                    int out = alter_in_operation_state(machines, machine_id, op_id);
                    Machine* selected_machine = get_machine_by_id(machines, machine_id);
                    Operation* selected_operation = get_operation_by_id(machines, op_id);

                    if (out) {
                        char* cmd = command_from_mach_manager(state.available, op_id);
                        printf("\nOperation %hhd removed from plant floor!\n", op_id);
                        printf("%s\n", cmd);
                        send_data(cmd);
                        temperature_and_humidity_readings(selected_machine, selected_operation);
                        
                    } else {
                        printf("Unable to remove operation %hhd from plant floor!\n", op_id);
                    }

                } else {
                    printf("\nCanceled...\n");
                }
                break;

            case 9:
                machine_option = show_and_select_machine(machines);
                if (machine_option != 0) {
                    short machine_id = get_machine_id_by_option(machines, machine_option);
                    Machine* selected_machine = get_machine_by_id(machines, machine_id);
                    char operation_option = show_and_select_operation(machines, machine_id);
                    char op_id = get_operation_id_by_option(selected_machine, operation_option);
                    Operation* selected_operation = get_operation_by_id(machines, op_id);

                    if (strncmp(selected_operation->reading_values.state, state.unavailable, sizeof(selected_operation->reading_values.state)) != 0) {
                        char* cmd = command_from_mach_manager(selected_operation->reading_values.state, op_id);
                        printf("%s\n", cmd);
                        send_data(cmd);
                    
                    } else {    
                    printf("Operation %hhd of Machine %hd is OFF!\n", selected_operation->op_number, selected_machine->identifier);
                    }
            
        
            } else {
                printf("\nCanceled...\n");
            }
            break;

            case 10:
                if(perform_instructions_file(machines)){
                    printf("\nFile finished sucessfully!\n");
                } else {
                    printf("\nInsucess reading instructions file!\n");
                }
                break;

            case 0:
                printf("\nExit...\n\n");
                return 0;

            default:
                printf("Invalid choice. Try again...\n");
        }
    }


    free(machines->operations);
    free(machines);
    free(ALL_MACHINE_IDS);
    free(ALL_OPERATION_IDS);
    return 0;

}