/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/22/2016
 */
import java.awt.*;
import java.util.*;


public class Team
{
  Color color;
  String name;
  ArrayList<Player> players;


  public Team(String teamName, Color teamColor)
  {
    name = teamName;
    color = teamColor;
    players = new ArrayList<Player>();
  }

  public int getSize()
  {
    return players.size();
  }

  public ArrayList<Player> getPlayers()
  {
    return new ArrayList<Player>(players);
  }

  public void addPlayer(Player newPlayer)
  {
    newPlayer.setColor(color);
    players.add(newPlayer);
  }

  // Creates a new player, add to team, then return that player.
  public Player addNewPlayer(Point position, String playerName, int playerNumber, int playerDiameter)
  {
    Player newPlayer = new Player(position.getX(), position.getY(), playerName, playerNumber, color, playerDiameter);
    players.add(newPlayer);
    return newPlayer;
  }

  public Player addNewPlayer(int x, int y, String playerName, int playerNumber, int playerDiameter)
  {
    Player newPlayer = new Player(x, y, playerName, playerNumber, color, playerDiameter);
    players.add(newPlayer);
    return newPlayer;
  }

  // Return the player object located at target location, otherwise return null if no player on team is at target location
  public Player findPlayerAtPoint(Point target)
  {
    int targetX = target.getX();
    int targetY = target.getY();

    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);
      int playerPositionX = currentPlayer.getX();
      int playerPositionY = currentPlayer.getY();
      int playerCircleDiameter = currentPlayer.getDiameter();

      if (playerPositionX < targetX && targetX < playerPositionX + playerCircleDiameter && playerPositionY < targetY && targetY < playerPositionY+playerCircleDiameter)
      {
        return currentPlayer;
      }
    }

    return null;
  }

  public Player findPlayerAtPoint(int x, int y)
  {
    int targetX = x;
    int targetY = y;

    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);
      int playerPositionX = currentPlayer.getX();
      int playerPositionY = currentPlayer.getY();
      int playerCircleDiameter = currentPlayer.getDiameter();

      if (playerPositionX < targetX && targetX < playerPositionX + playerCircleDiameter && playerPositionY < targetY && targetY < playerPositionY+playerCircleDiameter)
      {
        return currentPlayer;
      }
    }

    return null;
  }

  public void displayTeam(Graphics gBuffer)
  {
    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);
      int playerPositionX = currentPlayer.getX();
      int playerPositionY = currentPlayer.getY();
      int playerCircleDiameter = currentPlayer.getDiameter();
      gBuffer.setColor(color);
      gBuffer.fillOval(playerPositionX, playerPositionY, playerCircleDiameter, playerCircleDiameter);
    }
  }

  public void displayTeamAtFrame(Graphics gBuffer, int frame)
  {
    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);
      Point playerPosition = currentPlayer.getPositionAtFrame(frame);
      int playerCircleDiameter = currentPlayer.getDiameter();
      gBuffer.setColor(color);
      gBuffer.fillOval(playerPosition.getX(), playerPosition.getY(), playerCircleDiameter, playerCircleDiameter);
    }
  }
  
  public void updateTeamAtFrame(int frame, int step)
  {
    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);      
      currentPlayer.setX((int)(currentPlayer.getPositionAtFrame(frame).getX()+step*currentPlayer.getVelX())); 
      currentPlayer.setY((int)(currentPlayer.getPositionAtFrame(frame).getY()+step*currentPlayer.getVelY()));
    }
  }
  
  public void calculateVelocity(int frame, int frameTime)
  {
    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);
      Point startPos = currentPlayer.getPositionAtFrame(frame);
      Point finalPos = currentPlayer.getPositionAtFrame(frame+1);
      double xStep = ((double)finalPos.getX() - (double)startPos.getX())/((double)frameTime);
      double yStep = ((double)finalPos.getY() - (double)startPos.getY())/((double)frameTime);
      currentPlayer.setVel(xStep,yStep);
    }
  }
  
  public void addFrame(int frame)
  {
    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);
      Point defaultPos = currentPlayer.getPositionAtFrame(frame-1);
      currentPlayer.addFrame(defaultPos);
    }
  }
  
  public void setPositions(int frame)
  {
    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);
      Point startingPos = currentPlayer.getPositionAtFrame(frame);
      currentPlayer.setXY(startingPos);
    }
  }
}
