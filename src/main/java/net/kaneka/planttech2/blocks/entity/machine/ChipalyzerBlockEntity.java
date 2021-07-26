package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryBlockEntity;
import net.kaneka.planttech2.inventory.ChipalyzerMenu;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChipalyzerBlockEntity extends ConvertEnergyInventoryBlockEntity
{
	protected List<ChipalyzerRecipe> recipes = null;
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return ChipalyzerBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return ChipalyzerBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return ChipalyzerBlockEntity.this.ticksPassed;
			default:
				return 0;
			}
		}
		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				ChipalyzerBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				ChipalyzerBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				ChipalyzerBlockEntity.this.ticksPassed = value;
				break;
			}
		}
		public int getCount()
		{
			return 3;
		}
	};

	public ChipalyzerBlockEntity()
	{
		this(BlockPos.ZERO, ModBlocks.CHIPALYZER.defaultBlockState());
	}

	public ChipalyzerBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.CHIPALYZER_TE, pos, state, 1000, 7, PlantTechConstants.MACHINETIER_CHIPALYZER);
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
			for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CHIPALYZER))
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
	public ContainerData getIntArray()
	{
		return field_array;
	}

	@Override
	public String getNameString()
	{
		return "chipalyzer";
	}


	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new ChipalyzerMenu(id, inv, this);
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
