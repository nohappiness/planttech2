package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.DNACombinerBlockEntity;
import net.kaneka.planttech2.inventory.DNACombinerContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.entity.player.Inventory;

public class DNACombinerScreen extends BaseContainerScreen<DNACombinerContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/dna_combiner.png");

	public DNACombinerScreen(DNACombinerContainer container, Inventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(mStack, partialTicks, mouseX, mouseY);

		int l = this.getCookProgressScaled(27);
		blit(mStack, this.leftPos + 96, this.topPos + 43, 0, 200, 22, l);

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
	}

//	@Override
//	protected void drawGuiContainerBackgroundLayer(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
//	{
//		super.drawGuiContainerBackgroundLayer(mStack, partialTicks, mouseX, mouseY);
//
//		int l = this.getCookProgressScaled(27);
//		blit(mStack, this.guiLeft + 96, this.guiTop + 43, 0, 200, 22, l);
//
//		int k = this.getEnergyStoredScaled(55);
//		blit(mStack, this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
//	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = menu.getValue(2);
		return i != 0 ? i * pixels / ((DNACombinerBlockEntity) this.te).ticksPerItem() : 0;
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}

	@Override
	protected String getGuideEntryString()
	{
		return "dna_combiner";
	}
}
