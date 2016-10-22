/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/22/2016
 */

public class Point
{
  int x;
  int y;

  public Point(int xPoint, int yPoint)
  {
    x = xPoint;
    y = yPoint;
  }

  public Point(Point other)
  {
    x = other.getX();
    y = other.getY();
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public void setX(int xPoint)
  {
    x = xPoint;
  }

  public void setY(int yPoint)
  {
    y = yPoint;
  }

  public Point add(Point other)
  {
    return new Point(this.getX() + other.getX(), this.getY() + other.getY());
  }

  public Point subtract(Point other)
  {
    return new Point(this.getX() - other.getX(), this.getY() - other.getY());
  }

  public Point multiply(int magnitude)
  {
    return new Point(this.getX() * magnitude, this.getY() * magnitude);
  }
}
