import java.awt.Point;
import java.awt.Rectangle;

public class Player {
    private String name;

    public Point position;
    public Rectangle bodyRectangle;
    int width = 32; // Assuming a width of 32 pixels
    int height = 32;
    
    
    public Player(String name) {
        this.name = name;
        int xcord = 352;
        int ycord = 288;
        this.position = new Point(xcord, ycord);
        bodyRectangle = new Rectangle(xcord-16, ycord-16, width, height);

    }
    public void updatBodyRectangle(int translatiox, int translatey) {
        this.bodyRectangle.translate(translatiox, translatey);
        // System.out.println("Player body rectangle updated to: " + bodyRectangle);
    }
    public String getName() {
        return name;
    }

    
}
