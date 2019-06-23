package net.kaneka.planttech2.blocks.machines;

import java.util.List;
import net.kaneka.planttech2.blocks.BaseBlock;
import net.kaneka.planttech2.container.CompressorContainer;
import net.kaneka.planttech2.container.DNACleanerContainer;
import net.kaneka.planttech2.container.DNACombinerContainer;
import net.kaneka.planttech2.container.DNAExtractorContainer;
import net.kaneka.planttech2.container.DNARemoverContainer;
import net.kaneka.planttech2.container.EnergyStorageContainer;
import net.kaneka.planttech2.container.IdentifierContainer;
import net.kaneka.planttech2.container.InfuserContainer;
import net.kaneka.planttech2.container.MegaFurnaceContainer;
import net.kaneka.planttech2.container.PlantFarmContainer;
import net.kaneka.planttech2.container.SeedSqueezerContainer;
import net.kaneka.planttech2.container.SeedconstructorContainer;
import net.kaneka.planttech2.container.SolarGeneratorContainer;
import net.kaneka.planttech2.gui.ScreenReferences;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.machine.CompressorTileEntity;
import net.kaneka.planttech2.tileentity.machine.DNACleanerTileEntity;
import net.kaneka.planttech2.tileentity.machine.DNACombinerTileEntity;
import net.kaneka.planttech2.tileentity.machine.DNAExtractorTileEntity;
import net.kaneka.planttech2.tileentity.machine.DNARemoverTileEntity;
import net.kaneka.planttech2.tileentity.machine.EnergyStorageTileEntity;
import net.kaneka.planttech2.tileentity.machine.IdentifierTileEntity;
import net.kaneka.planttech2.tileentity.machine.InfuserTileEntity;
import net.kaneka.planttech2.tileentity.machine.MegaFurnaceTileEntity;
import net.kaneka.planttech2.tileentity.machine.PlantFarmTileEntity;
import net.kaneka.planttech2.tileentity.machine.SeedSqueezerTileEntity;
import net.kaneka.planttech2.tileentity.machine.SeedconstructorTileEntity;
import net.kaneka.planttech2.tileentity.machine.SolarGeneratorTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MachineBaseBlock extends BaseBlock
{

    public MachineBaseBlock(String name, ItemGroup group)
    {
	super(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0f, 10.0f), name, group, true);
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
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray)
    {
	if (!world.isRemote)
	{
	    TileEntity te = world.getTileEntity(pos);
	    if (te instanceof EnergyTileEntity)
	    {
	    	((ServerPlayerEntity) player).openContainer((EnergyTileEntity) te);
	    }
	}

	return true;
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
	else
	    return new IdentifierTileEntity();
    }

    /*
    public Container createContainer(PlayerInventory playerInventory, TileEntity te)
    {
	if (this == ModBlocks.IDENTIFIER && te instanceof TileEntityIdentifier)
	    return new ContainerIdentifier(playerInventory, (TileEntityIdentifier) te);
	else if (this == ModBlocks.MEGAFURNACE && te instanceof TileEntityMegaFurnace)
	    return new ContainerMegaFurnace(playerInventory, (TileEntityMegaFurnace) te);
	else if (this == ModBlocks.PLANTFARM && te instanceof TileEntityPlantFarm)
	    return new ContainerPlantFarm(playerInventory, (TileEntityPlantFarm) te);
	else if (this == ModBlocks.SOLARGENERATOR && te instanceof TileEntitySolarGenerator)
	    return new ContainerSolarGenerator(playerInventory, (TileEntitySolarGenerator) te);
	else if (this == ModBlocks.SEEDSQUEEZER && te instanceof TileEntitySeedSqueezer)
	    return new ContainerSeedSqueezer(playerInventory, (TileEntitySeedSqueezer) te);
	else if (this == ModBlocks.DNA_COMBINER && te instanceof TileEntityDNACombiner)
	    return new ContainerDNACombiner(playerInventory, (TileEntityDNACombiner) te);
	else if (this == ModBlocks.DNA_EXTRACTOR && te instanceof TileEntityDNAExtractor)
	    return new ContainerDNAExtractor(playerInventory, (TileEntityDNAExtractor) te);
	else if (this == ModBlocks.DNA_REMOVER && te instanceof TileEntityDNARemover)
	    return new ContainerDNARemover(playerInventory, (TileEntityDNARemover) te);
	else if (this == ModBlocks.SEEDCONSTRUCTOR && te instanceof TileEntitySeedconstructor)
	    return new ContainerSeedconstructor(playerInventory, (TileEntitySeedconstructor) te);
	else if (this == ModBlocks.DNA_CLEANER && te instanceof TileEntityDNACleaner)
	    return new ContainerDNACleaner(playerInventory, (TileEntityDNACleaner) te);
	else if (this == ModBlocks.COMPRESSOR && te instanceof TileEntityCompressor)
	    return new ContainerCompressor(playerInventory, (TileEntityCompressor) te);
	else if (this == ModBlocks.ENERGYSTORAGE && te instanceof TileEntityEnergyStorage)
	    return new ContainerEnergyStorage(playerInventory, (TileEntityEnergyStorage) te);
	else if (this == ModBlocks.INFUSER && te instanceof TileEntityInfuser)
    	return new ContainerInfuser(playerInventory, (TileEntityInfuser) te);
	else
	    return new ContainerIdentifier(playerInventory, (TileEntityIdentifier) te);
    }
    */

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
	return BlockRenderType.MODEL;
    }

    public String getGuiString()
    {
	if (this == ModBlocks.IDENTIFIER)
	    return ScreenReferences.GUI_IDENTIFIER;
	else if (this == ModBlocks.MEGAFURNACE)
	    return ScreenReferences.GUI_MEGA_FURNACE;
	else if (this == ModBlocks.PLANTFARM)
	    return ScreenReferences.GUI_PLANTFARM;
	else if (this == ModBlocks.SOLARGENERATOR)
	    return ScreenReferences.GUI_SOLARGENERATOR;
	else if (this == ModBlocks.SEEDSQUEEZER)
	    return ScreenReferences.GUI_SEEDSQUEEZER;
	else if (this == ModBlocks.DNA_COMBINER)
	    return ScreenReferences.GUI_DNA_COMBINER;
	else if (this == ModBlocks.DNA_EXTRACTOR)
	    return ScreenReferences.GUI_DNA_EXTRACTOR;
	else if (this == ModBlocks.DNA_REMOVER)
	    return ScreenReferences.GUI_DNA_REMOVER;
	else if (this == ModBlocks.SEEDCONSTRUCTOR)
	    return ScreenReferences.GUI_SEEDCONSTRUCTOR;
	else if (this == ModBlocks.DNA_CLEANER)
	    return ScreenReferences.GUI_DNA_CLEANER;
	else if (this == ModBlocks.COMPRESSOR)
	    return ScreenReferences.GUI_COMPRESSOR;
	else if (this == ModBlocks.ENERGYSTORAGE)
	    return ScreenReferences.GUI_ENERGYSTORAGE;
	else if (this == ModBlocks.INFUSER)
	    return ScreenReferences.GUI_INFUSER;
	else
	    return ScreenReferences.GUI_IDENTIFIER;
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
