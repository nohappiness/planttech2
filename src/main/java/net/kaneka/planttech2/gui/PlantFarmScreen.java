package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.PlantFarmContainer;
import net.kaneka.planttech2.tileentity.machine.MachineBulbReprocessorTileEntity;
import net.kaneka.planttech2.tileentity.machine.PlantFarmTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PlantFarmScreen extends BaseContainerScreen<PlantFarmContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/plantfarm.png");

	public PlantFarmScreen(PlantFarmContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(mStack, partialTicks, mouseX, mouseY);
		
		for(int i = 0; i < 5; i++)
		{
			int l = this.getCookProgressScaled(16, i);
			blit(mStack, this.guiLeft + 106, this.guiTop + 37 + i * 5, 0, 200, l, 4);
		}
		
		
		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
		
		int j = this.getFluidStoredScaled(55);
		blit(mStack, this.guiLeft + 41, this.guiTop + 28 + (55-j), 224, 55-j, 16, 0 + j);
	}
	
	private int getCookProgressScaled(int pixels, int slot)
	{
		int i = container.getValue(4 + slot);
		return i != 0 ? i * pixels / ((PlantFarmTileEntity) this.te).getTicks() : 0;
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture() { return BACKGROUND; }

	@Override
	protected String getGuideEntryString()
	{
		return "void_plantfarm";
	}
}
