import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Battery
{
    // instance variables - replace the example below with your own
    private int x,y,radius,explosionRadius,missiles,ticksTillImpact;
    private boolean firing,exploding,standing;
    private double targetX,targetY,missileX,missileY,missileDeliveredX,missileDeliveredY;

   
    public Battery(int newX, int newY)
    {
        // initialise instance variables
        x = newX;
        y= newY;
        radius = 25;
        missiles = 10;
        ticksTillImpact = 40;
        standing = true;
        firing = false;
        exploding = false;
        
    }
    public void rebuild() {
        standing = true;
        missiles = 10;
    }
    public Point getPos() {
    Point p = new Point(x,y);
    return p;
    }
    
    public boolean getStatus() {
    return standing;
    }
    public void kill() {
        standing = false;
    }
    public boolean fire(int mouseX,int mouseY) {
        if (!firing && !exploding && mouseY<500 && missiles > 0 && standing) {
            missiles--;
            targetX = mouseX;
            targetY = mouseY;
            missileX = x;
            missileY = y;
            missileDeliveredX = (targetX-x)/ticksTillImpact;
            missileDeliveredY = (targetY-y)/ticksTillImpact;
            firing = true;
            return true;
        } else {
            return false;
        }
        
    }
    public boolean update() {
        
        if (firing && !((Math.abs(missileX - targetX)<5) && (Math.abs(missileY - targetY)<5))) {
            missileX += missileDeliveredX;
            missileY += missileDeliveredY;
        } else if (firing) {
            firing = false;
            exploding = true;
            explosionRadius = 0;
            return true;
        } if (exploding && explosionRadius < radius) {
            explosionRadius++;
        } else if (exploding) {
            exploding = false;
        }
        return false;
    }
    
    public boolean collides(EnemyMissile e) {
        if (exploding) {
            Rectangle explosion = new Rectangle((int)missileX - explosionRadius,(int)missileY-explosionRadius,explosionRadius*2,explosionRadius*2);
            if (explosion.contains(e.getPos())) {
                return true; 
            } 
            return false;
        }
        return false;
    }
    public boolean collidesB(EnemyMissile e) {
        if ((Math.sqrt((e.getCY() - y) * (e.getCY() - y) + (e.getCX() - x) * (e.getCX() - x))) <= 5)
        {
            kill();
            return true;
          }
        return false;
    }
    public boolean collides(Rectangle plane) {
        if (exploding) {
            Rectangle explosion = new Rectangle((int)missileX - explosionRadius,(int)missileY-explosionRadius,explosionRadius*2,explosionRadius*2);
            if (explosion.intersects(plane)) {
                return true;
            }
        }
        return false;
    }
        
        
    public void draw(Graphics g) {
        if (standing) {
            g.setColor(Color.YELLOW);
            g.fillOval(x-radius*2,y-radius,radius*2,radius*2);
            g.setColor(Color.BLACK);
            g.drawString("" + missiles,x-5,y+10);
        }
        if (firing) {
            g.setColor(Color.BLUE);
            g.drawLine(x,y,(int)missileX,(int)missileY);
        } else if (exploding) {
            g.setColor(Color.BLUE);
            g.fillOval((int)missileX-explosionRadius,(int)missileY-explosionRadius,explosionRadius*2,explosionRadius*2);
        }
    }
    

	

    
   
}
