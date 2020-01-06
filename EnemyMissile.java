import java.util.Random;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EnemyMissile
{
    // instance variables - replace the example below with your own
    private int endX,endY;
    private int isExplosion, isAlive;
    private HeroMissile _missile;
    
    /**
     * Constructor for objects of class eMissile
     */
    public EnemyMissile(int x,int y,int x2, int x3)
    {
        endX= x;
        endY= y;
        isExplosion = 0;
        isAlive = 0;
        _missile = new HeroMissile(x,y,x2,x3);
        
    }
    public boolean isAtTarget(){
        if (_missile.distance <=5){
            return true;
        }
        else{return false;}
    }
    public Point getPos(){
        return (new Point((int)_missile._currentX,(int)_missile._currentY));
    }
    public void moveForward(){
        _missile.moveForward();
    }
    public double getCX(){
        return _missile._currentX;
    }
    public double getCY(){
        return _missile._currentY;
    }
    public double getSX(){
        return _missile._startingX;
    }
    public double getSY(){
        return _missile._startingY;
    }
    public double getDistanceFromTarget(){
        return _missile.getDistanceFromTarget();
    }
    
}
