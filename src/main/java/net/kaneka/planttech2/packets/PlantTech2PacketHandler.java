package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PlantTech2PacketHandler
{
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(PlantTechMain.MODID, "main_channel"))
	        .clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();

	public static final void register()
	{
		int i = 0;
		INSTANCE.registerMessage(i++, ButtonPressMessage.class, ButtonPressMessage::encode, ButtonPressMessage::decode, ButtonPressMessage.ButtonPressMessageHandler::handle);
		INSTANCE.registerMessage(i++, CropConfigChangeMessage.class, CropConfigChangeMessage::encode, CropConfigChangeMessage::decode, CropConfigChangeMessage.CropConfigChangeHandler::handle);
		INSTANCE.registerMessage(i++, TeleporterBlockButtonPressMessage.class, TeleporterBlockButtonPressMessage::encode, TeleporterBlockButtonPressMessage::decode, TeleporterBlockButtonPressMessage.ButtonPressMessageHandler::handle);
		INSTANCE.registerMessage(i++, DoTechVillagerTradeMessage.class, DoTechVillagerTradeMessage::encode, DoTechVillagerTradeMessage::decode, DoTechVillagerTradeMessage.DoTechVillagerTradeHandler::handle);
		INSTANCE.registerMessage(i++, DoTechVillagerTaskMessage.class, DoTechVillagerTaskMessage::encode, DoTechVillagerTaskMessage::decode, DoTechVillagerTaskMessage.DoTechVillagerTaskHandler::handle);
		INSTANCE.registerMessage(i++, SyncTrustMessage.class, SyncTrustMessage::encode, SyncTrustMessage::decode, SyncTrustMessage.SyncTrustHandler::handle);
		INSTANCE.registerMessage(i++, SyncRadiationLevelMessage.class, SyncRadiationLevelMessage::encode, SyncRadiationLevelMessage::decode, SyncRadiationLevelMessage.SyncRadiationLevelHandler::handle);
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
