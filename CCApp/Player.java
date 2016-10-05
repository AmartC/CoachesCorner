/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/2/2016
 */

public class Player
{
  int x;
  int y;
  int diameter;

  public Player(int xPoint, int yPoint, int playerDiameter)
  {
    x = xPoint;
    y = yPoint;
    diameter = playerDiameter;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
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
}
