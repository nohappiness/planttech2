package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FacingWallLightBase extends BaseBlock
{
    public static final DirectionProperty HORIZONTAL_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    //5 -> not damaged, 4-1 -> flashing (broke), 0 -> completely broken
    public static final IntegerProperty LIGHT_STATUS = IntegerProperty.create("status", 0, 5);
    public static final BooleanProperty IS_ON = BooleanProperty.create("is_on");

    public FacingWallLightBase(Properties property, String name, ItemGroup group, boolean hasItem)
    {
        super(property.notSolid(), name, group, hasItem);
        setDefaultState(getDefaultState()
                .with(HORIZONTAL_FACING, Direction.NORTH)
                .with(LIGHT_STATUS, 5)
                .with(IS_ON, true));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, LIGHT_STATUS, IS_ON);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState()
                .with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing())
                .with(LIGHT_STATUS, 5)
                .with(IS_ON, true);
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        int status = state.get(LIGHT_STATUS);
        if (status < 5 && status > 0)
        {
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, new Random().nextInt(20), TickPriority.VERY_LOW);
        }
    }

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, Entity projectile)
    {
        if (state.get(LIGHT_STATUS) != 0)
        {
            /*if (!projectile.removed && projectile.isAlive())
            {
                projectile.remove();
            }*/
            worldIn.playSound((PlayerEntity) null, hit.getPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 0.8F, 1.0F);
            worldIn.setBlockState(hit.getPos(), getDefaultState()
                    .with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING))
                    .with(LIGHT_STATUS, state.get(LIGHT_STATUS) - 1)
                    .with(IS_ON, state.get(IS_ON)));
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        int status = state.get(LIGHT_STATUS);
        if (status < 5 && status > 0)
        {
            worldIn.setBlockState(pos, getDefaultState()
                    .with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING))
                    .with(LIGHT_STATUS, state.get(LIGHT_STATUS))
                    .with(IS_ON, !state.get(IS_ON)));
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, rand.nextInt(200), TickPriority.VERY_LOW);
        }
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos)
    {
        return state.get(IS_ON) ? state.get(LIGHT_STATUS) * 3 : 0;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return getShape(state, worldIn, pos, context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return state.get(LIGHT_STATUS) == 0 ? Collections.emptyList() : super.getDrops(state, builder);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.toRotation(state.get(HORIZONTAL_FACING)));
    }

}
