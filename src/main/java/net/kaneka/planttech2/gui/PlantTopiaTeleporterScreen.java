package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.PlantTopiaTeleporterBlockEntity;
import net.kaneka.planttech2.gui.buttons.CustomButton;
import net.kaneka.planttech2.inventory.PlantTopiaTeleporterMenu;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.packets.TeleporterBlockButtonPressMessage;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PlantTopiaTeleporterScreen extends BaseContainerScreen<PlantTopiaTeleporterMenu>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/blockteleporter.png");

	public PlantTopiaTeleporterScreen(PlantTopiaTeleporterMenu container, Inventory player, Component name)
    {
    	super(container, player, name);
    }
	
	@Override
	public void init()
	{
		super.init();
		addRenderableWidget(new CustomButton(1, this.leftPos + 15, this.topPos + 35, 140, 20, "To PlantTopia (1000 BE)", (button) ->
		{
			PlantTopiaTeleporterScreen.this.buttonClicked(0);
		})); 
	}
	
	private void buttonClicked(int buttonid)
	{
		if(menu.getValue(0) >= ((PlantTopiaTeleporterBlockEntity) te).energyPerAction())
		{
			PlantTech2PacketHandler.sendToServer(new TeleporterBlockButtonPressMessage(te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), buttonid));
		}
		else
		{
			//TODO 
		}
	}

	@Override
	protected void renderBg(PoseStack mStack, float partialTicks, int mouseX, int mouseY)
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
