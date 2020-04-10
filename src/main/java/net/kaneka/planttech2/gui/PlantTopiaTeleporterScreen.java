package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.PlantTopiaTeleporterContainer;
import net.kaneka.planttech2.gui.buttons.CustomButton;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.packets.TeleporterBlockButtonPressMessage;
import net.kaneka.planttech2.tileentity.machine.PlantTopiaTeleporterTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PlantTopiaTeleporterScreen extends BaseContainerScreen<PlantTopiaTeleporterContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/blockteleporter.png");
	private static final ResourceLocation TEXTURES_COLORBLIND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container_colorblind/blockteleporter.png");
	
	
	public PlantTopiaTeleporterScreen(PlantTopiaTeleporterContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	public void init()
	{
		super.init();
		addButton(new CustomButton(1, this.guiLeft + 15, this.guiTop + 35, 140, 20, "To PlantTopia (1000 BE)", (button) -> 
		{
			PlantTopiaTeleporterScreen.this.buttonClicked(0);
		})); 
	}
	
	private void buttonClicked(int buttonid)
	{
		if(container.getValue(0) >= ((PlantTopiaTeleporterTileEntity) te).getEnergyPerTeleportation())
		{
			PlantTech2PacketHandler.sendToServer(new TeleporterBlockButtonPressMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), buttonid));
		}
		else
		{
			//TODO 
		}
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
	
	@Override
	protected String getGuideEntryString()
	{
		return "general";
	}
}
