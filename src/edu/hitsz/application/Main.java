package edu.hitsz.application;
import javax.swing.*;
import java.awt.*;
/**
 * 程序入口
 * @author hitsz
 */
public class Main {
    static final CardLayout cardLayout = new CardLayout(0,0);
    static final JPanel cardPanel = new JPanel(cardLayout);
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static JPanel getCardPanel() {
        return cardPanel;
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        System.out.println("Hello Aircraft War");
        frame.add(cardPanel);
        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartMenu start = new StartMenu();
        cardPanel.add(start.getMainPanel());
        cardLayout.last(Main.cardPanel);

    }
}
