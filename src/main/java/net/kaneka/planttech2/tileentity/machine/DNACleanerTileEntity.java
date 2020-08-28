package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.DNACleanerContainer;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public class DNACleanerTileEntity extends EnergyInventoryTileEntity
{
    private int ticksPassed = 0;
    protected final IIntArray field_array = new IIntArray() {
        public int get(int index) {
           switch(index) {
           case 0:
              return DNACleanerTileEntity.this.energystorage.getEnergyStored();
           case 1:
              return DNACleanerTileEntity.this.energystorage.getMaxEnergyStored();
           case 2:
              return DNACleanerTileEntity.this.ticksPassed;
           default:
              return 0;
           }
        }

        public void set(int index, int value) {
           switch(index) {
           case 0:
        	   DNACleanerTileEntity.this.energystorage.setEnergyStored(value);
              break;
           case 1:
        	   DNACleanerTileEntity.this.energystorage.setEnergyMaxStored(value);
              break;
           case 2:
        	   DNACleanerTileEntity.this.ticksPassed = value;;
              break;
           }

        }

        public int size() {
           return 3;
        }
     };

    public DNACleanerTileEntity()
    {
	super(ModTileEntities.DNACLEANER_TE,1000, 6, PlantTechConstants.MACHINETIER_DNA_CLEANER);
    }

    @Override
    public void doUpdate()
    {
	if (this.energystorage.getEnergyStored() > energyPerTick())
	{
	    ItemStack stack1 = itemhandler.getStackInSlot(0);
	    ItemStack stack2 = itemhandler.getStackInSlot(1);
	    if (!stack1.isEmpty())
	    {
		if (stack1.getItem() == ModItems.DNA_CONTAINER)
		{
		    if (ticksPassed < ticksPerItem())
		    {
			ticksPassed++;
			energystorage.extractEnergy(energyPerTick(), false);
		    }
		    else
		    {
			if (stack2.isEmpty())
			{
			    itemhandler.setStackInSlot(1, new ItemStack(ModItems.DNA_CONTAINER_EMPTY));
			    energystorage.extractEnergy(energyPerTick(), false);
			    stack1.shrink(1);
			    ticksPassed = 0;
			    addKnowledge();
			}
			else if (stack2.getItem() == ModItems.DNA_CONTAINER_EMPTY)
			{
			    stack2.grow(1);
			    energystorage.extractEnergy(energyPerTick(), false);
			    stack1.shrink(1);
			    ticksPassed = 0;
			    addKnowledge();
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

    public int energyPerTick()
    {
	return 4 + (getUpgradeTier(2, SPEED_UPGRADE) * 4);
    }

    public int ticksPerItem()
    {
	return 200 - (getUpgradeTier(2, SPEED_UPGRADE) * 35);
    }

    @Override
    public String getNameString()
    {
	return "dnacleaner";
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
	compound.putInt("tickspassed", ticksPassed);
	super.write(compound);
	return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound)
    {
	this.ticksPassed = compound.getInt("tickspassed");
	super.read(state, compound);
    }
    
    @Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new DNACleanerContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 3;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 4;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 5;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 50;
	}
}
