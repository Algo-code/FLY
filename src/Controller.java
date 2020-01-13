import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;

public class Controller {
    protected static int mapWidth ;
    protected static int mapHeight ;
    protected static BufferedImage hp;
    protected static BufferedImage bullet;
    protected static BufferedImage enemy_a;
    protected static BufferedImage enemy_b;
    protected static BufferedImage plane;
    protected static BufferedImage background;
    protected static BufferedImage gameOver;
    protected static BufferedImage start;
    protected static BufferedImage pause;
    protected static BufferedImage restartButton;
    protected static BufferedImage return_to_main;
    protected static BufferedImage rank;
    protected static BufferedImage store;
    protected static BufferedImage storeMenu;
    protected static BufferedImage title;
    protected static BufferedImage cover;
    protected static BufferedImage award;
    protected static BufferedImage enemy_bullet;
    protected static BufferedImage shield;
    protected static BufferedImage[] numbers = new BufferedImage[10];
    protected static int score = 0;
    protected static ArrayList<Integer> rankList = new ArrayList<>();

    public static enum Types{
        HERO,
        ENEMY,
        AWARD,
        ENEMY_A,
        ENEMY_B,
        BOSS_A,
        BOSS_B,
        AWARD_LIFE,//加生命
        AWARD_FIRE_RATE,//加射速
        AWARD_SHIELD,//加护盾
        AWARD_LEVEL_UP,//升级
        BULLET
    }

    static {
        try {
            hp = ImageIO.read(new File("image/HP.gif"));
            bullet = ImageIO.read(new File("image/BULLET.gif"));
            enemy_a = ImageIO.read(new File("image/Enemy_a.gif"));
            enemy_b = ImageIO.read(new File("image/Enemy_b.gif"));
            plane = ImageIO.read(new File("image/PLANE_B.gif"));
            background = ImageIO.read(new File("image/BACKGROUND.gif"));
            gameOver = ImageIO.read(new File("image/GAMEOVER.gif"));
            pause = ImageIO.read(new File("image/BACKGROUND.gif"));
            restartButton = ImageIO.read(new File("image/over_button.png"));
            return_to_main = ImageIO.read(new File("image/return_to_main.png"));
            store = ImageIO.read(new File("image/STORE.gif"));
            storeMenu = ImageIO.read(new File("image/StoreMenu.png"));
            title = ImageIO.read(new File("image/TITLE.gif"));
            cover = ImageIO.read(new File("image/COVER.gif"));
            start = ImageIO.read(new File("image/START.gif"));
            rank = ImageIO.read(new File("image/RANK.gif"));
            award = ImageIO.read(new File("image/AWARD.gif"));
            enemy_bullet = ImageIO.read(new File("image/ENEMY_BULLET.gif"));
            shield = ImageIO.read(new File("image/SHIELD.gif"));
            mapWidth = background.getWidth();
            mapHeight = background.getHeight();
            for (int i = 0; i < 10; i++) {
                numbers[i] = ImageIO.read(new File( "image/number/" + i + ".gif"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void addScore() {
        rankList.add(score);
        Collections.sort(rankList,Collections.reverseOrder());
    }

}
