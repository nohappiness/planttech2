package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.InfuserContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class InfuserScreen extends BaseContainerScreen<InfuserContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/infuser.png");
	private static final ResourceLocation TEXTURES_COLORBLIND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container_colorblind/infuser.png");
	
	
	public InfuserScreen(InfuserContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int l = this.getCookProgressScaled(31);
		blit(this.guiLeft + 89, this.guiTop + 50, 0, 202, l, 12);
		
		int k = this.getEnergyStoredScaled(55);
		blit(this.guiLeft + 159, this.guiTop + 28 + (55 - k), 205, 55 - k, 16, 0 + k);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = container.getValue(4);
		return i != 0 ? i * pixels / container.getValue(5) : 0;
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
	    drawTooltip( container.getValue(2) + "/" + container.getValue(3), mouseX, mouseY, 27, 28, 16, 55);

	    super.drawTooltips(mouseX,mouseY);
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return TEXTURES;
	}


	@Override
	protected ResourceLocation getBackgroundTextureColorblind()
	{
		return TEXTURES_COLORBLIND;
	}
}
