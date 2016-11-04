/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/22/2016
 */
import java.awt.*;
import java.util.*;


/**
 * The Team class holds the structure of the football team and holds all of
 * its players in an organized manner.
 */
public class Team
{
  Color color;
  String name;
  ArrayList<Player> players;



  /**
    * Constructor for objects of class Team which take in a team name and
    * jersey color.
    */
  public Team(String teamName, Color teamColor)
  {
    name = teamName;
    color = teamColor;
    players = new ArrayList<Player>();
  }

  /**
    * Function which retrieves the number of players on the team.
    */
  public int getSize()
  {
    return players.size();
  }

  /**
    * Function which retrieves a list of all the players on the team.
    */
  public ArrayList<Player> getPlayers()
  {
    return new ArrayList<Player>(players);
  }

  /**
    * Function which adds a new player to the team.
    */
  public void addPlayer(Player newPlayer)
  {
    newPlayer.setColor(color);
    players.add(newPlayer);
  }

  /**
    * Function which adds a new player to the team, but first creates a Player
    * object for that player, adds the player to its list of players and
    * finally returns that player.
    */
  public Player addNewPlayer(Point position, String playerName, int playerNumber, int playerDiameter)
  {
    Player newPlayer = new Player(position.getX(), position.getY(), playerName, playerNumber, color, playerDiameter);
    players.add(newPlayer);
    return newPlayer;
  }

  /**
    * Function which adds a new player to the team, but first creates a Player
    * object for that player, adds the player to its list of players and
    * finally returns that player. Instead of taking in a Point object for
    * the player's coordinates, it instead takes in the individual x and y
    * coordinates.
    */
  public Player addNewPlayer(int x, int y, String playerName, int playerNumber, int playerDiameter)
  {
    Player newPlayer = new Player(x, y, playerName, playerNumber, color, playerDiameter);
    players.add(newPlayer);
    return newPlayer;
  }


  /**
    * Function which will add a new frame to each player on the team.
    */
  public void addNewFrameToPlayers()
  {
    for(int i = 0; i < players.size(); i++)
    {
      (players.get(i)).addFrame();
    }
  }

  /**
    * Function which will remove the last frame from each player on the team.
    */
  public void removeLastFrameFromPlayers()
  {
    for(int i = 0; i < players.size(); i++)
    {
      (players.get(i)).removeLastFrame();
    }
  }

  /**
    * Function which will return the player object at a specific point.
    * If no player found, return null.
    */
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
  /**
    * Function which will return the player object at a specified set of coordinates.
    * If no player found, return null.
    */
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

  /**
    * Function which will display all of the members of the team on the field.
    */
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

  /**
    * Function which will display all of the members of the team on the field
    * at the specified frame.
    */
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

  /**
    * Function which will updates its team's position at the specified frame.
    */
  public void updateTeamAtFrame(int frame, int step)
  {
    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);
      currentPlayer.setX((int)(currentPlayer.getPositionAtFrame(frame).getX()+step*currentPlayer.getVelX()));
      currentPlayer.setY((int)(currentPlayer.getPositionAtFrame(frame).getY()+step*currentPlayer.getVelY()));
    }
  }

  /**
    * Function which set each team member's velocity for the animation.
    */
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

  /**
    * Function which will add a frame for each team member.
    */
  public void addFrame(int frame)
  {
    for(int i = 0; i < players.size(); i++)
    {
      Player currentPlayer = players.get(i);
      Point defaultPos = currentPlayer.getPositionAtFrame(frame-1);
      currentPlayer.addFrame(defaultPos);
    }
  }

  /**
    * Function which will set each team members position based on a specified frame.
    */
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
