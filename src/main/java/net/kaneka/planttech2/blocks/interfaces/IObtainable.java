package net.kaneka.planttech2.blocks.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IObtainable
{
    void onObtained(Level level, Player player, ItemStack obtainer, BlockPos pos);

    void onReleased(UseOnContext context, BlockState state);

    boolean isObtainable(UseOnContext context);

    BlockState getObtainedBlockState(BlockState state);
}
