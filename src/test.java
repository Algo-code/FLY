import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test extends JPanel {


    @Override
    public void paint(Graphics g) {
        g.drawImage(Controller.pause, 0, 0, null);
        JButton btn = new JButton();

        // 设置按钮的默认图片
        btn.setIcon(new ImageIcon("start.png"));

        // 设置按钮被点击时的图片
        btn.setPressedIcon(new ImageIcon("rank.png"));

        // 不绘制边框
        btn.setBorderPainted(false);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("按钮被点击了");
            }
        });

        this.add(btn);

    }

    public void addButton() {


    }

    public static void main(String[] args) throws AWTException {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(200, 200);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        test t = new test();
        t.addButton();

        jf.setContentPane(t);
        jf.setVisible(true);
    }

}