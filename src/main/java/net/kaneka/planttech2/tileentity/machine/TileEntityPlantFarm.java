package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.blocks.BlockCropBase;
import net.kaneka.planttech2.container.ContainerPlantFarm;
import net.kaneka.planttech2.items.ItemWithTier;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.TileEntityCrops;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventory;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class TileEntityPlantFarm extends TileEntityEnergyInventory
{
    private int actualX = 0, actualY = 0;

    public TileEntityPlantFarm()
    {
	super(ModTileEntities.PLANTFARM_TE, 100000, 17);
    }

    @Override
    public void doUpdate()
    {
	if (world.getGameTime() % (400F - getSpeedReduction() * 45F) == 0F)
	{
	    actualX++;
	    int range = getRange();
	    int diameter = range * 2 + 1;
	    if (actualX >= diameter)
	    {
		actualX = 0;
		actualY++;
		if (actualY >= diameter)
		{
		    actualY = 0;
		}
	    }

	    if (energystorage.getEnergyStored() >= getEnergyPerAction() || true)
	    {

		BlockPos actualPos = this.pos.add(actualX - range, 0, actualY - range);

		BlockState state = world.getBlockState(actualPos);
		if (world.getBlockState(actualPos).getBlock() instanceof BlockCropBase)
		{
		    if (state.get(BlockCropBase.GROWSTATE) > 6)
		    {
			NonNullList<ItemStack> drops = NonNullList.create();
			TileEntity te = world.getTileEntity(actualPos);
			if (te instanceof TileEntityCrops)
			{
			    ((TileEntityCrops) te).dropsRemoveOneSeed(drops, 7);
			    for (ItemStack stack : drops)
			    {
				for (int i = 0; i < 15; i++)
				{
				    if (!stack.isEmpty())
				    {
					stack = itemhandler.insertItem(i, stack, false);
				    }
				}
				if (!stack.isEmpty())
				{
				    spawnAsEntity(world, pos.up(), stack);
				}
			    }
			    world.setBlockState(actualPos, state.with(BlockCropBase.GROWSTATE, 0));
			    //energystorage.extractEnergy(getEnergyPerAction());
			}
		    }
		}
	    }
	}
    }

    private int getEnergyPerAction()
    {
	return 20 + 40 * (getSpeedReduction() + getRange());
    }

    private int getSpeedReduction()
    {

	ItemStack stack = itemhandler.getStackInSlot(16);
	if (!stack.isEmpty())
	{
	    if (stack.getItem() instanceof ItemWithTier)
	    {
		ItemWithTier item = (ItemWithTier) stack.getItem();
		if (item.getItemType() == Constants.SPEEDUPGRADE_TYPE)
		{ 
		    return item.getTier();
		}
	    }
	}
	return 0;
    }

    private int getRange()
    {
	ItemStack stack = itemhandler.getStackInSlot(15);
	if (!stack.isEmpty())
	{
	    if (stack.getItem() instanceof ItemWithTier)
	    {
		ItemWithTier item = (ItemWithTier) stack.getItem();
		if (item.getItemType() == Constants.RANGEUPGRADE_TYPE)
		{
		    return item.getTier() + 1;
		}
	    }
	}
	return 1;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
	compound.putInt("actualx", actualX);
	compound.putInt("actualY", actualY);
	return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound)
    {
	actualX = compound.getInt("actualx");
	actualY = compound.getInt("actualy");
	super.read(compound);
    }

    @Override
    public String getNameString()
    {
	return "plantfarm";
    }
    
}
