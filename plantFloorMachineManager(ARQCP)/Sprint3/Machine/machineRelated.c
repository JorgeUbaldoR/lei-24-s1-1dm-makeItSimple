#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <termios.h>
#include <errno.h>
#include "../MachManager/header.h"
#include "../UI/UIrelated.h"
#include "../../Sprint2/UI/include/asm.h"

#define SERIAL_PORT "/dev/ttyACM0"  
#define BAUDRATE B9600  

int send_data(char* cmd) {
    int serial_port = open(SERIAL_PORT, O_RDWR);

    if (serial_port < 0) {
        perror("Falha ao abrir a porta serial");
        return 0;
    }

    struct termios tty;
    memset(&tty, 0, sizeof(tty));

    if (tcgetattr(serial_port, &tty) != 0) {
        perror("Erro ao obter os parâmetros da porta");
        return 0;
    }

    cfsetispeed(&tty, BAUDRATE);  
    cfsetospeed(&tty, BAUDRATE);  

    
    tty.c_cflag &= ~PARENB; // Desabilita paridade
    tty.c_cflag &= ~CSTOPB; // Usa 1 bit de parada
    tty.c_cflag &= ~CSIZE;  // Limpa o tamanho de palavra
    tty.c_cflag |= CS8;     // Usa 8 bits por palavra
    tty.c_cflag &= ~CRTSCTS; // Desabilita controle de fluxo RTS/CTS
    tty.c_cflag |= CREAD | CLOCAL; // Habilita a leitura e a comunicação local
    tty.c_iflag &= ~(IXON | IXOFF | IXANY); // Desabilita controle de fluxo XON/XOFF
    tty.c_iflag &= ~(ICANON | ECHO | ECHOE | ISIG); // Desabilita a configuração de entrada canônica
    tty.c_oflag &= ~OPOST;  // Desabilita post-processamento
    tty.c_cc[VMIN] = 1;     // Minimo de caracteres para leitura
    tty.c_cc[VTIME] = 10;   // Timeout de 1 segundo para leitura

    if (tcsetattr(serial_port, TCSANOW, &tty) != 0) {
        perror("Erro ao aplicar configurações");
        return 0;
    }

    // Enviar dados para o Arduino
    write(serial_port, cmd, strlen(cmd));
    
    
    close(serial_port);

    return 1;
}

int get_data(int* temp_buffer, int* hum_buffer, int buffer_length, int* head_temp, int* tail_temp, int* head_hum, int* tail_hum) {


    int serial_fd = open(SERIAL_PORT, O_RDWR | O_NOCTTY);
    if (serial_fd == -1) {
        perror("Erro ao abrir a porta serial");
        return 0;
    }

    struct termios tty;
    memset(&tty, 0, sizeof tty);

    if (tcgetattr(serial_fd, &tty) != 0) {
        perror("Erro ao obter atributos da porta serial");
        close(serial_fd);
        return 0;
    }

    cfsetospeed(&tty, BAUDRATE);
    cfsetispeed(&tty, BAUDRATE);

    tty.c_cflag = (tty.c_cflag & ~CSIZE) | CS8; 
    tty.c_iflag &= ~IGNBRK;                     
    tty.c_lflag = 0;                            
    tty.c_oflag = 0;                            
    tty.c_cc[VMIN] = 1;                         
    tty.c_cc[VTIME] = 1;                        

    tty.c_iflag &= ~(IXON | IXOFF | IXANY); 
    tty.c_cflag |= (CLOCAL | CREAD);        

    if (tcsetattr(serial_fd, TCSANOW, &tty) != 0) {
        perror("Erro ao configurar a porta serial");
        close(serial_fd);
        return 0;
    }

    // Leitura limitada a 6 segundos
    char data[256];
    time_t start_time = time(NULL);

    while (time(NULL) - start_time < 6) {
        memset(data, 0, sizeof(data));
        int bytes_read = read(serial_fd, data, sizeof(data) - 1);

        if (bytes_read > 0) {
            data[bytes_read] = '\0'; 
            int temperature, humidity;
            if (sscanf(data, "%d,%d", &temperature, &humidity) == 2) {
                //printf("%d°C, %d%%\n", temperature, humidity);
                enqueue_value(hum_buffer, buffer_length, tail_hum, head_hum, humidity);
                enqueue_value(temp_buffer, buffer_length, tail_temp, head_temp, temperature); 
                   
            } else {
                //printf("Invalid data received!\n");
            }
        } else if (bytes_read == -1) {
            perror("Erro na leitura da porta serial");
            break;
        } else {
            printf("No data\n");
        }
        usleep(100000);
    }

    close(serial_fd);
    return 1;
}