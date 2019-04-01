package net.kaneka.planttech2.gui;

import java.io.IOException;

import net.java.games.input.Mouse;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.gui.buttons.CustomGuiButton;
import net.kaneka.planttech2.items.ItemGuide;
import net.kaneka.planttech2.items.ModItems;
import net.kaneka.planttech2.proxy.ClientProxy;
import net.kaneka.planttech2.utilities.CustomFontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiGuideBase extends GuiScreen
{
    protected static final ResourceLocation BACKGROUND = new ResourceLocation("planttech2:textures/gui/plantencyclopeadia_big.png");
    protected int xSize = 512;
    protected int ySize = 196;
    protected int guiLeft;
    protected int guiTop;
    protected int scrollMax;
    protected int scrollPos = 0;
    protected int fadeInTimer = 50;
    protected int selectedId;
    protected boolean canscroll; 

    public GuiGuideBase(int scrollMax, boolean canscroll)
    {
	this.scrollMax = scrollMax;
	this.canscroll = canscroll; 
    }

    @Override
    public void initGui()
    {
	super.initGui();
	if (mc.getInstance().player.getHeldItemMainhand().getItem() instanceof ItemGuide)
	{
	    mc.getInstance().player.getHeldItemMainhand().setDamage(1);
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
	GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	this.mc.getTextureManager().bindTexture(BACKGROUND);
	if (fadeInTimer > 0)
	{
	    fadeInTimer--;
	    drawFadeInEffect();
	}
	else
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

	for (int j = 0; j < this.labels.size(); ++j)
	{
	    ((GuiLabel) this.labels.get(j)).render(mouseX, mouseY, partialTicks);
	}
    }

    protected void drawBackground()
    {
	this.drawModalRectWithCustomSizedTexture(this.guiLeft + 100, this.guiTop, 212, 0, 300, this.ySize, 512, 512);
	this.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
    }

    protected void drawForeground()
    {

    }

    private void drawFadeInEffect()
    {
	float percentage = 1f - ((float) fadeInTimer / 50f);
	this.drawModalRectWithCustomSizedTexture(this.guiLeft + 100, this.guiTop, this.xSize - (300 * percentage), 0, (int) (300 * percentage), this.ySize, 512, 512);

	this.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_)
    {
	if(canscroll)
	{
	    if (p_mouseScrolled_1_ != 0)
	    {
		scrollPos += p_mouseScrolled_1_ > 0 ? -1 : 1;
		scrollPos = Math.max(0, scrollPos);
		scrollPos = Math.min(scrollMax, scrollPos);
		updateButtons();
	    }
	}
        return super.mouseScrolled(p_mouseScrolled_1_);
    }

    protected void drawStrings()
    {

    }

    protected String translateUnformated(String name)
    {
	return new TextComponentTranslation(name).getUnformattedComponentText();
    }

    protected void drawCenteredString(String string, int posX, int posY)
    {
	fontRenderer.drawString(string, posX - (fontRenderer.getStringWidth(string) / 2), posY, Integer.parseInt("00e803", 16));
    }

    public void renderItem(ItemStack itemstack, int x, int y)
    {
	this.itemRender.renderItemAndEffectIntoGUI(itemstack, this.guiLeft + x, this.guiTop + y);
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
	    drawHoveringText(lines, mouseX, mouseY);
	}
    }

    protected void drawTooltips(int mouseX, int mouseY)
    {

    }

    @Override
    public void onGuiClosed()
    {
	if (mc.getInstance().player.getHeldItemMainhand().getItem() instanceof ItemGuide)
	{
	    mc.getInstance().player.getHeldItemMainhand().setDamage(0);
	}
    }
}
