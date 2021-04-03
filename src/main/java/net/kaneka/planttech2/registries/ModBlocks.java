package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.CarverBlock;
import net.kaneka.planttech2.blocks.CropBarsBlock;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.blocks.ElectricFence;
import net.kaneka.planttech2.blocks.ElectricFenceGate;
import net.kaneka.planttech2.blocks.ElectricFenceTop;
import net.kaneka.planttech2.blocks.FacingGrowingBlock;
import net.kaneka.planttech2.blocks.GlassPaneEnd;
import net.kaneka.planttech2.blocks.GlassPanePillar;
import net.kaneka.planttech2.blocks.GrowingBlock;
import net.kaneka.planttech2.blocks.Hedge;
import net.kaneka.planttech2.blocks.ObtainableTallBushBlock;
import net.kaneka.planttech2.blocks.WallLight;
import net.kaneka.planttech2.blocks.baseclasses.BaseOreBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomDoorBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomFenceBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomSlabBlock;
import net.kaneka.planttech2.blocks.baseclasses.CustomStairsBlock;
import net.kaneka.planttech2.blocks.baseclasses.ObtainableNaturalPlants;
import net.kaneka.planttech2.blocks.machines.EnergyStorageBlock;
import net.kaneka.planttech2.blocks.machines.EnergySupplierBlock;
import net.kaneka.planttech2.blocks.machines.MachineBaseBlock;
import net.kaneka.planttech2.blocks.machines.MachineFacingBlock;
import net.kaneka.planttech2.blocks.machines.MachineTeleporterEndBlock;
import net.kaneka.planttech2.blocks.machines.TestCableBlock;
import net.kaneka.planttech2.crops.CropEntry;
import net.kaneka.planttech2.tileentity.machine.*;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.kaneka.planttech2.utilities.ModCreativeTabs.*;
import static net.minecraft.block.AbstractBlock.Properties.create;

@ObjectHolder(PlantTechMain.MODID)
public class ModBlocks
{
	public static List<Supplier<? extends Block>> SPECIAL_RENDER_BLOCKS = new ArrayList<>();
	public static List<Supplier<? extends Item>> BLOCK_ITEM_SUPPLIERS = new ArrayList<>();
	public static List<Hedge> HEDGE_BLOCKS = new ArrayList<>();
	public static HashMap<String, CropBaseBlock> CROPS = new HashMap<>();

	@ObjectHolder("biomassfluid_block") public static FlowingFluidBlock BIOMASSFLUIDBLOCK;

	@ObjectHolder("cable") public static TestCableBlock CABLE;
	@ObjectHolder("carver") public static CarverBlock CARVER;
	@ObjectHolder("chipalyzer") public static MachineFacingBlock CHIPALYZER;
	@ObjectHolder("compressor") public static MachineFacingBlock COMPRESSOR;
	@ObjectHolder("crop_aura_generator") public static MachineFacingBlock CROP_AURA_GENERATOR;
	@ObjectHolder("cropbars") public static CropBarsBlock CROPBARS;
	@ObjectHolder("dancium_block") public static Block DANCIUM_BLOCK;
	@ObjectHolder("dna_cleaner") public static MachineFacingBlock DNA_CLEANER;
	@ObjectHolder("dna_combiner") public static MachineFacingBlock DNA_COMBINER;
	@ObjectHolder("dna_extractor") public static MachineFacingBlock DNA_EXTRACTOR;
	@ObjectHolder("dna_remover") public static MachineFacingBlock DNA_REMOVER;
	@ObjectHolder("electric_fence") public static ElectricFence ELECTRIC_FENCE;
	@ObjectHolder("electric_fence_top") public static ElectricFenceTop ELECTRIC_FENCE_TOP;
	@ObjectHolder("electric_fence_gate") public static ElectricFenceGate ELECTRIC_FENCE_GATE;
	@ObjectHolder("energystorage") public static EnergyStorageBlock ENERGYSTORAGE;
	@ObjectHolder("energy_supplier") public static EnergySupplierBlock ENERGY_SUPPLIER;
	@ObjectHolder("fibre_fence") public static Block FIBRE_FENCE;
	@ObjectHolder("identifier") public static MachineFacingBlock IDENTIFIER;
	@ObjectHolder("infuser") public static MachineFacingBlock INFUSER;
	@ObjectHolder("kanekium_block") public static Block KANEKIUM_BLOCK;
	@ObjectHolder("kinnoium_block") public static Block KINNOIUM_BLOCK;
	@ObjectHolder("lenthurium_block") public static Block LENTHURIUM_BLOCK;
	@ObjectHolder("machinebulbreprocessor") public static Block MACHINEBULBREPROCESSOR;
	@ObjectHolder("machineshell_iron") public static Block MACHINESHELL_IRON;
	@ObjectHolder("machineshell_plantium") public static Block MACHINESHELL_PLANTIUM;
	@ObjectHolder("mega_furnace") public static MachineFacingBlock MEGAFURNACE;
	@ObjectHolder("plantfarm") public static MachineBaseBlock PLANTFARM;
	@ObjectHolder("plantium_block") public static Block PLANTIUM_BLOCK;
	@ObjectHolder("seedconstructor") public static MachineFacingBlock SEEDCONSTRUCTOR;
	@ObjectHolder("seedsqueezer") public static MachineFacingBlock SEEDSQUEEZER;
	@ObjectHolder("solargenerator") public static MachineBaseBlock SOLARGENERATOR;
	@ObjectHolder("planttopia_teleporter") public static MachineBaseBlock PLANTTOPIA_TELEPORTER;
	@ObjectHolder("planttopia_teleporter_end") public static MachineTeleporterEndBlock PLANTTOPIA_TELEPORTER_END;
	@ObjectHolder("universal_soil") public static Block UNIVERSAL_SOIL;
	@ObjectHolder("universal_soil_infused") public static Block UNIVERSAL_SOIL_INFUSED;

