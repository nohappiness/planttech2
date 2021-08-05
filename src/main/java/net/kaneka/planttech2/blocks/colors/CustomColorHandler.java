package net.kaneka.planttech2.blocks.colors;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class CustomColorHandler  implements BlockColor
{
	@Override
	public int getColor(BlockState state, BlockAndTintGetter blockDisplayReader, BlockPos pos, int tintindex)
	{
		if (tintindex == 0)
		{
			if(state.getBlock() instanceof IColoredBlock color)
			{
				return color.getColor();
			}
		}
		return 0xFFFFFFFF;
	}

}
