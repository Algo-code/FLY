public class Bullet extends FlyingObject {
    private int speed = 2;
    private boolean alive = true;

    public Bullet(int x, int y, Controller.Types type) {
        setY(y);
        setX(x);
        setType(type);
        if (type.equals(Controller.Types.HERO)) {
            this.setImage(Controller.bullet);
        } else {
            this.setImage(Controller.enemy_bullet);
        }
        setWidth(getImage().getWidth());
        setHeight(getImage().getHeight());
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void move() {
        if (getType().equals(Controller.Types.HERO)) {
            setY(getY() - speed);
        } else {
            setY(getY() + speed);
        }
    }

    @Override
    public boolean outOfBounds() {
        return getY() < -getHeight();
    }
}
