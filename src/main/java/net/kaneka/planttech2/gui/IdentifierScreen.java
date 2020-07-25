package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.IdentifierContainer;
import net.kaneka.planttech2.tileentity.machine.IdentifierTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class IdentifierScreen extends BaseContainerScreen<IdentifierContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/identifier.png");

	public IdentifierScreen(IdentifierContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(mStack, partialTicks, mouseX, mouseY);

		int l = this.getCookProgressScaled(18);
		blit(mStack, this.guiLeft + 74, this.guiTop + 44, 0, 200, l, 18);

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, k);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = container.getValue(2);
		return i != 0 ? i * pixels / ((IdentifierTileEntity) this.te).getTicksPerItem() : 0;
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}

	@Override
	protected String getGuideEntryString()
	{
		return "identifier";
	}
}
