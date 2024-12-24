main: files/main.o files/usac03.o | files
	gcc files/main.o files/usac03.o -z noexecstack -o files/main
	
files/main.o: src/main.c | files
	gcc -g -Wall -Wextra -fanalyzer -c src/main.c -o files/main.o
	
files/usac03.o: src/usac03.s | files
	gcc -g -Wall -Wextra -fanalyzer -c src/usac03.s -o files/usac03.o

files/test_usac03.o: tests/test_usac03.c | files
	gcc -g -Wall -Wextra -fanalyzer -c tests/main.c -o files/test_usac03.o

files/unity.o: ../unity_folder/unity.c | files
	gcc -g -Wall -Wextra -fanalyzer -c ../unity_folder/unity.c -o files/unity.o

tests: files/test_usac03.o files/unity.o files/usac03.o
	gcc files/test_usac03.o files/usac03.o files/unity.o -o files/test_executable

files:
	mkdir -p files

run: main
	files/./main
	
clean:
	rm -f files/*.o files/main files/test_executable

unity_tests: tests
	files/./test_executable

debug: run
	gdb -tui files/./main


