package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.DNAExtractorContainer;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class DNAExtractorTileEntity extends EnergyInventoryTileEntity
{
    private int ticksPassed = 0;
    protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return DNAExtractorTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return DNAExtractorTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return DNAExtractorTileEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				DNAExtractorTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				DNAExtractorTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				DNAExtractorTileEntity.this.ticksPassed = value;
				;
				break;
			}

		}

		public int size()
		{
			return 3;
		}
	};

    public DNAExtractorTileEntity()
    {
	super(ModTileEntities.DNAEXTRACTOR_TE, 1000, 7, PlantTechConstants.MACHINETIER_DNA_EXTRACTOR);
    }

    @Override
    public void doUpdate()
    {
	if (this.energystorage.getEnergyStored() > energyPerTick())
	{
	    ItemStack stack1 = itemhandler.getStackInSlot(0);
	    ItemStack stack2 = itemhandler.getStackInSlot(1);
	    ItemStack stack3 = itemhandler.getStackInSlot(2);
	    if (!stack1.isEmpty() && !stack2.isEmpty())
	    {
		if (stack1.getItem() instanceof CropSeedItem && stack2.getItem() == ModItems.DNA_CONTAINER_EMPTY)
		{
		    if (stack1.hasTag())
		    {
			if (ticksPassed < ticksPerItem())
			{
			    ticksPassed++;
			    energystorage.extractEnergy(energyPerTick(), false);
			}
			else
			{
			    if (stack3.isEmpty())
			    {
				ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
				CompoundNBT nbt = stack1.getTag().copy();
				nbt.remove("analysed");
				stack.setTag(nbt);
				itemhandler.setStackInSlot(2, stack);
				endProcess();
				addKnowledge();
			    }
			    else if (stack3.hasTag() && stack3.getItem() == ModItems.DNA_CONTAINER)
			    {
				if (stack3.getTag().equals(stack1.getTag()))
				{
				    stack3.grow(1);
				    addKnowledge();
				    endProcess();
				}
			    }
			}
		    }
		}
	    }
	}
	doEnergyLoop();
    }
    
    @Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

    private void endProcess()
    {
	ticksPassed = 0;
	//energystorage.extractEnergy(energyPerTick(), false);
	itemhandler.getStackInSlot(0).shrink(1);
	itemhandler.getStackInSlot(1).shrink(1);
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
	return "dnaextractor";
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
		return new DNAExtractorContainer(id, inv, this);
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
		return 50;
	}
}
