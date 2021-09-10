package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class TeleporterContainer extends AbstractContainerMenu
{
	private ItemStack stack; 
	
	public TeleporterContainer(int id, Inventory inv)
	{
		this(id, inv, new ItemStack(ModItems.CYBERBOW), -1);
	}
	
	public TeleporterContainer(int id, Inventory playerInv, ItemStack itemInv, int slot)
	{ 
		super(ModContainers.TELEPORTERITEM, id); 
		stack = itemInv; 
	}

    @Override
	public boolean stillValid(Player  playerIn)
	{
		return true;
	}
	
	@Override
	public ItemStack quickMoveStack(Player playerIn, int index)
	{
		return ItemStack.EMPTY;
	}
	
	public ItemStack getStack()
	{
		return stack; 
	}
}
