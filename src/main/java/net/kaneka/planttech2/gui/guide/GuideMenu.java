package net.kaneka.planttech2.gui.guide;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TranslationTextComponent;

public class GuideMenu
{
	private String name; 
	private List<GuideEntry> entrys = new ArrayList<GuideEntry>();
	
	public GuideMenu(String name)
	{
		this.name = name; 
	}
	
	public int getAmountEntrys()
	{
		return entrys.size();
	}
	
	public String getNameString()
	{
		return name; 
	}
	
	public String getName()
	{
		return new TranslationTextComponent("guide.planttech2." + name + ".menu").getString(); 
	}

	public List<GuideEntry> getSubmenus()
	{
		return entrys;
	}
	
	public GuideEntry getEntryById(int id)
	{
		return entrys.get(id);
	}

	public GuideMenu addEntry(GuideEntry entry)
	{
		this.entrys.add(entry.setMenu(this));
		return this; 
	} 
	
}

