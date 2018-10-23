package ar.edu.itba.ss.Integrators;

import ar.edu.itba.ss.Forces.Force;
import ar.edu.itba.ss.Particles.Body;

public class Beeman implements Integrator {

    private static Double maxSpeed = 0.75;
    private static Double maxAcceleration = 24000.0;

    @Override
    public void calculate(Body b, Double dt, Force f) {

        f.evaluate();

        Double x = b.getPositionX();
        Double y = b.getPositionY();

        Double vx = b.getVelocityX();
        Double vy = b.getVelocityY();

        Double ax = b.getAccelerationX();
        Double ay = b.getAccelerationY();

        Double axP = b.getPreviousAccelerationX();
        Double ayP = b.getPreviousAccelerationY();

        Double axF = f.getX()/b.getMass();
        Double ayF = f.getY()/b.getMass();

        Double xF = x + vx*dt + (2.0/3.0)*ax*dt*dt - (1.0/6.0)*axP*dt*dt;
        Double yF = y + vy*dt + (2.0/3.0)*ay*dt*dt - (1.0/6.0)*ayP*dt*dt;

        Double vxF = vx + (1.0/3.0)*axF*dt + (5.0/6.0)*ax*dt - (1.0/6.0)*axP*dt;
        Double vyF = vy + (1.0/3.0)*ayF*dt + (5.0/6.0)*ay*dt - (1.0/6.0)*ayP*dt;

        Double speedMod = Math.sqrt(vxF*vxF + vxF*vxF);
        if(speedMod > maxSpeed) {
            vxF /= speedMod;
            vyF /= speedMod;
            vxF *= maxSpeed;
            vyF *= maxSpeed;
        }

        Double accelerationMod = Math.sqrt(axF*axF + axF*axF);
        if(accelerationMod > maxAcceleration) {
            axF /= accelerationMod;
            ayF /= accelerationMod;
            axF *= maxAcceleration;
            ayF *= maxAcceleration;
        }

        b.setPositionX(xF);
        b.setVelocityX(vxF);
        b.setAccelerationX(axF);

        b.setPositionY(yF);
        b.setVelocityY(vyF);
        b.setAccelerationY(ayF);
    }

    public String toString(){
        return "beeman";
    }
}