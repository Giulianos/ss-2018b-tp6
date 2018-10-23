package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Particles.Body;

public class Social extends ForceBetweenParticles {

    private static Double A = 2000.0;
    private static Double B = 0.08;


    public Social(Body b1, Body b2) {
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

        // Geometric variables
        Double epsilon = b1.getRadius() + b2.getRadius() - rmod;

        if(epsilon < 0){
            x = 0.0;
            y = 0.0;
            return;
        }

        Double fModule = A * Math.exp(-epsilon/B);

        x = fModule * enX;
        y = fModule * enY;
    }
}
