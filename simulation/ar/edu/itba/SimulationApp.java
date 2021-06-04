package ar.edu.itba;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ar.edu.itba.models.Wall;
import ar.edu.itba.models.particle.Particle;
import ar.edu.itba.models.particle.ParticleRadius;
import ar.edu.itba.models.particle.Vector;

public class SimulationApp {
    public static final double TIME_STEP = 0.025;
    public static final String INPUT_FILENAME = "./data/system.txt";
    public static final String OUTPUT_FILENAME = "./data/output.txt";

    private static enum ReadingFileState{
        READING_WALLS,
        READING_TARGETS,
        READING_PARTICLES,
        IGNORE
    };

    public static void main(String[] args) throws IOException{

        // Open file
        File file = new File(INPUT_FILENAME);
        Scanner scanner = new Scanner(file);

        // Parse file
        List<Wall> walls = new ArrayList<>();
        List<Wall> targets = new ArrayList<>();
        List<Particle> particles = new ArrayList<>();
        ReadingFileState state = ReadingFileState.READING_WALLS;
        while(scanner.hasNextLine() && state != ReadingFileState.IGNORE){
            String line[] = scanner.nextLine().replaceAll("^\\s+","").replaceAll("\\s+$", "").split(" ");
            switch (state) {
                case READING_WALLS:
                    if(line == null || line.length < 4){
                        state = ReadingFileState.READING_TARGETS;
                    }else{
                        walls.add(new Wall(
                            new Vector(Double.parseDouble(line[0]),Double.parseDouble(line[1])), 
                            new Vector(Double.parseDouble(line[2]),Double.parseDouble(line[3]))
                        ));
                    }
                    
                    break;
                case READING_TARGETS:
                    if(line == null || line.length < 4){
                        state = ReadingFileState.READING_PARTICLES;
                    }else{
                        targets.add(new Wall(
                            new Vector(Double.parseDouble(line[0]),Double.parseDouble(line[1])), 
                            new Vector(Double.parseDouble(line[2]),Double.parseDouble(line[3]))
                        ));
                    }
                    break;
                case READING_PARTICLES:
                    if(line == null || line.length < 10){
                        state = ReadingFileState.IGNORE;
                    }else{
                        particles.add(new Particle(
                            new ParticleRadius(
                                Double.parseDouble(line[0]), Double.parseDouble(line[1]), 
                                Double.parseDouble(line[2]), Double.parseDouble(line[3])
                            ),
                            Double.parseDouble(line[4]), Double.parseDouble(line[5]),
                            new Vector(Double.parseDouble(line[6]),Double.parseDouble(line[7])), 
                            new Vector(Double.parseDouble(line[8]),Double.parseDouble(line[9]))
                        ));
                    }
                    break;
                case IGNORE:
                    break;
            }
        }
        // close file and init system
        scanner.close();
        CPMSystem system = new CPMSystem(TIME_STEP, walls, targets, particles);

        // Open file for writing
        File outputFile = new File(Paths.get(OUTPUT_FILENAME).toAbsolutePath().toString());
        if(!outputFile.getParentFile().exists()){
            if(!outputFile.getParentFile().mkdirs()){
                java.lang.System.err.println("Output's folder does not exist and could not be created");
                java.lang.System.exit(1);
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(OUTPUT_FILENAME).toAbsolutePath().toString(), false));

        // add starting information
        Utils.writeWalls(system.walls, writer);
        writer.write("\n");
        Utils.writeWalls(system.targets, writer);
        writer.write("\n");
        Utils.writeParticles(system.particles, writer);


        // loop until condition is met
        for(long i = 0; continueLooping(system.deltaTime * i,system); i++){
            if(i%100 == 0 && i != 0){
                writer.write("\n");
                Utils.writeParticles(system.particles, writer);
            }
            if(i%1000 == 0){
                System.out.println("Steps taken:" + i + ", time:" + system.deltaTime * i);
            }
            system.simulateStep();
        }

        writer.close();
    }


    private static boolean continueLooping(double time, CPMSystem system) {
        return TIME_STEP * 2000000 > time && system.particles.size() > 0;
    }
}
