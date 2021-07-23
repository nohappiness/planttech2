package net.kaneka.planttech2.addons.jei.libs;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AbstractJeiRecipe
{
	protected List<ItemStack> inputs = new ArrayList<ItemStack>();
	protected List<ItemStack> outputs = new ArrayList<ItemStack>();
	
	public int getInputLength()
	{
		return inputs.size();
	}
	
	public int getOutputLength()
	{
		return outputs.size();
	}
	
	public ItemStack getInput(int i)
	{
		if(i < getInputLength())
		{
			return inputs.get(i);
		}
		return ItemStack.EMPTY;
	}
	
	public List<ItemStack> getInputs()
	{
		return inputs; 
	}
	
	public ItemStack getOutput(int i)
	{
		if(i < getOutputLength())
		{
			return outputs.get(i);
		}
		return ItemStack.EMPTY;
		
	}
	
	public List<ItemStack> getOutputs()
	{
		return outputs; 
	}
}
