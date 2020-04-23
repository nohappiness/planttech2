package net.kaneka.planttech2.dimensions.planttopia.biomes;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import java.util.HashMap;

public class RadiationWastelastBiome extends PlantTopiaBaseBiome
{
    private static HashMap<String, Float> FOG_RGB = new HashMap<String, Float>()
    {
        {
            /*put("red", 153.0F);
            put("green", 204.0F);
            put("blue", 255.0F);*/
            put("red", 255.0F);
            put("green", 204.0F);
            put("blue", 153.0F);
        }
    };

    public RadiationWastelastBiome()
    {
        super((new Biome.Builder())
                        .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRAVEL_CONFIG)
                        .precipitation(Biome.RainType.RAIN)
                        .category(Biome.Category.PLAINS)
                        .depth(0.1F)
                        .scale(0.4F)
                        .temperature(0.7F)
                        .downfall(0.8F)
                        .waterColor(0)
                        .waterFogColor(329011),
                PlantTopiaBaseBiome.BiomeRarity.COMMON,
                new ResourceLocation(PlantTechMain.MODID, "radiationwastelastbiome"),
                BiomeManager.BiomeType.DESERT,
                BiomeDictionary.Type.DEAD
        );
        DefaultBiomeFeatures.addDefaultFlowers(this);
    }

    @Override
    public float getFogDensity()
    {
        return 0.04F;
    }

    /*@Override
    public float getRadiationDensity()
    {
        return (float) 1 / (float) 10000;
    }*/

    @Override
    public BiomeRadiation getRadiationLevel()
    {
        return BiomeRadiation.HIGH;
    }

    @Override
    public HashMap<String, Float> getFogRGB()
    {
        return FOG_RGB;
    }
}
