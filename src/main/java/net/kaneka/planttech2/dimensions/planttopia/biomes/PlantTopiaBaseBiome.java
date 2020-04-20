package net.kaneka.planttech2.dimensions.planttopia.biomes;

import net.kaneka.planttech2.registries.ModBiomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;

import java.util.HashMap;

abstract public class PlantTopiaBaseBiome extends Biome
{
	private BiomeType biomeType; 
	private BiomeDictionary.Type[] biomDictType; 

	public PlantTopiaBaseBiome(Builder biomeBuilder, BiomeRarity rarity, ResourceLocation resLoc, BiomeType biomeType, BiomeDictionary.Type... biomDictType)
	{
		super(biomeBuilder);
		this.biomeType = biomeType;
		this.biomDictType = biomDictType;
		
		setRegistryName(resLoc); 
		
		ModBiomes.BIOMES.add(this);
		if(rarity.equals(BiomeRarity.COMMON))
		{
			ModBiomes.COMMON_BIOMES.add(this);
		}
		else if(rarity.equals(BiomeRarity.UNCOMMON))
		{
			ModBiomes.UNCOMMON_BIOMES.add(this);
		}
		else if(rarity.equals(BiomeRarity.RARE))
		{
			ModBiomes.RARE_BIOMES.add(this);
		}
	} 
	
	public BiomeType getBiomeType()
	{
		return biomeType; 
	}
	
	public BiomeDictionary.Type[] getBiomeDictionaryType()
	{
		return biomDictType; 
	}

	public enum BiomeRarity 
	{
		COMMON(0), 
		UNCOMMON(1), 
		RARE(2);
		
		int id; 
		BiomeRarity(int id)
		{
			this.id = id; 
		}
	}

	public abstract float getFogDensity();

	public abstract HashMap<String, Float> getFogRGB();

	public float getFogRed()
	{
		return getFogRGB().get("red");
	}

	public float getFogGreen()
	{
		return getFogRGB().get("green");
	}

	public float getFogBlue()
	{
		return getFogRGB().get("blue");
	}
}
