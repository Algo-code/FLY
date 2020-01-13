import java.awt.image.BufferedImage;


public class Plane extends FlyingObject {//己方飞机
    private static final int NON = 0;
    private static final int MISSILE = 1;
    private static final int LASER = 2;
    private int hp;
    private int speed;
    private int fire = 1;
    private int level = 1;
    private BufferedImage[] images = {};
    private int index = 0;
    private int fireRate = 50;
    private boolean shield = false;
    private int invincible_time = 0;

    public Plane() {
        hp = 1;
        speed = 3;
        setType(Controller.Types.HERO);
        setImage(Controller.plane);
        setHeight(getImage().getHeight());
        setWidth(getImage().getWidth());
        setX(Controller.mapWidth - getWidth()/ 2);
        setY(Controller.mapHeight + 1);
    }

    public void moveLeft() {
        setX(getX() - speed);
    }

    public void moveRight() {
        setX(getX() + speed);
    }

    public void moveUp() {
        setY(getY() - speed);
    }

    public void moveDown() {
        setY(getY() + speed);
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public int getFire() {
        return fire;
    }

    public int getLevel() {
        return level;
    }

    public void addHp() {
        hp++;
    }

    public void loseHp() {
        if (invincible_time == 0) {
            hp--;
        }
    }

    public void levelUp() {
        if (fireRate > 10) {
            fireRate -= 5;
        }
        level++;
        fire++;
    }

    public Bullet[] fire() {
        int xStep = getWidth()/5;
        int yStep = 20;
            Bullet[] bullets = new Bullet[fire];
            for (int i = 0; i< fire; i++) {
                bullets[i] = new Bullet(getX() + i * xStep + 20, getY() - yStep, Controller.Types.HERO);
            }
            return bullets;
    }

    public boolean hitBy(FlyingObject obj) {
        int x1 = obj.getX() - this.getWidth()/2;
        int x2 = obj.getX() + this.getWidth()/2 + obj.getWidth();
        int y1 = obj.getY() - this.getHeight()/2;
        int y2 = obj.getY() + this.getHeight()/2 + obj.getHeight();
        int herox = this.getX() + this.getWidth()/2;
        int heroy = this.getY() + this.getHeight()/2;

        return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;
    }

    public void solveHit(FlyingObject object) {
        switch (object.getType()) {
            case ENEMY_A:
                loseHp();
                break;
            case ENEMY_B:
                loseHp();
                break;
            case BOSS_A:
                loseHp();
                break;
            case BOSS_B:
                loseHp();
                break;
            case AWARD_SHIELD:
                shield = true;
                break;
            case AWARD_LEVEL_UP:
                levelUp();
                break;
            case AWARD_LIFE:
                addHp();
                break;
            case AWARD_FIRE_RATE:
                setFireRate(fireRate - 10);
                break;
            case BULLET:
                loseHp();
                break;
            default:
        }
    }

    public int getHp() {
        return hp;
    }

    public boolean isShield() {
        return shield;
    }

    @Override
    public boolean shootBy(Bullet bullet)  {
        if (super.shootBy(bullet)){
            if (shield) {
                shield = false;
                invincible_time = 100;
            } else {
                if (invincible_time == 0) {
                    hp--;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean outOfBounds() {
        return getY() > Controller.mapHeight || getX() > Controller.mapWidth;
    }

    @Override
    public void move() {
        if(images.length > 0){
            setImage(images[index++ / 10 % images.length]);
        }
        if (invincible_time > 0) {
            invincible_time --;
        }

    }
    public void moveTo(int x,int y){
        setX(x - getWidth()/2 );
        setY(y - getHeight()/2);
    }

}
