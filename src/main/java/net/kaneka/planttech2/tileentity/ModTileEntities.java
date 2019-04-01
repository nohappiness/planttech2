package net.kaneka.planttech2.tileentity;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.tileentity.cable.TileEntityCable;
import net.kaneka.planttech2.tileentity.machine.TileEntityCompressor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACleaner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNACombiner;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNAExtractor;
import net.kaneka.planttech2.tileentity.machine.TileEntityDNARemover;
import net.kaneka.planttech2.tileentity.machine.TileEntityEnergyStorage;
import net.kaneka.planttech2.tileentity.machine.TileEntityIdentifier;
import net.kaneka.planttech2.tileentity.machine.TileEntityMegaFurnace;
import net.kaneka.planttech2.tileentity.machine.TileEntityPlantFarm;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedSqueezer;
import net.kaneka.planttech2.tileentity.machine.TileEntitySeedconstructor;
import net.kaneka.planttech2.tileentity.machine.TileEntitySolarGenerator;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModTileEntities 
{
    public static final TileEntityType<?> CROPS_TE = TileEntityType.Builder.create(TileEntityCrops::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitycrops"); 
    public static final TileEntityType<?> MEGAFURNACE_TE= TileEntityType.Builder.create(TileEntityMegaFurnace::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitymegafurnace"); 
    public static final TileEntityType<?> IDENTIFIER_TE = TileEntityType.Builder.create(TileEntityIdentifier::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityidentifier"); 
    public static final TileEntityType<?> SEEDSQUEEZER_TE = TileEntityType.Builder.create(TileEntitySeedSqueezer::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityseedsqueezer"); 
    public static final TileEntityType<?> SOLARGENERATOR_TE = TileEntityType.Builder.create(TileEntitySolarGenerator::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitysolargenerator"); 
    public static final TileEntityType<?> PLANTFARM_TE = TileEntityType.Builder.create(TileEntityPlantFarm::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityplantfarm"); 
    public static final TileEntityType<?> CABLE_TE = TileEntityType.Builder.create(TileEntityCable::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitycable"); 
    public static final TileEntityType<?> DNAEXTRACTOR_TE = TileEntityType.Builder.create(TileEntityDNAExtractor::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitydnaextractor"); 
    public static final TileEntityType<?> DNAREMOVER_TE = TileEntityType.Builder.create(TileEntityDNARemover::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitydnaremover"); 
    public static final TileEntityType<?> DNACLEANER_TE = TileEntityType.Builder.create(TileEntityDNACleaner::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitydnacleaner"); 
    public static final TileEntityType<?> DNACOMBINER_TE = TileEntityType.Builder.create(TileEntityDNACombiner::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitydnacombiner"); 
    public static final TileEntityType<?> SEEDCONSTRUCTOR_TE = TileEntityType.Builder.create(TileEntitySeedconstructor::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityseedconstructor"); 
    public static final TileEntityType<?> COMPRESSOR_TE = TileEntityType.Builder.create(TileEntityCompressor::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentitycompressor"); 
    public static final TileEntityType<?> ENERGYSTORAGE_TE = TileEntityType.Builder.create(TileEntityEnergyStorage::new).build(null).setRegistryName(PlantTechMain.MODID,  "tileentityenergystorage"); 
    
    
    
	public static final void register(IForgeRegistry<TileEntityType<?>> registry)
	{
	    registry.registerAll(CROPS_TE, 
		    		 MEGAFURNACE_TE, 
		    		 IDENTIFIER_TE, 
		    		 SEEDSQUEEZER_TE, 
		    		 SOLARGENERATOR_TE, 
		    		 PLANTFARM_TE, 
		    		 CABLE_TE, 
		    		 DNAEXTRACTOR_TE, 
		    		 DNAREMOVER_TE, 
		    		 DNACLEANER_TE, 
		    		 DNACOMBINER_TE, 
		    		 SEEDCONSTRUCTOR_TE, 
		    		 COMPRESSOR_TE);
		
	}

}
