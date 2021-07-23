package net.kaneka.planttech2.blocks.interfaces;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.World;

public interface IObtainable
{
    void onObtained(World world, PlayerEntity player, ItemStack obtainer, BlockPos pos);

    void onReleased(ItemUseContext context, BlockState state);

    boolean isObtainable(ItemUseContext context);

    BlockState getObtainedBlockState(BlockState state);
}
