package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.DNARemoverContainer;
import net.kaneka.planttech2.tileentity.machine.DNARemoverTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DNARemoverScreen extends BaseContainerScreen<DNARemoverContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/dna_remover.png");
	
	
	public DNARemoverScreen(DNARemoverContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(TEXTURES);
		blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int l = this.getCookProgressScaled(32);
		blit(this.guiLeft + 62, this.guiTop + 50, 0, 202, l, 12);
		
		int k = this.getEnergyStoredScaled(74);
		blit(this.guiLeft + 162, this.guiTop + 28 + (74-k), 205, 74-k, 16, 0 + k);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = this.te.getField(2);
		return i != 0 ? i * pixels / ((DNARemoverTileEntity) this.te).ticksPerItem() : 0;
	}
}
