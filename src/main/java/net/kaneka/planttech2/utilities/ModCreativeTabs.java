package net.kaneka.planttech2.utilities;

import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModCreativeTabs
{
	public static final ItemGroup groupmain = new ItemGroup("planttech2_main")
	{
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(ModItems.WRENCH);
		}
	};

	public static final ItemGroup groupmachines = new ItemGroup("planttech2_machines")
	{
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(ModBlocks.SOLARGENERATOR);
		}
	};

	public static final ItemGroup groupseeds = new ItemGroup("planttech2_seeds")
	{
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(ModItems.SEEDS.get("diamond"));
		}
	};

	public static final ItemGroup groupparticles = new ItemGroup("planttech2_particles")
	{
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(ModItems.COLOR_PARTICLES);
		}
	};

	public static final ItemGroup groupToolsAndArmor = new ItemGroup("planttech2_toolsandarmor")
	{
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(ModItems.CYBERARMOR_CHEST);
		}
	};
	
	public static final ItemGroup groupchips = new ItemGroup("planttech2_chips")
	{
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(ModItems.CAPACITYUPGRADE_TIER_1);
		}
	};
}
