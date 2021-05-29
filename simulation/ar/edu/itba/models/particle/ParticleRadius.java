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

    // setters
    public void setMinRadius(double minRadius) {
        this.minRadius = minRadius;
    }

    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public void setTimeToReachMax(double timeToReachMax) {
        this.timeToReachMax = timeToReachMax;
    }

    public void setCurrentRadius(double currentRadius) {
        this.currentRadius = currentRadius;
    }

    public void setRadius(double setRadius) {
        this.setCurrentRadius(setRadius);
    }

    // methods
    public void shrinkRadius() {
        currentRadius = minRadius;
    }

    public void updateRadius(double deltaTime) {
        if (deltaTime == 0.0) {
            return;
        }
        if (timeToReachMax == 0.0) {
            currentRadius = maxRadius;
            return;
        }
        currentRadius += maxRadius / (timeToReachMax / deltaTime);
        if (currentRadius > maxRadius) {
            currentRadius = maxRadius;
        }
    }
}
