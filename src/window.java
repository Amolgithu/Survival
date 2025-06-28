import java.awt.BorderLayout;

import javax.swing.JFrame;

public class window extends JFrame {

    public int tilescount = 11;

    public window() {
        setTitle("Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setGameArea(11);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    void setGameArea(int tilescount) {

        GamePanel gamePanel = new GamePanel(tilescount);
        add(gamePanel, BorderLayout.CENTER);


    }
    public static void main(String[] args) {
        window myWindow = new window();

        System.out.println("Hello, World!");
    }
}