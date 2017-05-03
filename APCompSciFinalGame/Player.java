
/**
 * Player.java  
 *
 * @author:
 * Assignment #:
 * 
 * Brief Program Description:
 * 
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Player
{
    private String name;
    private int x, y, size, color;
    private double velocity;

    private double du = 0, dd = 0, dr = 0, dl = 0; //when it can update movement
    private double au = 0, ad = 0, ar = 0, al = 0; //acceleration

    private int ID;

    public Player(String name, int size, int color, int x, int y, double velocity, int ID) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getVelocity() {
        return velocity;
    }

    public int getID() {
        return ID;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }

    public void moveUp() {

        du += 0.2+au;

        if(du >= 1 && ad == 0) {
            this.y -= velocity;
            du = 0;
            au += 0.02 + au < 4 ? 0.02 : 0;
        }else if(du >= 1 && ad != 0) {
            ad -= 0.02;
        }

    }

    public void moveDown() {

        dd += 0.2+ad;

        if(dd >= 1 && au == 0) {
            this.y += velocity;
            dd = 0;
            ad += 0.02 + ad < 4 ? 0.02 : 0;
        }else if(dd >= 1 && au != 0) {
            au -= 0.02;
        }

    }

    public void moveLeft() {

        dl += 0.2+al;

        if(dl >= 1 && ar == 0) {
            this.x -= velocity;
            dl = 0;
            al += 0.02 + al < 4 ? 0.02 : 0;
        } else if(dl >= 1 && ar != 0) {
            ar -= 0.02;
        }

    }

    public void moveRight() {

        dr += 0.2+ar;

        if(dr >= 1 && al == 0) {
            this.x += velocity;
            dr = 0;
            ar += 0.02 + ar < 4 ? 0.02 : 0;
        }else if(dr >= 1 && al != 0) {
            al -= 0.02;
        }

    }

    public boolean resetAcceleration(Direction a) {
        if(a == Direction.UP) {

            du += 0.2+au;

            if(du >= 1 && au > 0) {
                this.y -= velocity;
                du = 0;
                au -= 0.01;
            } else if(au <= 0) {
                au = 0;
                return true;
            }

            return false;
        }
        else if(a == Direction.DOWN) {
            dd += 0.2+ad;

            if(dd >= 1 && ad > 0) {
                this.y += velocity;
                dd = 0;
                ad -= 0.01;
            } else if(ad <= 0) {
                ad = 0;
                return true;
            }

            return false;
        }
        else if(a == Direction.RIGHT) {
            dr += 0.2+ar;
            if(dr >= 1 && ar > 0) {
                this.x += velocity;
                dr = 0;
                ar -= 0.01;
            } else if(ar <= 0) {
                ar = 0;
                return true;
            }

            return false;
        }
        else if(a == Direction.LEFT) {
            dl += 0.2+al;

            if(dl >= 1 && al > 0) {
                this.x -= velocity;
                dl = 0;
                al -= 0.01;
            } else if(al <= 0) {
                al = 0;
                return true;
            }

            return false;
        } else
            return true;
    }

}
