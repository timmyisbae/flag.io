
/**
 * Coordinates.java  
 *
 * @author:
 * Assignment #:
 * 
 * Brief Program Description:
 * 
 *
 */
import java.awt.*;
public class OtherPlayer
{
    
    private String name;
    
    private int x, y, size, color, ID;
    
    public OtherPlayer(String name, int x, int y, int size, int color, int ID) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.ID = ID;
    }
    
    public String getName() {
        return name;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getSize() {
        return size;
    }
    
    public int getID() {
        return ID;
    }
    
    //1 - blue, 2 - red, 3 - purple? color of person with the flag tbd
    public int getColor() {
        return color;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }
    
}
