package net.kaneka.planttech2.blocks.machines;

import java.util.List;
import net.kaneka.planttech2.blocks.BlockBase;
import net.kaneka.planttech2.container.ContainerCompressor;
import net.kaneka.planttech2.container.ContainerDNACleaner;
import net.kaneka.planttech2.container.ContainerDNACombiner;
import net.kaneka.planttech2.container.ContainerDNAExtractor;
import net.kaneka.planttech2.container.ContainerDNARemover;
import net.kaneka.planttech2.container.ContainerEnergyStorage;
import net.kaneka.planttech2.container.ContainerIdentifier;
import net.kaneka.planttech2.container.ContainerInfuser;
import net.kaneka.planttech2.container.ContainerMegaFurnace;
import net.kaneka.planttech2.container.ContainerPlantFarm;
import net.kaneka.planttech2.container.ContainerSeedSqueezer;
import net.kaneka.planttech2.container.ContainerSeedconstructor;
import net.kaneka.planttech2.container.ContainerSolarGenerator;
import net.kaneka.planttech2.gui.GUIReferences;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.machine.TileEntityCompressor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACleaner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACombiner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNAExtractor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNARemover;
import net.kaneka.planttech2.tileentity.machine.TileEntityEnergyStorage;
import net.kaneka.planttech2.tileentity.machine.TileEntityIdentifier;
import net.kaneka.planttech2.tileentity.machine.TileEntityInfuser;
import net.kaneka.planttech2.tileentity.machine.TileEntityMegaFurnace;
import net.kaneka.planttech2.tileentity.machine.TileEntityPlantFarm;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedSqueezer;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedconstructor;
import net.kaneka.planttech2.tileentity.machine.TileEntitySolarGenerator;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergy;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockMachineBase extends BlockBase
{

    public BlockMachineBase(String name, ItemGroup group)
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
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ)
    {
	if (!world.isRemote)
	{
	    TileEntity te = world.getTileEntity(pos);
	    if (te instanceof TileEntityEnergy)
	    {
		NetworkHooks.openGui((ServerPlayerEntity) player, (TileEntityEnergy) te, extraData ->
		{
		    extraData.writeBlockPos(pos);
		});
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
	    return new TileEntityIdentifier();
	else if (this == ModBlocks.MEGAFURNACE)
	    return new TileEntityMegaFurnace();
	else if (this == ModBlocks.PLANTFARM)
	    return new TileEntityPlantFarm();
	else if (this == ModBlocks.SOLARGENERATOR)
	    return new TileEntitySolarGenerator();
	else if (this == ModBlocks.SEEDSQUEEZER)
	    return new TileEntitySeedSqueezer();
	else if (this == ModBlocks.DNA_COMBINER)
	    return new TileEntityDNACombiner();
	else if (this == ModBlocks.DNA_EXTRACTOR)
	    return new TileEntityDNAExtractor();
	else if (this == ModBlocks.DNA_REMOVER)
	    return new TileEntityDNARemover();
	else if (this == ModBlocks.SEEDCONSTRUCTOR)
	    return new TileEntitySeedconstructor();
	else if (this == ModBlocks.DNA_CLEANER)
	    return new TileEntityDNACleaner();
	else if (this == ModBlocks.COMPRESSOR)
	    return new TileEntityCompressor();
	else if (this == ModBlocks.ENERGYSTORAGE)
	    return new TileEntityEnergyStorage();
	else if (this == ModBlocks.INFUSER)
		return new TileEntityInfuser(); 
	else
	    return new TileEntityIdentifier();
    }

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

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
	return BlockRenderType.MODEL;
    }

    public String getGuiString()
    {
	if (this == ModBlocks.IDENTIFIER)
	    return GUIReferences.GUI_IDENTIFIER;
	else if (this == ModBlocks.MEGAFURNACE)
	    return GUIReferences.GUI_MEGA_FURNACE;
	else if (this == ModBlocks.PLANTFARM)
	    return GUIReferences.GUI_PLANTFARM;
	else if (this == ModBlocks.SOLARGENERATOR)
	    return GUIReferences.GUI_SOLARGENERATOR;
	else if (this == ModBlocks.SEEDSQUEEZER)
	    return GUIReferences.GUI_SEEDSQUEEZER;
	else if (this == ModBlocks.DNA_COMBINER)
	    return GUIReferences.GUI_DNA_COMBINER;
	else if (this == ModBlocks.DNA_EXTRACTOR)
	    return GUIReferences.GUI_DNA_EXTRACTOR;
	else if (this == ModBlocks.DNA_REMOVER)
	    return GUIReferences.GUI_DNA_REMOVER;
	else if (this == ModBlocks.SEEDCONSTRUCTOR)
	    return GUIReferences.GUI_SEEDCONSTRUCTOR;
	else if (this == ModBlocks.DNA_CLEANER)
	    return GUIReferences.GUI_DNA_CLEANER;
	else if (this == ModBlocks.COMPRESSOR)
	    return GUIReferences.GUI_COMPRESSOR;
	else if (this == ModBlocks.ENERGYSTORAGE)
	    return GUIReferences.GUI_ENERGYSTORAGE;
	else if (this == ModBlocks.INFUSER)
	    return GUIReferences.GUI_INFUSER;
	else
	    return GUIReferences.GUI_IDENTIFIER;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
	if (state.getBlock() != newState.getBlock())
	{
	    if (worldIn.getTileEntity(pos) instanceof TileEntityEnergyInventory)
	    {
		TileEntityEnergyInventory te = (TileEntityEnergyInventory) worldIn.getTileEntity(pos);
		List<ItemStack> toSpawn = te.getInventoryContent();
		for (ItemStack stack : toSpawn)
		{
		    worldIn.func_217376_c(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
		}
	    }
	    super.onReplaced(state, worldIn, pos, newState, isMoving);
	}
    }

}
