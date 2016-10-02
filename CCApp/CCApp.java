/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 9/16/2016
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class CCApp extends Applet implements Runnable, MouseListener, MouseMotionListener
{
  Thread runner;
  Image Buffer;
  Graphics gBuffer;
  int width, height;
  boolean rightKey, leftKey, upKey, downKey;
  Image footballField;
  Image football;
  double ONE_YARD;
  int CENTER_OF_FIELD;
  
  int x, y;
  int mx, my;  // the most recently recorded mouse coordinates
  boolean isMouseDraggingBox = false;

  public void init()
  {
    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //width = (int)screenSize.getWidth();
    //height = (int)screenSize.getHeight();
    width=this.getSize().width;
    height=this.getSize().height;
    Buffer=createImage(width,height);
    gBuffer=Buffer.getGraphics();
    addKeyListener(new MyKeyListener());
    footballField = getImage(getCodeBase(), "football2.jpg");
    football = getImage(getCodeBase(), "footballcloseup.png");
    ONE_YARD = height/20;
    CENTER_OF_FIELD = width/2;
    y = (int)(ONE_YARD*15);
    x = width/2;
    
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  private class MyKeyListener extends KeyAdapter
  {
    public void keyPressed(KeyEvent e)
    {
      switch (e.getKeyCode())
      {
        case KeyEvent.VK_A:
        leftKey = true;
        break;
        case KeyEvent.VK_D:
        rightKey = true;
        break;
        case KeyEvent.VK_W:
        upKey = true;
        break;
        case KeyEvent.VK_S:
        downKey = true;
        break;
        case KeyEvent.VK_LEFT:
        leftKey = true;
        break;
        case KeyEvent.VK_RIGHT:
        rightKey = true;
        break;
        case KeyEvent.VK_UP:
        upKey = true;
        break;
        case KeyEvent.VK_DOWN:
        downKey = true;
        break;
      }
    }

    public void keyReleased(KeyEvent e)
    {
      switch (e.getKeyCode())
      {
        case KeyEvent.VK_A:
        leftKey = false;
        break;
        case KeyEvent.VK_D:
        rightKey = false;
        break;
        case KeyEvent.VK_W:
        upKey = false;
        break;
        case KeyEvent.VK_S:
        downKey = false;
        break;
        case KeyEvent.VK_LEFT:
        leftKey = false;
        break;
        case KeyEvent.VK_RIGHT:
        rightKey = false;
        break;
        case KeyEvent.VK_UP:
        upKey = false;
        break;
        case KeyEvent.VK_DOWN:
        downKey = false;
        break;
      }
    }
  }

  public void start()
  {
    if (runner == null)
    {
      runner = new Thread (this);
      runner.start();
    }
  }

  public void stop()
  {
    if (runner != null)
    {
      runner.stop();
      runner = null;
    }
  }

  public boolean mouseDown(Event evt,int x,int y)
  {
    return true;
  }

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mouseClicked(MouseEvent e) {}

  public void mousePressed(MouseEvent e)
  {
    mx = e.getX();
    my = e.getY();
    if (x < mx && mx < x+30 && y < my && my < y+30)
    {
      isMouseDraggingBox = true;
    }
    e.consume();
  }

  public void mouseReleased(MouseEvent e)
  {
    isMouseDraggingBox = false;
    e.consume();
  }

  public void mouseMoved(MouseEvent e) {}

  public void mouseDragged(MouseEvent e)
  {
    if (isMouseDraggingBox)
    {
      // get the latest mouse position
      int new_mx = e.getX();
      int new_my = e.getY();

      // displace the box by the distance the mouse moved since the last event
      x += new_mx - mx;
      y += new_my - my;

      // update our data
      mx = new_mx;
      my = new_my;

      repaint();
      e.consume();
    }
  }
  
  public void paintField(Graphics gBuffer)
  {
      gBuffer.setColor(Color.white);
      for(int i = 0; i <= 20; i++)
      {
          if(i % 5 == 0)
          {
              gBuffer.drawLine(0,i*(int)ONE_YARD,width,i*(int)ONE_YARD);
          }else{
              gBuffer.drawLine(0,i*(int)ONE_YARD,10,i*(int)ONE_YARD);
              gBuffer.drawLine(width-10,i*(int)ONE_YARD,width,i*(int)ONE_YARD);
              gBuffer.drawLine((int)(CENTER_OF_FIELD-ONE_YARD*(12.5/3)),i*(int)ONE_YARD,(int)(CENTER_OF_FIELD-ONE_YARD*(11.5/3)),i*(int)ONE_YARD);
              gBuffer.drawLine((int)(CENTER_OF_FIELD+ONE_YARD*(11.5/3)),i*(int)ONE_YARD,(int)(CENTER_OF_FIELD+ONE_YARD*(12.5/3)),i*(int)ONE_YARD);
          }
      }
  }

  public void run()
  {
    while(true)
    {
      try {runner.sleep(13);}
      catch (Exception e) {}
      gBuffer.setColor(new Color(25,150,10));
      gBuffer.fillRect(0,0,width,height);
      //gBuffer.drawImage(football, x, y, this);
      
      paintField(gBuffer);
      
      gBuffer.setColor(Color.blue);
      gBuffer.fillOval(x,y,20,20);
      
      repaint();
    }
  }

  public void update(Graphics g)
  {
    paint(g);
  }

  public void paint(Graphics g)
  {
    g.drawImage (Buffer,0,0, this);
  }
}
