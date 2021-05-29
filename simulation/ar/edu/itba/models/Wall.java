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
    // isLeft is true if the wall extends to the left
    // and falls if it extends to the right
    protected boolean isLeft;

    // constructor
    public Wall(Wall wall) {
        startPos = wall.startPos;
        endPos = wall.endPos;
        isLeft = wall.isLeft;
    }

    public Wall(Vector startPos, Vector endPos, boolean isLeft) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.isLeft = isLeft;
        this.lenghtSquared = startPos.getDistanceSquaredTo(endPos);
    }

    // getters
    public Vector getStartPos() {
        return startPos;
    }

    public Vector getEndPos() {
        return endPos;
    }

    public boolean getIsLeft() {
        return isLeft;
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

    public void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    protected void updateLengthSquared() {
        this.lenghtSquared = this.startPos.getDistanceSquaredTo(this.endPos);
    }

    public boolean insideWall(Particle particle) {
        if (this.distLineToPointSquared(particle.getPosition()) > particle.getRadius()*particle.getRadius()) {
            return false;
        }
        return true;
    }

    public double distLineToPointSquared(Vector point) {
        // if length is zero then it is a point
        if (this.lenghtSquared == 0.0)
            return this.startPos.getDistanceSquaredTo(point);
        double scalar = Math.max(0, Math.min(1, point.substract(startPos).dotProduct(endPos.substract(startPos)) / lenghtSquared));
        return startPos.add(endPos.substract(startPos).scalarProduct(scalar)).getDistanceSquaredTo(point);
    }

    public double distLineToPoint(Vector point) {
        return Math.sqrt(this.distLineToPointSquared(point));
    }
}