package net.kaneka.planttech2.gui;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.MachineBulbReprocessorContainer;
import net.kaneka.planttech2.items.MachineBulbItem;
import net.kaneka.planttech2.packets.ButtonPressMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.tileentity.machine.MachineBulbReprocessorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MachineBulbReprocessorScreen extends BaseContainerScreen<MachineBulbReprocessorContainer>
{ 
	private static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/machinebulbreprocessor.png");
	private static final ResourceLocation TEXTURES_COLORBLIND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container_colorblind/machinebulbreprocessor.png");
	
	
	public MachineBulbReprocessorScreen(MachineBulbReprocessorContainer container, PlayerInventory player, ITextComponent name)
    {
    	super(container, player, name);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		int l = this.getCookProgressScaled(30);
		blit(this.guiLeft + 88, this.guiTop + 89, 32, 200, l, 8);
		
		int k = this.getEnergyStoredScaled(55);
		blit(this.guiLeft + 149, this.guiTop + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
		
		int j = this.getFluidStoredScaled(55);
		blit(this.guiLeft + 41, this.guiTop + 28 + (55-j), 224, 55-j, 16, 0 + j);
		
		int m = container.getValue(5) - 1;
		if(m >= 0)
		{
			blit(this.guiLeft + 59 + (m % 5)*18, this.guiTop + 27 + ((int)m/5)*18, 0, 200, 16, 16);
		}
		
		int n = container.getValue(6); 
		int x = 0; 
		int y = 0; 
		for(MachineBulbItem bulb: ModItems.MACHINEBULBS)
		{
			Block machine = bulb.getMachine(); 
			if(machine != null)
			{
				if(n <  bulb.getTier())
				{
					blit(this.guiLeft + 59 + x*18, this.guiTop + 27 + y*18, 16, 200, 16, 16);
				}
			}
			x++; 
			if(x > 4)
			{
				x = 0; 
				y++; 
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int x = 0; 
		int y = 0; 
		for(MachineBulbItem bulb: ModItems.MACHINEBULBS)
		{
			Block machine = bulb.getMachine(); 
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
		int i = container.getValue(4);
		return i != 0 ? i * pixels / ((MachineBulbReprocessorTileEntity) this.te).ticksPerItem() : 0;
	}
	
	@Override
	protected void drawTooltips(int mouseX, int mouseY)
	{
		drawTooltip( container.getValue(2) + "/" + container.getValue(3), mouseX, mouseY, 41, 28, 16, 55);
	    
	    int x = 0; 
		int y = 0; 
		for(MachineBulbItem bulb: ModItems.MACHINEBULBS)
		{
			Block machine = bulb.getMachine(); 
			if(machine != null)
			{
				drawTooltip( new ItemStack(machine).getHighlightTip(machine.getNameTextComponent().getFormattedText()), mouseX, mouseY, 59 + x*18, 27 + y*18, 16, 16);
			}
			x++; 
			if(x > 4)
			{
				x = 0; 
				y++; 
			}
		}
	    super.drawTooltips(mouseX,mouseY);
	}
	
	@Override
    public boolean mouseClicked(double mouseX, double mouseY, int p_mouseClicked_5_)
    {
    	for(int y = 0; y < 6; y++)
    	{
    	    for(int x = 0; x < 5; x++)
    	    {
        		if(inItemStackArea(mouseX, mouseY, 59 + x * 18, 27 + y * 18) && x + y * 5 < ModItems.MACHINEBULBS.size())
        		{ 
        		    PlantTech2PacketHandler.sendToServer(new ButtonPressMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), x + y * 5 + 1));
        		}
    	    }
    	}
        return super.mouseClicked(mouseX, mouseY, p_mouseClicked_5_);
    }
	
	public void renderItem(ItemStack itemstack, int x, int y)
	{
		itemRenderer.renderItemAndEffectIntoGUI(itemstack, x, y);
	}
	
	private boolean inArea(double mouseX, double mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
		{
			return true;
		}
		return false;
	}

	private boolean inItemStackArea(double mouseX, double mouseY, int posX, int posY)
	{
		return this.inArea(mouseX, mouseY, posX, posY, 16, 16);
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
