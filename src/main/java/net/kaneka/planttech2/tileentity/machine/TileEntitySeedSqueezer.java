package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.container.ContainerSeedSqueezer;
import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventory;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventoryFluid;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntitySeedSqueezer extends TileEntityEnergyInventoryFluid
{
    public int ticksPassed = 0;

    public TileEntitySeedSqueezer()
    {
	super(ModTileEntities.SEEDSQUEEZER_TE, 100000, 14, 5000);
    }

    @Override
    public void tick()
    {
	if (!world.isRemote)
	{
	    if (itemhandler.getStackInSlot(9).isEmpty())
	    {
		int i = this.getSqueezeableItem();
		if (i != 100)
		{
		    ItemStack stack = itemhandler.getStackInSlot(i);
		    ItemStack stack2 = stack.copy();
		    stack2.setCount(1);
		    itemhandler.setStackInSlot(9, stack2);
		    stack.shrink(1);
		}
	    }

	    if (this.energystorage.getEnergyStored() <= energystorage.getMaxEnergyStored() - this.getEnergyPerItem() && !itemhandler.getStackInSlot(9).isEmpty())
	    {
		ItemStack stack = itemhandler.getStackInSlot(9);
		if (stack.getCount() == 1 && (stack.getItem() instanceof ItemSeeds || stack.getItem() instanceof ItemCropSeed))
		{
		    ticksPassed += getUpgradeTier(11, Constants.SPEEDUPGRADE_TYPE) + 1;
		    if (ticksPassed >= this.getTicksPerItem())
		    {
			squeezeItem();
			fluidtank.receive(10);
			ticksPassed = 0;
		    }
		}
		else if (stack.getCount() > 0)
		{
		    if (!world.isRemote)
		    {
			world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
			itemhandler.setStackInSlot(9, ItemStack.EMPTY);
		    }
		}
		else if (ticksPassed > 0)
		{
		    ticksPassed = 0;
		}
	    }
	}
	
	doFluidLoop();
    }

    private int getSqueezeableItem()
    {
	for (int i = 0; i < 9; i++)
	{
	    ItemStack stack = this.itemhandler.getStackInSlot(i);
	    if (!stack.isEmpty())
	    {
		if (stack.getItem() instanceof ItemSeeds || stack.getItem() instanceof ItemCropSeed)
		{
		    return i;
		}
	    }
	}
	return 100;
    }

    public void squeezeItem()
    {
	this.energystorage.receiveEnergy(getEnergyPerItem(), false);
	itemhandler.setStackInSlot(9, ItemStack.EMPTY);
    }

    public int getTicksPerItem()
    {
	return 200;
    }

    public int getEnergyPerItem()
    {
	ItemStack stack = this.itemhandler.getStackInSlot(9);
	if (!stack.isEmpty())
	{
	    if (stack.getItem() instanceof ItemCropSeed)
	    {
		NBTTagCompound nbt = stack.getTag();
		if (nbt != null)
		{
		    if (nbt.hasKey("energyvalue"))
		    {
			return nbt.getInt("energyvalue") * 20;
		    }
		}
	    }
	}
	return 20;
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
	return "seedsqueezer";
    }

    @Override
    public int getField(int id)
    {
	if (id < 4)
	{
	    return super.getField(id);
	}
	else
	{
	    switch (id)
	    {
	    case 4:
		return this.ticksPassed;
	    default:
		return 0;
	    }
	}
    }

    @Override
    public void setField(int id, int value)
    {
	if (id < 4)
	{
	    super.setField(id, value);
	}
	else
	{
	    switch (id)
	    {
	    case 4:
		ticksPassed = value;
		break;
	    }
	}
    }

    @Override
    public int getAmountFields()
    {
	return 5;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
	return new ContainerSeedSqueezer(playerInventory, this);
    }

    @Override
    protected int getFluidInSlot()
    {
	return 12;
    }

    @Override
    protected int getFluidOutSlot()
    {
	return 13;
    }
}
