CC=g++
SRC=main.cpp
BIN=$(subst .cpp,.bin,$(SRC))
INPUT_MASK=input_*.txt
TEST_OUTPUT=test_output.txt
OUTPUT=output.txt
CFLAGS=-std=c++11 -Wall -Werror #-DDEBUG 
LDFLAGS=

all:
	$(CC) -o $(BIN) $(CFLAGS) $(SRC) $(LDFLAGS)

.PHONY: clean run

run:
	@for each in `ls -1 $(INPUT_MASK)`; do \
		echo "Test for $$each"; \
		time ./$(BIN) < $$each > $(TEST_OUTPUT); \
		diff $(TEST_OUTPUT) `echo $$each | sed 's/input/output/g'`; \
		[ $$? -eq 0 ] && echo "Succeded" || echo "Failed";\
	done

clean:
	rm -f $(BIN) $(TEST_OUTPUT)
