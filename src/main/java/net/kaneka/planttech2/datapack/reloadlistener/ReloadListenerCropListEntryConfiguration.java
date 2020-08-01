package net.kaneka.planttech2.datapack.reloadlistener;

import com.google.gson.*;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.datapack.CropListEntryConfiguration;
import net.kaneka.planttech2.packets.CropConfigChangeMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReloadListenerCropListEntryConfiguration extends JsonReloadListener
{
    public static final String PATH = "pt2_crops";
    public static final String EXTENTION = ".json";

    public ReloadListenerCropListEntryConfiguration()
    {
        super(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), PATH);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, IResourceManager iResourceManager, IProfiler iProfiler)
    {
        PlantTechMain.LOGGER.info("Load crop configuration from data packs");
        Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
        Map<String, CropListEntryConfiguration> configs = new HashMap<String, CropListEntryConfiguration>();

        Collection<ResourceLocation> files = iResourceManager.getAllResourceLocations(PATH, f -> {
            return f.endsWith(EXTENTION);
        });

        // First load all default configurations
        for (ResourceLocation file : files.stream().filter(file -> file.getNamespace().equals("planttech2")).collect(Collectors.toList()))
        {
            tryDeserialization(iResourceManager, file, gson, configs, false);
        }

        // Then then load overrides
        for (ResourceLocation file : files.stream().filter(file -> !file.getNamespace().equals("planttech2")).collect(Collectors.toList()))
        {
            tryDeserialization(iResourceManager, file, gson, configs, true);
        }

        //Apply values
        for(CropListEntryConfiguration config: configs.values())
        {
            config.applyToEntry();
        }

        //save on server
        PlantTechMain.croplist.setConfigs(configs);
//        Sync all Clients
        if (ServerLifecycleHooks.getCurrentServer() != null)
            for (ServerPlayerEntity player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
                PlantTech2PacketHandler.sendTo(new CropConfigChangeMessage(configs), player);
    }

    private void tryDeserialization(IResourceManager iResourceManager, ResourceLocation file, Gson gson, Map<String, CropListEntryConfiguration> configs, boolean merge)
    {
        String name = file.getPath().replace(PATH, "").replace(EXTENTION, "").replace("/", "");
        try (IResource iresource = iResourceManager.getResource(file))
        {
            JsonObject jsonobject = JSONUtils.fromJson(gson, IOUtils.toString(iresource.getInputStream(), StandardCharsets.UTF_8), JsonObject.class);
            if (jsonobject == null)
            {
                PlantTechMain.LOGGER.error("Couldn't load recipe {} as it's null or empty", file);
            }
            else
            {
                if(!merge)
                {
                    configs.put(name, CropListEntryConfiguration.Deserializer.read(name, jsonobject));
                }
                else
                {
                    configs.get(name).merge(CropListEntryConfiguration.Deserializer.read(name, jsonobject));
                }
            }

        }
        catch (IllegalArgumentException | JsonParseException jsonparseexception)
        {
            PlantTechMain.LOGGER.error("Parsing error loading recipe {}", name, jsonparseexception);
        }
        catch (IOException ioexception)
        {
            PlantTechMain.LOGGER.error("Couldn't read custom advancement {} from {}", name, file, ioexception);
        }
    }
}
