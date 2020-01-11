package net.kaneka.planttech2.entities.renderer;


import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.entities.models.TechVillagerModel;
import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TechVillagerRenderer extends MobRenderer<TechVillagerEntity, TechVillagerModel<TechVillagerEntity>> 
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(PlantTechMain.MODID, "textures/entities/techvillager/planttech_scientist.png");

	public TechVillagerRenderer(EntityRendererManager manager)
	{
		super(manager, new TechVillagerModel<>(0.0F), 0.5F);
		this.addLayer(new HeadLayer<>(this));
	}
	
	@Override
	public ResourceLocation getEntityTexture(TechVillagerEntity entity)
	{
		return TEXTURE;
	}
}
