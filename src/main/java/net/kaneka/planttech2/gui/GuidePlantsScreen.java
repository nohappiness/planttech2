package net.kaneka.planttech2.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.crops.CropEntry;
import net.kaneka.planttech2.crops.DropEntry;
import net.kaneka.planttech2.crops.ParentPair;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.gui.buttons.CustomButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class GuidePlantsScreen extends GuideBaseScreen
{
	private final String[] buttonEntryNames = new String[8];
	private Component selectedName = new TextComponent("");
	protected ItemStack primarySeed = ItemStack.EMPTY;
	protected ItemStack soil = ItemStack.EMPTY;
	protected ItemStack[] seeds = new ItemStack[9];
	protected DropEntry[] drops = new DropEntry[9];
	protected ItemStack[][] parents = new ItemStack[4][2];
	protected EnumTemperature temp = EnumTemperature.NORMAL;

	public GuidePlantsScreen()
	{
		super(PlantTechMain.getCropList().getLengthEnabledOnly() - 8, true, "screen.guideplants");
	}

	@Override
	public void init()
	{
		super.init();
		final int xPos = this.guiLeft + 31, baseYPos = this.guiTop + 10, width = 100, height = 20;
		for (int id = 0; id < 8; id++)
		{
			addRenderableWidget(new CustomButton(id, xPos, baseYPos + (id * 22), width, height, "Button " + (id + 1), GuidePlantsScreen.this::buttonClicked));
		}
		if(buttonEntryNames != null) updateButtons();
	}

	@Override
	protected void drawForeground(PoseStack pStack)
	{
		if (hasSelection)
		{
			blit(pStack, this.guiLeft + 307, this.guiTop + 65, 0, 196 + 16 * temp.ordinal(), 16, 16, 512, 512);
			renderItem(this.primarySeed, 261, 32);

//			RenderHelper.disableStandardItemLighting();
//			RenderSystem.enableDepthTest();
			if (!soil.isEmpty())
				this.renderItem(this.soil, 217, 65);
//			RenderSystem.enableDepthTest();
			for (int i = 0; i < 9; i++)
			{
				if (!seeds[i].isEmpty())
				{
					this.renderItem(seeds[i], 189 + 18 * i, 98);
				}

				if (drops[i] != DropEntry.EMPTY)
				{
					this.renderItem(drops[i].getItemStack(), 189 + 18 * i, 131);
				}
			}
			for (int i = 0; i < 4; i++)
			{
				if (!parents[i][0].isEmpty())
				{
					this.renderItem(parents[i][0], 162 + 56 * i, 164);
					this.renderItem(parents[i][1], 192 + 56 * i, 164);
				}
			}
		}

	}

	@Override
	protected void updateButtons()
	{
		List<CropEntry> list = PlantTechMain.getCropList().values(false);
		for (int i = 0; i < 8; i++)
		{
			if (scrollPos + i < list.size())
			{
				CropEntry entry = list.get(scrollPos + i);
				if(this.renderables.get(i) instanceof AbstractWidget aWidget) aWidget.setMessage(entry.getDisplayName());
				buttonEntryNames[i] = entry.getName();
			}
		}
	}

	@Override
	protected void drawStrings(PoseStack mStack)
	{
		if (!hasSelection)
		{
			drawCenteredString(mStack,  new TranslatableComponent("gui.non_selected").getString(), this.guiLeft + 255, this.guiTop + 90);
		} else
		{
			drawCenteredString(mStack, selectedName.getString(), this.guiLeft + 271, this.guiTop + 15);
			drawCenteredString(mStack, new TranslatableComponent("gui.soil").getString(), this.guiLeft + 225, this.guiTop + 54);
			drawCenteredString(mStack, new TranslatableComponent("gui.temperature").getString(), this.guiLeft + 314, this.guiTop + 54);
			drawCenteredString(mStack, new TranslatableComponent("gui.seeds").getString(), this.guiLeft + 271, this.guiTop + 87);
			drawCenteredString(mStack, new TranslatableComponent("gui.drops").getString(), this.guiLeft + 271, this.guiTop + 120);
			drawCenteredString(mStack, new TranslatableComponent("gui.parents").getString(), this.guiLeft + 271, this.guiTop + 153);
		}
	}

	protected void drawCenteredString(PoseStack mStack, String string, int posX, int posY)
	{
		font.draw(mStack, string, posX - (font.width(string) / 2), posY, Integer.parseInt("000000", 16));
	}

	protected void buttonClicked(CustomButton button)
	{
		int buttonID = button.id;
		if (buttonID >= 0 && buttonID < 8)
		{
			this.setItems(buttonEntryNames[buttonID]);
		}
	}

	@SuppressWarnings("deprecation")
	protected void setItems(String entryName)
	{
		CropEntry entry = PlantTechMain.getCropList().getByName(entryName);
		hasSelection = entry != null;
		if (!hasSelection)
		{
			// TODO: empty the lists?
			return;
		}

		this.selectedName = entry.getDisplayName();
		this.primarySeed = entry.getPrimarySeed().getItemStack();
		this.temp = entry.getConfiguration().getTemperature();
		this.soil = new ItemStack(entry.getConfiguration().getSoil().get());

		Arrays.fill(this.seeds, ItemStack.EMPTY);
		Arrays.fill(this.drops, DropEntry.EMPTY);
		for (int k = 0; k < 4; k++)
		{
			Arrays.fill(this.parents[k], ItemStack.EMPTY);
		}

		List<Supplier<Item>> seeds = entry.getSeeds();
		List<DropEntry> drops = entry.getAdditionalDrops();
		List<ParentPair> parents = entry.getParents();

		this.seeds[0] = entry.getPrimarySeed().getItemStack();
		for (int j = 0; j < 8 && j < seeds.size(); j++)
		{
			this.seeds[j + 1] = new ItemStack(seeds.get(j).get());
		}

		this.drops[0] = entry.getPrimarySeed();
		for (int j = 0; j < 8 && j < drops.size(); j++)
		{
			this.drops[j + 1] = drops.get(j);
		}

		for (int j = 0; j < 4 && j < parents.size(); j++)
		{
			ParentPair pair = parents.get(j);
			this.parents[j][0] = PlantTechMain.getCropList().getByName(pair.getFirstParent()).getPrimarySeed().getItemStack();
			this.parents[j][1] = PlantTechMain.getCropList().getByName(pair.getSecondParent()).getPrimarySeed().getItemStack();
		}
	}

	@Override
	protected void drawTooltips(PoseStack mStack, int mouseX, int mouseY)
	{
		if (hasSelection)
		{
			this.drawTooltip(mStack, primarySeed.getHoverName(), mouseX, mouseY, 261, 32);
			if (!soil.isEmpty())
			{
				this.drawTooltip(mStack, soil.getHoverName(), mouseX, mouseY, 217, 65);
			}
			this.drawTooltip(mStack, temp.getDisplayString(), mouseX, mouseY, 307, 65);

			for (int i = 0; i < 9; i++)
			{
				if (!seeds[i].isEmpty())
					this.drawTooltip(mStack, seeds[i].getHoverName(), mouseX, mouseY, 189 + 18 * i, 98);

				if (drops[i] != DropEntry.EMPTY)
					this.drawTooltip(mStack, new TextComponent("").append(drops[i].getMin() + "-" + drops[i].getMax() + "x ")
									.append(drops[i].getItem().get().asItem().getDescription()), mouseX, mouseY,
							189 + 18 * i, 131);
			}

			for (int i = 0; i < 4; i++)
			{
				if (!parents[i][0].isEmpty())
				{
					this.drawTooltip(mStack, parents[i][0].getHoverName(), mouseX, mouseY, 162 + 56 * i, 164);
					this.drawTooltip(mStack, parents[i][1].getHoverName(), mouseX, mouseY, 192 + 56 * i, 164);
				}
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)
	{
		if (hasSelection && mouseButton == 0)
		{
			Item clickedOn = null;
			if (this.inItemStackArea(mouseX, mouseY, 261, 32))
				clickedOn = this.primarySeed.getItem();
			if (this.inItemStackArea(mouseX, mouseY, 217, 65))
				if (soil != null)
					if (!soil.isEmpty())
						clickedOn = this.soil.getItem();

			for (int i = 0; i < 9; i++)
			{
				if (seeds[i] != null)
				{
					if (this.inItemStackArea(mouseX, mouseY, 189 + 18 * i, 98))
						clickedOn = this.seeds[i].getItem();
				}

				if (drops[i] != null)
				{
					if (this.inItemStackArea(mouseX, mouseY, 189 + 18 * i, 131))
						clickedOn = this.drops[i].getItem().get().asItem();
				}
			}

			for (int i = 0; i < 4; i++)
			{
				if (parents[i][0] != null)
				{
					if (this.inItemStackArea(mouseX, mouseY, 162 + 56 * i, 164))
						clickedOn = this.parents[i][0].getItem();
					if (this.inItemStackArea(mouseX, mouseY, 192 + 56 * i, 164))
						clickedOn = this.parents[i][1].getItem();
				}
			}

			if (clickedOn != null)
			{
				CropEntry entry = PlantTechMain.getCropList().getBySeed(clickedOn);
				if (entry != null)
				{
					this.setItems(entry.getName());
				}
			}
		}
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	private boolean inArea(double mouseX, double mouseY, int posX, int posY, int width, int height)
	{
		posX += this.guiLeft;
		posY += this.guiTop;
		return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
	}

	private boolean inItemStackArea(double mouseX, double mouseY, int posX, int posY)
	{
		return this.inArea(mouseX, mouseY, posX, posY, 16, 16);
	}

	@Override
	protected void drawBackground(PoseStack mStack)
	{
		if (!hasSelection)
		{
			blit(mStack, this.guiLeft + 100, this.guiTop, 212, 0, 300, this.ySize, 512, 512);
		} else
		{
			blit(mStack, this.guiLeft + 100, this.guiTop, 212, 196, 300, this.ySize, 512, 512);
		}
		blit(mStack, this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
	}
}
