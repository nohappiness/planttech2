package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.registries.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
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
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ElectricFenceGate extends BaseBlock
{
    public static final DirectionProperty HORIZONTAL_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");
    public static final VoxelShape FRAME_Z = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 1.0D, 16.0D, 10.0D),
            Block.makeCuboidShape(15.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D));
    public static final VoxelShape FRAME_X = VoxelShapes.or(
            Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 1.0D),
            Block.makeCuboidShape(6.0D, 0.0D, 15.0D, 10.0D, 16.0D, 16.0D));
    public static final VoxelShape FRAME_TOP_Z = Block.makeCuboidShape(0.0D, 15.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    public static final VoxelShape FRAME_TOP_X = Block.makeCuboidShape(6.0D, 15.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public static final VoxelShape DOOR_CLOSE_Z = Block.makeCuboidShape(1.0D, 0.0D, 7.0D, 15.0D, 16.0D, 9.0D);
    public static final VoxelShape DOOR_CLOSE_X = Block.makeCuboidShape(7.0D, 0.0D, 1.0D, 9.0D, 16.0D, 15.0D);

    public static final VoxelShape DOOR_NEGATIVE_Z = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 3.0D, 16.0D, 15.0D);
    public static final VoxelShape DOOR_POSITIVE_Z = Block.makeCuboidShape(13.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public static final VoxelShape DOOR_NEGATIVE_X = Block.makeCuboidShape(1.0D, 0.0D, 13.0D, 15.0D, 16.0D, 15.0D);
    public static final VoxelShape DOOR_POSITIVE_X = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 3.0D);
    public ElectricFenceGate(Properties property, String name, ItemGroup group, boolean hasItem)
    {
        super(property.notSolid(), name, group, hasItem);
        setDefaultState(getDefaultState()
                .with(HORIZONTAL_FACING, Direction.NORTH)
                .with(OPEN, false)
                .with(IS_TOP, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, OPEN, IS_TOP);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (!checkValid(currentPos, (World) worldIn))
        {
            worldIn.destroyBlock(currentPos, !stateIn.get(IS_TOP));
        }
        BlockState state;
        if (stateIn.get(IS_TOP))
        {
            state = worldIn.getBlockState(currentPos.down());
        }
        else
        {
            state = worldIn.getBlockState(currentPos.up());
        }
        if (state.getBlock() instanceof ElectricFenceGate)
        {
            return getDefaultState()
                    .with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING))
                    .with(OPEN, state.get(OPEN))
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
                    .with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing())
                    .with(OPEN, false)
                    .with(IS_TOP, false);
        }
        return null;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!checkValid(pos, worldIn))
        {
            worldIn.destroyBlock(pos, !state.get(IS_TOP));
            return ActionResultType.FAIL;
        }
        worldIn.setBlockState(pos, getDefaultState()
                .with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING))
                .with(OPEN, !state.get(OPEN))
                .with(IS_TOP, state.get(IS_TOP)));
        worldIn.playEvent(player, state.get(OPEN) ? 1005 : 1011, pos, 0);
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlockState(pos.up(), state
                .with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING))
                .with(OPEN, state.get(OPEN))
                .with(IS_TOP, true));
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
        Direction facing = state.get(HORIZONTAL_FACING);
        boolean open = state.get(OPEN);
        boolean top = state.get(IS_TOP);
        switch (facing)
        {
            case NORTH:
            default:
                shape = DOOR_CLOSE_Z;
                if (open)
                {
                    shape = (top) ? DOOR_NEGATIVE_Z : toTopShape(DOOR_NEGATIVE_Z);
                }
                break;
            case SOUTH:
                shape = DOOR_CLOSE_Z;
                if (open)
                {
                    shape = (top) ? DOOR_POSITIVE_Z : toTopShape(DOOR_POSITIVE_Z);
                }
                break;
            case WEST:
                shape = DOOR_CLOSE_X;
                if (open)
                {
                    shape = (top) ? DOOR_NEGATIVE_X : toTopShape(DOOR_NEGATIVE_X);
                }
                break;
            case EAST:
                shape = DOOR_CLOSE_X;
                if (open)
                {
                    shape = (top) ? DOOR_POSITIVE_X : toTopShape(DOOR_POSITIVE_X);
                }
                break;
        }
        switch (facing.getAxis())
        {
            case Z:
            default:
                shape = add(shape, FRAME_Z);
                if (top)
                {
                    shape = add(shape, FRAME_TOP_Z);
                }
                break;
            case X:
                shape = add(shape, FRAME_X);
                if (top)
                {
                    shape = add(shape, FRAME_TOP_X);
                }
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
        AxisAlignedBB aabb = shape.getBoundingBox();
        return Block.makeCuboidShape(aabb.minX, aabb.minY - 1, aabb.minZ, aabb.maxX, aabb.maxY - 1, aabb.maxZ);
    }

    private boolean checkValid(BlockPos pos, World world)
    {
        BlockState state = world.getBlockState(pos);
        BlockState state2;
        if (state.get(IS_TOP))
        {
            state2 = world.getBlockState(pos.down());
        }
        else
        {
            state2 = world.getBlockState(pos.up());
        }
        if (state2.getBlock() instanceof ElectricFenceGate)
        {
            if (state.get(IS_TOP) && state2.get(IS_TOP))
            {
                return false;
            }
            if (!state.get(IS_TOP) && !state2.get(IS_TOP))
            {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent("can be dismantled by wrench"));
    }
}
