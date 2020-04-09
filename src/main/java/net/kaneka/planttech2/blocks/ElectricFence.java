package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.blocks.machines.EnergySupplierBlock;
import net.kaneka.planttech2.registries.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class ElectricFence extends BaseBlock
{
    public static final IntegerProperty ELECTRIC_POWER = IntegerProperty.create("electric_power", 0, 15);
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public boolean isTop;
    public static final VoxelShape TOP_X_AXIS = VoxelShapes.or(
            Block.makeCuboidShape(7.0D, 1.0D, 1.0D, 9.0D, 5.0D, 15.0D),
            Block.makeCuboidShape(6.0D, 5.0D, 1.0D, 10.0D, 7.0D, 15.0D),

            Block.makeCuboidShape(1.0D, 10.0D, 1.0D, 3.0D, 13.0D, 15.0D),
            Block.makeCuboidShape(3.0D, 7.0D, 1.0D, 6.0D, 10.0D, 15.0D),
            Block.makeCuboidShape(10.0D, 7.0D, 1.0D, 13.0D, 10.0D, 15.0D),
            Block.makeCuboidShape(13.0D, 10.0D, 1.0D, 15.0D, 13.0D, 15.0D));
    public static final VoxelShape TOP_Z_AXIS = VoxelShapes.or(
            Block.makeCuboidShape(1.0D, 1.0D, 7.0D, 15.0D, 5.0D, 9.0D),
            Block.makeCuboidShape(1.0D, 5.0D, 6.0D, 15.0D, 7.0D, 10.0D),

            Block.makeCuboidShape(1.0D, 10.0D, 1.0D, 15.0D, 13.0D, 3.0D),
            Block.makeCuboidShape(1.0D, 7.0D, 3.0D, 15.0D, 10.0D, 6.0D),
            Block.makeCuboidShape(1.0D, 7.0D, 10.0D, 15.0D, 10.0D, 13.0D),
            Block.makeCuboidShape(1.0D, 10.0D, 13.0D, 15.0D, 13.0D, 15.0D));
    public static final VoxelShape X_AXIS = VoxelShapes.or(
            Block.makeCuboidShape(7.0D, 1.0D, 1.0D, 9.0D, 15.0D, 15.0D));
    public static final VoxelShape Z_AXIS = VoxelShapes.or(
            Block.makeCuboidShape(1.0D, 1.0D, 7.0D, 15.0D, 15.0D, 9.0D));

    public ElectricFence(Properties property, String name, ItemGroup group, boolean hasItem, boolean isTop)
    {
        super(property.notSolid().lightValue(6), name, group, hasItem);
        setDefaultState(getDefaultState().with(ELECTRIC_POWER, 1).with(HORIZONTAL_FACING, Direction.NORTH));
        this.isTop = isTop;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
       builder.add(ELECTRIC_POWER, HORIZONTAL_FACING);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return this.getDefaultState()
                .with(HORIZONTAL_FACING, stateIn.get(HORIZONTAL_FACING))
                .with(ELECTRIC_POWER, updatePower((World) worldIn, currentPos));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState()
                .with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing())
                .with(ELECTRIC_POWER, updatePower(context.getWorld(), context.getPos()));
    }

    public int updatePower(World worldIn, BlockPos pos)
    {
        int Largestpower = 0;
        for (Direction direction : Direction.values())
        {
            BlockState state = worldIn.getBlockState(pos.offset(direction));
            if (state.getBlock() instanceof EnergySupplierBlock && state.get(EnergySupplierBlock.SUPPLYING))
            {
                return 15;
            }
            if (state.getBlock() instanceof ElectricFence && state.has(ELECTRIC_POWER))
            {
                int power = state.get(ELECTRIC_POWER);
                if (power > Largestpower)
                {
                    Largestpower = power - 1;
                }
            }
        }
        return Largestpower;
    }

    @SuppressWarnings("deprecation")
	@Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        switch (state.get(HORIZONTAL_FACING).getAxis())
        {
            case X:
                if (isTop)
                {
                    return TOP_X_AXIS;
                }
                return X_AXIS;
            case Z:
                if (isTop)
                {
                    return TOP_Z_AXIS;
                }
                return Z_AXIS;
            default:
                return super.getShape(state, worldIn, pos, context);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return getShape(state, worldIn, pos, context);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if (state.get(ELECTRIC_POWER) > 0)
        {
            if (entityIn instanceof LivingEntity)
            {
                if (worldIn.isRaining() && worldIn.canSeeSky(pos))
                {
                    entityIn.attackEntityFrom(DamageSource.SWEET_BERRY_BUSH, 5.0F);
                    if (worldIn.isRemote)
                    {
                        doCollideAnimation(pos, worldIn, 1, ParticleTypes.LARGE_SMOKE, SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.75F, 50F);
                    }
                }
                else
                {
                    entityIn.attackEntityFrom(DamageSource.SWEET_BERRY_BUSH, 2.5F);
                    if (worldIn.isRemote)
                    {
                        doCollideAnimation(pos, worldIn, 1, ParticleTypes.SMOKE, SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.55F, 20F);
                    }
                }
            }
            else
            {
                entityIn.remove();
                if (worldIn.isRemote)
                {
                    doCollideAnimation(pos, worldIn, 7, ParticleTypes.SMOKE, SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.8F, 20F);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void doCollideAnimation(BlockPos pos, World worldIn, int amount, BasicParticleType particle, SoundEvent sound, float volume, float pitch)
    {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        Random random = new Random();
//        worldIn.playSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, volume, pitch, false);
        worldIn.playSound(x + 0.5, y + 0.5, z + 0.5, sound, SoundCategory.BLOCKS, volume, pitch, false);
        for (int i = 0; i < amount; i++)
        {
            worldIn.addParticle(particle, x + random.nextFloat(), y + random.nextFloat(), z + random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (stateIn.get(ELECTRIC_POWER) == 0)
        {
            return;
        }
        if (rand.nextInt(250) == 1)
        {
            if (worldIn.isRemote)
            {
                doCollideAnimation(pos, worldIn, 1, ParticleTypes.CRIT, ModSounds.ELECTRIC_FENCE_IDLE, 0.15F, 100F);
            }
        }
    }
}
