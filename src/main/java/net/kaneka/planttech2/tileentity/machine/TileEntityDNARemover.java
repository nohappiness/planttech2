package net.kaneka.planttech2.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventory;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class TileEntityDNARemover extends TileEntityEnergyInventory
{
    private int ticksPassed = 0;

    public TileEntityDNARemover()
    {
	super(ModTileEntities.DNAREMOVER_TE, 1000, 3);
    }

    @Override
    public void doUpdate()
    {
	if (this.energystorage.getEnergyStored() > energyPerTick() || true)
	{
	    ItemStack stack1 = itemhandler.getStackInSlot(0);
	    ItemStack stack2 = itemhandler.getStackInSlot(1);
	    if (!stack1.isEmpty() && stack2.isEmpty())
	    {
		if (stack1.getItem() == ModItems.DNA_CONTAINER)
		{
		    List<String> traitsList = getAvailableTraits(stack1);
		    if (traitsList.size() > 1)
		    {
			if (ticksPassed < ticksPerItem())
			{
			    ticksPassed++;
			    //energystorage.extractEnergy(energyPerTick(), false);
			}
			else
			{
			    Collections.shuffle(traitsList);
			    CompoundNBT nbt = stack1.getTag().copy();
			    nbt.remove(traitsList.get(0));
			    ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
			    stack.setTag(nbt);
			    itemhandler.setStackInSlot(1, stack);
			    stack1.shrink(1);
			    //energystorage.extractEnergy(energyPerTick(), false);
			    ticksPassed = 0;
			}
		    }
		}
	    }
	}
    }

    private List<String> getAvailableTraits(ItemStack stack)
    {
	List<String> list = new ArrayList<String>();
	if (stack.hasTag())
	{
	    CompoundNBT nbt = stack.getTag();
	    for (String key : HashMapCropTraits.getTraitsKeyList())
	    {
		if (nbt.contains(key))
		    list.add(key);
	    }
	}
	return list;
    }

    public int energyPerTick()
    {
	return 4 + (getUpgradeTier(2, Constants.SPEEDUPGRADE_TYPE) * 4);
    }

    public int ticksPerItem()
    {
	return 200 - (getUpgradeTier(2, Constants.SPEEDUPGRADE_TYPE) * 35);
    }

    @Override
    public String getNameString()
    {
	return "dnayremover";
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

}
