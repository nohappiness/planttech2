package net.kaneka.planttech2.tileentity.machine;

import java.util.Random;

import net.kaneka.planttech2.container.ContainerDNACombiner;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDNACombiner extends TileEntityEnergyInventory
{
    private int ticksPassed = 0; 

    public TileEntityDNACombiner()
    {
	super(ModTileEntities.DNACOMBINER_TE, 1000, 5);
    }
    
    @Override
    public void doUpdate()
    {
	if (this.energystorage.getEnergyStored() > energyPerTick() || true)
	{
	    ItemStack stack1 = itemhandler.getStackInSlot(0);
	    ItemStack stack2 = itemhandler.getStackInSlot(1);
	    ItemStack stack3 = itemhandler.getStackInSlot(2);
	    ItemStack stack4 = itemhandler.getStackInSlot(3);
	    if(!stack1.isEmpty() && !stack2.isEmpty() && !stack3.isEmpty() && stack4.isEmpty())
	    {
		if(stack1.getItem() == ModItems.DNA_CONTAINER && stack2.getItem() == ModItems.DNA_CONTAINER && stack1.hasTag() && stack2.hasTag() && stack3.getItem() == ModItems.DNA_CONTAINER_EMPTY)
		{
		    if(ticksPassed < ticksPerItem())
		    {
			ticksPassed++; 
			//energystorage.extractEnergy(energyPerTick(), false);
		    }
		    else
		    {
			ticksPassed = 0; 
			//energystorage.extractEnergy(energyPerTick(), false);
			NBTTagCompound nbt = getCombinedNBT(stack1.getTag(), stack2.getTag());
			ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER); 
			stack.setTag(nbt);
			itemhandler.setStackInSlot(3, stack);
			stack3.shrink(1);
		    }
		}
	    }
	}
    }
    
    private NBTTagCompound getCombinedNBT(NBTTagCompound nbt1, NBTTagCompound nbt2)
    {
	NBTTagCompound newNBT = new NBTTagCompound(); 
	Random rand = new Random(); 
	for(String key: HashMapCropTraits.getTraitsKeyList())
	{
	    if(key.equals("type"))
	    {
		if(nbt1.hasKey(key) && !nbt2.hasKey(key))
		{
		    newNBT.setString(key, nbt1.getString(key));
		}
		else if(!nbt1.hasKey(key) && nbt2.hasKey(key))
		{
		    newNBT.setString(key, nbt2.getString(key));
		}
		else if(nbt1.hasKey(key) && nbt2.hasKey(key))
		{
		    if(rand.nextBoolean())
		    {
			newNBT.setString(key, nbt1.getString(key));
		    }
		    else
		    {
			newNBT.setString(key, nbt2.getString(key));
		    }
		}
	    }
	    else
	    {
		if(nbt1.hasKey(key) && !nbt2.hasKey(key))
		{
		    newNBT.setInt(key, nbt1.getInt(key));
		}
		else if(!nbt1.hasKey(key) && nbt2.hasKey(key))
		{
		    newNBT.setInt(key, nbt2.getInt(key));
		}
		else if(nbt1.hasKey(key) && nbt2.hasKey(key))
		{
		    newNBT.setInt(key, Math.min(nbt1.getInt(key), nbt2.getInt(key)));
		}
	    }
	}
	return newNBT; 
    }
    
    public int energyPerTick()
    {
	return 4 + (getUpgradeTier(4, Constants.SPEEDUPGRADE_TYPE) * 4); 
    }
    
    public int ticksPerItem()
    {
	return 200 - (getUpgradeTier(4, Constants.SPEEDUPGRADE_TYPE) * 35); 
    }

    @Override
    public String getNameString()
    {
	return "dna_combiner";
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
	return new ContainerDNACombiner(playerInventory, this);
    }

}
