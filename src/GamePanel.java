import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel{



    private ArrayList<tile> tiles = new ArrayList<tile>();
    private Random random = new Random();

    public GamePanel(int tilescount) {  
        setPreferredSize(new Dimension(64*tilescount, 64*tilescount ));

        for(int i = 0; i < tilescount; i++) {
            for(int j = 0; j < tilescount; j++) {
                tile t = new tile(i, j);
                tiles.add(t);
            }
        }

        addPlayer(new Player("Steve"));

        setBackground(java.awt.Color.BLACK);
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
        
    }

    private Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    void addPlayer(Player player) {

        
        
    }
    
}
