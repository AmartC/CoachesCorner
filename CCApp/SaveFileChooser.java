
import java.io.File;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.*;
import java.util.*;


/**
 * The SaveFileChooser class holds the selected file to be saved.
 * Also provides an interface (file browser) for user to select a file.
 */
public class SaveFileChooser {

	File selectedFile;

	/**
   * Constructor for SaveFileChooser opens a file browser
   * to allow user to select a directory and a file name for saving.
   */
	public SaveFileChooser()
	{

		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new File("."));

		// Shows file browser
		int result = jFileChooser.showSaveDialog(new JFrame());

		// Check if user selected SAVE
		if (result == JFileChooser.APPROVE_OPTION)
		{
	    selectedFile = jFileChooser.getSelectedFile();
	    System.out.println("Selected file: " + selectedFile.getAbsolutePath());

		}
	}


	/**
   * Function responsible for serializing Team objects.
   */
	public void savePlay(Team offense, Team defense)
	{
		// If file was not selected
		if(selectedFile == null)
		{
			return;
		}

		// Array that stores the teams to be serialized
		ArrayList<Team> teams = new ArrayList<Team>();
		teams.add(offense);
		teams.add(defense);

    // Tries to serialize the teams
		try
		{
			// Create the file for output streaming
			FileOutputStream newFile = new FileOutputStream(selectedFile.getAbsolutePath());

			// Tries to serialize each Team object
			try
			{
				for (Team team : teams)
				{
					ObjectOutputStream out = new ObjectOutputStream(newFile);
					out.writeObject(team);
				}
			}
			finally
			{
				// Close the file if opened
				if (newFile != null)
				{
					newFile.close();
				}
			}

		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}

}
