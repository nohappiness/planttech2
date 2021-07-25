package net.kaneka.planttech2.gui.guide;

import net.minecraft.client.gui.Font;

public class GuideString
{
	private int x, line, width;  
	String string = ""; 

	public GuideString(int x, int line, int width)
	{
		this.x = x; 
		this.line = line; 
		this.width = width; 
	}
	
	public int getX()
	{
		return x; 
	}
	
	public int getLine()
	{
		return line; 
	}
	
	public String getString()
	{
		return string; 
	}
	
	public boolean appendWord(Font font, String word)
	{
		if(font.width(string) + font.width(" ") + font.width(word) <= width)
		{
			string += " " + word; 
			return true; 
		}
		return false; 
	}
	
	public String fillWithX(Font font)
	{
		String string = ""; 
		for(int i = 0; i < 100; i++)
		{
			if(font.width(string) + font.width("X") < width)
			{
				string += "X"; 
			}
		}
		return string; 
	}
}
