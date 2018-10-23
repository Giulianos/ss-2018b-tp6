package ar.edu.itba.ss.Forces;

import ar.edu.itba.ss.Container.Container;
import ar.edu.itba.ss.Particles.Body;

public class Desired implements Force{

    private static Double t = 0.5;
    private static Double vDesired = 2.5;
    private static Container container;

    private Double x;
    private Double y;

    private Body b;

    public Desired(Body b, Container container) {
        this.b = b;
        this.container = container;
    }

    @Override
    public void evaluate() {
        // Relative distance
        Double rx = b.getPositionX() - calculateTargetX();
        Double ry = b.getPositionY() - calculateTargetY();

        Double rmod = Math.sqrt(rx*rx + ry*ry);

        // Calculate normal direction
        Double enX = rx/rmod;
        Double enY = ry/rmod;

        x = b.getMass()*(vDesired*enX-b.getVelocityX())/t;
        y = b.getMass()*(vDesired*enY-b.getVelocityY())/t;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public Double getModule() {
        return Math.sqrt(x*x + y*y);
    }

    private Double calculateTargetX(){
        Double efectiveDiameter = container.getDiameter() - 2*b.getRadius();
        Double y2 = container.getHeight()/2 - efectiveDiameter/2;
        Double y1 = container.getHeight()/2 + efectiveDiameter/2;

        if (b.getPositionY() < y2 || b.getPositionY() > y1) {
            return container.getWidth();
        } else {
            // Final target is 10% the width of the room
            return container.getWidth() + 0.1*container.getWidth();
        }
    }

    private Double calculateTargetY(){
        Double efectiveDiameter = container.getDiameter()- 2*b.getRadius();
        Double y2 = container.getHeight()/2 - efectiveDiameter/2;
        Double y1 = container.getHeight()/2 + efectiveDiameter/2;

        if(b.getPositionY() < y2) {
            return y2;
        } else if(b.getPositionY() > y1) {
            return y1;
        } else {
            return container.getHeight()/2;
        }
    }
}