	@ObjectHolder("dark_crystal_glasspane_cross") public static GlassPanePillar DARK_CRYSTAL_GLASSPANE_CROSS;
	@ObjectHolder("dark_crystal_glasspane_middle") public static GlassPanePillar DARK_CRYSTAL_GLASSPANE_MIDDLE;
	@ObjectHolder("dark_crystal_glasspane_end") public static GlassPaneEnd DARK_CRYSTAL_GLASSPANE_END;
	@ObjectHolder("dark_crystal_block") public static Block DARK_CRYSTAL_BLOCK;
	@ObjectHolder("dark_crystal_brick") public static Block DARK_CRYSTAL_BRICK;
	@ObjectHolder("dark_crystal_tiling") public static Block DARK_CRYSTAL_TILING;
	@ObjectHolder("dark_crystal_fence") public static CustomFenceBlock DARK_CRYSTAL_FENCE;
	@ObjectHolder("dark_crystal_lamp") public static Block DARK_CRYSTAL_LAMP;
	@ObjectHolder("dark_crystal_door") public static CustomDoorBlock DARK_CRYSTAL_DOOR;
	@ObjectHolder("dark_crystal_stairs") public static CustomStairsBlock DARK_CRYSTAL_STAIRS;
	@ObjectHolder("dark_crystal_slab") public static CustomSlabBlock DARK_CRYSTAL_SLAB;
	@ObjectHolder("dark_crystal_ore") public static BaseOreBlock DARK_CRYSTAL_ORE;

	@ObjectHolder("white_crystal_glasspane_cross") public static GlassPanePillar WHITE_CRYSTAL_GLASSPANE_CROSS;
	@ObjectHolder("white_crystal_glasspane_middle") public static GlassPanePillar WHITE_CRYSTAL_GLASSPANE_MIDDLE;
	@ObjectHolder("white_crystal_glasspane_end") public static GlassPaneEnd WHITE_CRYSTAL_GLASSPANE_END;
	@ObjectHolder("white_crystal_block") public static Block WHITE_CRYSTAL_BLOCK;
	@ObjectHolder("white_crystal_brick") public static Block WHITE_CRYSTAL_BRICK;
	@ObjectHolder("white_crystal_tiling") public static Block WHITE_CRYSTAL_TILING;
	@ObjectHolder("white_crystal_fence") public static CustomFenceBlock WHITE_CRYSTAL_FENCE;
	@ObjectHolder("white_crystal_door") public static CustomDoorBlock WHITE_CRYSTAL_DOOR;
	@ObjectHolder("white_crystal_stairs") public static CustomStairsBlock WHITE_CRYSTAL_STAIRS;
	@ObjectHolder("white_crystal_slab") public static CustomSlabBlock WHITE_CRYSTAL_SLAB;
	@ObjectHolder("white_crystal_ore") public static BaseOreBlock WHITE_CRYSTAL_ORE;

	@ObjectHolder("wall_light") public static WallLight WALL_LIGHT;

	@ObjectHolder("testblock") public static ObtainableNaturalPlants TEST_BLOCK;
	
