package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.crops.CropEntryConfigData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class CropConfigChangeMessage
{
    private final Map<ResourceLocation, CropEntryConfigData> configData;

    public CropConfigChangeMessage(Map<ResourceLocation, CropEntryConfigData> configData)
    {
        this.configData = configData;
    }

    public static void encode(CropConfigChangeMessage pkt, FriendlyByteBuf buf)
    {
        buf.writeVarInt(pkt.configData.size());
        for (Entry<ResourceLocation, CropEntryConfigData> entry : pkt.configData.entrySet())
        {
            buf.writeResourceLocation(entry.getKey());
            CropEntryConfigData.Serializer.INSTANCE.write(entry.getValue(), buf);
        }
    }

    public static CropConfigChangeMessage decode(FriendlyByteBuf buf)
    {
        Map<ResourceLocation, CropEntryConfigData> configData = new HashMap<>();
        int size = buf.readVarInt();
        for (int i = 0; i < size; i++)
        {
            ResourceLocation name = buf.readResourceLocation();
            configData.put(name, CropEntryConfigData.Serializer.INSTANCE.read(buf));
        }

        return new CropConfigChangeMessage(configData);
    }

    public static void handle(final CropConfigChangeMessage pkt, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            PlantTechMain.LOGGER.debug("Syncing crop configurations from server");
            PlantTechMain.getCropList().configureFromConfigData(pkt.configData.values());
        });
        ctx.get().setPacketHandled(true);
    }
}
