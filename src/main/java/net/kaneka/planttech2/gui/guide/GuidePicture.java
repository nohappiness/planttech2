package net.kaneka.planttech2.gui.guide;

import net.minecraft.resources.ResourceLocation;

public class GuidePicture
{
	private ResourceLocation resloc; 
	private int xStart, yStart, width, height; 
	
	public GuidePicture(ResourceLocation resloc, int xStart, int yStart, int width, int height)
	{
		this.resloc = resloc; 
		this.xStart = xStart; 
		this.yStart = yStart; 
		this.width = width; 
		this.height = height; 
	}
	
	public ResourceLocation getResloc()
	{
		return resloc;
	}
	
	public int getXStart()
	{
		return xStart;
	}

	public int getYStart()
	{
		return yStart;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	
	public boolean isIn(int x, int y)
	{
		if(xStart <= x && x <= xStart + width)
		{
			if(yStart <= y && y <= yStart + height)
			{
				return true; 
			}
		}
		return false; 
	}
	
}
