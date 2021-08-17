package net.kaneka.planttech2.blocks.baseclasses;

import net.kaneka.planttech2.blocks.machines.EnergySupplierBlock;
import net.kaneka.planttech2.registries.ModDamageSources;
import net.kaneka.planttech2.registries.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BaseElectricFence extends Block
{
    public static final IntegerProperty ELECTRIC_POWER = IntegerProperty.create("electric_power", 0, 15);
    public BaseElectricFence(Properties property)
	{
		super(property.noOcclusion().lightLevel((p) -> 6));
	}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(ELECTRIC_POWER);
    }

    public static int calculatePower(Level levelIn, BlockPos pos)
    {
        int fencePower = 0;
        for (Direction direction : Direction.values())
        {
            BlockState state = levelIn.getBlockState(pos.relative(direction));
            Block block = state.getBlock();
            if (block instanceof EnergySupplierBlock && state.getValue(EnergySupplierBlock.SUPPLYING))
                return 15;
            if (block instanceof BaseElectricFence && state.hasProperty(ELECTRIC_POWER))
            {
                int power = state.getValue(ELECTRIC_POWER);
                if (power > fencePower)
                    fencePower = power - 1;
            }
        }
        return fencePower;
    }

    @Override
    public void entityInside(BlockState state, Level levelIn, BlockPos pos, Entity entityIn)
    {
        if (state.getValue(ELECTRIC_POWER) > 0 && levelIn.getGameTime() % 8L == 0L)
        {
            if (entityIn instanceof LivingEntity)
            {
                if (levelIn.isRaining() && levelIn.canSeeSky(pos))
                {
                    entityIn.hurt(ModDamageSources.ELECTRIC_FENCE, 5.0F);
                    if (levelIn.isClientSide)
                        doCollideAnimation(pos, levelIn, 1, ParticleTypes.LARGE_SMOKE, SoundEvents.FIRE_EXTINGUISH, 0.75F, 50F);
                }
                else
                {
                    entityIn.hurt(ModDamageSources.ELECTRIC_FENCE, 2.5F);
                    if (levelIn.isClientSide)
                        doCollideAnimation(pos, levelIn, 1, ParticleTypes.SMOKE, SoundEvents.FIRE_EXTINGUISH, 0.55F, 20F);
                }
            }
            else
            {
                entityIn.remove(Entity.RemovalReason.DISCARDED);
                if (levelIn.isClientSide)
                    doCollideAnimation(pos, levelIn, 7, ParticleTypes.SMOKE, SoundEvents.FIRE_EXTINGUISH, 0.8F, 20F);
            }
        }
    }

	private void doCollideAnimation(BlockPos pos, Level levelIn, int amount, SimpleParticleType particle, SoundEvent sound, float volume, float pitch)
    {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        Random random = new Random();
        // levelIn.playSound(x + 0.5, y + 0.5, z + 0.5, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, volume, pitch, false);
        levelIn.playLocalSound(x + 0.5, y + 0.5, z + 0.5, sound, SoundSource.BLOCKS, volume, pitch, false);
        for (int i = 0; i < amount; i++)
            levelIn.addParticle(particle, x + random.nextFloat(), y + random.nextFloat(), z + random.nextFloat(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void animateTick(BlockState stateIn, Level levelIn, BlockPos pos, Random rand)
    {
        if (stateIn.getValue(ELECTRIC_POWER) > 0 && rand.nextInt(350) == 1)
            if (levelIn.isClientSide)
                doCollideAnimation(pos, levelIn, 1, ParticleTypes.CRIT, ModSounds.ELECTRIC_FENCE_IDLE, 0.05F, 1.0F);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter levelIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(new TextComponent("can be dismantled by wrench, connect to a powered energy supplier or electric fence to activate"));
    }
}
