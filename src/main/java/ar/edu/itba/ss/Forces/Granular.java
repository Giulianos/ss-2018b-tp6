package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;

public class Granular extends ForceBetweenParticles {

    public Granular(Body b1, Body b2) {
        super(b1, b2);
    }

    @Override
    public void evaluate() {
        // Relative velocity
        Double vx = b1.getVelocityX() - b2.getVelocityX();
        Double vy = b1.getVelocityY() - b2.getVelocityY();

        // Distance distance
        Double rx = b1.getPositionX() - b2.getPositionX();
        Double ry = b1.getPositionY() - b2.getPositionY();
        Double rmod = Math.sqrt(rx*rx + ry*ry);

        // Calculate normal direction
        Double enX = rx/rmod;
        Double enY = ry/rmod;

        // Calculate tangential direction
        Double etX = -enY;
        Double etY = enX;

        Double relativeVelocity = vx*etX + vy*etY;

        // Geometric variables
        Double epsilon = -b1.getRadius() - b2.getRadius() + rmod;

        if(epsilon < 0){
            x = 0.0;
            y = 0.0;
            return;
        }

        x = (-epsilon*kn*enX + relativeVelocity*epsilon*kt*etX)*epsilon;
        y = (-epsilon*kn*enY + relativeVelocity*epsilon*kt*etY)*epsilon;
    }
}
