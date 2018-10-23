package ar.edu.itba.ss.Particles;

public class FixedBody extends Body {

    public FixedBody(Double x, Double y, Double vx, Double vy, Double mass, Double radius) {
        super(x, y, vx, vy, mass, radius);
    }

    @Override
    public Boolean isFixed() {
        return true;
    }

    @Override
    public void update() {
    }

    @Override
    public Double getPreviousPositionX() {
        return getPositionX();
    }

    @Override
    public Double getPreviousVelocityX() {
        return getVelocityX();
    }

    @Override
    public Double getFuturePositionX() {
        return getPositionX();
    }

    @Override
    public Double getFutureVelocityX() {
        return getVelocityX();
    }

    @Override
    public Double getPreviousPositionY() {
        return getPositionY();
    }

    @Override
    public Double getPreviousVelocityY() {
        return getVelocityY();
    }

    @Override
    public Double getFuturePositionY() {
        return getPositionY();
    }

    @Override
    public Double getFutureVelocityY() {
        return getVelocityY();
    }
}
