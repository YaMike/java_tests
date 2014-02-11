SRC=heapsort.java
OBJS=$(subst .java,.class,$(SRC))
TARG=$(subst .class,,$(OBJS))
CC=javac
RUN=java

all: $(OBJS) 

%.class:%.java
	@echo "Compiling \"$<\"..."
	@$(CC) -Xlint $<

.PHONY:

clean:
	@echo "Removing $(OBJS).."
	@rm -f $(OBJS)

turn_debug:
	@echo "Switching on debug mode..."
	@sed -i 's/DEBUG=false/DEBUG=true/' $(SRC)

