package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, LIGHT_STATUS, IS_ON);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState()
                .setValue(HORIZONTAL_FACING, context.getHorizontalDirection())
                .setValue(LIGHT_STATUS, 5)
                .setValue(IS_ON, true);
    }

    @Override
    public void onPlace(BlockState state, Level levelIn, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        int status = state.getValue(LIGHT_STATUS);
        if (status < 5 && status > 0)
        {
            levelIn.getBlockTicks().scheduleTick(pos, this, new Random().nextInt(20), TickPriority.VERY_LOW);
        }
    }
    
    @Override
    public void onProjectileHit(Level levelIn, BlockState state, BlockHitResult hit, Projectile projectile)
    {
        if (state.getValue(LIGHT_STATUS) != 0)
        {
            /*if (!projectile.removed && projectile.isAlive())
            {
                projectile.remove();
            }*/
            levelIn.playSound((PlayerEntity) null, hit.getBlockPos(), SoundEvents.GLASS_BREAK, SoundCategory.BLOCKS, 0.8F, 1.0F);
            levelIn.setBlockAndUpdate(hit.getBlockPos(), defaultBlockState()
                    .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                    .setValue(LIGHT_STATUS, state.getValue(LIGHT_STATUS) - 1)
                    .setValue(IS_ON, state.getValue(IS_ON)));
        }
    }

    @Override
    public void tick(BlockState state, Serverlevel levelIn, BlockPos pos, Random rand)
    {
        int status = state.getValue(LIGHT_STATUS);
        if (status < 5 && status > 0)
        {
            levelIn.setBlockAndUpdate(pos, defaultBlockState()
                    .setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING))
                    .setValue(LIGHT_STATUS, state.getValue(LIGHT_STATUS))
                    .setValue(IS_ON, !state.getValue(IS_ON)));
            levelIn.getBlockTicks().scheduleTick(pos, this, rand.nextInt(200), TickPriority.VERY_LOW);
        }
    }

    @Override
    public int getLightValue(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.getValue(IS_ON) ? state.getValue(LIGHT_STATUS) * 3 : 0;
    }

    @SuppressWarnings("deprecation")
	@Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context)
    {
        return getShape(state, levelIn, pos, context);
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
