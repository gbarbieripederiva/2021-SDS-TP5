package ar.edu.itba.models.particle;

import java.util.Objects;

public class Vector {
    protected double x;
    protected double y;

    // constructors
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    // getters and setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // vector operations
    public Vector add(Vector vector) {
        return new Vector(this.getX() + vector.getX(), this.getY() + vector.getY());
    }

    public Vector substract(Vector vector) {
        return new Vector(this.getX() - vector.getX(), this.getY() - vector.getY());
    }

    public double dotProduct(Vector vector) {
        return this.getX() * vector.getX() + this.getY() * vector.getY();
    }

    public Vector scalarProduct(double scalar) {
        return new Vector(scalar * getX(), scalar * getY());
    }

    public double getDistanceSquaredTo(Vector vector) {
        return this.substract(vector).getMagnitudeSquared();
    }

    public double getMagnitudeSquared() {
        return this.dotProduct(this);
    }

    public double getDistanceTo(Vector vector) {
        return Math.sqrt(getDistanceSquaredTo(vector));
    }

    public double getMagnitude() {
        return Math.sqrt(getMagnitudeSquared());
    }

    // object methods
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vector position = (Vector) o;
        return Double.compare(position.x, x) == 0 && Double.compare(position.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
