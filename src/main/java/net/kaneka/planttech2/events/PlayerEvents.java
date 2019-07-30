package net.kaneka.planttech2.events;

import java.util.Map.Entry;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.ITechVillagerTrust;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.kaneka.planttech2.items.upgradeable.UpgradeableArmorItem;
import net.kaneka.planttech2.packets.CropConfigChangeMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID)
public class PlayerEvents
{
	@SubscribeEvent
	public static void playerConnect(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
	{
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		PlantTech2PacketHandler.sendTo(new CropConfigChangeMessage(PlantTechMain.croplist.getConfigs()), player);
	}
	
	@SubscribeEvent
	public static void livingDamaged(LivingDamageEvent e)
	{
		if(e.getEntityLiving() instanceof PlayerEntity)
		{
			for(ItemStack stack: e.getEntityLiving().getArmorInventoryList())
			{
				if(!stack.isEmpty())
				{
					if(stack.getItem() instanceof UpgradeableArmorItem)
					{
						((UpgradeableArmorItem)stack.getItem()).extractEnergy(stack, UpgradeableArmorItem.getEnergyCost(stack), false); 
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof PlayerEntity)
		{
			event.addCapability(ModReferences.TECHVILLAGERTRUSTCAP, new TechVillagerTrust());
		}
	}
	
	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		if(event.isWasDeath())
		{
    		event.getOriginal().revive();
    		ITechVillagerTrust old = event.getOriginal().getCapability(TechVillagerTrust.INSTANCE).orElse(null); 
    		ITechVillagerTrust playercap = event.getEntityPlayer().getCapability(TechVillagerTrust.INSTANCE).orElse(new TechVillagerTrust()); 
    		if(old != null)
    		{
    			for(Entry<String, Integer> entry: old.getTrustsMap().entrySet())
    			{
    				playercap.setTrust(entry.getKey(), entry.getValue());
    			}
    		}
		}
	}
}
