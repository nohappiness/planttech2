package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.MegaFurnaceContainer;
import net.kaneka.planttech2.tileentity.machine.MegaFurnaceTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MegaFurnaceScreen extends BaseContainerScreen<MegaFurnaceContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/megafurnace.png");

	public MegaFurnaceScreen(MegaFurnaceContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(mStack, partialTicks, mouseX, mouseY);

		for(int p = 0; p < 6; p++)
		{
			int l = this.getCookProgressScaled(p, 15);
			blit(mStack, this.guiLeft + 23 + p * 22, this.guiTop + 46, 0, 200, 12, l);
		}

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
	}

	private int getCookProgressScaled(int id, int pixels)
	{
		int i = container.getValue(id + 2);
		return i != 0 ? i * pixels / ((MegaFurnaceTileEntity) this.te).ticksPerItem() : 0;
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}

	@Override
	protected String getGuideEntryString()
	{
		return "mega_furnace";
	}
}
