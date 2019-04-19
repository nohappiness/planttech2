package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ContainerSeedconstructor;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedconstructor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUISeedconstructor extends GuiContainerBase
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/seedconstructor.png");
	
	
	public GUISeedconstructor(InventoryPlayer player, TileEntitySeedconstructor te) 
	{
		super(new ContainerSeedconstructor(player, te), te, player);
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String tileName = this.te.getDisplayName().getUnformattedComponentText();
		
		fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) -5, 14, Integer.parseInt("00e803",16));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int l = this.getCookProgressScaled(13);
		this.drawTexturedModalRect(this.guiLeft + 98, this.guiTop + 50, 0, 202, 12, l);
		
		int k = this.getEnergyStoredScaled(74);
		this.drawTexturedModalRect(this.guiLeft + 162, this.guiTop + 28 + (74-k), 205, 74-k, 16, 0 + k);
		
		int j = this.getFluidStoredScaled(55);
		this.drawTexturedModalRect(this.guiLeft + 27, this.guiTop + 28 + (55-j), 221, 0, 16, 0 + j);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = this.te.getField(4);
		return i != 0 ? i * pixels / ((TileEntitySeedconstructor) this.te).ticksPerItem() : 0;
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
	    drawTooltip( te.getField(2) + "/" + te.getField(3), mouseX, mouseY, 27, 28, 16, 55);

	    super.drawTooltips(mouseX,mouseY);
	}
}
