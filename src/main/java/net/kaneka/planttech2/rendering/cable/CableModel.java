package net.kaneka.planttech2.rendering.cable;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

public class CableModel implements IUnbakedModel
{
    public static final ResourceLocation TEXTURE_CABLE = new ResourceLocation("planttech2:blocks/cables/cable");
    public static final ResourceLocation TEXTURE_IN = new ResourceLocation("planttech2:blocks/cables/connection_in");
    public static final ResourceLocation TEXTURE_OUT = new ResourceLocation("planttech2:blocks/cables/connection_out");
    public static final ResourceLocation TEXTURE_BOTH = new ResourceLocation("planttech2:blocks/cables/connection_both");

    public static final ModelResourceLocation MODEL_CORE = new ModelResourceLocation("planttech2:cable/cable_core");

    public static final ModelResourceLocation MODEL_UP_CABLE = new ModelResourceLocation("planttech2:cable/cable_up");
    public static final ModelResourceLocation MODEL_UP_IN = new ModelResourceLocation("planttech2:cable/cable_connection_up_in");
    public static final ModelResourceLocation MODEL_UP_OUT = new ModelResourceLocation("planttech2:cable/cable_connection_up_out");
    public static final ModelResourceLocation MODEL_UP_BOTH = new ModelResourceLocation("planttech2:cable/cable_connection_up_both");

    public static final ModelResourceLocation MODEL_DOWN_CABLE = new ModelResourceLocation("planttech2:cable/cable_down");
    public static final ModelResourceLocation MODEL_DOWN_IN = new ModelResourceLocation("planttech2:cable/cable_connection_down_in");
    public static final ModelResourceLocation MODEL_DOWN_OUT = new ModelResourceLocation("planttech2:cable/cable_connection_down_out");
    public static final ModelResourceLocation MODEL_DOWN_BOTH = new ModelResourceLocation("planttech2:cable/cable_connection_down_both");

    public static final ModelResourceLocation MODEL_EAST_CABLE = new ModelResourceLocation("planttech2:cable/cable_east");
    public static final ModelResourceLocation MODEL_EAST_IN = new ModelResourceLocation("planttech2:cable/cable_connection_east_in");
    public static final ModelResourceLocation MODEL_EAST_OUT = new ModelResourceLocation("planttech2:cable/cable_connection_east_out");
    public static final ModelResourceLocation MODEL_EAST_BOTH = new ModelResourceLocation("planttech2:cable/cable_connection_east_both");

    public static final ModelResourceLocation MODEL_WEST_CABLE = new ModelResourceLocation("planttech2:cable/cable_west");
    public static final ModelResourceLocation MODEL_WEST_IN = new ModelResourceLocation("planttech2:cable/cable_connection_west_in");
    public static final ModelResourceLocation MODEL_WEST_OUT = new ModelResourceLocation("planttech2:cable/cable_connection_west_out");
    public static final ModelResourceLocation MODEL_WEST_BOTH = new ModelResourceLocation("planttech2:cable/cable_connection_west_both");

    public static final ModelResourceLocation MODEL_NORTH_CABLE = new ModelResourceLocation("planttech2:cable/cable_north");
    public static final ModelResourceLocation MODEL_NORTH_IN = new ModelResourceLocation("planttech2:cable/cable_connection_north_in");
    public static final ModelResourceLocation MODEL_NORTH_OUT = new ModelResourceLocation("planttech2:cable/cable_connection_north_out");
    public static final ModelResourceLocation MODEL_NORTH_BOTH = new ModelResourceLocation("planttech2:cable/cable_connection_north_both");

    public static final ModelResourceLocation MODEL_SOUTH_CABLE = new ModelResourceLocation("planttech2:cable/cable_south");
    public static final ModelResourceLocation MODEL_SOUTH_IN = new ModelResourceLocation("planttech2:cable/cable_connection_south_in");
    public static final ModelResourceLocation MODEL_SOUTH_OUT = new ModelResourceLocation("planttech2:cable/cable_connection_south_out");
    public static final ModelResourceLocation MODEL_SOUTH_BOTH = new ModelResourceLocation("planttech2:cable/cable_connection_south_both");

