package net.kaneka.planttech2.gui.guide;

import net.minecraft.client.gui.FontRenderer;

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
	
	public boolean appendWord(FontRenderer font, String word)
	{
		if(font.getStringWidth(string) + font.getStringWidth(" ") + font.getStringWidth(word) <= width)
		{
			string += " " + word; 
			return true; 
		}
		return false; 
	}
	
	public String fillWithX(FontRenderer font)
	{
		String string = ""; 
		for(int i = 0; i < 100; i++)
		{
			if(font.getStringWidth(string) + font.getStringWidth("X") < width)
			{
				string += "X"; 
			}
		}
		return string; 
	}
}
