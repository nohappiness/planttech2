package net.kaneka.planttech2.blocks.machines;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class EnergySupplierBlock extends MachineBaseBlock
{
    public static final BooleanProperty SUPPLYING = BooleanProperty.create("supplying");
    public EnergySupplierBlock(Supplier<? extends TileEntity> teCreator, int tier)
    {
        super(teCreator, tier);
        registerDefaultState(defaultBlockState().setValue(SUPPLYING, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return defaultBlockState().setValue(SUPPLYING, false);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(SUPPLYING);
    }
}
