package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.datapack.CropListEntryConfiguration;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class CropConfigChangeMessage
{
	private final Map<String, CropListEntryConfiguration> user_configs;

	public CropConfigChangeMessage(Map<String, CropListEntryConfiguration> user_configs)
	{
		this.user_configs = user_configs;
	}

	public static void encode(CropConfigChangeMessage pkt, PacketBuffer buf)
	{
		buf.writeVarInt(pkt.user_configs.size());
		for (Entry<String, CropListEntryConfiguration> entry : pkt.user_configs.entrySet())
		{
			buf.writeString(entry.getKey());
			CropListEntryConfiguration.Deserializer.write(buf, entry.getValue());
		}
	}

	public static CropConfigChangeMessage decode(PacketBuffer buf)
	{
		Map<String, CropListEntryConfiguration> new_configs = new HashMap<>();
		int size = buf.readVarInt();
		for (int i = 0; i < size; i++)
		{
			String name = buf.readString(1024);
			new_configs.put(name, CropListEntryConfiguration.Deserializer.read(name, buf));
		}

		return new CropConfigChangeMessage(new_configs);
	}

	public static void handle(final CropConfigChangeMessage pkt, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			PlantTechMain.LOGGER.info("Sync crop configurations");
			for (CropListEntryConfiguration config : pkt.user_configs.values())
			{
				config.applyToEntry();
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
