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
  
  public Player(int x, int y)
  {
    xPoint = x;
    yPoint = y;
  }

  public int getX()
  {
    return xPoint;
  }

  public int getY()
  {
    return yPoint;
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
  
  
}
