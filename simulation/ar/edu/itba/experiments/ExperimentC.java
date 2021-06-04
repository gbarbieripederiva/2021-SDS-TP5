package ar.edu.itba.experiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ar.edu.itba.CPMSystem;
import ar.edu.itba.models.Wall;
import ar.edu.itba.models.particle.Particle;
import ar.edu.itba.models.particle.ParticleRadius;
import ar.edu.itba.models.particle.Vector;

public class ExperimentC {
    private static final int TRIES_TO_DO = 3;
    private static final String OUTPUT_FILENAME = "./data/experiment/experimentC4.txt";
    private static final double OPEN_SPACE = 3.0;
    private static final double WALL_SIDE = 20;
    private static final double TARGET_WALL_GAP = 0.1;
    private static final double SECOND_TARGET_DISTANCE = 10;
    private static final int PARTICLES_TO_GENERATE = 380;
    private static final double MIN_RADIUS = 0.15 ;
    private static final double MAX_RADIUS = 0.32;
    private static final double TIME_TO_REACH_MAX_RADIUS = 0.5;
    private static final double SPEED_RADIUS_RELATION = 0.9;
    private static final double DESIRED_SPEED = 2;

    private static final double TIME_STEP = 0.01;

    // TODO:check
    private static final long SEED = System.currentTimeMillis();
    private static final Random RAND_GEN = new Random(SEED);

    public static void main(String[] args) throws IOException{
        // Open file for writing
        File outputFile = new File(Paths.get(OUTPUT_FILENAME).toAbsolutePath().toString());
        if(!outputFile.getParentFile().exists()){
            if(!outputFile.getParentFile().mkdirs()){
                java.lang.System.err.println("Output's folder does not exist and could not be created");
                java.lang.System.exit(1);
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(OUTPUT_FILENAME).toAbsolutePath().toString(), false));   

        writer.write("" + SEED + "-" + TIME_STEP);
        
        for (int tries = 0; tries < TRIES_TO_DO; tries++) {
            writer.write("\n");
            // Generate system and execute simulation
            CPMSystem system = generateSystem();
            long i = 0;
            int pqty = system.particles.size();
            int previousQty = pqty;
            for(; system.particles.size() > 0; i++){
                int currPqty = system.particles.size();
                if(previousQty - currPqty > 0){
                    writer.write("" + (system.deltaTime * i) + "-" + (pqty-currPqty) + " ");
                    previousQty = currPqty;
                }
                system.simulateStep();
            }
            System.out.println("tries:"+tries);
        }
        // close files
        writer.close();
    }

    private static CPMSystem generateSystem(){
        // Add the walls in a counterclockwise way so isLeft stays false
        List<Wall> walls = new ArrayList<Wall>();
        walls.add(new Wall(new Vector(0,0), new Vector(0,WALL_SIDE)));
        walls.add(new Wall(new Vector(0,WALL_SIDE), new Vector(WALL_SIDE,WALL_SIDE)));
        walls.add(new Wall(new Vector(WALL_SIDE,WALL_SIDE), new Vector(WALL_SIDE,0)));
        walls.add(new Wall(new Vector( (WALL_SIDE-OPEN_SPACE) / 2,0), new Vector(0,0)));
        walls.add(new Wall(new Vector(WALL_SIDE,0), new Vector((WALL_SIDE+OPEN_SPACE)/2,0)));
        
        // Add targets
        List<Wall> targets = new ArrayList<>();
        targets.add(new Wall(new Vector((WALL_SIDE+OPEN_SPACE)/2 - TARGET_WALL_GAP, 0),new Vector((WALL_SIDE-OPEN_SPACE)/2 + TARGET_WALL_GAP, 0)));
        // TODO: check this values for second target
        targets.add(new Wall(new Vector(WALL_SIDE,- SECOND_TARGET_DISTANCE), new Vector(0,- SECOND_TARGET_DISTANCE)));

        // TODO: check this whole particle generation
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < PARTICLES_TO_GENERATE; i++) {
            particles.add(new Particle(
                new ParticleRadius(MIN_RADIUS, MAX_RADIUS, TIME_TO_REACH_MAX_RADIUS, MAX_RADIUS), 
                DESIRED_SPEED, 
                SPEED_RADIUS_RELATION,
                new Vector(
                    getNumberBetween(MAX_RADIUS, WALL_SIDE - MAX_RADIUS),
                    getNumberBetween(MAX_RADIUS, WALL_SIDE - MAX_RADIUS)
                    ), 
                new Vector(0,0)
                ));
        }

        // GENERATE SYSTEM
        return new CPMSystem(TIME_STEP, walls, targets, particles);

    }

    private static double getNumberBetween(double smaller,double bigger){
        return smaller + RAND_GEN.nextDouble() * (bigger-smaller);
    }

}
