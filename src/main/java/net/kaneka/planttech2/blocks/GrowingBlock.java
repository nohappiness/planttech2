package net.kaneka.planttech2.blocks;

import com.google.common.collect.Lists;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class GrowingBlock extends Block
{
	public static final IntegerProperty GROWINGSTATE = IntegerProperty.create("growingstate", 0, 6);
	protected final Supplier<Block> blockSupplier;
	protected boolean growAlone; 
	
	public GrowingBlock(Supplier<Block> blockSupplier, boolean growAlone)
	{
		super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(0.9F).randomTicks());
		this.blockSupplier = blockSupplier;
		this.growAlone = growAlone; 
		this.registerDefaultState(this.stateDefinition.any().setValue(GROWINGSTATE, 0));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand)
	{
		if (growAlone)
    		grow(state, world, pos);
	}
	
	public void grow(BlockState state, ServerWorld world, BlockPos pos)
	{
		int i = state.getValue(GROWINGSTATE);
		if (i < 6)
			world.setBlockAndUpdate(pos, state.setValue(GROWINGSTATE, i + 1));
		else
			placeBlock(world, pos, state);
	}
	
	protected void placeBlock(ServerWorld world, BlockPos pos, BlockState state)
	{
		world.setBlockAndUpdate(pos, getBlock().defaultBlockState());
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) 
	{
		builder.add(GROWINGSTATE);
	}
	
	public Block getBlock()
	{
		return blockSupplier.get();
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder)
	{
		List<ItemStack> drops = Lists.newArrayList();
		Block block = state.getBlock();
		if (block == ModBlocks.COMPRESSOR_GROWING || block == ModBlocks.MACHINEBULBREPROCESSOR_GROWING || block == ModBlocks.SEEDSQUEEZER_GROWING || block == ModBlocks.INFUSER_GROWING || block == ModBlocks.IDENTIFIER_GROWING)
			drops.add(new ItemStack(ModBlocks.MACHINESHELL_IRON));
		else if (block == ModBlocks.MACHINESHELL_IRON_GROWING)
			drops.add(new ItemStack(Blocks.IRON_BLOCK));
		else if (block == ModBlocks.MACHINESHELL_PLANTIUM_GROWING)
			drops.add(new ItemStack(ModBlocks.PLANTIUM_BLOCK));
		else
			drops.add(new ItemStack(ModBlocks.MACHINESHELL_PLANTIUM));
		return drops;
	}

}
