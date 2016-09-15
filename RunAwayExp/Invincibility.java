
/**
 * Author: Timothy Castiglia
 */
import java.util.*;
import java.awt.*;
import java.applet.*;
public class Invincibility
{
    int x;
    int y;
    int diameter;
    int radius;
    int centerX;
    int centerY;
    Random D = new Random();
    Graphics g;
    public Invincibility()
    {
        x = D.nextInt(750) + 10;
        y = D.nextInt(650) + 10;
        diameter = 20;
        radius = diameter / 2;
        centerX = x + radius;
        centerY = y + radius;
    }
    public Invincibility(int X, int Y, int D)
    {
        x = X;
        y = Y;
        diameter = D;
        radius = diameter / 2;
        centerX = x + radius;
        centerY = y + radius;
    }
    public int getX()
   {
      return x;
   } 
    public int getY()
    {
       return y; 
    }
    public void drawInv()
    {
        g.setColor(Color.green);
        g.fillOval(x,y,diameter,diameter);
        g.setFont(new Font("Arial Black",70,15));
        g.drawString("+*",x,y);
    }
    public boolean collision(Ball A)
   { 
     boolean hit = false;
     
     int centXA = A.getCenterX();
     int centYA = A.getCenterY();
     
     int xSq = (centXA - centerX)*(centXA - centerX);
     int ySq = (centYA - centerY)*(centYA - centerY);
     
     double distance = Math.sqrt(xSq + ySq);
     
     int radiusA = A.getRadius();
     
     if(radiusA + radius >= distance)
     {
         hit = true;
         centerX = -1000;
     }
     return hit;
   }
    public void paint(Graphics gr)
  {
     g = gr; 
     drawInv(); 
  }
}
