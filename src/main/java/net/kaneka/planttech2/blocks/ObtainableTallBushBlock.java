package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.ObtainableNaturalPlants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;

public class ObtainableTallBushBlock extends ObtainableNaturalPlants
{
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");

    public ObtainableTallBushBlock(float width, float height)
    {
        super(width, height);
        registerDefaultState(defaultBlockState()
                .setValue(IS_TOP, false));
    }

    public ObtainableTallBushBlock()
    {
        this(16, 16);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(IS_TOP);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor levelIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (!checkValid(currentPos, (Level) levelIn))
            levelIn.destroyBlock(currentPos, !stateIn.getValue(IS_TOP));
        BlockState state = levelIn.getBlockState(stateIn.getValue(IS_TOP) ? currentPos.below() : currentPos.above());
        return state.getBlock() == this ? defaultBlockState().setValue(IS_TOP, stateIn.getValue(IS_TOP)) : stateIn;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        if (blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context))
            return defaultBlockState()
                    .setValue(IS_TOP, false);
        return null;
    }

    @Override
    public void setPlacedBy(Level levelIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        levelIn.setBlockAndUpdate(pos.above(), state
                .setValue(IS_TOP, true));
    }

    private boolean checkValid(BlockPos pos, Level level)
    {
        BlockState state = level.getBlockState(pos);
        BlockState state2 = level.getBlockState(state.getValue(IS_TOP) ? pos.below() : pos.above());
        return state2.getBlock() == this && (state.getValue(IS_TOP) != state2.getValue(IS_TOP));
    }

    @Override
    public boolean canPlaceAt(Level level, BlockPos pos)
    {
        return super.canPlaceAt(level, pos) && pos.getY() < 255 && level.isEmptyBlock(pos.above());
    }

    @Override
    public void onReleased(UseOnContext context, BlockState state)
    {
        context.getLevel().setBlockAndUpdate(context.getClickedPos().relative(context.getClickedFace()), defaultBlockState());
        setPlacedBy(context.getLevel(), context.getClickedPos().relative(context.getClickedFace()), state, context.getPlayer(), context.getItemInHand());
    }
}
