package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nullable;
import java.util.Random;

public class InfusedIceBlock extends IceBlock
{
    public InfusedIceBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity BlockEntity, ItemStack stack)
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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random rand)
    {
        if (level.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(level, pos))
            this.melt(state, level, pos);
    }

    @Override
    protected void melt(BlockState state, Level level, BlockPos pos) {
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
