package net.kaneka.planttech2.packets;

import java.util.function.Supplier;

import net.kaneka.planttech2.entities.capabilities.techvillagertrust.ITechVillagerTrust;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SyncTrustMessage
{
	private String profession; 
	private int value;

	public SyncTrustMessage(String profession, int value)
	{
		this.profession = profession; 
		this.value = value;
	}

	public static void encode(SyncTrustMessage pkt, PacketBuffer buf)
	{
		buf.writeString(pkt.profession);
		buf.writeInt(pkt.value);
	}

	public static SyncTrustMessage decode(PacketBuffer buf)
	{
		return new SyncTrustMessage(buf.readString(), buf.readInt());
	}

	public static class SyncTrustHandler
	{
		public static void handle(final SyncTrustMessage pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				ITechVillagerTrust trust = Minecraft.getInstance().player.getCapability(TechVillagerTrust.INSTANCE).orElse(null);
				if(trust != null)
				{
					trust.setTrust(pkt.profession, pkt.value);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
