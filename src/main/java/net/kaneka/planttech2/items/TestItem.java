package net.kaneka.planttech2.items;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class TestItem extends BaseItem
{

	public TestItem()
	{
		super("testitem", new Item.Properties().group(ModCreativeTabs.groupmain));

	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{

		// place(Sets.newHashSet(), ctx.getWorld(), new Random(), ctx.getPos().up(),
		// MutableBoundingBox.getNewBoundingBox());
		// System.out.println(ModDimensionPlantTopia.getDimensionType());
		return super.onItemUse(ctx);
	}
}