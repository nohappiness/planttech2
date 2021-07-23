package net.kaneka.planttech2.blocks.colors;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class CustomColorHandler  implements IBlockColor
{
	

	@Override
	public int getColor(BlockState state, IBlockDisplayReader blockDisplayReader, BlockPos pos, int tintindex)
	{
		if (tintindex == 0)
		{
			if(state.getBlock() instanceof IColoredBlock)
			{
				return ((IColoredBlock) state.getBlock()).getColor(); 
			}
		}
		return 0xFFFFFFFF;
	}

}
