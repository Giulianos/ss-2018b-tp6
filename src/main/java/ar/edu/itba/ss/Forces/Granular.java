package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Vector;

public class Granular extends ForceBetweenParticles {

    public Granular(Body b1, Body b2) {
        super(b1, b2);
    }

    @Override
    public void evaluate() {
        // Relative velocity
        Vector relativeVelocity = b2.getVelocity().subtract(b1.getVelocity());

        // Distance distance
        Vector relativeDistance = b1.getPosition().subtract(b2.getPosition());

        // Calculate normal direction
        Vector en = relativeDistance.divideBy(relativeDistance.module());

        // Calculate tangential direction
        Vector et = new Vector(-en.getY(),en.getX());

        double vt = relativeVelocity.dot(et);

        // Geometric variables
        Double epsilon = b1.getRadius() + b2.getRadius()-relativeDistance.module();

        if(epsilon < 0){
            force = new Vector(0.0,0.0);
            return;
        }
        force = en.multiplyBy(epsilon * kn).add(et.multiplyBy(vt * epsilon * kt));
    }
}
