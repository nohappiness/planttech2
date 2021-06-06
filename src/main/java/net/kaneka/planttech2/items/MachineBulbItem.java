package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.FacingGrowingBlock;
import net.kaneka.planttech2.blocks.GrowingBlock;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class MachineBulbItem extends BlockItem
{
	private final Supplier<Block> hull, crop;
	private final int tier;
	private final int neededBiomass;

	public MachineBulbItem(Supplier<Block> hull, Supplier<Block> crop, int tier, int neededBiomass)
	{
		super(crop.get(), new Item.Properties().tab(ModCreativeTabs.SEEDS));
		this.hull = hull;
		this.crop = crop;
		this.tier = tier;
		this.neededBiomass = neededBiomass;
	}

	@Override
	public ActionResultType useOn(ItemUseContext ctx)
	{
		World world = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		Block target = world.getBlockState(pos).getBlock();
		ItemStack stack = ctx.getItemInHand();
		if (target == getHull())
		{
			if (getMachine() instanceof FacingGrowingBlock)
			{
				Direction direction = ctx.getClickedFace();
				if (!direction.equals(Direction.DOWN) && !direction.equals(Direction.UP))
				{
					world.setBlockAndUpdate(pos, getMachine().defaultBlockState().setValue(FacingGrowingBlock.FACING, direction));
					stack.shrink(1);
				}
			}
			else
			{
				world.setBlockAndUpdate(pos, getMachine().defaultBlockState());
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

	public Block getMachine()
	{
		return crop.get();
	}

	public int getNeededBiomass()
	{
		return neededBiomass;
	}
}
