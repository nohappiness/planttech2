package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.PlantFarmContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PlantFarmScreen extends BaseContainerScreen<PlantFarmContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/plantfarm.png");
	private static final ResourceLocation TEXTURES_COLORBLIND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container_colorblind/plantfarm.png");
	
	public PlantFarmScreen(PlantFarmContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int k = this.getEnergyStoredScaled(55);
		blit(this.guiLeft + 159, this.guiTop + 28 + (55 - k), 205, 55 - k, 16, 0 + k);
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return TEXTURES;
	}


	@Override
	protected ResourceLocation getBackgroundTextureColorblind()
	{
		return TEXTURES_COLORBLIND;
	}
}
