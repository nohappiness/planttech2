package net.kaneka.planttech2.world.planttopia.biomes.layer;

import net.kaneka.planttech2.registries.ModBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum PlantTopiaRareBiomeLayer implements IC1Transformer {
   INSTANCE;

   private static final int PLAINS = Registry.BIOME.getId(ModBiomes.PLANTTOPIA_PLAINS);
   private static final int SUNFLOWER_PLAINS = Registry.BIOME.getId(ModBiomes.PLANTTOPIA_FOREST);

   public int apply(INoiseRandom context, int value) {
	  int i = context.random(57) == 0 && value == PLAINS ? SUNFLOWER_PLAINS : value;
	  return i;
   }
}