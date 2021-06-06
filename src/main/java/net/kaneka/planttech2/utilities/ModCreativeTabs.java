package net.kaneka.planttech2.utilities;

import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModCreativeTabs
{
	public static final ItemGroup MAIN = new ItemGroup("planttech2_main")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.WRENCH);
		}
	};

	public static final ItemGroup BLOCKS = new ItemGroup("planttech2_blocks")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModBlocks.KANEKIUM_BLOCK);
		}
	};

	public static final ItemGroup MACHINES = new ItemGroup("planttech2_machines")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModBlocks.SOLARGENERATOR);
		}
	};

	public static final ItemGroup SEEDS = new ItemGroup("planttech2_seeds")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.SEEDS.get("diamond"));
		}
	};

	public static final ItemGroup PARTICLES = new ItemGroup("planttech2_particles")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.COLOR_PARTICLES);
		}
	};

	public static final ItemGroup TOOLS_AND_ARMOR = new ItemGroup("planttech2_toolsandarmor")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.CYBERARMOR_CHEST);
		}
	};

	public static final ItemGroup CHIPS = new ItemGroup("planttech2_chips")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.CAPACITYUPGRADE_TIER_1);
		}
	};
}
