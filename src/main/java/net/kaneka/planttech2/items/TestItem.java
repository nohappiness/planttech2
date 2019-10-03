package net.kaneka.planttech2.items;

import net.kaneka.planttech2.filehelper.JsonGenerator;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.registries.ForgeRegistries;

public class TestItem extends BaseItem
{

	public TestItem()
	{
		super("testitem", new Item.Properties().group(ModCreativeTabs.groupmain));

	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		
		
		if(!ctx.getWorld().isRemote)
		{
			new JsonGenerator().create();
		}
		/*
		if(!ctx.getWorld().isRemote)
		{
		for(Enchantment ench: ForgeRegistries.ENCHANTMENTS)
		{
			System.out.println("\"item.planttech2."+ ench.getName().replace("enchantment.minecraft.", "") + "_chip\": \""+ench.getName().replace("enchantment.minecraft.", "")+" Chip\""); 
		}
		}
		*/
		// place(Sets.newHashSet(), ctx.getWorld(), new Random(), ctx.getPos().up(),
		// MutableBoundingBox.getNewBoundingBox());
		// System.out.println(ModDimensionPlantTopia.getDimensionType());
		return super.onItemUse(ctx);
	}
}