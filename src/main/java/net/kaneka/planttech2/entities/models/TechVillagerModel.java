package net.kaneka.planttech2.entities.models;

import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TechVillagerModel<T extends Entity> extends VillagerModel<T>
{
	public TechVillagerModel(float scale)
	{
		super(scale);
	}
}
