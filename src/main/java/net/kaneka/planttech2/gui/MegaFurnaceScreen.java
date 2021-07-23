package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.MegaFurnaceBlockEntity;
import net.kaneka.planttech2.inventory.MegaFurnaceContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.entity.player.Inventory;

public class MegaFurnaceScreen extends BaseContainerScreen<MegaFurnaceContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/megafurnace.png");

	public MegaFurnaceScreen(MegaFurnaceContainer container, Inventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(mStack, partialTicks, mouseX, mouseY);

		for(int p = 0; p < 6; p++)
		{
			int l = this.getCookProgressScaled(p, 15);
			blit(mStack, this.leftPos + 23 + p * 22, this.topPos + 46, 0, 200, 12, l);
		}

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
	}

	private int getCookProgressScaled(int id, int pixels)
	{
		int i = menu.getValue(id + 2);
		return i != 0 ? i * pixels / ((MegaFurnaceBlockEntity) this.te).ticksPerItem() : 0;
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
