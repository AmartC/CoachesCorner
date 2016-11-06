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


/**
 * The CCApp class is the "brain" of the application.
 * Runs application and manages/displays all GUI interactions.
 */
public class CCApp extends Applet implements Runnable, MouseListener, MouseMotionListener
{
  Thread runner;  // Main thread running the applet
  Graphics gBuffer; // Used to manage what is displayed
  Image Buffer; // Used to paint all graphics onto window
  CCAppGraphics graphics;
  int width, height;  // Width and height of applet window
  boolean rightKey, leftKey, upKey, downKey;  // Used for keyboard controls (currently not used)
  
  int x, y; // Used for positioning of certain objects on field
  int mx, my;  // the most recently recorded mouse coordinates
  int selectedFrame;  // Holds the number of current frame selected by used

  JLabel frameLabel;  // Previously used for labeling a textfield (not used anymore)
  JTextField newFrame;  // Previously used for adding textfield to modify frames (not used anymore)

  int playerDiameter;   // Player's circle/dot diameter
  boolean isMouseDraggingPlayer, whiteBoardMode, markerMode, lineDraw, freeDraw, sqDraw, circDraw, running, animating; // various booleans for dragging mouse, whiteboard mode, and animation
  ArrayList<Point> paint_coords; // coordinates for drawing straight lines
  ArrayList<Line> lines = new ArrayList<Line>(); // list of straight lines to draw in Whiteboard Mode
  ArrayList<Line> squares = new ArrayList<Line>(); // list of squares to draw in Whiteboard Mode
  ArrayList<Line> circles = new ArrayList<Line>(); // list of circles to draw in Whiteboard Mode
  ArrayList<ArrayList<Integer> > frees = new ArrayList<ArrayList<Integer>>();

  Player selectedPlayer;  // The player that has been most recently clicked on
  Team offensiveTeam;
  Team defensiveTeam;


  /**
   * Function called when applet starts up.
   * Initializes all global variables
   */
  public void init()
  {
    width=this.getSize().width;
    height=this.getSize().height;
    Buffer=createImage(width,height);
    gBuffer=Buffer.getGraphics();  
    addKeyListener(new MyKeyListener());

    y = (int)(height/20*15);
    x = width/2;
    selectedFrame = 0;
    animating = false;

    playerDiameter = 30;
    isMouseDraggingPlayer = false;
    selectedPlayer = null;

    offensiveTeam = new Team("Offense", Color.red);
    defensiveTeam = new Team("Defensive", Color.blue);

    // Create players and add them to each team. Position them near center of field (for now)
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
    
    graphics = new CCAppGraphics(gBuffer,Buffer,width,height,offensiveTeam,defensiveTeam, this);  

    // Add mouse listeners to detect mouse interactions
    addMouseListener(this);
    addMouseMotionListener(this);


    frameLabel = new JLabel("Frame");
    newFrame = new JTextField(10);
    this.add(frameLabel);
    this.add(newFrame);

    // Construct the menu button
    Button menu = new Button("Menu");
    this.add(menu);
    menu.setLocation(0,0);
    menu.addActionListener(
      new ActionListener()
      {
        public void actionPerformed(ActionEvent event)
        {
            running = false;
        }
      }
    );
  }


  /**
   * Class used for detection of keyboard interactions (currently not used)
   */
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

    /**
     * Another class used for detection of keyboard interactions (currently not used)
     */
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

  /**
   * Function responsible for starting the applet thread.
   */
  public void start()
  {
    if (runner == null)
    {
      runner = new Thread (this);
      runner.start();
    }
  }

  /**
   * Function responsible for stopping the applet thread (apparently deprecated)
   */
  public void stop()
  {
    if (runner != null)
    {
      runner.stop();
      runner = null;
    }
  }

