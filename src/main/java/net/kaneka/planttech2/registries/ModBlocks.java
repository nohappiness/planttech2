package net.kaneka.planttech2.registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.BlockBase;
import net.kaneka.planttech2.blocks.BlockCropBars;
import net.kaneka.planttech2.blocks.BlockCropBase;
import net.kaneka.planttech2.blocks.machines.BlockCable;
import net.kaneka.planttech2.blocks.machines.BlockEnergyStorage;
import net.kaneka.planttech2.blocks.machines.BlockMachineBase;
import net.kaneka.planttech2.blocks.machines.BlockMachineFacing;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
	public static List<BlockBase> BLOCKS = new ArrayList<BlockBase>();
	public static List<BlockBase> BLOCKITEMS = new ArrayList<BlockBase>();

	public static BlockBase 
			CABLE = new BlockCable(), 
			COMPRESSOR = new BlockMachineFacing("compressor", ModCreativeTabs.groupmachines), 
			CROPBARS = new BlockCropBars(),
	        DANCIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "dancium_block", ModCreativeTabs.groupmain, true),
	        DNA_CLEANER = new BlockMachineFacing("dna_cleaner", ModCreativeTabs.groupmachines), 
	        DNA_COMBINER = new BlockMachineFacing("dna_combiner", ModCreativeTabs.groupmachines),
	        DNA_EXTRACTOR = new BlockMachineFacing("dna_extractor", ModCreativeTabs.groupmachines), 
	        DNA_REMOVER = new BlockMachineFacing("dna_remover", ModCreativeTabs.groupmachines),
	        ENERGYSTORAGE = new BlockEnergyStorage(), 
	        IDENTIFIER = new BlockMachineFacing("identifier", ModCreativeTabs.groupmachines),
	        INFUSER = new BlockMachineFacing("infuser", ModCreativeTabs.groupmachines),
	        KANEKIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "kanekium_block", ModCreativeTabs.groupmain, true),
	        KINNOIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "kinnoium_block", ModCreativeTabs.groupmain, true),
	        LENTHURIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "lenthurium_block", ModCreativeTabs.groupmain, true),
		    MACHINESHELL = new BlockBase(Block.Properties.create(Material.IRON), "machineshell", ModCreativeTabs.groupmain, true),  
		    MACHINESHELL_INFUSED = new BlockBase(Block.Properties.create(Material.IRON), "machineshell_infused", ModCreativeTabs.groupmain, true),  
	        MEGAFURNACE = new BlockMachineFacing("mega_furnace", ModCreativeTabs.groupmachines), 
	        PLANTFARM = new BlockMachineBase("plantfarm", ModCreativeTabs.groupmachines),
	        PLANTIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "plantium_block", ModCreativeTabs.groupmain, true),
	        SEEDCONSTRUCTOR = new BlockMachineFacing("seedconstructor", ModCreativeTabs.groupmachines),
	        SEEDSQUEEZER = new BlockMachineFacing("seedsqueezer", ModCreativeTabs.groupmachines),
	        SOLARGENERATOR = new BlockMachineBase("solargenerator", ModCreativeTabs.groupmachines);

	public static HashMap<String, BlockCropBase> CROPS = new HashMap<String, BlockCropBase>();

	public static void register(IForgeRegistry<Block> registry)
	{
		for (BlockBase block : BLOCKS)
		{
			registry.register(block);
		}

		BlockCropBase tempcrop;
		String name;
		for (CropListEntry entry : PlantTechMain.croplist.getAllEntries())
		{
			name = entry.getString();
			tempcrop = new BlockCropBase(name);
			CROPS.put(name, tempcrop);
			registry.register(tempcrop);
		}
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry)
	{

		for (BlockBase block : BLOCKITEMS)
		{
			registry.register(block.createItemBlock());
		}

	}

	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColorHandler(ColorHandlerEvent.Block event)
	{
		for (BlockCropBase block : CROPS.values())
		{
			event.getBlockColors().register(new BlockCropBase.ColorHandler(), block);
		}
	}
}
