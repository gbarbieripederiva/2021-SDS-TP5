package ar.edu.itba;

import java.util.Iterator;
import java.util.List;

import ar.edu.itba.models.Wall;
import ar.edu.itba.models.particle.Particle;

public class CPMSystem {
    public double deltaTime;
    public List<Wall> walls;
    public List<Wall> targets;
    public List<Particle> particles;

    public CPMSystem(double deltaTime, List<Wall> walls, List<Wall> targets,List<Particle> particles){
        this.deltaTime = deltaTime;
        this.walls = walls;
        this.targets = targets;
        this.particles = particles;
    }

    public void simulateStep(){
        /*
            TODO: check the following things

            1)  should the wall calculate if its touching? could be optimized if you
                get the nearest point and check yourself as you could use that point again
            2)  What happens if a particle is touching two walls? probably shouldnt happen
            3)  What happens if a particle is touching two particles simultaneously?
            4)  The interaction between particles maybe optimized wth cellIndexMethod (Agus help!!)
        */
        // No particle starts escaping for no reason
        for (Particle p : particles) {
            p.setIsEscaping(false);
        }
        for (int i = 0; i < particles.size(); i++) {
            Particle p1 = particles.get(i);
            for (int j = i+1; j < particles.size(); j++) {
                Particle p2 = particles.get(j);
                
                // The reason
                if (p1.isTouching(p2)) {
                    p1.setIsEscaping(true).setTarget(p2.getPosition());
                    p2.setIsEscaping(true).setTarget(p1.getPosition());
                } 
            }
            for (Wall w : this.walls) {
                if(w.isTouching(p1)){
                    p1.setIsEscaping(true).setTarget(w.nearestPointFromLineToPoint(p1.getPosition()));
                }
            }
        }
        // Set target of all particles with no target and update them
        for (Iterator<Particle> it = particles.iterator(); it.hasNext();) {
            Particle p = it.next();
            boolean removed = false;
            if(!p.getIsEscaping()){
                // TODO: look into particle having a reference to an iterator of targets
                Wall target = this.targets.get(p.getTargetNumber());
                if(target.isTouching(p)){
                    if(this.targets.size() < p.getTargetNumber()){
                        p.setTargetNumber(p.getTargetNumber()+1);
                    }else{
                        it.remove();
                        removed = true;
                    }
                }
            }
            if(!removed){
                p.update(deltaTime);
            }
        }
    }
}
