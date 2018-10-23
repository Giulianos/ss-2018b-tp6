package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Vector;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class Social extends ForceBetweenParticles {

    private static Double A = 2000.0;
    private static Double B = 0.08;


    public Social(Body b1, Body b2) {
        super(b1, b2);
    }

    @Override
    public void evaluate() {

        // Distance distance
        Vector relativeDistance = b1.getPosition().subtract(b2.getPosition());

        // Calculate normal direction
        Vector en = relativeDistance.divideBy(relativeDistance.module());

        // Geometric variables
        Double epsilon = (b1.getRadius() + b2.getRadius())-relativeDistance.module();

        force = en.multiplyBy(A * Math.exp(epsilon/B));

    }
}
