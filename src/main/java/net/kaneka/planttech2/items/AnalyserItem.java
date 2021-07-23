package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class AnalyserItem extends Item
{

	public AnalyserItem()
	{
		super(new Item.Properties().stacksTo(1).tab(ModCreativeTabs.MAIN));
	}
	
	@Override
	public InteractionResult useOn(UseOnContext ctx)
	{
	    Level level = ctx.getLevel();
	    BlockPos pos = ctx.getClickedPos(); 
	    Player player = ctx.getPlayer();
		if(!level.isClientSide && player != null)
		{
			Block targetBlock = level.getBlockState(pos).getBlock();

			if(targetBlock instanceof CropBaseBlock)
			{
				String[] messages = ((CropBaseBlock) targetBlock).canGrowString(level, pos);
				boolean ok = true; 
				for(int i = 0; i < 5; i++)
				{
					if(messages[i] != null)
					{
						player.sendMessage(new TextComponent(messages[i]), player.getUUID());
						ok = false; 
					}
				}
				if(ok)
				{
					player.sendMessage(new TextComponent("Everything ok"), player.getUUID());
				}
			}
		}
		return super.useOn(ctx);
	}
}
