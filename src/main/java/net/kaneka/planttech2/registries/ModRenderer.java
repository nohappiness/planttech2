package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.kaneka.planttech2.entities.renderer.TechVillagerRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModRenderer
{
	public static void registerEntityRenderer()
	{
		RenderingRegistry.registerEntityRenderingHandler(TechVillagerEntity.class, manager -> new TechVillagerRenderer(manager));
	}
}
