package net.kaneka.planttech2.handlers;

import net.kaneka.planttech2.entities.capabilities.techvillagertrust.ITechVillagerTrust;
import net.kaneka.planttech2.entities.capabilities.techvillagertrust.TechVillagerTrust;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler
{
	public static final void registerAll()
	{
		CapabilityManager.INSTANCE.register(ITechVillagerTrust.class, new TechVillagerTrust.TechVillagerTrustStorage(), TechVillagerTrust::new);
	}
	
}
