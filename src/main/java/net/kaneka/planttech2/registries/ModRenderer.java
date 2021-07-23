package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.entities.renderer.TechGhoulRenderer;
import net.kaneka.planttech2.entities.renderer.TechPenguinRenderer;
import net.kaneka.planttech2.entities.renderer.TechVillagerRenderer;
import net.minecraftforge.fmlclient.registry.RenderingRegistry;

public class ModRenderer
{
	public static void registerEntityRenderer()
	{
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.TECHVILLAGERENTITY, TechVillagerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.TECHGHOULENTITY, TechGhoulRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.TECHPENGUINENTITY, TechPenguinRenderer::new);
	}
}
