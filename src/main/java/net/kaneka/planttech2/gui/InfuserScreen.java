package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.InfuserContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class InfuserScreen extends BaseContainerScreen<InfuserContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/infuser.png");

	public InfuserScreen(InfuserContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(mStack, partialTicks, mouseX, mouseY);

		int l = this.getCookProgressScaled(32);
		blit(mStack, this.guiLeft + 87, this.guiTop + 48, 0, 200, l, 14);

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);

		int j = this.getFluidStoredScaled(55);
		blit(mStack, this.guiLeft + 41, this.guiTop + 28 + (55-j), 224, 55-j, 16, 0 + j);
	}

	private int getCookProgressScaled(int pixels)
	{
		int i = container.getValue(4);
		return i != 0 ? i * pixels / container.getValue(5) : 0;
	}
	
	@Override
	protected void drawTooltips(MatrixStack mStack, int mouseX, int mouseY)
	{
		drawTooltip( mStack, container.getValue(2) + "/" + container.getValue(3), mouseX, mouseY, 41, 28, 16, 55);
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
