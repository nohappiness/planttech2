package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.CompressorContainer;
import net.kaneka.planttech2.packets.ButtonPressMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.tileentity.machine.CompressorTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CompressorScreen extends BaseContainerScreen<CompressorContainer>
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/compressor.png");

    public CompressorScreen(CompressorContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }

	@Override
    public boolean mouseClicked(double mouseX, double mouseY, int p_mouseClicked_5_)
    {
	for(int y = 0; y < 3; y++)
	{
	    for(int x = 0; x < 6; x++)
	    {
    		if(inArea(mouseX, mouseY, 35 + x * 18, 26 + y * 18))
    		{ 
    		    PlantTech2PacketHandler.sendToServer(new ButtonPressMessage(te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), x + y * 6));
    		}
	    }
	}
        return super.mouseClicked(mouseX, mouseY, p_mouseClicked_5_);
    }

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(mStack, partialTicks, mouseX, mouseY);

		int l = this.getCookProgressScaled(70);
		blit(mStack, this.leftPos + 53, this.topPos + 81, 0, 200, l, 8);

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, k);
		int i = menu.getValue(3) - 2;
		if(i >= 0)
			blit(mStack, this.leftPos + 34 + (i % 6) * 18, this.topPos + 25 + (i / 6) * 18, 224, 0, 18, 18);
	}

    private int getCookProgressScaled(int pixels)
    {
	int i = menu.getValue(2);
	return i != 0 ? i * pixels / ((CompressorTileEntity) this.te).ticksPerItem() : 0;
    }
    private boolean inArea(double mouseX, double mouseY, int posX, int posY)
    {
		posX += this.leftPos;
		posY += this.topPos;
		return mouseX >= posX && mouseX <= posX + 18 && mouseY >= posY && mouseY <= posY + 18;
    }

    @Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}

	@Override
	protected String getGuideEntryString()
	{
		return "compressor";
	}
}
