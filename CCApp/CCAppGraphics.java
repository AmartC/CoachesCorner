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
    
  /**
   * Constructor for objects of class CCAppGraphics
   */
  public CCAppGraphics(int w, int h, Team A, Team B)
  {
    width = w;
    height = h;
    offensiveTeam = A;
    defensiveTeam = B;
      
    frameButtonSize = 25;
    frameButtonIndentation = 10;
    frameMenuPositionX = 20;
    frameMenuPositionY = height - frameButtonSize - 20;
    ONE_YARD = height/20;
    CENTER_OF_FIELD = width/2;
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
  public void displayPlayerPositions(Graphics gBuffer)
  {
    offensiveTeam.displayTeam(gBuffer);
    defensiveTeam.displayTeam(gBuffer);
  }

  /**
   * Function that displays the frame memu.
   */
  public void displayFrameMenu(Graphics gBuffer)
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
   * Function responsible for handling the event when a user
   * right clicks on a player on field.
   * Essentially allows users to modify attributes of the player
   * object.
   */
  public void handleInput(Player selectedPlayer, int selectedFrame, int playerDiameter)
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
    
    JLabel ballLabel = new JLabel("Has Ball?");    
    String[] boolStrings = { "False", "True" };
    JComboBox newBall = new JComboBox(boolStrings);
    if(selectedPlayer.hasBall())
        newBall.setSelectedIndex(1);
    else
         newBall.setSelectedIndex(0);
    panel.add(ballLabel);
    panel.add(newBall);

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
      
      if(newBall.getSelectedItem() == "True")
      {          
          offensiveTeam.setBall();
          defensiveTeam.setBall();
          selectedPlayer.setBall(true);
          
          offensiveTeam.setBallAtFrame(selectedFrame);
          defensiveTeam.setBallAtFrame(selectedFrame);
          selectedPlayer.setBallAtFrame(selectedFrame, true);
      }
    }
  }
  
  
  
  /**
   * Function that displays the lines that have been drawn by the user.
   */
  public void drawLines(Graphics gBuffer, ArrayList<Line> lines, ArrayList<Line> squares, ArrayList<Line> circles, ArrayList<ArrayList<Integer> > frees){
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
  
  public void whiteBoardMenu(Graphics gBuffer, boolean markerMode, boolean lineDraw, boolean freeDraw, boolean sqDraw, boolean circDraw)
  {
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
    }
}
