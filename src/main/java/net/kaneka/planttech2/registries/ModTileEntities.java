package net.kaneka.planttech2.registries;

import com.mojang.datafixers.types.Type;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.kaneka.planttech2.tileentity.cable.CableTileEntity;
import net.kaneka.planttech2.tileentity.cable.TestCableTileEntity;
import net.kaneka.planttech2.tileentity.machine.ChipalyzerTileEntity;
import net.kaneka.planttech2.tileentity.machine.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;
import javax.annotation.Nullable;

@ObjectHolder(PlantTechMain.MODID)
public class ModTileEntities
{
	@ObjectHolder("tileentitycrops") public static TileEntityType<CropsTileEntity> CROPS_TE;
	@ObjectHolder("tileentitymegafurnace") public static TileEntityType<MegaFurnaceTileEntity> MEGAFURNACE_TE;
	@ObjectHolder("tileentityidentifier") public static TileEntityType<IdentifierTileEntity> IDENTIFIER_TE;
	@ObjectHolder("tileentityseedsqueezer") public static TileEntityType<SeedSqueezerTileEntity> SEEDSQUEEZER_TE;
	@ObjectHolder("tileentitysolargenerator") public static TileEntityType<SolarGeneratorTileEntity> SOLARGENERATOR_TE;
	@ObjectHolder("tileentityplantfarm") public static TileEntityType<PlantFarmTileEntity> PLANTFARM_TE;
	@ObjectHolder("tileentitycable") public static TileEntityType<TestCableTileEntity> CABLE_TE;
	@ObjectHolder("tileentitydnaextractor") public static TileEntityType<DNAExtractorTileEntity> DNAEXTRACTOR_TE;
	@ObjectHolder("tileentitydnaremover") public static TileEntityType<DNARemoverTileEntity> DNAREMOVER_TE;
	@ObjectHolder("tileentitydnacleaner") public static TileEntityType<DNACleanerTileEntity> DNACLEANER_TE;
	@ObjectHolder("tileentitydnacombiner") public static TileEntityType<DNACombinerTileEntity> DNACOMBINER_TE;
	@ObjectHolder("tileentityseedconstructor") public static TileEntityType<SeedconstructorTileEntity> SEEDCONSTRUCTOR_TE;
	@ObjectHolder("tileentitycompressor") public static TileEntityType<CompressorTileEntity> COMPRESSOR_TE;
	@ObjectHolder("tileentityenergystorage") public static TileEntityType<EnergyStorageTileEntity> ENERGYSTORAGE_TE;
	@ObjectHolder("tileentityinfuser") public static TileEntityType<InfuserTileEntity> INFUSER_TE;
	@ObjectHolder("tileentitychipalyzer") public static TileEntityType<ChipalyzerTileEntity> CHIPALYZER_TE;
	@ObjectHolder("tileentitymachinebulbreprocessor") public static TileEntityType<MachineBulbReprocessorTileEntity> MACHINEBULBREPROCESSOR_TE;
	@ObjectHolder("tileentitycrops") public static TileEntityType<PlantTopiaTeleporterTileEntity> PLANTTOPIATELEPORTER_TE;
	@ObjectHolder("tileentityenergysupplier") public static TileEntityType<EnergySupplierTileEntity> ENERGY_SUPPLIER_TE;
	@ObjectHolder("tileentitycropauragenerator") public static TileEntityType<CropAuraGeneratorTileEntity> CROP_AURA_GENERATOR_TE;

	public static void register(IForgeRegistry<TileEntityType<?>> registry)
	{
		registry.register(make("tileentitycrops", CropsTileEntity::new, ModBlocks.CROPS.values().toArray(new Block[0])));
		registry.register(make("tileentitymegafurnace", MegaFurnaceTileEntity::new, ModBlocks.MEGAFURNACE));
		registry.register(make("tileentityidentifier", IdentifierTileEntity::new, ModBlocks.IDENTIFIER));
		registry.register(make("tileentityseedsqueezer", SeedSqueezerTileEntity::new, ModBlocks.SEEDSQUEEZER));
		registry.register(make("tileentitysolargenerator", SolarGeneratorTileEntity::new, ModBlocks.SOLARGENERATOR));
		registry.register(make("tileentityplantfarm", PlantFarmTileEntity::new, ModBlocks.PLANTFARM));
		registry.register(make("tileentitycable", TestCableTileEntity::new, ModBlocks.CABLE));
		registry.register(make("tileentitydnaextractor", DNAExtractorTileEntity::new, ModBlocks.DNA_EXTRACTOR));
		registry.register(make("tileentitydnaremover", DNARemoverTileEntity::new, ModBlocks.DNA_REMOVER));
		registry.register(make("tileentitydnacleaner", DNACleanerTileEntity::new, ModBlocks.DNA_CLEANER));
		registry.register(make("tileentitydnacombiner", DNACombinerTileEntity::new, ModBlocks.DNA_COMBINER));
		registry.register(make("tileentityseedconstructor", SeedconstructorTileEntity::new, ModBlocks.SEEDCONSTRUCTOR));
		registry.register(make("tileentitycompressor", CompressorTileEntity::new, ModBlocks.COMPRESSOR));
		registry.register(make("tileentityenergystorage", EnergyStorageTileEntity::new, ModBlocks.ENERGYSTORAGE));
		registry.register(make("tileentityinfuser", InfuserTileEntity::new, ModBlocks.INFUSER));
		registry.register(make("tileentitychipalyzer", ChipalyzerTileEntity::new, ModBlocks.CHIPALYZER));
		registry.register(make("tileentitymachinebulbreprocessor", MachineBulbReprocessorTileEntity::new, ModBlocks.MACHINEBULBREPROCESSOR));
		registry.register(make("tileentityplanttopiateleporter", MachineBulbReprocessorTileEntity::new, ModBlocks.PLANTTOPIA_TELEPORTER, ModBlocks.PLANTTOPIA_TELEPORTER_END));
		registry.register(make("tileentityenergysupplier", EnergySupplierTileEntity::new, ModBlocks.ENERGY_SUPPLIER));
		registry.register(make("tileentitycropauragenerator", CropAuraGeneratorTileEntity::new, ModBlocks.CROP_AURA_GENERATOR));
	}

	static <T extends TileEntity> TileEntityType<T> make(String registryName, Supplier<T> factory, Block... validBlocks) {
		return make(registryName, factory, null, validBlocks);
	}

	static <T extends TileEntity> TileEntityType<T> make(String registryName, Supplier<T> factory, Type<?> dataFixer, Block... validBlocks) {
		TileEntityType<T> result = TileEntityType.Builder.of(factory, validBlocks).build(dataFixer);
		result.setRegistryName(registryName);
		return result;
	}
}
