package net.kaneka.planttech2.blocks.baseclasses;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

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
				return switch (state.getValue(AXIS))
						{
							case X -> state.setValue(AXIS, Direction.Axis.Z);
							case Z -> state.setValue(AXIS, Direction.Axis.X);
							default -> state;
						};
			default:
				return state;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(AXIS);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
	}
}
