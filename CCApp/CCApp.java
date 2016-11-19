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
import java.io.*;
import java.util.*;
import java.applet.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.PrintWriter;


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

  int playerDiameter;   // Player's circle/dot diameter
  boolean isMouseDraggingPlayer, whiteBoardMode, markerMode, lineDraw, freeDraw, sqDraw, circDraw, running, animating; // various booleans for dragging mouse, whiteboard mode, and animation
  ArrayList<Point> paint_coords; // coordinates for drawing straight lines
  ArrayList<Line> lines = new ArrayList<Line>(); // list of straight lines to draw in Whiteboard Mode
  ArrayList<Line> squares = new ArrayList<Line>(); // list of squares to draw in Whiteboard Mode
  ArrayList<Line> circles = new ArrayList<Line>(); // list of circles to draw in Whiteboard Mode
  ArrayList<Point> frees = new ArrayList<Point>();

  Player selectedPlayer;  // The player that has been most recently clicked on
  Team offensiveTeam;
  Team defensiveTeam;

  int dClkRes = 300;    // double-click speed in ms
  long timeMouseDown=0; // last mouse down time
  int lastX=0,lastY=0;  //  last x and y

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

    y = (int)(height/20*15);
    x = width/2;
    selectedFrame = 0;
    running = false;
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

    graphics = new CCAppGraphics(width,height,offensiveTeam,defensiveTeam);

    // Add mouse listeners to detect mouse interactions
    addMouseListener(this);
    addMouseMotionListener(this);

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
   * Function that detects
   */
  public boolean mouseDown(Event event,int x,int y)
  {
    long currentTime = event.when;
    /*Check is user has double-clicked*/
    if ((lastX==x) && (lastY==y) && ((event.when-timeMouseDown) < dClkRes))
    {
        // Find the player on offensive team that was right clicked on
      Player newSelectedPlayer = offensiveTeam.findPlayerAtPoint(x, y);
      if(newSelectedPlayer != null) // A player on offensive team was right clicked on
      {
        // Keep track of player that was recently clicked on
        selectedPlayer = newSelectedPlayer;
      }
      else  // A player on offensive team that was NOT clicked on
      {
        // Find the player on defensive team team that was right clicked on
        newSelectedPlayer = defensiveTeam.findPlayerAtPoint(x, y);
        if(newSelectedPlayer != null) // A player on Defensive team was right clicked on
        {
          // Keep track of player that was recently clicked on
          selectedPlayer = newSelectedPlayer;
        }
      }

      offensiveTeam.setBall();
      defensiveTeam.setBall();
      selectedPlayer.setBall(true);

      offensiveTeam.setBallAtFrame(selectedFrame);
      defensiveTeam.setBallAtFrame(selectedFrame);
      selectedPlayer.setBallAtFrame(selectedFrame, true);
    } else {
        timeMouseDown = event.when;
        lastX=x;
        lastY=y;
    }
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
        graphics.handleInput(selectedPlayer,selectedFrame,playerDiameter);
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
          graphics.handleInput(selectedPlayer,selectedFrame,playerDiameter);
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
    else if(markerMode && freeDraw)
    {
      frees.add(new Point(e.getX(), e.getY()));
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
	      "Save Play",
	      "Load Play",
	    // Get choice from user
	    int choice = JOptionPane.showOptionDialog(null,
	      "Welcome to Coach's Corner!",
	      "CCApp",
	      JOptionPane.DEFAULT_OPTION,
	      JOptionPane.INFORMATION_MESSAGE,
	      icon,
	      options,
	      options[5]);

	    running = true;

	    // Interpret the user's choice
	    if(choice == 0){
	      createPlay();
	    }else if(choice == 1){
	      animating = true;
	      runPlay();
	      animating = false;
	    }else if(choice == 2){
	      whiteBoard();
	    }else if(choice == 3){
	      savePlay();
	    }else if(choice == 4){
	      loadPlay();
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
   * Function that runs when user selected "Create Play"
   * option from main menu.
   */
  public void createPlay()
  {
    offensiveTeam.setPositions(0);
    defensiveTeam.setPositions(0);
    running = true;
    while(running)
    {
      try {runner.sleep(13);}
      catch (Exception e) {}
      graphics.paintField(gBuffer);
      graphics.displayFrameMenu(gBuffer,selectedFrame);
      graphics.displayPlayerPositions(gBuffer);

      repaint();
    }
  }

   /**
   * Function that runs when user selected "Run Play"
   * option from main menu.
   */
  public void runPlay()
  {
    boolean passing = false;
    Point ballPos = new Point(-1,-1);
    Point startPos = new Point(-1,-1);
    Point finalPos = new Point(-1,-1);
    double velX = 0;
    double velY = 0;

    for(int i = 0; i < graphics.numberOfFrames - 1; i++)
    {
      offensiveTeam.setPositions(i);
      defensiveTeam.setPositions(i);
      graphics.calculateVelocity(i);

      Player hasBallThisFrame = offensiveTeam.getBallAtFrame(i);
      if(hasBallThisFrame == null)
        hasBallThisFrame = defensiveTeam.getBallAtFrame(i);

      int ballThisFrameIndex = offensiveTeam.getBallAtFrameIndex(i);
      if(ballThisFrameIndex == -1)
        ballThisFrameIndex = defensiveTeam.getBallAtFrameIndex(i);

      Player hasBallNextFrame = offensiveTeam.getBallAtFrame(i+1);
      if(hasBallNextFrame == null)
      hasBallNextFrame = defensiveTeam.getBallAtFrame(i+1);

      int ballNextFrameIndex = offensiveTeam.getBallAtFrameIndex(i+1);
      if(ballNextFrameIndex == -1)
        ballNextFrameIndex = defensiveTeam.getBallAtFrameIndex(i+1);

      if(ballThisFrameIndex != ballNextFrameIndex)
      {
        passing = true;
        ballPos = hasBallThisFrame.getPositionAtFrame(i);
        offensiveTeam.setBall();
        defensiveTeam.setBall();
        startPos = hasBallThisFrame.getPositionAtFrame(i);
        finalPos = hasBallNextFrame.getPositionAtFrame(i+1);
        velX = 0;
        velY = 0;
      }else{
        passing = false;
      }

      for(int j = 0; j < graphics.frameTime; j++)
      {
        try {runner.sleep(13);}
        catch (Exception e) {}
        graphics.updatePlayerPositions(i,j);

        graphics.paintField(gBuffer);
        graphics.displayPlayerPositions(gBuffer);
        graphics.displayFrameMenu(gBuffer,i+1);
        
        if(passing)
        {
          gBuffer.setColor(new Color(102,51,0));
          gBuffer.fillOval(ballPos.getX(),ballPos.getY(),16,26);
          gBuffer.setColor(Color.white);
          gBuffer.drawLine(ballPos.getX()+8,ballPos.getY()+4,ballPos.getX()+8,ballPos.getY()+22);
          gBuffer.drawLine(ballPos.getX()+5,ballPos.getY()+13,ballPos.getX()+11,ballPos.getY()+13);
          gBuffer.drawLine(ballPos.getX()+5,ballPos.getY()+18,ballPos.getX()+11,ballPos.getY()+18);
          gBuffer.drawLine(ballPos.getX()+5,ballPos.getY()+8,ballPos.getX()+11,ballPos.getY()+8);

          velX += ((double)finalPos.getX() - (double)startPos.getX())/((double)graphics.frameTime);
          velY += ((double)finalPos.getY() - (double)startPos.getY())/((double)graphics.frameTime);
          ballPos.setX(startPos.getX() + (int)velX);
          ballPos.setY(startPos.getY() + (int)velY);
        }

        repaint();
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
      graphics.displayPlayerPositions(gBuffer);
      graphics.drawLines(gBuffer, lines, squares, circles, frees);
      graphics.whiteBoardMenu(gBuffer,markerMode,lineDraw,freeDraw,sqDraw,circDraw);

      repaint();
    }
    markerMode = false;
  }

  /**
   * Function that runs when user selected "Create Playbook"
   * option from main menu.
   */
  public void savePlay()
  {
    SaveFileChooser saveNewFile = new SaveFileChooser();
    saveNewFile.savePlay(offensiveTeam, defensiveTeam);
  }

  /**
   * Function that runs when user selected "Load Playbook"
   * option from main menu.
   */
  public void loadPlay()
  {
    OpenFileChooser loadNewFile = new OpenFileChooser();
    ArrayList<Team> loadedTeams = loadNewFile.loadPlay();
    if(loadedTeams == null)
    {
      return;
    }
    offensiveTeam = loadedTeams.get(0);
    defensiveTeam = loadedTeams.get(1);
    System.out.println("Size of team: " + offensiveTeam.getSize());
    graphics = new CCAppGraphics(width,height,offensiveTeam,defensiveTeam);
    graphics.numberOfFrames = offensiveTeam.getPlayers().get(0).getNumberOfFrames();
  }

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
    paint(g);
  }

  public void paint(Graphics g)
  {
    g.drawImage (Buffer,0,0, this);
  }

}
