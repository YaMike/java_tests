SRC=Board.java Solver.java PuzzleChecker.java
OBJS=$(subst .java,.class,$(SRC))
TARG=$(subst .class,,$(OBJS))
CC=javac
RUN=java
ZSRC=$(filter-out PuzzleChecker.java,$(SRC))
ZARCH=puzzle.zip
TESTARCHNAME=8puzzle-testing
EXTCLASSES=StdIn StdOut StdRandom StdStats StdArrayIO In StdDraw Queue MinPQ Stack

all: prepare_libs $(OBJS) prepare_tests

prepare_libs:
	@echo "Prepare libs..."
	@mkdir libs 2>/dev/null; cd libs; jar xf ../../stdlib.jar; jar xf ../../algs4.jar; cd ..;

prepare_tests:
	@echo "Prepare test files..."
	@rm -rf $(TESTARCHNAME); unzip -o $(TESTARCHNAME) > /dev/null

%.class:%.java
	@echo "Compiling \"$<\"..."
	@$(foreach ext_class,$(EXTCLASSES),cp libs/$(ext_class).java .;)
	@$(CC) -Xlint $<

.PHONY:

clean:
	@echo "Removing $(OBJS), $(ZARCH), $(TESTARCHNAME)..."
	@rm -f $(OBJS) $(ZARCH)
	@rm -rf 8puzzle
	@$(foreach ext_class,$(EXTCLASSES),echo "Remove $(ext_class).[java,class]"; rm -f $(ext_class).java;)
	@rm -rf libs *.class

run:
	@echo "****************************************************************"
	@echo "Here a place for unit tests"
	@echo "****************************************************************"

turn_debug:
	@echo "Switching on debug mode..."
	@sed -i 's/DEBUG=false/DEBUG=true/' $(SRC)

zip:
	@echo "Switching off debug mode..."
	@sed -i 's/DEBUG=true/DEBUG=false/' $(SRC)
	@echo "Creating *.zip archive..."
	@zip ~/$(ZARCH) $(ZSRC)
	@echo "~/$(ZARCH) has been created."
