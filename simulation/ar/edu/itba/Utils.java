package ar.edu.itba;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.models.particle.Particle;
import ar.edu.itba.models.particle.Vector;

public class Utils {
    public static List<Particle> generateParticles(double minRadius,double maxRadius){
        List <Particle> particles = new ArrayList<Particle>();
        for (int i = 0; i < 10; i++) {
            particles.add(new Particle(
                minRadius,maxRadius,maxRadius,
                new Vector(0,i),
                new Vector(0,0)
            ));
        }

        return particles;
    }
}
