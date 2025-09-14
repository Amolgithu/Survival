import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class GamePanel extends JPanel{

    private ArrayList<tile> tiles = new ArrayList<tile>();
    private Random random = new Random();
    private Player player;
    private window w;
    private static final double FREQUENCY = 0.1; // Tuned for a grid
    private static final int OCTAVES = 3;
    private static final double PERSISTENCE = 0.5;

    JViewport viewport;
    JScrollPane scrollPane;

    JScrollBar vBar, hBar;
    boolean canScrollDown,canScrollLeft,canScrollRight,canScrollUp;

    public GamePanel(int tilescount,window w) {  
        setPreferredSize(new Dimension(64*tilescount, 64*tilescount ));

        new Rectangle(352, 288, 64*tilescount-352, 64*tilescount-288);

        for(int i = 0; i < tilescount; i++) {
            for(int j = 0; j < tilescount; j++) {

                double finalNoiseValue = 0;
                    double currentFrequency = FREQUENCY;
                    double currentAmplitude = 1;

                    for (int ii = 0; ii < OCTAVES; ii++) {
                        finalNoiseValue += PerlinNoise.noise(i * currentFrequency, j * currentFrequency, 0) * currentAmplitude;
                        currentFrequency *= 2;
                        currentAmplitude *= PERSISTENCE;
                    }
                    
                    finalNoiseValue = Math.max(0, Math.min(1, finalNoiseValue));

                    tile t = new tile(i, j);
                    t.setTileColor(Tileproperty.tileType(finalNoiseValue));
                    tiles.add(t);
            }
        }
        player=new Player("Steve");
        this.w = w;

        setBackground(java.awt.Color.BLACK);
        System.out.println("here");
    }

    

    public void runGameLoop(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        vBar = scrollPane.getVerticalScrollBar();
        hBar = scrollPane.getHorizontalScrollBar();
        viewport= scrollPane.getViewport();

        Thread gameLoop = new Thread(() -> {
            
            
            long lastTime = System.nanoTime();
            int fps = 0;
            
            while (true) {

                if(System.nanoTime() - lastTime >= 1000000000){
                    lastTime = System.nanoTime();
                    System.out.println("FPS: " + fps);
                    fps = 0;
                }

                viewport = scrollPane.getViewport();
                
                repaint(viewport.getViewPosition().x,viewport.getViewPosition().y,viewport.getWidth(),viewport.getHeight());
                updateViewPort();
                updatePlayerMovement();
                fps++;

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        gameLoop.start();
    }

    private void updatePlayerMovement() {

        int movment = 5;
        int translatiox=0,translatey=0;
        boolean inframex = viewport.getViewPosition().getX()+viewport.getWidth()/2-16 < player.position.getX() && viewport.getViewPosition().getX()+viewport.getWidth()/2+16 > player.position.getX();
        boolean inframey = viewport.getViewPosition().getY()+viewport.getHeight()/2-16 < player.position.getY() && viewport.getViewPosition().getY()+viewport.getHeight()/2+16 > player.position.getY(); 
            
        
        
            if(w.keysPressed.contains(KeyEvent.VK_A) && playerleft ){
                player.position.translate(-movment, 0);
                translatiox -= movment;
                if(canScrollLeft && inframex){
                    hBar.setValue(hBar.getValue() - movment);
                }
                
            }
            if(w.keysPressed.contains(KeyEvent.VK_D) && playerright){
                player.position.translate(movment, 0);
                translatiox += movment;  
                if(canScrollRight && inframex ){
                    hBar.setValue(hBar.getValue() + movment);
                }
            }
            if(w.keysPressed.contains(KeyEvent.VK_W) && playerup){
                player.position.translate(0, -movment);
                translatey -= movment;
               if(canScrollUp && inframey){
                    vBar.setValue(vBar.getValue() - movment);
                }
            }
            if(w.keysPressed.contains(KeyEvent.VK_S) && playerdown){
                player.position.translate(0, movment);

                translatey += movment;
                if(canScrollDown && inframey){
                    vBar.setValue(vBar.getValue() + movment);
                }
            }
            player.updatBodyRectangle(translatiox, translatey);
        
    }



    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
    
        renderTiles(g);
        rednerPlayers(g);

    }

    void renderTiles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int count = 1;

        for (tile tile : tiles) {
            // g2d.setColor(Color.black);
            // g2d.drawString(""+count, tile.xcoord, tile.ycoord);
            g2d.setColor(tile.tileColor);
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

    
    boolean playerleft, playerright,playerup,playerdown;
    public void updateViewPort() {

     canScrollRight = hBar.getValue() < hBar.getMaximum() - hBar.getVisibleAmount();
     canScrollLeft = hBar.getValue() > hBar.getMinimum();

     canScrollDown = vBar.getValue() < vBar.getMaximum() - vBar.getVisibleAmount();
     canScrollUp = vBar.getValue() > vBar.getMinimum();


        playerleft = player.position.getX()-16 > 0;
        playerright = player.position.getX()+16 <this.getWidth();
        playerup  = player.position.getY() -16 > 0;
        playerdown = player.position.getY() + 16 <this.getHeight(); 

    }

    
    
}
