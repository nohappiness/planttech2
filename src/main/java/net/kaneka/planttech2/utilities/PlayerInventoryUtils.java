package net.kaneka.planttech2.utilities;

import net.kaneka.planttech2.items.CreditCardItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PlayerInventoryUtils
{
	public static boolean enoughSpace(Inventory inv, int neededSpace)
	{
		if (neededSpace <= 0)
		{
			return true;
		}
		
		int freeSlots = 0;
		for (int i = 0; i < 36; i++)
		{
			if (inv.getItem(i).isEmpty())
			{
				freeSlots++;
				if (freeSlots >= neededSpace)
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean hasList(Inventory inv, List<ItemStack> stacks)
	{
		for (int i = 0; i < stacks.size(); i++)
		{
			ItemStack stack = stacks.get(i);
			if (!stack.isEmpty())
			{
				if (inv.countItem(stack.getItem()) < stack.getCount())
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean addList(Inventory inv, List<ItemStack> stacks)
	{
		boolean flag = true; 
		for(ItemStack stack: stacks)
		{
			if(!inv.add(stack))
			{
				flag = false; 
			}
		}
		return flag; 
	}

	public static boolean removeList(Inventory inv, List<ItemStack> stacks)
	{
		boolean flag = true; 
		for(ItemStack stack: stacks)
		{
			if(!remove(inv, stack))
			{
				flag = false; 
			}
		}
		return flag; 
	}

	public static boolean remove(Inventory inv, ItemStack stack)
	{
		int count = stack.getCount();
		for (int j = 0; j < 36; j++)
		{
			ItemStack invstack = inv.getItem(j);
			if (!invstack.isEmpty())
			{
				if (invstack.getItem() == stack.getItem())
				{
					int k = Math.min(count, invstack.getCount());
					count-= k; 
					invstack.shrink(k);
					if (invstack.isEmpty())
					{
						inv.setItem(j, ItemStack.EMPTY);
					}
					
					if(count <= 0)
					{
						return true; 
					}
				}
			}
		}
		return false; 
	}
	
	public static boolean enoughCredits(Inventory inv, int amount)
	{
		if(amount <=  0)
		{
			return true; 
		}
		
		int count = 0;
		for (int i = 0; i < 36; i++)
		{
			ItemStack invstack = inv.getItem(i);
			if (!invstack.isEmpty())
			{
				if(invstack.getItem() instanceof CreditCardItem)
				{
					count += CreditCardItem.getCredits(invstack);
					if(count >= amount)
					{
						return true; 
					}
				}
			}
		}
		return false; 
	}
	
	public static boolean removeCredits(Inventory inv, int amount)
	{
		int count = amount;
		if(count <=  0)
		{
			return true; 
		}
		for (int i = 0; i < 36; i++)
		{
			ItemStack invstack = inv.getItem(i);
			if (!invstack.isEmpty())
			{
				if(invstack.getItem() instanceof CreditCardItem)
				{
					int credits = CreditCardItem.getCredits(invstack);
					credits -= CreditCardItem.shrinkCredits(invstack, count);
					count -= credits;
					if(count <= 0)
					{
						return true; 
					}
				}
			}
		}
		return false; 
	}
	
	public static boolean addCredits(Inventory inv, int amount)
	{
		for (int i = 0; i < 36; i++)
		{
			ItemStack invstack = inv.getItem(i);
			if (!invstack.isEmpty())
			{
				if(invstack.getItem() instanceof CreditCardItem)
				{
					CreditCardItem.addCredits(invstack, amount);
				}
			}
		}
		return false; 
	}
}
