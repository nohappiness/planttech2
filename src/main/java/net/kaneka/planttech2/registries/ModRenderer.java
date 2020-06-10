package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.entities.neutral.TechGhoulEntity;
import net.kaneka.planttech2.entities.passive.TechPenguinEntity;
import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.kaneka.planttech2.entities.renderer.TechGhoulRenderer;
import net.kaneka.planttech2.entities.renderer.TechPenguinRenderer;
import net.kaneka.planttech2.entities.renderer.TechVillagerRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModRenderer
{
	public static void registerEntityRenderer()
	{
		RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends TechVillagerEntity>) ModEntityTypes.TECHVILLAGERENTITY, TechVillagerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends TechGhoulEntity>) ModEntityTypes.TECHGHOULENTITY, TechGhoulRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends TechPenguinEntity>) ModEntityTypes.TECHPENGUINENTITY, TechPenguinRenderer::new);
	}
}
