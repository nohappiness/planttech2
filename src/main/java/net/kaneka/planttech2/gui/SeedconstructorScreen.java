package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.SeedconstructorBlockEntity;
import net.kaneka.planttech2.inventory.SeedConstructorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SeedconstructorScreen extends BaseContainerScreen<SeedConstructorMenu>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/seedconstructor.png");

	public SeedconstructorScreen(SeedConstructorMenu container, Inventory player, Component name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(PoseStack pStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(pStack, partialTicks, mouseX, mouseY);

		int l = this.getCookProgressScaled(15);
		blit(pStack, this.leftPos + 96, this.topPos + 48, 0, 200, 14, l);

		int k = this.getEnergyStoredScaled(55);
		blit(pStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, 0 + k);

		int j = this.getFluidStoredScaled(55);
		blit(pStack, this.leftPos + 41, this.topPos + 28 + (55-j), 224, 55-j, 16, 0 + j);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = menu.getValue(4);
		return i != 0 ? i * pixels / ((SeedconstructorBlockEntity) this.te).ticksPerItem() : 0;
	}
	
	@Override
	protected void drawTooltips(PoseStack mStack, int mouseX, int mouseY)
	{
	    drawTooltip(mStack, menu.getValue(2) + "/" + menu.getValue(3), mouseX, mouseY, 41, 28, 16, 55);
	    super.drawTooltips(mStack, mouseX, mouseY);
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture() { return BACKGROUND; }

	@Override
	protected String getGuideEntryString()
	{
		return "seedconstructor";
	}
}
