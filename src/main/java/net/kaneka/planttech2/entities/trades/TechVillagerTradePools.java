package net.kaneka.planttech2.entities.trades;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TechVillagerTradePools
{
	private static List<TechVillagerTradePool> SCIENTISTS; 
	
	

	public static List<TechVillagerTradePool> getSCIENTISTS()
	{
		if(SCIENTISTS == null)
		{
			initScientists(); 
		}
		return SCIENTISTS;
	}



	private static void initScientists()
	{
		SCIENTISTS = new ArrayList<TechVillagerTradePool>(); 
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.APPLE), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.ACACIA_SLAB), 5, 10)), 0, 0));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.ACACIA_LOG), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.CROSSBOW), 5, 10)), 0, 0));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.ACACIA_SAPLING), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BLUE_SHULKER_BOX), 5, 10)), 0, 1));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BIRCH_WOOD), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BLACK_WOOL), 5, 10)), 0, 1));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BOOKSHELF), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BIRCH_STAIRS), 5, 10)), 0, 2));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BOW), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BLACK_STAINED_GLASS_PANE), 5, 10)), 0, 2));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BLUE_CARPET), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BARREL), 5, 10)), 0, 3));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BLACK_DYE), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.ENDER_CHEST), 5, 10)), 0, 3));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BIRCH_SIGN), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.DRAGON_HEAD), 5, 10)), 0, 4));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BRICK), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BRICKS), 5, 10)), 0, 4));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BLAZE_ROD), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.BIRCH_WOOD), 5, 10)), 0, 5));
		SCIENTISTS.add(new TechVillagerTradePool(generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.CHEST), 5, 10)), generateStackList(new ItemStackWithRandomSize(new ItemStack(Items.LIGHT_GRAY_BED), 5, 10)), 0, 5));

	}
	
	private static List<ItemStackWithRandomSize> generateStackList(ItemStackWithRandomSize... stacks)
	{
		List<ItemStackWithRandomSize> list = new ArrayList<ItemStackWithRandomSize>(); 
		for(int i = 0; i < stacks.length; i++)
		{
			list.add(stacks[i]);
		}
		return list; 
	}
}
