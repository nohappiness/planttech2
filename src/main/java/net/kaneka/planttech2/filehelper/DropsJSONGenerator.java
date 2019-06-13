package net.kaneka.planttech2.filehelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.kaneka.planttech2.blocks.BlockBase;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.item.Item;

public class DropsJSONGenerator
{
	int maxLines = 500;
	int nextLine = 0;
	String[] lines = new String[maxLines];

	private void saveFile(String file)
	{

		try
		{
			File f = new File("json/" + file + ".json");
			f.createNewFile();
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < nextLine; i++)
			{
				bw.write(lines[i]);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		lines = new String[maxLines];
		nextLine = 0;
	}

	private void addLine(String line)
	{
		this.lines[nextLine] = line;
		this.nextLine++;
	}

	private void createFile(Item item)
	{
		addLine("{");
		addLine("	  \"type\": \"minecraft:block\",");
		addLine("	  \"pools\": [");
		addLine("	    {");
		addLine("		  \"name\": \""+item.getRegistryName()+"\","); 
		addLine("	      \"rolls\": 1,");
		addLine("	      \"entries\": [");
		addLine("	        {");
		addLine("	          \"type\": \"minecraft:item\",");
		addLine("	          \"name\": \""+ item.getRegistryName()+"\"");
		addLine("	        }");
		addLine("	      ],");
		addLine("	      \"conditions\": [");
		addLine("	        {");
		addLine("	          \"condition\": \"minecraft:survives_explosion\"");
		addLine("	        }");
		addLine("	      ]");
		addLine("	    }");
		addLine("	  ]");
		addLine("	}");

		saveFile(item.getRegistryName().toString().replace("minecraft:", "").replace("planttech2:", ""));
	}

	public void defaultValues()
	{
		for(BlockBase block:ModBlocks.BLOCKITEMS)
		{
			@SuppressWarnings("deprecation")
			Item item = Item.getItemFromBlock(block); 
			if(item != null)
			{
				createFile(item);
			}
		}
	}
}
