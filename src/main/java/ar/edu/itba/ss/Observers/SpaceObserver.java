package ar.edu.itba.ss.Observers;

import ar.edu.itba.ss.Container.Container;
import ar.edu.itba.ss.Particles.Body;

import java.io.IOException;
import java.util.Set;

public interface SpaceObserver {
    /**
     * Injects params into the observer,
     * so then they can be used by observe.
     * @param bodies
     * @param time
     */
    public void injectData(Set<Body> bodies, Double time);

    /**
     * Observes the space using the data.
     * @throws Exception
     */
    public void observe() throws Exception;

    /**
     * Returns whether the simulation must end or not
     *
     * @return true if the simulation must end, false otherwise
     */
    public Boolean simulationMustEnd();

    /**
     * Allows the observers to close any files if necessary
     */
    public void finalizeObserver() throws IOException;
}
