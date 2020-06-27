package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.kaneka.planttech2.items.GuideItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground();
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(BACKGROUND);
		if (fadeInTimer > 0)
		{
			fadeInTimer--;
			drawFadeInEffect();
		} else
		{
			this.drawBackground();
			this.drawForeground();
			this.drawButtons(mouseX, mouseY, partialTicks);
			this.drawStrings();
			this.drawTooltips(mouseX, mouseY);
		}
	}

	private void drawButtons(int mouseX, int mouseY, float partialTicks)
	{
		for (int i = 0; i < this.buttons.size(); ++i)
		{
			this.buttons.get(i).render(mouseX, mouseY, partialTicks);
		}
	}

	protected void drawBackground()
	{
		blit(this.guiLeft + 100, this.guiTop, 212, 0, 300, this.ySize, 512, 512);
		blit(this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
	}

	protected void drawForeground()
	{

	}

	private void drawFadeInEffect()
	{
		float percentage = 1f - ((float) fadeInTimer / 50f);
		blit(this.guiLeft + 100, this.guiTop, this.xSize - (300 * percentage), 0, (int) (300 * percentage), this.ySize, 512, 512);

		blit(this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
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

	protected void drawStrings()
	{

	}

	protected String translateUnformated(String name)
	{
		return new TranslationTextComponent(name).getUnformattedComponentText();
	}

	protected void drawCenteredString(String string, int posX, int posY)
	{
		field_230712_o_.drawString(string, posX - (field_230712_o_.getStringWidth(string) / 2), posY, Integer.parseInt("00e803", 16));
	}

	public void renderItem(ItemStack itemstack, int x, int y)
	{
		itemRenderer.renderItemAndEffectIntoGUI(itemstack, this.guiLeft + x, this.guiTop + y);
	}

	public void drawTooltip(String lines, int mouseX, int mouseY, int posX, int posY)
	{
		drawTooltip(lines, mouseX, mouseY, posX, posY, 16, 16);
	}

	public void drawTooltip(String lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
		{
			renderTooltip(lines, mouseX, mouseY);
		}
	}

	protected void drawTooltips(int mouseX, int mouseY)
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
