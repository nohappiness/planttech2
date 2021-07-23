package net.kaneka.planttech2.addons.jei.machine_growing;

import net.kaneka.planttech2.addons.jei.libs.AbstractJeiRecipe;
import net.minecraft.world.item.ItemStack;

public class MachineGrowingRecipe extends AbstractJeiRecipe
{
	public MachineGrowingRecipe(ItemStack bulb, ItemStack shell, ItemStack machine)
	{
		inputs.add(bulb);
		inputs.add(shell); 
		outputs.add(machine); 
	}
}
