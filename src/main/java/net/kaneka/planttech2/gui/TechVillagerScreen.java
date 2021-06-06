package net.kaneka.planttech2.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.entities.TechVillagerContainer;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.ITechVillagerTrust;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTask;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTrade;
import net.kaneka.planttech2.packets.DoTechVillagerTaskMessage;
import net.kaneka.planttech2.packets.DoTechVillagerTradeMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.PlayerInventoryUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;

public class TechVillagerScreen extends ContainerScreen<TechVillagerContainer>
{
	protected static final ResourceLocation BACKGROUND = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/techvillager.png");
	protected final PlayerInventory player;
	protected int invsize;
	protected IEnergyStorage energystorage;
	protected TechVillagerTrade selectedTrade = null;
	protected TechVillagerTask selectedTask = null;
	protected int selectedID = -1;
	protected List<ITextComponent> canTradeTextProperties = new ArrayList<>();
	private String profession = "scientist";
	private String professionname = "loading"; 
	private int playertrustlevel = 0; 

	public TechVillagerScreen(TechVillagerContainer container, PlayerInventory inv, ITextComponent name)
	{
		super(container, inv, name);
		this.player = inv;
	}

	@Override
	public void init()
	{
		super.init();
		this.imageWidth = 442;
		this.imageHeight = 196;
		this.leftPos = (this.width - this.imageWidth) / 2;
		this.topPos = (this.height - this.imageHeight) / 2;
		canTradeTextProperties = getCanTradeTextProperties();
		profession = TechVillagerEntity.getProfessionString(menu.getProfession());
		professionname = new TranslationTextComponent("techvillager.profession." + TechVillagerEntity.getProfessionString(menu.getProfession())).getString();
		checkPlayerTrustLevel();
	
	}

	private void checkPlayerTrustLevel()
	{
		ITechVillagerTrust trust = player.player.getCapability(TechVillagerTrust.INSTANCE).orElse(null);
		if(trust != null)
		{
			playertrustlevel = trust.getLevel(profession);
		}
		
	}

