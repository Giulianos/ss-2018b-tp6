package ar.edu.itba.ss.Integrators;

import ar.edu.itba.ss.Forces.Force;
import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Vector;

public class Beeman implements Integrator {

    private static Double maxSpeed = 0.75;
    private static Double maxAcceleration = 24000.0;

    @Override
    public void calculate(Body b, Double dt, Force f) {

        f.evaluate();

        Vector currentPosition = b.getPosition();

        Vector currentVelocity = b.getVelocity();

        Vector currentAcceleration = b.getAcceleration();

        Vector previusAcceleration = b.getPreviousAcceleration();

        Vector futureAcceleration = f.getForce().divideBy(b.getMass());

        Vector futurePosition = currentPosition.add(currentVelocity.multiplyBy(dt)).add(currentAcceleration.multiplyBy(dt*dt*(2.0/3.0))).subtract(previusAcceleration.multiplyBy(dt*dt*(1.0/6.0)));

       Vector futureVelocity = currentVelocity.add(futureAcceleration.multiplyBy(dt*(1.0/3.0))).add(currentAcceleration.multiplyBy(dt*(5.0/6.0))).subtract(previusAcceleration.multiplyBy(dt*(1.0/6.0)));

       b.setFuturePosition(futurePosition);
       b.setFutureVelocity(futureVelocity);
       b.setFutureAcceleration(futureAcceleration);
    }

    public String toString(){
        return "beeman";
    }
}