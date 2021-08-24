package net.kaneka.planttech2.addons.jei.carver;


import net.kaneka.planttech2.addons.jei.libs.AbstractJeiRecipe;
import net.minecraft.world.item.ItemStack;

public class CarverRecipe extends AbstractJeiRecipe
{
	public CarverRecipe(ItemStack input, ItemStack output)
	{
		inputs.add(input);
		outputs.add(output);
	}
}
