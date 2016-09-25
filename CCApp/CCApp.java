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

public class CCApp extends Applet implements Runnable
{
    Thread runner;
    Image Buffer;
    Graphics gBuffer;
    int width, height;
    boolean rightKey, leftKey, upKey, downKey;
    Image football;
   public void init()
   {   
        width=this.getSize().width;
        height=this.getSize().height;
        Buffer=createImage(width,height);
        gBuffer=Buffer.getGraphics();
        addKeyListener(new MyKeyListener());
        football = getImage(getCodeBase(), "football.jpg");
   }
   private class MyKeyListener extends KeyAdapter{
       public void keyPressed(KeyEvent e){
           switch(e.getKeyCode()){
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
        public void keyReleased(KeyEvent e){
           switch(e.getKeyCode()){
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
    { if (runner == null)
        {   runner = new Thread (this);
            runner.start();    }    }
  
  public void stop()
    {  if (runner != null)
        {   runner.stop();
            runner = null;        }   }
  
 public boolean mouseDown(Event evt,int x,int y)
    {  return true;    }
    public void run()
    {
        while(true)
        {
            try {runner.sleep(13);}
            catch (Exception e) { }
            gBuffer.setColor(Color.black);
            gBuffer.fillRect(0,0,width,height);
            gBuffer.drawImage(football, 0, 0, this);
            repaint();  
        } 
    }
  public void update(Graphics g)
    {        paint(g);   
    }
  
  public void paint(Graphics g)
    {        g.drawImage (Buffer,0,0, this);    }
}
