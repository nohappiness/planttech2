package net.kaneka.planttech2.items;

import java.util.List;

import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.kaneka.planttech2.energy.EnergyProvider;
import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import net.minecraft.item.Item.Properties;

public class EnergyStorageItem extends Item implements IItemChargeable
{
	private int basecapacity; 

	public EnergyStorageItem(Properties property, int basecapacity)
	{
		super(property.maxStackSize(1));
		this.basecapacity = basecapacity;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
	{
		return new EnergyProvider(basecapacity);
	}
	
	@Override
	public int extractEnergy(ItemStack stack, int amount, boolean simulate)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			int returnvalue = storage.extractEnergy(amount, simulate);
			updateEnergy(stack); 
			return returnvalue; 
		}
		return 0;
	}

	@Override
	public int receiveEnergy(ItemStack stack, int amount, boolean simulate)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			int returnvalue = storage.receiveEnergy(amount, simulate);
			updateEnergy(stack);
			return returnvalue; 
		}
		return 0;
	}

	@Override
	public int maxEnergy(ItemStack stack)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			return storage.getMaxEnergyStored();
		}
		return 0;
	}

	@Override
	public int currentEnergy(ItemStack stack)
	{
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage != null)
		{
			return storage.getEnergyStored();
		}
		return 0;
	}
	
	public static IEnergyStorage getEnergyCap(ItemStack stack)
	{
		LazyOptional<IEnergyStorage> provider = stack.getCapability(CapabilityEnergy.ENERGY);
		if (provider.isPresent())
		{
			return provider.orElse(null);
		}
		return null;
	}
	
	protected void updateEnergy(ItemStack stack)
	{
		CompoundNBT tag = stack.getTag();
		if (tag == null)
		{
			tag = new CompoundNBT();
		}
		IEnergyStorage storage = getEnergyCap(stack);
		if (storage instanceof BioEnergyStorage)
		{
			tag.putInt("current_energy", ((BioEnergyStorage) storage).getEnergyStored());
			tag.putInt("max_energy", ((BioEnergyStorage) storage).getMaxEnergyStored());
		}
		stack.setTag(tag);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		CompoundNBT tag = stack.getTag();
		if (tag != null)
		{
			tooltip.add(new StringTextComponent(tag.getInt("current_energy") + "/" + tag.getInt("max_energy") + " BE"));
		}
		else
		{
			updateEnergy(stack);
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		CompoundNBT tag = stack.getTag();
		if (tag != null)
		{
			return 1D - ((double) tag.getInt("current_energy") / (double) tag.getInt("max_energy"));
		}
		else
		{
			updateEnergy(stack);
		}

		return 1D;
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack)
	{
		return Integer.parseInt("06bc00", 16);
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if(group == this.group)
		{
			items.add(new ItemStack(this));
			ItemStack full = new ItemStack(this);
			this.receiveEnergy(full, this.maxEnergy(full), false);
			items.add(full);
		}
	}
}
