package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.blocks.entity.machine.PlantTopiaTeleporterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class TeleporterBlockButtonPressMessage
{

	private final int x, y, z, buttonId;

	public TeleporterBlockButtonPressMessage(int x, int y, int z, int buttonId)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.buttonId = buttonId;
	}

	public static void encode(TeleporterBlockButtonPressMessage pkt, FriendlyByteBuf buf)
	{
		buf.writeInt(pkt.x);
		buf.writeInt(pkt.y);
		buf.writeInt(pkt.z);
		buf.writeInt(pkt.buttonId);
	}

	public static TeleporterBlockButtonPressMessage decode(FriendlyByteBuf buf)
	{
		return new TeleporterBlockButtonPressMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
	}

	public static void handle(final TeleporterBlockButtonPressMessage pkt, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			ServerPlayer serverPlayer = ctx.get().getSender();
			BlockPos pos = new BlockPos(pkt.x, pkt.y, pkt.z);
			if (serverPlayer != null && serverPlayer.level.isAreaLoaded(pos, 0))
			{
				BlockEntity te = serverPlayer.level.getBlockEntity(pos);
				if (te != null)
				{
					if (te instanceof PlantTopiaTeleporterBlockEntity)
					{

						((PlantTopiaTeleporterBlockEntity) te).doTeleportation();
					}
				}
			}

			//TeleporterUtilities.changeDimension(serverPlayer.world, pos, serverPlayer, ModDimensionPlantTopia.getDimensionType(), ModBlocks.PLANTTOPIA_TELEPORTER_END);
		});
		ctx.get().setPacketHandled(true);
	}
}
