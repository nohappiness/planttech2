package net.kaneka.planttech2.packets;

import java.util.function.Supplier;

import net.kaneka.planttech2.entities.capabilities.techvillagertrust.ITechVillagerTrust;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTask;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTaskList;
import net.kaneka.planttech2.utilities.PlayerInventoryUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class DoTechVillagerTaskMessage
{
	;
	private int task;

	public DoTechVillagerTaskMessage(int task)
	{
		this.task = task;
	}

	public static void encode(DoTechVillagerTaskMessage pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.task);
	}

	public static DoTechVillagerTaskMessage decode(PacketBuffer buf)
	{
		return new DoTechVillagerTaskMessage(buf.readInt());
	}

	public static class DoTechVillagerTaskHandler
	{
		public static void handle(final DoTechVillagerTaskMessage pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				ServerPlayerEntity player = ctx.get().getSender();
				PlayerInventory inv = player.inventory;
				TechVillagerTask task = TechVillagerTaskList.getByID(pkt.task);
				if (PlayerInventoryUtils.hasList(inv, task.getInputs()))
				{
					ITechVillagerTrust trust = player.getCapability(TechVillagerTrust.INSTANCE).orElse(null);
					if (trust != null)
					{
						int level = trust.getLevel(TechVillagerEntity.getProfessionString(task.getProfession()));
						if(level >= task.getMinTrustLevel() && level <= task.getMaxTrustLevel())
						{
    						if (PlayerInventoryUtils.removeList(inv, task.getInputs()))
    						{
    							String profession = TechVillagerEntity.getProfessionString(task.getProfession()); 
    							trust.increaseTrust(profession, task.getTrust(), task.getMaxTrustLevel() + 1);
    							PlantTech2PacketHandler.sendTo(new SyncTrustMessage(profession, trust.getTrust(profession)), player);
    						}
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
