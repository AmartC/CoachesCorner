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

  int playerDiameter;   // Player's circle/dot diameter
  boolean isMouseDraggingPlayer, whiteBoardMode, markerMode, lineDraw, freeDraw, sqDraw, circDraw; // various booleans for things
  ArrayList<Point> paint_coords; // coordinates for drawing straight lines
  ArrayList<Line> lines = new ArrayList<Line>(); // list of straight lines to draw in Whiteboard Mode
  ArrayList<Line> squares = new ArrayList<Line>(); // list of squares to draw in Whiteboard Mode
  ArrayList<Line> circles = new ArrayList<Line>(); // list of circles to draw in Whiteboard Mode
  ArrayList<ArrayList<Integer>> frees = new ArrayList<ArrayList<Integer>>();

  Player selectedPlayer;  // The player that has been clicked on
  Team offensiveTeam;
  Team defensiveTeam;
  JLabel frameLabel;
  JTextField newFrame;

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

    playerDiameter = 30;
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


    frameLabel = new JLabel("Frame");
    newFrame = new JTextField(10);
    this.add(frameLabel);
    this.add(newFrame);

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
    JPanel panel = new JPanel();
    JLabel xLabel = new JLabel("X");
    JTextField newX = new JTextField(10);
    Point playerPoints = selectedPlayer.getPositionAtFrame(selectedFrame);
    //newX.setText(String.valueOf(selectedPlayer.getX()));
    newX.setText(String.valueOf(playerPoints.getX()));
    panel.add(xLabel);
    panel.add(newX);
    JLabel yLabel = new JLabel("Y");
    JTextField newY = new JTextField(10);
    //newY.setText(String.valueOf(selectedPlayer.getY()));
    newY.setText(String.valueOf(playerPoints.getY()));
    panel.add(yLabel);
    panel.add(newY);
    JLabel speedLabel = new JLabel("Speed");
    JTextField newSpeed = new JTextField(10);
    newSpeed.setText(String.valueOf(selectedPlayer.getSpeed()));
    panel.add(speedLabel);
    panel.add(newSpeed);
    int value = JOptionPane.showConfirmDialog(null, panel, "Enter position and speed for player in this frame.", JOptionPane.OK_CANCEL_OPTION);
    if (value == JOptionPane.OK_OPTION)
    {
      int updatedX = playerPoints.getX();
      int updatedY = playerPoints.getY();
      int updatedSpeed = selectedPlayer.getSpeed();
      // OK was pressed
      if(newX.getText().replaceAll("\\s","").length() != 0)
      {
        // Verify that input can be parsed to be an integer.
        try
        {
          updatedX = Integer.parseInt(newX.getText());
          if(updatedX < 0)
          {
            updatedX = 0;
          }
          else if (updatedX > width)
          {
            updatedX = width - playerDiameter - (playerDiameter / 2);
          }
          selectedPlayer.setX(updatedX);
        }
        catch(NumberFormatException e)
        {
          selectedPlayer.setX(playerPoints.getX());
        }
      }

      if(newY.getText().replaceAll("\\s","").length() != 0)
      {
        // Verify that input can be parsed to be an integer.
        try
        {
          updatedY = Integer.parseInt(newY.getText());
          if(updatedY < 0)
          {
            updatedY = 0;
          }
          else if (updatedY > height)
          {
            updatedY = height - playerDiameter - (playerDiameter / 2);
          }
          selectedPlayer.setY(updatedY);
        }
        catch(NumberFormatException e)
        {
          selectedPlayer.setY(playerPoints.getY());
        }
      }

      selectedPlayer.setPositionAtFrame(selectedFrame, updatedX, updatedY);

      if(newSpeed.getText().replaceAll("\\s","").length() != 0)
      {
        // Verify that input can be parsed to be an integer.
        try
        {
          updatedSpeed = Integer.parseInt(newSpeed.getText());
          if (updatedSpeed >= 1 && updatedSpeed <= 3)
          {
            selectedPlayer.setSpeed(updatedSpeed);
          }
        }
        catch(NumberFormatException e)
        {
          selectedPlayer.setSpeed(selectedPlayer.getSpeed());
        }
      }
    }
  }
  public boolean mouseDown(Event evt,int x,int y)
  {
    return true;
  }

  public void mouseEntered(MouseEvent e) {}

  public void mouseExited(MouseEvent e) {}

  public void mouseClicked(MouseEvent e) {
  // NEED TO FIGURE OUT

    /*if(markerMode && freeDraw){
      ArrayList<Integer> circ = new ArrayList<Integer>();
      circ.add(mx-2);
      circ.add(my-2);
      frees.add(circ);
      circ.clear();
    }  */
  }

  public void mousePressed(MouseEvent e)
  {
    mx = e.getX();
    my = e.getY();

    // booleans for different draw modes (buttons)
    if(whiteBoardMode)
    {
      // Marker Mode on/off
      if(mx >= 5 && mx <= 55 && my >= 5 && my <= 55)
      {
        markerMode = !markerMode;
      }
      // Erase all lines from screen
      if(mx >= 58 && mx <= 108 && my >= 5 && my < 55)
      {
        lines.clear();
        frees.clear();
        squares.clear();
        circles.clear();
      }
      // Draw Line on/off
      if(mx >= 5 && mx <= 55 && my > 58 && my <= 108 && markerMode)
      {
        if(!lineDraw)
        {
            lineDraw = true;
            freeDraw = false;
            sqDraw = false;
            circDraw = false;
        }
        else
        {
           lineDraw = false;
        }
      }
      // Draw Free on/off
      if(mx >= 5 && mx <= 55 && my >= 111 && my <= 161 && markerMode)
      {
        if(!freeDraw)
        {
            freeDraw = true;
            lineDraw = false;
            sqDraw = false;
            circDraw = false;
        }
        else
        {
           freeDraw = false;
        }
      }
      // Draw Square on/off
      if(mx >= 58 && mx <= 108 && my >= 58 && my <= 108 && markerMode)
      {
        if(!sqDraw)
        {
          sqDraw = true;
          circDraw = false;
          lineDraw = false;
          freeDraw = false;
        }
        else
        {
          sqDraw = false;
        }
      }
      // Draw Circle on/off
      if(mx >= 58 && mx <= 108 && my >= 111 && my <= 161 && markerMode)
      {
        if(!circDraw)
        {
          circDraw = true;
          sqDraw = false;
          lineDraw = false;
          freeDraw = false;
        }
        else
        {
          circDraw = false;
        }
      }
    }
    if(SwingUtilities.isRightMouseButton(e) && !markerMode)
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
      if(markerMode){
          if(lineDraw)
          {
            paint_coords = new ArrayList<Point>();
            paint_coords.add(new Point(mx, my));
          }
          else if(sqDraw || circDraw)
          {
            paint_coords = new ArrayList<Point>();
            paint_coords.add(new Point(mx, my));
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
    }

    e.consume();
  }

  public void mouseReleased(MouseEvent e)
  {
    isMouseDraggingPlayer = false;
    if(markerMode && lineDraw)
    {
      paint_coords.add(new Point(e.getX(), e.getY()));
      Point start_point = paint_coords.get(0);
      Point end_point = paint_coords.get(1);
      Line new_line = new Line(start_point, end_point);
      lines.add(new_line);
      paint_coords.clear();
    }
    else if(markerMode && (sqDraw || circDraw))
    {
      paint_coords.add(new Point(e.getX(), e.getY()));
      Point start_point = paint_coords.get(0);
      Point end_point = paint_coords.get(1);
      Line new_line = new Line(start_point, end_point);
      if(sqDraw) squares.add(new_line);
      else circles.add(new_line);
      paint_coords.clear();
    }
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

      int newPlayerPositionX = playerPositionX + new_mx - mx;
      int newPlayerPositionY = playerPositionY + new_my - my;

      // Check if player's X coordinate is out of boundary.
      if(newPlayerPositionX + playerDiameter > width)
      {
        newPlayerPositionX = width - playerDiameter;
      }
      else if(newPlayerPositionX < 0)
      {
        newPlayerPositionX = 0;
      }
      else
      {
        // update mouse X coordinate data if player is within boundary
        mx = new_mx;
      }

      // Check if player's Y coordinate is out of boundary.
      if(newPlayerPositionY + playerDiameter > height)
      {
        newPlayerPositionY = height - playerDiameter;
      }
      else if(newPlayerPositionY < 0)
      {
        newPlayerPositionY = 0;
      }
      else
      {
        // update mouse Y coordinate data if player is within boundary
        my = new_my;
      }

      selectedPlayer.setPositionAtFrame(selectedFrame, newPlayerPositionX, newPlayerPositionY);

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
      }
      else
      {
        gBuffer.drawLine(0,i*(int)ONE_YARD,10,i*(int)ONE_YARD);
        gBuffer.drawLine(width-10,i*(int)ONE_YARD,width,i*(int)ONE_YARD);
        gBuffer.drawLine((int)(CENTER_OF_FIELD-ONE_YARD*(12.5/3)),i*(int)ONE_YARD,(int)(CENTER_OF_FIELD-ONE_YARD*(11.5/3)),i*(int)ONE_YARD);
        gBuffer.drawLine((int)(CENTER_OF_FIELD+ONE_YARD*(11.5/3)),i*(int)ONE_YARD,(int)(CENTER_OF_FIELD+ONE_YARD*(12.5/3)),i*(int)ONE_YARD);
      }
    }
  }

  public void displayPlayerPositions()
  {
    offensiveTeam.displayTeamAtFrame(gBuffer, selectedFrame);
    defensiveTeam.displayTeamAtFrame(gBuffer, selectedFrame);
  }

  public void drawLines(){
    gBuffer.setColor(Color.YELLOW);
    // draw straight lines
    for(int i = 0; i < lines.size(); i++)
    {
      Line curr = lines.get(i);
      Point sp = curr.getStart();
      Point ep = curr.getEnd();
      gBuffer.drawLine(sp.getX(), sp.getY(), ep.getX(), ep.getY());
    }
    // draw free lines
    for(int i = 0; i < frees.size(); i++)
    {
      ArrayList<Integer> curr = frees.get(i);
      gBuffer.fillOval(curr.get(0), curr.get(1), 4, 4);
    }
    // draw squares
    for(int i = 0; i < squares.size(); i++)
    {
      Line curr = squares.get(i);
      int x1 = curr.getStart().getX();
      int y1 = curr.getStart().getY();
      int x2 = curr.getEnd().getX();
      int y2 = curr.getEnd().getY();
      int height = y2 - y1;
      int width = x2 - x1;
      if(height < 0 && width < 0)
      {
        height *= -1;
        width *= -1;
        gBuffer.drawRect(x2,y2,width,height);
      }
      else if(height < 0)
      {
        height *= -1;
        gBuffer.drawRect(x1,y2,width,height);
      }
      else if(width < 0)
      {
        width *= -1;
        gBuffer.drawRect(x2,y1,width,height);
      }
      else gBuffer.drawRect(x1,y1,width,height);
    }
    // draw circles
    for(int i = 0; i < circles.size(); i++)
    {
      Line curr = circles.get(i);
      int x1 = curr.getStart().getX();
      int y1 = curr.getStart().getY();
      int x2 = curr.getEnd().getX();
      int y2 = curr.getEnd().getY();
      int height = y2 - y1;
      int width = x2 - x1;
      if(height < 0 && width < 0)
      {
        height *= -1;
        width *= -1;
        gBuffer.drawOval(x2,y2,width,height);
      }
      else if(height < 0)
      {
        height *= -1;
        gBuffer.drawOval(x1,y2,width,height);
      }
      else if(width < 0)
      {
        width *= -1;
        gBuffer.drawOval(x2,y1,width,height);
      }
      else gBuffer.drawOval(x1,y1,width,height);
    }
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
      createPlay();
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
  public void runPlay(){}
  public void whiteBoard()
  {
    whiteBoardMode = true;
    while(true){
      try {runner.sleep(13);}
      catch (Exception e) {}
      paintField(gBuffer);
      displayPlayerPositions();
      drawLines();
      // "Marker Mode" Toggle
      if(markerMode)gBuffer.setColor(Color.GREEN);
      else gBuffer.setColor(Color.WHITE);
      gBuffer.fillRect(5,5,50,50);
      gBuffer.setColor(Color.BLACK);
      for(int i = 0; i < 3; i++)
      {
        gBuffer.drawRect(5+i,5+i,50-(2*i),50-(2*i));
      }
      gBuffer.drawString("Marker",12,27);
      gBuffer.drawString("Mode",15,42);
      // "Clear All" Toggle
      gBuffer.setColor(Color.WHITE);
      gBuffer.fillRect(58,5,50,50);
      gBuffer.setColor(Color.BLACK);
      for(int i = 0; i < 3; i++)
      {
        gBuffer.drawRect(58+i,5+i,50-(2*i),50-(2*i));
      }
      gBuffer.drawString("Clear",67,27);
      gBuffer.drawString("All",77,42);
      // If Marker Mode toggled 'on', display more options
      if(markerMode)
      {
        // "Draw Line" Toggle
        if(lineDraw) gBuffer.setColor(Color.GREEN);
        else gBuffer.setColor(Color.WHITE);
        gBuffer.fillRect(5,58,50,50);
        gBuffer.setColor(Color.BLACK);
        for(int i = 0; i < 3; i++)
        {
          gBuffer.drawRect(5+i,58+i,50-(2*i),50-(2*i));
        }
        gBuffer.drawString("Draw", 15, 80);
        gBuffer.drawString("Line", 17, 95);
        // "Draw Free" Toggle
        if(freeDraw) gBuffer.setColor(Color.GREEN);
        else gBuffer.setColor(Color.WHITE);
        gBuffer.fillRect(5, 111, 50, 50);
        gBuffer.setColor(Color.BLACK);
        for(int i = 0; i < 3; i++)
        {
          gBuffer.drawRect(5+i,111+i,50-(2*i),50-(2*i));
        }
        gBuffer.drawString("Draw", 15, 133);
        gBuffer.drawString("Free", 17, 148);
        // "Draw Square" Toggle
        if(sqDraw) gBuffer.setColor(Color.GREEN);
        else gBuffer.setColor(Color.WHITE);
        gBuffer.fillRect(58,58,50,50);
        gBuffer.setColor(Color.BLACK);
        for(int i = 0; i < 3; i++)
        {
          gBuffer.drawRect(58+i,58+i,50-(2*i),50-(2*i));
        }
        gBuffer.drawRect(68,68,30,30);
        // "Draw Circle" Toggle
        if(circDraw) gBuffer.setColor(Color.GREEN);
        else gBuffer.setColor(Color.WHITE);
        gBuffer.fillRect(58,111,50,50);
        gBuffer.setColor(Color.BLACK);
        for(int i = 0; i < 3; i++)
        {
          gBuffer.drawRect(58+i,111+i,50-(2*i),50-(2*i));
        }
        gBuffer.drawOval(68,121,30,30);
      }

      repaint();
    }
  }
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
