package net.kaneka.planttech2.blocks;

import java.util.HashMap;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.machines.BlockCable;
import net.kaneka.planttech2.blocks.machines.BlockMachineBase;
import net.kaneka.planttech2.blocks.machines.BlockMachineFacing;
import net.kaneka.planttech2.items.ItemCropSeed;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{

    public static BlockBase CROPBARS = new BlockCropBars();
    public static HashMap<String, BlockCropBase> CROPS = new HashMap<String, BlockCropBase>(); 

    public static BlockMachineFacing MEGAFURNACE = new BlockMachineFacing("mega_furnace", PlantTechMain.groupmachines);
    public static BlockMachineFacing IDENTIFIER = new BlockMachineFacing("identifier", PlantTechMain.groupmachines);
    public static BlockMachineFacing SEEDSQUEEZER = new BlockMachineFacing("seedsqueezer", PlantTechMain.groupmachines);
    public static BlockMachineBase SOLARGENERATOR = new BlockMachineBase("solargenerator", PlantTechMain.groupmachines);
    public static BlockMachineBase PLANTFARM = new BlockMachineBase("plantfarm", PlantTechMain.groupmachines);
    public static BlockMachineFacing DNA_EXTRACTOR = new BlockMachineFacing("dna_extractor", PlantTechMain.groupmachines);
    public static BlockMachineFacing DNA_REMOVER = new BlockMachineFacing("dna_remover", PlantTechMain.groupmachines);
    public static BlockMachineFacing DNA_COMBINER = new BlockMachineFacing("dna_combiner", PlantTechMain.groupmachines);
    public static BlockMachineFacing SEEDCONSTRUCTOR = new BlockMachineFacing("seedconstructor", PlantTechMain.groupmachines);
    public static BlockMachineFacing DNA_CLEANER = new BlockMachineFacing("dna_cleaner", PlantTechMain.groupmachines);
    public static BlockMachineFacing COMPRESSOR = new BlockMachineFacing("compressor", PlantTechMain.groupmachines);
    
    public static BlockMachineFacing ENERGYSTORAGE = new BlockMachineFacing("energystorage", PlantTechMain.groupmachines); 
    
    public static BlockCable CABLE = new BlockCable();

    public static BlockBase PLANTIUM_BLOCK = new BlockBase(Block.Properties.create(Material.IRON), "plantium_block", PlantTechMain.groupmain);

    public static BlockBase LENTHURIUM_BLOCK = new BlockBase(Block.Properties.create(Material.ROCK), "lenthurium_block", PlantTechMain.groupmain);
    public static BlockBase KANEKIUM_BLOCK = new BlockBase(Block.Properties.create(Material.ROCK), "kanekium_block", PlantTechMain.groupmain);
    public static BlockBase DANCIUM_BLOCK = new BlockBase(Block.Properties.create(Material.ROCK), "dancium_block", PlantTechMain.groupmain);
    public static BlockBase KINNOIUM_BLOCK = new BlockBase(Block.Properties.create(Material.ROCK), "kinnoium_block", PlantTechMain.groupmain);


    public static void register(IForgeRegistry<Block> registry)
    {
	registry.registerAll(CROPBARS, 
			     MEGAFURNACE, 
			     IDENTIFIER, 
			     SEEDSQUEEZER, 
			     SOLARGENERATOR, 
			     PLANTFARM, 
			     CABLE, 
			     PLANTIUM_BLOCK, 
			     DNA_COMBINER, 
			     DNA_EXTRACTOR, 
			     DNA_REMOVER, 
			     SEEDCONSTRUCTOR, 
			     DNA_CLEANER, 
			     COMPRESSOR, 
			     LENTHURIUM_BLOCK,
			     KANEKIUM_BLOCK, 
			     DANCIUM_BLOCK, 
			     KINNOIUM_BLOCK
			     );
	
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

	
	registry.register(MEGAFURNACE.createItemBlock());
	registry.register(IDENTIFIER.createItemBlock());
	registry.register(SEEDSQUEEZER.createItemBlock());
	registry.register(SOLARGENERATOR.createItemBlock());
	registry.register(PLANTFARM.createItemBlock());
	registry.register(CABLE.createItemBlock());
	registry.register(PLANTIUM_BLOCK.createItemBlock());
	registry.register(DNA_COMBINER.createItemBlock());
	registry.register(DNA_EXTRACTOR.createItemBlock());
	registry.register(DNA_REMOVER.createItemBlock());
	registry.register(SEEDCONSTRUCTOR.createItemBlock());
	registry.register(DNA_CLEANER.createItemBlock());
	registry.register(LENTHURIUM_BLOCK.createItemBlock());
	registry.register(KANEKIUM_BLOCK.createItemBlock());
	registry.register(DANCIUM_BLOCK.createItemBlock());
	registry.register(KINNOIUM_BLOCK.createItemBlock());
	registry.register(COMPRESSOR.createItemBlock());
	registry.register(CROPBARS.createItemBlock());
	//registry.register(ENERGYSTORAGE.createItemBlock());

    }

    public static void registerBlockColorHandler(ColorHandlerEvent.Block event)
    {
	for(BlockCropBase block: CROPS.values())
	{
	    event.getBlockColors().register(new BlockCropBase.ColorHandler(), block);
	}
    }
}
