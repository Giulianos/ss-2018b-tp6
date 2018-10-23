package ar.edu.itba.ss.Forces;

import java.util.Set;

public class SumForce implements Force {
    private Set<Force> forces;

    private Double x;
    private Double y;

    public SumForce(Set<Force> forces) {
        this.forces = forces;
    }

    @Override
    public void evaluate() {
        x = 0d;
        y = 0d;
        for(Force f : forces) {
            f.evaluate();
            x += f.getX();
            y += f.getY();
        }
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public Double getModule() {
        Double module = 0.0;
        for(Force force: forces){
            module += force.getModule();
        }
        return module;
    }
}
