
import java.io.File;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.*;
import java.util.*;

public class OpenFileChooser {

  File selectedFile;

	public OpenFileChooser()
  {

		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new File("."));

		//jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = jFileChooser.showOpenDialog(new JFrame());


		if (result == JFileChooser.APPROVE_OPTION)
    {
		    selectedFile = jFileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}

  public ArrayList<Team> loadPlay()
  {
    if(!selectedFile.getAbsolutePath().contains(".ser"))
    {
      return null;
    }

    ArrayList<Team> teams = new ArrayList<Team>();

    FileInputStream loadedFile = null;
    try
    {
      loadedFile = new FileInputStream(selectedFile.getAbsolutePath());
      try
      {
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
    //System.out.println("results = " + teams);
    return teams;
  }

}
