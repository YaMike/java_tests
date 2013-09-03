SRC=$(wildcard *.java)
OBJS=$(subst .java,.class,$(SRC))
TARG=$(subst .class,,$(OBJS))
CC=javac
RUN=java

all: $(OBJS)

%.class:%.java
	@$(CC) $<

.PHONY:
clean:
	@rm -f $(OBJS)

run:
	@java $(TARG)
