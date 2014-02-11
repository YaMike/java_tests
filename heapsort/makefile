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

run:
	@CNT=10; for i in `seq 1 6`;\
		do\
		CNT=$$(($$CNT*10));\
		$(RUN) $(TARG) $$CNT;\
		done;\


clean:
	@echo "Removing $(OBJS).."
	@rm -f $(OBJS) *.class

turn_debug:
	@echo "Switching on debug mode..."
	@sed -i 's/DEBUG=false/DEBUG=true/' $(SRC)

