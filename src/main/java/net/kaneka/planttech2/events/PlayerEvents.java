package net.kaneka.planttech2.events;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.packets.CropListSyncMessage;
import net.kaneka.planttech2.packets.PlantTech2PacketHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlantTechMain.MODID)
public class PlayerEvents
{
	@SubscribeEvent
	public static void playerConnect(PlayerEvent.PlayerLoggedInEvent event)
	{
		Player player = event.getPlayer();
		if (!player.level.isClientSide())
		{
			PlantTechMain.LOGGER.info(event.getPlayer().getDisplayName().getString() + " has logged in, syncing crop list");
			PlantTech2PacketHandler.sendTo(new CropListSyncMessage(), (ServerPlayer) player);
		}
		/*
//
//		if (event.getEntity() instanceof ServerPlayerEntity && !event.getEntity().getEntityWorld().isRemote())
//		{
//			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
//			syncRadiationCapWithClient(player);
//			PlantTech2PacketHandler.sendTo(new CropConfigChangeMessage(PlantTechMain.croplist.getConfigs()), player);
//		}
//		if (event.getPlayer().getEntityWorld().isRemote() && event.getPlayer().getEntityWorld().getDimension().getType() == ModDimensions.getPlantTopiaDimensionType())
//		{
//			PlayerEntity player = event.getPlayer();
//			IPlayerRenderRGB capability = PlayerRenderRGB.getCap(player);
//			Biome biome = player.getEntityWorld().getBiome(player.getPosition());
//			if (biome instanceof PlantTopiaBaseBiome)
//			{
//				PlantTopiaBaseBiome pt2biome = (PlantTopiaBaseBiome) biome;
//				float[] rgb = new float[]{pt2biome.getFogRed(), pt2biome.getFogGreen(), pt2biome.getFogBlue()};
//				capability.setCurrentFogDensity(pt2biome.getFogDensity());
//				capability.setRGB(rgb);
//			}
		}
//		*/
	}

//	@SubscribeEvent
//	public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
//	{
//		PlayerEntity player = event.getPlayer();
//		if (player instanceof ServerPlayerEntity && !event.getEntity().getEntityWorld().isRemote())
//		{
//			syncRadiationCapWithClient((ServerPlayerEntity) player);
//		}
//	}
//
//	@SubscribeEvent
//	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
//	{
//		PlayerEntity player = event.getPlayer();
//		if (player instanceof ServerPlayerEntity && !event.getEntity().getEntityWorld().isRemote())
//		{
//			RadiationEffect.getCap((ServerPlayerEntity) player).setLevel(0.0F);
//			syncRadiationCapWithClient((ServerPlayerEntity) player);
//		}
//	}

	/*@SubscribeEvent
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
	}*/

//	@SubscribeEvent
//	public static void playerTicking(TickEvent.PlayerTickEvent event)
//	{
		/*
		PlayerEntity player = event.player;
		if (player == null || !player.isAlive() || !(player instanceof ServerPlayerEntity) || player.getEntityWorld().isRemote() || player.abilities.isCreativeMode)
		{
			if (player != null && player.getActivePotionEffect(ModEffects.RADIATION_SICKNESS) != null && player.abilities.isCreativeMode)
			{
				player.removePotionEffect(ModEffects.RADIATION_SICKNESS);
			}
			return;
		}
		Biome biome = player.getEntityWorld().getBiome(player.getPosition());
		IRadiationEffect capability = RadiationEffect.getCap((ServerPlayerEntity) player);
		if (biome instanceof PlantTopiaBaseBiome)
		{
			BiomeRadiation level = ((PlantTopiaBaseBiome) biome).getRadiationLevel();
			if (level == BiomeRadiation.FRESH)
			{
				capability.decreaseLevel(BiomeRadiation.getDensity(level));
			}
			else
			{
				capability.increaseLevel(BiomeRadiation.getDensity(level));
			}
		}
		else
		{
			//heals player in all biomes that are not created by pt2
			capability.decreaseLevel((1.0F / 108000.0F));
		}*/
		//if (capability.getLevel() >= 1.0F && (player.getActivePotionEffect(ModEffects.RADIATION_SICKNESS) == null || player.getActivePotionEffect(ModEffects.RADIATION_SICKNESS).getDuration() <= /*32767*/33000))
		/*{
			player.addPotionEffect(new EffectInstance(ModEffects.RADIATION_SICKNESS, 99999));
		}
		else if (capability.getLevel() < 1.0F && player.getActivePotionEffect(ModEffects.RADIATION_SICKNESS) != null)
		{
			player.removePotionEffect(ModEffects.RADIATION_SICKNESS);
		}
		*/
//	}

//	@SubscribeEvent
//	public static void onPlayerHurt(LivingDamageEvent event)
//	{
//		Entity trueSource = event.getSource().getTrueSource();
//		if (event.getEntityLiving() instanceof ServerPlayerEntity && !((ServerPlayerEntity) event.getEntityLiving()).abilities.isCreativeMode && trueSource instanceof IAffectPlayerRadiation)
//			if (((IAffectPlayerRadiation) trueSource).shouldAffectPlayer())
//				((IAffectPlayerRadiation) trueSource).onTriggerAffectingPlayer((ServerPlayerEntity) event.getEntityLiving());
//	}

//		@SubscribeEvent
//		public static void onPlayerClone(PlayerEvent.Clone event)
//		{
//			/*if(event.isWasDeath())
//			{
//				event.getOriginal().revive();
//				ITechVillagerTrust old = event.getOriginal().getCapability(TechVillagerTrust.INSTANCE).orElse(null);
//				ITechVillagerTrust playercap = event.getPlayer().getCapability(TechVillagerTrust.INSTANCE).orElse(new TechVillagerTrust());
//				if(old != null)
//				{
//					for(Entry<String, Integer> entry: old.getTrustsMap().entrySet())
//					{
//						playercap.setTrust(entry.getKey(), entry.getValue());
//					}
//				}
//			}*/
//			IRadiationEffect oldCap = RadiationEffect.getCap((ServerPlayerEntity) event.getOriginal());
//			IRadiationEffect newCap = RadiationEffect.getCap((ServerPlayerEntity) event.getEntityLiving());
//			if (event.isWasDeath())
//			{
//				newCap.setLevel(oldCap.getLevel());
//			}
//			else
//			{
//				newCap.setLevel(0.0F);
//			}
//		}

//	private static void syncRadiationCapWithClient(ServerPlayerEntity player)
//	{
//		PlantTech2PacketHandler.sendTo(new SyncRadiationLevelMessage(RadiationEffect.getCap(player).getLevel()), player);
//	}
}
