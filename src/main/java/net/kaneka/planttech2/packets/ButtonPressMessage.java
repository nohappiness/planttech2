package net.kaneka.planttech2.packets;

import java.util.function.Supplier;

import net.kaneka.planttech2.tileentity.machine.CompressorTileEntity;
import net.kaneka.planttech2.tileentity.machine.MachineBulbReprocessorTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class ButtonPressMessage
{

	private int x, y, z, buttonId;

	public ButtonPressMessage(int x, int y, int z, int buttonId)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.buttonId = buttonId;
	}

	public static void encode(ButtonPressMessage pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.x);
		buf.writeInt(pkt.y);
		buf.writeInt(pkt.z);
		buf.writeInt(pkt.buttonId);

	}

	public static ButtonPressMessage decode(PacketBuffer buf)
	{
		return new ButtonPressMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());

	}

	public static class ButtonPressMessageHandler
	{

		public static void handle(final ButtonPressMessage pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				
				ServerPlayerEntity serverPlayer = ctx.get().getSender();
				BlockPos pos = new BlockPos(pkt.x, pkt.y, pkt.z);
				int buttonId = pkt.buttonId;
				if (serverPlayer.world.isBlockLoaded(pos))
				{
					TileEntity te = serverPlayer.world.getTileEntity(pos);
					if (te != null)
					{
						if (te instanceof CompressorTileEntity)
						{
							((CompressorTileEntity) te).setSelectedId(buttonId);
						}
						else if(te instanceof MachineBulbReprocessorTileEntity)
						{
							((MachineBulbReprocessorTileEntity) te).setSelectedId(buttonId);
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
		}

	}
}