    @Override
    public Collection<ResourceLocation> getOverrideLocations()
    {
	return ImmutableList.copyOf(new ResourceLocation[]
	{ MODEL_CORE, MODEL_UP_CABLE, MODEL_UP_IN, MODEL_UP_OUT, MODEL_UP_BOTH, MODEL_DOWN_CABLE, MODEL_DOWN_IN, MODEL_DOWN_OUT, MODEL_DOWN_BOTH, MODEL_EAST_CABLE, MODEL_EAST_IN, MODEL_EAST_OUT, MODEL_EAST_BOTH, MODEL_WEST_CABLE,
		MODEL_WEST_IN, MODEL_WEST_OUT, MODEL_WEST_BOTH, MODEL_NORTH_CABLE, MODEL_NORTH_IN, MODEL_NORTH_OUT, MODEL_NORTH_BOTH, MODEL_SOUTH_CABLE, MODEL_SOUTH_IN, MODEL_SOUTH_OUT, MODEL_SOUTH_BOTH });
    }

    @Override
    public Collection<ResourceLocation> getTextures(Function<ResourceLocation, IUnbakedModel> modelGetter, Set<String> missingTextureErrors)
    {
	return ImmutableList.copyOf(new ResourceLocation[]
	{ TEXTURE_CABLE, TEXTURE_IN, TEXTURE_OUT, TEXTURE_BOTH });
    }

    @Override
    public IBakedModel bake(Function<ResourceLocation, IUnbakedModel> modelGetter, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, IModelState state, boolean uvlock, VertexFormat format)
    {
	try
	{
	    IUnbakedModel subComponent = ModelLoaderRegistry.getModel(MODEL_CORE);

	    IBakedModel bakedModelCore = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    IBakedModel[] bakedModelUp = new IBakedModel[4];
	    subComponent = ModelLoaderRegistry.getModel(MODEL_UP_CABLE);
	    bakedModelUp[0] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_UP_IN);
	    bakedModelUp[1] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_UP_OUT);
	    bakedModelUp[2] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_UP_BOTH);
	    bakedModelUp[3] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    IBakedModel[] bakedModelDown = new IBakedModel[4];
	    subComponent = ModelLoaderRegistry.getModel(MODEL_DOWN_CABLE);
	    bakedModelDown[0] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_DOWN_IN);
	    bakedModelDown[1] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_DOWN_OUT);
	    bakedModelDown[2] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_DOWN_BOTH);
	    bakedModelDown[3] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    IBakedModel[] bakedModelEast = new IBakedModel[4];
	    subComponent = ModelLoaderRegistry.getModel(MODEL_EAST_CABLE);
	    bakedModelEast[0] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_EAST_IN);
	    bakedModelEast[1] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_EAST_OUT);
	    bakedModelEast[2] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_EAST_BOTH);
	    bakedModelEast[3] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    IBakedModel[] bakedModelWest = new IBakedModel[4];
	    subComponent = ModelLoaderRegistry.getModel(MODEL_WEST_CABLE);
	    bakedModelWest[0] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_WEST_IN);
	    bakedModelWest[1] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_WEST_OUT);
	    bakedModelWest[2] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_WEST_BOTH);
	    bakedModelWest[3] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    IBakedModel[] bakedModelNorth = new IBakedModel[4];
	    subComponent = ModelLoaderRegistry.getModel(MODEL_NORTH_CABLE);
	    bakedModelNorth[0] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_NORTH_IN);
	    bakedModelNorth[1] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_NORTH_OUT);
	    bakedModelNorth[2] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_NORTH_BOTH);
	    bakedModelNorth[3] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    IBakedModel[] bakedModelSouth = new IBakedModel[4];
	    subComponent = ModelLoaderRegistry.getModel(MODEL_SOUTH_CABLE);
	    bakedModelSouth[0] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_SOUTH_IN);
	    bakedModelSouth[1] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_SOUTH_OUT);
	    bakedModelSouth[2] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    subComponent = ModelLoaderRegistry.getModel(MODEL_SOUTH_BOTH);
	    bakedModelSouth[3] = subComponent.bake(modelGetter, spriteGetter, state, uvlock, format);

	    return new CompositeModel(bakedModelCore, bakedModelUp, bakedModelDown, bakedModelWest, bakedModelEast, bakedModelNorth, bakedModelSouth);
	}
	catch (Exception exception)
	{
	    PlantTechMain.instance.LOGGER.info("CableModel.bake() failed due to exception:" + exception);
	    return ModelLoaderRegistry.getMissingModel().bake(modelGetter, spriteGetter, state, uvlock, format);
	}
    }

}
