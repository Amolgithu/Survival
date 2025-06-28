import java.awt.geom.Rectangle2D;

public class tile{

    private int xindex,yindex;
    private Rectangle2D rect;
    private int size = 64;

    public tile(int xindex, int yindex) {
        this.xindex = xindex;
        this.yindex = yindex;

        this.rect = new Rectangle2D.Double(xindex*size, yindex*size, size, size);
    }

    public Rectangle2D getRect() {
        return rect;
    }
    
}
