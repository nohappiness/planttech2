package net.kaneka.planttech2.addons.jei.crossbreeding;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class CrossbreedingRecipe
{
	private List<ItemStack> parents = new ArrayList<ItemStack>();
	private ItemStack output;

	public CrossbreedingRecipe(ItemStack output, ItemStack parent1, ItemStack parent2)
	{
		this.output = output;
		parents.add(parent1);
		parents.add(parent2);
	}

	public List<ItemStack> getParents()
	{
		return parents;
	}
	
	public ItemStack getOutput()
	{
		return output;
		
	}

}
