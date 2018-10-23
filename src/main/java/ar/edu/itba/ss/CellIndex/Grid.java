package ar.edu.itba.ss.CellIndex;

import ar.edu.itba.ss.Logger;
import ar.edu.itba.ss.Particles.Body;

import java.util.*;

/**
 * Created by giulianoscaglioni on 14/10/18.
 */
public class Grid<E extends Locatable> {

    // Cell length
    private Double length;

    // Cells
    private Map<Cell<E>, Cell<E>> cellMap;

    public Grid(Double l) {
        this.length = l;
        this.cellMap = new HashMap<>();
    }

    public void injectElements(Set<E> elements) {
        Logger.log("Injecting elements into grid");
        for(E element : elements) {
            addElementToCell(element);
        }
    }

    public void updateGrid(){
        Set<E> shouldUpdate = new HashSet<>();
        for(Cell<E> cell : cellMap.values()) {
            Iterator<E> i = cell.getElements().iterator();
            while(i.hasNext()) {
                E e = i.next();
                if(!cell.shouldContain(e)) {
                    shouldUpdate.add(e);
                    i.remove();
                }
            }
        }
        for(E element : shouldUpdate) {
            addElementToCell(element);
        }
    }

    public Set<E> getNeighbours(E element) {
        Set<E> neighbours = new HashSet<>();

        Cell<E> elementCell = getCell(element);
        // Add all elements in element's cell
        neighbours.addAll(elementCell.getElements());
        // Remove element from the neighbor set
        neighbours.remove(element);

        // For each neighbor cell add its elements
        for(Cell<E> neighbourCell : elementCell.getNeighbors()) {
            Cell neighbourCellFromMap = getCell(neighbourCell, false);
            if(neighbourCellFromMap != null)
                neighbours.addAll(neighbourCellFromMap.getElements());
        }

        return neighbours;
    }

    private Cell<E> getCell(E element) {
        Long row = Math.round(element.getPositionY() / length);
        Long column = Math.round(element.getPositionX() / length);

        Cell obtainedCell = new Cell<>(row, column, length);

        return getCell(obtainedCell, true);
    }

    private Cell<E> getCell(Cell<E> cell, Boolean create) {
        Cell cellFromMap = cellMap.get(cell);
        if(cellFromMap == null && create) {
            cellMap.put(cell, cell);
            cellFromMap = cell;
        }

        return cellFromMap;
    }

    private void addElementToCell(E element) {
        getCell(element).add(element);
    }
}
