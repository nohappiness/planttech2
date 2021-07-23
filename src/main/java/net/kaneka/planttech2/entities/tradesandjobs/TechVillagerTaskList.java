package net.kaneka.planttech2.entities.tradesandjobs;

import java.util.ArrayList;
import java.util.List;

import net.kaneka.planttech2.entities.passive.TechVillagerEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TechVillagerTaskList
{
	private static List<List<TechVillagerTask>> lists; 
	private static int maxid = 0; 
	
	public static List<TechVillagerTask> getTaskList(int i)
	{
		if(lists == null)
		{
			initLists();
		}
		return lists.get(i);
	}
	
	public static void initLists()
	{
		lists = new ArrayList<List<TechVillagerTask>>(); 
		int id = 0; 
		List<TechVillagerTask> listScientists = new ArrayList<TechVillagerTask>(); 
		listScientists.add(new TechVillagerTask(id++, "Get lunch", TechVillagerEntity.SCIENTIST, generateStackList(new ItemStack(Items.APPLE,5), new ItemStack(Items.POTATO,5), new ItemStack(Items.CARROT,5)), 10, 0, 1));
		listScientists.add(new TechVillagerTask(id++, "Kill 5 ghasts", TechVillagerEntity.SCIENTIST, generateStackList(new ItemStack(Items.GHAST_TEAR, 5)), 100, 1, 2));
		listScientists.add(new TechVillagerTask(id++, "DIAMONDS", TechVillagerEntity.SCIENTIST, generateStackList(new ItemStack(Items.DIAMOND, 64)), 1000, 2, 5));
		
		lists.add(listScientists); 
		maxid = id; 
		
	}
	
	public static TechVillagerTask getByID(int id)
	{
		if(lists == null)
		{
			initLists();
		}
		
		for(List<TechVillagerTask> list: lists)
		{
			for(TechVillagerTask task: list)
			{
				if(id == task.getID())
				{
					return task; 
				}
			}
		}
		return null; 
		
	}
	
	private static List<ItemStack> generateStackList(ItemStack... stacks)
	{
		List<ItemStack> list = new ArrayList<ItemStack>(); 
		for(int i = 0; i < stacks.length; i++)
		{
			list.add(stacks[i]);
		}
		return list; 
	}
}
