package ar.edu.itba.ss;
import ar.edu.itba.ss.Container.Box;
import ar.edu.itba.ss.Container.Container;
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
import java.util.stream.Collectors;

public class Space {

    // Space domain
    private static Container container;
    private Set<Body> bodies;
    private Double elapsedTime = 0.0;

    // Utilities
    private Integrator integrator;
    private List<SpaceObserver> observers = new ArrayList<>();

    // Concurrency
     ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Space(Double width, Double height, Double diameter, Integer particleQuantity) {

        // Create container
        this.container = new Box(diameter, height, width);

        // Create bodies set
        this.bodies = new HashSet<>();

        // Insert bodies
        insertBodies(particleQuantity);

        // Create integrator
        this.integrator = new Beeman();

    }

    public void attachObserver(SpaceObserver observer) {
        observers.add(observer);

        // Inject initial data to observer
        observer.injectData(bodies, elapsedTime);
    }

    public void simulationStep(Double dt) throws InterruptedException {
        Long startTime = System.currentTimeMillis();

        bodies = bodies.stream().filter(body ->  body.getPositionX() < container.getWidth()).collect(Collectors.toSet());

        List<Callable<Object>> tasks = new ArrayList<>(bodies.size());

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
            observer.injectData(bodies, elapsedTime);
        }
    }

    private void singularSimulationStep(Body body, Double dt) {

        // Set dt on particle
        body.setDtBetweenStates(dt);//

        // If the body is fixed, just skip it
        if(body.isFixed()) {
            return;
        }

        Set<Force> appliedForces = new HashSet<>();

        // Add desired force
      appliedForces.add(new Desired(body,container));
        // Check collisions against neighbours
        for(Body neighbour : bodies) {
            if(!neighbour.equals(body)){
                appliedForces.add(new ParticlesInteraccion(body,neighbour));
            }
        }

        // Check collisions against walls
        Set<Body> wallCollisions = container.getWallCollision(body);
        if(wallCollisions.size() > 0) {
            wallCollisions.parallelStream()
                    .map(wallBody -> new WallInteraction(body, wallBody))
                    .forEach(appliedForces::add);
        }

        // Sum forces
        Force appliedForce = new SumForce(appliedForces);

        // Integrate
        integrator.calculate(body, dt, appliedForce);
    }

    private void insertBodies(Integer quantity) {
        // TODO: parametrize rand ranges
        Logger.log("Trying to add "+ quantity +" particles!");
        Random rand = new Random();
        Integer currentQuantity = 0;
        while(currentQuantity < quantity) {
            double angle = rand.nextDouble()*2*Math.PI;
            double vModule = rand.nextDouble()*5.92+0.08;
            Body newBody = new Body(
                    new Vector(rand.nextDouble() * (container.getWidth(0.0) - 2 * 0.29) + 0.29, rand.nextDouble() * (container.getHeight() - 2 * 0.29) + 0.29),
                    new Vector(Math.cos(angle)*vModule, Math.sin(angle)*vModule),
                80.0,
                (rand.nextDouble()*0.04+0.25)
            );
            // Check that newBody doesn't touch any other body
            if(bodies.stream().noneMatch(newBody::touches)) {
                bodies.add(newBody);
                Logger.log("Particle "+currentQuantity+"/"+quantity+" added!");
                currentQuantity++;
            }
        }
    }

    public void finishExectutor() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
