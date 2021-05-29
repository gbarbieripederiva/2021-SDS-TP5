package ar.edu.itba.models.particle;

public class Particle {
    // props
    private ParticleRadius particleRadius;
    private Vector position;
    private Vector velocity;

    // constructors
    public Particle(Particle particle) {
        particleRadius = new ParticleRadius(particle.particleRadius);
        position = new Vector(particle.getPosition());
        velocity = new Vector(particle.getVelocity());
    }

    public Particle(ParticleRadius radius, Vector position, Vector velocity) {
        this.particleRadius = radius;
        this.position = position;
        this.velocity = velocity;
    }

    public Particle(double radius, Vector position, Vector velocity) {
        this.particleRadius = new ParticleRadius(radius, radius, 1, radius);
        this.position = position;
        this.velocity = velocity;
    }

    // getters
    public double getRadius() {
        return particleRadius.getRadius();
    }
    public ParticleRadius getParticleRadius() {
        return particleRadius;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    // setters
    public void setParticleRadius(ParticleRadius particleRadius) {
        this.particleRadius = particleRadius;
    }
    public void setRadius(double radius) {
        this.particleRadius.setRadius(radius);
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    // methods
    public void updatePosition(double deltaTime){
        position = position.add(velocity.scalarProduct(deltaTime));
    }
}
