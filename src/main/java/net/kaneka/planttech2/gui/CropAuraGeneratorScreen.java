package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.inventory.CropAuraGeneratorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CropAuraGeneratorScreen extends BaseContainerScreen<CropAuraGeneratorMenu>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/crop_aura_generator.png");

	public CropAuraGeneratorScreen(CropAuraGeneratorMenu container, Inventory player, Component name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}
	
	@Override
	protected String getGuideEntryString()
	{
		return "cropauragenerator";
	}
}
