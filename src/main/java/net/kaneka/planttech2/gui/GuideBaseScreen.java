package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.items.GuideItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class GuideBaseScreen extends Screen
{
	protected static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID, "textures/gui/plantencyclopaedia_big.png");
	protected static final int TEXT_COLOR = 0x00e803;
	protected int xSize = 512;
	protected int ySize = 196;
	protected int guiLeft;
	protected int guiTop;
	protected int scrollMax;
	protected int scrollPos = 0;
	protected int fadeInTimer = 50;
	protected boolean hasSelection;
	protected boolean allowScroll;

	public GuideBaseScreen(int scrollMax, boolean allowScroll, String title)
	{
		super(new TranslationTextComponent(title));
		this.scrollMax = scrollMax;
		this.allowScroll = allowScroll;
	}

	@SuppressWarnings("resource")
	@Override
	public void init()
	{
		super.init();
		if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getMainHandItem().getItem() instanceof GuideItem)
		{
			// TODO: move this to use a custom item property
			Minecraft.getInstance().player.getMainHandItem().setDamageValue(1);
		}
		hasSelection = false;
		this.guiLeft = (this.width - 400) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	protected abstract void updateButtons();

	@Override
	public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(mStack);
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bind(BACKGROUND);
		if (fadeInTimer > 0)
		{
			fadeInTimer--;
			drawFadeInEffect(mStack);
		} else
		{
			this.drawBackground(mStack);
			this.drawForeground(mStack);
			this.drawButtons(mStack, mouseX, mouseY, partialTicks);
			this.drawStrings(mStack);
			this.drawTooltips(mStack, mouseX, mouseY);
		}
	}

	private void drawButtons(MatrixStack mStack, int mouseX, int mouseY, float partialTicks)
	{
		for (Widget button : this.buttons)
		{
			button.render(mStack, mouseX, mouseY, partialTicks);
		}
	}

	protected void drawBackground(MatrixStack mStack)
	{
		blit(mStack, this.guiLeft + 100, this.guiTop, 212, 0, 300, this.ySize, 512, 512);
		blit(mStack, this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
	}

	protected abstract void drawForeground(MatrixStack mStack);

	private void drawFadeInEffect(MatrixStack mStack)
	{
		float percentage = 1f - ((float) fadeInTimer / 50f);
		blit(mStack, this.guiLeft + 100, this.guiTop, this.xSize - (300 * percentage), 0, (int) (300 * percentage), this.ySize, 512, 512);
		blit(mStack, this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta)
	{

		if (allowScroll && mouseX != 0)
		{
			scrollPos += delta > 0 ? -1 : 1;
			scrollPos = Math.max(0, scrollPos);
			scrollPos = Math.min(scrollMax, scrollPos);

			updateButtons();
		}
		return super.mouseScrolled(mouseX, mouseY, delta);
	}

	protected abstract void drawStrings(MatrixStack mStack);

	public void renderItem(ItemStack itemstack, int x, int y)
	{
		itemRenderer.renderAndDecorateItem(itemstack, this.guiLeft + x, this.guiTop + y);
	}

	public void drawTooltip(MatrixStack mStack, ITextComponent lines, int mouseX, int mouseY, int posX, int posY)
	{
		drawTooltip(mStack, lines, mouseX, mouseY, posX, posY, 16, 16);
	}

	public void drawTooltip(MatrixStack mStack, ITextComponent lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
		{
			renderTooltip(mStack, lines, mouseX, mouseY);
		}
	}

	protected abstract void drawTooltips(MatrixStack mStack, int mouseX, int mouseY);

	@SuppressWarnings("resource")
	@Override
	public void removed()
	{
		if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof GuideItem)
		{
			Minecraft.getInstance().player.getMainHandItem().setDamageValue(0);
		}
		super.removed();
	}
}
