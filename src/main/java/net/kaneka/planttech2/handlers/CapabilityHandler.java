package net.kaneka.planttech2.handlers;

import net.kaneka.planttech2.fluids.capability.IBiomassFluidEnergy;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler
{
	public static void registerAll()
	{
//		CapabilityManager.INSTANCE.register(ITechVillagerTrust.class, new TechVillagerTrust.TechVillagerTrustStorage(), TechVillagerTrust::new);
//		CapabilityManager.INSTANCE.register(IRadiationEffect.class, new RadiationEffect.RadiationEffectStorage(), RadiationEffect::new);
//		CapabilityManager.INSTANCE.register(IPlayerRenderRGB.class, new PlayerRenderRGB.PlayerRenderRGBStorage(), PlayerRenderRGB::new);
		CapabilityManager.INSTANCE.register(IBiomassFluidEnergy.class);
	}
}
