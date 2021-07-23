package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncRadiationLevelMessage
{
    private final float level;

    public SyncRadiationLevelMessage(float level)
    {
        this.level = level;
    }

    public static void encode(SyncRadiationLevelMessage message, FriendlyByteBuf buffer)
    {
        buffer.writeFloat(message.level);
    }

    public static SyncRadiationLevelMessage decode(FriendlyByteBuf buffer)
    {
        return new SyncRadiationLevelMessage(buffer.readFloat());
    }

    public static void handle(SyncRadiationLevelMessage message, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isAlive())
            {
                Minecraft.getInstance().player.getCapability(RadiationEffect.RADIATION_CAPABILITY).orElseThrow(() -> new NullPointerException("An error occurred during packet process")).setLevel(message.level);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
