package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.items.GuideItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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
		super(new TranslatableComponent(title));
		this.init();
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
	public void render(PoseStack pStack, int mouseX, int mouseY, float partialTicks)
	{
		//this.renderBackground(pStack);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, BACKGROUND);
		if (fadeInTimer > 0)
		{
			fadeInTimer--;
			drawFadeInEffect(pStack);
		} else
		{
			this.drawBackground(pStack);
			this.drawForeground(pStack);
			this.drawButtons(pStack, mouseX, mouseY, partialTicks);
			this.drawStrings(pStack);
			this.drawTooltips(pStack, mouseX, mouseY);
		}
	}

	private void drawButtons(PoseStack mStack, int mouseX, int mouseY, float partialTicks)
	{
		for (Widget button : this.renderables)
		{
			 button.render(mStack, mouseX, mouseY, partialTicks);
		}
	}

	protected void drawBackground(PoseStack mStack)
	{
		blit(mStack, this.guiLeft + 100, this.guiTop, 212, 0, 300, this.ySize, 512, 512);
		blit(mStack, this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
	}

	protected abstract void drawForeground(PoseStack mStack);

	private void drawFadeInEffect(PoseStack mStack)
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

	protected abstract void drawStrings(PoseStack mStack);

	public void renderItem(ItemStack itemstack, int x, int y)
	{
		itemRenderer.renderAndDecorateItem(itemstack, this.guiLeft + x, this.guiTop + y);
	}

	public void drawTooltip(PoseStack pStack, Component lines, int mouseX, int mouseY, int posX, int posY)
	{
		drawTooltip(pStack, lines, mouseX, mouseY, posX, posY, 16, 16);
	}

	public void drawTooltip(PoseStack pStack, Component lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
		{
			renderTooltip(pStack, lines, mouseX, mouseY);
		}
	}

	protected abstract void drawTooltips(PoseStack pStack, int mouseX, int mouseY);

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
