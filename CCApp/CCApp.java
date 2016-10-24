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

  double ONE_YARD;
  int CENTER_OF_FIELD;

  int x, y;
  int mx, my;  // the most recently recorded mouse coordinates
  int selectedFrame;
  int frameTime;
  int numberOfFrames;

  int playerDiameter;   // Player's circle/dot diameter
  boolean isMouseDraggingPlayer;
  Player selectedPlayer;  // The player that has been clicked on

  Team offensiveTeam;
  Team defensiveTeam;
  Label frameLabel;
  TextField newFrame;
  Button menu;
  
  boolean running = false;

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

    ONE_YARD = height/20;
    CENTER_OF_FIELD = width/2;

    y = (int)(ONE_YARD*15);
    x = width/2;
    selectedFrame = 0;
    frameTime = 200;
    numberOfFrames = 1;

    playerDiameter = 20;
    isMouseDraggingPlayer = false;
    selectedPlayer = null;

    offensiveTeam = new Team("Offense", Color.red);
    defensiveTeam = new Team("Defensive", Color.blue);

    for(int a = 0; a < 11; a++)
    {
      Player c = new Player(x + (25 * (a - 5)) - 20, y - 20);
      offensiveTeam.addPlayer(c);
    }

    for(int b = 0; b < 11; b++)
    {
      Player c = new Player(x + (25 * (b - 5)) - 20, y + 10);
      defensiveTeam.addPlayer(c);
    }

    addMouseListener(this);
    addMouseMotionListener(this);
    
    
    frameLabel = new Label("Frame");
    newFrame = new TextField(10);
    add(frameLabel);
    add(newFrame);
    newFrame.setText("0");
    newFrame.addActionListener(
      new ActionListener() 
      {
        public void actionPerformed(ActionEvent event) 
        {
            int userFrame = Integer.parseInt(newFrame.getText());
            if(userFrame > numberOfFrames)
            {
              newFrame.setText(Integer.toString(numberOfFrames-1));
              selectedFrame = numberOfFrames - 1;
            }else{
              if(numberOfFrames == userFrame)
              {
                offensiveTeam.addFrame(userFrame-1);
                defensiveTeam.addFrame(userFrame-1);
                numberOfFrames = numberOfFrames + 1;
              }
              selectedFrame = userFrame;
            }
        }
      }
    );
    
    // Construct the button
    Button menu = new Button("Menu");
    this.add(menu);
    menu.setLocation(0,0);
    menu.addActionListener(
      new ActionListener() 
      {
        public void actionPerformed(ActionEvent event) 
        {
            run();
        }
      }
    );
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

  public void handleInput()
  {
    Point pos = selectedPlayer.getPositionAtFrame(selectedFrame);  
      
    JPanel panel = new JPanel();
    JLabel xLabel = new JLabel("X");
    JTextField newX = new JTextField(10);
    panel.add(xLabel);
    panel.add(newX);
    newX.setText(Integer.toString(pos.getX()));
    
    JLabel yLabel = new JLabel("Y");
    JTextField newY = new JTextField(10);
    panel.add(yLabel);
    panel.add(newY);
    newY.setText(Integer.toString(pos.getY()));
    
    JLabel speedLabel = new JLabel("Speed");
    JTextField newSpeed = new JTextField(10);
    panel.add(speedLabel);
    panel.add(newSpeed);
    newSpeed.setText(Integer.toString(selectedPlayer.getSpeed()));
    
    int value = JOptionPane.showConfirmDialog(null, panel, "Enter position and speed for player in this frame.", JOptionPane.OK_CANCEL_OPTION);
    if (value == JOptionPane.OK_OPTION)
    {
      // OK was pressed
      int updatedX = Integer.parseInt(newX.getText());
      int updatedY = Integer.parseInt(newY.getText());
      selectedPlayer.setPositionAtFrame(selectedFrame, updatedX, updatedY);

      int updatedSpeed = Integer.parseInt(newSpeed.getText());
      if(updatedSpeed >= 1 && updatedSpeed <= 3)
      {
        selectedPlayer.setSpeed(updatedSpeed);
      }
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
    if(SwingUtilities.isRightMouseButton(e))
    {
      Player newSelectedPlayer = offensiveTeam.findPlayerAtPoint(mx, my);
      if(newSelectedPlayer != null)
      {
        selectedPlayer = newSelectedPlayer;
        this.handleInput();
      }
      else
      {
        newSelectedPlayer = defensiveTeam.findPlayerAtPoint(mx, my);
        if(newSelectedPlayer != null)
        {
          selectedPlayer = newSelectedPlayer;
          this.handleInput();
        }
      }
    }
    else
    {
      Player newSelectedPlayer = offensiveTeam.findPlayerAtPoint(mx, my);
      if(newSelectedPlayer != null)
      {
        isMouseDraggingPlayer = true;
        selectedPlayer = newSelectedPlayer;
      }

      // If mouse did not click on an offensive player, then check the defensive players
      if(!isMouseDraggingPlayer)
      {
        newSelectedPlayer = defensiveTeam.findPlayerAtPoint(mx, my);
        if(newSelectedPlayer != null)
        {
          isMouseDraggingPlayer = true;
          selectedPlayer = newSelectedPlayer;
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
      Point selectedPlayerPosition = selectedPlayer.getPositionAtFrame(selectedFrame);
      int playerPositionX = selectedPlayerPosition.getX();
      int playerPositionY = selectedPlayerPosition.getY();
      selectedPlayer.setPositionAtFrame(selectedFrame, playerPositionX + new_mx - mx, playerPositionY + new_my - my);
      
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
    gBuffer.setColor(new Color(25,150,10));
    gBuffer.fillRect(0,0,width,height);
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
    offensiveTeam.displayTeam(gBuffer);
    defensiveTeam.displayTeam(gBuffer);
  }
  
  public void updatePlayerPositions(int frame)
  {
    offensiveTeam.updateTeamAtFrame(gBuffer, frame);
    defensiveTeam.updateTeamAtFrame(gBuffer, frame);
  }
  
  public void calculateVelocity(int frame)
  {
    offensiveTeam.calculateVelocity(gBuffer, frame, frameTime);
    defensiveTeam.calculateVelocity(gBuffer, frame, frameTime);
  }

  public void run(){
    ImageIcon icon = new ImageIcon("CCApp_img.png", "CCApp logo");
    String[] options = new String[] {"Create a Play",
      "Run a Play",
      "Whiteboard Mode",
      "Create Playbook",
      "Load Playbook",
      "Export Playbook",
      "View Tutorials"};
    // Get choice from user
    int choice = JOptionPane.showOptionDialog(null,
      "Welcome to Coach's Corner!",
      "CCApp",
      JOptionPane.DEFAULT_OPTION,
      JOptionPane.INFORMATION_MESSAGE,
      icon,
      options,
      options[6]);

    // Interpret the user's choice
    if(choice == 0){
      if(!running)
      {
        createPlay();
      }
    }else if(choice == 1){
      runPlay();
    }else if(choice == 2){
      whiteBoard();
    }else if(choice == 3){
      createBook();
    }else if(choice == 4){
      loadBook();
    }else if(choice == 5){
      exportBook();
    }else if(choice == 6){
      viewTutorial();
    }
  }

  public void createPlay()
  {
    while(true)
    {
      try {runner.sleep(13);}
      catch (Exception e) {}

      //gBuffer.drawImage(football, x, y, this);

      paintField(gBuffer);
      displayPlayerPositions();

      repaint();
    }
  }
  public void runPlay()
  {
    for(int i = 0; i < numberOfFrames - 1; i++)
    {
      calculateVelocity(i);
      for(int j = 0; j < frameTime; j++)
      {
          try {runner.sleep(13);}
          catch (Exception e) {}
    
          if(!running)
          {
              menu.setLocation(0,0);
              running = true;
          }
    
          paintField(gBuffer);
          updatePlayerPositions(i);
          offensiveTeam.displayTeam(gBuffer);
          defensiveTeam.displayTeam(gBuffer);
          
          repaint();
      }
    }
  }
  public void whiteBoard(){}
  public void createBook(){}
  public void loadBook(){}
  public void exportBook(){}
  public void viewTutorial(){}

  public void update(Graphics g)
  {
    paint(g);
  }

  public void paint(Graphics g)
  {
    g.drawImage (Buffer,0,0, this);
  }
}
