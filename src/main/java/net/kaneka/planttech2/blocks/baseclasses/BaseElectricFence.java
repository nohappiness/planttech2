package net.kaneka.planttech2.blocks.baseclasses;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import net.kaneka.planttech2.blocks.machines.EnergySupplierBlock;
import net.kaneka.planttech2.registries.ModDamageSources;
import net.kaneka.planttech2.registries.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class BaseElectricFence extends Block
{
    public static final IntegerProperty ELECTRIC_POWER = IntegerProperty.create("electric_power", 0, 15);
    public BaseElectricFence(Properties property)
	{
		super(property.notSolid().setLightLevel((p) -> 6));
	}

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(ELECTRIC_POWER);
    }

    protected int calculatePower(World worldIn, BlockPos pos)
    {
        int fencePower = 0;
        for (Direction direction : Direction.values())
        {
            BlockState state = worldIn.getBlockState(pos.offset(direction));
            Block block = state.getBlock();
            if (block instanceof EnergySupplierBlock && state.get(EnergySupplierBlock.SUPPLYING))
                return 15;
            if (block instanceof BaseElectricFence && state.hasProperty(ELECTRIC_POWER))
            {
                int power = state.get(ELECTRIC_POWER);
                if (power > fencePower)
                    fencePower = power - 1;
            }
        }
        return fencePower;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if (state.get(ELECTRIC_POWER) > 0 && worldIn.getGameTime() % 4L == 0L)
        {
            if (entityIn instanceof LivingEntity)
            {
                if (worldIn.isRaining() && worldIn.canSeeSky(pos))
                {
                    entityIn.attackEntityFrom(ModDamageSources.ELECTRIC_FENCE, 5.0F);
                    if (worldIn.isRemote)
                        doCollideAnimation(pos, worldIn, 1, ParticleTypes.LARGE_SMOKE, SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.75F, 50F);
                }
                else
                {
                    entityIn.attackEntityFrom(ModDamageSources.ELECTRIC_FENCE, 2.5F);
                    if (worldIn.isRemote)
                        doCollideAnimation(pos, worldIn, 1, ParticleTypes.SMOKE, SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.55F, 20F);
                }
            }
            else
            {
                entityIn.remove();
                if (worldIn.isRemote)
                    doCollideAnimation(pos, worldIn, 7, ParticleTypes.SMOKE, SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.8F, 20F);
            }
        }
    }

	private void doCollideAnimation(BlockPos pos, World worldIn, int amount, BasicParticleType particle, SoundEvent sound, float volume, float pitch)
    {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        Random random = new Random();
        // worldIn.playSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, volume, pitch, false);
        worldIn.playSound(x + 0.5, y + 0.5, z + 0.5, sound, SoundCategory.BLOCKS, volume, pitch, false);
        for (int i = 0; i < amount; i++)
            worldIn.addParticle(particle, x + random.nextFloat(), y + random.nextFloat(), z + random.nextFloat(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (stateIn.get(ELECTRIC_POWER) > 0 && rand.nextInt(325) == 1)
            if (worldIn.isRemote)
                doCollideAnimation(pos, worldIn, 1, ParticleTypes.CRIT, ModSounds.ELECTRIC_FENCE_IDLE, 0.05F, 1.0F);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new StringTextComponent("can be dismantled by wrench, connect to a powered energy supplier or electric fence to activate"));
    }
}
