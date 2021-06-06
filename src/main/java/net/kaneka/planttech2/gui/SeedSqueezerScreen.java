package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.SeedSqueezerContainer;
import net.kaneka.planttech2.gui.guide.Guide;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.kaneka.planttech2.tileentity.machine.SeedSqueezerTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SeedSqueezerScreen extends BaseContainerScreen<SeedSqueezerContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/seedsqueezer.png");

	public SeedSqueezerScreen(SeedSqueezerContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderLabels(MatrixStack mStack, int mouseX, int mouseY)
	{
		super.renderLabels(mStack, mouseX, mouseY);

		this.setBlitOffset(this.getBlitOffset() + 300);
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bind(BACKGROUND);
		int l = this.getCookProgressScaled(7);
		blit(mStack, 122, 36, 0, 202 + 8 - l, 16, l + 12);

		blit(mStack, 122, 62 - l, 16, 202, 16, l + 12);
		this.setBlitOffset(this.getBlitOffset() - 300);
	}

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
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
		return i != 0 ? i * pixels / ((SeedSqueezerTileEntity) this.te).ticksPerItem() : 0;
	}

	@Override
	protected void drawTooltips(MatrixStack mStack, int mouseX, int mouseY)
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
