package net.kaneka.planttech2.blocks;

import javax.annotation.Nullable;

import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.blocks.colors.IColoredBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlassPaneEnd extends BaseBlock implements IColoredBlock
{
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<AttachFace> FACE = BlockStateProperties.FACE;
	private final int colorInt;  

	public GlassPaneEnd(Properties property, String name, int color, ItemGroup group, boolean hasItem)
	{
		super(property, name, group, hasItem);
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(FACE, AttachFace.WALL));
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
			{
				blockstate = this.getDefaultState().with(FACE, direction == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING).with(HORIZONTAL_FACING,
				        context.getPlacementHorizontalFacing());
			} else
			{
				blockstate = this.getDefaultState().with(FACE, AttachFace.WALL).with(HORIZONTAL_FACING, direction);
			}

			if (blockstate.isValidPosition(context.getWorld(), context.getPos()))
			{
				return blockstate;
			}
		}

		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
	{
		return getFacing(stateIn).getOpposite() == facing && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState()
		        : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING, FACE);
	}

	protected static Direction getFacing(BlockState state)
	{
		switch ((AttachFace) state.get(FACE))
		{
		case CEILING:
			return Direction.DOWN;
		case FLOOR:
			return Direction.UP;
		default:
			return state.get(HORIZONTAL_FACING);
		}
	}

	@Override
	public int getColor()
	{
		return colorInt;
	}
	
	@Override 
	@OnlyIn(Dist.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) 
	{
	   return true;
	}
}
