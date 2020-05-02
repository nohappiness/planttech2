package net.kaneka.planttech2.blocks.baseclasses;

import net.kaneka.planttech2.blocks.interfaces.IObtainable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ObtainableNaturalPlants extends NaturalPlants implements IObtainable
{
    public ObtainableNaturalPlants(String name, boolean hasItem, float width, float height)
    {
        super(name, hasItem, width, height);
    }

    public ObtainableNaturalPlants(String name, boolean hasItem)
    {
        super(name, hasItem);
    }

    @Override
    public void onObtained(World world, PlayerEntity player, ItemStack obtainer, BlockPos pos)
    {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    @Override
    public void onReleased(ItemUseContext context, Block block, String data)
    {
        context.getWorld().setBlockState(context.getPos().offset(context.getFace()), block.getDefaultState());
    }

    @Override
    public boolean isObtainable(ItemUseContext context)
    {
        return true;
    }

    @Override
    public Block getBlockObtained(ItemUseContext context)
    {
        return context.getWorld().getBlockState(context.getPos()).getBlock();
    }

    @Override
    public String transferStateForObtainer(BlockState state)
    {
        return "";
    }

    @Override
    public BlockState getStateForObtainer(String data)
    {
        return getDefaultState();
    }
}
