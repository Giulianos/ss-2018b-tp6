package ar.edu.itba.ss.Particles;

import ar.edu.itba.ss.CellIndex.Locatable;

public class Body implements Locatable {
    private static Integer nextID = 0;

    // Position vectors
    private Double previousPositionX;
    private Double previousPositionY;
    private Double currentPositionX;
    private Double currentPositionY;
    private Double futurePositionX;
    private Double futurePositionY;

    // Velocity vectors
    private Double previousVelocityX;
    private Double previousVelocityY;
    private Double currentVelocityX;
    private Double currentVelocityY;
    private Double futureVelocityX;
    private Double futureVelocityY;

    // Acceleration vectors
    private Double previousAccelerationX;
    private Double previousAccelerationY;
    private Double currentAccelerationX;
    private Double currentAccelerationY;
    private Double futureAccelerationX;
    private Double futureAccelerationY;

    // State info
    private Double dtBetweenStates;
    private Boolean shouldResetMovement = false;

    // Properties
    private Double mass;
    private Double radius;
    private Integer id;
    private Double pressure;

    public Body(Double x, Double y, Double vx, Double vy, Double mass, Double radius) {
        this.currentPositionX = x;
        this.currentPositionY = y;
        this.previousPositionX = x;
        this.previousPositionY = y;

        this.currentVelocityX = vx;
        this.currentVelocityY = vy;
        this.previousPositionX = vx;
        this.previousPositionY = vy;

        this.currentAccelerationX = 0d;
        this.currentAccelerationY = 0d;
        this.previousAccelerationX = 0d;
        this.previousAccelerationY = 0d;

        this.mass = mass;
        this.radius = radius;
        this.id = nextID++;
        this.pressure = 0.0;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    // Getters and setters
    @Override
    public Double getPositionX() {
        return currentPositionX;
    }

    public Double getVelocityX() {
        return currentVelocityX;
    }

    public Double getAccelerationX() {
        return currentAccelerationX;
    }

    @Override
    public Double getPositionY() {
        return currentPositionY;
    }

    public Double getVelocityY() {
        return currentVelocityY;
    }

    public Double getAccelerationY() {
        return currentAccelerationY;
    }


    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public Double getPreviousPositionX() {
        return previousPositionX;
    }

    public Double getPreviousVelocityX() {
        return previousVelocityX;
    }

    public Double getPreviousAccelerationX() {
        return previousAccelerationX;
    }

    public Double getPreviousPositionY() {
        return previousPositionY;
    }

    public Double getPreviousVelocityY() {
        return previousVelocityY;
    }

    public Double getPreviousAccelerationY() {
        return previousAccelerationY;
    }

    public Double getFuturePositionX() {
        return futurePositionX;
    }

    public Double getFutureVelocityX() {
        return futureVelocityX;
    }

    public Double getFutureAccelerationX() {
        return futureAccelerationX;
    }

    public Double getFuturePositionY() {
        return futurePositionY;
    }

    public Double getFutureVelocityY() {
        return futureVelocityY;
    }

    public Double getFutureAccelerationY() {
        return futureAccelerationY;
    }

    public Double getDtBetweenStates() {
        return dtBetweenStates;
    }

    public void setPositionX(Double positionX) { this.futurePositionX = positionX; }

    public void setVelocityX(Double velocityX) { this.futureVelocityX = velocityX; }

    public void setAccelerationX(Double accelerationX) {
        this.futureAccelerationX = accelerationX;
    }

    public void setPositionY(Double positionY) { this.futurePositionY = positionY; }

    public void setVelocityY(Double velocityY) { this.futureVelocityY = velocityY; }

    public void setAccelerationY(Double accelerationY) {
        this.futureAccelerationY = accelerationY;
    }

    public void setDtBetweenStates(Double dtBetweenStates) {
        this.dtBetweenStates = dtBetweenStates;
    }

    // Other methods
    public void update() {
        if(this.futurePositionX == null || this.futureVelocityX == null || this.futureAccelerationX == null ||
                this.futurePositionY == null || this.futureVelocityY == null || this.futureAccelerationY == null) {
            throw new IllegalStateException("Updating body without future state!");
        }

        // Update position
        this.previousPositionX = this.currentPositionX;
        this.currentPositionX = this.futurePositionX;
        this.futurePositionX = null;
        this.previousPositionY = this.currentPositionY;
        this.currentPositionY = this.futurePositionY;
        this.futurePositionY = null;

        if(shouldResetMovement) {
            // Update velocity
            this.previousVelocityX = 0d;
            this.currentVelocityX = 0d;
            this.futureVelocityX = null;
            this.previousVelocityY = 0d;
            this.currentVelocityY = 0d;
            this.futureVelocityY = null;

            // Update acceleration
            this.previousAccelerationX = 0d;
            this.currentAccelerationX = 0d;
            this.futureAccelerationX = null;
            this.previousAccelerationY = 0d;
            this.currentAccelerationY = 0d;
            this.futureAccelerationY = null;

            shouldResetMovement = false;

            return;
        }

        // Update velocity
        this.previousVelocityX = this.currentVelocityX;
        this.currentVelocityX = this.futureVelocityX;
        this.futureVelocityX = null;
        this.previousVelocityY = this.currentVelocityY;
        this.currentVelocityY = this.futureVelocityY;
        this.futureVelocityY = null;

        // Update acceleration
        this.previousAccelerationX = this.currentAccelerationX;
        this.currentAccelerationX = this.futureAccelerationX;
        this.futureAccelerationX = null;
        this.previousAccelerationY = this.currentAccelerationY;
        this.currentAccelerationY = this.futureAccelerationY;
        this.futureAccelerationY = null;
    }

    public void shouldResetMovement() {
        this.shouldResetMovement = true;
    }

    public boolean touches(Body b){
        if(equals(b)){
            return false;
        }
        Double distanceX = currentPositionX-b.currentPositionX;
        Double distanceY = currentPositionY-b.currentPositionY;
        double minDistance = radius + b.radius;
        return Math.sqrt(distanceX*distanceX + distanceY*distanceY) <= minDistance;
    }

    public String toString(){
        return currentPositionX +"\t"+currentPositionY + "\t" + this.getRadius();
    }

    public Boolean isFixed() {
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Body body = (Body) o;

        return id != null ? id.equals(body.id) : body.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
