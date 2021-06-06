package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

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
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/blockteleporter.png");

	public PlantTopiaTeleporterScreen(PlantTopiaTeleporterContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	public void init()
	{
		super.init();
		addButton(new CustomButton(1, this.leftPos + 15, this.topPos + 35, 140, 20, "To PlantTopia (1000 BE)", (button) -> 
		{
			PlantTopiaTeleporterScreen.this.buttonClicked(0);
		})); 
	}
	
	private void buttonClicked(int buttonid)
	{
		if(menu.getValue(0) >= ((PlantTopiaTeleporterTileEntity) te).energyPerAction())
		{
			PlantTech2PacketHandler.sendToServer(new TeleporterBlockButtonPressMessage(te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), buttonid));
		}
		else
		{
			//TODO 
		}
	}

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(mStack, partialTicks, mouseX, mouseY);

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.leftPos + 159, this.topPos + 28 + (55 - k), 205, 55 - k, 16, 0 + k);
	}

	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}

	@Override
	protected String getGuideEntryString()
	{
		return "general";
	}
}
