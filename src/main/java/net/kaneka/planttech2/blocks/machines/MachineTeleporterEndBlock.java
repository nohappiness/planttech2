package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class MachineTeleporterEndBlock extends BaseBlock
{

	public MachineTeleporterEndBlock(String name, Block.Properties builder, ItemGroup tab)
	{
		super(builder, name, tab, true);
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
