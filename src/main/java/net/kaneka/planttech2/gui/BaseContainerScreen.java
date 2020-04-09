package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.configuration.ClientConfig;
import net.kaneka.planttech2.container.BaseContainer;
import net.kaneka.planttech2.container.BaseContainer.SlotItemHandlerWithInfo;
import net.kaneka.planttech2.gui.guide.Guide;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

abstract class BaseContainerScreen<T extends BaseContainer> extends ContainerScreen<T>
{
	protected static final ResourceLocation TEXTURES = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/solargenerator.png");
	protected final PlayerInventory player;
	protected final EnergyTileEntity te;

	@SuppressWarnings("unchecked")
	public BaseContainerScreen(BaseContainer inventorySlotsIn, PlayerInventory inventoryPlayer, ITextComponent title)
	{
		super((T) inventorySlotsIn, inventoryPlayer, title);
		
		this.te = inventorySlotsIn.getTE(); 
		this.player = inventoryPlayer; 
	}
	
	@Override
	public void init()
    {
        super.init();
        this.xSize = 208; 
        this.ySize = 200; 
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
			this.renderBackground();
			super.render(mouseX, mouseY, partialTicks);
			this.drawTooltips(mouseX, mouseY);
	        this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	protected void drawTooltips(int mouseX, int mouseY)
	{
		drawTooltip( te.getEnergyStored() + "/" + te.getMaxEnergyStored(), mouseX, mouseY, 148, 27	, 16, 55);
	}
	
	public void drawTooltip(String lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		
		posX += this.guiLeft;
		posY += this.guiTop; 
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) 
        {
        	renderTooltip(lines, mouseX, mouseY);
        }
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(getBackgroundTexture());
		if(ClientConfig.colorblind_guis.get())
		{
			minecraft.getTextureManager().bindTexture(getBackgroundTextureColorblind());
		}
		blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	protected abstract ResourceLocation getBackgroundTexture();
	protected abstract ResourceLocation getBackgroundTextureColorblind();
	
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String tileName = title.getUnformattedComponentText();
		int textcolor = Integer.parseInt("000000",16); 
		if(ClientConfig.colorblind_guis.get())
		{
			textcolor = Integer.parseInt("000000",16); 
		}
		font.drawString(tileName, (this.xSize / 2 - font.getStringWidth(tileName) / 2) + 1, 14, textcolor);
	}
	
	protected int getEnergyStoredScaled(int pixels)
	{
		int i = container.getValue(0);
		int j = container.getValue(1);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	protected int getFluidStoredScaled(int pixels)
	{
		int i = container.getValue(2);
		int j = container.getValue(3);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	@Override
	protected void renderHoveredToolTip(int x, int y)
	{
		if (this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && !this.hoveredSlot.getHasStack() && this.hoveredSlot instanceof BaseContainer.SlotItemHandlerWithInfo)
		{
	         this.renderTooltip(new TranslationTextComponent(((SlotItemHandlerWithInfo) this.hoveredSlot).getUsageString()).getUnformattedComponentText(), x, y);
	    }
		else
		{
			super.renderHoveredToolTip(x, y);
		}
	}
	
	@Override
	public boolean mouseClicked(double posX, double posY, int buttonid)
	{
		if(posX - guiLeft >= 190 && posX - guiLeft <= 190 + 10 && posY - guiTop >= 15 && posY - guiTop <= 15 + 11)
		{
			Guide guide = new Guide();
			int menu = guide.getMenuByName(getGuideMenuString()); 
			int entry = guide.getEntryByName(menu, getGuideEntryString()); 
			Minecraft.getInstance().displayGuiScreen(new GuideScreen(menu, entry));
			return true; 
		}
		return super.mouseClicked(posX, posY, buttonid);
	}
	
	protected String getGuideMenuString()
	{
		return "Main2"; 
	}
	
	protected String getGuideEntryString()
	{
		return "traits"; 
	}

}
