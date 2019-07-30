package net.kaneka.planttech2.world.structure.tech;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.kaneka.planttech2.registries.ModStructures;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class TechVillageStructure extends Structure<TechVillageConfig>
{

	public TechVillageStructure(Function<Dynamic<?>, ? extends TechVillageConfig> function)
	{
		super(function);
	}

	protected int getSeedModifier()
	{
		return 987654321;
	}
	
	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
	      int i = chunkGenerator.getSettings().getVillageDistance();
	      int j = chunkGenerator.getSettings().getVillageSeparation();
	      int k = x + i * spacingOffsetsX;
	      int l = z + i * spacingOffsetsZ;
	      int i1 = k < 0 ? k - i + 1 : k;
	      int j1 = l < 0 ? l - i + 1 : l;
	      int k1 = i1 / i;
	      int l1 = j1 / i;
	      ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, getSeedModifier());
	      k1 = k1 * i;
	      l1 = l1 * i;
	      k1 = k1 + random.nextInt(i - j);
	      l1 = l1 + random.nextInt(i - j);
	      return new ChunkPos(k1, l1);
	   }

	@Override
	   public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
	      ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
	      if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
	         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9));
	         return chunkGen.hasStructure(biome, ModStructures.TECHVILLAGE);
	      } else {
	         return false;
	      }
	   }
	
	@Override
	public IStartFactory getStartFactory()
	{
		return TechVillageStructure.Start::new;
	}

	@Override
	public String getStructureName()
	{
		return "techvillage";
	}

	@Override
	public int getSize()
	{
		return 10;
	}

	public static class Start extends MarginedStructureStart
	{

		public Start(Structure<?> structure, int chunkX, int chunkZ, Biome biome, MutableBoundingBox mutableBB, int reference, long seed)
		{
			super(structure, chunkX, chunkZ, biome, mutableBB, reference, seed);
		}

		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
		{
			BlockPos blockpos = new BlockPos(chunkX * 16, 90, chunkZ * 16);
			TechVillagePieces.init(generator, templateManagerIn, blockpos, this.components, this.rand);
			this.recalculateStructureSize();

		}
	}

}
