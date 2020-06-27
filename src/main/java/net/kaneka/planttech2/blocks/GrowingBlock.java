package net.kaneka.planttech2.blocks;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.loot.LootContext.Builder;

public class GrowingBlock extends BaseBlock
{
	public static final IntegerProperty GROWINGSTATE = IntegerProperty.create("growingstate", 0, 6);
	protected final Block block; 
	protected boolean growAlone; 
	
	public GrowingBlock(String name, Block block, boolean growAlone)
	{
		super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F).tickRandomly(), name, ModCreativeTabs.groupmain, false, true);
		this.block = block; 
		this.growAlone = growAlone; 
		this.setDefaultState(this.stateContainer.getBaseState().with(GROWINGSTATE, Integer.valueOf(0)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand)
	{
		super.tick(state, world, pos, rand);
		if(growAlone) 
		{
    		grow(state, world, pos);
		}
	}
	
	public void grow(BlockState state, ServerWorld world, BlockPos pos)
	{
		int i = state.get(GROWINGSTATE).intValue();
		if(i < 6)
		{
			world.setBlockState(pos, state.with(GROWINGSTATE, i + 1), 2);
		}
		
		if(i >= 6)
		{
			placeBlock(world, pos, state);
		}
	}
	
	protected void placeBlock(ServerWorld world, BlockPos pos, BlockState state)
	{
		world.setBlockState(pos, block.getDefaultState());
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) 
	{
		builder.add(GROWINGSTATE);
	}
	
	public Block getBlock()
	{
		return block; 
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder)
	{
		List<ItemStack> drops = Lists.newArrayList();
		Block block = state.getBlock();
		if(block == ModBlocks.COMPRESSOR_GROWING || block == ModBlocks.MACHINEBULBREPROCESSOR_GROWING || block == ModBlocks.SEEDSQUEEZER_GROWING || block == ModBlocks.INFUSER_GROWING || block == ModBlocks.IDENTIFIER_GROWING)
		{
			drops.add(new ItemStack(ModBlocks.MACHINESHELL_IRON));
		}
		else if(block == ModBlocks.MACHINESHELL_IRON_GROWING)
		{
			drops.add(new ItemStack(Blocks.IRON_BLOCK));
		}
		else if(block == ModBlocks.MACHINESHELL_PLANTIUM_GROWING)
		{
			drops.add(new ItemStack(ModBlocks.PLANTIUM_BLOCK));
		}
		else
		{
			drops.add(new ItemStack(ModBlocks.MACHINESHELL_PLANTIUM)); 
		}
		return drops;
	}

}
