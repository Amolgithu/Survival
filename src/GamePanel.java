import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Timer;

public class GamePanel extends JPanel{

    private ArrayList<tile> tiles = new ArrayList<tile>();
    private Random random = new Random();
    private Player player;
    private boolean ismovable = true;
    private window w;
    private Rectangle boundrec;

    JScrollBar vBar, hBar;

    public GamePanel(int tilescount,window w) {  
        setPreferredSize(new Dimension(64*tilescount, 64*tilescount ));

        boundrec = new Rectangle(352, 288, 64*tilescount-352, 64*tilescount-288);

        for(int i = 0; i < tilescount; i++) {
            for(int j = 0; j < tilescount; j++) {
                tile t = new tile(i, j);
                tiles.add(t);
            }
        }
        player=new Player("Steve");
        this.w = w;

        setBackground(java.awt.Color.BLACK);
        System.out.println("here");
    }

    

    public void runGameLoop(JScrollPane scrollPane) {

        JViewport viewport = scrollPane.getViewport();

        // Timer timer = new Timer(10,e->{
        //     updatePlayerMovement();
        // });
        // timer.start();



        Thread gameLoop = new Thread(() -> {
            
            
            long lastTime = System.nanoTime();
            int fps = 0;
            
            while (true) {

                if(System.nanoTime() - lastTime >= 1000000000){
                    lastTime = System.nanoTime();
                    System.out.println("FPS: " + fps);
                    fps = 0;

                }

                repaint();
                updateViewPort(viewport);
                updatePlayerMovement();
                //fps counter
                fps++;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                // System.out.println("running");
            }
        });
        gameLoop.start();
    }



    private void updatePlayerMovement() {

        int movment = 5;
        int translatiox=0,translatey=0;
        ismovable=false;
            
            if(w.keysPressed.contains(KeyEvent.VK_A) && player.position.getX() > boundrec.getX()){
                player.position.translate(-movment, 0);
                translatiox -= movment;
                ismovable = true;
            }
            if(w.keysPressed.contains(KeyEvent.VK_D) && player.position.getX() < this.getWidth()){
                player.position.translate(movment, 0);
                translatiox += movment;  
                ismovable = true;
            }
            if(w.keysPressed.contains(KeyEvent.VK_W) && player.position.getY() > boundrec.getY()){
                player.position.translate(0, -movment);
                translatey -= movment;
                ismovable = true;
            }
            if(w.keysPressed.contains(KeyEvent.VK_S) && player.position.getY() < boundrec.getHeight()){
                player.position.translate(0, movment);
                translatey += movment;
                ismovable = true;
            }
        player.updatBodyRectangle(translatiox, translatey);
    }



    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        // g2d.drawString("Game Panel", 50, 50);
        
        renderTiles(g);
        rednerPlayers(g);

    }

    void renderTiles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int count = 1;
        for (tile tile : tiles) {
            g2d.setColor(Color.black);
            g2d.drawString(""+count, tile.xcoord, tile.ycoord);
            g2d.setColor(Color.GREEN);
            g2d.fill(tile.getRect());
            count++;
        }
    }

    void rednerPlayers(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        
        
        g2d.draw(player.bodyRectangle);
        
    }

    private Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public void updateViewPort(JViewport viewport) {
            if(boundrec.contains(player.bodyRectangle) || ismovable) {
                viewport.setViewPosition(new Point((int)player.position.getX()-352, (int)player.position.getY()-288));
            }    
    
        }

    
    
}
