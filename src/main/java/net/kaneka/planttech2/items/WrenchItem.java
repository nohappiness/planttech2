package net.kaneka.planttech2.items;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.item.Item;

public class WrenchItem extends BaseItem
{

	public WrenchItem()
	{
		super("wrench", new Item.Properties().maxStackSize(1).group(ModCreativeTabs.groupmain));
	}
	
	/*
	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		World world = ctx.getWorld(); 
	    BlockPos pos = ctx.getPos(); 
	    PlayerEntity player = ctx.getPlayer();
		if (!world.isRemote)
		{
			BlockState target = world.getBlockState(pos); 
			ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
			if (!stack.isEmpty())
			{
				System.out.println(player.isSneaking());
				if (stack.getItem() instanceof WrenchItem && player.isSneaking())
				{
					world.removeBlock(pos, false);
					Block.spawnAsEntity(world, pos, new ItemStack(target.getBlock()));
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.onItemUse(ctx);
	}
	*/
}
