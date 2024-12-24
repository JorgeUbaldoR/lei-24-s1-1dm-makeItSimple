executable: files/main.o files/usac08.o ../USAC07/files/usac07.o | files
	gcc files/main.o files/usac08.o ../USAC07/files/usac07.o -z noexecstack -o files/executable

files/main.o: src/main.c | files
	gcc -g -Wall -Wextra -fanalyzer -c src/main.c -o files/main.o

files/usac08.o: src/usac08.s ../USAC07/src/usac07.s | files
	gcc -g -Wall -Wextra -fanalyzer -c src/usac08.s -o files/usac08.o

../USAC09/files/usac07.o: ../USAC07/src/usac07.s | files
	gcc -g -Wall -Wextra -fanalyzer -c ../USAC07/src/usac07.s -o ../USAC09/files/usac07.o


files/test_usac08.o: tests/test_usac08.c | files
	gcc -g -Wall -Wextra -fanalyzer -c tests/test_usac08.c -o files/test_usac08.o

files/unity.o: ../unity_folder/unity.c | files
	gcc -g -Wall -Wextra -fanalyzer -c ../unity_folder/unity.c -o files/unity.o

tests: files/test_usac08.o files/unity.o files/usac08.o ../USAC07/files/usac07.o
	gcc files/test_usac08.o files/usac08.o ../USAC07/files/usac07.o files/unity.o -o files/test_executable

files:
	mkdir -p files

clean:
	rm -f files/*.o ../USAC09/files/usac09.o files/executable files/test_executable

run: executable
	files/./executable

unity_tests: tests
	files/./test_executable
