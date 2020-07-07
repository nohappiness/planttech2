package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.EnergyStorageContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EnergyStorageScreen extends BaseContainerScreen<EnergyStorageContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/energystorage.png");

	public EnergyStorageScreen(EnergyStorageContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(mStack, partialTicks, mouseX, mouseY);
		
		
		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.guiLeft + 159, this.guiTop + 28 + (55 - k), 205, 55 - k, 16, 0 + k);
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}

	@Override
	protected String getGuideEntryString()
	{
		return "energy_storage";
	}
}
