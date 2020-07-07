package net.kaneka.planttech2.gui.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class CustomButton extends Button
{
	protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("planttech2:textures/gui/button.png");
	// protected static CustomFontRenderer customFontRenderer;

	public CustomButton(int id, int xPos, int yPos, int width, int height, String displayString, IPressable pressable)
	{
		super(xPos, yPos, width, height, new StringTextComponent(displayString), pressable);
		// this.customFontRenderer = ClientProxy.fontRenderer;
	}

	@Override
	public void renderButton(MatrixStack mStack, int mouseX, int mouseY, float partial)
	{
		 if (this.visible)
	        {
	            Minecraft mc = Minecraft.getInstance();
	            this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	            int k = this.getYImage(this.isHovered());
	            GuiUtils.drawContinuousTexturedBox(WIDGETS_LOCATION, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.getBlitOffset());
	            this.renderBg(mStack, mc, mouseX, mouseY);

	            ITextComponent buttonText = this.getNarrationMessage();
	            int strWidth = mc.fontRenderer.func_238414_a_(buttonText);
	            int ellipsisWidth = mc.fontRenderer.getStringWidth("...");

	            if (strWidth > width - 6 && strWidth > ellipsisWidth)
	                //TODO, srg names make it hard to figure out how to append to an ITextProperties from this trim operation, wraping this in StringTextComponent is kinda dirty.
	                buttonText = new StringTextComponent(mc.fontRenderer.func_238417_a_(buttonText, width - 6 - ellipsisWidth).getString() + "...");

	            this.drawCenteredString(mStack, mc.fontRenderer, buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, getFGColor());
	        }
	}

}
