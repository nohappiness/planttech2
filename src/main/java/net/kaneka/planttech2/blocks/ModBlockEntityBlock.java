package net.kaneka.planttech2.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ModBlockEntityBlock extends Block implements EntityBlock
{
    protected ModBlockEntityBlock(BlockBehaviour.Properties property)
    {
        super(property);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

}
