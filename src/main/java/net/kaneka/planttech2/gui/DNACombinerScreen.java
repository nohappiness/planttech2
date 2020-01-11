package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.DNACombinerContainer;
import net.kaneka.planttech2.tileentity.machine.DNACombinerTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DNACombinerScreen extends BaseContainerScreen<DNACombinerContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/dna_combiner.png");
	private static final ResourceLocation TEXTURES_COLORBLIND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container_colorblind/dna_combiner.png");
	
	
	public DNACombinerScreen(DNACombinerContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int l = this.getCookProgressScaled(25);
		blit(this.guiLeft + 98, this.guiTop + 38, 0, 202, 20, l);
		
		int k = this.getEnergyStoredScaled(55);
		blit(this.guiLeft + 159, this.guiTop + 28 + (55 - k), 205, 55 - k, 16, 0 + k);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = container.getValue(2);
		return i != 0 ? i * pixels / ((DNACombinerTileEntity) this.te).ticksPerItem() : 0;
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
