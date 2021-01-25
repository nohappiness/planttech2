package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.CustomFenceBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;

public class Hedge extends CustomFenceBlock
{
	private final Block leaves, wood, soil;

	public Hedge(Block leaves, Block wood, Block soil)
	{
		super(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid());
		this.leaves = leaves;
		this.wood = wood; 
		this.soil = soil; 
	}

	public Block getLeaves()
	{
		return leaves;
	}

	public Block getWood()
	{
		return wood;
	}
	
	public Block getSoil()
	{
		return soil;
	}

	public static class ColorHandler implements IBlockColor
	{
		private final Block leaves, soil;


		public ColorHandler(Block leaves, Block soil)
		{
			this.leaves = leaves;
			this.soil = soil; 
		}

		@Override
		public int getColor(BlockState state, IBlockDisplayReader reader, BlockPos pos, int tintindex)
		{
			if (tintindex == 0)
			{
				if (leaves == Blocks.OAK_LEAVES)
					return BiomeColors.getFoliageColor(reader, pos);
				if (leaves == Blocks.SPRUCE_LEAVES)
					return FoliageColors.getSpruce();
				if (leaves == Blocks.BIRCH_LEAVES)
					return FoliageColors.getBirch();
				if (leaves == Blocks.JUNGLE_LEAVES)
					return BiomeColors.getFoliageColor(reader, pos);
				if (leaves == Blocks.ACACIA_LEAVES)
					return BiomeColors.getFoliageColor(reader, pos);
				if (leaves == Blocks.DARK_OAK_LEAVES)
					return BiomeColors.getFoliageColor(reader, pos);

			}
			else if (tintindex == 1 && soil == Blocks.GRASS_BLOCK)
			{ 
				return reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : GrassColors.get(0.5D, 1.0D);
			}
			return 0xFFFFFFFF;
		}

	}

	public static class ColorHandlerItem implements IItemColor
	{
		private final Block leaves;

		public ColorHandlerItem(Block leaves)
		{
			this.leaves = leaves;
		}

		@Override
		public int getColor(ItemStack stack, int color)
		{
			if (leaves == Blocks.OAK_LEAVES)
				return FoliageColors.get(0.5, 0.5);
			if (leaves == Blocks.SPRUCE_LEAVES)
				return FoliageColors.getSpruce();
			if (leaves == Blocks.BIRCH_LEAVES)
				return FoliageColors.getBirch();
			if (leaves == Blocks.JUNGLE_LEAVES)
				return FoliageColors.get(0.5, 0.5);
			if (leaves == Blocks.ACACIA_LEAVES)
				return FoliageColors.get(0.5, 0.5);
			if (leaves == Blocks.DARK_OAK_LEAVES)
				return FoliageColors.get(0.5, 0.5);

			return 0xFFFFFFFF;
		}

	}

}
