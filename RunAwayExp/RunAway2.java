/**Author: Timothy Castiglia
 * Date: 5/15/12
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;
public class RunAway2 extends Applet implements Runnable
{
    Thread runner;
    Image Buffer;
    Graphics gBuffer;
    int width, height;
    int score,lives,score2,lives2;
    int rx, ry;
    Ball user, user2;
    ArrayList B;
    ArrayList R;
    ArrayList R2;
    ArrayList H;
    ArrayList I;
    Ball tempB, tempBU;
    Rectangle tempR;
    Rectangle2 tempR2;
    String sc,liv,invinc,sc2,liv2,invinc2;
    boolean rightKey,rightKey2;
    boolean leftKey, leftKey2;
    boolean upKey,upKey2;
    boolean downKey,downKey2;
    int c, c1, c2, c3, c4, inv, inv2;
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
        user = new Ball(300, 300, 25, 0, 0, 4);
        user2 = new Ball(500, 300, 25, 0, 0, 4);
        score = 0;
        lives = 25;
        score2 = 0;
        lives2 = 25;
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
        inv2 = 0;
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
               leftKey2 = true;
               break;
               case KeyEvent.VK_RIGHT:
               rightKey2 = true;
               break;
               case KeyEvent.VK_UP:
               upKey2 = true;
               break;
               case KeyEvent.VK_DOWN:
               downKey2 = true;
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
               leftKey2 = false;
               break;
               case KeyEvent.VK_RIGHT:
               rightKey2 = false;
               break;
               case KeyEvent.VK_UP:
               upKey2 = false;
               break;
               case KeyEvent.VK_DOWN:
               downKey2 = false;
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
            if(upKey2){user2.moveUp();}
            if(downKey2){user2.moveDown();}
            if(leftKey2){user2.moveLeft();}
            if(rightKey2){user2.moveRight();}
            repaint();
            try {runner.sleep(15);}
            catch (Exception e) { }
            gBuffer.setColor(Color.black);
            gBuffer.fillRect(0,0,width,height);
            for(int i = 0; i < B. size(); i++)
            {
                tempB = (Ball)B.get(i);
                tempB.moveBall(width, height);
            }
            user.paint(gBuffer);
            user2.paint(gBuffer);
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
            if(lives <= 0 && lives2 <= 0)
            {
                gBuffer.setFont(new Font("Castellar",70,100));
                gBuffer.drawString("GAME OVER",50,250);
                gBuffer.setFont(new Font("Castellar",70,75));
                if(score > score2)
                {
                    gBuffer.drawString("Player 1 Wins!",50,450);
                }else{
                    gBuffer.drawString("Player 2 Wins!",50,450);
                }
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
                 if(lives > 0)
                 {
                 if(tempB.collision(user))
                 {
                     if(inv <= 0)
                        lives--;
                     else
                        B.remove(tempB);   
                     break;
                    }
                }
                }
                for(int i = 0; i < B.size(); i++)
              {
                 tempB = (Ball)B.get(i);
                 if(lives2 > 0)
                 {
                 if(tempB.collision(user2))
                 {
                     if(inv2 <= 0)
                        lives2--;
                     else
                        B.remove(tempB);   
                     break;
                    }
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
                 if(tempR2.collision(user2))
                 {
                     if(inv2 <= 0)
                        lives2--;
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
                 if(tempR.collision(user2))
                 {
                     if(inv2 <= 0)
                        lives2--;
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
              if((!user2.checkBound(width, height)) && lives2 > 0)
                    score2++;
              if(lives > 0)
              {
                if(tempH.collision(user))
                {
                    lives = lives + tempH.getH();
                    H.remove(tempH);
                }
              }
              if(lives2 > 0)
              {
              if(tempH.collision(user2))
              {
                  lives2 = lives2 + tempH.getH();
                  H.remove(tempH);
              }
              }
              if(lives > 0)
              {
              if(tempI.collision(user))
              {    
                  inv = 500;
                  I.remove(tempI);
              }
              }
              if(lives2 > 0)
              {
              if(tempI.collision(user2))
              {    
                  inv2 = 500;
                  I.remove(tempI);
              }
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
             liv2 = String.valueOf(lives2);
             sc2 = String.valueOf(score2);
             invinc2 = String.valueOf(inv2);
             gBuffer.setColor(Color.blue);
             gBuffer.setFont(new Font("Castellar",70,30));
             gBuffer.drawString("Health 1: " + liv,10,68);             
             gBuffer.drawString("Score 1: " + sc,10,38);
             gBuffer.drawString("Health 2: " + liv2,10,630);             
             gBuffer.drawString("Score 2: " + sc2,10,660);
             if(c4 < 200)
             {
                gBuffer.setFont(new Font("Castellar",70,100));
                gBuffer.drawString("Run Away!!!",50,175);
                gBuffer.setFont(new Font("Castellar",70,30));
                gBuffer.drawString("Use 'A','W','S','D' To Move Player 1", 150, 550);
                gBuffer.drawString("Use Arrow Keys to Move Player 2", 115, 580);
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
                 gBuffer.drawString("Invincibility 1: " + invinc, 500, 38);
             }
             if(inv2 >= 0)
             {
                 gBuffer.setFont(new Font("Castellar",70,25));
                 gBuffer.drawString("Invincibility 2: " + invinc2, 500, 625);
             }
             c++;
             c1++;
             c2++;
             c3++;
             c4++;
             inv--;
             inv2--;
             repaint();  
        } 
    }
  public void update(Graphics g)
    {        paint(g);   
    }
  
  public void paint(Graphics g)
    {        g.drawImage (Buffer,0,0, this);    }
}
