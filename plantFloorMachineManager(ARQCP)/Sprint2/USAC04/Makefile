executable: files/main.o files/usac04.o | files
	gcc files/main.o files/usac04.o -z noexecstack -o files/executable

files/main.o: src/main.c | files
	gcc -g -Wall -Wextra -fanalyzer -c src/main.c -o files/main.o

files/usac04.o: src/usac04.s | files
	gcc -g -Wall -Wextra -fanalyzer -c src/usac04.s -o files/usac04.o

files/test_usac04.o: tests/test_usac04.c | files
	gcc -g -Wall -Wextra -fanalyzer -c tests/test_usac04.c -o files/test_usac04.o

files/unity.o: ../unity_folder/unity.c | files
	gcc -g -Wall -Wextra -fanalyzer -c ../unity_folder/unity.c -o files/unity.o

tests: files/test_usac04.o files/unity.o files/usac04.o | files
	gcc files/test_usac04.o files/usac04.o files/unity.o -o files/test_executable



files:
	mkdir -p files

clean:
	rm -f files/*.o files/executable files/test_executable




run: executable
	files/./executable

unity_tests: tests
	files/./test_executable
