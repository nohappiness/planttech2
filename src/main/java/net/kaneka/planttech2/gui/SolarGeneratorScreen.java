package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.SolarGeneratorContainer;
import net.kaneka.planttech2.tileentity.machine.SolarGeneratorTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SolarGeneratorScreen extends BaseContainerScreen<SolarGeneratorContainer>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/solargenerator.png");
    
	public SolarGeneratorScreen(SolarGeneratorContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(TEXTURES);
		//this.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, tileWidth, tileHeight);
		blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int k = this.getEnergyStoredScaled(74);
		blit(this.guiLeft + 162, this.guiTop + 28 + (74-k), 205, 74-k, 16, 0 + k);
		
		int l = 0; 
		switch(((SolarGeneratorTileEntity) this.te).getUpgradeTier(0, PlantTechConstants.SOLARFOCUS_TYPE))
		{
			case 0:l = 0; break;   
			case 1:l = 6; break; 
			case 2:l = 15; break; 
			case 3:l = 25; break; 
			case 4:l = 35; break; 
		}
		
		int j = getWorkLoadScaled(17); 
		blit(this.guiLeft + 139, this.guiTop + 61, 205, 75, j, l);
	}
	
	private int getWorkLoadScaled(int pixels)
	{
		int i = container.getValue(2);
		int j = ((SolarGeneratorTileEntity) this.te).getTicksPerAmount();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
}