  /**
   * Function responsible for handling the event when a user
   * right clicks on a player on field.
   * Essentially allows users to modify attributes of the player
   * object.
   */
  public void handleInput()
  {
    // Set up a JOptionPane (pop up dialog) with three
    // textfield that will allow user to change player
    // member variables
    JPanel panel = new JPanel();

    JLabel xLabel = new JLabel("X");
    JTextField newX = new JTextField(10);
    Point playerPoints = selectedPlayer.getPositionAtFrame(selectedFrame);
    newX.setText(String.valueOf(playerPoints.getX()));
    panel.add(xLabel);
    panel.add(newX);

    JLabel yLabel = new JLabel("Y");
    JTextField newY = new JTextField(10);
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
            updatedX = width - playerDiameter;
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
            updatedY = height - playerDiameter;
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

  /**
   * Function that detects
   */
  public boolean mouseDown(Event evt,int x,int y)
  {
    return true;
  }

  /**
   * Function that detects when mouse/cursor has entered the window.
   */
  public void mouseEntered(MouseEvent e) {}

  /**
   * Function that detects when mouse/cursor has left the window.
   */
  public void mouseExited(MouseEvent e) {}

  /**
   * Function that detects when mouse/cursor has pressed and released.
   */
  public void mouseClicked(MouseEvent e) {
    // NEED TO FIGURE OUT
    /*if(markerMode && freeDraw){
      ArrayList<Integer> circ = new ArrayList<Integer>();
      circ.add(mx-2);
      circ.add(my-2);
      frees.add(circ);
      circ.clear();
    }  */

    // Grab mouse coordinates
    mx = e.getX();
    my = e.getY();

    // Calculate position of the remove frame button
    int minusFrameButtonX = graphics.frameMenuPositionX;
    int minusFrameButtonY = graphics.frameMenuPositionY - graphics.frameButtonSize - graphics.frameButtonIndentation;

    // Check if the remove frame button has been clicked on
    if (graphics.numberOfFrames > 1 && (minusFrameButtonX < mx && mx < minusFrameButtonX + graphics.frameButtonSize && minusFrameButtonY < my && my < minusFrameButtonY + graphics.frameButtonSize))
    {
      offensiveTeam.removeLastFrameFromPlayers();
      defensiveTeam.removeLastFrameFromPlayers();

      // Check if the current frame being viewed is getting removed
      if(selectedFrame >= graphics.numberOfFrames - 1)
      {
        selectedFrame = graphics.numberOfFrames - 2;
      }

	  offensiveTeam.setPositions(selectedFrame);
      defensiveTeam.setPositions(selectedFrame);
      graphics.numberOfFrames--;
    }

    // Calculate position of the add frame button
    int plusFrameButtonX = graphics.frameMenuPositionX + graphics.frameButtonSize + graphics.frameButtonIndentation;
    int plusFrameButtonY = graphics.frameMenuPositionY - graphics.frameButtonSize - graphics.frameButtonIndentation;

    // Check if the add frame button has been clicked on
    if (plusFrameButtonX < mx && mx < plusFrameButtonX + graphics.frameButtonSize && plusFrameButtonY < my && my < plusFrameButtonY + graphics.frameButtonSize)
    {
      offensiveTeam.addNewFrameToPlayers();
      defensiveTeam.addNewFrameToPlayers();
      selectedFrame++;
      graphics.numberOfFrames++;
    }

    // Used for X position of each frame number botton
    int indentationX = graphics.frameMenuPositionX;
    for(int i = 0; i < graphics.numberOfFrames; i++)
    {
      // Calculate position of the frame number button
      int numberFrameButtonX = indentationX;
      int numberFrameButtonY = graphics.frameMenuPositionY;

      // Check if the frame number button has been clicked on
      if (numberFrameButtonX < mx && mx < numberFrameButtonX + graphics.frameButtonSize && numberFrameButtonY < my && my < numberFrameButtonY + graphics.frameButtonSize)
      {
        selectedFrame = i;
	    offensiveTeam.setPositions(selectedFrame);
        defensiveTeam.setPositions(selectedFrame);
        break;
      }

      // Calculate X position of next frame number button
      indentationX += graphics.frameButtonSize + graphics.frameButtonIndentation;
    }
  }

  /**
   * Function that detects when mouse has been pressed by user.
   */
  public void mousePressed(MouseEvent e)
  {
    // Grab mouse coordinates
    mx = e.getX();
    my = e.getY();

    // Booleans for different draw modes (buttons)
    // Check if whiteboard mode has been selected
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
      // Toggle draw Line mode on/off
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
      // Toggle draw Free mode on/off
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
      // Toggle draw Square on/off
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
      // Toggle draw Circle on/off
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

    // If marker mode is off and user right clicks
    if(SwingUtilities.isRightMouseButton(e) && !markerMode)
    {
      // Find the player on offensive team that was right clicked on
      Player newSelectedPlayer = offensiveTeam.findPlayerAtPoint(mx, my);
      if(newSelectedPlayer != null) // A player on offensive team was right clicked on
      {
        // Keep track of player that was recently clicked on
        selectedPlayer = newSelectedPlayer;
        // Delegate work
        this.handleInput();
      }
      else  // A player on offensive team that was NOT clicked on
      {
        // Find the player on defensive team team that was right clicked on
        newSelectedPlayer = defensiveTeam.findPlayerAtPoint(mx, my);
        if(newSelectedPlayer != null) // A player on Defensive team was right clicked on
        {
          // Keep track of player that was recently clicked on
          selectedPlayer = newSelectedPlayer;
          // Delegate work
          this.handleInput();
        }
      }
    }
    else
    {
      // If user clicked on screen while marker mode was on
      if(markerMode){
          // If in draw line mode
          if(lineDraw)
          {
            // Store where user clicked
            paint_coords = new ArrayList<Point>();
            paint_coords.add(new Point(mx, my));
          }
          else if(sqDraw || circDraw) // If draw square or circle mode is on
          {
            // Store where user clicked
            paint_coords = new ArrayList<Point>();
            paint_coords.add(new Point(mx, my));
          }
      }
      else  // If user clicked on screen while marker mode was off
      {
          // Find the player on offensive team that was right clicked on
          Player newSelectedPlayer = offensiveTeam.findPlayerAtPoint(mx, my);
          if(newSelectedPlayer != null) // A player was clicked on
          {
            // Note that mouse may start to drag
            isMouseDraggingPlayer = true;
            selectedPlayer = newSelectedPlayer;
          }

          // If mouse did not click on an offensive player, then check the defensive players
          if(!isMouseDraggingPlayer)
          {
            // Find the player on defensive team that was right clicked on
            newSelectedPlayer = defensiveTeam.findPlayerAtPoint(mx, my);
            if(newSelectedPlayer != null) // A player was clicked on
            {
              // Note that mouse may start to drag
              isMouseDraggingPlayer = true;
              selectedPlayer = newSelectedPlayer;
            }
          }
      }
    }

    // Mouse press event has passed
    e.consume();
  }

  /**
   * Function that detects when mouse has been released by user.
   */
  public void mouseReleased(MouseEvent e)
  {
    isMouseDraggingPlayer = false;

    // If marker and draw line mode were on, then keep track of point of release
    if(markerMode && lineDraw)
    {
      paint_coords.add(new Point(e.getX(), e.getY()));
      Point start_point = paint_coords.get(0);
      Point end_point = paint_coords.get(1);
      Line new_line = new Line(start_point, end_point);
      lines.add(new_line);
      paint_coords.clear();
    }
    else if(markerMode && (sqDraw || circDraw)) // If marker and draw circle/square mode were on, then keep track of point of release
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

  /**
   * Function that detects when mouse/cursor has been moved by user.
   */
  public void mouseMoved(MouseEvent e) {}

  /**
   * Function that detects when mouse/cursor has been pressed and dragged by user.
   */
  public void mouseDragged(MouseEvent e)
  {
    // If applet is not animating and user is dragging a player
    if (isMouseDraggingPlayer && !animating)
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

      // Set player's new position based on where it was dragged
      selectedPlayer.setPositionAtFrame(selectedFrame, newPlayerPositionX, newPlayerPositionY);
      selectedPlayer.setX(newPlayerPositionX);
      selectedPlayer.setY(newPlayerPositionY);

      // Repaint graphics to display movement of player
      repaint();

      e.consume();
    }
  }
  
  /**
   * Function that displays the lines that have been drawn by the user.
   */
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

  /**
   * Main function that is continuously called when
   * applet is running.
   */
  public void run()
  {
    // Display main menu at start of application
    boolean menuOn = true;
    while(menuOn)
    {
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

	    graphics.running = true;

	    // Interpret the user's choice
	    if(choice == 0){	      
	      graphics.createPlay(runner);
	    }else if(choice == 1){
	      animating = true;
	      graphics.runPlay(runner);
	      animating = false;
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
		  } else {
          menuOn = false;
      }
    }
  }

  /**
   * Function that runs when user selected "Whiteboard Mode"
   * option from main menu.
   */
  public void whiteBoard()
  {
    whiteBoardMode = true;
    while(running){
      try {runner.sleep(13);}
      catch (Exception e) {}
      graphics.paintField(gBuffer);
      graphics.displayPlayerPositions();
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
    markerMode = false;
  }

  /**
   * Function that runs when user selected "Create Playbook"
   * option from main menu.
   */
  public void createBook() {}

  /**
   * Function that runs when user selected "Load Playbook"
   * option from main menu.
   */
  public void loadBook(){}

  /**
   * Function that runs when user selected "Export Playbook"
   * option from main menu.
   */
  public void exportBook(){}

  /**
   * Function that runs when user selected "View Tutorial"
   * option from main menu.
   */
  public void viewTutorial(){}
  
  
  /**
   * Function that is automatically called
   * continuously to repaint graphics
   */
  public void update(Graphics g)
  {
    graphics.paint(g);
  }
}
