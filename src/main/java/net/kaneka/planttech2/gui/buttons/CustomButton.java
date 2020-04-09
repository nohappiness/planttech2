package net.kaneka.planttech2.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.gui.GuiUtils;

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
	     if (this.visible) 
	     {
	         Minecraft mc = Minecraft.getInstance();
	         this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	            int k = this.getYImage(this.isHovered);
	            GuiUtils.drawContinuousTexturedBox(WIDGETS_LOCATION, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
	            this.renderBg(mc, mouseX, mouseY);
	            int color = 14737632;

	            if (packedFGColor != 0)
	            {
	                color = Integer.parseInt("00e803",16);
	            }
	            else
	            if (!this.active)
	            {
	                color = Integer.parseInt("00e803",16);
	            }
	            else if (this.isHovered)
	            {
	                color = Integer.parseInt("00e803",16);
	            }

	            String buttonText = this.getMessage();
	            int strWidth = mc.fontRenderer.getStringWidth(buttonText);
	            int ellipsisWidth = mc.fontRenderer.getStringWidth("...");

	            if (strWidth > width - 6 && strWidth > ellipsisWidth)
	                buttonText = mc.fontRenderer.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";

	            this.drawCenteredString(mc.fontRenderer, buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
	       }
	 }

}
