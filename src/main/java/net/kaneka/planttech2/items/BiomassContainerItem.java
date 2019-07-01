package net.kaneka.planttech2.items;

import java.util.List;

import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class BiomassContainerItem extends BaseItem
{

	private static int capacity = 1000;

	public BiomassContainerItem()
	{
		super("biomasscontainer", new Item.Properties().group(ModCreativeTabs.groupmain));
		addPropertyOverride(new ResourceLocation("filled"), (stack, world, player) -> {
			return getFillLevelModel(stack);
		});
	}

	public static float getFillLevelModel(ItemStack stack)
	{
		CompoundNBT nbt = stack.getTag();
		if (nbt != null)
		{
			int biomass = nbt.getInt("biomass");

			if (biomass > (capacity / 9) * 9)
			{
				return 9F;
			} else if (biomass > (capacity / 9) * 8)
			{
				return 8F;
			} else if (biomass > (capacity / 9) * 7)
			{
				return 7F;
			} else if (biomass > (capacity / 9) * 6)
			{
				return 6F;
			} else if (biomass > (capacity / 9) * 5)
			{
				return 5F;
			} else if (biomass > (capacity / 9) * 4)
			{
				return 4F;
			} else if (biomass > (capacity / 9) * 3)
			{
				return 3F;
			} else if (biomass > (capacity / 9) * 2)
			{
				return 2F;
			} else if (biomass > (capacity / 9) * 1)
			{
				return 1F;
			}

		}

		return 0F;
	}

	public int getFillLevel(ItemStack stack)
	{
		CompoundNBT nbt = stack.getTag();
		if (nbt != null)
		{
			return nbt.getInt("biomass");
		}
		return 0;
	}

	private void setFillLevel(ItemStack stack, int value)
	{
		CompoundNBT nbt = stack.getTag();
		if (nbt == null)
		{
			nbt = new CompoundNBT();
		}
		nbt.putInt("biomass", value);
		stack.setTag(nbt);
	}

	public int receiveFillLevel(ItemStack stack, int value)
	{
		CompoundNBT nbt = stack.getTag();
		if (nbt == null)
		{
			nbt = new CompoundNBT();
		}
		int biomass = nbt.getInt("biomass");
		int space = capacity - biomass;
		if (space >= value)
		{
			setFillLevel(stack, biomass + value);
			return value;
		} else
		{
			setFillLevel(stack, capacity);
			return space;
		}
	}

	public int extractFillLevel(ItemStack stack, int value)
	{
		CompoundNBT nbt = stack.getTag();
		if (nbt == null)
		{
			nbt = new CompoundNBT();
		}
		int biomass = nbt.getInt("biomass");
		if (biomass >= value)
		{
			setFillLevel(stack, biomass - value);
			return value;
		} else
		{
			setFillLevel(stack, 0);
			return biomass;
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		CompoundNBT nbt = stack.getTag();
		if (nbt != null)
		{
			tooltip.add(new StringTextComponent(nbt.getInt("biomass") + "/" + capacity));
		} else
		{
			tooltip.add(new StringTextComponent("0/" + capacity));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) 
	{
		if(group == this.group)
		{
			items.add(new ItemStack(ModItems.BIOMASSCONTAINER)); 
			ItemStack full = new ItemStack(ModItems.BIOMASSCONTAINER);
			setFillLevel(full, capacity);
			items.add(full); 
		}
	}

}
