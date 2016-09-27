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
    double ONE_YARD;
    int FIFTY_YARD_LINE;
   public void init()
   {   
        width=this.getSize().width;
        height=this.getSize().height;
        Buffer=createImage(width,height);
        gBuffer=Buffer.getGraphics();
        addKeyListener(new MyKeyListener());
        football = getImage(getCodeBase(), "football2.jpg");
        ONE_YARD = 12.25;
        FIFTY_YARD_LINE = 636;
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
            gBuffer.drawImage(football, 0, 0, this);
            gBuffer.setColor(Color.blue);
            gBuffer.fillOval(600,300,30,30);
            gBuffer.drawLine(FIFTY_YARD_LINE,0,FIFTY_YARD_LINE,700);
            
            gBuffer.setColor(Color.red);
            gBuffer.drawLine((int)(FIFTY_YARD_LINE+ONE_YARD),0,(int)(FIFTY_YARD_LINE+ONE_YARD),700);
            gBuffer.drawLine((int)(FIFTY_YARD_LINE+5*ONE_YARD),0,(int)(FIFTY_YARD_LINE+5*ONE_YARD),700);
            gBuffer.drawLine((int)(FIFTY_YARD_LINE+20*ONE_YARD),0,(int)(FIFTY_YARD_LINE+20*ONE_YARD),700);
            gBuffer.drawLine((int)(FIFTY_YARD_LINE+50*ONE_YARD),0,(int)(FIFTY_YARD_LINE+50*ONE_YARD),700);
            repaint();  
        } 
    }
  public void update(Graphics g)
    {        paint(g);   
    }
  
  public void paint(Graphics g)
    {        g.drawImage (Buffer,0,0, this);    }
}
