package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ContainerSolarGenerator;
import net.kaneka.planttech2.tileentity.machine.TileEntitySolarGenerator;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUISolarGenerator extends GuiContainerBase
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/solargenerator.png");
    
	public GUISolarGenerator(InventoryPlayer inventoryPlayer, TileEntitySolarGenerator te)
	{
		super(new ContainerSolarGenerator(inventoryPlayer, te), te, inventoryPlayer);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String tileName = this.te.getDisplayName().getUnformattedComponentText();
		fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 12 , 20, Integer.parseInt("00e803",16));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		//this.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, tileWidth, tileHeight);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int k = this.getEnergyStoredScaled(74);
		this.drawTexturedModalRect(this.guiLeft + 162, this.guiTop + 28 + (74-k), 205, 74-k, 16, 0 + k);
		
		int l = 0; 
		switch(((TileEntitySolarGenerator) this.te).getUpgradeTier(0, Constants.SOLARFOCUS_TYPE))
		{
			case 0:l = 0; break;   
			case 1:l = 6; break; 
			case 2:l = 15; break; 
			case 3:l = 25; break; 
			case 4:l = 35; break; 
		}
		
		int j = getWorkLoadScaled(17); 
		this.drawTexturedModalRect(this.guiLeft + 139, this.guiTop + 61, 205, 75, j, l);
	}
	
	private int getWorkLoadScaled(int pixels)
	{
		int i = this.te.getField(2);
		int j = ((TileEntitySolarGenerator) this.te).getTicksPerAmount();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
}
