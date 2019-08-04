package net.kaneka.planttech2.entities.tradesandjobs;

import java.util.Random;

import net.minecraft.item.ItemStack;

public class ItemStackWithRandomSize
{
	private final ItemStack stack;
	private final int min, max;

	public ItemStackWithRandomSize(ItemStack stack, int min, int max)
	{
		this.stack = stack;
		this.min = min;
		this.max = max;
	}

	public ItemStack getRandomSizeStack(Random rand)
	{
		ItemStack retval = stack.copy();
		retval.setCount(min + rand.nextInt(max - min + 1));
		return retval;
	}
}