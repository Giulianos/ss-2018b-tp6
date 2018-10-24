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
    private static Integer N;
    private BufferedWriter writer;

    private Set<Body> bodies;
    private Double time;

    // Time variables
    private Double dt;
    private Double lastObservation;

    private Integer particlesLeft = null;

    public FlowObserver(String filename, int N, Double dt) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filename));
        this.N = N;
        this.dt = dt;
        this.lastObservation = null;
        this.particlesLeft = N;
    }

    @Override
    public void injectData(Set<Body> bodies,Double time) {
        this.bodies = bodies;
        this.time = time;
    }

    @Override
    public void observe() throws IOException {
       int aux = bodies.size();
       for(int i = aux; i < particlesLeft ; i++) {
           writer.write(time + "\n");
       }
       particlesLeft = aux;
    }

    @Override
    public Boolean simulationMustEnd() {
        if(bodies.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void finalizeObserver() throws IOException {
        writer.close();
    }
}
