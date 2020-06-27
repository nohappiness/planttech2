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
	public void func_230431_b_(MatrixStack mStack, int mouseX, int mouseY, float partial)
	{
		if (this.field_230694_p_)
		{
			Minecraft mc = Minecraft.getInstance();
			this.field_230692_n_ = mouseX >= this.field_230690_l_ && mouseY >= this.field_230691_m_ && mouseX < this.field_230690_l_ + this.field_230688_j_ && mouseY < this.field_230691_m_ + this.field_230689_k_;
			int k = this.func_230989_a_(this.field_230692_n_);
			GuiUtils.drawContinuousTexturedBox(field_230687_i_, this.field_230690_l_, this.field_230691_m_, 0, 46 + k * 20, this.field_230688_j_, this.field_230689_k_, 200, 20, 2, 3, 2, 2, this.func_230927_p_());
			this.func_230441_a_(mStack, mc, mouseX, mouseY);
			int color = 14737632;

			ITextComponent buttonText = this.func_230442_c_();
            int strWidth = mc.fontRenderer.func_238414_a_(buttonText);
            int ellipsisWidth = mc.fontRenderer.getStringWidth("...");
			if (strWidth > field_230688_j_ - 6 && strWidth > ellipsisWidth)
                //TODO, srg names make it hard to figure out how to append to an ITextProperties from this trim operation, wraping this in StringTextComponent is kinda dirty.
                buttonText = new StringTextComponent(mc.fontRenderer.func_238417_a_(buttonText, field_230688_j_ - 6 - ellipsisWidth).getString() + "...");

            this.func_238472_a_(mStack, mc.fontRenderer, buttonText, this.field_230690_l_ + this.field_230688_j_ / 2, this.field_230691_m_ + (this.field_230689_k_ - 8) / 2, getFGColor());
		}
	}

}
