package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.ObtainableNaturalPlants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ObtainableTallBushBlock extends ObtainableNaturalPlants
{
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");

    public ObtainableTallBushBlock(String name, boolean hasItem, float width, float height)
    {
        super(name, hasItem, width, height);
        setDefaultState(getDefaultState()
                .with(IS_TOP, false));
    }

    public ObtainableTallBushBlock(String name, boolean hasItem)
    {
        this(name, hasItem, 16, 16);
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
        if (state.getBlock() == this)
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

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.up(), state
                .with(IS_TOP, true));
    }

    private boolean checkValid(BlockPos pos, World world)
    {
        BlockState state = world.getBlockState(pos);
        BlockState state2 = world.getBlockState(state.get(IS_TOP) ? pos.down() : pos.up());
        return state2.getBlock() == this && (state.get(IS_TOP) != state2.get(IS_TOP));
    }

    @Override
    public boolean canPlaceAt(World world, BlockPos pos)
    {
        return super.canPlaceAt(world, pos) && pos.getY() < 255 && world.isAirBlock(pos.up());
    }

    @Override
    public void onReleased(ItemUseContext context, BlockState state)
    {
        context.getWorld().setBlockState(context.getPos().offset(context.getFace()), getDefaultState());
        onBlockPlacedBy(context.getWorld(), context.getPos().offset(context.getFace()), state, context.getPlayer(), context.getItem());
    }
}
