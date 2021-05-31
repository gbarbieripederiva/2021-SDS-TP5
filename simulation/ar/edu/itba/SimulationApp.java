package ar.edu.itba;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class SimulationApp {
    public static final double TIME_STEP = 0.01;
    public static final int STEPS_TO_TAKE = 100;
    public static final String OUTPUT_FILENAME = "./data/output.txt"; 


    public static void main(String[] args) throws IOException{

        // TODO: Parse input file
        CPMSystem system = null;

        // Open file for writing
        File outputFile = new File(Paths.get(OUTPUT_FILENAME).toAbsolutePath().toString());
        if(!outputFile.getParentFile().exists()){
            if(!outputFile.getParentFile().mkdirs()){
                java.lang.System.err.println("Output's folder does not exist and could not be created");
                java.lang.System.exit(1);
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(OUTPUT_FILENAME).toAbsolutePath().toString(), false));

        // loop until condition is met
        for(double t = 0; continueLooping(t,system); t += TIME_STEP){
            
        }

        writer.close();
    }


    private static boolean continueLooping(double time, CPMSystem system) {
        return time < TIME_STEP * STEPS_TO_TAKE;
    }
}
