import java.awt.geom.*;
import java.awt.geom.Line2D;


public class HeroMissile
{
    // instance variables - replace the example below with your own
    public double _startingX, _startingY,_endPointX,_endPointY,_currentX,_currentY,distance, movingDistance,remainingDistance;
    

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public HeroMissile(int x,int y, int a, int b)
    {
        _startingX = x;
        _startingY = y;
        _endPointX = a;
        _endPointY = b;
        _currentX = _startingX;
        _currentY = _startingY;
        movingDistance = 1;
        distance = calcDistance(_startingX, _startingY,_endPointX,_endPointY);
        remainingDistance = 0;
    }
    public void setCurrent(int x, int y)
    {
        _currentX = x;
        _currentY = y;
    }
    public double calcDistance(double x1,double y1,double x2, double y2)
    {
        return Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));
    }
    public double calcPDistance(double x1,double y1)
    {
        return Math.sqrt(Math.pow((_currentX-x1),2)+Math.pow((_currentY-y1),2));
    }
    public void moveForward()
    {
        double temp = movingDistance/distance;
        distance = distance - movingDistance;
        _currentX = (((1-temp)*_currentX)+(temp*_endPointX));
        _currentY = (((1-temp)*_currentY)+(temp*_endPointY));
    }
    public double getDistanceFromTarget(){
        return distance;
    }
    
}
