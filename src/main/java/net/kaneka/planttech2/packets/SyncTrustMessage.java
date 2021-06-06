package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncTrustMessage
{
	private final String profession;
	private final int value;

	public SyncTrustMessage(String profession, int value)
	{
		this.profession = profession; 
		this.value = value;
	}

	public static void encode(SyncTrustMessage pkt, PacketBuffer buf)
	{
		buf.writeUtf(pkt.profession);
		buf.writeInt(pkt.value);
	}

	public static SyncTrustMessage decode(PacketBuffer buf)
	{
		return new SyncTrustMessage(buf.readUtf(), buf.readInt());
	}

	public static void handle(final SyncTrustMessage pkt, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			if (Minecraft.getInstance().player != null)
			{
				Minecraft.getInstance().player.getCapability(TechVillagerTrust.INSTANCE).ifPresent(trust ->
						trust.setTrust(pkt.profession, pkt.value));
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
