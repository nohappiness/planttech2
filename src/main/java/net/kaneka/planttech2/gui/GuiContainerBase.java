package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiContainerBase extends GuiContainer
{
	protected static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/solargenerator.png");
	protected final InventoryPlayer player;
	protected final TileEntityEnergy te;
    protected int xSize = 205;
    protected int ySize = 202;

	public GuiContainerBase(Container inventorySlotsIn, TileEntityEnergy te, InventoryPlayer inventoryPlayer)
	{
		super(inventorySlotsIn);
		this.te = te; 
		this.player = inventoryPlayer; 
	}
	
	@Override
	public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
			super.render(mouseX, mouseY, partialTicks);
			this.drawTooltips(mouseX, mouseY);
	        this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	protected void drawTooltips(int mouseX, int mouseY)
	{
		drawTooltip( te.getEnergyStored() + "/" + te.getMaxEnergyStored(), mouseX, mouseY, 162, 28, 16, 74);
	}
	
	public void drawTooltip(String lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop; 
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) 
        {
            drawHoveringText(lines, mouseX, mouseY);
        }
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		
	}
	
	protected int getEnergyStoredScaled(int pixels)
	{
		int i = this.te.getEnergyStored();
		int j = this.te.getMaxEnergyStored();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	protected int getFluidStoredScaled(int pixels)
	{
		int i = te.getField(2);
		int j = te.getField(3);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}

}
