package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.EnergyStorageContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EnergyStorageScreen extends BaseContainerScreen<EnergyStorageContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/energystorage.png");
	
	
	public EnergyStorageScreen(EnergyStorageContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(TEXTURES);
		blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		
		int k = this.getEnergyStoredScaled(74);
		blit(this.guiLeft + 162, this.guiTop + 28 + (74-k), 205, 74-k, 16, 0 + k);
	}
}
