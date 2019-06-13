package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.tileentity.TileEntityCrops;
import net.kaneka.planttech2.tileentity.cable.TileEntityCable;
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
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModTileEntities 
{
    public static final TileEntityType<TileEntityCrops> CROPS_TE = TileEntityType.Builder.create(TileEntityCrops::new).build(null); 
    public static final TileEntityType<TileEntityMegaFurnace> MEGAFURNACE_TE= TileEntityType.Builder.create(TileEntityMegaFurnace::new).build(null); 
    public static final TileEntityType<TileEntityIdentifier> IDENTIFIER_TE = TileEntityType.Builder.create(TileEntityIdentifier::new).build(null); 
    public static final TileEntityType<TileEntitySeedSqueezer> SEEDSQUEEZER_TE = TileEntityType.Builder.create(TileEntitySeedSqueezer::new).build(null); 
    public static final TileEntityType<TileEntitySolarGenerator> SOLARGENERATOR_TE = TileEntityType.Builder.create(TileEntitySolarGenerator::new).build(null); 
    public static final TileEntityType<TileEntityPlantFarm> PLANTFARM_TE = TileEntityType.Builder.create(TileEntityPlantFarm::new).build(null); 
    public static final TileEntityType<TileEntityCable> CABLE_TE = TileEntityType.Builder.create(TileEntityCable::new).build(null); 
    public static final TileEntityType<TileEntityDNAExtractor> DNAEXTRACTOR_TE = TileEntityType.Builder.create(TileEntityDNAExtractor::new).build(null); 
    public static final TileEntityType<TileEntityDNARemover> DNAREMOVER_TE = TileEntityType.Builder.create(TileEntityDNARemover::new).build(null); 
    public static final TileEntityType<TileEntityDNACleaner> DNACLEANER_TE = TileEntityType.Builder.create(TileEntityDNACleaner::new).build(null); 
    public static final TileEntityType<TileEntityDNACombiner> DNACOMBINER_TE = TileEntityType.Builder.create(TileEntityDNACombiner::new).build(null); 
    public static final TileEntityType<TileEntitySeedconstructor> SEEDCONSTRUCTOR_TE = TileEntityType.Builder.create(TileEntitySeedconstructor::new).build(null); 
    public static final TileEntityType<TileEntityCompressor> COMPRESSOR_TE = TileEntityType.Builder.create(TileEntityCompressor::new).build(null); 
    public static final TileEntityType<TileEntityEnergyStorage> ENERGYSTORAGE_TE = TileEntityType.Builder.create(TileEntityEnergyStorage::new).build(null); 
    public static final TileEntityType<TileEntityInfuser> INFUSER_TE = TileEntityType.Builder.create(TileEntityInfuser::new).build(null); 
    
    
    
	public static final void register(IForgeRegistry<TileEntityType<?>> registry)
	{
	    registry.registerAll(CROPS_TE.setRegistryName(PlantTechMain.MODID,  "tileentitycrops"), 
		    		 MEGAFURNACE_TE.setRegistryName(PlantTechMain.MODID,  "tileentitymegafurnace"), 
		    		 IDENTIFIER_TE.setRegistryName(PlantTechMain.MODID,  "tileentityidentifier"), 
		    		 SEEDSQUEEZER_TE.setRegistryName(PlantTechMain.MODID,  "tileentityseedsqueezer"), 
		    		 SOLARGENERATOR_TE.setRegistryName(PlantTechMain.MODID,  "tileentitysolargenerator"), 
		    		 PLANTFARM_TE.setRegistryName(PlantTechMain.MODID,  "tileentityplantfarm"), 
		    		 CABLE_TE.setRegistryName(PlantTechMain.MODID,  "tileentitycable"), 
		    		 DNAEXTRACTOR_TE.setRegistryName(PlantTechMain.MODID,  "tileentitydnaextractor"), 
		    		 DNAREMOVER_TE.setRegistryName(PlantTechMain.MODID,  "tileentitydnaremover"), 
		    		 DNACLEANER_TE.setRegistryName(PlantTechMain.MODID,  "tileentitydnacleaner"), 
		    		 DNACOMBINER_TE.setRegistryName(PlantTechMain.MODID,  "tileentitydnacombiner"), 
		    		 SEEDCONSTRUCTOR_TE.setRegistryName(PlantTechMain.MODID,  "tileentityseedconstructor"), 
		    		 COMPRESSOR_TE.setRegistryName(PlantTechMain.MODID,  "tileentitycompressor"), 
		    		 ENERGYSTORAGE_TE.setRegistryName(PlantTechMain.MODID,  "tileentityenergystorage"), 
		    		 INFUSER_TE.setRegistryName(PlantTechMain.MODID,  "tileentityinfuser"));
		
	}

}
