package net.kaneka.planttech2.crops;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.packets.CropConfigChangeMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import static net.kaneka.planttech2.PlantTechMain.LOGGER;

public class CropListReloadListener extends JsonReloadListener
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
	protected void apply(Map<ResourceLocation, JsonElement> elementMap, IResourceManager resourceManager, IProfiler profiler)
	{
		LOGGER.debug("Loading crop configurations");
		Map<ResourceLocation, CropEntryConfigData> configs = new HashMap<>();
		// Ensure that our mod's crop configs are loaded first, as non-coded defaults
		List<ResourceLocation> keys = new LinkedList<>(elementMap.keySet());
		keys.sort((a, b) -> a.getNamespace().equals(PlantTechMain.MODID) ? -1 : a.compareTo(b));

		for (ResourceLocation key : keys)
		{
			try
			{
				JsonElement element = elementMap.get(key);
				if (!CraftingHelper.processConditions(JSONUtils.getJsonObject(element, "top element"), "conditions"))
				{
					LOGGER.debug("Skipping loading crop configuration {} as it's conditions were not met", key);
					continue;
				}
				configs.put(key, GSON.fromJson(element, CropEntryConfigData.class));
			} catch (IllegalArgumentException | JsonSyntaxException ex)
			{
				LOGGER.error("Error while loading crop configuration {}", key, ex);
			}
		}

		PlantTechMain.getCropList().configureFromConfigData(configs.values());

		if (ServerLifecycleHooks.getCurrentServer() != null)
		{
			for (ServerPlayerEntity player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
			{
				PlantTech2PacketHandler.sendTo(new CropConfigChangeMessage(configs), player);
			}
			// TODO: sync data on a player's first join
		}
	}

	public static JsonElement toJson(CropEntryConfigData data)
	{
		return GSON.toJsonTree(data);
	}
}
