package ar.edu.itba.models.particle;

public class ParticleRadius {
    private double minRadius;
    private double maxRadius;
    private double timeToReachMax;
    private double currentRadius;

    public ParticleRadius(double minRadius, double maxRadius, double timeToReachMax, double currentRadius) {
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.timeToReachMax = timeToReachMax;
        this.currentRadius = currentRadius;
    }

    public ParticleRadius(ParticleRadius radius) {
        this.minRadius = radius.minRadius;
        this.maxRadius = radius.maxRadius;
        this.timeToReachMax = radius.timeToReachMax;
        this.currentRadius = radius.currentRadius;
    }

    // getters
    public double getMinRadius() {
        return minRadius;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    public double getTimeToReachMax() {
        return timeToReachMax;
    }

    public double getCurrentRadius() {
        return currentRadius;
    }

    public double getRadius() {
        return getCurrentRadius();
    }

    public double getRangeOfRadius(){
        return getMaxRadius() - getMinRadius();
    }

    public double getCurrentAboveMinRadius(){
        return getRadius() - getMinRadius();
    }

    // setters
    public ParticleRadius setMinRadius(double minRadius) {
        this.minRadius = minRadius;
        return this;
    }

    public ParticleRadius setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
        return this;
    }

    public ParticleRadius setTimeToReachMax(double timeToReachMax) {
        this.timeToReachMax = timeToReachMax;
        return this;
    }

    public ParticleRadius setCurrentRadius(double currentRadius) {
        this.currentRadius = currentRadius;
        return this;
    }

    public ParticleRadius setRadius(double setRadius) {
        this.setCurrentRadius(setRadius);
        return this;
    }

    // methods
    public ParticleRadius shrinkRadius() {
        currentRadius = minRadius;
        return this;
    }

    public ParticleRadius updateRadius(double deltaTime) {
        if (deltaTime == 0.0) {
            return this;
        }
        if (timeToReachMax == 0.0) {
            currentRadius = maxRadius;
            return this;
        }
        currentRadius += maxRadius / (timeToReachMax / deltaTime);
        if (currentRadius > maxRadius) {
            currentRadius = maxRadius;
        }
        return this;
    }
}
