package net.kaneka.planttech2.blocks.machines;

import java.util.List;

import net.kaneka.planttech2.blocks.baseclasses.BaseBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.machine.*;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MachineBaseBlock extends BaseBlock
{

	public MachineBaseBlock(String name, ItemGroup group)
	{
		super(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0f, 10.0f).notSolid(), name, group, true);
	}

	public IItemProvider getItemDropped(BlockState state, World worldIn, BlockPos pos, int fortune)
	{
		return this;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		return new ItemStack(this);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray)
	{
		if (!world.isRemote)
		{
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof EnergyTileEntity)
			{
				((ServerPlayerEntity) player).openContainer((EnergyTileEntity) te);
			}
		}

		return ActionResultType.SUCCESS;
	}

	@Override
	public boolean hasTileEntity()
	{
		return true;
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		if (this == ModBlocks.IDENTIFIER)
			return new IdentifierTileEntity();
		else if (this == ModBlocks.MEGAFURNACE)
			return new MegaFurnaceTileEntity();
		else if (this == ModBlocks.PLANTFARM)
			return new PlantFarmTileEntity();
		else if (this == ModBlocks.SOLARGENERATOR)
			return new SolarGeneratorTileEntity();
		else if (this == ModBlocks.SEEDSQUEEZER)
			return new SeedSqueezerTileEntity();
		else if (this == ModBlocks.DNA_COMBINER)
			return new DNACombinerTileEntity();
		else if (this == ModBlocks.DNA_EXTRACTOR)
			return new DNAExtractorTileEntity();
		else if (this == ModBlocks.DNA_REMOVER)
			return new DNARemoverTileEntity();
		else if (this == ModBlocks.SEEDCONSTRUCTOR)
			return new SeedconstructorTileEntity();
		else if (this == ModBlocks.DNA_CLEANER)
			return new DNACleanerTileEntity();
		else if (this == ModBlocks.COMPRESSOR)
			return new CompressorTileEntity();
		else if (this == ModBlocks.ENERGYSTORAGE)
			return new EnergyStorageTileEntity();
		else if (this == ModBlocks.INFUSER)
			return new InfuserTileEntity();
		else if (this == ModBlocks.CHIPALYZER)
			return new ChipalyzerTileEntity();
		else if (this == ModBlocks.PLANTTOPIA_TELEPORTER)
			return new PlantTopiaTeleporterTileEntity();
		else if (this == ModBlocks.MACHINEBULBREPROCESSOR)
			return new MachineBulbReprocessorTileEntity();
		else if (this == ModBlocks.ENERGY_SUPPLIER)
			return new EnergySupplierTileEntity();
		else
			return new IdentifierTileEntity();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			if (worldIn.getTileEntity(pos) instanceof EnergyInventoryTileEntity)
			{
				EnergyInventoryTileEntity te = (EnergyInventoryTileEntity) worldIn.getTileEntity(pos);
				List<ItemStack> toSpawn = te.getInventoryContent();
				for (ItemStack stack : toSpawn)
				{
					worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
				}
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

}
