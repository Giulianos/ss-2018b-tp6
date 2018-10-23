package ar.edu.itba.ss.Container;

import ar.edu.itba.ss.Particles.Body;

import java.util.Set;

public interface Container {

    /**
     * Calculates collisions between body and
     * the container's walls.
     * @param body
     * @return a set of bodies to collide with
     */
    public Set<Body> getWallCollision(Body body);

    /**
     * The width of the container at
     * the specified depth
     * @param depth
     * @return The width of the container
     */
    public Double getWidth(Double depth);

    public Double getWidth();

    /**
     * The height of the container
     * @return
     */
    public Double getHeight();

    /**
     * Returns whether the body touches any wall
     * @param b
     * @return True if touches a wall, false otherwise
     */
    public Boolean touchesWall(Body b);

    Double getDiameter();
}
