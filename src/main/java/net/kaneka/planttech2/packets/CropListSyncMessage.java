package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.crops.CropEntryConfigData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class CropListSyncMessage
{
    private final CompoundNBT cropList;
    public CropListSyncMessage()
    {
        this(PlantTechMain.getCropList().write());
    }

    public CropListSyncMessage(CompoundNBT cropList)
    {
        this.cropList = cropList;
    }

    public static void encode(CropListSyncMessage pkt, PacketBuffer buf)
    {
        buf.writeCompoundTag(pkt.cropList);
    }

    public static CropListSyncMessage decode(PacketBuffer buf)
    {
        return new CropListSyncMessage(buf.readCompoundTag());
    }

    public static void handle(final CropListSyncMessage pkt, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            PlantTechMain.LOGGER.debug("Syncing crop configurations from server");
            PlantTechMain.getCropList().read(pkt.cropList);
        });
        ctx.get().setPacketHandled(true);
    }
}
