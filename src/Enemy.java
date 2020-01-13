import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends FlyingObject {//敌机
    private Controller.Types mainType = Controller.Types.ENEMY;
    private int speed;
    private int hp;
    private static int count = 0;
    private int score;
    private int coin;
    private int dirc = 2;

    public Enemy() {
        if (count++ < 10) {
            setImage(Controller.enemy_a);
            speed = 2;
            hp = 1;
            score = 100;
            coin = 10;
            setType(Controller.Types.ENEMY_A);
        } else {
            count -= 10;
            setImage(Controller.enemy_b);
            speed = 1;
            hp = 5;
            coin = 30;
            score = 500;
            setType(Controller.Types.ENEMY_B);
        }
        setHeight(getImage().getHeight());
        setWidth(getImage().getWidth());
        setY(-getHeight());
        setX(new Random().nextInt(Controller.mapWidth - getWidth()));
    }

    @Override
    public boolean outOfBounds() {
        return getY() > Controller.mapHeight;
    }

    public Bullet[] fire() {
        int xStep = getWidth()/5;
        int count = 0;
        Bullet[] bullets = new Bullet[6];
        for (int i = 0; i< 6; i++) {
            bullets[i] = new Bullet(getX() + i/3 * xStep + 40, getY() + getHeight() + i%3*Controller.enemy_bullet.getHeight(), getType());
        }
        return bullets;
    }

    @Override
    public void move() {
        if (getType().equals(Controller.Types.ENEMY_A)) {
            setY(getY() + speed);
        } else if (getType().equals(Controller.Types.ENEMY_B)){
            if (getY() < (Controller.mapHeight - getHeight()) / 2) {
                setY(getY() + speed);
            } else {
                int random = (int)(Math.random() * 10) % 5;
                if (getX() - random <= 0 || (dirc == 1 && getX() <= (Controller.mapWidth - getWidth()))) {
                    setX(getX() + random);
                    dirc = 1;
                } else if (getX() >= (Controller.mapWidth - getWidth()) || (dirc == 0 && getX() - random >= 0)) {
                    setX(getX() - random);
                    dirc = 0;
                } else {
                    setX(getX() + random);
                }
            }
        }

    }

    @Override
    public boolean shootBy(Bullet bullet) {
        if (super.shootBy(bullet)){
            hp--;
            System.out.println(hp);
            return true;
        }
        return false;

    }

    public int getScore() {
        return score;
    }

    public int getHp() {
        return hp;
    }

    public int getCoin() {
        return coin;
    }

    public Controller.Types getMainType() {
        return mainType;
    }

}
