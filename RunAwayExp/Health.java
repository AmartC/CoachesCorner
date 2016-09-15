/**
 * Author: Timothy Castiglia
 */
import java.util.*;
import java.awt.*;
import java.applet.*;
public class Health
{
    int h;
    int x;
    int y;
    int diameter;
    int radius;
    int centerX;
    int centerY;
    Random D = new Random();
    Graphics g;
    public Health()
    {
        h = D.nextInt(75) + 1;
        x = D.nextInt(750) + 10;
        y = D.nextInt(650) + 10;
        diameter = 20;
        radius = diameter / 2;
        centerX = x + radius;
        centerY = y + radius;
    }
    public Health(int H, int X, int Y, int D)
    {
        h = H;
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
    public int getH()
    {
       return h;
    }
    public void drawHealth()
    {
        if(h >= 0 && h < 25)
          g.setColor(Color.red);
       if(h >= 50)
          g.setColor(Color.green);
       if(h >= 25 && h < 50)
         g.setColor(Color.blue);
       g.fillOval(x,y,diameter,diameter);
       String H = String.valueOf(h);
       g.setFont(new Font("Arial Black",70,15));
       g.drawString("+" + H,x,y);  
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
     drawHealth(); 
  }
}
