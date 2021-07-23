package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.PlantFarmBlockEntity;
import net.kaneka.planttech2.inventory.PlantFarmContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.entity.player.Inventory;

public class PlantFarmScreen extends BaseContainerScreen<PlantFarmContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/plantfarm.png");

	public PlantFarmScreen(PlantFarmContainer container, Inventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(mStack, partialTicks, mouseX, mouseY);

		for(int i = 0; i < 5; i++)
		{
			int l = this.getCookProgressScaled(16, i);
			blit(mStack, this.leftPos + 106, this.topPos + 37 + i * 5, 0, 200, l, 4);
		}

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, 0 + k);

		int j = this.getFluidStoredScaled(55);
		blit(mStack, this.leftPos + 41, this.topPos + 28 + (55-j), 224, 55-j, 16, 0 + j);
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
