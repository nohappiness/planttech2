package net.kaneka.planttech2.world.structure.tech;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.kaneka.planttech2.registries.ModStructures;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class TechVillageStructure extends ScatteredStructure<TechVillageConfig>
{

	public TechVillageStructure(Function<Dynamic<?>, ? extends TechVillageConfig> function)
	{
		super(function);
	}

	@Override
	protected int getSeedModifier()
	{
		return 153947628;
	}
	
	@Override
	public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) 
	{
	      ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
	      if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
	         int i = chunkPosX >> 4;
	         int j = chunkPosZ >> 4;
	         rand.setSeed((long)(i ^ j << 4) ^ chunkGen.getSeed());
	         rand.nextInt();
	         if (rand.nextInt(2) != 0) {
	            return false;
	            
	         }

	         Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9));
	         if (chunkGen.hasStructure(biome, ModStructures.TECHVILLAGE)) 
	         {
	            return true;
	         }
	      }
	      return false;
	      
	   }

	@Override
	public IStartFactory getStartFactory()
	{
		return TechVillageStructure.Start::new;
	}

	@Override
	public String getStructureName()
	{
		return "tech_village";
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
