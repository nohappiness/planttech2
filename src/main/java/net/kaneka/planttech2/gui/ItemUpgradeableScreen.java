package net.kaneka.planttech2.gui;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ItemUpgradeableContainer;
import net.kaneka.planttech2.items.upgradeable.BaseUpgradeableItem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemUpgradeableScreen extends ContainerScreen<ItemUpgradeableContainer>
{
	protected static final Map<Integer, ResourceLocation> BACKGROUND = new HashMap<Integer, ResourceLocation>()	{
		private static final long serialVersionUID = 1L; {
			put(10, new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/itemupgradeable_10.png"));
	    }
	};
	protected final PlayerInventory player;
    protected ItemStack stack; 
    protected int invsize; 
    protected IEnergyStorage energystorage; 

    public ItemUpgradeableScreen(ItemUpgradeableContainer container, PlayerInventory inv, ITextComponent name)
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
	public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks)
	{
			this.renderBackground(mStack);
			super.render(mStack, mouseX, mouseY, partialTicks);
			this.drawTooltips(mStack, mouseX, mouseY);
	        this.renderTooltip(mStack, mouseX, mouseY);
	}

	protected void drawTooltips(MatrixStack mStack, int mouseX, int mouseY)
	{
		if(energystorage != null)
			drawTooltip(mStack, energystorage.getEnergyStored() + "/" + energystorage.getMaxEnergyStored(), mouseX, mouseY, 162, 28, 16, 74);
	}
	
	public void drawTooltip(MatrixStack mStack, String lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.leftPos;
		posY += this.topPos;
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
			renderComponentHoverEffect(mStack, null, mouseX, mouseY);
//            renderComponentHoverEffect(mStack, new StringTextComponent(lines), mouseX, mouseY);
    }

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int x, int y)
	{
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bind(BACKGROUND.get(invsize));
		blit(mStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

		int k = this.getEnergyStoredScaled(55);
		blit(mStack, this.leftPos + 149, this.topPos + 28 + (55 - k), 208, 55 - k, 16, 0 + k);
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
