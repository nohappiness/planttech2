package net.kaneka.planttech2.blocks.machines;

import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.BlockPlaceContext;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.BlockEntity.BlockEntity;

import javax.annotation.Nullable;

public class EnergySupplierBlock extends MachineBaseBlock
{
    public static final BooleanProperty SUPPLYING = BooleanProperty.create("supplying");
    public EnergySupplierBlock(Supplier<? extends BlockEntity> teCreator, int tier)
    {
        super(teCreator, tier);
        registerDefaultState(defaultBlockState().setValue(SUPPLYING, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(SUPPLYING, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(SUPPLYING);
    }
}
