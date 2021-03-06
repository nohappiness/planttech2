package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.machine.MachineBulbReprocessorBlockEntity;
import net.kaneka.planttech2.inventory.MachineBulbReprocessorMenu;
import net.kaneka.planttech2.items.MachineBulbItem;
import net.kaneka.planttech2.packets.ButtonPressMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class MachineBulbReprocessorScreen extends BaseContainerScreen<MachineBulbReprocessorMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/machinebulbreprocessor.png");

	public MachineBulbReprocessorScreen(MachineBulbReprocessorMenu container, Inventory player, Component name)
    {
    	super(container, player, name);
    }

	@Override
	protected void renderBg(PoseStack pStack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(pStack, partialTicks, mouseX, mouseY);

		int l = this.getCookProgressScaled(32);
		blit(pStack, this.leftPos + 87, this.topPos + 88, 32, 200, l, 10);

		int k = this.getEnergyStoredScaled(55);
		blit(pStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, k);

		int j = this.getFluidStoredScaled(55);
		blit(pStack, this.leftPos + 41, this.topPos + 28 + (55-j), 224, 55-j, 16, j);

		int m = menu.getValue(5) - 1;
		if(m >= 0)
			blit(pStack, this.leftPos + 59 + (m % 5)*18, this.topPos + 27 + ((int)m/5)*18, 0, 200, 16, 16);

		int n = menu.getValue(6);
		int x = 0;
		int y = 0;
		for(Supplier<MachineBulbItem> bulb: ModItems.MACHINE_BULBS)
		{
			Block machine = bulb.get().getMachine();
			if(machine != null)
				if(n <  bulb.get().getTier())
					blit(pStack, this.leftPos + 59 + x*18, this.topPos + 27 + y*18, 16, 200, 16, 16);
			x++;
			if(x > 4)
			{
				x = 0;
				y++;
			}
		}
	}

//	@Override
//	protected void drawGuiContainerBackgroundLayer(PoseStack mStack, float partialTicks, int mouseX, int mouseY)
//	{
//		super.drawGuiContainerBackgroundLayer(mStack, partialTicks, mouseX, mouseY);
//
//		int l = this.getCookProgressScaled(32);
//		blit(mStack, this.guiLeft + 87, this.guiTop + 88, 32, 200, l, 10);
//
//		int k = this.getEnergyStoredScaled(55);
//		blit(mStack, this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
//
//		int j = this.getFluidStoredScaled(55);
//		blit(mStack, this.guiLeft + 41, this.guiTop + 28 + (55-j), 224, 55-j, 16, 0 + j);
//
//		int m = container.getValue(5) - 1;
//		if(m >= 0)
//		{
//			blit(mStack, this.guiLeft + 59 + (m % 5)*18, this.guiTop + 27 + ((int)m/5)*18, 0, 200, 16, 16);
//		}
//
//		int n = container.getValue(6);
//		int x = 0;
//		int y = 0;
//		for(MachineBulbItem bulb: ModItems.MACHINEBULBS)
//		{
//			Block machine = bulb.getMachine();
//			if(machine != null)
//			{
//				if(n <  bulb.getTier())
//				{
//					blit(mStack, this.guiLeft + 59 + x*18, this.guiTop + 27 + y*18, 16, 200, 16, 16);
//				}
//			}
//			x++;
//			if(x > 4)
//			{
//				x = 0;
//				y++;
//			}
//		}
//	}

	@Override
	protected void renderLabels(PoseStack mStack, int mouseX, int mouseY)
	{
		super.renderLabels(mStack, mouseX, mouseY);
		int x = 0;
		int y = 0;
		for(Supplier<MachineBulbItem> bulb: ModItems.MACHINE_BULBS)
		{
			Block machine = bulb.get().getMachine();
			if(machine != null)
			{
				renderItem(new ItemStack(machine), 59 + x*18, 27 + y*18);
			}
			x++;
			if(x > 4)
			{
				x = 0;
				y++;
			}
		}
	}

	private int getCookProgressScaled(int pixels)
	{
		int i = menu.getValue(4);
		return i != 0 ? i * pixels / ((MachineBulbReprocessorBlockEntity) this.te).ticksPerItem() : 0;
	}

	@Override
	protected void drawTooltips(PoseStack mStack, int mouseX, int mouseY)
	{
		drawTooltip(mStack, menu.getValue(2) + "/" + menu.getValue(3), mouseX, mouseY, 41, 28, 16, 55);

	    int x = 0;
		int y = 0;
		for(Supplier<MachineBulbItem> bulb: ModItems.MACHINE_BULBS)
		{
			Block machine = bulb.get().getMachine();
			if(machine != null)
				drawTooltip(mStack, new ItemStack(machine).getHighlightTip(machine.getName()).getString(), mouseX, mouseY, 59 + x*18, 27 + y*18, 16, 16);
			x++;
			if(x > 4)
			{
				x = 0;
				y++;
			}
		}
	    super.drawTooltips(mStack, mouseX, mouseY);
	}

	@Override
    public boolean mouseClicked(double mouseX, double mouseY, int p_mouseClicked_5_)
    {
    	for(int y = 0; y < 6; y++)
    	{
    	    for(int x = 0; x < 5; x++)
    	    {
        		if(inItemStackArea(mouseX, mouseY, 59 + x * 18, 27 + y * 18) && x + y * 5 < ModItems.MACHINE_BULBS.size())
        		{
        		    PlantTech2PacketHandler.sendToServer(new ButtonPressMessage(te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), x + y * 5 + 1));
        		}
    	    }
    	}
        return super.mouseClicked(mouseX, mouseY, p_mouseClicked_5_);
    }

	public void renderItem(ItemStack itemstack, int x, int y)
	{
		itemRenderer.renderAndDecorateItem(itemstack, x, y);
	}

	private boolean inArea(double mouseX, double mouseY, int posX, int posY, int width, int height)
	{
		posX += this.leftPos;
		posY += this.topPos;
		return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
	}

	private boolean inItemStackArea(double mouseX, double mouseY, int posX, int posY)
	{
		return this.inArea(mouseX, mouseY, posX, posY, 16, 16);
	}

	@Override
	protected ResourceLocation getBackgroundTexture()
	{
		return BACKGROUND;
	}

	@Override
	protected String getGuideEntryString()
	{
		return "machinebulb_reprocessor";
	}
}
