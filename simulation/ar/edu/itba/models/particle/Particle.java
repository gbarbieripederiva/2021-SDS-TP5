package ar.edu.itba.models.particle;

public class Particle{
    // props
    private final double minRadius;
    private final double maxRadius;
    private double radius;
    private Position position;
    private Velocity velocity;

    // constructors
    public Particle(Particle particle){
        minRadius = particle.minRadius;
        maxRadius = particle.maxRadius;
        radius = particle.radius;
        position = new Position(particle.position.getX(), particle.position.getY());
        velocity = new Velocity(particle.velocity.getVelocityX(), particle.velocity.getVelocityY());
    }
    public Particle(double minRadius,double maxRadius,double radius,Position position, Velocity velocity) {
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

    public Position getPosition() {
        return position;
    }

    public Velocity getVelocity() {
        return velocity;
    }


    // setters
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double velocityX, double velocityY){
        velocity.setVelocityX(velocityX);
        velocity.setVelocityY(velocityY);
    }
}
