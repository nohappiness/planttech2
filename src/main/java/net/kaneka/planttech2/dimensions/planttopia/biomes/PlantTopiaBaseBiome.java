package net.kaneka.planttech2.dimensions.planttopia.biomes;

import net.kaneka.planttech2.registries.ModBiomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;


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
		 
		PlantTopiaBiomeDecorator.addCrystalOres(this);
		PlantTopiaBiomeDecorator.addBiomassLake(this);
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

	/**
	 * @return -1 for no changes
	 */
	public float getFogDensity()
	{
		return -1;
	}

	/**
	 * When the effect reaches 1, the player starts getting negative effects
	 * @return get the amount of radiation effect increase on player whilst the player is in this biome
	 */
//	public abstract float getRadiationDensity();

	public abstract BiomeRadiation getRadiationLevel();

	public float[] getFogRGB()
	{
		return new float[] {-1, -1, -1};
	}

	public float getFogRed()
	{
		return getFogRGB()[0];
	}

	public float getFogGreen()
	{
		return getFogRGB()[1];
	}

	public float getFogBlue()
	{
		return getFogRGB()[2];
	}
}
