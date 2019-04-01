package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ContainerSeedconstructor;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.items.ModItems;
import net.kaneka.planttech2.tileentity.ModTileEntities;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySeedconstructor extends TileEntityEnergyInventory
{
    private int ticksPassed = 0;

    public TileEntitySeedconstructor()
    {
	super(ModTileEntities.SEEDCONSTRUCTOR_TE, 1000, 4);
    }

    @Override
    public void doUpdate()
    {
	if (energystorage.getEnergyStored() > energyPerTick() || true)
	{
	    ItemStack stack1 = itemhandler.getStackInSlot(0);
	    ItemStack stack2 = itemhandler.getStackInSlot(1);
	    ItemStack stack3 = itemhandler.getStackInSlot(2);

	    if (!stack1.isEmpty() && !stack2.isEmpty() && stack3.isEmpty())
	    {
		if (stack1.getItem() == ModItems.DNA_CONTAINER && stack1.hasTag() && stack2.getItem() == ModItems.BIOMASS && stack2.getCount() >= 32)
		{
		    if (ticksPassed < ticksPerItem())
		    {
			ticksPassed++;
			//energystorage.extractEnergy(energyPerTick(), false);
		    }
		    else
		    {
			ticksPassed = 0;
			//energystorage.extractEnergy(energyPerTick(), false);
			NBTTagCompound nbt = stack1.getTag();
			HashMapCropTraits traits = new HashMapCropTraits();
			traits.setAnalysed(true);
			if (nbt.hasKey("type"))
			{
			    traits.setType(nbt.getString("type"));
			}
			for (String key : HashMapCropTraits.getTraitsKeyList())
			{
			    if (nbt.hasKey(key))
			    {
				if (!key.equals("type"))
				{
				    traits.setTrait(EnumTraitsInt.getByName(key), nbt.getInt(key));
				}
			    }
			}
			ItemStack stack = new ItemStack(ModItems.SEEDS.get(traits.getType()));
			itemhandler.setStackInSlot(2, traits.addToItemStack(stack));
			stack2.shrink(32);
		    }
		}
	    }
	}
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
	return "seedconstructor";
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
	return new ContainerSeedconstructor(playerInventory, this);
    }

}
