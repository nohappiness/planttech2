package net.kaneka.planttech2.container;


import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;

public class ContainerBase extends Container
{
	protected final TileEntityEnergy tileentity;
	protected int[] fields;
	
	public ContainerBase(int id, ContainerType<?> type, PlayerInventory player, TileEntityEnergy tileentity, int slots)
	{
		super(type, id);
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				addSlot(new Slot(player, x + y * 9 + 9, 24 + x * 18, 107 + y * 18));
			}
		}

		for (int x = 0; x < 9; x++)
		{
			addSlot(new Slot(player, x, 24 + x * 18, 165));
		}

		this.tileentity = tileentity;
		this.fields = new int[this.tileentity.getAmountFields()];
		for (int i = 0; i < this.tileentity.getAmountFields(); i++)// forcing sync on opening GUI
		{
			this.fields[i] = -1;
		}
	}

	@Override
	public void updateProgressBar(int id, int data)
	{
		this.tileentity.setField(id, data);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		/*
		for (IContainerListener listener : listeners)
		{
			for (int i = 0; i < tileentity.getAmountFields(); i++)
			{
				if (fields[i] != tileentity.getField(i))
				{
					fields[i] = tileentity.getField(i);
					listener.sendWindowProperty(this, i, fields[i]);
				}
			}
		}
		*/
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return tileentity.isUsableByPlayer(playerIn);
	}
	
	public TileEntityEnergy getTE()
	{
		return tileentity; 
	}
}
