package net.kaneka.planttech2.gui.buttons;

import net.kaneka.planttech2.proxy.ClientProxy;
import net.kaneka.planttech2.utilities.CustomFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;

public class CustomGuiButton extends GuiButton
{
	protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("planttech2:textures/gui/button.png");
	//protected static CustomFontRenderer customFontRenderer; 

	public CustomGuiButton(int id, int xPos, int yPos, int width, int height, String displayString)
	{
		super(id, xPos, yPos, width, height, displayString);
		//this.customFontRenderer = ClientProxy.fontRenderer; 
	}
	
	 @Override
	 public void render(int mouseX, int mouseY, float partial)
	 {
	     if (this.visible) {
	         Minecraft minecraft = Minecraft.getInstance();
	         FontRenderer fontrenderer = minecraft.fontRenderer;
	         minecraft.getTextureManager().bindTexture(BUTTON_TEXTURES);
	         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	         this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	         int i = this.getHoverState(this.hovered);
	         GlStateManager.enableBlend();
	         GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	         this.drawModalRectWithCustomSizedTexture(this.x, this.y, 0, i * 20 - 20, 50, 20, 200,40);
	         this.drawModalRectWithCustomSizedTexture(this.x + 50, this.y, 150, i * 20 - 20, 50, 20, 200, 40);
	         this.renderBg(minecraft, mouseX, mouseY);
            int j = Integer.parseInt("00e803",16);

            if (packedFGColor != 0)
            {
                j = Integer.parseInt("00e803",16);
            }
            else
            if (!this.enabled)
            {
                j = Integer.parseInt("00e803",16);
            }
            else if (this.hovered)
            {
                j = Integer.parseInt("00e803",16);
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2 , this.y + (this.height - 8) / 2, j);
	     }
	 }

}
