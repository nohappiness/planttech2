package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.entities.passive.TechGhoulEntity;
import net.kaneka.planttech2.entities.passive.TechPenguinEntity;
import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.kaneka.planttech2.entities.renderer.TechGhoulRenderer;
import net.kaneka.planttech2.entities.renderer.TechPenguinRenderer;
import net.kaneka.planttech2.entities.renderer.TechVillagerRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModRenderer
{
	public static void registerEntityRenderer()
	{
		RenderingRegistry.registerEntityRenderingHandler(TechVillagerEntity.getEntityType(), TechVillagerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(TechGhoulEntity.getEntityType(), TechGhoulRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(TechPenguinEntity.getEntityType(), TechPenguinRenderer::new);
	}
}
