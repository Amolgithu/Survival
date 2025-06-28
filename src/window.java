import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class window extends JFrame {

    Set<Integer> keysPressed = new HashSet<>();
    public int tilescount = 21;

    public window() {
        setTitle("Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setGameArea(21);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    void setGameArea(int tilescount) {

        GamePanel gamePanel = new GamePanel(tilescount);
        JScrollPane scrollPane = new JScrollPane(gamePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                keysPressed.add(e.getKeyCode());
                System.out.println("key preseed");
            }
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                keysPressed.remove(e.getKeyCode());
            }

        });

        JScrollBar vBar = scrollPane.getVerticalScrollBar();
        JScrollBar hBar = scrollPane.getHorizontalScrollBar();

       Timer timer = new Timer(10,e->{
            if(keysPressed.contains(KeyEvent.VK_W)){
                vBar.setValue(vBar.getValue() - 5);
            }
            if(keysPressed.contains(KeyEvent.VK_S)){
                vBar.setValue(vBar.getValue() + 5);
            }
            if(keysPressed.contains(KeyEvent.VK_A)){
                hBar.setValue(hBar.getValue() - 5
                );
            }
            if(keysPressed.contains(KeyEvent.VK_D)){
                hBar.setValue(hBar.getValue() + 5);
            }

       });
        timer.start();
       

        add(scrollPane, BorderLayout.CENTER);


    }
    public static void main(String[] args) {
        window myWindow = new window();

        System.out.println("Hello, World!");
    }
}