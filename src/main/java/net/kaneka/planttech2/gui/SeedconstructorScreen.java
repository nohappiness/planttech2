package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.SeedconstructorContainer;
import net.kaneka.planttech2.tileentity.machine.SeedconstructorTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SeedconstructorScreen extends BaseContainerScreen<SeedconstructorContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/seedconstructor.png");
	
	
	public SeedconstructorScreen(SeedconstructorContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(TEXTURES);
		blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int l = this.getCookProgressScaled(13);
		blit(this.guiLeft + 98, this.guiTop + 50, 0, 202, 12, l);
		
		int k = this.getEnergyStoredScaled(74);
		blit(this.guiLeft + 162, this.guiTop + 28 + (74-k), 205, 74-k, 16, 0 + k);
		
		int j = this.getFluidStoredScaled(55);
		blit(this.guiLeft + 27, this.guiTop + 28 + (55-j), 221, 0, 16, 0 + j);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = container.getValue(4);
		return i != 0 ? i * pixels / ((SeedconstructorTileEntity) this.te).ticksPerItem() : 0;
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
	    drawTooltip( container.getValue(2) + "/" + container.getValue(3), mouseX, mouseY, 27, 28, 16, 55);

	    super.drawTooltips(mouseX,mouseY);
	}
}