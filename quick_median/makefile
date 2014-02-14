JAVA_SRC=quickmedian.java
JAVA_OBJS=$(subst .java,.class,$(JAVA_SRC))
JAVA_TARG=$(subst .class,,$(JAVA_OBJS))
JAVA_CC=javac
JAVA_RUN=java

C_SRC=quickmedian.c
C_RUN=quickmedian
C_CC=gcc
C_CFLAGS=-O3 -std=c99
C_LDFLAGS=

all: $(JAVA_OBJS) $(C_RUN)

%.class:%.java
	@echo "Compiling \"$<\"..."
	@$(JAVA_CC) -Xlint $<

$(C_RUN):
	@echo "Compiling \"$(C_RUN)\"..."
	@$(C_CC) $(C_CFLAGS) -o $(C_RUN) $(C_SRC) $(C_LDFLAGS)

.PHONY:

run:
	@echo "Start testing..."
	@CNT=10; for i in `seq 1 6`;\
		do\
		CNT=$$(($$CNT*10));\
		echo "Java:";\
		$(JAVA_RUN) $(JAVA_TARG) $$CNT;\
		echo "C:";\
		./$(C_RUN) $$CNT;\
		echo "";\
		done;\

clean:
	@echo "Removing $(JAVA_OBJS), $(C_RUN).."
	@rm -f $(JAVA_OBJS) *.class
	@rm -f $(C_RUN)

debug:
	@echo "Switching on debug mode..."
	@sed -i 's/DEBUG=false/DEBUG=true/' $(JAVA_SRC)

nodebug:
	@echo "Switching off debug mode..."
	@sed -i 's/DEBUG=true/DEBUG=false/' $(JAVA_SRC)

