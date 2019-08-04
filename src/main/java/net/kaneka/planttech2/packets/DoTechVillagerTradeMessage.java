package net.kaneka.planttech2.packets;

import java.util.function.Supplier;

import net.kaneka.planttech2.entities.capabilities.techvillagertrust.ITechVillagerTrust;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTrade;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.PlayerInventoryUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class DoTechVillagerTradeMessage
{; 
	private TechVillagerTrade trade;
	private int profession; 

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

	public static class DoTechVillagerTradeHandler
	{
		public static void handle(final DoTechVillagerTradeMessage pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				PlayerInventory inv = ctx.get().getSender().inventory;
				TechVillagerTrade trade = pkt.trade; 
				ITechVillagerTrust trust = ctx.get().getSender().getCapability(TechVillagerTrust.INSTANCE).orElse(null);
				if (trust != null)
				{
					if(trust.getLevel(TechVillagerEntity.getProfessionString(pkt.profession)) >= trade.getNeededLevel())
					{
        				if(PlayerInventoryUtils.enoughSpace(inv, trade.getOutputs().size()))
        				{
        					if(PlayerInventoryUtils.hasList(inv, trade.getInputs()))
        					{
        						if(PlayerInventoryUtils.enoughCredits(inv, trade.getCreditsBuy()))
        						{
        							if(trade.getCreditsSell() > 0 && inv.count(ModItems.PLANTCARD) > 0)
        							{
        								if(PlayerInventoryUtils.removeCredits(inv, trade.getCreditsBuy()))
        								{
            								if(PlayerInventoryUtils.removeList(inv, trade.getInputs()))
            	        					{
            	        						PlayerInventoryUtils.addList(inv, trade.getOutputs()); 
            	        						PlayerInventoryUtils.addCredits(inv, trade.getCreditsSell()); 
            	        					}
        								}
        							}
        							else if(trade.getCreditsSell() == 0)
        							{
        								if(PlayerInventoryUtils.removeCredits(inv, trade.getCreditsBuy()))
        								{
            								if(PlayerInventoryUtils.removeList(inv, trade.getInputs()))
            	        					{
            	        						PlayerInventoryUtils.addList(inv, trade.getOutputs()); 
            	        					}
        								}
        							}
                					
        						}
        					}
        				}
    				}
    			}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
