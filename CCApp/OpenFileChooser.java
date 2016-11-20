
import java.io.File;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.*;
import java.util.*;


/**
 * The OpenFileChooser class holds the selected file to be opened.
 * Also provides an interface (file browser) for user to select a file.
 */
public class OpenFileChooser {

  File selectedFile;

  /**
   * Constructor for OpenFileChooser opens a file browser
   * to allow user to select a file to load.
   */
	public OpenFileChooser()
  {

		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new File("."));

    // Show the file browser
		int result = jFileChooser.showOpenDialog(new JFrame());

    // Check if user selected OPEN
		if (result == JFileChooser.APPROVE_OPTION)
    {
		    selectedFile = jFileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}


  /**
   * Function responsible for deserializing Team objects,
   * and returns them in an array list.
   */
  public ArrayList<Team> loadPlay()
  {
    // If file was not selected
    if(selectedFile == null)
    {
      return null;
    }

    // Check if selected file is not a ".ser" file
    if(!selectedFile.getAbsolutePath().contains(".ser"))
    {
      return null;
    }

    // Array that stores the deserialized teams
    ArrayList<Team> teams = new ArrayList<Team>();

    // Used to load the file
    FileInputStream loadedFile = null;

    // Tries to deserialize the file
    try
    {
      // Load the file for input streaming
      loadedFile = new FileInputStream(selectedFile.getAbsolutePath());

      // Tries to obtain each Team object from file
      try
      {
        // Loop until end of file
        while (true)
        {
          ObjectInputStream in = new ObjectInputStream(loadedFile);
          teams.add((Team)in.readObject());
        }
      }
      catch(ClassNotFoundException i)
      {
        i.printStackTrace();
      }
      catch(EOFException e)
      {
        /* Ignore, is as expected */
      }
      finally
      {
        // Close the file if opened
        if (loadedFile != null)
        {
          loadedFile.close();
        }
      }
    }
    catch (IOException i)
		{
			i.printStackTrace();
		}

    return teams;
  }

}
