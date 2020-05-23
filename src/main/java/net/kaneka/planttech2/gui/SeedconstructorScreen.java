package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.SeedconstructorContainer;
import net.kaneka.planttech2.tileentity.machine.SeedconstructorTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SeedconstructorScreen extends BaseContainerScreen<SeedconstructorContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/seedconstructor.png");

	public SeedconstructorScreen(SeedconstructorContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int l = this.getCookProgressScaled(15);
		blit(this.guiLeft + 96, this.guiTop + 48, 0, 200, 14, l);
		
		int k = this.getEnergyStoredScaled(55);
		blit(this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
		
		int j = this.getFluidStoredScaled(55);
		blit(this.guiLeft + 41, this.guiTop + 28 + (55-j), 224, 55-j, 16, 0 + j);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = container.getValue(4);
		return i != 0 ? i * pixels / ((SeedconstructorTileEntity) this.te).ticksPerItem() : 0;
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
	    drawTooltip( container.getValue(2) + "/" + container.getValue(3), mouseX, mouseY, 41, 28, 16, 55);

	    super.drawTooltips(mouseX,mouseY);
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture() { return BACKGROUND; }

	@Override
	protected String getGuideEntryString()
	{
		return "seedconstructor";
	}
}
