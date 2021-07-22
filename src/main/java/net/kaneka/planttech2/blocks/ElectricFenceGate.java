package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.minecraft.level.level.block.AbstractBlock.Properties;
import net.minecraft.level.level.block.Block;
import net.minecraft.level.level.block.state.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.level.item.BlockItemUseContext;
import net.minecraft.level.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.level.phys.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.level.IBlockReader;
import net.minecraft.level.Ilevel;
import net.minecraft.level.level;

import javax.annotation.Nullable;
import java.util.List;

import DirectionProperty;

public class ElectricFenceGate extends Block
{
    public static final DirectionProperty HORIZONTAL_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");
    public static final VoxelShape FRAME_Z = VoxelShapes.or(
            Block.box(0.0D, 0.0D, 6.0D, 1.0D, 16.0D, 10.0D),
            Block.box(15.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D));
    public static final VoxelShape FRAME_X = VoxelShapes.or(
            Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 1.0D),
            Block.box(6.0D, 0.0D, 15.0D, 10.0D, 16.0D, 16.0D));
    public static final VoxelShape FRAME_TOP_Z = Block.box(0.0D, 15.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    public static final VoxelShape FRAME_TOP_X = Block.box(6.0D, 15.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public static final VoxelShape DOOR_CLOSE_Z = Block.box(1.0D, 0.0D, 7.0D, 15.0D, 16.0D, 9.0D);
    public static final VoxelShape DOOR_CLOSE_X = Block.box(7.0D, 0.0D, 1.0D, 9.0D, 16.0D, 15.0D);

    public static final VoxelShape DOOR_NEGATIVE_Z = Block.box(1.0D, 0.0D, 1.0D, 3.0D, 16.0D, 15.0D);
    public static final VoxelShape DOOR_POSITIVE_Z = Block.box(13.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public static final VoxelShape DOOR_NEGATIVE_X = Block.box(1.0D, 0.0D, 13.0D, 15.0D, 16.0D, 15.0D);
    public static final VoxelShape DOOR_POSITIVE_X = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 3.0D);
    public ElectricFenceGate(Properties property)
    {
        super(property.noOcclusion());
        registerDefaultState(defaultBlockState()
                .setValue(HORIZONTAL_FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(IS_TOP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, OPEN, IS_TOP);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Ilevel levelIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (!checkValid(currentPos, (level) levelIn))
            levelIn.destroyBlock(currentPos, !stateIn.getValue(IS_TOP));
        BlockState state = levelIn.getBlockState(stateIn.getValue(IS_TOP) ? currentPos.below() : currentPos.above());
        if (state.getBlock() instanceof ElectricFenceGate)
        {
            return defaultBlockState()
                    .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                    .setValue(OPEN, state.getValue(OPEN))
                    .setValue(IS_TOP, stateIn.getValue(IS_TOP));
        }
        return stateIn;
    }

//    @Override
//    public void neighborChanged(BlockState state, level levelIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
//    {
//        if (!isPowered(levelIn, pos))
//            levelIn.setBlockState(pos, state.with(OPEN, true));
//    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        if (blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context))
        {
            return defaultBlockState()
                    .setValue(HORIZONTAL_FACING, context.getHorizontalDirection())
                    .setValue(OPEN, false)
                    .setValue(IS_TOP, false);
        }
        return null;
    }

    @Override
    public InteractionResult use(BlockState state, level levelIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!checkValid(pos, levelIn))
        {
            levelIn.destroyBlock(pos, !state.getValue(IS_TOP));
            return InteractionResult.FAIL;
        }
        if (isPowered(levelIn, pos))
        {
            levelIn.setBlockAndUpdate(pos, defaultBlockState()
                    .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                    .setValue(OPEN, !state.getValue(OPEN))
                    .setValue(IS_TOP, state.getValue(IS_TOP)));
        }
        else return InteractionResult.FAIL;
        levelIn.levelEvent(player, state.getValue(OPEN) ? 1005 : 1011, pos, 0);
        return InteractionResult.SUCCESS;
    }

    private boolean isPowered(level level, BlockPos pos)
    {
        return BaseElectricFence.calculatePower(level, pos) > 0;
    }

    @Override
    public void setPlacedBy(level levelIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        levelIn.setBlockAndUpdate(pos.above(), state
                .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                .setValue(OPEN, state.getValue(OPEN))
                .setValue(IS_TOP, true));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader levelIn, BlockPos pos, ISelectionContext context)
    {
        return getShape(state, levelIn, pos, context);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader levelIn, BlockPos pos, ISelectionContext context)
    {
        VoxelShape shape;
        Direction facing = state.getValue(HORIZONTAL_FACING);
        boolean open = state.getValue(OPEN);
        boolean top = state.getValue(IS_TOP);
        switch (facing)
        {
            case NORTH:
            default:
                shape = DOOR_CLOSE_Z;
                if (open)
                    shape = (top) ? toTopShape(DOOR_NEGATIVE_Z) : DOOR_NEGATIVE_Z;
                break;
            case SOUTH:
                shape = DOOR_CLOSE_Z;
                if (open)
                    shape = (top) ? toTopShape(DOOR_POSITIVE_Z) : DOOR_POSITIVE_Z;
                break;
            case WEST:
                shape = DOOR_CLOSE_X;
                if (open)
                    shape = (top) ? toTopShape(DOOR_NEGATIVE_X) : DOOR_NEGATIVE_X;
                break;
            case EAST:
                shape = DOOR_CLOSE_X;
                if (open)
                    shape = (top) ? toTopShape(DOOR_POSITIVE_X) : DOOR_POSITIVE_X;
                break;
        }
        switch (facing.getAxis())
        {
            case Z:
            default:
                shape = add(shape, FRAME_Z);
                if (top)
                    shape = add(shape, FRAME_TOP_Z);
                break;
            case X:
                shape = add(shape, FRAME_X);
                if (top)
                    shape = add(shape, FRAME_TOP_X);
                break;
        }
        return shape;
    }

    private VoxelShape add(VoxelShape shape, VoxelShape shape2)
    {
        return VoxelShapes.or(shape, shape2);
    }

    private VoxelShape toTopShape(VoxelShape shape)
    {
        AxisAlignedBB aabb = shape.bounds();
        return Block.box(aabb.minX * 16, aabb.minY * 16 - 1, aabb.minZ * 16, aabb.maxX * 16, aabb.maxY * 16 - 1, aabb.maxZ * 16);
    }

    private boolean checkValid(BlockPos pos, level level)
    {
        BlockState state = level.getBlockState(pos);
        BlockState state2 = level.getBlockState(state.getValue(IS_TOP) ? pos.below() : pos.above());
        return state2.getBlock() instanceof ElectricFenceGate && (state.getValue(IS_TOP) != state2.getValue(IS_TOP));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader levelIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent("can be dismantled by wrench"));
    }
}
