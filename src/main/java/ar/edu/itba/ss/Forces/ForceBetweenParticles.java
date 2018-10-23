package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;

public abstract class ForceBetweenParticles implements Force {

    protected static Double kn = 1.2e5;
    protected static Double kt = 2*kn;

    protected Body b1;
    protected Body b2;

    protected Double x;
    protected Double y;

    public ForceBetweenParticles(Body b1, Body b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    @Override
    public abstract void evaluate();

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