	@Override
	public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks)
	{
		checkPlayerTrustLevel();
		this.renderBackground(mStack);
		super.render(mStack, mouseX, mouseY, partialTicks);
		this.renderSelectedTrades();
		this.renderTooltip(mStack, mouseX, mouseY);
		this.renderTooltips(mStack, mouseX, mouseY);
	}

	protected void renderSelectedTrades()
	{
		RenderHelper.turnOff();
		RenderSystem.enableDepthTest();
		if (selectedTrade != null)
		{
			for (int i = 0; i < selectedTrade.getInputs().size(); i++)
				renderItem(selectedTrade.getInputs().get(i), 147 + (i % 3) * 18, 71 + (int) (i / 3) * 18);
			for (int i = 0; i < selectedTrade.getOutputs().size(); i++)
				renderItem(selectedTrade.getOutputs().get(i), 255 + (i % 3) * 18, 71 + (int) (i / 3) * 18);
		}
		else if(selectedTask != null)
			for (int i = 0; i < selectedTask.getInputs().size(); i++)
				renderItem(selectedTask.getInputs().get(i), 147 + (i % 2) * 18, 53 + (int) (i / 2) * 18);
		RenderSystem.enableDepthTest();
	}

	public void renderTooltips(MatrixStack mStack, int mouseX, int mouseY)
	{
		if (canTradeTextProperties.size() > 0)
		{
			if(selectedTrade != null)
				drawTooltip(mStack, canTradeTextProperties, mouseX, mouseY, 202, 87, 50, 20);
			else if(selectedTask != null)
				drawTooltip(mStack, canTradeTextProperties, mouseX, mouseY, 185, 87, 50, 20);
		}
		
		ITechVillagerTrust trust = player.player.getCapability(TechVillagerTrust.INSTANCE).orElse(null);
		int level = trust.getLevel(profession);
		if(level < trust.getMaxLevel())
		{
			float trustbefore = trust.getLevelTrust(level);
			float trustatm = trust.getTrust(profession) - trustbefore;
			float trustneeded = trust.getLevelTrust(level + 1) - trustbefore;
			drawTooltip(mStack, (int)trustatm + "/" + (int)trustneeded + " Trust", mouseX, mouseY, 141, 23, 172, 2);
		}
		else
			drawTooltip(mStack, "MAX", mouseX, mouseY, 141, 23, 172, 2);

	}

	public void drawTooltip(MatrixStack mStack, List<ITextComponent> lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.leftPos;
		posY += this.topPos;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
			for (ITextComponent line : lines)
				renderTooltip(mStack, line, mouseX, mouseY);
	}
	
	public void drawTooltip(MatrixStack mStack, String line, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.leftPos;
		posY += this.topPos;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
			renderTooltip(mStack, new StringTextComponent(line), mouseX, mouseY);
	}

	@Override
	protected void renderLabels(MatrixStack mStack, int mouseX, int mouseY)
	{
		super.renderLabels(mStack, mouseX, mouseY);

		font.draw(mStack, professionname, (this.imageWidth / 2.0F - font.width(professionname) / 2.0F), 10, Integer.parseInt("00e803",16));
		font.draw(mStack, "Trades", 70, 14, Integer.parseInt("00e803", 16));
		font.draw(mStack, "Jobs", 354, 14, Integer.parseInt("00e803", 16));
		font.draw(mStack, String.valueOf(playertrustlevel), 140, 20, Integer.parseInt("00e803", 16));
		font.draw(mStack, String.valueOf(playertrustlevel + 1), 309, 20, Integer.parseInt("00e803", 16));
		for (int i = 0; i < menu.getTrades().size(); i++)
		{
			String color = menu.getTrades().get(i).getNeededLevel() <= playertrustlevel ? "00e803" : "000000";
			font.draw(mStack, menu.getTrades().get(i).getName(), 43, 32 + i * 20, Integer.parseInt(color, 16));
		}

		for (int i = 0; i < menu.getTasks().size(); i++)
		{
			String color = menu.getTasks().get(i).getMinTrustLevel() <= playertrustlevel ? "00e803" : "000000";
			font.draw(mStack, menu.getTasks().get(i).getName(), 321, 32 + i * 20, Integer.parseInt(color, 16));
		}

		if (selectedTrade != null)
		{
			String color = selectedTrade.getNeededLevel() <= playertrustlevel ? "00e803" : "000000";
			font.draw(mStack, "Trade", 212, 93, Integer.parseInt(color, 16));
			font.draw(mStack, String.valueOf(selectedTrade.getCreditsBuy()), 193 - String.valueOf(selectedTrade.getCreditsBuy()).length() * 6, 61, Integer.parseInt("00e803", 16));
			font.draw(mStack, String.valueOf(selectedTrade.getCreditsSell()), 301 - String.valueOf(selectedTrade.getCreditsSell()).length() * 6, 61, Integer.parseInt("00e803", 16));
		}
		else if (selectedTask != null)
		{
			String color = selectedTask.getMinTrustLevel() <= playertrustlevel ? "00e803" : "000000";
			font.draw(mStack, "Trade", 194, 93, Integer.parseInt(color, 16));
			font.draw(mStack, String.valueOf(selectedTask.getTrust()) + " Trust", 317 - String.valueOf(selectedTask.getTrust() + " Trust").length() * 6, 78, Integer.parseInt("00e803", 16));
		}
		else
			font.draw(mStack, "Maybe some kind of intoduction?", 142, 30, Integer.parseInt("00e803", 16));
	}

	@Override
	protected void renderBg(MatrixStack mStack, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bind(BACKGROUND);
		int trust = getTrustScaled(172);
		blit(mStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 512, 512);
		blit(mStack, this.leftPos + 141, this.topPos + 23, 225, 197, trust , 2, 512, 512);
		for (int i = 0; i < menu.getTrades().size(); i++)
		{
			int k = inArea(mouseX, mouseY, 39, 25 + i * 20, 98, 20) ? 1 : 0;
			if (selectedTrade != null)
				if (i == selectedID)
					k = 1;
			if(menu.getTrades().get(i).getNeededLevel() > playertrustlevel)
				k = (i == selectedID && selectedTrade != null) || k == 1 ? 3 : 2;
			blit(mStack, this.leftPos + 39, this.topPos + 25 + i * 20, 0, 197 + 20 * k, 98, 20, 512, 512);
		}

		for (int i = 0; i < menu.getTasks().size(); i++)
		{
			int k = inArea(mouseX, mouseY, 317, 25 + i * 20, 98, 20) ? 1 : 0;
			if (selectedTask != null)
				if (i == selectedID)
					k = 1;

			if(menu.getTasks().get(i).getMinTrustLevel() > playertrustlevel)
				k = (i == selectedID && selectedTask != null) || k == 1 ? 3 : 2;
			blit(mStack, this.leftPos + 317, this.topPos + 25 + i * 20, 0, 197 + 20 * k, 98, 20, 512, 512);
		}
		if (selectedTrade != null)
		{
			int k = inArea(mouseX, mouseY, 203, 87, 48, 20) ? 1 : 0;
			if(selectedTrade.getNeededLevel() > playertrustlevel)
				k = k == 1 ? 3 : 2;
			blit(mStack, this.leftPos + 203, this.topPos + 87, 98, 197 + 20 * k, 48, 20, 512, 512);
			blit(mStack, this.leftPos + 145, this.topPos + 59, 450, 0, 56, 48, 512, 512);
			blit(mStack, this.leftPos + 253, this.topPos + 59, 450, 0, 56, 48, 512, 512);
			blit(mStack, this.leftPos + 203, this.topPos + 76, 450, 59, 48, 9, 512, 512);
		}
		else if(selectedTask != null)
		{
			int k = inArea(mouseX, mouseY, 185, 87, 48, 20) ? 1 : 0;
			if(selectedTask.getMinTrustLevel() > playertrustlevel || selectedTask.getMaxTrustLevel() < playertrustlevel)
				k = k == 1 ? 3 : 2;
			blit(mStack, this.leftPos + 185, this.topPos + 87, 98, 197 + 20 * k, 48, 20, 512, 512);
			blit(mStack, this.leftPos + 145, this.topPos + 51, 450, 68, 38, 56, 512, 512);
			blit(mStack, this.leftPos + 185, this.topPos + 76, 450, 59, 48, 9, 512, 512);
			blit(mStack, this.leftPos + 235, this.topPos + 76, 146, 197, 79, 20, 512, 512);
		}
	}

	@SuppressWarnings("deprecation")
	public void renderItem(ItemStack stack, int x, int y)
	{
		RenderSystem.color3f(0.0F, 0.0F, 32.0F);
		this.setBlitOffset(200);
		this.itemRenderer.blitOffset = 200.0F;
		FontRenderer font = stack.getItem().getFontRenderer(stack);
		if (font == null)
		{
			font = this.font;
		}
		itemRenderer.renderAndDecorateItem(stack, this.leftPos + x, this.topPos + y);
		this.itemRenderer.renderGuiItemDecorations(font, stack, this.leftPos + x, this.topPos + y, null);
		this.setBlitOffset(0);
		this.itemRenderer.blitOffset = 0.0F;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int p_mouseClicked_5_)
	{
		for (int i = 0; i < menu.getTrades().size(); i++)
		{
			if (inArea(mouseX, mouseY, 39, 25 + i * 20, 98, 20))
			{
				selectedTrade = menu.getTrades().get(i);
				selectedTask = null; 
				selectedID = i;

			}
		}
		
		for (int i = 0; i < menu.getTasks().size(); i++)
		{
			if (inArea(mouseX, mouseY, 317, 25 + i * 20, 98, 20))
			{
				selectedTask = menu.getTasks().get(i);
				selectedTrade = null;
				selectedID = i;

			}
		}
		
		if (selectedTrade != null && inArea(mouseX, mouseY, 203, 76, 48, 20))
		{
			if (getCanTradeTextProperties().size() <= 0)
			{
				PlantTech2PacketHandler.sendToServer(new DoTechVillagerTradeMessage(selectedTrade, menu.getProfession()));
			}
		}
		
		if (selectedTask != null && inArea(mouseX, mouseY, 185, 87, 50, 20))
		{
			if (getCanTradeTextProperties().size() <= 0)
			{
				PlantTech2PacketHandler.sendToServer(new DoTechVillagerTaskMessage(selectedTask.getID()));
			}
		}
		canTradeTextProperties = getCanTradeTextProperties();
		checkPlayerTrustLevel();
		return super.mouseClicked(mouseX, mouseY, p_mouseClicked_5_);
	}

	private boolean inArea(double mouseX, double mouseY, int startX, int startY, int width, int height)
	{
		startX += this.leftPos;
		startY += this.topPos;
		return mouseX >= startX && mouseX <= startX + width && mouseY >= startY && mouseY <= startY + height;
	}

	public List<ITextComponent> getCanTradeTextProperties()
	{
		List<String> list = new ArrayList<>();
		if (selectedTrade != null)
		{
			if(selectedTrade.getNeededLevel() <= playertrustlevel)
			{
    			if (PlayerInventoryUtils.enoughSpace(player, selectedTrade.getOutputs().size()))
    			{
    				if (PlayerInventoryUtils.hasList(player, selectedTrade.getInputs()))
    				{
    					if (PlayerInventoryUtils.enoughCredits(player, selectedTrade.getCreditsBuy()))
    						if (selectedTrade.getCreditsSell() <= 0 && player.countItem(ModItems.PLANTCARD) <= 0 && selectedTrade.getCreditsSell() > 0)
    							list.add("No plantcard in inventory");
    					else
    						list.add("Not enough credits");
    				}
    				else
    					list.add("Not enough items");
    			}
    			else
    				list.add("Not enough space");
			}
			else
				list.add("Not enough trust");
		} 
		else if(selectedTask != null)
		{
			if (PlayerInventoryUtils.hasList(player, selectedTask.getInputs()))
			{
				if(playertrustlevel >= selectedTask.getMinTrustLevel())
					if(playertrustlevel > selectedTask.getMaxTrustLevel())
    					list.add("To much Trust");
				else
					list.add("Not enough Trust");
			}
			else
				list.add("Not enough items");
		}
		else
			list.add("No trade selected");
		List<ITextComponent> textPropertiesList = new ArrayList<>();
		for (String str : list)
			textPropertiesList.add(new StringTextComponent(str));
		return textPropertiesList;
	}
	
	private int getTrustScaled(int pixel)
	{
		ITechVillagerTrust trust = player.player.getCapability(TechVillagerTrust.INSTANCE).orElse(null);
		if(trust != null)
		{
			int level = trust.getLevel(profession);
			if(level < trust.getMaxLevel())
			{
				float trustbefore = trust.getLevelTrust(level);
    			float trustatm = trust.getTrust(profession) - trustbefore;
    			float trustneeded = trust.getLevelTrust(level + 1) - trustbefore;
    			return (int)((trustatm / trustneeded) * (float) pixel);
			}
			else
			{
				return pixel; 
			}
			
			
		}
		return 0; 
	}
}
