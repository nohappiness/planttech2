package net.kaneka.planttech2.entities.renderer;


import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.entities.models.TechPenguinModel;
import net.kaneka.planttech2.entities.passive.TechPenguinEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TechPenguinRenderer extends MobRenderer<TechPenguinEntity, TechPenguinModel<TechPenguinEntity>>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(PlantTechMain.MODID, "textures/entities/techpenguin/planttech_penguin.png");

	public TechPenguinRenderer(EntityRendererManager manager)
	{
		super(manager, new TechPenguinModel(), 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(TechPenguinEntity entity)
	{
		return TEXTURE;
	}
}
