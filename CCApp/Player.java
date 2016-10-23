/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/2/2016
 */
import java.awt.*;
import java.util.*;


public class Player
{
  String name;
  //Point position;
  ArrayList<Point> positions; // Store positions of player in each frame. Indicies of array = frames.
  int speed;
  Color color;
  int number;
  int diameter;

  public Player(int x, int y)
  {
    name = "";
    //position = new Point(x, y);
    positions = new ArrayList<Point>();
    positions.add(new Point(x, y));
    speed = 0;
    color = Color.white;
    number = -1;
    diameter = 30;
  }

  public Player(int x, int y, String playerName, int playerNumber, Color playerColor, int playerDiameter)
  {
    //position = new Point(x, y);
    positions = new ArrayList<Point>();
    positions.add(new Point(x, y));
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

  public Point getPositionAtFrame(int frame)
  {
    if(frame < positions.size())
    {
      Point positionAtFrame = positions.get(frame);
      return new Point(positionAtFrame);
    }
    return null;
  }

  public int getX()
  {
    return (positions.get(0)).getX();
  }

  public int getY()
  {
    return (positions.get(0)).getY();
  }

  public Point getXY()
  {
    return new Point(positions.get(0));
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

  // Adds a new frame to player using given position
  public void addFrame(Point position)
  {
    positions.add(new Point(position));
  }

  public void addFrame(int x, int y)
  {
    positions.add(new Point(x, y));
  }

  public void setPositionAtFrame(int frame, Point position)
  {
    if(frame < positions.size())
    {
      Point positionAtFrame = positions.get(frame);
      positionAtFrame.setX(position.getX());
      positionAtFrame.setY(position.getY());
    }
  }

  public void setPositionAtFrame(int frame, int x, int y)
  {
    if(frame < positions.size())
    {
      Point positionAtFrame = positions.get(frame);
      positionAtFrame.setX(x);
      positionAtFrame.setY(y);
    }
  }

  public void setX(int x)
  {
    (positions.get(0)).setX(x);
  }

  public void setY(int y)
  {
    (positions.get(0)).setY(y);
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
