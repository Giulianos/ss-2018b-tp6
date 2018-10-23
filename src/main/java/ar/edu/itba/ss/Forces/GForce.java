package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;

public class GForce implements Force {

    private static double gravity = 9.8;
    private Body body;

    private Double x;
    private Double y;

    public GForce(Body body) {
        this.body = body;
    }

    public static void setGravity(double gravity) {
        GForce.gravity = gravity;
    }

    @Override
    public void evaluate() {
        x = 0.0;
        y = -1*gravity*body.getMass();
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
