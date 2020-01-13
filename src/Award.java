public class Award extends FlyingObject {//道具类
    private Controller.Types mainType = Controller.Types.AWARD;
    private int xFlag = 0;
    private int yFlag = 0;

    public Award() { }

    public Award(Controller.Types type, int x, int y) {
        setType(type);
        setImage(Controller.award);
        setX(x);
        setY(y);
    }

    @Override
    public boolean outOfBounds() {
        return getY() > Controller.mapHeight;
    }

    public Controller.Types getMainType() {
        return mainType;
    }

    @Override
    public void move() {
        int random = (int)(Math.random() * 10) % 4;
        int y = getY();
        setY(y + 1);
        if (getX() - random <= 0 || (xFlag == 1 && getX() <= (Controller.mapWidth - getWidth()))) {
            setX(getX() + random);
            xFlag = 1;
        } else if (getX() >= (Controller.mapWidth - getWidth()) || (xFlag == 0 && getX() - random >= 0)) {
            setX(getX() - random);
            xFlag = 0;
        } else {
            setX(getX() + random);
        }


    }

    public void reward(Plane plane) {
        switch (getType()) {
            case AWARD_LIFE:
                plane.addHp();
                break;
            case AWARD_LEVEL_UP:
                plane.levelUp();
                break;
            case AWARD_FIRE_RATE:
                plane.setFireRate(plane.getFireRate() - 10);
                break;
            case AWARD_SHIELD:
                break;
        }
    }

}
