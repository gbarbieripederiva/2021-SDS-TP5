package ar.edu.itba.models;

import ar.edu.itba.models.particle.Particle;
import ar.edu.itba.models.particle.Vector;

public class Wall {
    // constants
    public static final boolean LEFT_OF_LINE_IS_WALL = true;
    public static final boolean RIGHT_OF_LINE_IS_WALL = false;

    // props
    protected Vector startPos;
    protected Vector endPos;
    protected double lenghtSquared;

    // constructor
    public Wall(Wall wall) {
        startPos = wall.startPos;
        endPos = wall.endPos;
    }

    // start -> end should go in a counterclockwise fashion so that the "width" is to the right
    public Wall(Vector startPos, Vector endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.lenghtSquared = startPos.getDistanceSquaredTo(endPos);
    }

    // getters
    public Vector getStartPos() {
        return startPos;
    }

    public Vector getEndPos() {
        return endPos;
    }

    // setters
    public void setStartPos(Vector startPos) {
        this.startPos = startPos;
        updateLengthSquared();
    }

    public void setEndPos(Vector endPos) {
        this.endPos = endPos;
        updateLengthSquared();
    }

    public double getLength() {
        return Math.sqrt(this.lenghtSquared);
    }

    protected void updateLengthSquared() {
        this.lenghtSquared = this.startPos.getDistanceSquaredTo(this.endPos);
    }

    public boolean isTouching(Particle particle) {
        if (this.distLineToPointSquared(particle.getPosition()) > particle.getRadius()*particle.getRadius()) {
            return false;
        }
        return true;
    }

    
    public Vector nearestPointFromLineToPoint(Vector point){
        // if length is zero then it is a point
        if (this.lenghtSquared == 0.0)
            return this.startPos;
        // calculate the projection of point vector in the line
        // the 0 and 1 are so that the projection falls inside the segment
        double scalar = Math.max(0, Math.min(1, point.substract(startPos).dotProduct(endPos.substract(startPos)) / lenghtSquared));
        return startPos.add(endPos.substract(startPos).scalarProduct(scalar));
    }
    public double distLineToPointSquared(Vector point) {
        return nearestPointFromLineToPoint(point).getDistanceSquaredTo(point);
    }

    public double distLineToPoint(Vector point) {
        return Math.sqrt(this.distLineToPointSquared(point));
    }
}