
import java.io.File;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.*;
import java.util.*;

public class SaveFileChooser {

	File selectedFile;

	public SaveFileChooser()
	{

		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new File("."));

		//jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = jFileChooser.showSaveDialog(new JFrame());


		if (result == JFileChooser.APPROVE_OPTION)
		{
	    selectedFile = jFileChooser.getSelectedFile();
	    System.out.println("Selected file: " + selectedFile.getAbsolutePath());

		}
	}

	public void savePlay(Team offense, Team defense)
	{
		if(selectedFile == null)
		{
			return;
		}

		ArrayList<Team> teams = new ArrayList<Team>();
		teams.add(offense);
		teams.add(defense);

		try
		{
			FileOutputStream newFile = new FileOutputStream(selectedFile.getAbsolutePath());

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
				if (newFile != null)
				{
					newFile.close();
					//out.close();
				}
			}

		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}

}
