package net.kaneka.planttech2.dimensions.planttopia.biomes;


import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class FlowerBiome extends PlantTopiaBaseBiome
{

	public FlowerBiome()
	{
		super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.END_STONE_CONFIG).precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS).depth(0.1F).scale(0.4F).temperature(0.7F).downfall(0.8F).waterColor(000000).waterFogColor(329011), 
				PlantTopiaBaseBiome.BiomeRarity.COMMON,
				new ResourceLocation(PlantTechMain.MODID, "flowerbiome"), 
				BiomeType.WARM, 
				BiomeDictionary.Type.PLAINS);
		DefaultBiomeFeatures.addDefaultFlowers(this);
	}
}
