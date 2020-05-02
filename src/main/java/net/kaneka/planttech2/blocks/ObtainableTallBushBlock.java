package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.ObtainableNaturalPlants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class ObtainableTallBushBlock extends ObtainableNaturalPlants
{
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");

    public ObtainableTallBushBlock(String name, boolean hasItem, float width, float height)
    {
        super(name, hasItem, width, height);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(IS_TOP);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (!checkValid(currentPos, (World) worldIn))
        {
            worldIn.destroyBlock(currentPos, !stateIn.get(IS_TOP));
        }
        BlockState state = worldIn.getBlockState(stateIn.get(IS_TOP) ? currentPos.down() : currentPos.up());
        if (isSameBlock(state.getBlock()))
        {
            return getDefaultState()
                    .with(IS_TOP, stateIn.get(IS_TOP));
        }
        return stateIn;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockPos blockpos = context.getPos();
        if (blockpos.getY() < 255 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context))
        {
            return getDefaultState()
                    .with(IS_TOP, false);
        }
        return null;
    }

    private boolean checkValid(BlockPos pos, World world)
    {
        BlockState state = world.getBlockState(pos);
        BlockState state2 = world.getBlockState(state.get(IS_TOP) ? pos.down() : pos.up());
        return isSameBlock(state2.getBlock()) && (state.get(IS_TOP) != state2.get(IS_TOP));
    }

    /**
     * chech if the block given is an instance of this block
     * @param block block to compare
     */
    protected abstract boolean isSameBlock(Block block);
}
