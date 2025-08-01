import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;


import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class window extends JFrame {

    public Set<Integer> keysPressed = new HashSet<>();
    public int tilescount = 21;

    public window() {
        setTitle("Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setGameArea(tilescount);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    void setGameArea(int tilescount) {

        GamePanel gamePanel = new GamePanel(tilescount,this);

        JScrollPane scrollPane = new JScrollPane(gamePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(704, 576));

        gamePanel.runGameLoop(scrollPane);


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                keysPressed.add(e.getKeyCode());
            }
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                keysPressed.remove(e.getKeyCode());
            }

        });

        JScrollBar vBar = scrollPane.getVerticalScrollBar();
        JScrollBar hBar = scrollPane.getHorizontalScrollBar();


       
       

        add(scrollPane, BorderLayout.CENTER);
       pack();


    }
    public static void main(String[] args) {
        window myWindow = new window();

        System.out.println("Hello, World!");
    }
}