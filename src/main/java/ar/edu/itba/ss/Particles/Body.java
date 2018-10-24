package ar.edu.itba.ss.Particles;
import ar.edu.itba.ss.Vector;

public class Body{
    private static Integer nextID = 0;

    // Position vectors
    private Vector previousPosition;
    private Vector currentPosition;
    private Vector futurePosition;

    // Velocity vectors
    private Vector previousVelocity;
    private Vector currentVelocity;
    private Vector futureVelocity;

    // Acceleration vectors
    private Vector previousAcceleration;
    private Vector currentAcceleration;
    private Vector futureAcceleration;

    // State info
    private Double dtBetweenStates;

    // Properties
    private Double mass;
    private Double radius;
    private Integer id;

    public Body(Vector position, Vector velocity, Double mass, Double radius) {

        this.currentPosition = position;
        this.previousPosition = position;

        this.currentVelocity = velocity;
        this.previousVelocity = velocity;

        this.currentAcceleration = new Vector(0d,0d);
        this.previousAcceleration = new Vector(0d,0d);

        this.mass = mass;
        this.radius = radius;
        this.id = nextID++;
    }


    // Getters and setters
    public Vector getPosition() {
        return currentPosition;
    }

    public Vector getVelocity() {
        return currentVelocity;
    }

    public Vector getAcceleration() {
        return currentAcceleration;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }


    public Vector getPreviousAcceleration() {
        return previousAcceleration;
    }

    public void setDtBetweenStates(Double dtBetweenStates) {
        this.dtBetweenStates = dtBetweenStates;
    }

    // Other methods
    public void update() {
        if(this.futurePosition == null || this.futureVelocity == null || this.futureAcceleration == null) {
            throw new IllegalStateException("Updating body without future state!");
        }

        // Update position
        this.previousPosition = this.currentPosition;
        this.currentPosition = this.futurePosition;
        this.futurePosition = null;

        // Update velocity
        this.previousVelocity = this.currentVelocity;
        this.currentVelocity = this.futureVelocity;
        this.futureVelocity = null;

        // Update acceleration
        this.previousAcceleration = this.currentAcceleration;
        this.currentAcceleration = this.futureAcceleration;
        this.futureAcceleration = null;
    }

    public boolean touches(Body b){
        if(equals(b)){
            return false;
        }
        double minDistance = radius + b.radius;
        return this.currentPosition.distanceTo(b.currentPosition) <= minDistance;
    }

    public String toString(){
        return currentPosition.getX() +"\t"+currentPosition.getY() + "\t" + this.getRadius();
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

    public Double getPositionX() {
        return this.getPosition().getX();
    }

    public Double getPositionY() {
        return this.getPosition().getY();
    }

    public void setFuturePosition(Vector position) {
        this.futurePosition = position;
    }

    public void setFutureVelocity(Vector velocity) {
        this.futureVelocity = velocity;
    }

    public void setFutureAcceleration(Vector acceleration) {
        this.futureAcceleration = acceleration;
    }

    public Integer getId() {
        return id;
    }
}
