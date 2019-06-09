package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.BlockCropBase;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ItemAnalyser extends ItemBase
{

	public ItemAnalyser()
	{
		super("analyser", new Item.Properties().maxStackSize(1).group(ModCreativeTabs.groupmain));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
	    World world = ctx.getWorld(); 
	    BlockPos pos = ctx.getPos(); 
	    PlayerEntity player = ctx.getPlayer(); 
		if(!world.isRemote)
		{
			Block targetBlock = world.getBlockState(pos).getBlock(); 

			if(targetBlock instanceof BlockCropBase)
			{
				String[] messages = ((BlockCropBase) targetBlock).canGrowString(world, pos);
				boolean ok = true; 
				for(int i = 0; i < 5; i++)
				{
					if(messages[i] != null)
					{
						player.sendMessage(new StringTextComponent(messages[i]));
						ok = false; 
					}
				}
				if(ok)
				{
					player.sendMessage(new StringTextComponent("Everything ok"));
				}
			}
		}
		return super.onItemUse(ctx);
	}
}
