package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.SeedSqueezerBlockEntity;
import net.kaneka.planttech2.inventory.SeedSqueezerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SeedSqueezerScreen extends BaseContainerScreen<SeedSqueezerMenu>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/seedsqueezer.png");

	public SeedSqueezerScreen(SeedSqueezerMenu container, Inventory player, Component name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderLabels(PoseStack pStack, int mouseX, int mouseY)
	{
		super.renderLabels(pStack, mouseX, mouseY);

		this.setBlitOffset(this.getBlitOffset() + 300);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().getTexture(BACKGROUND);
		int l = this.getCookProgressScaled(7);
		blit(pStack, 122, 36, 0, 202 + 8 - l, 16, l + 12);

		blit(pStack, 122, 62 - l, 16, 202, 16, l + 12);
		this.setBlitOffset(this.getBlitOffset() - 300);
	}

	@Override
	protected void renderBg(PoseStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(mStack, partialTicks, mouseX, mouseY);

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, k);

		int j = this.getFluidStoredScaled(55);
		blit(mStack, this.leftPos + 41, this.topPos + 28 + (55-j), 224, 55-j, 16, j);
	}

	private int getCookProgressScaled(int pixels)
	{
		int i = menu.getValue(4);
		return i != 0 ? i * pixels / ((SeedSqueezerBlockEntity) this.te).ticksPerItem() : 0;
	}

	@Override
	protected void drawTooltips(PoseStack mStack, int mouseX, int mouseY)
	{
		drawTooltip(mStack, menu.getValue(2) + "/" + menu.getValue(3), mouseX, mouseY, 41, 28, 16, 55);

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
		return "seed_squeezer";
	}
}
