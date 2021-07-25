package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.inventory.ItemUpgradeableContainer;
import net.kaneka.planttech2.items.upgradeable.BaseUpgradeableItem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.HashMap;
import java.util.Map;

public class ItemUpgradeableScreen extends AbstractContainerScreen<ItemUpgradeableContainer>
{
	protected static final Map<Integer, ResourceLocation> BACKGROUND = new HashMap<Integer, ResourceLocation>()	{
		private static final long serialVersionUID = 1L; {
			put(10, new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/itemupgradeable_10.png"));
	    }
	};
	protected final Inventory player;
    protected ItemStack stack; 
    protected int invsize; 
    protected IEnergyStorage energystorage; 

    public ItemUpgradeableScreen(ItemUpgradeableContainer container, Inventory inv, Component name)
    {
    	super(container, inv, name); 
    	this.player = inv; 
    	stack = container.getStack(); 
    	this.invsize = BaseUpgradeableItem.getInventorySize(stack); 
		energystorage = BaseUpgradeableItem.getEnergyCap(stack); 
    }
	
	@Override
	public void init()
    {
        super.init();
        this.imageWidth = 205; 
        this.imageHeight = 202;     
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }
	
	@Override
	public void render(PoseStack pStack, int mouseX, int mouseY, float partialTicks)
	{
			this.renderBackground(pStack);
			super.render(pStack, mouseX, mouseY, partialTicks);
			this.drawTooltips(pStack, mouseX, mouseY);
	        this.renderTooltip(pStack, mouseX, mouseY);
	}

	protected void drawTooltips(PoseStack pStack, int mouseX, int mouseY)
	{
		if(energystorage != null)
			drawTooltip(pStack, energystorage.getEnergyStored() + "/" + energystorage.getMaxEnergyStored(), mouseX, mouseY, 162, 28, 16, 74);
	}
	
	public void drawTooltip(PoseStack mStack, String lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.leftPos;
		posY += this.topPos;
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
			renderComponentHoverEffect(mStack, null, mouseX, mouseY);
//            renderComponentHoverEffect(mStack, new TextComponent(lines), mouseX, mouseY);
    }

	@Override
	protected void renderBg(PoseStack pStack, float partialTicks, int x, int y)
	{
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().getTexture(BACKGROUND.get(invsize));
		blit(pStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

		int k = this.getEnergyStoredScaled(55);
		blit(pStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
	}

	protected int getEnergyStoredScaled(int pixels)
	{
		
		if(energystorage != null)
		{
			int i = energystorage.getEnergyStored();
			int j = energystorage.getMaxEnergyStored();
			return i != 0 && j != 0 ? i * pixels / j : 0; 
		}
		return 0; 
	}

	@Override
	protected boolean checkHotbarKeyPressed(int keyCode, int scanCode)
	{
		return false;
	}
}
