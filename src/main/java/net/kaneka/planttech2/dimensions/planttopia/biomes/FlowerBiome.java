package net.kaneka.planttech2.dimensions.planttopia.biomes;


import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;

import java.util.HashMap;

public class FlowerBiome extends PlantTopiaBaseBiome
{
	private static HashMap<String, Float> FOG_RGB = new HashMap<String, Float>()
	{
		{
			put("red", 230.0F);
			put("green", 255.0F);
			put("blue", 230.0F);
		}
	};
	public FlowerBiome()
	{
		super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.END_STONE_CONFIG).precipitation(Biome.RainType.RAIN).category(Biome.Category.PLAINS).depth(0.1F).scale(0.4F).temperature(0.7F).downfall(0.8F).waterColor(000000).waterFogColor(329011), 
				PlantTopiaBaseBiome.BiomeRarity.COMMON,
				new ResourceLocation(PlantTechMain.MODID, "flowerbiome"), 
				BiomeType.WARM, 
				BiomeDictionary.Type.PLAINS);
		DefaultBiomeFeatures.addDefaultFlowers(this);
	}

	@Override
	public float getFogDensity()
	{
		return 0.0125F;
	}

	@Override
	public HashMap<String, Float> getFogRGB()
	{
		return FOG_RGB;
	}
}
