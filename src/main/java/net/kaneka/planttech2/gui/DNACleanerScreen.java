package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.DNACleanerContainer;
import net.kaneka.planttech2.tileentity.machine.DNACleanerTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DNACleanerScreen extends BaseContainerScreen<DNACleanerContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/dna_cleaner.png");
	private static final ResourceLocation TEXTURES_COLORBLIND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container_colorblind/dna_cleaner.png");
	
	public DNACleanerScreen(DNACleanerContainer container, PlayerInventory player, ITextComponent string)
    {
    	super(container, player, string);

    }
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int l = this.getCookProgressScaled(32);
		blit(this.guiLeft + 61, this.guiTop + 49, 0, 200, l, 12);
		
		int k = this.getEnergyStoredScaled(55);
		blit(this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = container.getValue(2);
		return i != 0 ? i * pixels / ((DNACleanerTileEntity) this.te).ticksPerItem() : 0;
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


	@Override
	protected String getGuideEntryString()
	{
		return "dna_cleaner";
	}
}
