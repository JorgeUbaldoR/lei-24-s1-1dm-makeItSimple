# Compiler and flags
CC = gcc
CFLAGS = -g -Wall -Wextra

# Object files
OBJ = files/UI.o \
      ../USAC01/files/usac01.o \
      ../USAC02/files/usac02.o \
      ../USAC03/files/usac03.o \
      ../USAC06/files/usac06.o \
      ../USAC07/files/usac07.o \
      ../USAC09/files/usac09.o \
      ../USAC10/files/usac10.o \
	  ../USAC04/files/usac04.o \
	  ../USAC08/files/usac08.o \
	  ../USAC05/files/usac05.o

# Target executable
TARGET = UI

# Rules
all: $(TARGET)

files/UI.o: src/UI.c
	$(CC) $(CFLAGS) -c src/UI.c -o files/UI.o

../USAC01/files/usac01.o: ../USAC01/src/usac01.s
	$(CC) $(CFLAGS) -c ../USAC01/src/usac01.s -o ../USAC01/files/usac01.o

../USAC02/files/usac02.o: ../USAC02/src/usac02.s
	$(CC) $(CFLAGS) -c ../USAC02/src/usac02.s -o ../USAC02/files/usac02.o

../USAC03/files/usac03.o: ../USAC03/src/usac03.s
	$(CC) $(CFLAGS) -c ../USAC03/src/usac03.s -o ../USAC03/files/usac03.o

../USAC04/files/usac04.o: ../USAC04/src/usac04.s
	$(CC) $(CFLAGS) -c ../USAC04/src/usac04.s -o ../USAC04/files/usac04.o

../USAC05/files/usac05.o: ../USAC05/src/usac05.s
	$(CC) $(CFLAGS) -c ../USAC05/src/usac05.s -o ../USAC05/files/usac05.o

../USAC06/files/usac06.o: ../USAC06/src/usac06.s
	$(CC) $(CFLAGS) -c ../USAC06/src/usac06.s -o ../USAC06/files/usac06.o

../USAC07/files/usac07.o: ../USAC07/src/usac07.s
	$(CC) $(CFLAGS) -c ../USAC07/src/usac07.s -o ../USAC07/files/usac07.o

../USAC08/files/usac08.o: ../USAC08/src/usac08.s
	$(CC) $(CFLAGS) -c ../USAC08/src/usac08.s -o ../USAC08/files/usac08.o

../USAC09/files/usac09.o: ../USAC09/src/usac09.s
	$(CC) $(CFLAGS) -c ../USAC09/src/usac09.s -o ../USAC09/files/usac09.o

../USAC10/files/usac10.o: ../USAC10/src/usac10.s
	$(CC) $(CFLAGS) -c ../USAC10/src/usac10.s -o ../USAC10/files/usac10.o

$(TARGET): $(OBJ)
	$(CC) $(OBJ) -o files/$(TARGET)

run: $(TARGET)
	files/./$(TARGET)

clean:
	rm -f *.o $(TARGET)

.PHONY: all run clean