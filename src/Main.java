import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel {//游戏主体

    private int flag;
    private int begin = -1;
    private int shootIndex = 0;
    private int coin = 0;
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;
    private static final int RANK = 4;
    private static final int SHOPPING = 5;
    private Timer timer;
    private Graphics g;
    private static int bgPos = -Controller.mapHeight;
    private static int bgPos1 = 0;
    private int intervel = 10;
    private int flyEnteredIndex = 0;
    private FlyingObject[] enemies_and_awards = {};//飞行物数组（敌机，道具）
    private Bullet[] bullets = {};
    private Plane myPlane = new Plane();
    private JButton start = new JButton();
    private JButton rank = new JButton();
    private JButton restart = new JButton();
    private JButton return_to_main = new JButton();
    private JButton store = new JButton();

    class PrintBackGround extends Thread{//控制背景动态显示
        @Override
        public void run() {
            while(true){
                if(bgPos >= Controller.mapHeight){
                    bgPos = bgPos1 - Controller.background.getHeight();
                }else{
                    if(bgPos1>=Controller.mapHeight){
                        bgPos1 = bgPos - Controller.mapHeight;
                    }else{
                        if(getFlag() == RUNNING){//真正进入游戏才开始滚动
                            bgPos += 2;
                            bgPos1 += 2;
                        }
                    }
                }
                try {
                    Thread.sleep(50);//滚动速度的设定
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getFlag() {
        return flag;
    }

    private void setButton() {//初始化各个按钮
        start.setBorderPainted(false);
        start.setContentAreaFilled(false);//设置背景为透明
        start.setIcon(new ImageIcon("image/START.gif"));//设置图标
        start.addActionListener(new ActionListener() {//添加按钮监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = RUNNING;
                begin =RUNNING;
                remove(rank);
                remove(start);
                remove(store);
            }
        });
        restart.setContentAreaFilled(false);
        restart.setIcon(new ImageIcon(Controller.restartButton));
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//开始新游戏
                enemies_and_awards = new Enemy[0];
                bullets = new Bullet[0];
                myPlane = new Plane();
                Controller.score = 0;
                flag = START;
                begin = PAUSE;
                remove(restart);
            }
        });
        rank.setBorderPainted(false);
        rank.setContentAreaFilled(false);
        rank.setIcon(new ImageIcon("image/RANK.gif"));
        rank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = RANK;
                remove(rank);
                remove(start);
                remove(store);
//                rank.setVisible(false);
//                start.setVisible(false);
            }
        });
        return_to_main.setBorderPainted(false);
        return_to_main.setIcon(new ImageIcon(Controller.return_to_main));
        return_to_main.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(return_to_main);
                flag =START;
                repaint();
            }
        });
        store.setContentAreaFilled(false);
        store.setBorderPainted(false);
        store.setIcon(new ImageIcon(Controller.store));
        store.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(store);
                remove(start);
                remove(rank);
                flag = SHOPPING;
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics graphics) {
        this.g = graphics;
        g.drawImage(Controller.background, 0, bgPos, null);
        g.drawImage(Controller.background, 0, bgPos1, null);
        paintState();
        if (flag == RUNNING) {
            paintMyPlane();
            paintFlying();
            paintScore();
            paintBullets();
        }
    }

    public void paintMyPlane() {
        g.drawImage(myPlane.getImage(),myPlane.getX(),myPlane.getY(),null);
        if (myPlane.isShield()) {
            g.drawImage(Controller.shield, myPlane.getX() +
                    (myPlane.getWidth() - Controller.shield.getWidth()) / 2,
                    myPlane.getY() + (myPlane.getHeight() - Controller.shield.getHeight()) / 2, null);
        }
    }

    public void paintFlying() {
        for (int i = 0; i < enemies_and_awards.length; i++) {
            FlyingObject f = enemies_and_awards[i];
            g.drawImage(f.getImage(), f.getX(), f.getY(), null);
        }
    }

    public void paintScore() {
        int score = Controller.score;
        int pos = 100000;
        int width = Controller.numbers[0].getWidth();
        for (int i = 0; i < 6; i ++) {
            int num = score / pos;
            g.drawImage(Controller.numbers[num], (Controller.mapWidth - 6 * width) / 2 + 18 * i, 20, null);
            score %= pos;
            pos /= 10;
        }//画分数
        g.drawImage(Controller.hp, Controller.mapWidth - 8 * width, 20, null);//画血量
        g.drawImage(Controller.numbers[myPlane.getHp() / 10], Controller.mapWidth - 5 * width, 20, null);
        g.drawImage(Controller.numbers[myPlane.getHp() % 10], Controller.mapWidth - 4 * width, 20, null);

    }

    public void paintBullets() {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),
                    null);
        }
    }

    public void paintState() {
        switch (flag) {
            case START:
                paintMainMenu();
                break;
            case PAUSE:
                g.drawImage(Controller.pause, 0, 0, null);
                break;
            case GAME_OVER:
                begin = GAME_OVER;
                paintOverMenu();
                break;
            case RANK:
                begin = RANK;
                paintRankList();
                break;
            case SHOPPING:
                begin = SHOPPING;
                paintStoreMenu();
                break;
        }
    }

    public void paintMainMenu() {//绘制主菜单
        g.drawImage(Controller.cover, 0, 0, null);
        g.drawImage(Controller.title, (Controller.mapWidth - Controller.title.getWidth()) / 2, 80, null);
        g.drawImage(Controller.start,(Controller.mapWidth - start.getIcon().getIconWidth()) / 2, Controller.mapHeight/2 - 50, null);
        start.setBounds((Controller.mapWidth - start.getIcon().getIconWidth()) / 2, Controller.mapHeight/2 - 50, start.getIcon().getIconWidth(), start.getIcon().getIconHeight());//设置按钮位置
        g.drawImage(Controller.rank, (Controller.mapWidth - rank.getIcon().getIconWidth()) / 2, Controller.mapHeight/2 + 50 , null);
        rank.setBounds((Controller.mapWidth - rank.getIcon().getIconWidth()) / 2, Controller.mapHeight/2 + 50, rank.getIcon().getIconWidth(), rank.getIcon().getIconHeight());
        g.drawImage(Controller.store,(Controller.mapWidth - store.getIcon().getIconWidth()) / 2, Controller.mapHeight/2 + 150, null);
        store.setBounds((Controller.mapWidth - store.getIcon().getIconWidth()) / 2, Controller.mapHeight/2 + 150, store.getIcon().getIconWidth(), store.getIcon().getIconHeight());
        start.setVisible(true);
        rank.setVisible(true);
        store.setVisible(true);
        this.add(start);
        this.add(rank);
        this.add(store);
        start.repaint();
        rank.repaint();
        store.repaint();
    }

    public void paintRankList() {//绘制排行榜
        int x = Controller.mapWidth/2 - 30;
        int y = Controller.mapHeight/2 - 60;
        int count = 0;
        Font font1 = new Font(Font.SANS_SERIF, Font.BOLD, 50);//设置字体
        Font font2 = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        g.drawImage(Controller.background, 0, 0, null);
        g.setColor(new Color(0x2ECCFF));
        g.setFont(font1);
        g.drawString("排行榜" , x - 50, y - 90);
        g.setColor(new Color(0xF2742F));
        g.setFont(font2);
        for (int score:Controller.rankList) {
            y += 30;
            g.drawString(++count + ".  " + score, x - 50, y);
        }
        return_to_main.setBounds(20, 20, return_to_main.getIcon().getIconWidth(), return_to_main.getIcon().getIconHeight());
        this.add(return_to_main);
        return_to_main.setVisible(true);
        return_to_main.repaint();
    }

    public void paintOverMenu() {
        int y = Controller.mapHeight/2;
        int score = Controller.score;
        int pos = 100000;
        int width = Controller.numbers[0].getWidth();
        for (int i = 0; i < 6; i ++) {
            int num = score / pos;
            g.drawImage(Controller.numbers[num], (Controller.mapWidth - 6 * width) / 2 + 18 * i, y, null);
            score %= pos;
            pos /= 10;
        }//画分数
        bgPos = -Controller.mapHeight;
        bgPos1 = 0;
        g.drawImage(Controller.restartButton, (Controller.mapWidth  - Controller.restartButton.getWidth())/ 2, Controller.mapHeight/2 + 80, null);
        g.drawImage(Controller.gameOver, (Controller.mapWidth - Controller.gameOver.getWidth()) / 2, 0, null);
        restart.setBounds((Controller.mapWidth  - Controller.restartButton.getWidth())/ 2 , Controller.mapHeight/2 + 80, restart.getIcon().getIconWidth(), restart.getIcon().getIconHeight());
        restart.setVisible(true);
        this.add(restart);
        restart.repaint();
    }

    public void paintStoreMenu() {
        g.drawImage(Controller.storeMenu, 0, 0, null);

    }

    public void action() {//主要执行部分
        MouseAdapter m = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (flag == RUNNING) {
                    int x = e.getX();
                    int y = e.getY();
                    myPlane.moveTo(x, y);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (flag == PAUSE) {
                    flag = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (flag == RUNNING) {
                    flag = PAUSE;
                }
            }

        };
        new PrintBackGround().start();
        this.addMouseMotionListener(m);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (flag == RUNNING) {
                    enterAction();
                    stepAction();
                    shootAction();
                    bangAction();
                    outOfBoundsAction();
                    checkGameOverAction();
                }
                if (flag == RUNNING || (flag == GAME_OVER && begin == PAUSE) || (flag == START && begin == PAUSE) ) {
                    begin = -1;
                    repaint();
                }
            }

        },intervel , intervel);
    }

    public void enterAction() {//控制新单位的生成
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) {
            Enemy obj = nextOne();
            enemies_and_awards = Arrays.copyOf(enemies_and_awards, enemies_and_awards.length + 1);
            enemies_and_awards[enemies_and_awards.length - 1] = obj;
        }
    }

    public void stepAction() {//控制所有单位的移动
        for (int i = 0; i < enemies_and_awards.length; i++) {
            FlyingObject f = enemies_and_awards[i];
            f.move();
        }

        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            b.move();
        }
        myPlane.move();
    }


    public void shootAction() {
        shootIndex++;
        if (shootIndex % 120 == 0) {//敌机开火
            for (FlyingObject e: enemies_and_awards) {
                if (e.getType().equals(Controller.Types.ENEMY_B)) {
                    Bullet[] enemy_bullet = ((Enemy)e).fire();
                    bullets = Arrays.copyOf(bullets, bullets.length + enemy_bullet.length);
                    System.arraycopy(enemy_bullet, 0, bullets, bullets.length - enemy_bullet.length,
                            enemy_bullet.length);
                }
            }
        }
        if (shootIndex % myPlane.getFireRate() == 0) {//己方飞机开火
            Bullet[] hero_bullet = myPlane.fire();

            bullets = Arrays.copyOf(bullets, bullets.length + hero_bullet.length);
            System.arraycopy(hero_bullet, 0, bullets, bullets.length - hero_bullet.length,
                    hero_bullet.length); // 追加数组
        }
    }

    public void bangAction() {//检查是否被击中
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            bang(b);
        }
    }

    public void bang(Bullet bullet) {
        if (!bullet.getType().equals(Controller.Types.HERO)) {
            if (myPlane.shootBy(bullet)) {
                bullet.setAlive(false);
            }
            return;
        }
        int index = -1;
        for (int i = 0; i < enemies_and_awards.length; i++) {
            FlyingObject obj = enemies_and_awards[i];
            if (obj.shootBy(bullet) && !obj.getMainType().equals(Controller.Types.AWARD)) {
                bullet.setAlive(false);
                index = i;//获取被击中的飞行物下标
                break;
            }
        }
        if (index != -1) {
            Enemy one = (Enemy)enemies_and_awards[index];
            if (one.getHp() <= 0) {
                Enemy temp = (Enemy)enemies_and_awards[index];
                enemies_and_awards[index] = enemies_and_awards[enemies_and_awards.length - 1];
                enemies_and_awards[enemies_and_awards.length - 1] = temp;
                enemies_and_awards = Arrays.copyOf(enemies_and_awards, enemies_and_awards.length - 1);
                if (temp.getType().equals(Controller.Types.ENEMY_B)) {
                    Award award = new Award(Controller.Types.AWARD_SHIELD, temp.getX(), temp.getY());
                    enemies_and_awards = Arrays.copyOf(enemies_and_awards, enemies_and_awards.length + 1);
                    enemies_and_awards[enemies_and_awards.length - 1] = award;
                }
                Controller.score += one.getScore();
                coin += one.getCoin();
                if (Controller.score > 1000 * (int)Math.pow(myPlane.getLevel(),2) + 1800) {
                    myPlane.levelUp();
                }
            }
        }
    }

    public void outOfBoundsAction() {
        int index = 0;
        FlyingObject[] flyingLives = new FlyingObject[enemies_and_awards.length];
        for (int i = 0; i < enemies_and_awards.length; i++) {
            FlyingObject f = enemies_and_awards[i];
            if (!f.outOfBounds()) {
                flyingLives[index++] = f;
            }
        }
        enemies_and_awards = Arrays.copyOf(flyingLives, index);

        index = 0;
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            if (!b.outOfBounds() && b.isAlive()) {
                bulletLives[index++] = b;
            }
        }
        bullets = Arrays.copyOf(bulletLives, index);
    }

    public void checkGameOverAction() {
        if (gameOver()) {
            Controller.addScore();
            Attributes.addMoney(coin);
            coin = 0;
            flag = GAME_OVER;
            begin = PAUSE;
        }
    }

    public boolean gameOver() {

        for (int i = 0; i < enemies_and_awards.length; i++) {
            int index = -1;
            FlyingObject obj = enemies_and_awards[i];
            if (myPlane.hitBy(obj)) {//如果发生碰撞
                myPlane.solveHit(obj);
                index = i;
            }
            if (index != -1) {
                FlyingObject f = enemies_and_awards[index];
                enemies_and_awards[index] = enemies_and_awards[enemies_and_awards.length - 1];
                enemies_and_awards[enemies_and_awards.length - 1] = f;
                enemies_and_awards = Arrays.copyOf(enemies_and_awards, enemies_and_awards.length - 1);
            }
        }
        return myPlane.getHp() <= 0;
    }

    public static Enemy nextOne() {
        return new Enemy();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fly");
        Main game = new Main(); // 面板对象
        frame.add(game); // 将面板添加到JFrame中
        frame.setSize(Controller.mapWidth, Controller.mapHeight); // 设置大小
//        frame.setAlwaysOnTop(true); // 设置其总在最上
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
        frame.setIconImage(new ImageIcon("image/icon.png").getImage()); // 设置窗体的图标
        frame.setLocationRelativeTo(null); // 设置窗体初始位置
        frame.setContentPane(game);
        frame.setVisible(true); // 尽快调用paint
        game.setButton();
        game.action();
        // 启动执行
    }


}
