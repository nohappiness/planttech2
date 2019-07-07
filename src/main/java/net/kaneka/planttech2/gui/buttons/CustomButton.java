package net.kaneka.planttech2.gui.buttons;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

public class CustomButton extends Button
{
	protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("planttech2:textures/gui/button.png");
	//protected static CustomFontRenderer customFontRenderer; 

	public CustomButton(int id, int xPos, int yPos, int width, int height, String displayString, IPressable pressable)
	{
		super(xPos, yPos, width, height, displayString, pressable);
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
	         this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	         int i = isHovered ? 1 : 0;
	         GlStateManager.enableBlend();
	         GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	         blit(this.x, this.y, 0, i * 20, this.width / 2, this.height, 200, 80);
	         blit(this.x + this.width / 2, this.y, 200 - this.width / 2, i * 20, this.width / 2, this.height, 200, 80);
	         this.renderBg(minecraft, mouseX, mouseY);
            int j = Integer.parseInt("00e803",16);

            if (packedFGColor != 0)
            {
                j = Integer.parseInt("00e803",16);
            }
            else
            if (!this.active)
            {
                j = Integer.parseInt("00e803",16);
            }
            else if (this.isHovered)
            {
                j = Integer.parseInt("00e803",16);
            }

            this.drawCenteredString(fontrenderer, getMessage(), this.x + this.width / 2 , this.y + (this.height - 8) / 2, j);
	     }
	 }

}
