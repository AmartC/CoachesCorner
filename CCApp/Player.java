/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/2/2016
 */
import java.awt.*;


public class Player
{
  String name;
  Point position;
  int speed;
  Color color;
  int number;
  int diameter;

  public Player(int x, int y)
  {
    name = "";
    position = new Point(x, y);
    speed = 0;
    color = Color.white;
    number = -1;
    diameter = 30;
  }

  public Player(int x, int y, String playerName, int playerNumber, Color playerColor, int playerDiameter)
  {
    position = new Point(x, y);
    name = playerName;
    number = playerNumber;
    color = playerColor;
    diameter = playerDiameter;
    speed = 0;
  }

  public String getName()
  {
    return name;
  }

  public int getX()
  {
    return position.getX();
  }

  public int getY()
  {
    return position.getY();
  }

  public Point getXY()
  {
    return new Point(position);
  }

  public int getDiameter()
  {
    return diameter;
  }

  public int getSpeed()
  {
      return speed;
  }

  public Color getColor()
  {
      return color;
  }

  public int getNumber()
  {
      return number;
  }

  public void setX(int x)
  {
    position.setX(x);
  }

  public void setY(int y)
  {
    position.setY(y);
  }

  public void setNumber(int playerNumber)
  {
    number = playerNumber;
  }

  public void setColor(Color playerColor)
  {
    color = playerColor;
  }

  public void setDiameter(int playerDiameter)
  {
    diameter = playerDiameter;
  }

  // Precondition: 1 <= s <= 3
  public void setSpeed(int s)
  {
      speed = s;
  }
}
