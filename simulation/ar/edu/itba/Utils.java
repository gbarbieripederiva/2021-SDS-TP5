package ar.edu.itba;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import ar.edu.itba.models.Wall;
import ar.edu.itba.models.particle.Particle;

public class Utils {
    public static void writeParticles(List<Particle> particles, Writer writer) throws IOException{
        for (Particle particle : particles) {
            writer.write( "" + 
                particle.getParticleRadius().getMinRadius() + " " +
                particle.getParticleRadius().getMaxRadius() + " " +
                particle.getParticleRadius().getTimeToReachMax() + " " +
                particle.getParticleRadius().getRadius() + " " +

                particle.getDesiredSpeed() + " " +
                particle.getBeta() + " " +

                particle.getPosition().getX() + " " + particle.getPosition().getY() + " " +
                particle.getVelocity().getX() + " " + particle.getVelocity().getY() + "\n"
            );
        }
    }
    public static void writeWalls(List<Wall> walls, Writer writer) throws IOException{
        for (Wall wall : walls) {
            writer.write( "" + 
                wall.getStartPos().getX() + " " + wall.getStartPos().getY() + " " +
                wall.getEndPos().getX() + " " + wall.getEndPos().getY() + "\n"
            );
        }
    }
}
