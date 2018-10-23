package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Vector;

public class WallInteraction implements Force{ protected static Double kn = 1.2e5;
    protected static Double kt = 2*kn;

    private static Double A = 2000.0;
    private static Double B = 0.08;

    protected Body body;
    protected Body wall;

    protected Vector force;

    public WallInteraction(Body body, Body wall) {
        this.body = body;
        this.wall = wall;
    }

    @Override
    public void evaluate() {
        // Relative velocity
        Vector relativeVelocity = body.getVelocity();

        // Distance distance
        Vector relativeDistance = body.getPosition().subtract(wall.getPosition());

        // Calculate normal direction
        Vector en = relativeDistance.divideBy(relativeDistance.module());

        // Calculate tangential direction
        Vector et = new Vector(-en.getY(),en.getX());

        double vt = relativeVelocity.dot(et);

        // Geometric variables
        Double epsilon = body.getRadius()-relativeDistance.module();


        force = en.multiplyBy(A*Math.exp(epsilon/B) + kn*(epsilon > 0? epsilon: 0)).subtract(
                et.multiplyBy(vt*kt*(epsilon > 0? epsilon: 0)));

    }

    @Override
    public Vector getForce() {
        return force;
    }
}
