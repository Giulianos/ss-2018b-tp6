package ar.edu.itba.ss;

import ar.edu.itba.ss.CellIndex.Grid;
import ar.edu.itba.ss.Containers.Box;
import ar.edu.itba.ss.Containers.Container;
import ar.edu.itba.ss.Forces.*;
import ar.edu.itba.ss.Integrators.Beeman;
import ar.edu.itba.ss.Integrators.Integrator;
import ar.edu.itba.ss.Observers.SpaceObserver;
import ar.edu.itba.ss.Particles.Body;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Space {

    // Space domain
    private static Container container;
    private Set<Body> bodies;
    private Double elapsedTime = 0.0;

    // Utilities
    private Integrator integrator;
    private List<SpaceObserver> observers = new ArrayList<>();
    private Grid<Body> grid;

    // Space properties
    private Integer translatedParticles = 0;

    // Concurrency
     ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Space(Double width, Double height, Double diameter, Integer particleQuantity, Double friction, Double kn) {

        // Create container
        this.container = new Box(diameter, height, width);

        // Create bodies set
        this.bodies = new HashSet<>();

        // Insert opening edge bodies into set
        this.bodies.addAll(this.container.getOpeningBodies());

        // Insert bodies
        insertBodies(particleQuantity);

        // Create Grid
        this.grid = new Grid<>(0.03);

        // Add particles to grid
        this.grid.injectElements(bodies);

        // Create integrator
        this.integrator = new Beeman();

        // Update collision parameters
        ParticleCollisionForce.setFriction(friction);
        ParticleCollisionForce.setKn(kn);

    }

    public void attachObserver(SpaceObserver observer) {
        observers.add(observer);

        // Inject initial data to observer
        observer.injectData(bodies, container, elapsedTime, translatedParticles);
    }

    public void simulationStep(Double dt) throws InterruptedException {
        Long startTime = System.currentTimeMillis();

        translatedParticles = 0;

        List<Callable<Object>> tasks = new ArrayList<>(bodies.size());

        // Update grid before simulating
        this.grid.updateGrid();

        for(Body body : bodies) {
            tasks.add((() -> {
                singularSimulationStep(body, dt);
                return null;
            }));
        }

        executorService.invokeAll(tasks);

        // Update bodies state
        bodies.forEach(Body::update);

        // Update time
        this.elapsedTime += dt;

        for(SpaceObserver observer : observers) {
            observer.injectData(bodies, container, elapsedTime, translatedParticles);
        }

        // System.out.println("Time taken for 1 step: " + (System.currentTimeMillis()-startTime) + "ms");
    }

    private void singularSimulationStep(Body body, Double dt) {
        // Set dt on particle
        body.setDtBetweenStates(dt);//

        // If the body is fixed, just skip it
        if(body.isFixed()) {
            return;
        }

        Set<Force> appliedForces = new HashSet<>();
        // Check collisions against neighbours
        for(Body neighbour : getNeighbours(body)) {
            // Check if touching
            if(body.touches(neighbour)) {
                appliedForces.add(new ParticleCollisionForce(body, neighbour));
            }
        }

        // Check collisions against walls
        Set<Body> wallCollisions = container.getWallCollision(body);
        if(wallCollisions.size() > 0) {
            wallCollisions.parallelStream()
                    .map(wallBody -> new ParticleCollisionForce(body, wallBody))
                    .forEach(appliedForces::add);
        }

        // Add gravity force
        appliedForces.add(new GForce(body));

        // Sum forces
        Force appliedForce = new SumForce(appliedForces);

        // Integrate
        integrator.calculate(body, dt, appliedForce);

        // Update periodic
        updatePeriodic(body);

        body.setPressure(appliedForce.getModule()/(2*body.getRadius()*Math.PI));
    }

    private Set<Body> getNeighbours(Body body) {
        return grid.getNeighbours(body);
        // Set<Body> neighbours = new HashSet<>(bodies);
        // neighbours.remove(body);
        // return neighbours;
    }

    private void insertBodies(Integer quantity) {
        // TODO: parametrize rand ranges
        Logger.log("Trying to add "+ quantity +" particles!");
        Random rand = new Random();
        Integer currentQuantity = 0;
        while(currentQuantity < quantity) {
            Body newBody = new Body(
  //                  0.3,
                    rand.nextDouble() * (container.getWidth(0.0) - 2 * 0.015) + 0.015,
                    rand.nextDouble() * (container.getHeight() - 2 * 0.015) + 0.015,
                0.0,
                0.0,
                0.01,
                (rand.nextDouble()*0.01+0.02)/2.0
            );
            // Check that newBody doesn't touch any other body
            if(bodies.stream().noneMatch(newBody::touches)) {
                bodies.add(newBody);
                Logger.log("Particle "+currentQuantity+"/"+quantity+" added!");
                currentQuantity++;
            }
        }
    }

    private void updatePeriodic(Body b) {
        if(b.isFixed()) {
            return;
        }
        if(b.getPositionY() < container.getHeight()*(-0.1)) {
            b.setPositionX(new Random().nextDouble() * (container.getWidth(0.0) - 2 * 0.015) + 0.015);
            b.setPositionY(new Random().nextDouble() * (container.getHeight()*0.2 - 2 * 0.015) + 0.015 + container.getHeight()*0.8);
            b.shouldResetMovement();
            translatedParticles++;
            // Logger.log("Updated body position!");
        }
    }

    public void finishExectutor() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
