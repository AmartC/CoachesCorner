/**Author: Timothy Castiglia
 * Date: 5/15/12
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;
public class RunAway extends Applet implements Runnable
{
    Thread runner;
    Image Buffer;
    Graphics gBuffer;
    int width, height, score,lives;
    Ball user;
    ArrayList B, R, R2, H, I;
    Ball tempB, tempBU;
    Rectangle tempR;
    Rectangle2 tempR2;
    String sc,liv,invinc;
    boolean rightKey, leftKey, upKey, downKey;
    int c, c1, c2, c3,c4, inv;
    Health tempH;
    Invincibility tempI;
   public void init()
   {   
        width=this.getSize().width;
        height=this.getSize().height;
        Buffer=createImage(width,height);
        gBuffer=Buffer.getGraphics();
        addKeyListener(new MyKeyListener());
        B = new ArrayList();
        R = new ArrayList();
        R2 = new ArrayList();
        H = new ArrayList();
        I = new ArrayList();
        user = new Ball(400, 300, 25, 0, 0, 4);
        score = 0;
        lives = 25;
        tempB = new Ball();
        tempBU = new Ball();
        tempR = new Rectangle();
        tempR2 = new Rectangle2();
        tempH = new Health(-100,-100,0,0);
        tempI = new Invincibility(-100,-100,0);
        c = 100;
        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        inv = 0;
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
            if(upKey){user.moveUp();}
            if(downKey){user.moveDown();}
            if(leftKey){user.moveLeft();}
            if(rightKey){user.moveRight();}
            repaint();
            try {runner.sleep(13);}
            catch (Exception e) { }
            gBuffer.setColor(Color.black);
            gBuffer.fillRect(0,0,width,height);
            for(int i = 0; i < B. size(); i++)
            {
                tempB = (Ball)B.get(i);
                tempB.moveBall(width, height);
            }
            user.paint(gBuffer);
            if(c == 100)
            {
                tempB = new Ball();
                B.add(tempB);
                c = 0;
            }
            if(c1 == 2000)
            {
                tempR = new Rectangle();
                R.add(tempR);
            }
            if(c1 == 4000)
            {
                tempR2 = new Rectangle2();
                R2.add(tempR2);
                c1 = 0;
            }
            if(c2 == 1000)
            {                
                H.remove(tempH);
            }
            if(c2 == 2500)
            {
                tempH = new Health();
                H.add(tempH);
                c2 = 0;
            }
            if(c3 == 10000)
            {
                tempI = new Invincibility();
                I.add(tempI);
                c3 = 0;
            }
            if(c3 == 1000)
            {
                I.remove(tempI);
            }
            if(lives <= 0)
            {
                gBuffer.setFont(new Font("Castellar",70,100));
                gBuffer.drawString("GAME OVER",50,400);    
            }
            for(int i = 0; i < B.size(); i++)
             {
                 for(int j = 0; j < B.size(); j++)
                 {
                     tempB = (Ball)B.get(i);
                     tempBU = (Ball)B.get(j);
                     if(tempB.collision(tempBU))
                        break;
                  }
              }
              
              for(int i = 0; i < B.size(); i++)
              {
                 tempB = (Ball)B.get(i);
                 if(tempB.collision(user))
                 {
                     if(inv <= 0)
                        lives--;
                     else
                        B.remove(tempB);   
                     break;
                    }
                }
                for(int i = 0; i < R2.size(); i++)
                {
                 tempR2 = (Rectangle2)R2.get(i);
                 if(tempR2.collision(user))
                 {
                     if(inv <= 0)
                        lives--;
                     else
                        R2.remove(tempR2);
                     break;
                    }
                }
                for(int i = 0; i < R2.size(); i++)
                {
                 tempR2 = (Rectangle2)R2.get(i);
                 for(int j = 0; j < B.size(); j++)
                 {                     
                     tempB = (Ball)B.get(j);
                     if(tempR2.collision(tempB))                     
                        break;                    
                  }
              }
                for(int i = 0; i < R.size(); i++)
                {
                 tempR = (Rectangle)R.get(i);
                 for(int j = 0; j < B.size(); j++)
                 {                     
                     tempB = (Ball)B.get(j);
                     if(tempR.collision(tempB))                     
                        break;                    
                  }
              }
              for(int i = 0; i < R.size(); i++)
              {
                 tempR = (Rectangle)R.get(i);
                 if(tempR.collision(user))
                 {
                     if(inv <= 0)
                        lives--;
                     else
                        R.remove(tempR);
                     break;
                    }
                }
              for(int i = 0; i < R.size(); i++)
              {
                 tempR = (Rectangle)R.get(i);
                 tempR.moveRect(height);
              }
              for(int i = 0; i < R2.size(); i++)
              {
                 tempR2 = (Rectangle2)R2.get(i);
                 tempR2.moveRect(width);
              }
              if((!user.checkBound(width, height)) && lives > 0)
                    score++;
              if(tempH.collision(user) && lives > 0)
              {
                  lives = lives + tempH.getH();
                  H.remove(tempH);
              }
              if(tempI.collision(user) && lives > 0)
              {    
                  inv = 500;
                  I.remove(tempI);
              }
             for(int i = 0; i < B.size(); i++)
            {
                tempB = (Ball)B.get(i);
                tempB.paint(gBuffer);
            }
            for(int i = 0; i < R.size(); i++)
            {
                tempR = (Rectangle)R.get(i);
                tempR.paint(gBuffer);
            }
            for(int i = 0; i < R2.size(); i++)
            {
                tempR2 = (Rectangle2)R2.get(i);
                tempR2.paint(gBuffer);
            }
            for(int i = 0; i < H.size(); i++)
            {
                tempH = (Health)H.get(i);
                tempH.paint(gBuffer);
            }
            for(int i = 0; i < I.size(); i++)
            {
                tempI = (Invincibility)I.get(i);
                tempI.paint(gBuffer);
            }
             liv = String.valueOf(lives);
             sc = String.valueOf(score);
             invinc = String.valueOf(inv);
             gBuffer.setColor(Color.blue);
             gBuffer.setFont(new Font("Castellar",70,50));
             gBuffer.drawString("Health: " + liv,10,650);             
             gBuffer.drawString("Score: " + sc,10,38);
             if(c4 < 200)
             {
                gBuffer.setFont(new Font("Castellar",70,100));
                gBuffer.drawString("Run Away!!!",50,175);
                gBuffer.setFont(new Font("Castellar",70,30));
                gBuffer.drawString("Use 'A','W','S','D' or Arrow Keys to Move", 65, 550);
            }
            if(c4 < 400 && c4 > 200)
            {
                gBuffer.setFont(new Font("Castellar",70,30));
                gBuffer.drawString("Avoid All Enemy Objects",200,175);
                gBuffer.drawString("Grab Health Orbs For Extra Health",75,550);
            }
            if(c4 < 650 && c4 > 400)
            {
                gBuffer.setFont(new Font("Castellar",70,30));
                gBuffer.drawString("Orbs Labeled '+*' Are Invinciblilty Orbs",40,175);
                gBuffer.drawString("Invincibility Allows You To",150,500); 
                gBuffer.drawString("Destroy Enemy Objects",200,550);
            }
            if(c4 < 850 && c4 > 650)
            {
                gBuffer.setFont(new Font("Castellar",70,30));
                gBuffer.drawString("Moving Outside the Bounderies",140,175);
                gBuffer.drawString("Will Stop the Score From Increasing",55,500); 
            }
            if(c4 < 950 && c4 > 850)
            {
                gBuffer.setFont(new Font("Castellar",70,40));
                gBuffer.drawString("Good Luck",300,350);
            }
             if(inv >= 0)
             {
                 gBuffer.setFont(new Font("Castellar",70,25));
                 gBuffer.drawString("Invincibility: " + invinc, 525, 625);
             }
             c++;
             c1++;
             c2++;
             c3++;
             c4++;
             inv--;
             repaint();  
        } 
    }
  public void update(Graphics g)
    {        paint(g);   
    }
  
  public void paint(Graphics g)
    {        g.drawImage (Buffer,0,0, this);    }
}
