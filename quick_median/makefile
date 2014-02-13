SRC=quickmedian.java
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
	@echo "Start testing"
	@CNT=10; for i in `seq 1 6`;\
		do\
		CNT=$$(($$CNT*10));\
		$(RUN) $(TARG) $$CNT;\
		done;\


clean:
	@echo "Removing $(OBJS).."
	@rm -f $(OBJS) *.class

debug:
	@echo "Switching on debug mode..."
	@sed -i 's/DEBUG=false/DEBUG=true/' $(SRC)

nodebug:
	@echo "Switching off debug mode..."
	@sed -i 's/DEBUG=true/DEBUG=false/' $(SRC)

