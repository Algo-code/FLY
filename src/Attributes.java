import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Attributes {
    protected static int level;
    protected static int money;
    protected static int fire;
    protected static int firing_rate;
    protected static int hp;

    static {
        try {
            FileReader f = new FileReader("data.txt");
            level = f.read();

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public static void addMoney(int a) {
        money += a;
    }

    public static void save(Plane plane) {
        try {
            FileWriter w = new FileWriter("data.txt");
            w.write(plane.getLevel());
            w.write(money);
            w.write(plane.getFire());
            w.write(plane.getFireRate());
            w.write(plane.getHp());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
