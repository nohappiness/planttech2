package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ChipalyzerContainer;
import net.kaneka.planttech2.tileentity.machine.ChipalyzerTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ChipalyzerScreen extends BaseContainerScreen<ChipalyzerContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/chipalyzer.png");
	private static final ResourceLocation TEXTURES_COLORBLIND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container_colorblind/chipalyzer.png");
	
	
	public ChipalyzerScreen(ChipalyzerContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int l = this.getCookProgressScaled(32);
		blit(this.guiLeft + 62, this.guiTop + 48, 0, 202, l, 14);
		
		int k = this.getEnergyStoredScaled(55);
		blit(this.guiLeft + 159, this.guiTop + 28 + (55 - k), 205, 55 - k, 16, 0 + k);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = container.getValue(2);
		return i != 0 ? i * pixels / ((ChipalyzerTileEntity) this.te).ticksPerItem() : 0;
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
