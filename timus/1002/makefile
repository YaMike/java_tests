CC=g++
SRC=main.cpp
BIN=$(subst .cpp,.bin,$(SRC))
INPUT=input.txt
TEST_OUTPUT=test_output.txt
OUTPUT=output.txt
CFLAGS=-std=c++11 -Wall -Werror -ggdb #-DDEBUG
LDFLAGS=

all:
	$(CC) -o $(BIN) $(CFLAGS) $(SRC) $(LDFLAGS)

run:
	time ./$(BIN) < $(INPUT) > $(TEST_OUTPUT)
	diff $(TEST_OUTPUT) $(OUTPUT)
	@[ $$? -eq 0 ] && echo "Succeded" || echo "Failed"

.PHONY: clean

clean:
	rm -f $(BIN) $(TEST_OUTPUT)
