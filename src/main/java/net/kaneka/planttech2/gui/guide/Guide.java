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
		addMenu(new GuideMenu("crossbreeding").addEntry(new GuideEntry("general_crossbreeding"))
                                				.addEntry(new GuideEntry("getting_started"))
                                				.addEntry(new GuideEntry("crop_not_growing"))
                                				.addEntry(new GuideEntry("multiply_crops_seeds"))
                                				.addEntry(new GuideEntry("how_to_crossbreed").addPicture(new ResourceLocation("planttech2:textures/gui/guide/how_to_crossbreed.png"), 210, 50, 180, 90))
                                				.addEntry(new GuideEntry("autoreplanting"))
                                				.addEntry(new GuideEntry("croptraits"))
                                				.addEntry(new GuideEntry("identify_traits"))
                                				.addEntry(new GuideEntry("fertilizer"))
                                				.addEntry(new GuideEntry("genetic_engineering_crops")));
		
		addMenu(new GuideMenu("machines").addEntry(new GuideEntry("general_machines"))
                            				.addEntry(new GuideEntry("chipalyzer"))
                            				.addEntry(new GuideEntry("compressor"))
                            				.addEntry(new GuideEntry("dna_cleaner"))
                            				.addEntry(new GuideEntry("dna_combiner"))
                            				.addEntry(new GuideEntry("dna_extractor"))
                            				.addEntry(new GuideEntry("dna_remover"))
                            				.addEntry(new GuideEntry("energy_supplier"))
                            				.addEntry(new GuideEntry("identifier"))
                            				.addEntry(new GuideEntry("infuser"))
                            				.addEntry(new GuideEntry("machinebulb_reprocessor"))
                            				.addEntry(new GuideEntry("mega_furnace"))
                            				.addEntry(new GuideEntry("void_plantfarm"))
                            				.addEntry(new GuideEntry("seedconstructor"))
                            				.addEntry(new GuideEntry("seed_squeezer"))
                            				.addEntry(new GuideEntry("solar_generator"))
                            				);
		
		addMenu(new GuideMenu("genetic_engineering").addEntry(new GuideEntry("general_genetic_engineering"))
                                    				.addEntry(new GuideEntry("extracting_genes"))
                                    				.addEntry(new GuideEntry("purifying_genes"))
                                    				.addEntry(new GuideEntry("combining_genes"))
                                    				.addEntry(new GuideEntry("cleaning_containers"))
                                    				.addEntry(new GuideEntry("creating_designerseeds")));
		
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
