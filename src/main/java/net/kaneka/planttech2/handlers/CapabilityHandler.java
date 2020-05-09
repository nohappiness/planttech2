package net.kaneka.planttech2.handlers;

import net.kaneka.planttech2.entities.capabilities.player.IRadiationEffect;
import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.ITechVillagerTrust;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.kaneka.planttech2.registries.ModReferences;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.HashSet;
import java.util.Map;

public class CapabilityHandler
{
	public static final HashSet<ResourceLocation> CAPABILITIES = new HashSet<ResourceLocation>()
	{
		{
			add(ModReferences.TECHVILLAGERTRUSTCAP);
			add(ModReferences.RADIATIONEFFECTCAP);
		}
	};

	public static void registerAll()
	{
		CapabilityManager.INSTANCE.register(ITechVillagerTrust.class, new TechVillagerTrust.TechVillagerTrustStorage(), TechVillagerTrust::new);
		CapabilityManager.INSTANCE.register(IRadiationEffect.class, new RadiationEffect.RadiationEffectStorage(), RadiationEffect::new);
	}
}
