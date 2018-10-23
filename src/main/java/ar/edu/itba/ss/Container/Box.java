package ar.edu.itba.ss.Container;

import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.FixedBody;
import ar.edu.itba.ss.Vector;

import java.util.HashSet;
import java.util.Set;

public class Box implements Container {
    private Double height;
    private Double width;
    private Double openingDiameter;
    private Set<Body> openingEdgeBodies;

    // Opening coordinate
    private Vector openingCoordinades;

    public Box(Double openingDiameter, Double height, Double width) {
        this.height = height;
        this.width = width;
        this.openingDiameter = openingDiameter;

        openingCoordinades = new Vector(width/2-openingDiameter/2,width/2+openingDiameter/2);

        this.openingEdgeBodies = new HashSet<>();

    }

    private Double wallCollisionPositionVertical(Body body) {
        Double r = body.getRadius();

        if(body.getPosition().getX()-r > openingCoordinades.getX() && body.getPosition().getX()+r < openingCoordinades.getY()) {
            return null;
        }

        if(body.getPosition().getX()-r <= 0.0) {
            return 0.0;
        } else if (body.getPosition().getX()+r >= width) {
            return width;
        }

        return null;
    }

    /**
     * Calculates if there is a collision against a horizontal wall
     * @param body
     * @return the y coordinate of the collision
     */
    private Double wallCollisionPositionHorizontal(Body body) {
        Double x = body.getPositionX();
        Double y = body.getPositionY();
        Double r = body.getRadius();

        if(x-r > openingCoordinades.getX() && x+r < openingCoordinades.getY()) {
            return null;
        }

        if(y-r <= 0.0) {
            return 0.0;
        }
        return null;
    }

    @Override
    public Set<Body> getWallCollision(Body body) {
        Set<Body> bodies = new HashSet<>();

        // Check if there is a collision against a vertical wall
        Double position = wallCollisionPositionVertical(body);
        if(position != null) {
            bodies.add(new FixedBody(new Vector(position, body.getPositionY()),new Vector( 0.0, 0.0), 0.0, 0.0));
        }

        // Check if there is a collision against a horizontal wall
        position = wallCollisionPositionHorizontal(body);
        if(position != null) {
            bodies.add(new FixedBody(new Vector(body.getPositionX(), position), new Vector( 0.0, 0.0), 0.0, 0.0));
        }

        return bodies;
    }

    @Override
    public Set<Body> getOpeningBodies() {
        return openingEdgeBodies;
    }

    @Override
    public Double getWidth(Double depth) {
        return this.width;
    }

    @Override
    public Double getWidth() {
        return this.width;
    }

    @Override
    public Double getHeight() {
        return this.height;
    }

    @Override
    public Boolean touchesWall(Body b) {
        // Check if there is a collision against a vertical wall
        Double position = wallCollisionPositionVertical(b);
        if(position != null) {
            return true;
        }

        // Check if there is a collision against a horizontal wall
        position = wallCollisionPositionHorizontal(b);
        if(position != null) {
            return true;
        }

        return false;
    }

    @Override
    public Double getDiameter() {
        return this.openingDiameter;
    }
}
