package net.kaneka.planttech2.gui;

import java.util.List;
import java.util.Set;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.gui.buttons.CustomButton;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.librarys.utils.Drop;
import net.kaneka.planttech2.librarys.utils.Parents;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

public class GuidePlantsScreen extends GuideBaseScreen
{
	private int[] buttonIDs = new int[8];
	private String selectedName;
	protected ItemStack mainseed;
	protected ItemStack soil;
	protected ItemStack[] seeds = new ItemStack[9];
	protected Drop[] drops = new Drop[9];
	protected ItemStack[][] parents = new ItemStack[4][2];
	private EnumTemperature temp = EnumTemperature.NORMAL;

	public GuidePlantsScreen()
	{
		super(PlantTechMain.croplist.getLengthWithoutBlacklisted() - 8, true, "screen.guideplants");
	}

	@Override
	public void init()
	{
		super.init();
		
		addButton(new CustomButton(1, this.guiLeft + 28, this.guiTop + 10 + 0 * 22, 100, 20, "TEST", (button) -> 
		{
			GuidePlantsScreen.this.buttonClicked(0);
		})); 
		addButton(new CustomButton(2, this.guiLeft + 28, this.guiTop + 10 + 1 * 22, 100, 20, "TEST", (button) -> 
		{
			GuidePlantsScreen.this.buttonClicked(1);
		})); 
		addButton(new CustomButton(3, this.guiLeft + 28, this.guiTop + 10 + 2 * 22, 100, 20, "TEST", (button) -> 
		{
			GuidePlantsScreen.this.buttonClicked(2);
		})); 
		addButton(new CustomButton(4, this.guiLeft + 28, this.guiTop + 10 + 3 * 22, 100, 20, "TEST", (button) -> 
		{
			GuidePlantsScreen.this.buttonClicked(3);
		})); 
		addButton(new CustomButton(5, this.guiLeft + 28, this.guiTop + 10 + 4 * 22, 100, 20, "TEST", (button) -> 
		{
			GuidePlantsScreen.this.buttonClicked(4);
		})); 
		addButton(new CustomButton(6, this.guiLeft + 28, this.guiTop + 10 + 5 * 22, 100, 20, "TEST", (button) -> 
		{
			GuidePlantsScreen.this.buttonClicked(5);
		})); 
		addButton(new CustomButton(7, this.guiLeft + 28, this.guiTop + 10 + 6 * 22, 100, 20, "TEST", (button) -> 
		{
			GuidePlantsScreen.this.buttonClicked(6);
		})); 
		addButton(new CustomButton(8, this.guiLeft + 28, this.guiTop + 10 + 7 * 22, 100, 20, "TEST", (button) -> 
		{
			GuidePlantsScreen.this.buttonClicked(7);
		})); 

		updateButtons();
	}

