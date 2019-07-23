package net.kaneka.planttech2.world.structure.tech;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;

public class TechVillageConfig implements IFeatureConfig
{
	public final double probability;

	public TechVillageConfig(double probability)
	{
		this.probability = probability;
	}

	public <T> Dynamic<T> serialize(DynamicOps<T> ops)
	{
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("probability"), ops.createDouble(this.probability))));
	}

	public static <T> TechVillageConfig deserialize(Dynamic<T> dyn)
	{
		float f = dyn.get("probability").asFloat(0.0F);
		return new TechVillageConfig((double) f);
	}

}
