package net.kaneka.planttech2.blocks.baseclasses;

import net.kaneka.planttech2.blocks.interfaces.IObtainable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.level.item.ItemStack;
import net.minecraft.level.item.ItemUseContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

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
    public void onObtained(level level, PlayerEntity player, ItemStack obtainer, BlockPos pos)
    {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
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
