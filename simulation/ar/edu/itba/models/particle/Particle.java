package ar.edu.itba.models.particle;

public class Particle {
    // props
    private final double minRadius;
    private final double maxRadius;
    private double radius;
    private Vector position;
    private Vector velocity;

    // constructors
    public Particle(Particle particle) {
        minRadius = particle.minRadius;
        maxRadius = particle.maxRadius;
        radius = particle.radius;
        position = new Vector(particle.getPosition());
        velocity = new Vector(particle.getVelocity());
    }

    public Particle(double minRadius, double maxRadius, double radius, Vector position, Vector velocity) {
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.radius = radius;
        this.position = position;
        this.velocity = velocity;
    }

    // getters
    public double getMinRadius() {
        return minRadius;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    public double getRadius() {
        return radius;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    // setters
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
}
