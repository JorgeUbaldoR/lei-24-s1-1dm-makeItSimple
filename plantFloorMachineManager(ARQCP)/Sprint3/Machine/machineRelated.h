#ifndef MACHINE_RELATED_H
#define MACHINE_RELATED_H

int send_data(char* cmd);
int get_data(int* temp_buffer, int* hum_buffer, int buffer_length, int* head_temp, int* tail_temp, int* head_hum, int* tail_hum);

#endif