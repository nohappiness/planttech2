package net.kaneka.planttech2.utilities;

import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs
{
	public static final CreativeModeTab MAIN = new CreativeModeTab("planttech2_main")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.WRENCH);
		}
	};

	public static final CreativeModeTab BLOCKS = new CreativeModeTab("planttech2_blocks")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModBlocks.KANEKIUM_BLOCK);
		}
	};

	public static final CreativeModeTab MACHINES = new CreativeModeTab("planttech2_machines")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModBlocks.SOLARGENERATOR);
		}
	};

	public static final CreativeModeTab SEEDS = new CreativeModeTab("planttech2_seeds")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.SEEDS.get("diamond"));
		}
	};

	public static final CreativeModeTab PARTICLES = new CreativeModeTab("planttech2_particles")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.COLOR_PARTICLES);
		}
	};

	public static final CreativeModeTab TOOLS_AND_ARMOR = new CreativeModeTab("planttech2_toolsandarmor")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.CYBERARMOR_CHEST);
		}
	};

	public static final CreativeModeTab CHIPS = new CreativeModeTab("planttech2_chips")
	{
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.CAPACITYUPGRADE_TIER_1);
		}
	};
}
