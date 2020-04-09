package net.kaneka.planttech2.gui.guide;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

public class Guide
{
	private int amountMainMenus = 0; 
	private List<GuideMenu> mainmenus = new ArrayList<GuideMenu>(); 
	
	public Guide()
	{
		init(); 
	}
	
	private void init()
	{
		addMenu(new GuideMenu("Main1").addEntry(new GuideEntry("test1"))
											.addEntry(new GuideEntry("test2"))
											.addEntry(new GuideEntry("test3"))
											.addEntry(new GuideEntry("test4")));
		addMenu(new GuideMenu("Main2").addEntry(new GuideEntry("crops"))
                            				.addEntry(new GuideEntry("traits").addPicture(new ResourceLocation("planttech2:textures/gui/guide/crossbreeding.png"), 20, 20, 50, 50)
                            						                          .addPicture(new ResourceLocation("planttech2:textures/gui/guide/crossbreeding.png"), 250, 50, 140, 90))
                            				.addEntry(new GuideEntry("test").addPicture(new ResourceLocation("planttech2:textures/gui/guide/crossbreeding.png"), 20, 20, 50, 50)
                            						                        .addPicture(new ResourceLocation("planttech2:textures/gui/guide/crossbreeding.png"), 100, 30, 150, 145)
                            						                        .addPicture(new ResourceLocation("planttech2:textures/gui/guide/crossbreeding.png"), 320, 125, 30, 30))
                            				.addEntry(new GuideEntry("test4"))
                            				.addEntry(new GuideEntry("test5")));
		addMenu(new GuideMenu("Main3").addEntry(new GuideEntry("test1"))
                            				.addEntry(new GuideEntry("test2"))
                            				.addEntry(new GuideEntry("test3"))
                            				.addEntry(new GuideEntry("test4"))
                            				.addEntry(new GuideEntry("test5"))
                            				.addEntry(new GuideEntry("test6")));
		addMenu(new GuideMenu("Main4").addEntry(new GuideEntry("test1"))
                            				.addEntry(new GuideEntry("test2"))
                            				.addEntry(new GuideEntry("test3"))
                            				.addEntry(new GuideEntry("test4"))
                            				.addEntry(new GuideEntry("test5"))
                            				.addEntry(new GuideEntry("test6"))
                            				.addEntry(new GuideEntry("test7")));
	}
	
	public void addMenu(GuideMenu menu)
	{
		mainmenus.add(menu); 
		amountMainMenus++; 
	}
	
	public GuideMenu getMenuById(int id)
	{
		return mainmenus.get(id); 
	}

	public int getAmountMainMenus()
	{
		return amountMainMenus; 
	}
	
	public int getMenuByName(String name)
	{
		for(GuideMenu menu: mainmenus)
		{
			if(menu.getNameString().equals(name))
			{
				return mainmenus.indexOf(menu);
			}
		}
		return 0;
	}
	
	public int getEntryByName(int mainmenuid, String name)
	{
		GuideMenu menu = mainmenus.get(mainmenuid);
		for(GuideEntry entry: menu.getSubmenus())
		{
			if(entry.getNameString().equals(name))
			{
				return menu.getSubmenus().indexOf(entry);
			}
		}
		return 0;
	}
}
