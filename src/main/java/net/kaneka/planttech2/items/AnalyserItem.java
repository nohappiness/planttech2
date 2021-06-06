package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class AnalyserItem extends Item
{

	public AnalyserItem()
	{
		super(new Item.Properties().stacksTo(1).tab(ModCreativeTabs.MAIN));
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext ctx)
	{
	    World world = ctx.getLevel(); 
	    BlockPos pos = ctx.getClickedPos(); 
	    PlayerEntity player = ctx.getPlayer(); 
		if(!world.isClientSide && player != null)
		{
			Block targetBlock = world.getBlockState(pos).getBlock(); 

			if(targetBlock instanceof CropBaseBlock)
			{
				String[] messages = ((CropBaseBlock) targetBlock).canGrowString(world, pos);
				boolean ok = true; 
				for(int i = 0; i < 5; i++)
				{
					if(messages[i] != null)
					{
						player.sendMessage(new StringTextComponent(messages[i]), player.getUUID());
						ok = false; 
					}
				}
				if(ok)
				{
					player.sendMessage(new StringTextComponent("Everything ok"), player.getUUID());
				}
			}
		}
		return super.useOn(ctx);
	}
}
