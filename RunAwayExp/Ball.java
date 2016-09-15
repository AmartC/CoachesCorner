//Author: Timothy Castiglia
import java.util.*;
import java.awt.*;
import java.applet.*;
public class Ball
{
   int x,y,moveX,moveY,diameter,centerX,centerY,radius;
    int c;
    Graphics g;
    Random D = new Random();


    public Ball()
    {
      x = D.nextInt(700) + 50;
      y = D.nextInt(600) + 50;
      diameter = 20;
      radius = diameter / 2;
      centerX = x + radius;
      centerY = y + radius;
      moveX = D.nextInt(4) + 1;
      moveY = D.nextInt(4) + 1;
      c = D.nextInt(4);
      
    }
    public Ball(int X, int Y, int d, int xs, int ys, int C)
    {
      x = X;
      y = Y;
      diameter = d;
      radius = diameter / 2;
      centerX = x + radius;
      centerY = y + radius;
      moveX = xs;
      moveY = ys; 
      c = C;
    }
    public void changeDir()
    {
        moveX = moveX *-1;
        moveY = moveY *-1;   
    }
    public void changeXDir()
    {
        moveX = moveX *-1;      
    }
      
   public void changeYDir()
   {
       moveY = moveY *-1;
   }
   public int getX()
   {
      return x;
   } 
    public int getY()
    {
       return y; 
    }  
    public int getRadius()
    {
       return radius;
    }
    public int getDiameter()
    {
       return diameter;
    }
    public int getCenterX()
    {
        return centerX;
    }
    public int getCenterY()
    {
        return centerY;
    }
    public void drawBall()
    {
       if(c == 0)
          g.setColor(Color.red);
       if(c == 1)
          g.setColor(Color.green);
       if(c == 2)
         g.setColor(Color.gray);
       if(c == 3)
         g.setColor(Color.magenta);
       if(c == 4)
         g.setColor(Color.blue);
       g.fillOval(x,y,diameter,diameter);
    }
    public void moveBall(int w, int h)
    {
       x = x + moveX;
       y = y + moveY;
       centerX = x + radius;
       centerY = y + radius;
       
   if(( x >= w - diameter)|| (x <= 0))
   {
       changeXDir();
   }
   if(y <= 0)
   {
       changeYDir();
   }
   if(y >= h - diameter)
   {
       changeYDir();
    }
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
         changeDir();
         moveBall(800, 700);
         A.changeDir();
         A.moveBall(800, 700);
         hit = true;
     }
     return hit;
   }
public void moveUp()
{
    y = y - 3;
     centerY = y + radius;
}
public void moveDown()
{
    y = y + 3;
     centerY = y + radius;
}
public void moveLeft()
{
    x = x - 3;
    centerX = x + radius;
}
public void moveRight()
{
    x = x + 3;
    centerX = x + radius;
}
public boolean checkBound(int w, int h)
{
    boolean t = false;
    if(( x >= w - diameter)|| (x <= 0))
   {
       t = true;
   }
   if(y <= 0)
   {
       t = true;
   }
   if(y >= h - diameter)
   {
       t = true;
    } 
    return t;
}
 public void paint(Graphics gr)
  {
     g = gr; 
     drawBall(); 
    }
}
