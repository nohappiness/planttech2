package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.kaneka.planttech2.items.GuideItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuideBaseScreen extends Screen
{
	protected static final ResourceLocation BACKGROUND = new ResourceLocation("planttech2:textures/gui/plantencyclopaedia_big.png");
	protected int xSize = 512;
	protected int ySize = 196;
	protected int guiLeft;
	protected int guiTop;
	protected int scrollMax;
	protected int scrollPos = 0;
	protected int fadeInTimer = 50;
	protected int selectedId;
	protected boolean canscroll;

	public GuideBaseScreen(int scrollMax, boolean canscroll, String title)
	{
		super(new TranslationTextComponent(title));
		this.scrollMax = scrollMax;
		this.canscroll = canscroll;
	}

	@SuppressWarnings("resource")
	@Override
	public void init()
	{
		super.init();
		if (Minecraft.getInstance().player.getHeldItemMainhand().getItem() instanceof GuideItem)
		{
			Minecraft.getInstance().player.getHeldItemMainhand().setDamage(1);
		}
		selectedId = -1;
		this.guiLeft = (this.width - 400) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	protected void updateButtons()
	{

	}

	@Override
	public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(mStack);
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(BACKGROUND);
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
		for (int i = 0; i < this.buttons.size(); ++i)
		{
			this.buttons.get(i).render(mStack, mouseX, mouseY, partialTicks);
		}
	}

	protected void drawBackground(MatrixStack mStack)
	{
		blit(mStack, this.guiLeft + 100, this.guiTop, 212, 0, 300, this.ySize, 512, 512);
		blit(mStack, this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
	}

	protected void drawForeground(MatrixStack mStack)
	{

	}

	private void drawFadeInEffect(MatrixStack mStack)
	{
		float percentage = 1f - ((float) fadeInTimer / 50f);
		blit(mStack, this.guiLeft + 100, this.guiTop, this.xSize - (300 * percentage), 0, (int) (300 * percentage), this.ySize, 512, 512);

		blit(mStack, this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
	}

	@Override
	public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) 
	{
		
		if (canscroll)
		{
			if (p_mouseScrolled_1_ != 0)
			{
				scrollPos += p_mouseScrolled_5_ > 0 ? -1 : 1;
				scrollPos = Math.max(0, scrollPos);
				scrollPos = Math.min(scrollMax, scrollPos);
				
				updateButtons();
			}
		}
		return super.mouseScrolled(p_mouseScrolled_1_, p_mouseScrolled_3_, p_mouseScrolled_5_);
	}

	protected void drawStrings(MatrixStack mStack)
	{

	}

	protected String translateUnformated(String name)
	{
		return new TranslationTextComponent(name).getString();
	}

	protected void drawCenteredString(MatrixStack mStack, String string, int posX, int posY)
	{
		font.drawString(mStack, string, posX - (font.getStringWidth(string) / 2), posY, Integer.parseInt("00e803", 16));
	}

	public void renderItem(ItemStack itemstack, int x, int y)
	{
		itemRenderer.renderItemAndEffectIntoGUI(itemstack, this.guiLeft + x, this.guiTop + y);
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

	protected void drawTooltips(MatrixStack mStack, int mouseX, int mouseY)
	{

	}

	@SuppressWarnings("resource")
	@Override
	public void onClose()
	{
		if (Minecraft.getInstance().player.getHeldItemMainhand().getItem() instanceof GuideItem)
		{
			Minecraft.getInstance().player.getHeldItemMainhand().setDamage(0);
		}
		super.onClose(); 
	}
}
