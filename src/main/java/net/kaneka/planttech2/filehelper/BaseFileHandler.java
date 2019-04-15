package net.kaneka.planttech2.filehelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BaseFileHandler
{
    	String dir; 
    	String path; 
	File file;

	public List<String> loadFile()
	{
	    this.dir = getDirection(); 
	    this.path = getPath(); 
	    this.file = new File(path); 
	    
		try
		{
			if (!pathExists())
			{
				this.createFile();
				FileWriter fw = new FileWriter(path, true);
				BufferedWriter bw = new BufferedWriter(fw);
				List<String> defaults = this.defaultValues();
				int size = defaults.size();
				for (int i = 0; i < size; i++)
				{
					String str = defaults.get(i).toString();
					bw.write(str);
					bw.newLine();
				}
				bw.close();
				fw.close();
				return defaults;
			}
			Scanner s = new Scanner(file);
			ArrayList<String> list = new ArrayList<String>();
			while (s.hasNextLine())
			{
				list.add(s.nextLine());
			}
			s.close();
			return list;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean pathExists()
	{
		if (file.exists())
		{
			return true;
		}
		return false;
	}

	public void createFile()
	{
		try
		{
			File newDir = new File(dir);
			if (!newDir.exists())
			{
				newDir.mkdirs();
			}
			file.createNewFile();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getDirection()
	{
	    return "config/planttech2";
	}
	
	public String getPath()
	{
	    return "";
	}

	protected List<String> defaultValues()
	{
		return null;
	}
}
