package net.kaneka.planttech2.blocks.interfaces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IObtainable
{
    void onObtained(World world, PlayerEntity player, ItemStack obtainer, BlockPos pos);

    void onReleased(ItemUseContext context, BlockState state);

    boolean isObtainable(ItemUseContext context);

    Block getBlockObtained(ItemUseContext context);
}
