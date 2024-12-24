main: files/main.o files/usac07.o | files
	gcc files/main.o files/usac07.o -z noexecstack -o files/main
	
files/main.o: src/main.c | files
	gcc -g -Wall -Wextra -fanalyzer -c src/main.c -o files/main.o
	
files/usac07.o: src/usac07.s | files
	gcc -g -Wall -Wextra -fanalyzer -c src/usac07.s -o files/usac07.o

files/test_usac07.o: tests/test_usac07.c | files
	gcc -g -Wall -Wextra -fanalyzer -c tests/main.c -o files/test_usac07.o

files/unity.o: ../unity_folder/unity.c | files
	gcc -g -Wall -Wextra -fanalyzer -c ../unity_folder/unity.c -o files/unity.o

tests: files/test_usac07.o files/unity.o files/usac07.o
	gcc files/test_usac07.o files/usac07.o files/unity.o -o files/test_executable

files:
	mkdir -p files

run: main
	files/./main
	
clean:
	rm -f files/*.o files/main files/test_executable

unity_tests: tests
	files/./test_executable
