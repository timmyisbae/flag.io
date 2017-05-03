
/**
 * GamePanel.java  
 *
 * @author: Tim Spaeth
 * Assignment #:
 * 
 * Brief Program Description:
 * 
 *
 */

import javax.swing.*;
import javax.imageio.*;
import java.net.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener
{

    PrintWriter out;
    Player player;

    BufferedImage background;

    ArrayList<OtherPlayer> players = new ArrayList<OtherPlayer>();

    ArrayList<Direction> direction = new ArrayList<Direction>();
    ArrayList<Direction> releasedKeys = new ArrayList<Direction>();

    private boolean start = true, canSendCoords = true;

    private int offsetX, offsetY;

    public GamePanel(Dimension d, Player player) {
        super();

        this.player = player;

        try {
            background = ImageIO.read(getClass().getResource("background.jpg"));
        } catch(IOException e) {}

        this.setOpaque(true);
        this.setPreferredSize(d);
        this.addKeyListener(this);
        this.setFocusable(true);

        Timer timer = new Timer(1, this);
        timer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        out.println("EXITED ID:" + player.getID()); 
                    }
                }));

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(player.getColor() == 1)
            g.setColor(Color.BLUE);
        else if(player.getColor() == 2)
            g.setColor(Color.RED);
        else if(player.getColor() == 3)
            g.setColor(Color.PINK);

        g.drawImage(background, offsetX + (this.getWidth() / 2) - player.getSize() / 2, offsetY + (this.getHeight() / 2) - player.getSize() / 2, 4000, 4000, this);
        g.fillRect((this.getWidth() / 2) - player.getSize() / 2, (this.getHeight() / 2) - player.getSize() / 2, player.getSize(), player.getSize());
        g.setColor(Color.WHITE);
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        // Determine the X coordinate for the text
        int x = (this.getWidth() - metrics.stringWidth(player.getName())) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(g.getFont());
        // Draw the String
        g.drawString(player.getName(), x, y);

        //draw other players
        for(OtherPlayer p : players) {
            if(p.getColor() == 1)
                g.setColor(Color.BLUE);
            else if(p.getColor() == 2)
                g.setColor(Color.RED);
            else if(p.getColor() == 3)
                g.setColor(Color.PINK);
            g.fillRect(offsetX + p.getX() + (this.getWidth() / 2) - player.getSize() / 2, offsetY + p.getY() + (this.getHeight() / 2) - player.getSize() / 2, p.getSize(), p.getSize());

            g.setColor(Color.WHITE);
            // Determine the X coordinate for the text
            x = (offsetX + p.getX() + (this.getWidth() / 2)) - (metrics.stringWidth(player.getName()) / 2);
            // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
            y = (offsetY + p.getY() + (this.getHeight() / 2)) - (metrics.getHeight() / 2) + metrics.getAscent();
            // Set the font
            g.setFont(g.getFont());
            // Draw the String
            g.drawString(p.getName(), x, y);

        }

        if(start) start = false;
    }

    public void actionPerformed(ActionEvent e) {
        repaint();

        if(!start) {

            if(direction.contains(Direction.UP)) {
                player.moveUp();
            }
            if(direction.contains(Direction.DOWN)) {
                player.moveDown();
            }
            if(direction.contains(Direction.LEFT)) {
                player.moveLeft();
            }
            if(direction.contains(Direction.RIGHT)) {
                player.moveRight();
            }

            if(releasedKeys.contains(Direction.UP)) {
                if(player.resetAcceleration(Direction.UP))
                    releasedKeys.remove(Direction.UP);
            }
            if(releasedKeys.contains(Direction.DOWN)) {
                if(player.resetAcceleration(Direction.DOWN))
                    releasedKeys.remove(Direction.DOWN);
            }
            if(releasedKeys.contains(Direction.LEFT)) {
                if(player.resetAcceleration(Direction.LEFT))
                    releasedKeys.remove(Direction.LEFT);
            }
            if(releasedKeys.contains(Direction.RIGHT)) {
                if(player.resetAcceleration(Direction.RIGHT))
                    releasedKeys.remove(Direction.RIGHT);
            }

            if(player.getX() > 4000 - player.getSize())
                player.setX(4000 - player.getSize());

            if(player.getX() < 0)
                player.setX(0);

            if(player.getY() > 4000 - player.getSize())
                player.setY(4000 - player.getSize());

            if(player.getY() < 0)
                player.setY(0);

            if(offsetX != -player.getX() || offsetY != -player.getY())
                canSendCoords = true;

            offsetX = -player.getX();
            offsetY = -player.getY();

            if(canSendCoords && out != null)
                out.println("COORD ID:" + player.getID() + "|X:" + player.getX() + "|Y:" + player.getY());

            if(out!=null)
                canSendCoords = false;

        }

    }

    public void keyPressed(KeyEvent e) {
        if (out != null) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                if(!direction.contains(Direction.UP))direction.add(Direction.UP);
                if(releasedKeys.contains(Direction.UP))releasedKeys.remove(Direction.UP);
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {  
                if(!direction.contains(Direction.DOWN))direction.add(Direction.DOWN);
                if(releasedKeys.contains(Direction.DOWN))releasedKeys.remove(Direction.DOWN);
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {    
                if(!direction.contains(Direction.LEFT))direction.add(Direction.LEFT);
                if(releasedKeys.contains(Direction.LEFT))releasedKeys.remove(Direction.LEFT);
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(!direction.contains(Direction.RIGHT))direction.add(Direction.RIGHT);
                if(releasedKeys.contains(Direction.RIGHT))releasedKeys.remove(Direction.RIGHT);
            }
            if(e.getKeyCode() == KeyEvent.VK_W) {
                if(!direction.contains(Direction.UP))direction.add(Direction.UP);
                if(releasedKeys.contains(Direction.UP))releasedKeys.remove(Direction.UP);
            }
            if(e.getKeyCode() == KeyEvent.VK_S)
                out.println("down");
            if(e.getKeyCode() == KeyEvent.VK_A)
                out.println("left");
            if(e.getKeyCode() == KeyEvent.VK_D)
                out.println("right");
        }
    }

    public void keyReleased(KeyEvent e) {
        if (out != null) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                direction.remove(Direction.UP); 
                if(!releasedKeys.contains(Direction.UP))releasedKeys.add(Direction.UP);  
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                direction.remove(Direction.DOWN); 
                if(!releasedKeys.contains(Direction.DOWN))releasedKeys.add(Direction.DOWN); 
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                direction.remove(Direction.LEFT); 
                if(!releasedKeys.contains(Direction.LEFT))releasedKeys.add(Direction.LEFT); 
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direction.remove(Direction.RIGHT);    
                if(!releasedKeys.contains(Direction.RIGHT))releasedKeys.add(Direction.RIGHT); 
            }
            out.println("released");
        }       
    }

    public void keyTyped(KeyEvent e) {

    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public void addPlayer(OtherPlayer player) {
        players.add(player);
        System.out.println("ADDED PLAYER");
    }

    public boolean canAddPlayer(int ID) {
        for(OtherPlayer p : players) 
            if(p.getID() == ID)
                return false;

        //dont add this client to players
        if(this.player.getID() == ID)
            return false;

        return true;
    }

    public OtherPlayer getOtherPlayerByID(int ID) {
        for(OtherPlayer p : players)
            if(p.getID() == ID)
                return p;
        return null;
    }

    public void removeOtherPlayer(int ID) {
        for(int x = 0; x < players.size(); x ++)
            if(players.get(x).getID() == ID)
                players.remove(x);
    }
}

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NONE
}
