package net.kaneka.planttech2.gui;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.platform.GlStateManager;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ContainerItemUpgradeable;
import net.kaneka.planttech2.items.upgradeable.ItemBaseUpgradeable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;

public class GuiItemUpgradeable extends ContainerScreen<ContainerItemUpgradeable>
{
	protected static final Map<Integer, ResourceLocation> TEXTURES = new HashMap<Integer, ResourceLocation>(){
		private static final long serialVersionUID = 1L;

	{
			put(10, new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/itemupgradeable_10.png"));
	}}; 
	protected final PlayerInventory player;
    protected int xSize = 205;
    protected int ySize = 202;
    protected ItemStack stack; 
    protected int invsize; 
    protected IEnergyStorage energystorage; 

	public GuiItemUpgradeable(PlayerInventory inventoryPlayer, ItemStack stack)
	{
		super(new ContainerItemUpgradeable(inventoryPlayer, stack), inventoryPlayer, new TranslationTextComponent("container.upgradeableitems"));
		this.stack = stack;
		this.invsize = ItemBaseUpgradeable.getInventorySize(stack); 
		this.player = inventoryPlayer; 
		energystorage = ItemBaseUpgradeable.getEnergyCap(stack); 
	}
	
	@Override
	public void init()
    {
        super.init();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
			super.render(mouseX, mouseY, partialTicks);
			this.drawTooltips(mouseX, mouseY);
	        this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	protected void drawTooltips(int mouseX, int mouseY)
	{
		if(energystorage != null)
		{
		drawTooltip( energystorage.getEnergyStored() + "/" + energystorage.getMaxEnergyStored(), mouseX, mouseY, 162, 28, 16, 74);
		}
	}
	
	public void drawTooltip(String lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop; 
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) 
        {
            renderComponentHoverEffect(new StringTextComponent(lines), mouseX, mouseY);
        }
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(TEXTURES.get(invsize));
		blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int k = this.getEnergyStoredScaled(74);
		blit(this.guiLeft + 162, this.guiTop + 28 + (74 - k), 205, 74 - k, 16, 0 + k);
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
}
