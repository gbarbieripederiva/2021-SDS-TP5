JAVA_SOURCES = $(shell find simulation -name *.java)
OUT_FOLDER = out

compile:$(JAVA_SOURCES)
	mkdir -p $(OUT_FOLDER)
	javac -d $(OUT_FOLDER) $(JAVA_SOURCES)

run:compile
	java -cp $(OUT_FOLDER) ar.edu.itba.SimulationApp

generateFiles:compile
	java -cp $(OUT_FOLDER) ar.edu.itba.GenerateFiles


__debug_run_all:run visualizer

visualizer:
	bash -c "cd visualization;source .env/bin/activate; python visualizer.py"