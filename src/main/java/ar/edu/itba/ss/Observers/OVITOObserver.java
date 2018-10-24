package ar.edu.itba.ss.Observers;

import ar.edu.itba.ss.Container.Container;
import ar.edu.itba.ss.Particles.Body;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class OVITOObserver implements SpaceObserver {
    private static int N;
    private BufferedWriter writer;
    private Set<Body> bodies;
    private Double time;

    // Time variables
    private Double dt;
    private Double lastObservation = null;

    private Integer progress = 0;

    public OVITOObserver(String filename, int N, Double FPS) throws IOException{
        this.writer = new BufferedWriter(new FileWriter(filename));
        this.N = N;
        this.dt = 1.0/FPS;
        this.lastObservation = null;
    }

    @Override
    public void injectData(Set<Body> bodies, Double time) {
        this.bodies = bodies;
        this.time = time;
    }

    @Override
    public void observe() throws IOException {
        if(lastObservation == null || time-lastObservation > dt) {
            lastObservation = time;
            writer.write(bodies.size() + "\n\n");
            for (Body b : bodies) {
                if (!b.isFixed()) {
                    writer.write(b + "\n");
                }
            }
            int aux = N-bodies.size();
            if(progress < aux){
                progress = aux;
                System.out.println(progress+" particles out of "+N);
            }
        }
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
