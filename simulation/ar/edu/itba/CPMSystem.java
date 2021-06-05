package ar.edu.itba;

import java.util.Iterator;
import java.util.List;

import ar.edu.itba.models.Wall;
import ar.edu.itba.models.particle.Particle;
import ar.edu.itba.models.particle.Vector;

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
        // No particle starts escaping for no reason
        Vector vec0 = new Vector(0,0);
        for (Particle p : particles) {
            p.setIsEscaping(false);
            p.setDirection(vec0);
        }
        for (int i = 0; i < particles.size(); i++) {
            Particle p1 = particles.get(i);
            for (int j = i+1; j < particles.size(); j++) {
                Particle p2 = particles.get(j);
                
                // The reason
                if (p1.isTouching(p2)) {
                    Vector p1top2Versor = p2.getPosition().substract(p1.getPosition()).getVersor();
                    p1.setIsEscaping(true)
                        .setDirection(p1.getDirection().add(p1top2Versor.scalarProduct(-1)));
                    p2.setIsEscaping(true)
                        .setDirection(p1.getDirection().add(p1top2Versor));
                } 
            }
            for (Wall w : this.walls) {
                if(w.isTouching(p1)){
                    Vector ptop1Versor = w.nearestPointFromLineToPoint(p1.getPosition());
                    ptop1Versor = p1.getPosition().substract(ptop1Versor).getVersor();
                    p1.setIsEscaping(true)
                        .setDirection(p1.getDirection().add(ptop1Versor));
                }
            }
        }
        /* 
            Set target of all particles with no target and update them
            this is done against my will, to generalize the system
            one should probably handle different the way in which
            one considers that the target was reached. Also, the random
            should be taken out and a nearestPoint should be used. 
            For this particular system, though, this works so we'll
            roll it
        */ 
        for (Iterator<Particle> it = particles.iterator(); it.hasNext();) {
            Particle p = it.next();
            boolean removed = false;
            Wall target = this.targets.get(p.getTargetNumber());
            if(target.getStartPos().getY() >= p.getPosition().getY()){
                if(this.targets.size() > p.getTargetNumber()+1){
                    p.setTargetNumber(p.getTargetNumber()+1);
                    target = this.targets.get(p.getTargetNumber());
                }else{
                    it.remove();
                    removed = true;
                }
            }
            if(!removed){
                if(!p.getIsEscaping()){
                    Vector pTarget = null;
                    if(p.getTargetNumber() == 0){
                        if(
                            p.getPosition().getX() > target.getEndPos().getX() + 0.2 * target.getLength() &&
                            p.getPosition().getX() < target.getEndPos().getX() + 0.8 * target.getLength()
                            ){
                            pTarget = target.nearestPointFromLineToPoint(p.getPosition());                            
                        }else{
                            pTarget = new Vector(
                                target.getEndPos().getX() 
                                    + 0.2*target.getLength() 
                                    + 0.6 * target.getLength() * Math.random(),
                                target.getEndPos().getY()
                            );
                        }
                    }else{
                        pTarget = target.nearestPointFromLineToPoint(p.getPosition());
                    }
                    p.setDirection(pTarget.substract(p.getPosition()));
                }
                p.update(deltaTime);
            }
        }
    }
}
