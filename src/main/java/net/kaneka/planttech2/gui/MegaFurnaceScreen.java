package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.MegaFurnaceContainer;
import net.kaneka.planttech2.tileentity.machine.MegaFurnaceTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MegaFurnaceScreen extends BaseContainerScreen<MegaFurnaceContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/megafurnace.png");
	
	public MegaFurnaceScreen(MegaFurnaceContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(TEXTURES);
		blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		for(int p = 0; p < 6; p++)
		{
			int l = this.getCookProgressScaled(p, 13);
			blit(this.guiLeft + 29 + p * 22, this.guiTop + 46, 5, 202, 10, l);
		}
		
		int k = this.getEnergyStoredScaled(74);
		blit(this.guiLeft + 162, this.guiTop + 28 + (74-k), 205, 74-k, 16, 0 + k);
	}
	
	private int getCookProgressScaled(int id, int pixels)
	{
		int i = this.te.getField(id + 2);
		return i != 0 ? i * pixels / ((MegaFurnaceTileEntity) this.te).getTicksPerItem() : 0;
	}
}
