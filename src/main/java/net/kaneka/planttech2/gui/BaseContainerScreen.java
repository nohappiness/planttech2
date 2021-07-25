package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyBlockEntity;
import net.kaneka.planttech2.gui.guide.Guide;
import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.kaneka.planttech2.inventory.BaseMenu;
import net.kaneka.planttech2.inventory.BaseMenu.SlotItemHandlerWithInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class BaseContainerScreen<T extends BaseMenu> extends AbstractContainerScreen<T>
{
	//protected static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/solargenerator.png");
	protected final Inventory player;
	protected final EnergyBlockEntity te;
	protected abstract ResourceLocation getBackgroundTexture();

	@SuppressWarnings("unchecked")
	public BaseContainerScreen(BaseMenu inventorySlotsIn, Inventory inventoryPlayer, Component title)
	{
		super((T) inventorySlotsIn, inventoryPlayer, title);
		
		this.te = inventorySlotsIn.getTE(); 
		this.player = inventoryPlayer; 
	}
	
	@Override
	public void init()
    {
        super.init();
        this.imageWidth = 208; 
        this.imageHeight = 200; 
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }



	@Override
	public void render(PoseStack mStack, int mouseX, int mouseY, float partialTicks)
	{
			this.renderBackground(mStack);
			super.render(mStack, mouseX, mouseY, partialTicks);
			this.drawTooltips(mStack, mouseX, mouseY);
//	        this.renderHoveredToolTip(mStack, mouseX, mouseY);
	        this.renderTooltip(mStack, mouseX, mouseY);
	}
	
	protected void drawTooltips(PoseStack mStack, int mouseX, int mouseY)
	{
		drawTooltip(mStack, te.getEnergyStored() + "/" + te.getMaxEnergyStored(), mouseX, mouseY, 148, 27, 16, 55);
	}
	
	public void drawTooltip(PoseStack mStack, String lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		
		posX += this.leftPos;
		posY += this.topPos; 
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) 
        {
        	renderTooltip(mStack, new TextComponent(lines), mouseX, mouseY);
        }
    }

	@Override
	protected void renderBg(PoseStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().getTexture(getBackgroundTexture());
		blit(mStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(PoseStack mStack, int mouseX, int mouseY)
	{
		String tileName = title.getString();
		int textcolor = Integer.parseInt("000000",16);
		font.draw(mStack, tileName, (this.imageWidth / 2.0F - font.width(tileName) / 2.0F) + 1, 14, textcolor);
	}
	
	protected int getEnergyStoredScaled(int pixels)
	{
		int i = menu.getValue(0);
		int j = menu.getValue(1);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	protected int getFluidStoredScaled(int pixels)
	{
		int i = menu.getValue(2);
		int j = menu.getValue(3);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}

	//renderHoveredToolTip
	@Override
	protected void renderTooltip(PoseStack mStack, int x, int y)
	{
		if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && !this.hoveredSlot.hasItem() && this.hoveredSlot instanceof BaseMenu.SlotItemHandlerWithInfo)
			this.renderTooltip(mStack, new TranslatableComponent(((SlotItemHandlerWithInfo) this.hoveredSlot).getUsageString()), x, y);
		else
			super.renderTooltip(mStack, x, y);
	}

//	@Override
//	protected void renderHoveredToolTip(PoseStack mstack, int x, int y)
//	{
//		if (this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && !this.hoveredSlot.getHasStack() && this.hoveredSlot instanceof BaseContainer.SlotItemHandlerWithInfo)
//		{
//	         this.renderTooltip(new TranslationTextComponent(((SlotItemHandlerWithInfo) this.hoveredSlot).getUsageString()).getString(), x, y);
//	    }
//		else
//		{
//			super.renderHoveredToolTip(x, y);
//		}
//	}
	
	@Override
	public boolean mouseClicked(double posX, double posY, int buttonid)
	{
		if(posX - leftPos >= 190 && posX - leftPos <= 190 + 10 && posY - topPos >= 15 && posY - topPos <= 15 + 11)
		{
			Guide guide = new Guide();
			int menu = guide.getMenuByName(getGuideMenuString()); 
			int entry = guide.getEntryByName(menu, getGuideEntryString()); 
			Minecraft.getInstance().setScreen(new GuideScreen(menu, entry));
			return true; 
		}
		return super.mouseClicked(posX, posY, buttonid);
	}
	
	protected String getGuideMenuString()
	{
		return "machines"; 
	}
	
	abstract protected String getGuideEntryString();

}
