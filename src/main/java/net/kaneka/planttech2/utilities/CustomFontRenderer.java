package net.kaneka.planttech2.utilities;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class CustomFontRenderer extends FontRenderer
{

    public CustomFontRenderer(TextureManager textureManagerIn, Font fontIn)
    {
	super(textureManagerIn, fontIn);
	// TODO Auto-generated constructor stub
    }
/*
	public CustomFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode)
	{
		super(gameSettingsIn, location, textureManagerIn, unicode);
	}
	
	
	@Override
	protected float renderUnicodeChar(char ch, boolean italic)
	{
		int i = glyphWidth[ch] & 255;

		if (i == 0)
			return 0.0F;
		else
		{
			bindTexture(locationFontTexture);
			int j = ch / 256;
            int k = i / 20;
            int l = 8;
            if(ch == 'i' || ch == 'I' || ch == '1')
            {
            	l = 2;
            }
            float f = (float)k;
            float f1 = (float)(l + 1);
            float f2 = (float)(ch % 16 * 16) + f;
            float f3 = (float)((ch & 255) / 16 * 16);
            float f4 = f1 - f - 0.02F;
            float f5 = italic ? 1.0F : 0.0F;
            GlStateManager.glBegin(5);
            GlStateManager.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
            GlStateManager.glVertex3f(this.posX + f5, this.posY, 0.0F);
            GlStateManager.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
            GlStateManager.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
            GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
            GlStateManager.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
            GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
            GlStateManager.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
            GlStateManager.glEnd();
            return (f1 - f) / 2.0F + 1.0F;
		}
	}
	
	public void drawBold(String text, int x, int y, int color)
	{
		this.drawString(TextFormatting.BOLD + text, x, y, color);
	}
*/
}
