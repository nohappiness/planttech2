package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static java.util.Optional.of;

public class PlantTech2PacketHandler
{
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(PlantTechMain.MODID, "main_channel"))
	        .clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
	static int ID = 0;

	public static void register()
	{
		INSTANCE.registerMessage(ID++, ButtonPressMessage.class, ButtonPressMessage::encode,
				ButtonPressMessage::decode, ButtonPressMessage::handle, of(NetworkDirection.PLAY_TO_SERVER));
		INSTANCE.registerMessage(ID++, CropConfigChangeMessage.class, CropConfigChangeMessage::encode,
				CropConfigChangeMessage::decode, CropConfigChangeMessage::handle, of(NetworkDirection.PLAY_TO_CLIENT));
		INSTANCE.registerMessage(ID++, TeleporterBlockButtonPressMessage.class, TeleporterBlockButtonPressMessage::encode,
				TeleporterBlockButtonPressMessage::decode, TeleporterBlockButtonPressMessage::handle, of(NetworkDirection.PLAY_TO_SERVER));
		INSTANCE.registerMessage(ID++, DoTechVillagerTradeMessage.class, DoTechVillagerTradeMessage::encode,
				DoTechVillagerTradeMessage::decode, DoTechVillagerTradeMessage::handle, of(NetworkDirection.PLAY_TO_SERVER));
		INSTANCE.registerMessage(ID++, DoTechVillagerTaskMessage.class, DoTechVillagerTaskMessage::encode,
				DoTechVillagerTaskMessage::decode, DoTechVillagerTaskMessage::handle, of(NetworkDirection.PLAY_TO_SERVER));
		INSTANCE.registerMessage(ID++, SyncTrustMessage.class, SyncTrustMessage::encode,
				SyncTrustMessage::decode, SyncTrustMessage::handle, of(NetworkDirection.PLAY_TO_CLIENT));
		INSTANCE.registerMessage(ID++, SyncRadiationLevelMessage.class, SyncRadiationLevelMessage::encode,
				SyncRadiationLevelMessage::decode, SyncRadiationLevelMessage::handle, of(NetworkDirection.PLAY_TO_CLIENT));
		INSTANCE.registerMessage(ID++, SyncBiomassFluidEnergyMessage.class, SyncBiomassFluidEnergyMessage::encode,
				SyncBiomassFluidEnergyMessage::decode, SyncBiomassFluidEnergyMessage::handle, of(NetworkDirection.PLAY_TO_CLIENT));
		INSTANCE.registerMessage(ID++, CropListSyncMessage.class, CropListSyncMessage::encode,
				CropListSyncMessage::decode, CropListSyncMessage::handle, of(NetworkDirection.PLAY_TO_CLIENT));
	}

	public static void sendToServer(Object msg)
	{
		INSTANCE.sendToServer(msg);
	}

	public static void sendTo(Object msg, ServerPlayerEntity player)
	{
		if (!(player instanceof FakePlayer))
		{
			INSTANCE.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
		}
	}
}
