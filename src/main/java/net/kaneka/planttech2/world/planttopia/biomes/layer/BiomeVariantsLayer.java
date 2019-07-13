package net.kaneka.planttech2.world.planttopia.biomes.layer;

import net.kaneka.planttech2.registries.ModBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum BiomeVariantsLayer implements IC1Transformer {
    INSTANCE;

	private static final int FANTASIA_FOREST_ID = Registry.BIOME.getId(ModBiomes.PLANTTOPIA_FOREST);
    private static final int FANTASIA_DENSE_FOREST_ID = Registry.BIOME.getId(ModBiomes.PLANTTOPIA_FOREST);

    private BiomeVariantsLayer() {
    }

    BiomeVariantsUtil util = new BiomeVariantsUtil();
    public int apply(INoiseRandom context, int centerBiomeID) {
        if(util.getBiomesWithVariantsList().contains(Registry.BIOME.getByValue(centerBiomeID))){
            if(context.random(4)==0){
                return Registry.BIOME.getId(util.getBiomeVariant(Registry.BIOME.getByValue(centerBiomeID)));
            }
        }
        return centerBiomeID;
    }

    private int getRandomVariantID(){
        return FANTASIA_DENSE_FOREST_ID;
    }

	@Override
	public int func_215721_a(int p_215721_1_) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int func_215722_b(int p_215722_1_) {
		// TODO Auto-generated method stub
		return 0;
	}
}