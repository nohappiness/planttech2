package net.kaneka.planttech2.addons.jei.machinebulbreprocessor;

import net.kaneka.planttech2.addons.jei.libs.AbstractJeiRecipe;
import net.kaneka.planttech2.items.KnowledgeChip;
import net.minecraft.world.item.ItemStack;

public class MachinebulbReprocessorRecipe extends AbstractJeiRecipe
{
	private int biomass; 
	
	public MachinebulbReprocessorRecipe(int tier, ItemStack output, int biomass)
	{
		inputs.add(KnowledgeChip.getByTier(tier));
		outputs.add(output); 
		this.biomass = biomass; 
	}
	
	public int getBiomass()
	{
		return biomass; 
	}
}
