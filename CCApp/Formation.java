/**Authors:
 * Timothy Castiglia
 * Amartya Chakraborty
 * Ethan Fox
 * Aaron Cheng
 * Date: 10/23/2016
 */

import java.util.*;

public class Formation
{
    // instance variables - replace the example below with your own
    private String name;
    private ArrayList<Point> locations;

    public Formation(String formation_name, ArrayList<Player> players)
    {
      this.name = formation_name;
      Player current;
      for(int i = 0; i < players.size(); i++){
        locations.add(players.get(0).getPositionAtFrame(0));
      }    
    }
    
    public ArrayList<Point> getFormation(){
      return locations;       
    }
}
