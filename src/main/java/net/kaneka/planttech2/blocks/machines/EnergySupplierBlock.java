package net.kaneka.planttech2.blocks.machines;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;
import java.util.function.Supplier;

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
