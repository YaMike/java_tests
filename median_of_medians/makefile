JAVA_SRC=quickmedian.java
JAVA_OBJS=$(subst .java,.class,$(JAVA_SRC))
JAVA_TARG=$(subst .class,,$(JAVA_OBJS))
JAVA_CC=javac
JAVA_RUN=java

C_SRC=medianofmedians.c
C_RUN=medianofmedians_c
C_CC=gcc
C_CFLAGS=-O3 -std=c99
C_LDFLAGS=

CPP_SRC=quickmedian.cpp
CPP_RUN=quickmedian_cpp
CPP_CC=g++
CPP_CFLAGS=-O3 -std=c++11
CPP_LDFLAGS=

#all: $(JAVA_OBJS) $(C_RUN) $(CPP_RUN)
all: $(C_RUN)

%.class:%.java
	@echo "Compiling \"$<\"..."
	@$(JAVA_CC) -Xlint $<

$(C_RUN):
	@echo "Compiling \"$(C_RUN)\"..."
	@$(C_CC) $(C_CFLAGS) -o $(C_RUN) $(C_SRC) $(C_LDFLAGS)

$(CPP_RUN):
	@echo "Compiling \"$(CPP_RUN)\"..."
	@$(CPP_CC) $(CPP_CFLAGS) -o $(CPP_RUN) $(CPP_SRC) $(CPP_LDFLAGS)

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
		echo "C++:";\
		./$(CPP_RUN) $$CNT;\
		echo "";\
		done;\

clean:
	@echo "Removing $(JAVA_OBJS), $(C_RUN).."
	@rm -f $(JAVA_OBJS) *.class
	@rm -f $(C_RUN)
	@rm -f $(CPP_RUN)

debug:
	@echo "Switching on debug mode..."
	@sed -i 's/DEBUG=false/DEBUG=true/' $(JAVA_SRC)
	@sed -i 's/DEBUG=false/DEBUG=true/' $(C_SRC)
	@sed -i 's/DEBUG=false/DEBUG=true/' $(CPP_SRC)

nodebug:
	@echo "Switching off debug mode..."
	@sed -i 's/DEBUG=true/DEBUG=false/' $(JAVA_SRC)
	@sed -i 's/DEBUG=true/DEBUG=false/' $(C_SRC)
	@sed -i 's/DEBUG=true/DEBUG=false/' $(CPP_SRC)

