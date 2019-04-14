package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.kaneka.planttech2.fluids.TempFluidTank;
import net.kaneka.planttech2.items.ItemBiomassContainer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityEnergyInventoryFluid extends TileEntityEnergyInventory
{
    
    protected TempFluidTank fluidtank; 

    public TileEntityEnergyInventoryFluid(TileEntityType<?> type, int energyStorage, int invSize, int fluidtanksize)
    {
	super(type, energyStorage, invSize);
	fluidtank = new TempFluidTank(fluidtanksize);
    }
    
    public void doFluidLoop()
    {
	ItemStack stack = itemhandler.getStackInSlot(getFluidInSlot());
	ItemStack stack2 = itemhandler.getStackInSlot(getFluidOutSlot());
	if(stack != null)
	{
	    if(stack.getItem() instanceof ItemBiomassContainer)
	    {
		fluidtank.receive(((ItemBiomassContainer) stack.getItem()).extractFillLevel(stack, 4));
	    }
	}
	
	if(stack2 != null)
	{
	    if(stack2.getItem() instanceof ItemBiomassContainer)
	    {
		fluidtank.extract(((ItemBiomassContainer) stack2.getItem()).receiveFillLevel(stack2, 4));
	    }
	}
    }
    
    @Override
    public NBTTagCompound write(NBTTagCompound compound)
    {
	compound.setTag("fluidtank", fluidtank.serializeNBT());
	super.write(compound);
	return compound;
    }
    
    @Override
    public void read(NBTTagCompound compound)
    {
	fluidtank.deserializeNBT(compound.getCompound("fluidtank"));
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
	    return fluidtank.getBiomass();
	case 3:
	    return fluidtank.getCapacity();
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
	    fluidtank.setBiomass(value);
	    break; 
	case 3: 
	    fluidtank.setCapacity(value);
	    break;
	}
    }

    @Override
    public int getAmountFields()
    {
	return 4;
    }
    
    protected abstract int getFluidInSlot();
    
    protected abstract int getFluidOutSlot();
    
}
