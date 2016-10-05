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
  int playerDiameter;   // Player's circle/dot diameter
  boolean isMouseDraggingPlayer;
  ArrayList<Player> offensivePlayers;
  ArrayList<Player> defensivePlayers;
  Player selectedPlayer;  // The player that has been clicked on

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
    playerDiameter = 20;
    isMouseDraggingPlayer = false;
    offensivePlayers = new ArrayList<Player>();
    defensivePlayers = new ArrayList<Player>();
    selectedPlayer = null;

    for(int a = 0; a < 11; a++)
    {
      Player c = new Player(x + (25 * (a - 5)) - 20, y - 20, playerDiameter);
      offensivePlayers.add(c);
    }

    for(int b = 0; b < 11; b++)
    {
      Player c = new Player(x + (25 * (b - 5)) - 20, y + 10, playerDiameter);
      defensivePlayers.add(c);
    }

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

    for(int i = 0; i < offensivePlayers.size(); i++)
    {
      // Grab the player's information
      Player currentPlayer = offensivePlayers.get(i);
      int playerPositionX = currentPlayer.getX();
      int playerPositionY = currentPlayer.getY();
      int playerCircleDiameter = currentPlayer.getDiameter();

      // Check if mouse clicked on this player by comparing coordinates
      if (playerPositionX < mx && mx < playerPositionX+playerCircleDiameter && playerPositionY < my && my < playerPositionY+playerCircleDiameter)
      {
        isMouseDraggingPlayer = true;
        selectedPlayer = currentPlayer;
        break;
      }
    }

    // If mouse did not click on an offensive player, then check the defensive players
    if(!isMouseDraggingPlayer)
    {
      for(int i = 0; i < defensivePlayers.size(); i++)
      {
        // Grab the player's information
        Player currentPlayer = defensivePlayers.get(i);
        int playerPositionX = currentPlayer.getX();
        int playerPositionY = currentPlayer.getY();
        int playerCircleDiameter = currentPlayer.getDiameter();

        // Check if mouse clicked on this player by comparing coordinates
        if (playerPositionX < mx && mx < playerPositionX+playerCircleDiameter && playerPositionY < my && my < playerPositionY+playerCircleDiameter)
        {
          isMouseDraggingPlayer = true;
          selectedPlayer = currentPlayer;
          break;
        }
      }
    }

    e.consume();
  }

  public void mouseReleased(MouseEvent e)
  {
    isMouseDraggingPlayer = false;
    e.consume();
  }

  public void mouseMoved(MouseEvent e) {}

  public void mouseDragged(MouseEvent e)
  {
    if (isMouseDraggingPlayer)
    {
      // get the latest mouse position
      int new_mx = e.getX();
      int new_my = e.getY();

      // displace the player by the distance the mouse moved since the last event
      int playerPositionX = selectedPlayer.getX();
      int playerPositionY = selectedPlayer.getY();
      selectedPlayer.setX(playerPositionX + new_mx - mx);
      selectedPlayer.setY(playerPositionY + new_my - my);

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

  public void displayPlayerPositions()
  {
    for(int a = 0; a < offensivePlayers.size(); a++)
    {
      Player currentPlayer = offensivePlayers.get(a);
      int playerPositionX = currentPlayer.getX();
      int playerPositionY = currentPlayer.getY();
      int playerCircleDiameter = currentPlayer.getDiameter();
      gBuffer.setColor(Color.blue);
      gBuffer.fillOval(playerPositionX, playerPositionY, playerCircleDiameter, playerCircleDiameter);
    }

    for(int b = 0; b < defensivePlayers.size(); b++)
    {
      Player currentPlayer = defensivePlayers.get(b);
      int playerPositionX = currentPlayer.getX();
      int playerPositionY = currentPlayer.getY();
      int playerCircleDiameter = currentPlayer.getDiameter();
      gBuffer.setColor(Color.red);
      gBuffer.fillOval(playerPositionX, playerPositionY, playerCircleDiameter, playerCircleDiameter);
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
      displayPlayerPositions();

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