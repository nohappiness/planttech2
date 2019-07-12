package net.kaneka.planttech2.world.planttopia.biomes.layer;

import net.kaneka.planttech2.registries.ModBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum PlantTopiaRiverMixLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    private static final int FROZEN_RIVER = Registry.BIOME.getId(Biomes.FROZEN_RIVER);
    private static final int SNOWY_TUNDRA = Registry.BIOME.getId(Biomes.SNOWY_TUNDRA);
    private static final int MUSHROOM_FIELDS = Registry.BIOME.getId(Biomes.MUSHROOM_FIELDS);
    private static final int MUSHROOM_FIELD_SHORE = Registry.BIOME.getId(Biomes.MUSHROOM_FIELD_SHORE);
    private static final int RIVER = Registry.BIOME.getId(ModBiomes.PLANTTOPIA_RIVER);

    private PlantTopiaRiverMixLayer() {
    }

    public int apply(INoiseRandom p_202709_1_, IArea p_202709_3_, IArea p_202709_4_, int p_202709_5_, int p_202709_6_) {
        int biomeId = p_202709_3_.getValue(p_202709_5_, p_202709_6_);
        int riverId = p_202709_4_.getValue(p_202709_5_, p_202709_6_);
        if (PlantTopiaLayerUtil.isOcean(biomeId)) {
            return biomeId;
        } else if (riverId == RIVER) {
            if (biomeId == SNOWY_TUNDRA) {
                return FROZEN_RIVER;
            } else {
                return biomeId != MUSHROOM_FIELDS && biomeId != MUSHROOM_FIELD_SHORE ? riverId & 255 : MUSHROOM_FIELD_SHORE;
            }
        } else {
            return biomeId;
        }
    }
}