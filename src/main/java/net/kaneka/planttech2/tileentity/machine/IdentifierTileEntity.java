package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.IdentifierContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class IdentifierTileEntity extends EnergyInventoryTileEntity
{
	public int ticksPassed = 0;
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return IdentifierTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return IdentifierTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return IdentifierTileEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				IdentifierTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				IdentifierTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				IdentifierTileEntity.this.ticksPassed = value;
				break;
			}

		}

		public int size()
		{
			return 3;
		}
	};

	public IdentifierTileEntity()
	{
		super(ModTileEntities.IDENTIFIER_TE, 10000, 19);
	}

	@Override
	public void doUpdate()
	{
		if (this.energystorage.getEnergyStored() > this.getEnergyPerTickPerItem() || true)
		{
			if (this.canIdentify())
			{
				// this.energystorage.extractEnergy(getEnergyPerTickPerItem(), false);
				ticksPassed++;
				if (ticksPassed >= this.getTicksPerItem())
				{
					this.identifyItem();
					ticksPassed = 0;
				}
			} else if (ticksPassed > 0)
			{
				ticksPassed = 0;
			}
		} else
		{
			if (!this.canIdentify() && ticksPassed > 0)
			{
				ticksPassed = 0;
			}
		}

	}

	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	private boolean canIdentify()
	{
		if (!this.hasFreeOutputSlot())
		{
			return false;
		}

		for (int i = 0; i < 9; i++)
		{
			ItemStack stack = this.itemhandler.getStackInSlot(i);
			if (!stack.isEmpty())
			{
				CompoundNBT nbt = stack.getTag();
				if (nbt != null)
				{
					if (nbt.contains("analysed"))
					{
						if (!nbt.getBoolean("analysed"))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void identifyItem()
	{
		if (this.canIdentify())
		{
			for (int i = 0; i < 9; i++)
			{
				ItemStack stack = this.itemhandler.getStackInSlot(i);
				if (!stack.isEmpty())
				{
					CompoundNBT nbt = stack.getTag();

					if (nbt != null)
					{
						if (nbt.contains("analysed"))
						{
							if (!nbt.getBoolean("analysed"))
							{
								nbt.putBoolean("analysed", true);
								stack.setTag(nbt);
								this.itemhandler.setStackInSlot(this.getFreeOutputSlot(), stack);
								this.itemhandler.setStackInSlot(i, ItemStack.EMPTY);
								break;
							}
						}
					}
				}
			}
		}
	}

	public boolean hasFreeOutputSlot()
	{
		for (int i = 9; i < 18; i++)
		{
			ItemStack stack = this.itemhandler.getStackInSlot(i);
			if (stack.isEmpty())
			{
				return true;
			}
		}
		return false;
	}

	public int getFreeOutputSlot()
	{
		for (int i = 9; i < 18; i++)
		{
			ItemStack stack = this.itemhandler.getStackInSlot(i);
			if (stack.isEmpty())
			{
				return i;
			}
		}
		return 9;
	}

	public int getEnergyPerTickPerItem()
	{
		return 4 + (getUpgradeTier(18, PlantTechConstants.SPEEDUPGRADE_TYPE) * 4);
	}

	public int getTicksPerItem()
	{
		return 200 - (getUpgradeTier(18, PlantTechConstants.SPEEDUPGRADE_TYPE) * 35);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("cooktime", ticksPassed);
		super.write(compound);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound)
	{
		this.ticksPassed = compound.getInt("cooktime");
		super.read(compound);
	}

	@Override
	public String getNameString()
	{
		return "identifier";
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new IdentifierContainer(id, inv, this);
	}
}
