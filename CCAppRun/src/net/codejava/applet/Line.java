
/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/23/2016
 */

 /**
  * The Line class is used to assist with the implementation of Whiteboard mode.
  * It holds the two endpoints of a line.
  */
public class Line
{
    private Point start;
    private Point end;

    /**
     * Constructor for objects of class Line
     */
    public Line(Point s, Point e)
    {
      start = s;
      end = e;
    }

    /**
     * Function which will retrieve the start point of the line object.
     */
    public Point getStart(){
      return start;
    }

    /**
     * Function which will retrieve the end point of the line object.
     */
    public Point getEnd(){
      return end;
    }
}
