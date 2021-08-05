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
			if(level.getBlockState(pos).getBlock() instanceof CropBaseBlock cbb)
			{
				String[] messages = cbb.canGrowString(level, pos);
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
