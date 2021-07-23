package net.kaneka.planttech2.entities.tradesandjobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.item.ItemStack;

public class TechVillagerTradePool
{
	private final String name; 
	private final List<ItemStackWithRandomSize> inputs;
	private final List<ItemStackWithRandomSize> outputs;
	private final int creditsBuy, creditsSell;
	private final int neededTrustLevel;

	public TechVillagerTradePool(String name, List<ItemStackWithRandomSize> inputs, List<ItemStackWithRandomSize> outputs, int creditsBuy, int creditsSell, int neededTrustLevel)
	{
		this.name = name; 
		this.inputs = inputs;
		this.outputs = outputs;
		this.creditsBuy = creditsBuy;
		this.creditsSell = creditsSell;
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
		return new TechVillagerTrade(name, inputstacks, outputstacks, creditsBuy, creditsSell, neededTrustLevel);
	}

	
}
