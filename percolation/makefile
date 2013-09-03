SRC=$(wildcard *.java)
OBJS=$(subst .java,.class,$(SRC))
TARG=$(subst .class,,$(OBJS))
CC=javac
RUN=java
ZARCH=percolation.zip
EXTCLASSES=StdIn StdOut StdRandom StdStats StdDraw StdArrayIO

all: $(OBJS)

%.class:%.java
	@echo "Compiling..."
	@mkdir libs 2>/dev/null; cd libs; jar xf ../stdlib.jar; cd ..;
	@$(foreach ext_class,$(EXTCLASSES),cp libs/$(ext_class).java .;)
	@$(CC) -Xlint $<

.PHONY:
clean:
	@echo "Removing $(OBJS), $(ZARCH)..."
	@rm -f $(OBJS) $(ZARCH)
	@$(foreach ext_class,$(EXTCLASSES),echo "Remove $(ext_class).[java,class]"; rm -f $(ext_class).java;)
	@rm -rf libs

run:
	@echo 10 50 | java PercolationStats

zip:
	@zip $(ZARCH) Percolation.java PercolationStats.java
