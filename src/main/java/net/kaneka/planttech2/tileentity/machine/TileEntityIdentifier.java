package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.ContainerIdentifier;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventory;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityIdentifier extends TileEntityEnergyInventory
{
    public int ticksPassed = 0;

    public TileEntityIdentifier()
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
		//this.energystorage.extractEnergy(getEnergyPerTickPerItem(), false);
		ticksPassed++;
		if (ticksPassed >= this.getTicksPerItem())
		{
		    this.identifyItem();
		    ticksPassed = 0;
		}
	    }
	    else if (ticksPassed > 0)
	    {
		ticksPassed = 0;
	    }
	}
	else
	{
	    if (!this.canIdentify() && ticksPassed > 0)
	    {
		ticksPassed = 0;
	    }
	}

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
		NBTTagCompound nbt = stack.getTag();
		if (nbt != null)
		{
		    if (nbt.hasKey("analysed"))
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
		    NBTTagCompound nbt = stack.getTag();

		    if (nbt != null)
		    {
			if (nbt.hasKey("analysed"))
			{
			    if (!nbt.getBoolean("analysed"))
			    {
				nbt.setBoolean("analysed", true);
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
	return 4 + (getUpgradeTier(18, Constants.SPEEDUPGRADE_TYPE) * 4);
    }

    public int getTicksPerItem()
    {
	return 200 - (getUpgradeTier(18, Constants.SPEEDUPGRADE_TYPE) * 35);
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound)
    {
	compound.setInt("cooktime", ticksPassed);
	super.write(compound);
	return compound;
    }

    @Override
    public void read(NBTTagCompound compound)
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
	return new ContainerIdentifier(playerInventory, this);
    }
}
