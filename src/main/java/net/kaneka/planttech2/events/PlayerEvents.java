package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.items.upgradeable.UpgradeableArmorItem;
import net.kaneka.planttech2.packets.CropConfigChangeMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID)
public class PlayerEvents
{
	@SubscribeEvent
	public static void playerConnect(PlayerEvent.PlayerLoggedInEvent event)
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
}
