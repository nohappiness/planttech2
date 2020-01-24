package net.kaneka.planttech2.items;


import net.kaneka.planttech2.gui.guide.GuideScreen;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TestItem extends BaseItem
{

	public TestItem()
	{
		super("testitem", new Item.Properties().group(ModCreativeTabs.groupmain));

	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		//TeleportationUtils.changeDimension(ctx.getWorld(), ctx.getPos(), ctx.getPlayer(), ModDimensions.planttopia_dimtype, Blocks.DIRT);	
		Minecraft.getInstance().displayGuiScreen(new GuideScreen());
		/*
		if(!ctx.getWorld().isRemote)
		{
			new JsonGenerator().create();
		}
		
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