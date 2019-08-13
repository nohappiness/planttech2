package net.kaneka.planttech2.items;

import java.util.List;

import com.google.common.collect.Lists;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class CropRemover extends BaseItem
{

	public CropRemover()
	{
		super("cropremover", new Item.Properties().maxStackSize(1).group(ModCreativeTabs.groupmain));
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
	    World world = ctx.getWorld(); 
	    BlockPos pos = ctx.getPos(); 
		if(!world.isRemote)
		{
			BlockState target = world.getBlockState(pos); 
			TileEntity te = world.getTileEntity(pos);
			if(!target.isAir(world, pos) && te != null)
			{
				if(target.getBlock() instanceof CropBaseBlock && te instanceof CropsTileEntity)
				{
					List<ItemStack> drops = Lists.newArrayList();
					int growstate = target.get(CropBaseBlock.GROWSTATE).intValue();
					((CropsTileEntity) te).addDrops(drops, growstate);
					for(ItemStack stack:drops)
					{
						Block.spawnAsEntity(world, pos, stack);
					}
					world.setBlockState(pos, ModBlocks.CROPBARS.getDefaultState());
				}
			}
		}
		return super.onItemUse(ctx);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new StringTextComponent("Rightclick on cropbars to remove crop"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
