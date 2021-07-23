package net.kaneka.planttech2.gui;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.ChipalyzerBlockEntity;
import net.kaneka.planttech2.inventory.ChipalyzerContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.entity.player.Inventory;

public class ChipalyzerScreen extends BaseContainerScreen<ChipalyzerContainer>
{ 
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/chipalyzer.png");

	public ChipalyzerScreen(ChipalyzerContainer container, Inventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(mStack, partialTicks, mouseX, mouseY);

		int l = this.getCookProgressScaled(32);
		blit(mStack, this.leftPos + 60, this.topPos + 46, 0, 200, l, 16);

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, k);
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = menu.getValue(2);
		return i != 0 ? i * pixels / ((ChipalyzerBlockEntity) this.te).ticksPerItem() : 0;
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}
	
	@Override
	protected String getGuideEntryString()
	{
		return "chipalyzer";
	}
}
