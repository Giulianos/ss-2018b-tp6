package ar.edu.itba.ss;

import ar.edu.itba.ss.Observers.SpaceObserver;

import java.util.ArrayList;
import java.util.List;

public class Simulator {
    private Space space;
    private List<SpaceObserver> observers = new ArrayList<>();
    private SpaceObserver principalObserver;
    private Double timeStep;

    public Simulator(Space space, SpaceObserver observer, Double timeStep) {
        this.space = space;
        this.principalObserver = observer;
        this.timeStep = timeStep;
    }

    public void attachObserver(SpaceObserver observer) {
        observers.add(observer);
    }

    public void simulate() throws Exception {
        Long timeStart = System.currentTimeMillis();
        while (!principalObserver.simulationMustEnd()) {
            space.simulationStep(timeStep);
            principalObserver.observe();
            for (SpaceObserver observer : observers) {
                observer.observe();
            }
        }
        Long timeTaken = System.currentTimeMillis() - timeStart;
        Logger.log("Time taken: " + timeTaken + "ms", Logger.LogType.TIMING);
        principalObserver.finalizeObserver();
        for(SpaceObserver observer : observers) {
            observer.finalizeObserver();
        }
        space.finishExectutor();
    }
}
