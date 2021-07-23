package net.kaneka.planttech2.blocks;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ModBlockEntityBlock extends BaseEntityBlock
{
    protected ModBlockEntityBlock(Properties property)
    {
        super(property);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

}
