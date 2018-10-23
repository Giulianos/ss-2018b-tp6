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
public class FlowObserver implements SpaceObserver {
    private BufferedWriter writer;
    private Double totalTime;

    private Set<Body> bodies;
    private Integer translatedParticles;
    private Double time;

    // Window
    private Integer currentTranslatedParticles = 0;
    private Double elapsedTime = 0.0;
    private static Integer particleWindow = 20;

    // Time variables
    private Double dt;
    private Double lastObservation;

    private Long progress = null;

    public FlowObserver(String filename, Double totalTime, Double dt) throws IOException {
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
        this.translatedParticles = translatedParticles;
        this.time = time;
    }

    @Override
    public void observe() throws IOException {
        currentTranslatedParticles += translatedParticles;
        elapsedTime += 0.00001;
        if(currentTranslatedParticles > particleWindow) {
            writer.write(time + "\t" + currentTranslatedParticles/elapsedTime+"\n");
            currentTranslatedParticles = 0;
            elapsedTime = 0.0;
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

    @Override
    public void finalizeObserver() throws IOException {
        writer.close();
    }
}
