package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.block.AbstractBlock.Properties;

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
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (!checkValid(currentPos, (World) worldIn))
            worldIn.destroyBlock(currentPos, !stateIn.getValue(IS_TOP));
        BlockState state = worldIn.getBlockState(stateIn.getValue(IS_TOP) ? currentPos.below() : currentPos.above());
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
//    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
//    {
//        if (!isPowered(worldIn, pos))
//            worldIn.setBlockState(pos, state.with(OPEN, true));
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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!checkValid(pos, worldIn))
        {
            worldIn.destroyBlock(pos, !state.getValue(IS_TOP));
            return ActionResultType.FAIL;
        }
        if (isPowered(worldIn, pos))
        {
            worldIn.setBlockAndUpdate(pos, defaultBlockState()
                    .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                    .setValue(OPEN, !state.getValue(OPEN))
                    .setValue(IS_TOP, state.getValue(IS_TOP)));
        }
        else return ActionResultType.FAIL;
        worldIn.levelEvent(player, state.getValue(OPEN) ? 1005 : 1011, pos, 0);
        return ActionResultType.SUCCESS;
    }

    private boolean isPowered(World world, BlockPos pos)
    {
        return BaseElectricFence.calculatePower(world, pos) > 0;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlockAndUpdate(pos.above(), state
                .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                .setValue(OPEN, state.getValue(OPEN))
                .setValue(IS_TOP, true));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
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

    private boolean checkValid(BlockPos pos, World world)
    {
        BlockState state = world.getBlockState(pos);
        BlockState state2 = world.getBlockState(state.getValue(IS_TOP) ? pos.below() : pos.above());
        return state2.getBlock() instanceof ElectricFenceGate && (state.getValue(IS_TOP) != state2.getValue(IS_TOP));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent("can be dismantled by wrench"));
    }
}
