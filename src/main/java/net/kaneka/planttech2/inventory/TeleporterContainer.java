package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class TeleporterContainer extends Container
{
	private ItemStack stack; 
	
	public TeleporterContainer(int id, Inventory inv)
	{
		this(id, inv, new ItemStack(ModItems.CYBERBOW)); 
	}
	
	public TeleporterContainer(int id, Inventory playerInv, ItemStack itemInv)
	{ 
		super(ModContainers.TELEPORTERITEM, id); 
		stack = itemInv; 
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn)
	{
		return true;
	}
	
	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) 
	{
		return stack;
	}
	
	public ItemStack getStack()
	{
		return stack; 
	}
}
