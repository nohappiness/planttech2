package net.kaneka.planttech2.container;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;

public class TeleporterContainer extends Container
{
	private ItemStack stack; 
	
	public TeleporterContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new ItemStack(ModItems.CYBERBOW)); 
	}
	
	public TeleporterContainer(int id, PlayerInventory playerInv, ItemStack itemInv)
	{ 
		super(ModContainers.TELEPORTERITEM, id); 
		stack = itemInv; 
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) 
	{
		return stack;
	}
	
	public ItemStack getStack()
	{
		return stack; 
	}
}
