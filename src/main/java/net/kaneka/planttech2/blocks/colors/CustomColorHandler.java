package net.kaneka.planttech2.blocks.colors;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

public class CustomColorHandler  implements IBlockColor
{
	

	@Override
	public int getColor(BlockState state, ILightReader lightreader, BlockPos pos, int tintindex)
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
