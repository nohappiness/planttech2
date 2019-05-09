package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.BlockCropBase;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemAnalyser extends ItemBase
{

	public ItemAnalyser()
	{
		super("analyser", new Item.Properties().maxStackSize(1).group(ModCreativeTabs.groupmain));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemUseContext ctx)
	{
	    World world = ctx.getWorld(); 
	    BlockPos pos = ctx.getPos(); 
	    EntityPlayer player = ctx.getPlayer(); 
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
						player.sendMessage(new TextComponentString(messages[i]));
						ok = false; 
					}
				}
				if(ok)
				{
					player.sendMessage(new TextComponentString("Everything ok"));
				}
			}
		}
		return super.onItemUse(ctx);
	}
}
