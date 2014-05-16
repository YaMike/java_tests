SRC=$(wildcard *.java)
OBJS=$(subst .java,.class,$(SRC))
TARG=$(subst .class,,$(OBJS))
CC=javac
RUN=java
BIN=LittleEndianStreamsTest

all: $(OBJS) run

%.class:%.java
	@echo "Compiling..."
	@$(CC) -Xlint $<

.PHONY:

run:
	@make tests

tests:
	@rm -rf $(BIN);
	@echo "****************************************************************"
	@$(RUN) $(BIN)
	@echo "****************************************************************"
	@echo "Big endian output:"
	hexdump output.txt
	@echo "Little endian output:"
	hexdump le_output.txt
	@echo "****************************************************************"

clean:
	@echo "Removing $(OBJS), $(PKG)..."
	@rm -f $(OBJS)
	@rm -rf *.class $(BIN) *output.txt

commit:
	@make clean
	@git add *
	@git commit

