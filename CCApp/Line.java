
/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/23/2016
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

    public Point getStart(){
      return start;
    }
    
    public Point getEnd(){
      return end;
    }
}
