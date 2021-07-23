package net.kaneka.planttech2.packets;

import io.netty.buffer.ByteBuf;
import net.kaneka.planttech2.blocks.entity.machine.CompressorBlockEntity;
import net.kaneka.planttech2.blocks.entity.machine.MachineBulbReprocessorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class ButtonPressMessage
{

	private final int x, y, z, buttonId;

	public ButtonPressMessage(int x, int y, int z, int buttonId)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.buttonId = buttonId;
	}

	public static void encode(ButtonPressMessage pkt, FriendlyByteBuf buf)
	{
		buf.writeInt(pkt.x);
		buf.writeInt(pkt.y);
		buf.writeInt(pkt.z);
		buf.writeInt(pkt.buttonId);
	}

	public static ButtonPressMessage decode(ByteBuf buf)
	{
		return new ButtonPressMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
	}

	public static void handle(final ButtonPressMessage pkt, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {

			ServerPlayer serverPlayer = ctx.get().getSender();
			BlockPos pos = new BlockPos(pkt.x, pkt.y, pkt.z);
			int buttonId = pkt.buttonId;
			if (serverPlayer != null && serverPlayer.level.hasChunkAt(pos))
			{
				BlockEntity te = serverPlayer.level.getBlockEntity(pos);
				if (te != null)
				{
					if (te instanceof CompressorBlockEntity cbe)
					{
						cbe.setSelectedId(buttonId);
					} else if (te instanceof MachineBulbReprocessorBlockEntity)
					{
						((MachineBulbReprocessorBlockEntity) te).setSelectedId(buttonId);
					}
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
