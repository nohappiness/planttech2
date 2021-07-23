package net.kaneka.planttech2.gui.guide;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class GuideEntry
{
	private String name; 
	private GuideMenu menu; 
	private List<GuidePicture> piclist = new ArrayList<GuidePicture>(); 
	private List<GuideString> stringList; 
	
	public GuideEntry(String name)
	{
		this.name = name; 
	}
	
	public GuideEntry setMenu(GuideMenu submenu)
	{
		this.menu = submenu; 
		return this; 
	}
	
	public String getNameString()
	{
		return name; 
	}
	
	public String getName()
	{
		return new TranslationTextComponent("guide.planttech2." + name + ".header").getString();
	}
	
	public ITextComponent getHeader()
	{
		return new TranslationTextComponent("guide.planttech2." + name + ".header").withStyle(TextFormatting.UNDERLINE, TextFormatting.ITALIC);
	}
	
	public GuideMenu getMenu()
	{
		return menu; 
	}
	
	public List<GuidePicture> getPictures()
	{
		return piclist; 
	}
	
	public GuideEntry addPicture(ResourceLocation resloc, int xStart, int yStart, int width, int height)
	{
		piclist.add(new GuidePicture(resloc, xStart, yStart, width, height));
		return this; 
	}
	
	public String getText()
	{
		return new TranslationTextComponent("guide.planttech2." + name + ".text").getString();
	}
	
	public List<GuideString> getTextFormated(FontRenderer font)
	{
		if(stringList == null)
		{
    		stringList = new ArrayList<GuideString>(); 
    		int lines = 19; 
    		int lineWidth = 395; 
    		boolean isInPic = false; 
    		int startX = 0;
    		
    		for(int line = 0; line < lines; line++)
    		{
        		for(int x = 0; x < lineWidth; x += 2)
        		{
        			isInPic = false; 
        			for(int y = 0; y < 2; y++)
        			{
        				for(GuidePicture pic: piclist)
        				{ 
        					if(pic.isIn(x, line * 10 + y * font.lineHeight))
        					{
        						isInPic = true; 
        					}
        				}
        			}
        			
        			if(isInPic)
        			{
        				if(startX >=  0)
        				{
        					stringList.add(new GuideString(startX, line, x - startX));
        					startX = -1;
        				}
        			}
        			else
        			{
        				if(startX < 0)
        				{
        					startX = x; 
        				}
        			}
        		}
        		if(!isInPic)
        		{
        			stringList.add(new GuideString(startX, line, lineWidth - startX));
        		}
        		startX = 0; 
    		}
    		
    		String[] words = getText().split("\\s+");
    		int actString = 0;  
    		GuideString guideString = stringList.get(actString); 
    		for(String word: words)
    		{
    			if(word.equals("<br>"))
    			{
    				int actLine = guideString.getLine(); 
    				for(int counter = 0; counter < 50; counter++)
        			{
    					if(stringList.size() > actString + 1)
        				{
        					actString++; 
        					guideString = stringList.get(actString); 
        				}
    					else
        				{
    						stringList.add(new GuideString(0, guideString.getLine() + 1, lineWidth)); 
        					actString++; 
        					guideString = stringList.get(actString); 
        				}
    					
    					if(guideString.getLine() > actLine)
    					{
    						break; 
    					}
        			}
    			}
    			else 
    			{
        			for(int counter = 0; counter < 50; counter++)
        			{
            			if(!guideString.appendWord(font, word))
            			{
            				if(stringList.size() > actString + 1)
            				{
            					actString++; 
            					guideString = stringList.get(actString); 
            				}
            				else
            				{
            					stringList.add(new GuideString(0, guideString.getLine() + 1, lineWidth)); 
            					actString++; 
            					guideString = stringList.get(actString); 
            				}
            				
            			}
            			else
            			{
            				break; 
            			}
        			}
    			}
    		}
		}
    		
		return stringList; 
	}
}
