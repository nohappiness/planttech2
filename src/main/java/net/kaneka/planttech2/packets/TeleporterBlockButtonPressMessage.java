package net.kaneka.planttech2.packets;

import java.util.function.Supplier;

import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.machine.PlantTopiaTeleporterTileEntity;
import net.kaneka.planttech2.world.TeleporterUtilities;
import net.kaneka.planttech2.world.planttopia.ModDimensionPlantTopia;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class TeleporterBlockButtonPressMessage
{

	private int x, y, z, buttonId;

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

	public static class ButtonPressMessageHandler
	{

		public static void handle(final TeleporterBlockButtonPressMessage pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				ServerPlayerEntity serverPlayer = ctx.get().getSender();
				BlockPos pos = new BlockPos(pkt.x, pkt.y, pkt.z);
				if (serverPlayer.world.isBlockLoaded(pos))
				{
					TileEntity te = serverPlayer.world.getTileEntity(pos);
					if (te != null)
					{
						if (te instanceof PlantTopiaTeleporterTileEntity)
						{
							
							((PlantTopiaTeleporterTileEntity) te).doTeleportation();
						}
					}
				}
				
				TeleporterUtilities.changeDimension(serverPlayer.world, pos, serverPlayer, ModDimensionPlantTopia.getDimensionType(), ModBlocks.PLANTTOPIA_TELEPORTER_END);
			});
			ctx.get().setPacketHandled(true);
		}

	}
}
