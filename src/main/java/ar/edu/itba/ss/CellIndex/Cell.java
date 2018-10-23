package ar.edu.itba.ss.CellIndex;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by giulianoscaglioni on 14/10/18.
 */
public class Cell<T extends Locatable> {
    // Grid position
    private Long row;
    private Long column;
    private Double length;

    private Set<T> elements;

    public Cell(Long row, Long column, Double length) {
        this.row = row;
        this.column = column;
        this.length = length;
        this.elements = new HashSet<>();
    }

    public Boolean add(T element) {
        return elements.add(element);
    }

    public Boolean remove(T element) {
        return elements.remove(element);
    }

    public Boolean contains(T element) {
        return elements.contains(element);
    }

    public Set<T> getElements() {
        return elements;
    }

    public Boolean shouldContain(T element) {
        Double startX = column * length;
        Double endX = (column + 1) * length;;
        Double startY = row * length;
        Double endY = (row + 1) * length;
        Double x = element.getPositionX();
        Double y = element.getPositionY();

        return (x<endX) && (x>=startX) && (y<endY) && (y>=startY);
    }

    public Set<Cell<T>> getNeighbors() {
        Set<Cell<T>> neighbors = new HashSet<>();

        neighbors.add(new Cell<T>(row+1, column-1, length));
        neighbors.add(new Cell<T>(row+1, column, length));
        neighbors.add(new Cell<T>(row+1, column+1, length));

        neighbors.add(new Cell<T>(row, column-1, length));
        neighbors.add(new Cell<T>(row, column+1, length));

        neighbors.add(new Cell<T>(row-1, column-1, length));
        neighbors.add(new Cell<T>(row-1, column, length));
        neighbors.add(new Cell<T>(row-1, column+1, length));

        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell<?> cell = (Cell<?>) o;

        if (!row.equals(cell.row)) return false;
        return column.equals(cell.column);
    }

    @Override
    public int hashCode() {
        int result = row.hashCode();
        result = 31 * result + column.hashCode();
        return result;
    }
}
