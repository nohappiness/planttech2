package net.kaneka.planttech2.blocks;

import net.kaneka.planttech2.blocks.baseclasses.CustomFenceBlock;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.List;

public class Hedge extends CustomFenceBlock
{
	private final Block leaves, wood, soil;

	public Hedge(Block leaves, Block wood, Block soil)
	{
		super(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion());
		this.leaves = leaves;
		this.wood = wood; 
		this.soil = soil;
		this.collisionShapes = this.makeShapes(3.0F, 3.0F, 24.0F, 0.0F, 24.0F);
		this.shapes = this.makeShapes(3.0F, 3.0F, 16.0F, 0.0F, 16.0F);
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
	
	@Override
	public void appendHoverText(ItemStack stack, BlockGetter levelIn, List<Component> tooltip, TooltipFlag flagIn)
	{
		tooltip.add(new TextComponent("Log: " + getWood().getName().getString()));
		tooltip.add(new TextComponent("Leaf: " + getLeaves().getName().getString()));
		tooltip.add(new TextComponent("Soil: " + getSoil().getName().getString()));
	}



	public static class ColorHandler implements BlockColor
	{
		private final Block leaves, soil;


		public ColorHandler(Block leaves, Block soil)
		{
			this.leaves = leaves;
			this.soil = soil; 
		}

		@Override
		public int getColor(BlockState state, BlockAndTintGetter reader, BlockPos pos, int tintindex)
		{
			if (tintindex == 0)
			{
				if (leaves == Blocks.OAK_LEAVES)
					return BiomeColors.getAverageFoliageColor(reader, pos);
				if (leaves == Blocks.SPRUCE_LEAVES)
					return FoliageColor.getEvergreenColor();
				if (leaves == Blocks.BIRCH_LEAVES)
					return FoliageColor.getBirchColor();
				if (leaves == Blocks.JUNGLE_LEAVES)
					return BiomeColors.getAverageFoliageColor(reader, pos);
				if (leaves == Blocks.ACACIA_LEAVES)
					return BiomeColors.getAverageFoliageColor(reader, pos);
				if (leaves == Blocks.DARK_OAK_LEAVES)
					return BiomeColors.getAverageFoliageColor(reader, pos);

			}
			else if (tintindex == 1 && soil == Blocks.GRASS_BLOCK)
			{ 
				return reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColor.get(0.5D, 1.0D);
			}
			return 0xFFFFFFFF;
		}

	}



	public static class ColorHandlerItem implements ItemColor
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
				return FoliageColor.get(0.5, 0.5);
			if (leaves == Blocks.SPRUCE_LEAVES)
				return FoliageColor.getEvergreenColor();
			if (leaves == Blocks.BIRCH_LEAVES)
				return FoliageColor.getBirchColor();
			if (leaves == Blocks.JUNGLE_LEAVES)
				return FoliageColor.get(0.5, 0.5);
			if (leaves == Blocks.ACACIA_LEAVES)
				return FoliageColor.get(0.5, 0.5);
			if (leaves == Blocks.DARK_OAK_LEAVES)
				return FoliageColor.get(0.5, 0.5);

			return 0xFFFFFFFF;
		}

	}

}
