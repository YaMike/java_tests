SRC=Brute.java Fast.java Point.java PointPlotter.java
OBJS=$(subst .java,.class,$(SRC))
TARG=$(subst .class,,$(OBJS))
CC=javac
RUN=java
ZARCH=collinear.zip
TESTARCHNAME=collinear-testing
EXTCLASSES=StdIn StdOut StdRandom StdStats StdArrayIO In StdDraw

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
	@rm -rf collinear
	@$(foreach ext_class,$(EXTCLASSES),echo "Remove $(ext_class).[java,class]"; rm -f $(ext_class).java;)
	@rm -rf libs *.class

run:
	@echo "****************************************************************"
	@echo "Here a place for unit tests"
	@echo "****************************************************************"

zip:
	@echo "Switching off debug mode..."
	@sed -i 's/DEBUG=true/DEBUG=false/' $(SRC)
	@echo "Creating *.zip archive..."
	@zip ~/$(ZARCH) $(filter-out PointPlotter.java,$(SRC))
	@echo "~/$(ZARCH) has been created."
