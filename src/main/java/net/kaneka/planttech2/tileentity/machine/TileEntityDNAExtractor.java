package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.ContainerDNAExtractor;
import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.items.ModItems;
import net.kaneka.planttech2.tileentity.ModTileEntities;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDNAExtractor extends TileEntityEnergyInventory
{
    private int ticksPassed = 0;

    public TileEntityDNAExtractor()
    {
	super(ModTileEntities.DNAEXTRACTOR_TE, 1000, 4);
    }

    @Override
    public void doUpdate()
    {
	if (this.energystorage.getEnergyStored() > energyPerTick() || true)
	{
	    ItemStack stack1 = itemhandler.getStackInSlot(0);
	    ItemStack stack2 = itemhandler.getStackInSlot(1);
	    ItemStack stack3 = itemhandler.getStackInSlot(2);
	    if (!stack1.isEmpty() && !stack2.isEmpty())
	    {
		if (stack1.getItem() instanceof ItemCropSeed && stack2.getItem() == ModItems.DNA_CONTAINER_EMPTY)
		{
		    if (stack1.hasTag())
		    {
			if (ticksPassed < ticksPerItem())
			{
			    ticksPassed++;
			    //energystorage.extractEnergy(energyPerTick(), false);
			}
			else
			{
			    if (stack3.isEmpty())
			    {
				ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
				NBTTagCompound nbt = stack1.getTag().copy();
				nbt.removeTag("analysed");
				stack.setTag(nbt);
				itemhandler.setStackInSlot(2, stack);
				endProcess();
			    }
			    else if (stack3.hasTag() && stack3.getItem() == ModItems.DNA_CONTAINER)
			    {
				if (stack3.getTag().equals(stack1.getTag()))
				{
				    stack3.grow(1);
				    endProcess();
				}
			    }
			}
		    }
		}
	    }
	}
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
	return 4 + (getUpgradeTier(3, Constants.SPEEDUPGRADE_TYPE) * 4);
    }

    public int ticksPerItem()
    {
	return 200 - (getUpgradeTier(3, Constants.SPEEDUPGRADE_TYPE) * 35);
    }

    @Override
    public String getNameString()
    {
	return "dna_extractor";
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound)
    {
	compound.setInt("tickspassed", ticksPassed);
	super.write(compound);
	return compound;
    }

    @Override
    public void read(NBTTagCompound compound)
    {
	this.ticksPassed = compound.getInt("tickspassed");
	super.read(compound);
    }

    @Override
    public int getField(int id)
    {
	switch (id)
	{
	case 0:
	case 1:
	    return super.getField(id);
	case 2:
	    return this.ticksPassed;
	default:
	    return 0;
	}
    }

    @Override
    public void setField(int id, int value)
    {
	switch (id)
	{
	case 0:
	case 1:
	    super.setField(id, value);
	    break;
	case 2:
	    this.ticksPassed = value;
	    break;
	}
    }

    @Override
    public int getAmountFields()
    {
	return 3;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
	return new ContainerDNAExtractor(playerInventory, this);
    }

}
