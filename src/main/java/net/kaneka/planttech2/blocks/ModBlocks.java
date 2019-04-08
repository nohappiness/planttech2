package net.kaneka.planttech2.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.machines.BlockCable;
import net.kaneka.planttech2.blocks.machines.BlockEnergyStorage;
import net.kaneka.planttech2.blocks.machines.BlockMachineBase;
import net.kaneka.planttech2.blocks.machines.BlockMachineFacing;
import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
    public static List<BlockBase> BLOCKS = new ArrayList<BlockBase>(); 
    public static List<BlockBase> BLOCKITEMS = new ArrayList<BlockBase>(); 
    
    public static BlockBase CABLE = new BlockCable(),
	    		    COMPRESSOR = new BlockMachineFacing("compressor", PlantTechMain.groupmachines),
                            CROPBARS = new BlockCropBars(),
                            DANCIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "dancium_block", PlantTechMain.groupmain, true),
                            DNA_CLEANER = new BlockMachineFacing("dna_cleaner", PlantTechMain.groupmachines),
                            DNA_COMBINER = new BlockMachineFacing("dna_combiner", PlantTechMain.groupmachines),
                	    DNA_EXTRACTOR = new BlockMachineFacing("dna_extractor", PlantTechMain.groupmachines),
                            DNA_REMOVER = new BlockMachineFacing("dna_remover", PlantTechMain.groupmachines),
                            ENERGYSTORAGE = new BlockEnergyStorage(), 
                            IDENTIFIER = new BlockMachineFacing("identifier", PlantTechMain.groupmachines),    	    
                            KANEKIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "kanekium_block", PlantTechMain.groupmain, true),
                            KINNOIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "kinnoium_block", PlantTechMain.groupmain, true),        
                            LENTHURIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "lenthurium_block", PlantTechMain.groupmain, true),
                            MEGAFURNACE = new BlockMachineFacing("mega_furnace", PlantTechMain.groupmachines),
                            PLANTFARM = new BlockMachineBase("plantfarm", PlantTechMain.groupmachines),
                            PLANTIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON).sound(SoundType.METAL), "plantium_block", PlantTechMain.groupmain, true),
                            SEEDCONSTRUCTOR = new BlockMachineFacing("seedconstructor", PlantTechMain.groupmachines),
                            SEEDSQUEEZER = new BlockMachineFacing("seedsqueezer", PlantTechMain.groupmachines),
                            SOLARGENERATOR = new BlockMachineBase("solargenerator", PlantTechMain.groupmachines);              
    
    public static HashMap<String, BlockCropBase> CROPS = new HashMap<String, BlockCropBase>(); 

    public static void register(IForgeRegistry<Block> registry)
    {
	for(BlockBase block: BLOCKS)
	{
	    registry.register(block);
	}
	
	BlockCropBase tempcrop; 
	String name; 
	for(CropListEntry entry : PlantTechMain.instance.croplist.getAllEntries())
	{
	    name = entry.getString();
	    tempcrop = new BlockCropBase(name);
	    CROPS.put(name, tempcrop);
	    registry.register(tempcrop);
	}
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry)
    {

	for(BlockBase block: BLOCKITEMS)
	{
	    registry.register(block.createItemBlock());
	}

    }

    public static void registerBlockColorHandler(ColorHandlerEvent.Block event)
    {
	for(BlockCropBase block: CROPS.values())
	{
	    event.getBlockColors().register(new BlockCropBase.ColorHandler(), block);
	}
    }
}
