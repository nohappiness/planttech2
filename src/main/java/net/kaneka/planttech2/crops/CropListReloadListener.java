package net.kaneka.planttech2.crops;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.packets.CropListSyncMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.server.ServerLifecycleHooks;
import org.antlr.runtime.debug.Profiler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static net.kaneka.planttech2.PlantTechMain.LOGGER;

public class CropListReloadListener extends JsonReloadListener //Propably SimplePreparableReloadListener
{
	public static final String FOLDER = "pt2_crops";
	private static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(CropEntryConfigData.class, CropEntryConfigData.Serializer.INSTANCE)
			.registerTypeAdapter(DropEntry.class, DropEntry.Serializer.INSTANCE)
			.registerTypeAdapter(ParentPair.class, ParentPair.Serializer.INSTANCE)
			.create();

	public CropListReloadListener()
	{
		super(GSON, FOLDER);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> elementMap, ResourceManager resourceManager, Profiler profiler)
	{
		LOGGER.debug("Loading crop configurations");
		Map<ResourceLocation, CropEntryConfigData> configsDefault = new HashMap<>();
		Map<ResourceLocation, CropEntryConfigData> configsOther = new HashMap<>();
		List<ResourceLocation> keys = new LinkedList<>(elementMap.keySet());
		
		for (ResourceLocation key : keys)
		{
			try
			{
				JsonElement element = elementMap.get(key);
				if (!CraftingHelper.processConditions(JSONUtils.convertToJsonObject(element, "top element"), "conditions"))
				{
					LOGGER.debug("Skipping loading crop configuration {} as it's conditions were not met", key);
					continue;
				}
				
				if(key.getNamespace().equals(PlantTechMain.MODID))
				{
					configsDefault.put(key, GSON.fromJson(element, CropEntryConfigData.class));
				}
				else
				{
					configsOther.put(key, GSON.fromJson(element, CropEntryConfigData.class));
				}
				
			} catch (IllegalArgumentException | JsonSyntaxException ex)
			{
				LOGGER.error("Error while loading crop configuration {}", key, ex);
			}
		}
		
		PlantTechMain.getCropList().configureFromConfigData(configsDefault.values());
		PlantTechMain.getCropList().configureFromConfigData(configsOther.values());
		
		// Check if the server is up
		if (ServerLifecycleHooks.getCurrentServer() != null)
		{
			LOGGER.info("syncing crop list to clients");
			PlantTech2PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new CropListSyncMessage());
		}
		else LOGGER.info("Server is not up yet, will not send the changes to clients");
	}
	

	public static JsonElement toJson(CropEntryConfigData data)
	{
		return GSON.toJsonTree(data);
	}
}
