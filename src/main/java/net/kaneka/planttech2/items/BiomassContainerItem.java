package net.kaneka.planttech2.items;

import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.kaneka.planttech2.fluids.capability.IBiomassFluidEnergy;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.List;

public class BiomassContainerItem extends Item
{
	private static final int CAPACITY = 1000;
	public BiomassContainerItem()
	{
		super(new Item.Properties().tab(ModCreativeTabs.MAIN).stacksTo(1));
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
	public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected)
	{
//		if (entityIn instanceof PlayerEntity && !worldIn.isRemote)
//		{
//			int oldBiomass = stack.getOrCreateTag().getInt("biomass");
//			if (oldBiomass != BiomassFluidEnergy.getItemStackCap(stack).getCurrentStorage())
//				BiomassFluidEnergy.getItemStackCap(stack).setCurrentStorage(oldBiomass);
//		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt)
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

	private static CompoundTag initTag(ItemStack stack)
	{
		CompoundTag CompoundTag;
		if (stack.hasTag())
			CompoundTag = stack.getTag();
		else
		{
			CompoundTag = new CompoundTag();
			CompoundTag.putInt("biomass", 0);
			stack.setTag(CompoundTag);
		}
		return CompoundTag;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
		IBiomassFluidEnergy cap = BiomassFluidEnergy.getItemStackCap(stack);
//		if (cap.getCurrentStorage() > 0)
//		if (getCurrentStorage(stack) > 0)
//			tooltip.add(new TextComponent(cap.getMaxStorage() + "/" + cap.getMaxStorage()));
//			tooltip.add(new TextComponent(getCapacity() + "/" + getCapacity()));
//		else
			tooltip.add(new TextComponent(cap.getCurrentStorage() + "/" + cap.getMaxStorage()));
//		tooltip.add(new TextComponent(getCurrentStorage(stack) + "/" + getCapacity()));
	}

	@Override
	public CompoundTag getShareTag(ItemStack stack)
	{
		CompoundTag compound = stack.getOrCreateTag();
		Tag compound2 = BiomassFluidEnergy.write(stack.getCapability(BiomassFluidEnergy.BIOMASS_FLUID_ENERGY).orElseThrow(NullPointerException::new));
		compound.put("biomass", compound2);
		return compound;
	}

	@Override
	public void readShareTag(ItemStack stack, CompoundTag nbt)
	{
		if (nbt != null && nbt.contains("biomass"))
			BiomassFluidEnergy.read(stack.getCapability(BiomassFluidEnergy.BIOMASS_FLUID_ENERGY).orElseThrow(NullPointerException::new), nbt.getCompound("biomass"));
		super.readShareTag(stack, nbt);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items)
	{
		if (group == this.category)
		{
			items.add(new ItemStack(ModItems.BIOMASSCONTAINER)); 
			ItemStack full = new ItemStack(ModItems.BIOMASSCONTAINER);
			BiomassFluidEnergy.getItemStackCap(full).setCurrentStorage(BiomassFluidEnergy.getItemStackCap(full).getMaxStorage());
//			setCurrentStorage(full, getCapacity());
			items.add(full);
		}
	}

}
