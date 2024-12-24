main: files/main.o files/usac09.o | files
	gcc files/main.o files/usac09.o -z noexecstack -o files/main
	
files/main.o: src/main.c | files
	gcc -g -Wall -Wextra -fanalyzer -c src/main.c -o files/main.o
	
files/usac09.o: src/usac09.s | files
	gcc -g -Wall -Wextra -fanalyzer -c src/usac09.s -o files/usac09.o

files/test_usac09.o: tests/test_usac09.c | files
	gcc -g -Wall -Wextra -fanalyzer -c tests/main.c -o files/test_usac09.o

files/unity.o: ../unity_folder/unity.c | files
	gcc -g -Wall -Wextra -fanalyzer -c ../unity_folder/unity.c -o files/unity.o

tests: files/test_usac09.o files/unity.o files/usac09.o
	gcc files/test_usac09.o files/usac09.o files/unity.o -o files/test_executable

files:
	mkdir -p files

run: main
	files/./main
	
clean:
	rm -f files/*.o files/main files/test_executable

unity_tests: tests
	files/./test_executable
