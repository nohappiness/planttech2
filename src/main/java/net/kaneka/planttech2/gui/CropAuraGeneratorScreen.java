package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.CropAuraGeneratorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CropAuraGeneratorScreen extends BaseContainerScreen<CropAuraGeneratorContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/crop_aura_generator.png");

	public CropAuraGeneratorScreen(CropAuraGeneratorContainer container, PlayerInventory player, ITextComponent name)
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
