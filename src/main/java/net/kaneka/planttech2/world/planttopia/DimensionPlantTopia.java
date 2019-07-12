package net.kaneka.planttech2.world.planttopia;

import javax.annotation.Nullable;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.world.planttopia.biomes.PlantTopiaBiomeProvider;
import net.kaneka.planttech2.world.planttopia.biomes.PlantTopiaBiomeProviderSettings;
import net.minecraft.block.Blocks;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IRenderHandler;

public class DimensionPlantTopia extends Dimension {

	public DimensionPlantTopia(World world) {
		super(world, DimensionType.byName(new ResourceLocation(PlantTechMain.MODID,"planttopia")));
	}
	
	public DimensionPlantTopia(World world, DimensionType dimensionType) {
		super(world, dimensionType);
	}
	
	@Override
	public ChunkGenerator<? extends GenerationSettings> createChunkGenerator() {
		BiomeProviderType<PlantTopiaBiomeProviderSettings, PlantTopiaBiomeProvider> biomeprovidertype1 = new BiomeProviderType<>(PlantTopiaBiomeProvider::new,PlantTopiaBiomeProviderSettings::new);
		PlantTopiaBiomeProviderSettings overworldbiomeprovidersettings1 = biomeprovidertype1.createSettings().setGeneratorSettings(new PlantTopiaGenSettings()).setWorldInfo(this.world.getWorldInfo());
		PlantTopiaBiomeProvider biomeprovider = biomeprovidertype1.create(overworldbiomeprovidersettings1);
	
		ChunkGeneratorType<PlantTopiaGenSettings, ChunkGeneratorPlantTopia> chunkgeneratortype4 = new ChunkGeneratorType<>(ChunkGeneratorPlantTopia::new,true,PlantTopiaGenSettings::new);
		PlantTopiaGenSettings overworldgensettings1 = chunkgeneratortype4.createSettings();
		overworldgensettings1.setDefaultBlock(Blocks.STONE.getDefaultState());
		overworldgensettings1.setDefaultFluid(Blocks.WATER.getDefaultState());
		return chunkgeneratortype4.create(this.world, biomeprovider, overworldgensettings1);
	}
	
	@Override
	public float getCloudHeight() {
		return -1000;
	}
	
	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	public IRenderHandler getCloudRenderer() {
		return null;
	}
	
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		double d0 = MathHelper.frac((double)worldTime / 24000.0D - 0.25D);
		double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
		return (float)(d0 * 2.0D + d1) / 3.0F;
	}
	
	public boolean isSurfaceWorld() {
		return true;
	}
	
	@OnlyIn(Dist.CLIENT)
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
		float f = MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) * 2.0F + 0.5F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		float f1 = 0.7529412F;
		float f2 = 0.84705883F;
		float f3 = 1.0F;
		f1 = f1 * (f * 0.94F + 0.06F);
		f2 = f2 * (f * 0.94F + 0.06F);
		f3 = f3 * (f * 0.91F + 0.09F);
		return new Vec3d((double)f1, (double)f2, (double)f3);
	}
	
	@Nullable
	@Override
	public MusicTicker.MusicType getMusicType() {
		return null;
	}
	
	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return super.getSkyRenderer();
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
	@Override
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}
	
	@Override
	public boolean hasSkyLight() {
		return true;
	}

	@Override
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
		return null;
	}

	@Override
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
		return null;
	}
}