package ar.edu.itba.ss.Observers;

import ar.edu.itba.ss.Container.Container;
import ar.edu.itba.ss.Particles.Body;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by giulianoscaglioni on 15/10/18.
 */
public class EnergyObserver implements SpaceObserver {
    private BufferedWriter writer;
    private Double totalTime;

    private Set<Body> bodies;
    private Container container;
    private Double time;

    // Time variables
    private Double dt;
    private Double lastObservation = null;

    private Long progress = null;

    public EnergyObserver(String filename, Double totalTime, Double dt) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filename));
        this.totalTime = totalTime;
        this.dt = dt;
        this.lastObservation = null;
    }

    public void closeFile() throws IOException {
        writer.close();
    }

    @Override
    public void injectData(Set<Body> bodies, Container container, Double time, Integer translatedParticles) {
        this.bodies = bodies;
        this.container = container;
        this.time = time;
    }

    @Override
    public void observe() throws IOException {

        Double energy = 0.0;

        for (Body b : bodies) {
            if (!b.isFixed()) {
                energy += getEnergy(b);
            }
        }

        if(lastObservation == null || time-lastObservation > dt) {
            lastObservation = time;
            writer.write(time + "\t" + energy + "\n");
        }
    }

    @Override
    public Boolean simulationMustEnd() {
        if(progress == null || progress != Math.round((time/totalTime) * 100)) {
            progress = Math.round((time/totalTime) * 100);
            System.out.println("Progress: " + progress + "%");
        }
        if(time >= totalTime) {
            return true;
        }
        return false;
    }

    private Double getEnergy(Body b) {
        double speedMod = Math.sqrt(b.getVelocityX()*b.getVelocityX() + b.getVelocityY()*b.getVelocityY());
        return (1.0/2.0)*b.getMass()*Math.pow(speedMod, 2);
    }

    @Override
    public void finalizeObserver() throws IOException {
        writer.close();
    }
}
