package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class CropListSyncMessage
{
    private final CompoundTag cropList;
    public CropListSyncMessage()
    {
        this(PlantTechMain.getCropList().save());
    }

    public CropListSyncMessage(CompoundTag cropList)
    {
        this.cropList = cropList;
    }

    public static void encode(CropListSyncMessage pkt, FriendlyByteBuf buf)
    {
        buf.writeNbt(pkt.cropList);
    }

    public static CropListSyncMessage decode(FriendlyByteBuf buf)
    {
        return new CropListSyncMessage(buf.readNbt());
    }

    public static void handle(final CropListSyncMessage pkt, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            PlantTechMain.LOGGER.debug("Reading crop configurations sent from server");
            try
            {
                PlantTechMain.getCropList().load(pkt.cropList);
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
