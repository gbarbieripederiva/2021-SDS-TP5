package ar.edu.itba.models.particle;

public class Particle {
    // props of particles
    private ParticleRadius particleRadius;
    private Vector position;
    private Vector velocity;
    
    // props of this system
    private double desiredSpeed;
    private double beta;
    // TODO:check if best idea. This would be used by the system to keep track of the target
    private int targetNumber = 0;

    // props to set before update
    private boolean isEscaping = false;
    private Vector target = new Vector(0,0);
    
    // constructors
    public Particle(Particle particle) {
        this(
            particle.getParticleRadius(),
            particle.getDesiredSpeed(),
            particle.getBeta(),
            new Vector(particle.getPosition()),
            new Vector(particle.getVelocity())
        );
    }

    public Particle(ParticleRadius radius,double desiredSpeed,double beta, Vector position, Vector velocity) {
        this.particleRadius = radius;
        this.desiredSpeed = desiredSpeed;
        this.beta = beta;
        this.position = position;
        this.velocity = velocity;
    }
    // TODO: maybe remove this
    public Particle(double radius, Vector position, Vector velocity) {
        this( new ParticleRadius(radius, radius, 1, radius), 0, 0, position, velocity );
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

    public double getDesiredSpeed() {
        return desiredSpeed;
    }

    public double getBeta(){
        return beta;
    }
    public int getTargetNumber(){
        return targetNumber;
    }
    public boolean getIsEscaping(){
        return isEscaping;
    }
    public Vector getTarget(){
        return target;
    }

    // setters
    public Particle setParticleRadius(ParticleRadius particleRadius) {
        this.particleRadius = particleRadius;
        return this;
    }
    public Particle setRadius(double radius) {
        this.particleRadius.setRadius(radius);
        return this;
    }

    public Particle setPosition(Vector position) {
        this.position = position;
        return this;
    }

    public Particle setVelocity(Vector velocity) {
        this.velocity = velocity;
        return this;
    }

    public Particle setDesiredSpeed(double desiredSpeed){
        this.desiredSpeed = desiredSpeed;
        return this;
    }

    public Particle setBeta(double beta) {
        this.beta = beta;
        return this;
    }

    public Particle setTargetNumber(int targetNumber){
        this.targetNumber = targetNumber;
        return this;
    }
    public Particle setIsEscaping(boolean isEscaping){
        this.isEscaping = isEscaping;
        return this;
    }
    public Particle setTarget(Vector target){
        this.target = target;
        return this;
    }
    

    // methods
    public Particle updatePosition(double deltaTime){
        position = position.add(velocity.scalarProduct(deltaTime));
        return this;
    }

    // before calling this target and isEscaping should be properly set
    public Particle update(double deltaTime){
        if(getIsEscaping()){
            this.velocity = getEscapeVelocity(getTarget());
            this.particleRadius.shrinkRadius();
        }else{
            this.particleRadius.updateRadius(deltaTime);
            this.velocity = getNormalVelocity(getTarget());
        }
        this.updatePosition(deltaTime);
        return this;
    }

    public Vector getNormalVelocity(Vector vector) {
        double scalar = getDesiredSpeed()*Math.pow(
            getParticleRadius().getCurrentAboveMinRadius() / getParticleRadius().getRangeOfRadius(),
            getBeta());
        return vector.scalarProduct(scalar);
    }

    public Vector getEscapeVelocity(Vector point){
        Vector direction = point.substract(position);
        double length = direction.getMagnitude();
        if(length != 0){
            direction = direction.scalarProduct(1/length);
        }else{
            // TODO: check what to when they are the same point
        }
        return direction.scalarProduct(getDesiredSpeed());
    }

    public boolean isTouching(Particle other){   
        return position.getDistanceSquaredTo(other.position) < Math.pow(getRadius() + other.getRadius(),2);
    }
    public boolean isTouching(Vector point){
        return position.getDistanceSquaredTo(point) < Math.pow(getRadius(),2);
    }
}
