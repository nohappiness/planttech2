package net.kaneka.planttech2.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kaneka.planttech2.container.ChipalyzerContainer;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.ConvertEnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IIntArray;

public class ChipalyzerTileEntity extends ConvertEnergyInventoryTileEntity
{
	protected List<ChipalyzerRecipe> recipes = null;
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return ChipalyzerTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return ChipalyzerTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return ChipalyzerTileEntity.this.ticksPassed;
			default:
				return 0;
			}
		}
		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				ChipalyzerTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				ChipalyzerTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				ChipalyzerTileEntity.this.ticksPassed = value;
				break;
			}
		}
		public int getCount()
		{
			return 3;
		}
	};

	public ChipalyzerTileEntity()
	{
		super(ModTileEntities.CHIPALYZER_TE, 1000, 7, PlantTechConstants.MACHINETIER_CHIPALYZER);
	}

	@Override
	protected boolean onProcessFinished(ItemStack input, ItemStack output)
	{
		boolean finished = super.onProcessFinished(input, output);
		if (finished)
			getChip().shrink(1);
		return finished;
	}

	@Override
	protected boolean canProceed(ItemStack input, ItemStack output)
	{
		return !getChip().isEmpty() && !getRecipeList(getChip(), input, false).isEmpty();
	}

	@Override
	protected ItemStack getResult(ItemStack input, ItemStack output)
	{
		List<ChipalyzerRecipe> recipe = getRecipeList(getChip(), input, false);
		return recipe.get(rand.nextInt(recipe.size())).getResultItem().copy();
	}

	private ItemStack getChip()
	{
		return itemhandler.getStackInSlot(0);
	}

	private List<ChipalyzerRecipe> getRecipeList(ItemStack chip, ItemStack stack, boolean forceUpdate)
	{
		if (stack.isEmpty() || level == null)
			return Collections.emptyList();
		if (recipes == null || forceUpdate)
		{
			List<ChipalyzerRecipe> list = new ArrayList<>();
			for (IRecipe<?> recipe : level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CHIPALYZER))
			{
				ChipalyzerRecipe chipRecipe = (ChipalyzerRecipe) recipe;
				ItemStack fake = chip.copy();
				fake.setCount(1);
				if (ItemStack.matches(chipRecipe.getChip(), fake))
					if (chipRecipe.compare(chip, stack))
						list.add(chipRecipe);
			}
			recipes = list;
			return list;
		}
		return recipes;
	}

	@Override
	public void onContainerUpdated(int slotIndex)
	{
		getRecipeList(getChip(), getInput(), true);
	}

	@Override
	public int getInputSlotIndex()
	{
		return 1;
	}

	@Override
	public int getOutputSlotIndex()
	{
		return 2;
	}

	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	@Override
	public String getNameString()
	{
		return "chipalyzer";
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new ChipalyzerContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 4;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 5;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 6;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 100;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 3;
	}
}
