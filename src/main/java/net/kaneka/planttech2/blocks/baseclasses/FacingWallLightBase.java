package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
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
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.loot.LootContext;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class FacingWallLightBase extends Block
{
    public static final DirectionProperty HORIZONTAL_FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    //5 -> not damaged, 4-1 -> flashing (broke), 0 -> completely broken
    public static final IntegerProperty LIGHT_STATUS = IntegerProperty.create("status", 0, 5);
    public static final BooleanProperty IS_ON = BooleanProperty.create("is_on");

    public FacingWallLightBase(Properties property)
    {
        super(property.noOcclusion());
        registerDefaultState(defaultBlockState()
                .setValue(HORIZONTAL_FACING, Direction.NORTH)
                .setValue(LIGHT_STATUS, 5)
                .setValue(IS_ON, true));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, LIGHT_STATUS, IS_ON);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return defaultBlockState()
                .setValue(HORIZONTAL_FACING, context.getHorizontalDirection())
                .setValue(LIGHT_STATUS, 5)
                .setValue(IS_ON, true);
    }

    @Override
    public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        int status = state.getValue(LIGHT_STATUS);
        if (status < 5 && status > 0)
        {
            worldIn.getBlockTicks().scheduleTick(pos, this, new Random().nextInt(20), TickPriority.VERY_LOW);
        }
    }
    
    @Override
    public void onProjectileHit(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile)
    {
        if (state.getValue(LIGHT_STATUS) != 0)
        {
            /*if (!projectile.removed && projectile.isAlive())
            {
                projectile.remove();
            }*/
            worldIn.playSound((PlayerEntity) null, hit.getBlockPos(), SoundEvents.GLASS_BREAK, SoundCategory.BLOCKS, 0.8F, 1.0F);
            worldIn.setBlockAndUpdate(hit.getBlockPos(), defaultBlockState()
                    .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                    .setValue(LIGHT_STATUS, state.getValue(LIGHT_STATUS) - 1)
                    .setValue(IS_ON, state.getValue(IS_ON)));
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        int status = state.getValue(LIGHT_STATUS);
        if (status < 5 && status > 0)
        {
            worldIn.setBlockAndUpdate(pos, defaultBlockState()
                    .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                    .setValue(LIGHT_STATUS, state.getValue(LIGHT_STATUS))
                    .setValue(IS_ON, !state.getValue(IS_ON)));
            worldIn.getBlockTicks().scheduleTick(pos, this, rand.nextInt(200), TickPriority.VERY_LOW);
        }
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos)
    {
        return state.getValue(IS_ON) ? state.getValue(LIGHT_STATUS) * 3 : 0;
    }

    @SuppressWarnings("deprecation")
	@Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return getShape(state, worldIn, pos, context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return state.getValue(LIGHT_STATUS) == 0 ? Collections.emptyList() : super.getDrops(state, builder);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

}