	@ObjectHolder("hedge_oak_spruce_dirt") public static Hedge OAK_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_oak_birch_dirt") public static Hedge OAK_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_oak_jungle_dirt") public static Hedge OAK_JUNGLEDIRT_HEDGE;
	@ObjectHolder("hedge_oak_acacia_dirt") public static Hedge OAK_ACACIADIRT_HEDGE;
	@ObjectHolder("hedge_oak_dark_oak_dirt") public static Hedge OAK_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_oak_dirt") public static Hedge SPRUCE_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_spruce_dirt") public static Hedge SPRUCE_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_birch_dirt") public static Hedge SPRUCE_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_jungle_dirt") public static Hedge SPRUCE_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_acacia_dirt") public static Hedge SPRUCE_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_spruce_dark_oak_dirt") public static Hedge SPRUCE_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_oak_dirt") public static Hedge BIRCH_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_spruce_dirt") public static Hedge BIRCH_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_birch_dirt") public static Hedge BIRCH_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_jungle_dirt") public static Hedge BIRCH_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_acacia_dirt") public static Hedge BIRCH_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_birch_dark_oak_dirt") public static Hedge BIRCH_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_oak_dirt") public static Hedge JUNGLE_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_spruce_dirt") public static Hedge JUNGLE_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_birch_dirt") public static Hedge JUNGLE_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_jungle_dirt") public static Hedge JUNGLE_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_acacia_dirt") public static Hedge JUNGLE_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_jungle_dark_oak_dirt") public static Hedge JUNGLE_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_oak_dirt") public static Hedge ACACIA_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_spruce_dirt") public static Hedge ACACIA_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_birch_dirt") public static Hedge ACACIA_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_jungle_dirt") public static Hedge ACACIA_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_acacia_dirt") public static Hedge ACACIA_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_acacia_dark_oak_dirt") public static Hedge ACACIA_DARK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_oak_dirt") public static Hedge DARK_OAK_OAK_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_spruce_dirt") public static Hedge DARK_OAK_SPRUCE_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_birch_dirt") public static Hedge DARK_OAK_BIRCH_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_jungle_dirt") public static Hedge DARK_OAK_JUNGLE_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_acacia_dirt") public static Hedge DARK_OAK_ACACIA_DIRT_HEDGE;
	@ObjectHolder("hedge_dark_oak_dark_oak_dirt") public static Hedge DARK_OAK_DARK_OAK_DIRT_HEDGE;
	
	@ObjectHolder("hedge_oak_spruce_grass") public static Hedge OAK_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_oak_birch_grass") public static Hedge OAK_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_oak_jungle_grass") public static Hedge OAK_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_oak_acacia_grass") public static Hedge OAK_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_oak_dark_oak_grass") public static Hedge OAK_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_oak_grass") public static Hedge SPRUCE_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_spruce_grass") public static Hedge SPRUCE_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_birch_grass") public static Hedge SPRUCE_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_jungle_grass") public static Hedge SPRUCE_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_acacia_grass") public static Hedge SPRUCE_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_spruce_dark_oak_grass") public static Hedge SPRUCE_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_oak_grass") public static Hedge BIRCH_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_spruce_grass") public static Hedge BIRCH_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_birch_grass") public static Hedge BIRCH_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_jungle_grass") public static Hedge BIRCH_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_acacia_grass") public static Hedge BIRCH_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_birch_dark_oak_grass") public static Hedge BIRCH_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_oak_grass") public static Hedge JUNGLE_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_spruce_grass") public static Hedge JUNGLE_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_birch_grass") public static Hedge JUNGLE_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_jungle_grass") public static Hedge JUNGLE_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_acacia_grass") public static Hedge JUNGLE_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_jungle_dark_oak_grass") public static Hedge JUNGLE_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_oak_grass") public static Hedge ACACIA_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_spruce_grass") public static Hedge ACACIA_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_birch_grass") public static Hedge ACACIA_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_jungle_grass") public static Hedge ACACIA_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_acacia_grass") public static Hedge ACACIA_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_acacia_dark_oak_grass") public static Hedge ACACIA_DARK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_oak_grass") public static Hedge DARK_OAK_OAK_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_spruce_grass") public static Hedge DARK_OAK_SPRUCE_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_birch_grass") public static Hedge DARK_OAK_BIRCH_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_jungle_grass") public static Hedge DARK_OAK_JUNGLE_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_acacia_grass") public static Hedge DARK_OAK_ACACIA_GRASS_HEDGE;
	@ObjectHolder("hedge_dark_oak_dark_oak_grass") public static Hedge DARK_OAK_DARK_OAK_GRASS_HEDGE;
	
	@ObjectHolder("hedge_oak_spruce_podzol") public static Hedge OAK_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_oak_birch_podzol") public static Hedge OAK_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_oak_jungle_podzol") public static Hedge OAK_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_oak_acacia_podzol") public static Hedge OAK_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_oak_dark_oak_podzol") public static Hedge OAK_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_oak_podzol") public static Hedge SPRUCE_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_spruce_podzol") public static Hedge SPRUCE_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_birch_podzol") public static Hedge SPRUCE_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_jungle_podzol") public static Hedge SPRUCE_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_acacia_podzol") public static Hedge SPRUCE_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_spruce_dark_oak_podzol") public static Hedge SPRUCE_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_oak_podzol") public static Hedge BIRCH_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_spruce_podzol") public static Hedge BIRCH_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_birch_podzol") public static Hedge BIRCH_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_jungle_podzol") public static Hedge BIRCH_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_acacia_podzol") public static Hedge BIRCH_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_birch_dark_oak_podzol") public static Hedge BIRCH_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_oak_podzol") public static Hedge JUNGLE_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_spruce_podzol") public static Hedge JUNGLE_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_birch_podzol") public static Hedge JUNGLE_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_jungle_podzol") public static Hedge JUNGLE_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_acacia_podzol") public static Hedge JUNGLE_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_jungle_dark_oak_podzol") public static Hedge JUNGLE_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_oak_podzol") public static Hedge ACACIA_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_spruce_podzol") public static Hedge ACACIA_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_birch_podzol") public static Hedge ACACIA_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_jungle_podzol") public static Hedge ACACIA_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_acacia_podzol") public static Hedge ACACIA_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_acacia_dark_oak_podzol") public static Hedge ACACIA_DARK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_oak_podzol") public static Hedge DARK_OAK_OAK_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_spruce_podzol") public static Hedge DARK_OAK_SPRUCE_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_birch_podzol") public static Hedge DARK_OAK_BIRCH_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_jungle_podzol") public static Hedge DARK_OAK_JUNGLE_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_acacia_podzol") public static Hedge DARK_OAK_ACACIA_PODZOL_HEDGE;
	@ObjectHolder("hedge_dark_oak_dark_oak_podzol") public static Hedge DARK_OAK_DARK_OAK_PODZOL_HEDGE;

	@ObjectHolder("hedge_oak_oak") public static Hedge OAK_OAK_HEDGE;

	@ObjectHolder("mutated_dandelion") public static ObtainableNaturalPlants MUTATED_DANDELION;
	@ObjectHolder("mutated_poppy") public static ObtainableNaturalPlants MUTATED_POPPY;
	@ObjectHolder("mutated_blue_orchid") public static ObtainableNaturalPlants MUTATED_BLUE_ORCHID;
	@ObjectHolder("mutated_allium") public static ObtainableNaturalPlants MUTATED_ALLIUM;
	@ObjectHolder("mutated_azure_bluet") public static ObtainableNaturalPlants MUTATED_AZURE_BLUET;
	@ObjectHolder("mutated_red_tulip") public static ObtainableNaturalPlants MUTATED_RED_TULIP;
	@ObjectHolder("mutated_orange_tulip") public static ObtainableNaturalPlants MUTATED_ORANGE_TULIP;
	@ObjectHolder("mutated_white_tulip") public static ObtainableNaturalPlants MUTATED_WHITE_TULIP;
	@ObjectHolder("mutated_pink_tulip") public static ObtainableNaturalPlants MUTATED_PINK_TULIP;
	@ObjectHolder("mutated_oxeye_daisy") public static ObtainableNaturalPlants MUTATED_OXEYE_DAISY;
	@ObjectHolder("mutated_cornflower") public static ObtainableNaturalPlants MUTATED_CORNFLOWER;
	@ObjectHolder("mutated_lily_of_the_valley") public static ObtainableNaturalPlants MUTATED_LILY_OF_THE_VALLEY;
	@ObjectHolder("mutated_lilac") public static ObtainableTallBushBlock MUTATED_LILAC;
	@ObjectHolder("mutated_rose_bush") public static ObtainableTallBushBlock MUTATED_ROSE_BUSH;
	@ObjectHolder("mutated_peony") public static ObtainableTallBushBlock MUTATED_PEONY;

	@ObjectHolder("machinebulbreprocessor_growing") public static GrowingBlock MACHINEBULBREPROCESSOR_GROWING;
	@ObjectHolder("machineshell_iron_growing") public static GrowingBlock MACHINESHELL_IRON_GROWING;
	@ObjectHolder("machineshell_plantium_growing") public static GrowingBlock MACHINESHELL_PLANTIUM_GROWING;
	@ObjectHolder("seedsqueezer_growing") public static GrowingBlock SEEDSQUEEZER_GROWING;
	@ObjectHolder("chipalyzer_growing") public static GrowingBlock CHIPALYZER_GROWING;
	@ObjectHolder("compressor_growing") public static GrowingBlock COMPRESSOR_GROWING;
	@ObjectHolder("dna_cleaner_growing") public static GrowingBlock DNA_CLEANER_GROWING;
	@ObjectHolder("dna_combiner_growing") public static GrowingBlock DNA_COMBINER_GROWING;
	@ObjectHolder("dna_extractor_growing") public static GrowingBlock DNA_EXTRACTOR_GROWING;
	@ObjectHolder("dna_remover_growing") public static GrowingBlock DNA_REMOVER_GROWING;
	@ObjectHolder("identifier_growing") public static GrowingBlock IDENTIFIER_GROWING;
	@ObjectHolder("infuser_growing") public static GrowingBlock INFUSER_GROWING;
	@ObjectHolder("mega_furnace_growing") public static GrowingBlock MEGAFURNACE_GROWING;
	@ObjectHolder("plantfarm_growing") public static GrowingBlock PLANTFARM_GROWING;
	@ObjectHolder("energy_supplier_growing") public static GrowingBlock ENERGY_SUPPLIER_GROWING;
	@ObjectHolder("seedconstructor_growing") public static GrowingBlock SEEDCONSTRUCTOR_GROWING;
	@ObjectHolder("solargenerator_growing") public static GrowingBlock SOLARGENERATOR_GROWING;

	public static void register(IForgeRegistry<Block> r)
	{
		r.register(makeBlock("biomassfluid_block", new FlowingFluidBlock(() -> ModFluids.BIOMASS, create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops())));

		r.register(makeSpecialWithItem("cable", BLOCKS, new TestCableBlock()));
		r.register(makeSpecialWithItem("carver", MAIN, new CarverBlock()));
		r.register(makeSpecialWithItem("chipalyzer", MACHINES, new MachineFacingBlock(ChipalyzerTileEntity::new, PlantTechConstants.MACHINETIER_CHIPALYZER)));
		r.register(makeSpecialWithItem("compressor", MACHINES, new MachineFacingBlock(CompressorTileEntity::new, PlantTechConstants.MACHINETIER_COMPRESSOR)));
		r.register(makeSpecialWithItem("crop_aura_generator", MACHINES, new MachineFacingBlock(CropAuraGeneratorTileEntity::new, PlantTechConstants.MACHINETIER_CROP_AURA_GENERATOR)));
		r.register(makeSpecialWithItem("cropbars", MAIN, new CropBarsBlock()));
		r.register(makeWithItem("dancium_block", BLOCKS, new Block(create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F))));
		r.register(makeSpecialWithItem("dna_cleaner", MACHINES, new MachineFacingBlock(DNACleanerTileEntity::new, PlantTechConstants.MACHINETIER_DNA_CLEANER)));
		r.register(makeSpecialWithItem("dna_combiner", MACHINES, new MachineFacingBlock(DNACombinerTileEntity::new, PlantTechConstants.MACHINETIER_DNA_COMBINER)));
		r.register(makeSpecialWithItem("dna_extractor", MACHINES, new MachineFacingBlock(DNAExtractorTileEntity::new, PlantTechConstants.MACHINETIER_DNA_EXTRACTOR)));
		r.register(makeSpecialWithItem("dna_remover", MACHINES, new MachineFacingBlock(DNARemoverTileEntity::new, PlantTechConstants.MACHINETIER_DNA_REMOVER)));
		r.register(makeWithItem("electric_fence", BLOCKS, new ElectricFence(create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(30.0F))));
		r.register(makeWithItem("electric_fence_top", BLOCKS, new ElectricFenceTop(create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(30.0F))));
		r.register(makeWithItem("electric_fence_gate", BLOCKS, new ElectricFenceGate(create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(30.0F))));
		r.register(makeSpecialWithItem("energystorage", MACHINES, new EnergyStorageBlock(EnergyStorageTileEntity::new)));
		r.register(makeSpecialWithItem("energy_supplier", MACHINES, new EnergySupplierBlock(EnergySupplierTileEntity::new, PlantTechConstants.MACHINETIER_ENERGY_SUPPLIER)));
		r.register(makeWithItem("fibre_fence", BLOCKS, new Block(create(Material.WOOD).sound(SoundType.WOOD))));
		r.register(makeSpecialWithItem("identifier", MACHINES, new MachineFacingBlock(IdentifierTileEntity::new, PlantTechConstants.MACHINETIER_IDENTIFIER)));
		r.register(makeSpecialWithItem("infuser", MACHINES, new MachineFacingBlock(InfuserTileEntity::new, PlantTechConstants.MACHINETIER_INFUSER)));
		r.register(makeWithItem("kanekium_block", BLOCKS, new Block(create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("kinnoium_block", BLOCKS, new Block(create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("lenthurium_block", BLOCKS, new Block(create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F))));
		r.register(makeSpecialWithItem("machinebulbreprocessor", MACHINES, new MachineBaseBlock(MachineBulbReprocessorTileEntity::new, PlantTechConstants.MACHINETIER_MACHINEBULBREPROCESSOR)));
		r.register(makeSpecialWithItem("machineshell_iron", MAIN, new Block(create(Material.IRON).hardnessAndResistance(0.9F).notSolid())));
		r.register(makeSpecialWithItem("machineshell_plantium", MAIN, new Block(create(Material.IRON).hardnessAndResistance(0.9F).notSolid())));
		r.register(makeSpecialWithItem("mega_furnace", MACHINES, new MachineFacingBlock(MegaFurnaceTileEntity::new, PlantTechConstants.MACHINETIER_MEGAFURNACE)));
		r.register(makeSpecialWithItem("plantfarm", MACHINES, new MachineBaseBlock(PlantFarmTileEntity::new, PlantTechConstants.MACHINETIER_PLANTFARM)));
		r.register(makeWithItem("plantium_block", BLOCKS, new Block(create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(0.9F))));
		r.register(makeSpecialWithItem("seedconstructor", MACHINES, new MachineFacingBlock(SeedconstructorTileEntity::new, PlantTechConstants.MACHINETIER_SEEDCONSTRUCTOR)));
		r.register(makeSpecialWithItem("seedsqueezer", MACHINES, new MachineFacingBlock(SeedSqueezerTileEntity::new, PlantTechConstants.MACHINETIER_SEEDSQUEEZER)));
		r.register(makeSpecialWithItem("solargenerator", MACHINES, new MachineBaseBlock(SolarGeneratorTileEntity::new, PlantTechConstants.MACHINETIER_SOLARGENERATOR)));
		r.register(makeSpecialWithItem("planttopia_teleporter", MACHINES, new MachineBaseBlock(PlantTopiaTeleporterTileEntity::new)));
		r.register(makeWithItem("planttopia_teleporter_end", MACHINES, new MachineTeleporterEndBlock(create(Material.IRON).hardnessAndResistance(0.5f))));
		r.register(makeWithItem("universal_soil", BLOCKS, new Block(create(Material.EARTH).hardnessAndResistance(0.5F))));
		r.register(makeWithItem("universal_soil_infused", BLOCKS, new Block(create(Material.EARTH).hardnessAndResistance(0.7F))));

		r.register(makeSpecialWithItem("dark_crystal_glasspane_cross", BLOCKS, new GlassPanePillar(create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F))));
		r.register(makeSpecialWithItem("dark_crystal_glasspane_middle", BLOCKS, new GlassPanePillar(create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F))));
		r.register(makeSpecialWithItem("dark_crystal_glasspane_end", BLOCKS, new GlassPaneEnd(create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), Color.WHITE.getRGB())));
		r.register(makeWithItem("dark_crystal_block", BLOCKS, new Block(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("dark_crystal_brick", BLOCKS, new Block(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("dark_crystal_tiling", BLOCKS, new Block(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("dark_crystal_fence", BLOCKS, new CustomFenceBlock(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("dark_crystal_lamp", BLOCKS, new Block(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F).setLightLevel((p) -> {return 15;}).notSolid())));
		r.register(makeSpecialWithItem("dark_crystal_door", BLOCKS, new CustomDoorBlock(create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F))));
		r.register(makeWithItem("dark_crystal_stairs", BLOCKS, new CustomStairsBlock(() -> DARK_CRYSTAL_BLOCK.getDefaultState(), create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F))));
		r.register(makeWithItem("dark_crystal_slab", BLOCKS, new CustomSlabBlock(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F))));
		r.register(makeWithItem("dark_crystal_ore", BLOCKS, new BaseOreBlock(create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0.2F), 1, 3)));

		r.register(makeSpecialWithItem("white_crystal_glasspane_cross", BLOCKS, new GlassPanePillar(create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F))));
		r.register(makeSpecialWithItem("white_crystal_glasspane_middle", BLOCKS, new GlassPanePillar(create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F))));
		r.register(makeSpecialWithItem("white_crystal_glasspane_end", BLOCKS, new GlassPaneEnd(create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F), Color.WHITE.getRGB())));
		r.register(makeWithItem("white_crystal_block", BLOCKS, new Block(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("white_crystal_brick", BLOCKS, new Block(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("white_crystal_tiling", BLOCKS, new Block(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("white_crystal_fence", BLOCKS, new CustomFenceBlock(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F))));
		r.register(makeWithItem("white_crystal_lamp", BLOCKS, new Block(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.9F).setLightLevel((p) -> {return 15;}).notSolid())));
		r.register(makeSpecialWithItem("white_crystal_door", BLOCKS, new CustomDoorBlock(create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.2F))));
		r.register(makeWithItem("white_crystal_stairs", BLOCKS, new CustomStairsBlock(() -> WHITE_CRYSTAL_BLOCK.getDefaultState(), create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F))));
		r.register(makeWithItem("white_crystal_slab", BLOCKS, new CustomSlabBlock(create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.2F))));
		r.register(makeWithItem("white_crystal_ore", BLOCKS, new BaseOreBlock(create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0.2F), 1, 3)));

		r.register(makeWithItem("wall_light", BLOCKS, new WallLight(create(Material.ROCK).sound(SoundType.GLASS))));

		r.register(makeSpecialWithItem("testblock", BLOCKS, new ObtainableNaturalPlants(4, 6)));
		

        r.register(makeHedge("hedge_oak_oak_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_spruce_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_birch_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_jungle_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_acacia_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_oak_dark_oak_dirt", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_oak_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_spruce_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_birch_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_jungle_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_acacia_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_spruce_dark_oak_dirt", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_oak_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_spruce_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_birch_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_jungle_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_acacia_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_birch_dark_oak_dirt", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_oak_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_spruce_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_birch_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_jungle_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_acacia_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES , Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_jungle_dark_oak_dirt", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_oak_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_spruce_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_birch_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_jungle_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_acacia_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_acacia_dark_oak_dirt", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_oak_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.OAK_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_spruce_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_birch_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_jungle_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_acacia_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.DIRT)));
        r.register(makeHedge("hedge_dark_oak_dark_oak_dirt", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.DIRT)));

        
        r.register(makeHedge("hedge_oak_oak_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_spruce_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_birch_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_jungle_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_acacia_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_oak_dark_oak_grass", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_oak_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_spruce_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_birch_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_jungle_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_acacia_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_spruce_dark_oak_grass", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_oak_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_spruce_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_birch_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_jungle_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_acacia_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_birch_dark_oak_grass", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_oak_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_spruce_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_birch_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_jungle_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_acacia_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES , Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_jungle_dark_oak_grass", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_oak_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_spruce_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_birch_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_jungle_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_acacia_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_acacia_dark_oak_grass", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_oak_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.OAK_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_spruce_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_birch_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_jungle_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_acacia_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.GRASS_BLOCK)));
        r.register(makeHedge("hedge_dark_oak_dark_oak_grass", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.GRASS_BLOCK)));
        
        r.register(makeHedge("hedge_oak_oak_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_spruce_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_birch_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_jungle_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_acacia_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_oak_dark_oak_podzol", BLOCKS, new Hedge(Blocks.OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_oak_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_spruce_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_birch_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_jungle_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_acacia_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_spruce_dark_oak_podzol", BLOCKS, new Hedge(Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_oak_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_spruce_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_birch_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_jungle_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_acacia_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_birch_dark_oak_podzol", BLOCKS, new Hedge(Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_oak_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_spruce_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_birch_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_jungle_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_acacia_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES , Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_jungle_dark_oak_podzol", BLOCKS, new Hedge(Blocks.JUNGLE_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_oak_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_spruce_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_birch_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_jungle_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_acacia_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_acacia_dark_oak_podzol", BLOCKS, new Hedge(Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_oak_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.OAK_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_spruce_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.SPRUCE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_birch_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.BIRCH_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_jungle_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_acacia_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LOG, Blocks.PODZOL)));
        r.register(makeHedge("hedge_dark_oak_dark_oak_podzol", BLOCKS, new Hedge(Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_LOG, Blocks.PODZOL)));

		r.register(makeSpecialWithItem("mutated_dandelion", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_poppy", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_blue_orchid", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_allium", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_azure_bluet", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_red_tulip", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_orange_tulip", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_white_tulip", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_pink_tulip", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_oxeye_daisy", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_cornflower", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_lily_of_the_valley", BLOCKS, new ObtainableNaturalPlants()));
		r.register(makeSpecialWithItem("mutated_lilac", BLOCKS, new ObtainableTallBushBlock()));
		r.register(makeSpecialWithItem("mutated_rose_bush", BLOCKS, new ObtainableTallBushBlock()));
		r.register(makeSpecialWithItem("mutated_peony", BLOCKS, new ObtainableTallBushBlock()));

		r.register(makeSpecial("machinebulbreprocessor_growing", new GrowingBlock(() -> ModBlocks.MACHINEBULBREPROCESSOR, true)));
		r.register(makeSpecial("machineshell_iron_growing", new GrowingBlock(() -> ModBlocks.MACHINESHELL_IRON, false)));
		r.register(makeSpecial("machineshell_plantium_growing", new GrowingBlock(() -> ModBlocks.MACHINESHELL_PLANTIUM, false)));
		r.register(makeSpecial("seedsqueezer_growing", new FacingGrowingBlock(() -> ModBlocks.SEEDSQUEEZER, true)));
		r.register(makeSpecial("chipalyzer_growing", new FacingGrowingBlock(() -> ModBlocks.CHIPALYZER, true)));
		r.register(makeSpecial("compressor_growing", new FacingGrowingBlock(() -> ModBlocks.COMPRESSOR, true)));
		r.register(makeSpecial("dna_cleaner_growing", new FacingGrowingBlock(() -> ModBlocks.DNA_CLEANER, true)));
		r.register(makeSpecial("dna_combiner_growing", new FacingGrowingBlock(() -> ModBlocks.DNA_COMBINER, true)));
		r.register(makeSpecial("dna_extractor_growing", new FacingGrowingBlock(() -> ModBlocks.DNA_EXTRACTOR, true)));
		r.register(makeSpecial("dna_remover_growing", new FacingGrowingBlock(() -> ModBlocks.DNA_REMOVER, true)));
		r.register(makeSpecial("identifier_growing", new FacingGrowingBlock(() -> ModBlocks.IDENTIFIER, true)));
		r.register(makeSpecial("infuser_growing", new FacingGrowingBlock(() -> ModBlocks.INFUSER, true)));
		r.register(makeSpecial("mega_furnace_growing", new FacingGrowingBlock(() -> ModBlocks.MEGAFURNACE, true)));
		r.register(makeSpecial("plantfarm_growing", new GrowingBlock(() -> ModBlocks.PLANTFARM, true)));
		r.register(makeSpecial("energy_supplier_growing", new GrowingBlock(() -> ModBlocks.ENERGY_SUPPLIER, true)));
		r.register(makeSpecial("seedconstructor_growing", new FacingGrowingBlock(() -> ModBlocks.SEEDCONSTRUCTOR, true)));
		r.register(makeSpecial("solargenerator_growing", new GrowingBlock(() -> ModBlocks.SOLARGENERATOR, true)));

		CROPS.clear();
		CropBaseBlock tempcrop;
		String name;
		for (CropEntry entry : PlantTechMain.getCropList().values())
		{
			name = entry.getName();
			tempcrop = new CropBaseBlock(name);
			CROPS.put(name, tempcrop);
			r.register(tempcrop);
		}
		SPECIAL_RENDER_BLOCKS.addAll(ModBlocks.CROPS.values().stream().map(b -> (Supplier<? extends Block>) () -> b).collect(Collectors.toList()));
	}

	static <B extends Block> B makeWithItem(String registryName, ItemGroup group, B block)
	{
		final B b = makeBlock(registryName, block);
		BLOCK_ITEM_SUPPLIERS.add(() -> new BlockItem(b, new Item.Properties().group(group)).setRegistryName(registryName));
		return b;
	}

	static <B extends Block> B makeSpecialWithItem(String registryName, ItemGroup group, B block)
	{
		final B b = makeSpecial(registryName, block);
		BLOCK_ITEM_SUPPLIERS.add(() -> new BlockItem(b, new Item.Properties().group(group)).setRegistryName(registryName));
		return b;
	}
	
	static <B extends Hedge> B makeHedge(String registryName, ItemGroup group, B block)
	{
		final B b = makeSpecial(registryName, block);
		BLOCK_ITEM_SUPPLIERS.add(() -> new BlockItem(b, new Item.Properties().group(group)).setRegistryName(registryName));
		HEDGE_BLOCKS.add(b);
		return b;
	}

	static <B extends Block> B makeBlock(String registryName, B block)
	{
		block.setRegistryName(registryName);
		return block;
	}

	static <B extends Block> B makeSpecial(String registryName, B block)
	{
		block.setRegistryName(registryName);
		SPECIAL_RENDER_BLOCKS.add(() -> block);
		return block;
	}
	
	

	public static void registerItemBlocks(IForgeRegistry<Item> registry)
	{
		for (Supplier<? extends Item> supplier : BLOCK_ITEM_SUPPLIERS)
		{
			registry.register(supplier.get());
		}
	}
}
