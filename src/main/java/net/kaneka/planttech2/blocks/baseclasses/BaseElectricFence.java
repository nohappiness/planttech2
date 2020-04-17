package net.kaneka.planttech2.blocks.baseclasses;

import net.kaneka.planttech2.blocks.machines.EnergySupplierBlock;
import net.kaneka.planttech2.registries.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BaseElectricFence extends BaseBlock
{
    public static final IntegerProperty ELECTRIC_POWER = IntegerProperty.create("electric_power", 0, 15);
    public BaseElectricFence(Properties property, String name, ItemGroup group, boolean hasItem)
    {
        super(property.notSolid().lightValue(6), name, group, hasItem);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(ELECTRIC_POWER);
    }

    public int updatePower(World worldIn, BlockPos pos)
    {
        int Largestpower = 0;
        for (Direction direction : Direction.values())
        {
            BlockState state = worldIn.getBlockState(pos.offset(direction));
            Block block = state.getBlock();
            if (block instanceof EnergySupplierBlock && state.get(EnergySupplierBlock.SUPPLYING))
            {
                return 15;
            }
            if (block instanceof BaseElectricFence && state.has(ELECTRIC_POWER))
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
        if (rand.nextInt(300) == 1)
        {
            if (worldIn.isRemote)
            {
                doCollideAnimation(pos, worldIn, 1, ParticleTypes.CRIT, ModSounds.ELECTRIC_FENCE_IDLE, 0.05F, 1.0F);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent("can be dismantled by wrench, connect to a powered energy supplier or electric fence to activate"));
    }
}
