package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.SeedconstructorContainer;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class SeedconstructorTileEntity extends EnergyInventoryFluidTileEntity
{
	private int ticksPassed = 0;

	public SeedconstructorTileEntity()
	{
		super(ModTileEntities.SEEDCONSTRUCTOR_TE, 1000, 5, 5000);
	}

	@Override
	public void doUpdate()
	{
		if (energystorage.getEnergyStored() > energyPerTick() || true)
		{
			ItemStack stack1 = itemhandler.getStackInSlot(0);
			ItemStack stack2 = itemhandler.getStackInSlot(1);

			if (!stack1.isEmpty() && stack2.isEmpty())
			{
				if (stack1.getItem() == ModItems.DNA_CONTAINER && stack1.hasTag() && fluidtank.getBiomass() >= fluidPerItem())
				{
					if (ticksPassed < ticksPerItem())
					{
						ticksPassed++;
						// energystorage.extractEnergy(energyPerTick(), false);
					} else
					{
						ticksPassed = 0;
						// energystorage.extractEnergy(energyPerTick(), false);
						CompoundNBT nbt = stack1.getTag();
						HashMapCropTraits traits = new HashMapCropTraits();
						traits.setAnalysed(true);
						if (nbt.contains("type"))
						{
							traits.setType(nbt.getString("type"));
						}
						for (String key : HashMapCropTraits.getTraitsKeyList())
						{
							if (nbt.contains(key))
							{
								if (!key.equals("type"))
								{
									traits.setTrait(EnumTraitsInt.getByName(key), nbt.getInt(key));
								}
							}
						}
						ItemStack stack = new ItemStack(ModItems.SEEDS.get(traits.getType()));
						itemhandler.setStackInSlot(1, traits.addToItemStack(stack));
						fluidtank.extract(fluidPerItem());
					}
				}
			}
		}

		doFluidLoop();
	}

	public int fluidPerItem()
	{
		return 500;
	}

	public int energyPerTick()
	{
		return 4 + (getUpgradeTier(2, PlantTechConstants.SPEEDUPGRADE_TYPE) * 4);
	}

	public int ticksPerItem()
	{
		return 200 - (getUpgradeTier(2, PlantTechConstants.SPEEDUPGRADE_TYPE) * 35);
	}

	@Override
	public String getNameString()
	{
		return "seedconstructor";
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
	public int getField(int id)
	{
		if (id < 4)
		{
			return super.getField(id);
		} else
		{
			switch (id)
			{
			case 4:
				return this.ticksPassed;
			default:
				return 0;
			}
		}
	}

	@Override
	public void setField(int id, int value)
	{
		if (id < 4)
		{
			super.setField(id, value);
		} else
		{
			switch (id)
			{
			case 4:
				ticksPassed = value;
				break;
			}
		}
	}

	@Override
	public int getAmountFields()
	{
		return 5;
	}

	@Override
	protected int getFluidInSlot()
	{
		return 3;
	}

	@Override
	protected int getFluidOutSlot()
	{
		return 4;
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new SeedconstructorContainer(id, inv, this);
	}

}
