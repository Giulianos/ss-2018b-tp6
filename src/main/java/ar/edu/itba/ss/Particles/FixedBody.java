package ar.edu.itba.ss.Particles;

import ar.edu.itba.ss.Vector;

public class FixedBody extends Body {

    public FixedBody(Vector position, Vector velocity, Double mass, Double radius) {
        super(position, velocity, mass, radius);
    }

    @Override
    public Boolean isFixed() {
        return true;
    }

    @Override
    public void update() {
    }
}
