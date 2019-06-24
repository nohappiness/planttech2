package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.kaneka.planttech2.fluids.TempFluidTank;
import net.kaneka.planttech2.items.BiomassContainerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public abstract class EnergyInventoryFluidTileEntity extends EnergyInventoryTileEntity
{
    
    protected TempFluidTank fluidtank; 

    public EnergyInventoryFluidTileEntity(TileEntityType<?> type, int energyStorage, int invSize, int fluidtanksize)
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
	    if(stack.getItem() instanceof BiomassContainerItem)
	    {
		fluidtank.receive(((BiomassContainerItem) stack.getItem()).extractFillLevel(stack, 4));
	    }
	}
	
	if(stack2 != null)
	{
	    if(stack2.getItem() instanceof BiomassContainerItem)
	    {
		fluidtank.extract(((BiomassContainerItem) stack2.getItem()).receiveFillLevel(stack2, 4));
	    }
	}
    }
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
	compound.put("fluidtank", fluidtank.serializeNBT());
	super.write(compound);
	return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
	fluidtank.deserializeNBT(compound.getCompound("fluidtank"));
	super.read(compound);
    }
    
    protected abstract int getFluidInSlot();
    
    protected abstract int getFluidOutSlot();
    
}
