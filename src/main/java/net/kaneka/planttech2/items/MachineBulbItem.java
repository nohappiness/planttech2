package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.FacingGrowingBlock;
import net.kaneka.planttech2.blocks.GrowingBlock;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class MachineBulbItem extends Item
{
	private final Supplier<Block> crop, hull;
	private final int tier;
	private final int neededBiomass;

	public MachineBulbItem(Supplier<Block> hull, Supplier<Block> crop, int tier, int neededBiomass)
	{
		super(new Item.Properties().group(ModCreativeTabs.SEEDS));
		this.hull = hull;
		this.crop = crop;
		this.tier = tier;
		this.neededBiomass = neededBiomass;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		World world = ctx.getWorld();
		BlockPos pos = ctx.getPos();
		Block target = world.getBlockState(pos).getBlock();
		ItemStack stack = ctx.getItem();
		if (target == getHull())
		{
			if (getCrop() instanceof FacingGrowingBlock)
			{
				Direction direction = ctx.getFace();
				if (!direction.equals(Direction.DOWN) && !direction.equals(Direction.UP))
				{
					world.setBlockState(pos, getCrop().getDefaultState().with(FacingGrowingBlock.FACING, direction));
					stack.shrink(1);
				}
			}
			else
			{
				world.setBlockState(pos, getCrop().getDefaultState());
				stack.shrink(1);
			}
			return ActionResultType.CONSUME;
		}
		return ActionResultType.PASS;
	}

	public int getTier()
	{
		return tier;
	}

	public Block getHull()
	{
		return hull.get();
	}

	public Block getCrop()
	{
		return crop.get();
	}

	public Block getMachine()
	{
		return crop.get();
	}

	public int getNeededBiomass()
	{
		return neededBiomass;
	}
}
