package net.kaneka.planttech2.rendering.cable;

import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class ModelLoaderCable implements ICustomModelLoader
{
    private final String CABLE_MODEL_RESOURCE_LOCATION = "models/block/cable/";
    private IResourceManager resourceManager;

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager)
    {
	this.resourceManager = resourceManager;
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation)
    {
	System.out.println("test");
	return modelLocation.getNamespace().equals("planttech2") && modelLocation.getNamespace().startsWith(CABLE_MODEL_RESOURCE_LOCATION);
    }

    @Override
    public IUnbakedModel loadModel(ResourceLocation modelLocation) throws Exception
    {
	String resourcePath = modelLocation.getNamespace();
	if (!resourcePath.startsWith(CABLE_MODEL_RESOURCE_LOCATION))
	{
	    assert false : "loadModel expected " + CABLE_MODEL_RESOURCE_LOCATION + " but found " + resourcePath;
	}
	String modelName = resourcePath.substring(CABLE_MODEL_RESOURCE_LOCATION.length());

	if (modelName.equals("cablemodel"))
	{
	    return new CableModel();
	}
	else
	{
	    return ModelLoaderRegistry.getMissingModel();
	}
    }

}
