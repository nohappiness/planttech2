package net.kaneka.planttech2.gui;

import java.util.ArrayList;
import java.util.List;
import net.kaneka.planttech2.gui.buttons.CustomGuiButton;

public class GuiGuideOverview extends GuiGuideBase
{
	private String[] keys = new String[13]; 
	List<String> lines = new ArrayList<String>(); 
	private double charsPerLine = 40D; 
	private double linesPerSite = 14D; 
	private int site = 0; 
	private int maxSites = 1; 
	
	
	public GuiGuideOverview()
	{
		super(12-8, true, "screen.guideoverview");
		
	}
	
	@Override
	public void init()
	{
		super.init();
		for (int i = 0; i < 8; i++)
		{
		    addButton(new CustomGuiButton(i, this.guiLeft + 28, this.guiTop + 10 + i * 22, 100, 20, "TEST") 
		    {
			@Override
			public void onClick(double mouseX, double mouseY)
			{
			    GuiGuideOverview.this.buttonClicked(id); 
			    super.onClick(mouseX, mouseY);
			}
		    });
		}
		addButton(new CustomGuiButton(8, this.guiLeft + 150 , this.guiTop + 170,  100, 20, translateUnformated("gui.last")){
			@Override
			public void onClick(double mouseX, double mouseY)
			{
			    GuiGuideOverview.this.buttonClicked(id); 
			    super.onClick(mouseX, mouseY);
			}
		    });
		addButton(new CustomGuiButton(9, this.guiLeft + 285 , this.guiTop + 170,  100, 20, translateUnformated("gui.next")){
			@Override
			public void onClick(double mouseX, double mouseY)
			{
			    GuiGuideOverview.this.buttonClicked(id); 
			    super.onClick(mouseX, mouseY);
			}
		    });
		buttons.get(8).active = false; 
		buttons.get(8).visible = false;
		buttons.get(9).active = false;
		buttons.get(9).visible = false;
		keys[0] = "start"; 
		keys[1] = "plantium"; 
		keys[2] = "crops"; 
		keys[3] = "traits"; 
		keys[4] = "crossbreeding"; 
		keys[5] = "temperature"; 
		keys[6] = "analyzer"; 
		keys[7] = "machines"; 
		keys[8] = "cables"; 
		keys[9] = "upgrades"; 
		keys[10] = "particles"; 
		keys[11] = "genetic_engineering";
		keys[11] = "armor";
		updateButtons();
	}
	
	protected void buttonClicked(int button)
	{
		if(button >= 0 && button < 8)
		{
			selectedId = button + scrollPos;
			convertString(translateUnformated("guide." + keys[selectedId] + ".text"));
		}
		else if(button == 8)
		{
			if(site > 0)
			{
				site--;
				buttons.get(9).active = true; 
				buttons.get(9).visible = true;
				if(site == 0)
				{
					buttons.get(8).active = false; 
					buttons.get(8).visible = false;
				}
			}
		}
		else if(button == 9)
		{
			if(site < maxSites - 1)
			{
				site++; 
				buttons.get(8).active = true; 
				buttons.get(8).visible = true;
				if(site == maxSites)
				{
					buttons.get(9).active = false; 
					buttons.get(9).visible = false;
				}
			}
		}
	
	}
	
	@Override
	protected void drawStrings()
	{
		if(selectedId == -1)
		{
			this.drawCenteredString(translateUnformated("gui.non_selected"), this.guiLeft + 255, this.guiTop + 90);	
		}
		else
		{
			this.drawCenteredString(translateUnformated("guide." + keys[selectedId] + ".header"), this.guiLeft + 260, this.guiTop + 10);
			for(int i = 0; i < linesPerSite; i++)
			{
				if(lines.size() > i + site * linesPerSite)
				{
					this.drawLine(lines.get((int) (i + site * linesPerSite)), this.guiLeft + 160, this.guiTop + 25 + 10 * i);
				}
			}
		}
	}
	
	protected void drawLine(String text, int x, int y)
	{
	    font.drawString(text, x, y, Integer.parseInt("00e803",16));
	}
	
	protected void convertString(String string)
	{
		String[] array = string.split("=nl");
		lines.clear();
		String[] array2; 
		String combined = ""; 
		int charcounter = 0; 
		for(int i = 0; i < array.length; i++)
		{
			if(array[i].length() > 0)
			{
				if(Math.ceil(((double)array[i].length())/charsPerLine) > 1) 
				{
					array2 = array[i].split("\\s+");
					combined = ""; 
					charcounter = 0; 
					for(int k = 0; k < array2.length; k++)
					{
						if(array2[k].length() + charcounter > charsPerLine)
						{
							lines.add(combined); 
							combined = array2[k] + " "; 
							charcounter = array2[k].length() + 1; 
						}
						else
						{
							combined += array2[k] + " "; 
							charcounter += array2[k].length() + 1; 
						}
					}
					lines.add(combined); 
				}
				else
				{
					lines.add(array[i]);
				}
			}
			else
			{
				lines.add("");
			}
		}
		site = 0; 
		maxSites = (int) (Math.ceil((double)lines.size()/linesPerSite));
		buttons.get(8).active = false; 
		buttons.get(8).visible = false;
		if(maxSites > 1)
		{
			buttons.get(9).active = true; 
			buttons.get(9).visible = true;
		}
		else
		{
			buttons.get(9).active = false; 
			buttons.get(9).visible = false;
		}
	}
	
	@Override
	protected void updateButtons()
	{
		for(int i = 0; i < 8; i++)
		{
			this.buttons.get(i).setMessage(translateUnformated("guide." + keys[scrollPos + i] + ".header"));
		}
	}
	
	@Override 
	protected void drawCenteredString(String string, int posX, int posY)
	{
		font.drawString(string, posX - (font.getStringWidth(string) / 2), posY, Integer.parseInt("00e803",16));
	}
}
