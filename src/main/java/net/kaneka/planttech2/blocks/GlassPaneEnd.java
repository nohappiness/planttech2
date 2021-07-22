package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.colors.IColoredBlock;
import net.minecraft.level.level.block.AbstractBlock.Properties;
import net.minecraft.level.level.block.Block;
import net.minecraft.level.level.block.state.BlockState;
import net.minecraft.level.level.block.Blocks;
import net.minecraft.level.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.level.Ilevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import DirectionProperty;

public class GlassPaneEnd extends Block implements IColoredBlock
{
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
	private final int colorInt;  

	public GlassPaneEnd(Properties property, int color)
	{
		super(property.noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
		this.colorInt = color; 
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		for (Direction direction : context.getNearestLookingDirections())
		{
			BlockState blockstate;
			if (direction.getAxis() == Direction.Axis.Y)
				blockstate = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING).setValue(HORIZONTAL_FACING,
				        context.getHorizontalDirection());
			else
				blockstate = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(HORIZONTAL_FACING, direction);
			if (blockstate.canSurvive(context.getLevel(), context.getClickedPos()))
				return blockstate;
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, Ilevel levelIn, BlockPos currentPos, BlockPos facingPos)
	{
		return getFacing(stateIn).getOpposite() == facing && !stateIn.canSurvive(levelIn, currentPos) ? Blocks.AIR.defaultBlockState()
		        : super.updateShape(stateIn, facing, facingState, levelIn, currentPos, facingPos);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.setValue(HORIZONTAL_FACING, rot.rotate(state.getValue(HORIZONTAL_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING, FACE);
	}

	protected static Direction getFacing(BlockState state)
	{
		switch ((AttachFace) state.getValue(FACE))
		{
		case CEILING:
			return Direction.DOWN;
		case FLOOR:
			return Direction.UP;
		default:
			return state.getValue(HORIZONTAL_FACING);
		}
	}

	@Override
	public int getColor()
	{
		return colorInt;
	}
	
	@Override 
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) 
	{
	   return true;
	}
}
