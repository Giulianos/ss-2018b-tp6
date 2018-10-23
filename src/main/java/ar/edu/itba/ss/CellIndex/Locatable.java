package ar.edu.itba.ss.CellIndex;

/**
 * Created by giulianoscaglioni on 14/10/18.
 */

/**
 * Provides a inteface to define locatable (has position)
 * behavior on an Object.
 */
public interface Locatable {
    /**
     * Returns the x coordinate of the object
     * @return
     */
    public Double getPositionX();

    /**
     * Returns the y coordinate of the object
     * @return
     */
    public Double getPositionY();
}
