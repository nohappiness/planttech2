package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.SolarGeneratorBlockEntity;
import net.kaneka.planttech2.inventory.SolarGeneratorContainer;
import net.kaneka.planttech2.items.TierItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.entity.player.Inventory;

public class SolarGeneratorScreen extends BaseContainerScreen<SolarGeneratorContainer>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/solargenerator.png");

	public SolarGeneratorScreen(SolarGeneratorContainer container, Inventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(mStack, partialTicks, mouseX, mouseY);

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, 0 + k);

		int l = 0;
		switch(((SolarGeneratorBlockEntity) this.te).getUpgradeTier(0, TierItem.ItemType.SOLAR_FOCUS))
		{
			case 0:l = 0; break;
			case 1:l = 6; break;
			case 2:l = 15; break;
			case 3:l = 25; break;
			case 4:l = 35; break;
		}
		int j = getWorkLoadScaled(17);
		blit(mStack, this.leftPos + 133, this.topPos + 36, 205, 56, j, l);
	}

	private int getWorkLoadScaled(int pixels)
	{
		int i = menu.getValue(2);
		int j = ((SolarGeneratorBlockEntity) this.te).getTicksPerAmount();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	@Override
	protected ResourceLocation getBackgroundTexture() { return BACKGROUND; }

	@Override
	protected String getGuideEntryString()
	{
		return "solar_generator";
	}
}
