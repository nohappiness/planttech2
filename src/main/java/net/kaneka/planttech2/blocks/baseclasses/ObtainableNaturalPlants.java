package net.kaneka.planttech2.blocks.baseclasses;

import net.kaneka.planttech2.blocks.interfaces.IObtainable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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
    public void onObtained(Level level, Player player, ItemStack obtainer, BlockPos pos)
    {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }

    @Override
    public void onReleased(UseOnContext context, BlockState state)
    {
        context.getLevel().setBlockAndUpdate(context.getClickedPos().relative(context.getClickedFace()), state);
    }

    @Override
    public boolean isObtainable(UseOnContext context)
    {
        return true;
    }

    @Override
    public BlockState getObtainedBlockState(BlockState state)
    {
        return state;
    }
}
