package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.crops.CropEntryConfigData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.resources.ResourceLocation;
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
        buf.writeNbt(pkt.cropList);
    }

    public static CropListSyncMessage decode(PacketBuffer buf)
    {
        return new CropListSyncMessage(buf.readNbt());
    }

    public static void handle(final CropListSyncMessage pkt, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            PlantTechMain.LOGGER.debug("Reading crop configurations sent from server");
            try
            {
                PlantTechMain.getCropList().read(pkt.cropList);
            }
            catch (Exception e)
            {
                PlantTechMain.LOGGER.error("An error has occurred during the processing with crop list syncing" +
                        ", report this to the server.");
                PlantTechMain.LOGGER.error(e.getMessage());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
