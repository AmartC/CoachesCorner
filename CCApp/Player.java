/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/2/2016
 */

public class Player
{
  int xPoint;
  int yPoint;
  int speed;
  String color;
  int number;
  int diameter;
  
  public Player(int x, int y)
  {
    xPoint = x;
    yPoint = y;
    speed = 0;
    diameter = 20;
  }

  public int getX()
  {
    return xPoint;
  }

  public int getY()
  {
    return yPoint;
  }

  public int getDiameter()
  {
    return diameter;
  }

  public void setX(int xPoint)
  {
    x = xPoint;
  }

  public void setY(int yPoint)
  {
    y = yPoint;
  }

  public void setDiameter(int playerDiameter)
  {
    diameter = playerDiameter;
  }
  
  public int getSpeed()
  {
      return speed;
  }
  
  public String getColor()
  {
      return color;
  }
  
  public int getNum()
  {
      return number;
  }
  
  // Precondition: 1 <= s <= 3
  public void setSpeed(int s)
  {
      speed = s;
  }
}
