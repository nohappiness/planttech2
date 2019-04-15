package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ContainerCompressor;
import net.kaneka.planttech2.packets.ButtonPressMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.tileentity.machine.TileEntityCompressor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICompressor extends GuiContainerBase
{
    private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/compressor.png");

    public GUICompressor(InventoryPlayer player, TileEntityCompressor te)
    {
	super(new ContainerCompressor(player, te), te, player);

    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int p_mouseClicked_5_)
    {
	for(int y = 0; y < 3; y++)
	{
	    for(int x = 0; x < 6; x++)
	    {
		if(inArea(mouseX, mouseY, 35 + x * 18, 26 + y * 18))
		{
		    PlantTech2PacketHandler.sendToServer(new ButtonPressMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), x + y * 6));
		}
	    }
	}
        return super.mouseClicked(mouseX, mouseY, p_mouseClicked_5_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
	String tileName = this.te.getDisplayName().getUnformattedComponentText();

	fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) - 5, 14, Integer.parseInt("00e803", 16));
	//customFontRenderer.drawString("selectedID = " + this.te.getField(3), (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) - 5, 90, Integer.parseInt("00e803", 16));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
	GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	this.mc.getTextureManager().bindTexture(TEXTURES);
	this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

	int l = this.getCookProgressScaled(68);
	this.drawTexturedModalRect(this.guiLeft + 55, this.guiTop + 84, 0, 202, l, 12);

	int k = this.getEnergyStoredScaled(74);
	this.drawTexturedModalRect(this.guiLeft + 162, this.guiTop + 28 + (74 - k), 205, 74 - k, 16, 0 + k);
	
	int i = this.te.getField(3);
	if(i >= 0)
	{
	    this.drawTexturedModalRect(this.guiLeft + 35 + (i % 6) * 18, this.guiTop + 26 + (i / 6) * 18, 221, 0, 18, 18);
	}
    }

    private int getCookProgressScaled(int pixels)
    {
	int i = this.te.getField(2);
	return i != 0 ? i * pixels / ((TileEntityCompressor) this.te).ticksPerItem() : 0;
    }
    
    private boolean inArea(double mouseX, double mouseY, int posX, int posY)
    {
	posX += this.guiLeft;
	posY += this.guiTop;
	if (mouseX >= posX && mouseX <= posX + 18 && mouseY >= posY && mouseY <= posY + 18)
	{
	    return true;
	}
	return false;
    }
}
