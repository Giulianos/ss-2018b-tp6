package ar.edu.itba.ss.Integrators;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Forces.Force;
public interface Integrator {
    public void calculate(Body b, Double dt, Force f);

    public String toString();
}
