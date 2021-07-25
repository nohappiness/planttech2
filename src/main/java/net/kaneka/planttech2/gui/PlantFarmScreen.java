package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.PlantFarmBlockEntity;
import net.kaneka.planttech2.inventory.PlantFarmMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PlantFarmScreen extends BaseContainerScreen<PlantFarmMenu>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/plantfarm.png");

	public PlantFarmScreen(PlantFarmMenu container, Inventory player, Component name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(PoseStack pStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(pStack, partialTicks, mouseX, mouseY);

		for(int i = 0; i < 5; i++)
		{
			int l = this.getCookProgressScaled(16, i);
			blit(pStack, this.leftPos + 106, this.topPos + 37 + i * 5, 0, 200, l, 4);
		}

		int k = this.getEnergyStoredScaled(55);
		blit(pStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, 0 + k);

		int j = this.getFluidStoredScaled(55);
		blit(pStack, this.leftPos + 41, this.topPos + 28 + (55-j), 224, 55-j, 16, 0 + j);
	}

	private int getCookProgressScaled(int pixels, int slot)
	{
		int i = menu.getValue(4 + slot);
		return i != 0 ? i * pixels / ((PlantFarmBlockEntity) this.te).getTicks() : 0;
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture() { return BACKGROUND; }

	@Override
	protected String getGuideEntryString()
	{
		return "void_plantfarm";
	}
}
