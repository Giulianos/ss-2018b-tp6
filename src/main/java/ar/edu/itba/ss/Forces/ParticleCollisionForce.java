package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;

public class ParticleCollisionForce implements Force{
    private static Double kn = 1e5;
    private static Double gamma = 100.0;
    private static Double friction = 0.7;
    private Body b1;
    private Body b2;

    private static Double average = 0d;
    private static Double quantity = 0d;

    private Double x;
    private Double y;

    public ParticleCollisionForce(Body b1, Body b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    public static void setKn(Double kn) {
        ParticleCollisionForce.kn = kn;
    }

    public static void setFriction(Double friction) {
        ParticleCollisionForce.friction = friction;
    }

    @Override
    public void evaluate() {
        // Relative velocity
        Double vx = b1.getVelocityX() - b2.getVelocityX();
        Double vy = b1.getVelocityY() - b2.getVelocityY();

        // Distance distance
        Double rx = b2.getPositionX() - b1.getPositionX();
        Double ry = b2.getPositionY() - b1.getPositionY();
        Double rmod = Math.sqrt(rx*rx + ry*ry);

        // Calculate normal direction
        Double enX = rx/rmod;
        Double enY = ry/rmod;

        // Calculate tangential direction
        Double etX = -enY;
        Double etY = enX;

        Double relativeVelocity = vx*etX + vy*etY;

        // Geometric variables
        Double epsilon = b1.getRadius() + b2.getRadius() - rmod;

        if(epsilon < 0){
            x = 0.0;
            y = 0.0;
            return;
        }

        Double epsilonDerivated = b1.getVelocityX()*enX + b1.getVelocityY()*enY - b2.getVelocityX()*enX - b2.getVelocityY()*enY;

        Double fn = -kn * epsilon - gamma*epsilonDerivated;
        Double ft = -friction * Math.abs(fn) * Math.signum(relativeVelocity);

        x = fn*enX - ft*enY;
        y = fn*enY + ft*etX;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public Double getModule() {
        return Math.sqrt(x*x + y*y);
    }

}
