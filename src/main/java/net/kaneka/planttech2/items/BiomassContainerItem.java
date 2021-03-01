package net.kaneka.planttech2.items;

import net.kaneka.planttech2.energy.BioEnergyStorage;
import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.kaneka.planttech2.fluids.capability.IBiomassFluidEnergy;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class BiomassContainerItem extends Item
{
	private static final int CAPACITY = 1000;
	public BiomassContainerItem()
	{
		super(new Item.Properties().group(ModCreativeTabs.MAIN).maxStackSize(1));
	}

	public static float getFillLevelModel(ItemStack stack)
	{
		int biomass = BiomassFluidEnergy.getItemStackCap(stack).getCurrentStorage();
		int capacity = BiomassFluidEnergy.getItemStackCap(stack).getMaxStorage();
//		int biomass = getCurrentStorage(stack);
		if (biomass > (capacity / 9) * 9)
			return 9F;
		else if (biomass > (capacity / 9) * 8)
			return 8F;
		else if (biomass > (capacity / 9) * 7)
			return 7F;
		else if (biomass > (capacity / 9) * 6)
			return 6F;
		else if (biomass > (capacity / 9) * 5)
			return 5F;
		else if (biomass > (capacity / 9) * 4)
			return 4F;
		else if (biomass > (capacity / 9) * 3)
			return 3F;
		else if (biomass > (capacity / 9) * 2)
			return 2F;
		else if (biomass > (capacity / 9))
			return 1F;
		return 0F;
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
//		if (entityIn instanceof PlayerEntity && !worldIn.isRemote)
//		{
//			int oldBiomass = stack.getOrCreateTag().getInt("biomass");
//			if (oldBiomass != BiomassFluidEnergy.getItemStackCap(stack).getCurrentStorage())
//				BiomassFluidEnergy.getItemStackCap(stack).setCurrentStorage(oldBiomass);
//		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
	{
		return new BiomassFluidEnergy();
	}

	public static int extractBiomass(ItemStack stack, int amount)
	{
		int current = getCurrentStorage(stack);
		if (current<amount)
		{
			changeCurrentStorage(stack, -current);
			return current;
		}
		else
		{
			changeCurrentStorage(stack, -amount);
			return amount;
		}
	}

	public static int receiveBiomass(ItemStack stack, int amount)
	{
		int current = getCurrentStorage(stack);
		if (current + amount > CAPACITY)
		{
			changeCurrentStorage(stack, CAPACITY - current);
			return -CAPACITY + current;
		}
		else
		{
			changeCurrentStorage(stack, amount);
			return -amount;
		}
	}

	public static void changeCurrentStorage(ItemStack stack, int amount)
	{
		int current = getCurrentStorage(stack);
		if (current + amount < 0)
			current = 0;
		else if (current + amount > CAPACITY)
			current = CAPACITY;
		else
			current += amount;
		setCurrentStorage(stack, current);
	}

	public static void setCurrentStorage(ItemStack stack, int value)
	{
		initTag(stack).putInt("biomass", (value < 0) ? 0 : Math.min(value, CAPACITY));
	}

	public static int getCurrentStorage(ItemStack stack)
	{
		return initTag(stack).getInt("biomass");
	}

	public static int getCapacity()
	{
		return CAPACITY;
	}

	private static CompoundNBT initTag(ItemStack stack)
	{
		CompoundNBT compoundNBT;
		if (stack.hasTag())
			compoundNBT = stack.getTag();
		else
		{
			compoundNBT = new CompoundNBT();
			compoundNBT.putInt("biomass", 0);
			stack.setTag(compoundNBT);
		}
		return compoundNBT;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		IBiomassFluidEnergy cap = BiomassFluidEnergy.getItemStackCap(stack);
//		if (cap.getCurrentStorage() > 0)
//		if (getCurrentStorage(stack) > 0)
//			tooltip.add(new StringTextComponent(cap.getMaxStorage() + "/" + cap.getMaxStorage()));
//			tooltip.add(new StringTextComponent(getCapacity() + "/" + getCapacity()));
//		else
			tooltip.add(new StringTextComponent(cap.getCurrentStorage() + "/" + cap.getMaxStorage()));
//		tooltip.add(new StringTextComponent(getCurrentStorage(stack) + "/" + getCapacity()));
	}

	@Override
	public CompoundNBT getShareTag(ItemStack stack)
	{
		CompoundNBT compound = stack.getOrCreateTag();
		IBiomassFluidEnergy cap = stack.getCapability(BiomassFluidEnergy.BIOMASS_FLUID_ENERGY).orElseThrow(NullPointerException::new);
		INBT compound2 = BiomassFluidEnergy.BIOMASS_FLUID_ENERGY.getStorage().writeNBT(BiomassFluidEnergy.BIOMASS_FLUID_ENERGY, cap, null);
		if (compound2 != null)
			compound.put("biomass", compound2);
		return compound;
	}

	@Override
	public void readShareTag(ItemStack stack, CompoundNBT nbt)
	{
		if (nbt != null && nbt.contains("biomass"))
		{
			IBiomassFluidEnergy cap = stack.getCapability(BiomassFluidEnergy.BIOMASS_FLUID_ENERGY).orElseThrow(NullPointerException::new);
			BiomassFluidEnergy.BIOMASS_FLUID_ENERGY.getStorage().readNBT(BiomassFluidEnergy.BIOMASS_FLUID_ENERGY, cap, null, nbt.getCompound("biomass"));
		}
		super.readShareTag(stack, nbt);
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) 
	{
		if (group == this.group)
		{
			items.add(new ItemStack(ModItems.BIOMASSCONTAINER)); 
			ItemStack full = new ItemStack(ModItems.BIOMASSCONTAINER);
			BiomassFluidEnergy.getItemStackCap(full).setCurrentStorage(BiomassFluidEnergy.getItemStackCap(full).getMaxStorage());
//			setCurrentStorage(full, getCapacity());
			items.add(full);
		}
	}

}
