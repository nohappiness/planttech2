package net.kaneka.planttech2.gui;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;

public class TechVillagerScreen extends ContainerScreen<TechVillagerContainer>
{
	protected static final ResourceLocation TEXTURE = new ResourceLocation(PlantTechMain.MODID + ":textures/gui/container/techvillager.png");
	protected final PlayerInventory player;
	protected int invsize;
	protected IEnergyStorage energystorage;
	protected TechVillagerTrade selectedTrade = null;
	protected TechVillagerTask selectedTask = null;
	protected int selectedID = -1;
	protected List<String> canTradeStrings = new ArrayList<String>();
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
		this.xSize = 442;
		this.ySize = 196;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		canTradeStrings = getCanTradeStrings();
		profession = TechVillagerEntity.getProfessionString(container.getProfession());
		professionname = new TranslationTextComponent("techvillager.profession." + TechVillagerEntity.getProfessionString(container.getProfession())).getUnformattedComponentText();
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
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		checkPlayerTrustLevel();
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderSelectedTrades();
		this.renderHoveredToolTip(mouseX, mouseY);
		this.renderTooltips(mouseX, mouseY);
	}

	protected void renderSelectedTrades()
	{
		RenderHelper.disableStandardItemLighting();
		RenderSystem.enableDepthTest();
		if (selectedTrade != null)
		{
			
			for (int i = 0; i < selectedTrade.getInputs().size(); i++)
			{
				renderItem(selectedTrade.getInputs().get(i), 147 + (i % 3) * 18, 71 + (int) (i / 3) * 18);
			}
			for (int i = 0; i < selectedTrade.getOutputs().size(); i++)
			{
				renderItem(selectedTrade.getOutputs().get(i), 255 + (i % 3) * 18, 71 + (int) (i / 3) * 18);
			}
			
		}
		else if(selectedTask != null)
		{
			for (int i = 0; i < selectedTask.getInputs().size(); i++)
			{
				renderItem(selectedTask.getInputs().get(i), 147 + (i % 2) * 18, 53 + (int) (i / 2) * 18);
			}
		}
		RenderSystem.enableDepthTest();
	}

	public void renderTooltips(int mouseX, int mouseY)
	{
		if (canTradeStrings.size() > 0)
		{
			if(selectedTrade != null)
			{
				drawTooltip(canTradeStrings, mouseX, mouseY, 202, 87, 50, 20);
			}
			else if(selectedTask != null)
			{
				drawTooltip(canTradeStrings, mouseX, mouseY, 185, 87, 50, 20);
			}
		}
		
		ITechVillagerTrust trust = player.player.getCapability(TechVillagerTrust.INSTANCE).orElse(null);
		if(trust != null)
		{
			int level = trust.getLevel(profession);
			if(level < trust.getMaxLevel())
			{
				float trustbefore = trust.getLevelTrust(level);
    			float trustatm = trust.getTrust(profession) - trustbefore;
    			float trustneeded = trust.getLevelTrust(level + 1) - trustbefore;
    			drawTooltip((int)trustatm + "/" + (int)trustneeded + " Trust", mouseX, mouseY, 141, 23, 172, 2);
			}
			else
			{
				drawTooltip("MAX", mouseX, mouseY, 141, 23, 172, 2);
			}
		}
		
	}

	public void drawTooltip(List<String> lines, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
		{
			renderTooltip(lines, mouseX, mouseY);
		}
	}
	
	public void drawTooltip(String line, int mouseX, int mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop;
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height)
		{
			renderTooltip(line, mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		font.drawString(professionname, (this.xSize / 2 - font.getStringWidth(professionname) / 2), 10, Integer.parseInt("00e803",16));
		font.drawString("Trades", 70, 14, Integer.parseInt("00e803", 16));
		font.drawString("Jobs", 354, 14, Integer.parseInt("00e803", 16));
		font.drawString(String.valueOf(playertrustlevel), 140, 20, Integer.parseInt("00e803", 16));
		font.drawString(String.valueOf(playertrustlevel + 1), 309, 20, Integer.parseInt("00e803", 16));
		for (int i = 0; i < container.getTrades().size(); i++)
		{
			String color = "00e803"; 
			if(container.getTrades().get(i).getNeededLevel() > playertrustlevel)
			{
				color = "000000";
			}
			font.drawString(container.getTrades().get(i).getName(), 43, 32 + i * 20, Integer.parseInt(color, 16));
		}

		for (int i = 0; i < container.getTasks().size(); i++)
		{
			String color = "00e803"; 
			if(container.getTasks().get(i).getMinTrustLevel() > playertrustlevel)
			{
				color = "000000";
			}
			font.drawString(container.getTasks().get(i).getName(), 321, 32 + i * 20, Integer.parseInt(color, 16));
		}

		if (selectedTrade != null)
		{
			String color = "00e803"; 
			if(selectedTrade.getNeededLevel() > playertrustlevel)
			{
				color = "000000";
			}
			font.drawString("Trade", 212, 93, Integer.parseInt(color, 16));
			font.drawString(String.valueOf(selectedTrade.getCreditsBuy()), 193 - String.valueOf(selectedTrade.getCreditsBuy()).length() * 6, 61, Integer.parseInt("00e803", 16));
			font.drawString(String.valueOf(selectedTrade.getCreditsSell()), 301 - String.valueOf(selectedTrade.getCreditsSell()).length() * 6, 61, Integer.parseInt("00e803", 16));
		} 
		else if (selectedTask != null)
		{
			String color = "00e803"; 
			if(selectedTask.getMinTrustLevel() > playertrustlevel)
			{
				color = "000000";
			}
			font.drawString("Trade", 194, 93, Integer.parseInt(color, 16));
			font.drawString(String.valueOf(selectedTask.getTrust()) + " Trust", 317 - String.valueOf(selectedTask.getTrust() + " Trust").length() * 6, 78, Integer.parseInt("00e803", 16));
			
		} 
		else
		{
			font.drawString("Maybe some kind of intoduction?", 142, 30, Integer.parseInt("00e803", 16));
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(TEXTURE);
		blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512, 512);
		int trust = getTrustScaled(172); 
		blit(this.guiLeft + 141, this.guiTop + 23, 225, 197, trust , 2, 512, 512);
		
		for (int i = 0; i < container.getTrades().size(); i++)
		{
			int k = inArea(mouseX, mouseY, 39, 25 + i * 20, 98, 20) ? 1 : 0;
			if (selectedTrade != null)
			{
				if (i == selectedID)
				{
					k = 1;
				}
			}
			if(container.getTrades().get(i).getNeededLevel() > playertrustlevel)
			{
				if((i == selectedID && selectedTrade != null) || k == 1)
				{
					k = 3; 
				}
				else
				{
					k = 2; 
				}
			}
			blit(this.guiLeft + 39, this.guiTop + 25 + i * 20, 0, 197 + 20 * k, 98, 20, 512, 512);
		}

		for (int i = 0; i < container.getTasks().size(); i++)
		{
			int k = inArea(mouseX, mouseY, 317, 25 + i * 20, 98, 20) ? 1 : 0;
			if (selectedTask != null)
			{
				if (i == selectedID)
				{
					k = 1;
				}
			}
			
			if(container.getTasks().get(i).getMinTrustLevel() > playertrustlevel)
			{
				if((i == selectedID && selectedTask != null) || k == 1)
				{
					k = 3; 
				}
				else
				{
					k = 2; 
				}
			}
			blit(this.guiLeft + 317, this.guiTop + 25 + i * 20, 0, 197 + 20 * k, 98, 20, 512, 512);
		}
		if (selectedTrade != null)
		{
			int k = inArea(mouseX, mouseY, 203, 87, 48, 20) ? 1 : 0;
			if(selectedTrade.getNeededLevel() > playertrustlevel)
			{
				if(k == 1)
				{
					k = 3; 
				}
				else
				{
					k = 2; 
				}
			}
			
			blit(this.guiLeft + 203, this.guiTop + 87, 98, 197 + 20 * k, 48, 20, 512, 512);
			blit(this.guiLeft + 145, this.guiTop + 59, 450, 0, 56, 48, 512, 512);
			blit(this.guiLeft + 253, this.guiTop + 59, 450, 0, 56, 48, 512, 512);
			blit(this.guiLeft + 203, this.guiTop + 76, 450, 59, 48, 9, 512, 512);
		}
		else if(selectedTask != null)
		{
			int k = inArea(mouseX, mouseY, 185, 87, 48, 20) ? 1 : 0;
			if(selectedTask.getMinTrustLevel() > playertrustlevel || selectedTask.getMaxTrustLevel() < playertrustlevel)
			{
				if(k == 1)
				{
					k = 3; 
				}
				else
				{
					k = 2; 
				}
			}
			blit(this.guiLeft + 185, this.guiTop + 87, 98, 197 + 20 * k, 48, 20, 512, 512);
			blit(this.guiLeft + 145, this.guiTop + 51, 450, 68, 38, 56, 512, 512);
			blit(this.guiLeft + 185, this.guiTop + 76, 450, 59, 48, 9, 512, 512);
			blit(this.guiLeft + 235, this.guiTop + 76, 146, 197, 79, 20, 512, 512);
		}
	}

	@SuppressWarnings("deprecation")
	public void renderItem(ItemStack stack, int x, int y)
	{
		RenderSystem.color3f(0.0F, 0.0F, 32.0F);
		this.setBlitOffset(200);
		this.itemRenderer.zLevel = 200.0F;
		FontRenderer font = stack.getItem().getFontRenderer(stack);
		if (font == null)
		{
			font = this.font;
		}
		itemRenderer.renderItemAndEffectIntoGUI(stack, this.guiLeft + x, this.guiTop + y);
		this.itemRenderer.renderItemOverlayIntoGUI(font, stack, this.guiLeft + x, this.guiTop + y, null);
		this.setBlitOffset(0);
		this.itemRenderer.zLevel = 0.0F;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int p_mouseClicked_5_)
	{
		for (int i = 0; i < container.getTrades().size(); i++)
		{
			if (inArea(mouseX, mouseY, 39, 25 + i * 20, 98, 20))
			{
				selectedTrade = container.getTrades().get(i);
				selectedTask = null; 
				selectedID = i;

			}
		}
		
		for (int i = 0; i < container.getTasks().size(); i++)
		{
			if (inArea(mouseX, mouseY, 317, 25 + i * 20, 98, 20))
			{
				selectedTask = container.getTasks().get(i);
				selectedTrade = null;
				selectedID = i;

			}
		}
		
		if (selectedTrade != null && inArea(mouseX, mouseY, 203, 76, 48, 20))
		{
			if (getCanTradeStrings().size() <= 0)
			{
				PlantTech2PacketHandler.sendToServer(new DoTechVillagerTradeMessage(selectedTrade, container.getProfession()));
			}
		}
		
		if (selectedTask != null && inArea(mouseX, mouseY, 185, 87, 50, 20))
		{
			if (getCanTradeStrings().size() <= 0)
			{
				PlantTech2PacketHandler.sendToServer(new DoTechVillagerTaskMessage(selectedTask.getID()));
			}
		}
		canTradeStrings = getCanTradeStrings();
		checkPlayerTrustLevel();
		return super.mouseClicked(mouseX, mouseY, p_mouseClicked_5_);
	}

	private boolean inArea(double mouseX, double mouseY, int startX, int startY, int width, int height)
	{
		startX += this.guiLeft;
		startY += this.guiTop;
		if (mouseX >= startX && mouseX <= startX + width && mouseY >= startY && mouseY <= startY + height)
		{
			return true;
		}
		return false;
	}

	public List<String> getCanTradeStrings()
	{
		List<String> list = new ArrayList<String>();
		if (selectedTrade != null)
		{
			if(selectedTrade.getNeededLevel() <= playertrustlevel)
			{
    			if (PlayerInventoryUtils.enoughSpace(player, selectedTrade.getOutputs().size()))
    			{
    				if (PlayerInventoryUtils.hasList(player, selectedTrade.getInputs()))
    				{
    					if (PlayerInventoryUtils.enoughCredits(player, selectedTrade.getCreditsBuy()))
    					{
    						if (selectedTrade.getCreditsSell() > 0 && player.count(ModItems.PLANTCARD) > 0)
    						{
    
    						} else if (selectedTrade.getCreditsSell() <= 0)
    						{
    
    						} else
    						{
    							list.add("No plantcard in inventory");
    						}
    
    					} else
    					{
    						list.add("Not enough credits");
    					}
    				} else
    				{
    					list.add("Not enough items");
    				}
    			} else
    			{
    				list.add("Not enough space");
    			}
			}
			else
			{
				list.add("Not enough trust");
			}
		} 
		else if(selectedTask != null)
		{
			if (PlayerInventoryUtils.hasList(player, selectedTask.getInputs()))
			{
				if(playertrustlevel >= selectedTask.getMinTrustLevel() )
				{
					if(playertrustlevel <= selectedTask.getMaxTrustLevel())
					{
						
					}
					else
    				{
    					list.add("To much Trust");
    				}
				}
				else
				{
					list.add("Not enough Trust");
				}
			}
			else
			{
				list.add("Not enough items");
			}
		}
		else
		{
			list.add("No trade selected");
		}
		return list;
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
