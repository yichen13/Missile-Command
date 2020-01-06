import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import javax.sound.sampled.*;

/**

 
 * Jay Chandran
 * @version (a version number or a date)
 */
public class City
{
    // instapoince variables - replace the example below with your own
    private Point p;
    private boolean standing;
    private int radius;
    private double thisX,thisY;

    /**
     * Constructor for objects of class City
     */
    public City(int x,int y)
    {
        // initialise instance variables
        thisX = x;
        thisY = y;
        p = new Point(x,y);
        standing = true;
        radius = 25;
    }
    public void rebuild(){
        standing = true;
    }
    public boolean collides(EnemyMissile e, int x) {
        if ((Math.sqrt((e.getCY() - thisY) * (e.getCY() - thisY) + (e.getCX() - thisX) * (e.getCX() - thisX))) <= 5)
        {
            if (x < 3)
            {
                kill();
            }
            return true;
          }
        return false;
    }
    
    public boolean getStatus()
    {
        // put your code here
        return standing;
    }
    
    public void kill() {
        standing = false;
        
        
        
    } 
    public Point getLoc() {
        return p;
        
        
    }
    
    public void draw(Graphics g) {
        if (standing) {
            g.setColor(Color.BLUE);
            g.fillOval(p.x-radius,p.y-radius,radius*2,radius*2); 
        }
    }
    
}
