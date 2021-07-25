package net.kaneka.planttech2.gui;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.gui.buttons.CustomButton;
import net.kaneka.planttech2.inventory.TeleporterContainer;
import net.kaneka.planttech2.items.TeleporterItem;
import net.kaneka.planttech2.items.upgradeable.BaseUpgradeableItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

public class TeleporterScreen extends AbstractContainerScreen<TeleporterContainer>
{
	protected static final ResourceLocation BACKGROUND = new ResourceLocation("planttech2:textures/gui/teleporter.png");
	protected final Inventory player;
	protected int guiLeft;
	protected int guiTop;
	protected ItemStack stack = ItemStack.EMPTY; 
    protected int invsize; 
    protected IEnergyStorage energystorage; 

	
	public TeleporterScreen(TeleporterContainer container, Inventory player, Component name)
    {
    	super(container, player, name);
    	this.player = player; 
    	stack = container.getStack(); 
		energystorage = BaseUpgradeableItem.getEnergyCap(stack); 
    }

	@Override
	public void init()
	{
		super.init();
		this.imageWidth = 441; 
        this.imageHeight = 197; 
		this.guiLeft = (this.width - this.imageWidth) / 2;
		this.guiTop = (this.height - this.imageHeight) / 2;
		if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof TeleporterItem)
		{
			stack = Minecraft.getInstance().player.getMainHandItem();
		}
		else
		{
			this.removed();
		}
		
		int buttonwidth = 300; 
		
		addRenderableWidget(new CustomButton(1, this.guiLeft + 28, this.guiTop + 10 + 0 * 22, buttonwidth, 20, "TEST", (button) ->
		{
			TeleporterScreen.this.buttonClicked(0);
		})); 
		addRenderableWidget(new CustomButton(2, this.guiLeft + 28, this.guiTop + 10 + 1 * 22, buttonwidth, 20, "TEST", (button) ->
		{
			TeleporterScreen.this.buttonClicked(1);
		})); 
		addRenderableWidget(new CustomButton(3, this.guiLeft + 28, this.guiTop + 10 + 2 * 22, buttonwidth, 20, "TEST", (button) ->
		{
			TeleporterScreen.this.buttonClicked(2);
		})); 
		addRenderableWidget(new CustomButton(4, this.guiLeft + 28, this.guiTop + 10 + 3 * 22, buttonwidth, 20, "TEST", (button) ->
		{
			TeleporterScreen.this.buttonClicked(3);
		})); 
		addRenderableWidget(new CustomButton(5, this.guiLeft + 28, this.guiTop + 10 + 4 * 22, buttonwidth, 20, "TEST", (button) ->
		{
			TeleporterScreen.this.buttonClicked(4);
		})); 
		addRenderableWidget(new CustomButton(6, this.guiLeft + 28, this.guiTop + 10 + 5 * 22, buttonwidth, 20, "TEST", (button) ->
		{
			TeleporterScreen.this.buttonClicked(5);
		})); 
		addRenderableWidget(new CustomButton(7, this.guiLeft + 28, this.guiTop + 10 + 6 * 22, buttonwidth, 20, "TEST", (button) ->
		{
			TeleporterScreen.this.buttonClicked(6);
		})); 
		addRenderableWidget(new CustomButton(8, this.guiLeft + 28, this.guiTop + 10 + 7 * 22, buttonwidth, 20, "TEST", (button) ->
		{
			TeleporterScreen.this.buttonClicked(7);
		})); 
		
	}

	@Override
	public void render(PoseStack pStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(pStack);
//		drawGuiContainerBackgroundLayer(mStack, partialTicks, mouseX, mouseY);
		this.drawButtons(pStack, mouseX, mouseY, partialTicks);
		this.drawTooltips(pStack, mouseX, mouseY);
        this.renderTooltip(pStack, mouseX, mouseY);
	}
	
	private void buttonClicked(int id)
	{
		
	}
	
	
	
	private void drawButtons(PoseStack mStack, int mouseX, int mouseY, float partialTicks)
	{
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		for (int i = 0; i < this.renderables.size(); ++i)
		{
			this.renderables.get(i).render(mStack, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	protected void renderBg(PoseStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().getTexture(BACKGROUND);
		blit(mStack, this.guiLeft, this.guiTop, 0, 0, this.imageWidth, this.imageHeight, 512, 512);

		int k = this.getEnergyStoredScaled(157);
		blit(mStack, this.guiLeft + 396, this.guiTop + 23 + (157 - k), 441, 157 - k, 16, k, 512, 512);
	}
	
	protected void drawTooltips(PoseStack mStack, int mouseX, int mouseY)
	{
		if(energystorage != null)
		{
			drawTooltip(mStack, energystorage.getEnergyStored() + "/" + energystorage.getMaxEnergyStored(), mouseX, mouseY, 162, 28, 16, 74);
		}
	}
	
	public void drawTooltip(PoseStack mStack, String lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop; 
        if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
			renderComponentHoverEffect(mStack, null, mouseX, mouseY);
//            renderComponentHoverEffect(mStack, new TextComponent(lines), mouseX, mouseY);
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
