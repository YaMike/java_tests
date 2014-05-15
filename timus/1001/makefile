CC=g++
SRC=main.cpp
BIN=$(subst .cpp,.bin,$(SRC))
INPUT=input.txt
TEST_OUTPUT=test_output.txt
OUTPUT=output.txt

all:
	$(CC) -o $(BIN) $(SRC)
	$(MAKE) run

run:
	time ./$(BIN) < $(INPUT) > $(TEST_OUTPUT)
	diff $(TEST_OUTPUT) $(OUTPUT)
	@[ $$? -eq 0 ] && echo "Succeded" || "Fail"

.PHONY: clean

clean:
	rm -f $(BIN) $(TEST_OUTPUT)
