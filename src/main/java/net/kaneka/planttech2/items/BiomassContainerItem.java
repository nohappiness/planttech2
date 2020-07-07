package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class BiomassContainerItem extends BaseItem
{
	private static int CAPACITY = 1000;

	static
	{
		ItemModelsProperties.func_239418_a_(ModItems.PLANT_OBTAINER, new ResourceLocation(PlantTechMain.MODID, "filled"),
				(stack, world, entity) -> getFillLevelModel(stack));
	}

	public BiomassContainerItem()
	{
		super("biomasscontainer", new Item.Properties().group(ModCreativeTabs.groupmain).maxStackSize(1));
	}

	public static float getFillLevelModel(ItemStack stack)
	{
//		int biomass = BiomassFluidEnergy.getItemStackCap(stack).getCurrentStorage();
//		int capacity = BiomassFluidEnergy.getItemStackCap(stack).getMaxStorage();
		int biomass = getCurrentStorage(stack);
		if (biomass > (CAPACITY / 9) * 9)
		{
			return 9F;
		} else if (biomass > (CAPACITY / 9) * 8)
		{
			return 8F;
		} else if (biomass > (CAPACITY / 9) * 7)
		{
			return 7F;
		} else if (biomass > (CAPACITY / 9) * 6)
		{
			return 6F;
		} else if (biomass > (CAPACITY / 9) * 5)
		{
			return 5F;
		} else if (biomass > (CAPACITY / 9) * 4)
		{
			return 4F;
		} else if (biomass > (CAPACITY / 9) * 3)
		{
			return 3F;
		} else if (biomass > (CAPACITY / 9) * 2)
		{
			return 2F;
		} else if (biomass > (CAPACITY / 9) * 1)
		{
			return 1F;
		}
		return 0F;
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
		{
			current = 0;
		}
		else if (current + amount > CAPACITY)
		{
			current = CAPACITY;
		}
		else
		{
			current += amount;
		}
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
		{
			compoundNBT = stack.getTag();
		}
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
//		IBiomassFluidEnergy cap = BiomassFluidEnergy.getItemStackCap(stack);
//		if (cap.getCurrentStorage() > 0)
//		if (getCurrentStorage(stack) > 0)
//		{
//			tooltip.add(new StringTextComponent(cap.getMaxStorage() + "/" + cap.getMaxStorage()));
//			tooltip.add(new StringTextComponent(getCapacity() + "/" + getCapacity()));
//		}
//		else
//		{
//			tooltip.add(new StringTextComponent(cap.getCurrentStorage() + "/" + cap.getMaxStorage()));
		tooltip.add(new StringTextComponent(getCurrentStorage(stack) + "/" + getCapacity()));
//		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) 
	{
		if(group == this.group)
		{
			items.add(new ItemStack(ModItems.BIOMASSCONTAINER)); 
			ItemStack full = new ItemStack(ModItems.BIOMASSCONTAINER);
//			BiomassFluidEnergy.getItemStackCap(full).setCurrentStorage(BiomassFluidEnergy.getItemStackCap(full).getMaxStorage());
			setCurrentStorage(full, getCapacity());
			items.add(full);
		}
	}

}
