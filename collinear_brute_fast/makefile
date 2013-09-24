SRC=Brute.java Fast.java Point.java
OBJS=$(subst .java,.class,$(SRC))
TARG=$(subst .class,,$(OBJS))
CC=javac
RUN=java
ZARCH=collinear.zip
EXTCLASSES=StdIn StdOut StdRandom StdStats StdArrayIO In StdDraw

all: $(OBJS)

%.class:%.java
	@echo "Compiling..."
	@mkdir libs 2>/dev/null; cd libs; jar xf ../../stdlib.jar; jar xf ../../algs4.jar; cd ..;
	@$(foreach ext_class,$(EXTCLASSES),cp libs/$(ext_class).java .;)
	@$(CC) -Xlint $<

.PHONY:

clean:
	@echo "Removing $(OBJS), $(ZARCH)..."
	@rm -f $(OBJS) $(ZARCH)
	@$(foreach ext_class,$(EXTCLASSES),echo "Remove $(ext_class).[java,class]"; rm -f $(ext_class).java;)
	@rm -rf libs *.class

run:
	@echo "****************************************************************"
	@echo "Here a place for unit tests"
	@echo "****************************************************************"

zip:
	@echo "Creating *.zip archive..."
	@zip ~/$(ZARCH) $(SRC)
	@echo "~/$(ZARCH) has been created."
