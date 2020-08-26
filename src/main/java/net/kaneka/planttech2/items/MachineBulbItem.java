package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.FacingGrowingBlock;
import net.kaneka.planttech2.blocks.GrowingBlock;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineBulbItem extends BaseItem
{
	private Block crop, hull;
	private int tier;
	private int neededBiomass;

	public MachineBulbItem(String name, Block hull, Block crop, int tier, int neededBiomass)
	{
		super(name, new Item.Properties().group(ModCreativeTabs.groupseeds));
		this.hull = hull;
		this.crop = crop;
		this.tier = tier;
		this.neededBiomass = neededBiomass;
		ModItems.MACHINEBULBS.add(this);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		World world = ctx.getWorld();
		BlockPos pos = ctx.getPos();
		Block target = world.getBlockState(pos).getBlock();
		ItemStack stack = ctx.getItem();
		if(target != null)
		{
			if(target.equals(hull))
			{
				if(crop instanceof FacingGrowingBlock)
				{
					Direction direction = ctx.getFace();
					if(!direction.equals(Direction.DOWN) && !direction.equals(Direction.UP))
					{
        				world.setBlockState(pos, crop.getDefaultState().with(FacingGrowingBlock.FACING, direction));
        				stack.shrink(1);
        				return ActionResultType.CONSUME;
					}
				}
				else
				{
					world.setBlockState(pos, crop.getDefaultState());
    				stack.shrink(1);
    				return ActionResultType.CONSUME;
				}
			}
		}
		return ActionResultType.FAIL;
	}

	public int getTier()
	{
		return tier;
	}

	public Block getHull()
	{
		return hull;
	}

	public Block getCrop()
	{
		return crop;
	}

	public Block getMachine()
	{
		if(crop instanceof GrowingBlock)
		{
			return crop.getBlock();
		}
		return crop;
	}

	public int getNeededBiomass()
	{
		return neededBiomass;
	}

}
