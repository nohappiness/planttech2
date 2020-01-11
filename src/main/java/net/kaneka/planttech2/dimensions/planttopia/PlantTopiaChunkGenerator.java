package net.kaneka.planttech2.dimensions.planttopia;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.INoiseGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.WorldGenRegion;

public class PlantTopiaChunkGenerator extends ChunkGenerator<PlantTopiaChunkGenerator.Config> 
{
	protected final SharedSeedRandom randomSeed;
	private final INoiseGenerator surfaceDepthNoise;
	
	public PlantTopiaChunkGenerator(IWorld world, BiomeProvider provider)
	{
		super(world, provider, Config.createDefault());
		this.randomSeed = new SharedSeedRandom(this.seed);
		boolean usePerlin = true; 
		this.surfaceDepthNoise = (INoiseGenerator)(usePerlin ? new PerlinNoiseGenerator(this.randomSeed, 3, 0) : new OctavesNoiseGenerator(this.randomSeed, 3, 0));
	}

	@Override
	public void func_225551_a_(WorldGenRegion region, IChunk chunk)
	{
		ChunkPos chunkpos = chunk.getPos();
	      int i = chunkpos.x;
	      int j = chunkpos.z;
	      SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
	      sharedseedrandom.setBaseChunkSeed(i, j);
	      ChunkPos chunkpos1 = chunk.getPos();
	      int k = chunkpos1.getXStart();
	      int l = chunkpos1.getZStart();
	      double d0 = 0.0625D;
	      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

	      for(int i1 = 0; i1 < 16; ++i1) {
	         for(int j1 = 0; j1 < 16; ++j1) {
	            int k1 = k + i1;
	            int l1 = l + j1;
	            int i2 = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, i1, j1) + 1;
	            double d1 = this.surfaceDepthNoise.noiseAt((double)k1 * 0.0625D, (double)l1 * 0.0625D, 0.0625D, (double)i1 * 0.0625D) * 15.0D;
	           region.func_226691_t_(blockpos$mutable.setPos(k + i1, i2, l + j1)).buildSurface(sharedseedrandom, chunk, k1, l1, i2, d1, this.getSettings().getDefaultBlock(), this.getSettings().getDefaultFluid(), this.getSeaLevel(), this.world.getSeed());
	         }
	      }

	      this.makeBedrock(chunk, sharedseedrandom);
	   }

	   protected void makeBedrock(IChunk chunkIn, Random rand) {
	      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
	      int i = chunkIn.getPos().getXStart();
	      int j = chunkIn.getPos().getZStart();
	      Config t = this.getSettings();
	      int k = t.getBedrockFloorHeight();
	      int l = t.getBedrockRoofHeight();

	      for(BlockPos blockpos : BlockPos.getAllInBoxMutable(i, 0, j, i + 15, 0, j + 15)) {
	         if (l > 0) {
	            for(int i1 = l; i1 >= l - 4; --i1) {
	               if (i1 >= l - rand.nextInt(5)) {
	                  chunkIn.setBlockState(blockpos$mutable.setPos(blockpos.getX(), i1, blockpos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
	               }
	            }
	         }

	         if (k < 256) {
	            for(int j1 = k + 4; j1 >= k; --j1) {
	               if (j1 <= k + rand.nextInt(5)) {
	                  chunkIn.setBlockState(blockpos$mutable.setPos(blockpos.getX(), j1, blockpos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
	               }
	            }
	         }
	      }

	   }

	@Override
	public int getGroundHeight()
	{
		return world.getSeaLevel() + 1;
	}

	@Override
	public void makeBase(IWorld world, IChunk chunk)
	{
		
	}

	@Override
	public int func_222529_a(int p_222529_1_, int p_222529_2_, Type heightmapType)
	{
		return 0;
	}
	


	public static class Config extends GenerationSettings
	{
		public static Config createDefault() 
		{
            Config config = new Config();
            config.setDefaultBlock(Blocks.DIAMOND_BLOCK.getDefaultState());
            config.setDefaultFluid(Blocks.LAVA.getDefaultState());
            return config;
        }
	}
}
