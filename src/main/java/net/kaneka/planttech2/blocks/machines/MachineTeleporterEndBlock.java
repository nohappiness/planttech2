package net.kaneka.planttech2.blocks.machines;

import net.minecraft.world.level.block.Block;

public class MachineTeleporterEndBlock extends Block
{

	public MachineTeleporterEndBlock(Block.Properties builder)
	{
		super(builder);
	}

	/*
	@Override
	public InteractionResult onBlockActivated(BlockState state, Level level, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockHitResult rts)
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
			
			

			return InteractionResult.SUCCESS;
		}
		
		return InteractionResult.FAIL;
	}
	*/
}
