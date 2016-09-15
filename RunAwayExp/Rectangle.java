/**
 * Author: Timothy Castiglia
 */
import java.awt.*;
import java.applet.*;
import java.util.*;
public class Rectangle
{
int x,y,move,length,width,xEdge,yEdge, c;
Graphics g;

Random D = new Random();

public Rectangle()
{
    x = D.nextInt(700) + 10;
    y = D.nextInt(600) + 10;
    
    length = 75;
    width = 10;
    
    move = D.nextInt(4) + 1;
    
    xEdge = x + width;
    yEdge = y + length;
    c = D.nextInt(4);
}
public Rectangle(int X, int Y, int l, int w, int m, int C)
{
   x = X;
   y = Y;
    
   length = l;
   width = w;
    
   xEdge = x + width;
   yEdge = y + length; 
   
   move = m;
   c = C;
}

public int getX()
{
    return x;
} 
public int getY()
{
    return y;
}
public int getLength()
{
    return length;
}

public int getWidth()
{
    return width;
}

public int getXEdge()
{
     return xEdge;
}

public int getYEdge()
{
     return yEdge;
}

public boolean collision(Ball A)
{
    boolean hit = false;

    int bx = A.getX();
    int by = A.getY();
    int diameter = A.getDiameter();
    int radius = diameter / 2;
    int centerX = A.getCenterX();
    int centerY = A.getCenterY();
    
    int xsq = (centerX - xEdge)*(centerX - xEdge);
    int ysq = 0;
    double distance = 0;
    
    for(int i = y; i <= yEdge; i++)
    {
        ysq = (centerY - i)*(centerY-i);
        distance = Math.sqrt(xsq+ysq);
        if(radius >= distance)
        {
            A.changeXDir();
            hit = true;
            break;
        }
    }
    
    xsq = (centerX - x)*(centerX - x);
    for(int i = y; i <= yEdge; i++)
    {
        ysq = (centerY - i)*(centerY-i);
        distance = Math.sqrt(xsq+ysq);
        if(radius >= distance)
        {
            A.changeXDir();
            hit = true;
            break;
        }
    }
    
    ysq = (centerY - y)*(centerY - y);
    for(int i = x; i <= xEdge; i++)
    {
        xsq = (centerX - i)*(centerX-i);
        distance = Math.sqrt(xsq+ysq);
        if(radius >= distance)
        {
            A.changeYDir();
            hit = true;
            break;
        }
    }
    
    ysq = (centerY - yEdge)*(centerY - yEdge);
    for(int i = x; i <= xEdge; i++)
    {
        xsq = (centerX - i)*(centerX-i);
        distance = Math.sqrt(xsq+ysq);
        if(radius >= distance)
        {
            A.changeYDir();
            hit = true;
            break;
        }
    }
    return hit;
}

public void drawRect()
{
    if(c == 0)
        g.setColor(Color.red);
    if(c == 1)
        g.setColor(Color.green);
    if(c == 2)
       g.setColor(Color.yellow);
    if(c == 3)
       g.setColor(Color.gray);

    g.fillRect(x,y,width,length);
}
public void moveRect(int h)
{
   y = y + move;
   yEdge = y + length;
   if(( y >= h - length)|| (y <= 0))
   {
       move = move*-1;
   }
}
public void paint(Graphics gr)
{
    g = gr;
    drawRect();
}
}