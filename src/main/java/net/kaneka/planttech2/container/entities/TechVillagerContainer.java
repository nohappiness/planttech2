package net.kaneka.planttech2.container.entities;

import java.util.ArrayList;
import java.util.List;

import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTask;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTaskList;
import net.kaneka.planttech2.entities.tradesandjobs.TechVillagerTrade;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class TechVillagerContainer extends Container
{
	private List<TechVillagerTrade> trades; 
	private List<TechVillagerTask> tasks; 
	private int profession; 
	
	public TechVillagerContainer(int id, PlayerInventory inv, PacketBuffer data)
	{
		this(id, inv, new ArrayList<TechVillagerTrade>(), 0); 
		List<TechVillagerTrade> list = new ArrayList<TechVillagerTrade>();
		profession = data.readInt(); 
		tasks = TechVillagerTaskList.getTaskList(profession); 
		int size = data.readInt(); 
		for(int i = 0; i < size; i++)
		{
			list.add(TechVillagerTrade.fromBuffer(data));
		}
		trades = list; 
	}
	
	public TechVillagerContainer(int id, PlayerInventory playerInv, List<TechVillagerTrade> trades, int profession)
	{ 
		super(ModContainers.TECHVILLAGER, id); 
		this.trades = trades; 
		this.profession = profession; 
		tasks = TechVillagerTaskList.getTaskList(profession); 
		
		for (int y = 0; y < 3; y++)
		{
		    for (int x = 0; x < 9; x++)
		    {
		    	this.addSlot(new Slot(playerInv, x + y * 9 + 9, 147 + x * 18, 112 + y * 18));
		    }
		}

		for (int x = 0; x < 9; x++)
		{
		    this.addSlot(new Slot(playerInv, x, 147 + x * 18, 170));
		}
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn)
	{
		return true;
	}
	
	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.slots.get(index);
		if(slot != null && slot.hasItem()) 
		{
			ItemStack stack1 = slot.getItem();
			stack = stack1.copy();
			
			if(index < 36)
			{
				if(index >= 0 && index < 27)
				{
					if(!this.moveItemStackTo(stack1, 27, 35, false)) return ItemStack.EMPTY;
				}
				else if(index >= 27 && index < 36 && !this.moveItemStackTo(stack1, 0, 26, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(!this.moveItemStackTo(stack1, 0, 35, false)) 
			{
				return ItemStack.EMPTY;
			}
			
			
			if(stack1.isEmpty())
			{
				slot.set(ItemStack.EMPTY);
			}
			else
			{
				slot.setChanged();

			}
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}
	
	public void setTrades(List<TechVillagerTrade> trades)
	{
		this.trades = trades; 
	}
	
	public List<TechVillagerTrade> getTrades()
	{
		return trades; 
	}
	
	public List<TechVillagerTask> getTasks()
	{
		return tasks; 
	}
	
	public int getProfession()
	{
		return profession; 
	}
}
