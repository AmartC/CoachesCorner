/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/22/2016
 */

import java.awt.*;
import java.util.*;
import java.applet.*;
import javax.swing.*;

public class CCAppGraphics// extends Applet
{  
  // instance variables - replace the example below with your own
  Image Buffer; // Used to paint all graphics onto window
  Graphics gBuffer;
  int width, height; 
  int frameButtonSize;  // These are used to position the frame menu buttons
  int frameButtonIndentation;
  int frameMenuPositionX;
  int frameMenuPositionY;
  double ONE_YARD;  // Stores how many pixels are in a yard on field
  int CENTER_OF_FIELD;
  int numberOfFrames; // Total number of frames in animation
  int frameTime;  //number of iterations to animate a frame
  
  Team offensiveTeam;
  Team defensiveTeam;
  
  CCApp app;
  
  boolean running;
    
  /**
   * Constructor for objects of class CCAppGraphics
   */
  public CCAppGraphics(Graphics g, Image buff, int w, int h, Team A, Team B, CCApp C)
  {
    gBuffer = g;
    Buffer = buff;
    width = w;
    height = h;
    offensiveTeam = A;
    defensiveTeam = B;
    app = C;
      
    frameButtonSize = 25;
    frameButtonIndentation = 10;
    frameMenuPositionX = 20;
    frameMenuPositionY = height - frameButtonSize - 20;
    ONE_YARD = height/20;
    CENTER_OF_FIELD = width/2;
    running = false;
    numberOfFrames = 1;
    frameTime = 400;
  }

    /**
   * Function used to paint or draw the football field in the background
   */
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

  /**
   * Function that displays the players in both teams at their curret position.
   */
  public void displayPlayerPositions()
  {
    offensiveTeam.displayTeam(gBuffer);
    defensiveTeam.displayTeam(gBuffer);
  }

  /**
   * Function that displays the frame memu.
   */
  public void displayFrameMenu()
  {
    // Used for positioning of frame label
    int centerOfButton = frameButtonSize / 2;

    // Draw the remove ("-") frame button
    gBuffer.setColor(Color.gray);
    gBuffer.fillRect(frameMenuPositionX, frameMenuPositionY - frameButtonSize - frameButtonIndentation, frameButtonSize, frameButtonSize);
    gBuffer.setColor(Color.white);
    gBuffer.drawString("-", frameMenuPositionX + centerOfButton, frameMenuPositionY - frameButtonSize - frameButtonIndentation + centerOfButton);

    // Draw the add ("+") frame button
    gBuffer.setColor(Color.gray);
    gBuffer.fillRect(frameMenuPositionX + frameButtonSize + frameButtonIndentation, frameMenuPositionY - frameButtonSize - frameButtonIndentation, frameButtonSize, frameButtonSize);
    gBuffer.setColor(Color.white);
    gBuffer.drawString("+", frameMenuPositionX + frameButtonSize + frameButtonIndentation + centerOfButton, frameMenuPositionY - frameButtonSize - frameButtonIndentation + centerOfButton);

    // Used for X position of each frame number botton
    int indentationX = frameMenuPositionX;
    for(int i = 0; i < numberOfFrames; i++)
    {
      // Draw frame number button
      gBuffer.setColor(Color.white);
      gBuffer.fillRect(indentationX, frameMenuPositionY, frameButtonSize, frameButtonSize);

      // Label the frame number button
      gBuffer.setColor(Color.black);
      gBuffer.drawString("" + i, indentationX + centerOfButton, frameMenuPositionY + centerOfButton);

      // Calculate X position of next frame number button
      indentationX += frameButtonSize + frameButtonIndentation;
    }
  }

  

  /**
   * Function used for animation that updates the players'
   * position based on its current velocity
   */
  public void updatePlayerPositions(int frame, int frameStep)
  {
    offensiveTeam.updateTeamAtFrame(frame, frameStep);
    defensiveTeam.updateTeamAtFrame(frame, frameStep);
  }

  /**
   * Function used for animation that updates the players'
   * velocity based on the position of the frame they're in.
   */
  public void calculateVelocity(int frame)
  {
    offensiveTeam.calculateVelocity(frame, frameTime);
    defensiveTeam.calculateVelocity(frame, frameTime);
  }
  
  /**
   * Function that runs when user selected "Create Play"
   * option from main menu.
   */
  public void createPlay(Thread runner)
  {
    offensiveTeam.setPositions(0);
    defensiveTeam.setPositions(0);
    running = true;
    while(running)
    {
      try {runner.sleep(13);}
      catch (Exception e) {}
      paintField(gBuffer);
      displayFrameMenu();
      displayPlayerPositions();

      update(gBuffer);
    }
  }
    
   /**
   * Function that runs when user selected "Run Play"
   * option from main menu.
   */
  public void runPlay(Thread runner)
  {
    for(int i = 0; i < numberOfFrames - 1; i++)
    {
      offensiveTeam.setPositions(i);
      defensiveTeam.setPositions(i);
      calculateVelocity(i);
      for(int j = 0; j < frameTime; j++)
      {
        try {runner.sleep(13);}
        catch (Exception e) {}
        updatePlayerPositions(i,j);

        paintField(gBuffer);
        displayPlayerPositions();

        update(gBuffer);
      }
    }
  }
  
  /**
   * Function that is automatically called
   * continuously to repaint graphics
   */
  public void update(Graphics g)
  {
    paint(g);
  }

  /**
   * Function that essentially paints/displays
   * all graphics onto window.
   */
  public void paint(Graphics g)
  {
    g.drawImage (Buffer,0,0, app);
  }
}
