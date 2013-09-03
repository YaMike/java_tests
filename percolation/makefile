SRC=$(wildcard *.java)
OBJS=$(subst .java,.class,$(SRC))
TARG=$(subst .class,,$(OBJS))
CC=javac
RUN=java
ZARCH=percolation.zip

all: $(OBJS)

%.class:%.java
	@echo "Compiling..."
	@$(CC) $<

.PHONY:
clean:
	@echo "Removing $(OBJS), $(ZARCH)..."
	@rm -f $(OBJS) $(ZARCH)

run:
	@java $(TARG)

zip:
	@zip $(ZARCH) Percolation.java PercolationStats.java
