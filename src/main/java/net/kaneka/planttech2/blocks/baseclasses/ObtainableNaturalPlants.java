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
    public ObtainableNaturalPlants(float width, float height)
    {
        super(width, height);
    }

    public ObtainableNaturalPlants()
    {
        super();
    }

    @Override
    public void onObtained(World world, PlayerEntity player, ItemStack obtainer, BlockPos pos)
    {
        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }

    @Override
    public void onReleased(ItemUseContext context, BlockState state)
    {
        context.getLevel().setBlockAndUpdate(context.getClickedPos().relative(context.getClickedFace()), state);
    }

    @Override
    public boolean isObtainable(ItemUseContext context)
    {
        return true;
    }

    @Override
    public BlockState getObtainedBlockState(BlockState state)
    {
        return state;
    }
}