	@Override
	protected void drawForeground(MatrixStack mStack)
	{
		if (selectedId != -1)
		{
			blit(mStack, this.guiLeft + 307, this.guiTop + 65, 0, 196 + 16 * temp.getId(), 16, 16, 512, 512);
			renderItem(this.mainseed, 261, 32);
			RenderHelper.disableStandardItemLighting();
			RenderSystem.enableDepthTest();
			if (soil != null)
				if (!soil.isEmpty())
					this.renderItem(this.soil, 217, 65);
			RenderSystem.enableDepthTest();
			for (int i = 0; i < 9; i++)
			{
				if (seeds[i] != null)
				{
					this.renderItem(seeds[i], 189 + 18 * i, 98);
				}

				if (drops[i] != null)
				{
					this.renderItem(drops[i].getItemStack(), 189 + 18 * i, 131);
				}
			}
			for (int i = 0; i < 4; i++)
			{
				if (parents[i][0] != null)
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
		List<String> list = PlantTechMain.croplist.getAllEntriesWithoutBlacklisted();
		for (int i = 0; i < 8; i++)
		{
			if (scrollPos + i < list.size())
			{
				CropListEntry entry = PlantTechMain.croplist.getEntryByName(list.get(scrollPos + i));
				this.buttons.get(i).setMessage(new StringTextComponent(entry.getDisplayNameUnformated()));
				buttonIDs[i] = entry.getID();
			}
		}
	}

	@Override
	protected void drawStrings(MatrixStack mStack)
	{
		if (selectedId == -1)
		{
			this.drawCenteredString(mStack, translateUnformated("gui.non_selected"), this.guiLeft + 255, this.guiTop + 90);
		} else
		{
			this.drawCenteredString(mStack, selectedName, this.guiLeft + 263, this.guiTop + 15);
			this.drawCenteredString(mStack, translateUnformated("gui.soil"), this.guiLeft + 223, this.guiTop + 54);
			this.drawCenteredString(mStack, translateUnformated("gui.temperature"), this.guiLeft + 306, this.guiTop + 54);
			this.drawCenteredString(mStack, translateUnformated("gui.seeds"), this.guiLeft + 263, this.guiTop + 87);
			this.drawCenteredString(mStack, translateUnformated("gui.drops"), this.guiLeft + 263, this.guiTop + 120);
			this.drawCenteredString(mStack, translateUnformated("gui.parents"), this.guiLeft + 263, this.guiTop + 153);
		}
	}

	protected void buttonClicked(int button)
	{
		if (button >= 0 && button < 8)
		{
			this.setItems(buttonIDs[button]);
		}

	}

	@SuppressWarnings("deprecation")
	protected void setItems(int id)
	{
		selectedId = id;
		CropListEntry entry = PlantTechMain.croplist.getByID(id);
		selectedName = entry.getDisplayNameUnformated();
		if (entry.getMainSeed() != null)
		{
			mainseed = entry.getMainSeed();
		}

		this.soil = entry.getSoil();
		if (soil.isEmpty())
		{
			this.soil = new ItemStack(Item.getItemFromBlock(Blocks.DIRT));
		}

		List<ItemStack> seeds = entry.getSeeds();
		Set<Drop> drops = entry.getDrops();
		Set<Parents> parentslist = entry.getParents();

		int i = 0;
		if (!seeds.isEmpty())
		{
			for (ItemStack item : seeds)
			{
				if (i < 9)
				{
					this.seeds[i] = item;
				}
				i++;

			}
		}

		for (int k = i; k < 9; k++)
		{
			this.seeds[k] = null;
		}

		i = 1;
		this.drops[0] = entry.getMainSeedDrop();
		if (drops != null)
		{
			for (Drop drop : drops)
			{
				if (i < 9)
				{
					this.drops[i] = drop;
				}
				i++;

			}
		}

		for (int k = i; k < 9; k++)
		{
			this.drops[k] = null;
		}

		i = 0;
		if (parentslist != null)
		{
			for (Parents parents : parentslist)
			{
				if (i < 4)
				{
					this.parents[i][0] = PlantTechMain.croplist.getEntryByName(parents.getParent(0)).getMainSeed();
					this.parents[i][1] = PlantTechMain.croplist.getEntryByName(parents.getParent(1)).getMainSeed();
				}
				i++;
			}
		}

		for (int k = i; k < 4; k++)
		{
			this.parents[k][0] = null;
			this.parents[k][1] = null;
		}

		temp = entry.getTemperature();

	}

	@Override
	protected void drawTooltips(MatrixStack mStack, int mouseX, int mouseY)
	{
		if (selectedId != -1)
		{
			this.drawTooltip(mStack, mainseed.getDisplayName(), mouseX, mouseY, 261, 32);
			if (soil != null)
				if (!soil.isEmpty())
					this.drawTooltip(mStack, soil.getDisplayName(), mouseX, mouseY, 217, 65);
			this.drawTooltip(mStack, temp.getDisplayString(true), mouseX, mouseY, 307, 65);

			for (int i = 0; i < 9; i++)
			{
				if (seeds[i] != null)
					this.drawTooltip(mStack, seeds[i].getDisplayName(), mouseX, mouseY, 189 + 18 * i, 98);

				if (drops[i] != null)
					this.drawTooltip(mStack, new StringTextComponent("").appendString(drops[i].getMin() + "-" + drops[i].getMax() + "x ").append(drops[i].getItemStack().getDisplayName()), mouseX, mouseY,
					        189 + 18 * i, 131);
			}

			for (int i = 0; i < 4; i++)
			{
				if (parents[i][0] != null)
				{
					this.drawTooltip(mStack, parents[i][0].getDisplayName(), mouseX, mouseY, 162 + 56 * i, 164);
					this.drawTooltip(mStack, parents[i][1].getDisplayName(), mouseX, mouseY, 192 + 56 * i, 164);
				}
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)
	{
		if (selectedId != -1)
		{
			if (mouseButton == 0)
			{
				ItemStack clickedOn = null;
				if (this.inItemStackArea(mouseX, mouseY, 261, 32))
					clickedOn = this.mainseed;
				if (this.inItemStackArea(mouseX, mouseY, 217, 65))
					if (soil != null)
						if (!soil.isEmpty())
							clickedOn = this.soil;

				for (int i = 0; i < 9; i++)
				{
					if (seeds[i] != null)
					{
						if (this.inItemStackArea(mouseX, mouseY, 189 + 18 * i, 98))
							clickedOn = this.seeds[i];
					}

					if (drops[i] != null)
					{
						if (this.inItemStackArea(mouseX, mouseY, 189 + 18 * i, 131))
							clickedOn = this.drops[i].getItemStack();
					}
				}

				for (int i = 0; i < 4; i++)
				{
					if (parents[i][0] != null)
					{
						if (this.inItemStackArea(mouseX, mouseY, 162 + 56 * i, 164))
							clickedOn = this.parents[i][0];
						if (this.inItemStackArea(mouseX, mouseY, 192 + 56 * i, 164))
							clickedOn = this.parents[i][1];
					}
				}

				if (clickedOn != null)
				{
					CropListEntry entry = PlantTechMain.croplist.getBySeed(clickedOn);
					if (entry != null)
					{
						this.setItems(entry.getID());
					}
				}
			}
		}
		return super.mouseClicked(mouseX, mouseY, mouseButton);
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
	protected void drawBackground(MatrixStack mStack)
	{
		if (selectedId == -1)
		{
			blit(mStack, this.guiLeft + 100, this.guiTop, 212, 0, 300, this.ySize, 512, 512);
		} else
		{
			blit(mStack, this.guiLeft + 100, this.guiTop, 212, 196, 300, this.ySize, 512, 512);
		}
		blit(mStack, this.guiLeft, this.guiTop, 0, 0, 150, this.ySize, 512, 512);
	}
}
