package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Container.Container;
import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Vector;

public class Desired implements Force{

    private static Double t = 0.5;
    private static Double vDesired = 3.5;
    private static Container container;

    private Vector force;

    private Body b;

    public Desired(Body b, Container container) {
        this.b = b;
        this.container = container;
    }

    @Override
    public void evaluate() {
        // Relative distance
        Vector relativeDistance = calculateTarget().subtract(b.getPosition());

        // Calculate normal direction
        Vector en = relativeDistance.divideBy(relativeDistance.module());

        // Calculate force
        force = en.multiplyBy(vDesired).subtract(b.getVelocity()).multiplyBy(b.getMass()).divideBy(t);
    }

    public static void setvDesired(Double vDesired) {
        Desired.vDesired = vDesired;
    }

    @Override
    public Vector getForce() {
        return this.force;
    }


    private Vector calculateTarget(){
        Double efectiveDiameter = container.getDiameter() - 2*b.getRadius();
        Double y2 = container.getHeight()/2 - efectiveDiameter/2;
        Double y1 = container.getHeight()/2 + efectiveDiameter/2;
        Double x;
        Double y;

        if (b.getPosition().getY() < y2 || b.getPosition().getY() > y1) {
            x = container.getWidth();
        } else {
            // Final target is 10% the width of the room
            x = container.getWidth() + 0.1*container.getWidth();
        }

        if(b.getPosition().getY() < y2) {
            y = y2;
        } else if(b.getPosition().getY() > y1) {
            y = y1;
        } else {
            y = container.getHeight()/2;
        }

        return new Vector(x,y);
    }
}
