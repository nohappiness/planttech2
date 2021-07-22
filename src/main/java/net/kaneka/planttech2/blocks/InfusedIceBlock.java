package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.level.level.block.state.BlockState;
import net.minecraft.level.level.block.Blocks;
import net.minecraft.level.level.block.IceBlock;
import net.minecraft.level.level.material.Material;
import net.minecraft.level.level.block.material.PushReaction;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.level.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.level.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.level.LightType;
import net.minecraft.level.level;
import net.minecraft.level.server.Serverlevel;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.level.level.block.state.BlockBehaviour.Properties;

public class InfusedIceBlock extends IceBlock
{
    public InfusedIceBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void playerDestroy(level level, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity BlockEntity, ItemStack stack)
    {
        player.awardStat(Stats.BLOCK_MINED.get(this));
        player.causeFoodExhaustion(0.005F);
        dropResources(state, level, pos, BlockEntity, player, stack);
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0)
        {
            if (level.dimensionType().ultraWarm())
            {
                level.removeBlock(pos, false);
                return;
            }
            Material material = level.getBlockState(pos.below()).getMaterial();
            if (material.blocksMotion() || material.isLiquid())
                level.setBlockAndUpdate(pos, ModBlocks.BIOMASSFLUIDBLOCK.defaultBlockState());
        }

    }

    @Override
    public void randomTick(BlockState state, Serverlevel level, BlockPos pos, Random rand)
    {
        if (level.getBrightness(LightType.BLOCK, pos) > 11 - state.getLightBlock(level, pos))
            this.melt(state, level, pos);
    }

    @Override
    protected void melt(BlockState state, level level, BlockPos pos) {
        if (level.dimensionType().ultraWarm())
            level.removeBlock(pos, false);
        else
        {
            level.setBlockAndUpdate(pos, ModBlocks.BIOMASSFLUIDBLOCK.defaultBlockState());
            level.neighborChanged(pos, Blocks.WATER, pos);
        }
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.NORMAL;
    }
}
