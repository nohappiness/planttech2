package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.tileentity.machine.PlantTopiaTeleporterTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

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

	public static void encode(TeleporterBlockButtonPressMessage pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.x);
		buf.writeInt(pkt.y);
		buf.writeInt(pkt.z);
		buf.writeInt(pkt.buttonId);
	}

	public static TeleporterBlockButtonPressMessage decode(PacketBuffer buf)
	{
		return new TeleporterBlockButtonPressMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
	}

	public static void handle(final TeleporterBlockButtonPressMessage pkt, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity serverPlayer = ctx.get().getSender();
			BlockPos pos = new BlockPos(pkt.x, pkt.y, pkt.z);
			if (serverPlayer != null && serverPlayer.level.isAreaLoaded(pos, 0))
			{
				TileEntity te = serverPlayer.level.getBlockEntity(pos);
				if (te != null)
				{
					if (te instanceof PlantTopiaTeleporterTileEntity)
					{

						((PlantTopiaTeleporterTileEntity) te).doTeleportation();
					}
				}
			}

			//TeleporterUtilities.changeDimension(serverPlayer.world, pos, serverPlayer, ModDimensionPlantTopia.getDimensionType(), ModBlocks.PLANTTOPIA_TELEPORTER_END);
		});
		ctx.get().setPacketHandled(true);
	}
}
