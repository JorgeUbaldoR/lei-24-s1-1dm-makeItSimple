executable: files/main.o files/usac10.o ../USAC09/files/usac09.o | files
	gcc files/main.o files/usac10.o ../USAC09/files/usac09.o -z noexecstack -o files/executable

files/main.o: src/main.c | files
	gcc -g -Wall -Wextra -fanalyzer -c src/main.c -o files/main.o

files/usac10.o: src/usac10.s ../USAC09/src/usac09.s | files
	gcc -g -Wall -Wextra -fanalyzer -c src/usac10.s -o files/usac10.o

../USAC09/files/usac09.o: ../USAC09/src/usac09.s | files
	gcc -g -Wall -Wextra -fanalyzer -c ../USAC09/src/usac09.s -o ../USAC09/files/usac09.o


files/test_usac10.o: tests/test_usac10.c | files
	gcc -g -Wall -Wextra -fanalyzer -c tests/test_usac10.c -o files/test_usac10.o

files/unity.o: ../unity_folder/unity.c | files
	gcc -g -Wall -Wextra -fanalyzer -c ../unity_folder/unity.c -o files/unity.o

tests: files/test_usac10.o files/unity.o files/usac10.o ../USAC09/files/usac09.o
	gcc files/test_usac10.o files/usac10.o ../USAC09/files/usac09.o files/unity.o -o files/test_executable

files:
	mkdir -p files

clean:
	rm -f files/*.o ../USAC09/files/usac09.o files/executable files/test_executable

run: executable
	files/./executable

unity_tests: tests
	files/./test_executable