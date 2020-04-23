package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.dimensions.planttopia.biomes.BiomeRadiation;
import net.kaneka.planttech2.dimensions.planttopia.biomes.PlantTopiaBaseBiome;
import net.kaneka.planttech2.entities.capabilities.player.IRadiationEffect;
import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.kaneka.planttech2.items.upgradeable.UpgradeableArmorItem;
import net.kaneka.planttech2.packets.CropConfigChangeMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.kaneka.planttech2.packets.SyncRadiationLevelMessage;
import net.kaneka.planttech2.registries.ModDimensions;
import net.kaneka.planttech2.registries.ModEffects;
import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID)
public class PlayerEvents
{
	@SubscribeEvent
	public static void playerConnect(PlayerEvent.PlayerLoggedInEvent event)
	{
		if (event.getEntity() instanceof ServerPlayerEntity && !event.getEntity().getEntityWorld().isRemote())
		{
			syncRadiationCapWithClient((ServerPlayerEntity) event.getEntity());
		}
		ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
		PlantTech2PacketHandler.sendTo(new CropConfigChangeMessage(PlantTechMain.croplist.getConfigs()), player);
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
	{
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity && !event.getEntity().getEntityWorld().isRemote())
		{
			syncRadiationCapWithClient((ServerPlayerEntity) player);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
	{
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity && !event.getEntity().getEntityWorld().isRemote())
		{
			RadiationEffect.getCap((ServerPlayerEntity) player).setLevel(0.0F);
			syncRadiationCapWithClient((ServerPlayerEntity) player);
		}
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
	public static void attachCapability(final AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof PlayerEntity && !event.getCapabilities().containsKey(ModReferences.RADIATIONEFFECTCAP))
		{
//			event.addCapability(ModReferences.TECHVILLAGERTRUSTCAP, new TechVillagerTrust());
			event.addCapability(ModReferences.RADIATIONEFFECTCAP, new RadiationEffect());
		}
	}

	@SubscribeEvent
	public static void playerTicking(TickEvent.PlayerTickEvent event)
	{
		PlayerEntity player = event.player;
		if (player == null || !player.isAlive() || !(player instanceof ServerPlayerEntity) || player.getEntityWorld().isRemote() || player.getEntityWorld().getDimension().getType() != ModDimensions.getPlantTopiaDimensionType())
		{
			return;
		}
		Biome biome = player.getEntityWorld().getBiome(player.getPosition());
		IRadiationEffect capability = RadiationEffect.getCap((ServerPlayerEntity) player);
		float amount = capability.getLevel();
		if (biome instanceof PlantTopiaBaseBiome)
		{
			BiomeRadiation level = ((PlantTopiaBaseBiome) biome).getRadiationLevel();
			if (level == BiomeRadiation.FRESH)
			{
				if (amount - BiomeRadiation.getDensity(level) < 0.0F)
				{
					capability.setLevel(0.0F);
				}
				else
				{
					capability.decreaseLevel(BiomeRadiation.getDensity(level));
				}
			}
			else
			{
				if (amount + BiomeRadiation.getDensity(level) > 2.0F)
				{
					capability.setLevel(2.0F);
				}
				else
				{
					capability.increaseLevel(BiomeRadiation.getDensity(level));
				}
			}
			if (capability.getLevel() >= 1.0F && player.getActivePotionEffect(ModEffects.RADIATION_SICKNESS) == null)
			{
				player.addPotionEffect(new EffectInstance(ModEffects.RADIATION_SICKNESS, 99999));
			}
			else if (capability.getLevel() < 1.0F && player.getActivePotionEffect(ModEffects.RADIATION_SICKNESS) != null)
			{
				player.removePotionEffect(ModEffects.RADIATION_SICKNESS);
			}
		}
		else
		{
			float heal = (1.0F / 54000.0F);
			if (amount + heal < 0.0F)
			{
				capability.setLevel(0.0F);
			}
			else
			{
				capability.decreaseLevel(heal);
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		/*if(event.isWasDeath())
		{
    		event.getOriginal().revive();
    		ITechVillagerTrust old = event.getOriginal().getCapability(TechVillagerTrust.INSTANCE).orElse(null); 
    		ITechVillagerTrust playercap = event.getPlayer().getCapability(TechVillagerTrust.INSTANCE).orElse(new TechVillagerTrust()); 
    		if(old != null)
    		{
    			for(Entry<String, Integer> entry: old.getTrustsMap().entrySet())
    			{
    				playercap.setTrust(entry.getKey(), entry.getValue());
    			}
    		}
		}*/
		IRadiationEffect oldCap = RadiationEffect.getCap((ServerPlayerEntity) event.getOriginal());
		IRadiationEffect newCap = RadiationEffect.getCap((ServerPlayerEntity) event.getEntityLiving());
		if (event.isWasDeath())
		{
			newCap.setLevel(oldCap.getLevel());
		}
		else
		{
			newCap.setLevel(0.0F);
		}
	}

	private static void syncRadiationCapWithClient(ServerPlayerEntity player)
	{
		PlantTech2PacketHandler.sendTo(new SyncRadiationLevelMessage(RadiationEffect.getCap(player).getLevel()), player);
	}
}
