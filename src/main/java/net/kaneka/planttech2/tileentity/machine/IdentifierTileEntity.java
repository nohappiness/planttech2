package net.kaneka.planttech2.tileentity.machine;

import javax.annotation.Nullable;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.IdentifierContainer;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public class IdentifierTileEntity extends EnergyInventoryTileEntity
{
	public int ticksPassed = 0;
	private RangedWrapper inputs; 
	private RangedWrapper outputs; 
	private LazyOptional<IItemHandler> inputs_provider;
	private LazyOptional<IItemHandler> outputs_provider;
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
		super(ModTileEntities.IDENTIFIER_TE, 10000, 22, PlantTechConstants.MACHINETIER_IDENTIFIER);
		inputs = new RangedWrapper(itemhandler, 0,9); 
		outputs = new RangedWrapper(itemhandler, 9, 18); 
		inputs_provider = LazyOptional.of(() -> inputs);
		outputs_provider = LazyOptional.of(() -> outputs);
	}
	
	@Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (facing == Direction.DOWN) return outputs_provider.cast();
            if (facing != null) return inputs_provider.cast();
            return inventoryCap.cast();
        }

        return super.getCapability(capability, facing);
    }

	@Override
	public void doUpdate()
	{
		if (this.energystorage.getEnergyStored() > this.getEnergyPerTickPerItem())
		{
			if (this.canIdentify())
			{
				this.energystorage.extractEnergy(getEnergyPerTickPerItem(), false);
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
		doEnergyLoop();
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
				else
				{
					CropListEntry entry = PlantTechMain.croplist.getBySeed(stack);
			    	if (entry != null)
			    	{
			    		return true; 
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
								addKnowledge();
								break;
							}
						}
					}
					else
					{
						CropListEntry entry = PlantTechMain.croplist.getBySeed(stack);
				    	if (entry != null)
				    	{
				    		HashMapCropTraits newtraits = new HashMapCropTraits();
				    		newtraits.setType(entry.getString());
				    		newtraits.setAnalysed(true);
				    		ItemStack result = entry.getMainSeed().copy();
				    		result.setCount(stack.getCount());
				    		newtraits.addToItemStack(stack);
				    		this.itemhandler.setStackInSlot(this.getFreeOutputSlot(), result);
							this.itemhandler.setStackInSlot(i, ItemStack.EMPTY);
							addKnowledge();
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
		return 4 + (getUpgradeTier(18, SPEED_UPGRADE) * 4);
	}

	public int getTicksPerItem()
	{
		return 200 - (getUpgradeTier(18, SPEED_UPGRADE) * 35);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("cooktime", ticksPassed);
		super.write(compound);
		return compound;
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		this.ticksPassed = compound.getInt("cooktime");
		super.read(state, compound);
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

	@Override
	public int getEnergyInSlot()
	{
		return 19;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 20;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 21;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 5;
	}
}
