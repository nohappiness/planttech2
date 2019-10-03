package net.kaneka.planttech2.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.kaneka.planttech2.container.ChipalyzerContainer;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class ChipalyzerTileEntity extends EnergyInventoryTileEntity
{
	private int ticksPassed = 0;

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
				;
				break;
			}

		}

		public int size()
		{
			return 3;
		}
	};

	public ChipalyzerTileEntity()
	{
		super(ModTileEntities.CHIPALYZER_TE, 1000, 6);
	}

	@Override
	public void doUpdate()
	{
		if (this.energystorage.getEnergyStored() > energyPerTick())
		{
			ItemStack stackChip = itemhandler.getStackInSlot(0);
			ItemStack stackInput = itemhandler.getStackInSlot(1);
			ItemStack stackOutput = itemhandler.getStackInSlot(2);
			if (!stackChip.isEmpty() && !stackInput.isEmpty())
			{

				List<ChipalyzerRecipe> recipe = getRecipeList(stackChip, stackInput);
				if (!recipe.isEmpty())
				{

					if (ticksPassed < ticksPerItem())
					{
						ticksPassed++;
						energystorage.extractEnergy(energyPerTick(), false);
					} 
					else
					{
						
						if (stackOutput.isEmpty())
						{
							ItemStack result = recipe.get(new Random().nextInt(recipe.size())).getRecipeOutput().copy();
							itemhandler.setStackInSlot(2, result);
							energystorage.extractEnergy(energyPerTick(), false);
							stackChip.shrink(1);
							stackInput.shrink(1);
							ticksPassed = 0;
						} 
					}
				}
			}

		}
		doEnergyLoop();
	}

	private List<ChipalyzerRecipe> getRecipeList(ItemStack chip, ItemStack stack)
	{
		List<ChipalyzerRecipe> list = new ArrayList<ChipalyzerRecipe>();
		if (!stack.isEmpty())
		{
			if (world != null)
			{
				for (IRecipe<?> recipe : world.getRecipeManager().getRecipes())
				{
					if (recipe.getType() == ModRecipeTypes.CHIPALYZER)
					{
						ChipalyzerRecipe chipRecipe = (ChipalyzerRecipe) recipe;
						if (chipRecipe.getTier() == getUpgradeTier(0, PlantTechConstants.UPGRADECHIP_TYPE))
						{
							if (chipRecipe.compare(chip, stack))
							{
								list.add(chipRecipe);
							}
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	public int energyPerTick()
	{
		return 4 + (getUpgradeTier(3, PlantTechConstants.SPEEDUPGRADE_TYPE) * 4);
	}

	public int ticksPerItem()
	{
		return 200 - (getUpgradeTier(3, PlantTechConstants.SPEEDUPGRADE_TYPE) * 35);
	}

	@Override
	public String getNameString()
	{
		return "chipalyzer";
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("tickspassed", ticksPassed);
		super.write(compound);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound)
	{
		this.ticksPassed = compound.getInt("tickspassed");
		super.read(compound);
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
}