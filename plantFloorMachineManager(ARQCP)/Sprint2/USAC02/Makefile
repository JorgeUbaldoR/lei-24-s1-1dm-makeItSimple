executable: files/main.o files/usac02.o | files
	gcc files/main.o files/usac02.o -z noexecstack -o files/executable

files/main.o: src/main.c | files
	gcc -g -Wall -Wextra -fanalyzer -c src/main.c -o files/main.o

files/usac02.o: src/usac02.s | files 
	gcc -g -Wall -Wextra -fanalyzer -c src/usac02.s -o files/usac02.o

files/test_usac02.o: tests/test_usac02.c | files
	gcc -g -Wall -Wextra -fanalyzer -c tests/test_usac02.c -o files/test_usac02.o

files/unity.o: ../unity_folder/unity.c | files
	gcc -g -Wall -Wextra -fanalyzer -c ../unity_folder/unity.c -o files/unity.o

tests: files/test_usac02.o files/unity.o files/usac02.o | files
	gcc files/test_usac02.o files/usac02.o files/unity.o -o files/test_executable



files:
	mkdir -p files

clean:
	rm -f files/*.o files/executable files/test_executable



run: executable 
	files/./executable

unity_tests: tests
	files/./test_executable
