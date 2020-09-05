package net.kaneka.planttech2.blocks.machines;

import net.minecraft.block.Block;

public class MachineTeleporterEndBlock extends Block
{

	public MachineTeleporterEndBlock(Block.Properties builder)
	{
		super(builder);
	}

	/*
	@Override
	public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult rts)
	{
		
		if (!worldIn.isRemote)
		{
			//from PlantTopia
			if (worldIn.getDimension().getType() == ModDimensionPlantTopia.getDimensionType())
			{
				TeleporterUtilities.changeDimension(worldIn, pos, playerIn, DimensionType.OVERWORLD, ModBlocks.PLANTTOPIA_TELEPORTER_END, ModBlocks.PLANTTOPIA_TELEPORTER);
			}
			
			//else if(worldIn.getDimension().getType() == DimensionType.OVERWORLD)//to PlantTopia
			//{
			//	TeleporterUtilities.changeDimension(worldIn, pos, playerIn, ModDimensionPlantTopia.getDimensionType(), ModBlocks.PLANTTOPIA_TELEPORTER_END);
			//}
			
			

			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.FAIL;
	}
	*/
}
