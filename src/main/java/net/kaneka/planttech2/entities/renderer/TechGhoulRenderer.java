package net.kaneka.planttech2.entities.renderer;


import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.entities.models.TechGhoulModel;
import net.kaneka.planttech2.entities.models.TechVillagerModel;
import net.kaneka.planttech2.entities.passive.TechGhoulEntity;
import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TechGhoulRenderer extends MobRenderer<TechGhoulEntity, TechGhoulModel<TechGhoulEntity>>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(PlantTechMain.MODID, "textures/entities/techghoul/planttech_ghoul.png");

	public TechGhoulRenderer(EntityRendererManager manager)
	{
		super(manager, new TechGhoulModel(), 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(TechGhoulEntity entity)
	{
		return TEXTURE;
	}
}
