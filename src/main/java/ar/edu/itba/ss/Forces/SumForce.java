package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Vector;

import java.util.Set;

public class SumForce implements Force {
    private Set<Force> forces;

    private Vector force;

    public SumForce(Set<Force> forces) {
        this.forces = forces;
    }

    @Override
    public void evaluate() {
        force = new Vector(0.0,0.0);
        for(Force f : forces) {
            f.evaluate();
            force = force.add(f.getForce());
        }
    }

    @Override
    public Vector getForce() {
        return this.force;
    }

}
