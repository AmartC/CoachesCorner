/**Author: Timothy Castiglia
 * Date: 5/15/12
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.applet.*;
public class RunAway4 extends Applet implements Runnable
{
    Thread runner;
    Image Buffer;
    Graphics gBuffer;
    int width, height;
    int score,lives,score2,lives2,score3,lives3,score4,lives4;
    int rx, ry;
    Ball user, user2, user3, user4;
    ArrayList B;
    ArrayList R;
    ArrayList R2;
    ArrayList H;
    ArrayList I;
    Ball tempB, tempBU;
    Rectangle tempR;
    Rectangle2 tempR2;
    String sc,liv,invinc,sc2,liv2,invinc2,sc3,liv3,invinc3,sc4,liv4,invinc4;
    boolean rightKey,rightKey2,rightKey3,rightKey4;
    boolean leftKey, leftKey2, leftKey3, leftKey4;
    boolean upKey,upKey2,upKey3,upKey4;
    boolean downKey,downKey2,downKey3,downKey4;
    int c, c1, c2, c3, c4, inv, inv2, inv3, inv4;
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
        user2 = new Ball(300, 400, 25, 0, 0, 4);
        user3 = new Ball(500, 300, 25, 0, 0, 4);
        user4 = new Ball(500, 400, 25, 0, 0, 4);
        score = 0;
        lives = 25;
        score2 = 0;
        lives2 = 25;
        score3 = 0;
        lives3 = 25;
        score4 = 0;
        lives4 = 25;
        tempB = new Ball();
        tempBU = new Ball();
        tempR = new Rectangle();
        tempR2 = new Rectangle2();
        tempH = new Health(-100,-100,0,0);
        tempI = new Invincibility(-100,-100,0);
        c = 500;
        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        inv = 0;
        inv2 = 0;
        inv3 = 0;
        inv4 = 0;
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
               case KeyEvent.VK_G:
               leftKey3 = true;
               break;
               case KeyEvent.VK_J:
               rightKey3 = true;
               break;
               case KeyEvent.VK_Y:
               upKey3 = true;
               break;
               case KeyEvent.VK_H:
               downKey3 = true;
               break;
               case KeyEvent.VK_L:
               leftKey4 = true;
               break;
               case KeyEvent.VK_QUOTE:
               rightKey4 = true;
               break;
               case KeyEvent.VK_P:
               upKey4 = true;
               break;
               case KeyEvent.VK_SEMICOLON:
               downKey4 = true;
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
               case KeyEvent.VK_G:
               leftKey3 = false;
               break;
               case KeyEvent.VK_J:
               rightKey3 = false;
               break;
               case KeyEvent.VK_Y:
               upKey3 = false;
               break;
               case KeyEvent.VK_H:
               downKey3 = false;
               break;
               case KeyEvent.VK_L:
               leftKey4 = false;
               break;
               case KeyEvent.VK_QUOTE:
               rightKey4 = false;
               break;
               case KeyEvent.VK_P:
               upKey4 = false;
               break;
               case KeyEvent.VK_SEMICOLON:
               downKey4 = false;
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
            if(upKey3){user3.moveUp();}
            if(downKey3){user3.moveDown();}
            if(leftKey3){user3.moveLeft();}
            if(rightKey3){user3.moveRight();}
            if(upKey4){user4.moveUp();}
            if(downKey4){user4.moveDown();}
            if(leftKey4){user4.moveLeft();}
            if(rightKey4){user4.moveRight();}
            repaint();
            try {runner.sleep(14);}
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
            user3.paint(gBuffer);
            user4.paint(gBuffer);
            if(c == 500)
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
            if(lives <= 0 && lives2 <= 0 && lives3 <= 0 && lives4 <= 0)
            {
                gBuffer.setFont(new Font("Castellar",70,100));
                gBuffer.drawString("GAME OVER",50,250);
                gBuffer.setFont(new Font("Castellar",70,75));
                if(score > score2 && score > score3 && score > score4)
                {
                    gBuffer.drawString("Player 1 Wins!",50,450);
                }
                if(score2 > score && score2 > score3 && score2 > score4)
                {
                    gBuffer.drawString("Player 2 Wins!",50,450);
                }
                if(score3 > score && score3 > score2 && score3 > score4)
                {
                    gBuffer.drawString("Player 3 Wins!",50,450);
                }
                if(score4 > score && score4 > score3 && score4 > score2)
                {
                    gBuffer.drawString("Player 4 Wins!",50,450);
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
                 for(int i = 0; i < B.size(); i++)
              {
                 tempB = (Ball)B.get(i);
                 if(lives3 > 0)
                 {
                 if(tempB.collision(user3))
                 {
                     if(inv3 <= 0)
                        lives3--;
                     else
                        B.remove(tempB);   
                     break;
                    }
                }
                }
                 for(int i = 0; i < B.size(); i++)
              {
                 tempB = (Ball)B.get(i);
                 if(lives4 > 0)
                 {
                 if(tempB.collision(user4))
                 {
                     if(inv4 <= 0)
                        lives4--;
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
                 if(tempR2.collision(user3))
                 {
                     if(inv3 <= 0)
                        lives3--;
                     else
                        R2.remove(tempR2);
                     break;
                    }
                }
                for(int i = 0; i < R2.size(); i++)
                {
                 tempR2 = (Rectangle2)R2.get(i);
                 if(tempR2.collision(user4))
                 {
                     if(inv4 <= 0)
                        lives4--;
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
                 if(tempR.collision(user3))
                 {
                     if(inv3 <= 0)
                        lives3--;
                     else
                        R.remove(tempR);
                     break;
                    }
                }
                for(int i = 0; i < R.size(); i++)
              {
                 tempR = (Rectangle)R.get(i);
                 if(tempR.collision(user4))
                 {
                     if(inv4 <= 0)
                        lives4--;
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
              if((!user3.checkBound(width, height)) && lives3 > 0)
                    score3++;
              if((!user4.checkBound(width, height)) && lives4 > 0)
                    score4++;
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
              if(lives3 > 0)
              {
              if(tempH.collision(user3))
              {
                  lives3 = lives3 + tempH.getH();
                  H.remove(tempH);
              }
              }
              if(lives4 > 0)
              {
              if(tempH.collision(user4))
              {
                  lives4 = lives4 + tempH.getH();
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
              if(lives3 > 0)
              {
              if(tempI.collision(user3))
              {    
                  inv3 = 500;
                  I.remove(tempI);
              }
              }
              if(lives4 > 0)
              {
              if(tempI.collision(user4))
              {    
                  inv4 = 500;
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
             liv3 = String.valueOf(lives3);
             sc3 = String.valueOf(score3);
             invinc3 = String.valueOf(inv3);
             liv4 = String.valueOf(lives4);
             sc4 = String.valueOf(score4);
             invinc4 = String.valueOf(inv4);
             gBuffer.setColor(Color.blue);
             gBuffer.setFont(new Font("Castellar",70,30));
             gBuffer.drawString("Health 1: " + liv,10,68);             
             gBuffer.drawString("Score 1: " + sc,10,38);
             gBuffer.drawString("Health 2: " + liv2,10,630);             
             gBuffer.drawString("Score 2: " + sc2,10,660);
             gBuffer.drawString("Health 3: " + liv3,525,68);             
             gBuffer.drawString("Score 3: " + sc3,525,38);
             gBuffer.drawString("Health 4: " + liv4,525,630);             
             gBuffer.drawString("Score 4: " + sc4,525,660);
             if(c4 < 250)
             {
                gBuffer.setFont(new Font("Castellar",70,100));
                gBuffer.drawString("Run Away!!!",50,175);
                gBuffer.setFont(new Font("Castellar",70,30));
                gBuffer.drawString("Use 'A','W','S','D' To Move Player 1", 150, 490);
                gBuffer.drawString("Use Arrow Keys to Move Player 2", 115, 520);
                gBuffer.drawString("Use 'G','Y','H','J' To Move Player 3", 150, 550);
                gBuffer.drawString("Use 'L','P',';',''' To Move Player 4", 150, 580);
            }
            if(c4 < 450 && c4 > 250)
            {
                gBuffer.setFont(new Font("Castellar",70,30));
                gBuffer.drawString("Avoid All Enemy Objects",200,175);
                gBuffer.drawString("Grab Health Orbs For Extra Health",75,550);
            }
            if(c4 < 700 && c4 > 450)
            {
                gBuffer.setFont(new Font("Castellar",70,30));
                gBuffer.drawString("Orbs Labeled '+*' Are Invinciblilty Orbs",40,175);
                gBuffer.drawString("Invincibility Allows You To",150,500); 
                gBuffer.drawString("Destroy Enemy Objects",200,550);
            }
            if(c4 < 900 && c4 > 700)
            {
                gBuffer.setFont(new Font("Castellar",70,30));
                gBuffer.drawString("Moving Outside the Bounderies",140,175);
                gBuffer.drawString("Will Stop the Score From Increasing",55,500); 
            }
            if(c4 < 1000 && c4 > 900)
            {
                gBuffer.setFont(new Font("Castellar",70,40));
                gBuffer.drawString("Good Luck",300,350);
            }
             if(inv >= 0)
             {
                 gBuffer.setFont(new Font("Castellar",70,25));
                 gBuffer.drawString("Invincibility 1: " + invinc, 10, 98);
             }
             if(inv2 >= 0)
             {
                 gBuffer.setFont(new Font("Castellar",70,25));
                 gBuffer.drawString("Invincibility 2: " + invinc2, 10, 600);
             }
             if(inv3 >= 0)
             {
                 gBuffer.setFont(new Font("Castellar",70,25));
                 gBuffer.drawString("Invincibility 3: " + invinc3, 500, 98);
             }
             if(inv4 >= 0)
             {
                 gBuffer.setFont(new Font("Castellar",70,25));
                 gBuffer.drawString("Invincibility 4: " + invinc4, 500, 600);
             }
             c++;
             c1++;
             c2++;
             c3++;
             c4++;
             inv--;
             inv2--;
             inv3--;
             inv4--;
             repaint();  
        } 
    }
  public void update(Graphics g)
    {        paint(g);   
    }
  
  public void paint(Graphics g)
    {        g.drawImage (Buffer,0,0, this);    }
}
