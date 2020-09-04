package net.kaneka.planttech2.packets;

import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTrade;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.PlayerInventoryUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DoTechVillagerTradeMessage
{; 
	private final TechVillagerTrade trade;
	private final int profession;

	public DoTechVillagerTradeMessage(TechVillagerTrade trade, int profession)
	{
		this.trade = trade;
		this.profession = profession; 
	}

	public static void encode(DoTechVillagerTradeMessage pkt, PacketBuffer buf)
	{
		pkt.trade.toBuffer(buf);
		buf.writeInt(pkt.profession);
	}

	public static DoTechVillagerTradeMessage decode(PacketBuffer buf)
	{
		return new DoTechVillagerTradeMessage(TechVillagerTrade.fromBuffer(buf), buf.readInt());
	}

	public static void handle(final DoTechVillagerTradeMessage pkt, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity sender = ctx.get().getSender();
			if (sender == null) return;
			PlayerInventory inv = sender.inventory;
			TechVillagerTrade trade = pkt.trade;
			sender.getCapability(TechVillagerTrust.INSTANCE).ifPresent(trust ->
			{
				boolean success = trust.getLevel(TechVillagerEntity.getProfessionString(pkt.profession)) >= trade.getNeededLevel();
				success = success && PlayerInventoryUtils.enoughSpace(inv, trade.getOutputs().size());
				success = success && PlayerInventoryUtils.hasList(inv, trade.getInputs());
				success = success && PlayerInventoryUtils.enoughCredits(inv, trade.getCreditsBuy());
				if (success)
				{
					if (trade.getCreditsSell() > 0 && inv.count(ModItems.PLANTCARD) > 0)
					{
						if (PlayerInventoryUtils.removeCredits(inv, trade.getCreditsBuy())
								&& PlayerInventoryUtils.removeList(inv, trade.getInputs()))
						{
							PlayerInventoryUtils.addList(inv, trade.getOutputs());
							PlayerInventoryUtils.addCredits(inv, trade.getCreditsSell());
						}
					} else if (trade.getCreditsSell() == 0)
					{
						if (PlayerInventoryUtils.removeCredits(inv, trade.getCreditsBuy())
								&& PlayerInventoryUtils.removeList(inv, trade.getInputs()))
						{
							PlayerInventoryUtils.addList(inv, trade.getOutputs());
						}
					}
				}
			});
		});
		ctx.get().setPacketHandled(true);
	}
}
