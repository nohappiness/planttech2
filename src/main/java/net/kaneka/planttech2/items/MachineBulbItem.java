package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.FacingGrowingBlock;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

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
	public InteractionResult useOn(UseOnContext ctx)
	{
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		Block target = level.getBlockState(pos).getBlock();
		ItemStack stack = ctx.getItemInHand();
		if (target == getHull())
		{
			if (getMachine() instanceof FacingGrowingBlock)
			{
				Direction direction = ctx.getClickedFace();
				if (!direction.equals(Direction.DOWN) && !direction.equals(Direction.UP))
				{
					level.setBlockAndUpdate(pos, getMachine().defaultBlockState().setValue(FacingGrowingBlock.FACING, direction));
					stack.shrink(1);
				}
			}
			else
			{
				level.setBlockAndUpdate(pos, getMachine().defaultBlockState());
				stack.shrink(1);
			}
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
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
