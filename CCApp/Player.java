/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/2/2016
 */
import java.awt.*;
import java.util.*;

/**
 * The Player class holds the key information for a player on the field.
 */
public class Player
{
  String name;
  ArrayList<Point> positions; // Store positions of player in each frame. Indicies of array = frames.
  ArrayList<Boolean> ballPositions;
  int currentX, currentY, speed;
  Color color;
  int number;
  int diameter;
  double velocityX;
  double velocityY;
  boolean hasBall;

  /**
   * Constructor for objects of class Player which simply takes in x
   * and y coordinates denoting its position.
   */
  public Player(int x, int y)
  {
    name = "";
    positions = new ArrayList<Point>();
    positions.add(new Point(x, y));
    ballPositions = new ArrayList<Boolean>();
    ballPositions.add(false);
    currentX = x;
    currentY = y;
    speed = 0;
    color = Color.white;
    number = -1;
    diameter = 30;
    velocityX = 0;
    velocityY = 0;
    hasBall = false;
  }

  /**
   * Constructor for objects of class Player which in addition to x
   * and y coordinates also take in other variables including name, number,
   * color and diameter..
   */
  public Player(int x, int y, String playerName, int playerNumber, Color playerColor, int playerDiameter)
  {
    positions = new ArrayList<Point>();
    positions.add(new Point(x, y));
    currentX = x;
    currentY = y;
    name = playerName;
    number = playerNumber;
    color = playerColor;
    diameter = playerDiameter;
    speed = 0;
    hasBall = false;
    ballPositions.add(false);
  }

  /**
   * Function which will get the player's name.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Function which will retrieve the player's position at a particular frame.
   */
  public Point getPositionAtFrame(int frame)
  {
    if(frame < positions.size())
    {
      Point positionAtFrame = positions.get(frame);
      return new Point(positionAtFrame);
    }
    return null;
  }

  /**
   * Function which will get the player's x coordinate on the field.
   */
  public int getX()
  {
    return currentX;
  }

  /**
   * Function which will get the player's y coordinate on the field.
   */
  public int getY()
  {
    return currentY;
  }

  /**
   * Function which will return a point object consisting of the player's current position on the field.
   */
  public Point getXY()
  {
    return new Point(currentX, currentY);
  }

  /**
   * Function which will retrieve the velocity of the player moving in the x axis.
   */
  public double getVelX()
  {
    return velocityX;
  }

  /**
   * Function which will retrieve the velocity of the player moving in the y axis.
   */
  public double getVelY()
  {
    return velocityY;
  }

  /**
   * Function which will retrieve the diameter of the player.
   */
  public int getDiameter()
  {
    return diameter;
  }

  /**
   * Function which will retrieve the speed of the player.
   */
  public int getSpeed()
  {
      return speed;
  }

  /**
   * Function which will retrieve the jersey color of the player.
   */
  public Color getColor()
  {
      return color;
  }

  /**
   * Function which will retrieve the jersey number of the player.
   */
  public int getNumber()
  {
      return number;
  }
  
  public boolean hasBall()
  {
      return hasBall;
  }
  
  public boolean hasBallAtFrame(int frame)
  {
      return ballPositions.get(frame);
  }

  /**
   * Function which will add a new frame/position to the player
   * using the position from its last frame
   */
  public void addFrame()
  {
    if(positions.size() > 0)
    {
      Point lastFramePosition = positions.get(positions.size() - 1);
      positions.add(new Point(lastFramePosition));
    }
    else
    {
      // if no frames, then add a new frame/postion using point (0,0)
      positions.add(new Point(0,0));
    }
    ballPositions.add(hasBall);
  }

  /**
   * Function which will add a new frame/position to the player
   * using the given Point obkect passed in.
   */
  public void addFrame(Point position)
  {
    positions.add(new Point(position));
    ballPositions.add(hasBall);
  }

  /**
   * Function which will add a new frame/position to the player
   * using the x and y coordinates passed in.
   */
  public void addFrame(int x, int y)
  {
    positions.add(new Point(x, y));
    ballPositions.add(hasBall);
  }

  /**
   * Function which will remove the last frame from the player.
   */
  public void removeLastFrame()
  {
    if(positions.size() > 0)
    {
      positions.remove(positions.size() - 1);
      ballPositions.remove(ballPositions.size() - 1);
    }
  }

  /**
   * Function which will set the player's position at a particular frame
   * given a frame and a Point object which contains the coordinates.
   */
  public void setPositionAtFrame(int frame, Point position)
  {
    if(frame < positions.size())
    {
      Point positionAtFrame = positions.get(frame);
      positionAtFrame.setX(position.getX());
      positionAtFrame.setY(position.getY());
    }
  }

  /**
   * Function which will set the player's position at a particular frame
   * given a frame and the x and y coordinates.
   */
  public void setPositionAtFrame(int frame, int x, int y)
  {
    if(frame < positions.size())
    {
      Point positionAtFrame = positions.get(frame);
      positionAtFrame.setX(x);
      positionAtFrame.setY(y);
    }
  }

  /**
   * Function which will set the x coordinate of the player.
   */
  public void setX(int x)
  {
    currentX = x;
  }

  /**
   * Function which will set the y coordinate of the player.
   */
  public void setY(int y)
  {
    currentY = y;
  }

  /**
   * Function which will set the player's position taking in a Point object.
   */
  public void setXY(Point pos)
  {
    currentX = pos.getX();
    currentY = pos.getY();
  }

  /**
   * Function which will set the player's velocity.
   */
  public void setVel(double x, double y)
  {
    velocityX = x;
    velocityY = y;
  }

  /**
   * Function which will set the player's jersey number.
   */
  public void setNumber(int playerNumber)
  {
    number = playerNumber;
  }

  /**
   * Function which will set the player's jersey color.
   */
  public void setColor(Color playerColor)
  {
    color = playerColor;
  }

  /**
   * Function which will set the player's diameter.
   */
  public void setDiameter(int playerDiameter)
  {
    diameter = playerDiameter;
  }

  /**
   * Function which will set the player's speed.
   */
  public void setSpeed(int s)
  {
    if(s >= 1 && s <= 3)
    {
      speed = s;
    }
  }
  
  public void setBall(boolean b)
  {
      hasBall = b;
  }
  
  public void setBallAtFrame(int frame,boolean b)
  {
      if(frame < ballPositions.size())
      {
        ballPositions.set(frame,b);      
      }
  }
}
