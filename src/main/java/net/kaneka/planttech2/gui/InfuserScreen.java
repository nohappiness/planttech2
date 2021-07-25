package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.inventory.InfuserMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class InfuserScreen extends BaseContainerScreen<InfuserMenu>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/infuser.png");

	public InfuserScreen(InfuserMenu container, Inventory player, Component name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(PoseStack pStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(pStack, partialTicks, mouseX, mouseY);

		int l = this.getCookProgressScaled(32);
		blit(pStack, this.leftPos + 87, this.topPos + 48, 0, 200, l, 14);

		int k = this.getEnergyStoredScaled(55);
		blit(pStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, 0 + k);

		int j = this.getFluidStoredScaled(55);
		blit(pStack, this.leftPos + 41, this.topPos + 28 + (55-j), 224, 55-j, 16, 0 + j);
	}

	private int getCookProgressScaled(int pixels)
	{
		int i = menu.getValue(4);
		int k = menu.getValue(5); 
		return i != 0 && k  != 0 ? i * pixels / k : 0;
	}
	
	@Override
	protected void drawTooltips(PoseStack mStack, int mouseX, int mouseY)
	{
		drawTooltip( mStack, menu.getValue(2) + "/" + menu.getValue(3), mouseX, mouseY, 41, 28, 16, 55);
	    super.drawTooltips(mStack, mouseX, mouseY);
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}

	@Override
	protected String getGuideEntryString()
	{
		return "infuser";
	}
}
