CC=g++
SRC=main.cpp
BIN=$(subst .cpp,.bin,$(SRC))

all:
	$(CC) -o $(BIN) $(SRC)

.PHONY: clean

clean:
	rm -f $(BIN)
