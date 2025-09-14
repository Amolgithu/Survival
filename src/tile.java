import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class tile{

    private int xindex,yindex;
    private Rectangle2D rect;
    private int size = 64;
    public int xcoord, ycoord;
    public Color tileColor;
    public tile(int xindex, int yindex) {
        this.xindex = xindex;
        this.yindex = yindex;

        this.rect = new Rectangle2D.Double(xindex*size, yindex*size, size, size);
        this.xcoord = xindex * size;
        this.ycoord = yindex * size;
    }

    public Rectangle2D getRect() {
        return rect;
    }

    public void setTileColor(Color tileType) {
        this.tileColor = tileType;   
    }
    
}

class Tileproperty
 {

    public static Color tileType(double noise){

        if (noise < 0.70) {        
                        return new Color(60, 90, 200); 
                    } else if (noise < 0.80) { 
                        return new Color(240, 230, 140); 
                    } else {                             
                        return new Color(34, 139, 34);  
                    }

    }

}