package net.kaneka.planttech2.handlers;

import net.kaneka.planttech2.entities.capabilities.player.IPlayerRenderRGB;
import net.kaneka.planttech2.entities.capabilities.player.IRadiationEffect;
import net.kaneka.planttech2.entities.capabilities.player.PlayerRenderRGB;
import net.kaneka.planttech2.entities.capabilities.player.RadiationEffect;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.ITechVillagerTrust;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.kaneka.planttech2.fluids.capability.BiomassFluidEnergy;
import net.kaneka.planttech2.fluids.capability.IBiomassFluidEnergy;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler
{
	public static void registerAll()
	{
		CapabilityManager.INSTANCE.register(ITechVillagerTrust.class, new TechVillagerTrust.TechVillagerTrustStorage(), TechVillagerTrust::new);
		CapabilityManager.INSTANCE.register(IRadiationEffect.class, new RadiationEffect.RadiationEffectStorage(), RadiationEffect::new);
		CapabilityManager.INSTANCE.register(IPlayerRenderRGB.class, new PlayerRenderRGB.PlayerRenderRGBStorage(), PlayerRenderRGB::new);
		CapabilityManager.INSTANCE.register(IBiomassFluidEnergy.class, new BiomassFluidEnergy.BiomassFluidEnergyStorage(), BiomassFluidEnergy::new);
	}
}
