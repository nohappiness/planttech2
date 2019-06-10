package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ContainerSeedSqueezer;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedSqueezer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GUISeedSqueezer extends GuiContainerBase<ContainerSeedSqueezer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/seedsqueezer.png");
	
	public GUISeedSqueezer(ContainerSeedSqueezer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		this.blitOffset =+ 300; 
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(TEXTURES);
		int l = this.getCookProgressScaled(8);
		blit(113, 35, 0, 202 + 8 - l, 16, l + 12);
		
		blit(113, 63 - l, 16, 202, 16, l + 12);
		this.blitOffset =- 300; 
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(TEXTURES);
		blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int k = this.getEnergyStoredScaled(74);
		blit(this.guiLeft + 162, this.guiTop + 28 + (74-k), 205, 74-k, 16, 0 + k);
		
		int j = this.getFluidStoredScaled(55);
		blit(this.guiLeft + 27, this.guiTop + 28 + (55-j), 221, 0, 16, 0 + j);
		
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = this.te.getField(4);
		return i != 0 ? i * pixels / ((TileEntitySeedSqueezer) this.te).getTicksPerItem() : 0;
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
	    drawTooltip( te.getField(2) + "/" + te.getField(3), mouseX, mouseY, 27, 28, 16, 55);

	    super.drawTooltips(mouseX,mouseY);
	}
}
