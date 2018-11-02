package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Vector;

public class ParticlesInteraccion implements Force {

    protected static Double kn = 1.2e5;
    protected static Double kt = 2*kn;

    private static Double A = 2000.0;
    private static Double B = 0.08;

    protected Body b1;
    protected Body b2;

    protected Vector force;

    public ParticlesInteraccion(Body b1, Body b2) {
        this.b1 = b1;
        this.b2 = b2;
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


        force = en.multiplyBy(A*Math.exp(epsilon/B) + kn*(epsilon > 0? epsilon: 0)).add(
                et.multiplyBy(vt*kt*(epsilon > 0? epsilon: 0)));

        // Add pressure
        b1.addPressure(force.module()/(2* Math.PI * b1.getRadius()));

    }

    @Override
    public Vector getForce() {
        return force;
    }
}
