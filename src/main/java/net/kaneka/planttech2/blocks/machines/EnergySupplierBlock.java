package net.kaneka.planttech2.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;

import javax.annotation.Nullable;

public class EnergySupplierBlock extends MachineBaseBlock
{
    public static final BooleanProperty SUPPLYING = BooleanProperty.create("supplying");
    public EnergySupplierBlock(String name, ItemGroup group)
    {
        super(name, group);
        setDefaultState(getDefaultState().with(SUPPLYING, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(SUPPLYING, false);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(SUPPLYING);
    }
}
