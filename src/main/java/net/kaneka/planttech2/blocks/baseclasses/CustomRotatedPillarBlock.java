package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.level.level.block.AbstractBlock.Properties;
import net.minecraft.level.level.block.Block;
import net.minecraft.level.level.block.state.BlockState;
import net.minecraft.level.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.util.Rotation;

import EnumProperty;

public class CustomRotatedPillarBlock extends Block
{
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	public CustomRotatedPillarBlock(Properties property)
	{
		super(property);
		this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		switch (rot)
		{
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:
			switch ((Direction.Axis) state.getValue(AXIS))
			{
			case X:
				return state.setValue(AXIS, Direction.Axis.Z);
			case Z:
				return state.setValue(AXIS, Direction.Axis.X);
			default:
				return state;
			}
		default:
			return state;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(AXIS);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
	}
}
