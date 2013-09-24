SRC=$(wildcard Percolation*.java)
OBJS=$(subst .java,.class,$(SRC))
TARG=$(subst .class,,$(OBJS))
CC=javac
RUN=java
ZARCH=percolation.zip
EXTCLASSES=StdIn StdOut StdRandom StdStats StdDraw StdArrayIO QuickUnionUF WeightedQuickUnionUF In

all: $(OBJS)

%.class:%.java
	@echo "Compiling..."
	@mkdir libs 2>/dev/null; cd libs; jar xf ../../stdlib.jar; jar xf ../../algs4.jar; cd ..;
	@$(foreach ext_class,$(EXTCLASSES),cp libs/$(ext_class).java .;)
	@$(CC) -Xlint $<

.PHONY:

tests:
	@rm -rf percolation; unzip percolation-testing.zip

clean:
	@echo "Removing $(OBJS), $(ZARCH)..."
	@rm -f $(OBJS) $(ZARCH)
	@$(foreach ext_class,$(EXTCLASSES),echo "Remove $(ext_class).[java,class]"; rm -f $(ext_class).java;)
	@rm -rf libs *.class percolation

run: tests
	@echo "****************************************************************"
	@echo -ne "Must be:\n"\
		"mean                    = 0.5929934999999997\n"\
		"stddev                  = 0.00876990421552567\n"\
		"95% confidence interval = 0.5912745987737567, 0.5947124012262428\n"\
		"Test results:\n"
	@java PercolationStats 200 10
	@echo "****************************************************************"
	@echo -ne "Must be:\n"\
		"mean                    = 0.592877\n"\
		"stddev                  = 0.009990523717073799\n"\
		"95% confidence interval = 0.5909188573514536, 0.5948351426485464\n"\
		"Test results:\n"
	@java PercolationStats 200 100
	@echo "****************************************************************"
	@echo -ne "Must be:\n"\
		"mean                    = 0.666925\n"\
		"stddev                  = 0.11776536521033558\n"\
		"95% confidence interval = 0.6646167988418774, 0.6692332011581226\n"\
		"Test results:\n"
	@java PercolationStats 2 10000
	@echo "****************************************************************"
	@echo -ne "Must be\n"\
		"mean                    = 0.6669475\n"\
		"stddev                  = 0.11775205263262094\n"\
		"95% confidence interval = 0.666217665216461, 0.6676773347835391\n"\
		"Test results:\n"
	@java PercolationStats 2 100000
	@echo "****************************************************************"

zip:
	@zip ~/$(ZARCH) Percolation.java PercolationStats.java
