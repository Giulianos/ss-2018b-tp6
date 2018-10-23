package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Vector;

public interface Force {
    void evaluate();
    Vector getForce();
}
