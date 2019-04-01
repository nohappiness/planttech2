package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ContainerIdentifier;
import net.kaneka.planttech2.container.ContainerMegaFurnace;
import net.kaneka.planttech2.proxy.ClientProxy;
import net.kaneka.planttech2.tileentity.machine.TileEntityIdentifier;
import net.kaneka.planttech2.tileentity.machine.TileEntityMegaFurnace;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIIdentifier extends GuiContainerBase
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/identifier.png");
	
	
	public GUIIdentifier(InventoryPlayer player, TileEntityIdentifier te) 
	{
		super(new ContainerIdentifier(player, te), te, player);
		
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
		
		int l = this.getCookProgressScaled(16);
		this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 44, 0, 202, l, 16);
		
		int k = this.getEnergyStoredScaled(74);
		this.drawTexturedModalRect(this.guiLeft + 162, this.guiTop + 28 + (74-k), 205, 74-k, 16, 0 + k);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = this.te.getField(2);
		return i != 0 ? i * pixels / ((TileEntityIdentifier) this.te).getTicksPerItem() : 0;
	}
}
