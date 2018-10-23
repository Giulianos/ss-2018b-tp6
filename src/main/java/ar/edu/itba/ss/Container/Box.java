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

    // Opening coordinate
    private Vector openingCoordinades;

    public Box(Double openingDiameter, Double height, Double width) {
        this.height = height;
        this.width = width;
        this.openingDiameter = openingDiameter;

        openingCoordinades = new Vector(height/2-openingDiameter/2,height/2+openingDiameter/2);

    }

    private Double wallCollisionPositionVertical(Body body) {
        Double x = body.getPositionX();
        Double r = body.getRadius();
        Double y = body.getPositionY();

        if(x - r < 0){
            return 0.0;
        }else if(x + r >= width){
            double aux = y-r;
            if(aux >= openingCoordinades.getX() && aux<= openingCoordinades.getY()){
                return null;
            }
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
        Double y = body.getPositionY();
        Double r = body.getRadius();

        if(y-r <= 0){
            return 0.0;
        }else if( y+r >= height){
            return height;
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
