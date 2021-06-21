package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class InfusedIceBlock extends IceBlock
{
    public InfusedIceBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void playerDestroy(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        player.awardStat(Stats.BLOCK_MINED.get(this));
        player.causeFoodExhaustion(0.005F);
        dropResources(state, world, pos, tileEntity, player, stack);
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0)
        {
            if (world.dimensionType().ultraWarm())
            {
                world.removeBlock(pos, false);
                return;
            }
            Material material = world.getBlockState(pos.below()).getMaterial();
            if (material.blocksMotion() || material.isLiquid())
                world.setBlockAndUpdate(pos, ModBlocks.BIOMASSFLUIDBLOCK.defaultBlockState());
        }

    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand)
    {
        if (world.getBrightness(LightType.BLOCK, pos) > 11 - state.getLightBlock(world, pos))
            this.melt(state, world, pos);
    }

    @Override
    protected void melt(BlockState state, World world, BlockPos pos) {
        if (world.dimensionType().ultraWarm())
            world.removeBlock(pos, false);
        else
        {
            world.setBlockAndUpdate(pos, ModBlocks.BIOMASSFLUIDBLOCK.defaultBlockState());
            world.neighborChanged(pos, Blocks.WATER, pos);
        }
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.NORMAL;
    }
}
