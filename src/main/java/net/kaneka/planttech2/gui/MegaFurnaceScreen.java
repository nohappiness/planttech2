package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.MegaFurnaceContainer;
import net.kaneka.planttech2.tileentity.machine.MegaFurnaceTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MegaFurnaceScreen extends BaseContainerScreen<MegaFurnaceContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/megafurnace.png");
	private static final ResourceLocation TEXTURES_COLORBLIND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container_colorblind/megafurnace.png");
	
	public MegaFurnaceScreen(MegaFurnaceContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		for(int p = 0; p < 6; p++)
		{
			int l = this.getCookProgressScaled(p, 13);
			blit(this.guiLeft + 29 + p * 22, this.guiTop + 46, 5, 202, 10, l);
		}
		
		int k = this.getEnergyStoredScaled(55);
		blit(this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
	}
	
	private int getCookProgressScaled(int id, int pixels)
	{
		int i = container.getValue(id + 2);
		return i != 0 ? i * pixels / ((MegaFurnaceTileEntity) this.te).getTicksPerItem() : 0;
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
		return "mega_furnace";
	}
}
