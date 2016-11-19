/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/22/2016
 */

 /**
  * The Point class is used to store the coordinates of the players on the
  * football field in a more organized manner.
  */
import java.io.*;

public class Point implements Serializable
{
  int x;
  int y;

  /**
   * Constructor for objects of class Point which takes in x and y coordinates
   * denoting the point.
   */
  public Point(int xPoint, int yPoint)
  {
    x = xPoint;
    y = yPoint;
  }

  /**
   * Constructor for objects of class Player which takes in another Point object.
   */
  public Point(Point other)
  {
    x = other.getX();
    y = other.getY();
  }

  /**
   * Function that will retrieve the x coordinate of the point.
   */
  public int getX()
  {
    return x;
  }

  /**
   * Function that will retrieve the y coordinate of the point.
   */
  public int getY()
  {
    return y;
  }

  /**
   * Function which will set the x coordinate of the point.
   */
  public void setX(int xPoint)
  {
    x = xPoint;
  }

  /**
   * Function which will set the y coordinate of the point.
   */
  public void setY(int yPoint)
  {
    y = yPoint;
  }

  /**
   * Function which will add two points together and return a new point of the result.
   */
  public Point add(Point other)
  {
    return new Point(this.getX() + other.getX(), this.getY() + other.getY());
  }

  /**
   * Function which will add subtract points together and return a new point of the result.
   */
  public Point subtract(Point other)
  {
    return new Point(this.getX() - other.getX(), this.getY() - other.getY());
  }

  /**
   * Function which will multiply the point by some scalar value.
   */
  public Point multiply(int magnitude)
  {
    return new Point(this.getX() * magnitude, this.getY() * magnitude);
  }
}
