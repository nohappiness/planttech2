package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.DNAExtractorBlockEntity;
import net.kaneka.planttech2.inventory.DNAExtractorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DNAExtractorScreen extends BaseContainerScreen<DNAExtractorMenu>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/dna_extractor.png");

	public DNAExtractorScreen(DNAExtractorMenu container, Inventory player, Component name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(PoseStack pStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(pStack, partialTicks, mouseX, mouseY);

		int l = this.getCookProgressScaled(52);
		blit(pStack, this.leftPos + 76, this.topPos + 37, 0, 200, l, 22);

		int k = this.getEnergyStoredScaled(55);
		blit(pStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, k);
	}

//	@Override
//	protected void drawGuiContainerBackgroundLayer(PoseStack mStack, float partialTicks, int mouseX, int mouseY)
//	{
//		super.drawGuiContainerBackgroundLayer(mStack, partialTicks, mouseX, mouseY);
//
//		int l = this.getCookProgressScaled(52);
//		blit(mStack, this.guiLeft + 76, this.guiTop + 37, 0, 200, l, 22);
//
//		int k = this.getEnergyStoredScaled(55);
//		blit(mStack, this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
//	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = menu.getValue(2);
		return i != 0 ? i * pixels / ((DNAExtractorBlockEntity) this.te).ticksPerItem() : 0;
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}

	@Override
	protected String getGuideEntryString()
	{
		return "dna_extractor";
	}
}
