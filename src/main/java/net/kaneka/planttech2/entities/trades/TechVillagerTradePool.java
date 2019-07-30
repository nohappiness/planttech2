package net.kaneka.planttech2.entities.trades;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;

public class TechVillagerTradePool
{
	private final List<ItemStackWithRandomSize> inputs;
	private final List<ItemStackWithRandomSize> outputs;
	private final int credits;
	private final int neededTrustLevel;

	public TechVillagerTradePool(List<ItemStackWithRandomSize> inputs, List<ItemStackWithRandomSize> outputs, int credits, int neededTrustLevel)
	{
		this.inputs = inputs;
		this.outputs = outputs;
		this.credits = credits;
		this.neededTrustLevel = neededTrustLevel;
	}

	public TechVillagerTrade generateTechVillagerTrade()
	{
		List<ItemStack> inputstacks = new ArrayList<>(), outputstacks = new ArrayList<>();
		Random rand = new Random();
		for (ItemStackWithRandomSize template : inputs)
		{
			inputstacks.add(template.getRandomSizeStack(rand));
		}

		for (ItemStackWithRandomSize template : outputs)
		{
			outputstacks.add(template.getRandomSizeStack(rand));
		}
		return new TechVillagerTrade(inputstacks, outputstacks, credits, neededTrustLevel);
	}

	
}
