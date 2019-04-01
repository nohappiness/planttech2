package net.kaneka.planttech2.blocks.machines;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import io.netty.buffer.Unpooled;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.BlockBase;
import net.kaneka.planttech2.blocks.ModBlocks;
import net.kaneka.planttech2.container.ContainerCompressor;
import net.kaneka.planttech2.container.ContainerDNACleaner;
import net.kaneka.planttech2.container.ContainerDNACombiner;
import net.kaneka.planttech2.container.ContainerDNAExtractor;
import net.kaneka.planttech2.container.ContainerDNARemover;
import net.kaneka.planttech2.container.ContainerEnergyStorage;
import net.kaneka.planttech2.container.ContainerIdentifier;
import net.kaneka.planttech2.container.ContainerMegaFurnace;
import net.kaneka.planttech2.container.ContainerPlantFarm;
import net.kaneka.planttech2.container.ContainerSeedSqueezer;
import net.kaneka.planttech2.container.ContainerSeedconstructor;
import net.kaneka.planttech2.container.ContainerSolarGenerator;
import net.kaneka.planttech2.gui.GUIReferences;
import net.kaneka.planttech2.tileentity.machine.TileEntityCompressor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACleaner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACombiner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNAExtractor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNARemover;
import net.kaneka.planttech2.tileentity.machine.TileEntityEnergy;
import net.kaneka.planttech2.tileentity.machine.TileEntityEnergyInventory;
import net.kaneka.planttech2.tileentity.machine.TileEntityEnergyStorage;
import net.kaneka.planttech2.tileentity.machine.TileEntityIdentifier;
import net.kaneka.planttech2.tileentity.machine.TileEntityMegaFurnace;
import net.kaneka.planttech2.tileentity.machine.TileEntityPlantFarm;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedSqueezer;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedconstructor;
import net.kaneka.planttech2.tileentity.machine.TileEntitySolarGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockMachineBase extends BlockBase
{

    public BlockMachineBase(String name, ItemGroup group)
    {
	super(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0f, 10.0f), name, group);
    }

    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune)
    {
	return this;
    }
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this);
    }

   

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
	if (!world.isRemote)
	{
	    TileEntity te = world.getTileEntity(pos);
	    if(te instanceof TileEntityEnergy)
	    {
        	    NetworkHooks.openGui((EntityPlayerMP) player, (TileEntityEnergy)te, extraData -> {extraData.writeBlockPos(pos);});
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
    public boolean hasTileEntity(IBlockState state)
    {
	return true;
    }
    
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world)
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
	else
	    return new TileEntityIdentifier();
    }
    
    public Container createContainer(InventoryPlayer playerInventory, TileEntity te)
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
	/*else if (this == ModBlocks.ENERGYSTORAGE && te instanceof TileEntityEnergyStorage)
	    return new ContainerEnergyStorage(playerInventory, (TileEntityEnergyStorage) te);*/
	else
	    return new ContainerIdentifier(playerInventory, (TileEntityIdentifier) te);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
	return EnumBlockRenderType.MODEL;
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
	    else return GUIReferences.GUI_IDENTIFIER; 
    }
    
    @Override
    public void onReplaced(IBlockState state, World worldIn, BlockPos pos, IBlockState newState, boolean isMoving)
    {
	if (worldIn.getTileEntity(pos) instanceof TileEntityEnergyInventory)
	{
	    TileEntityEnergyInventory te = (TileEntityEnergyInventory) worldIn.getTileEntity(pos);
	    List<ItemStack> toSpawn = te.getInventoryContent();
	    for (ItemStack stack: toSpawn)
	    {
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
	    }
	}
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

}
