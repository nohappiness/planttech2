package net.kaneka.planttech2.container;

import net.kaneka.planttech2.tileentity.machine.TileEntityEnergy;
import net.kaneka.planttech2.tileentity.machine.TileEntityMegaFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;

public class ContainerBase extends Container
{
    protected final TileEntityEnergy tileentity;
    protected int[] fields;
    private int amountSlots;

    public ContainerBase(InventoryPlayer player, TileEntityEnergy tileentity, int slots)
    {
	this.amountSlots = slots;
	for (int y = 0; y < 3; y++)
	{
	    for (int x = 0; x < 9; x++)
	    {
		this.addSlot(new Slot(player, x + y * 9 + 9, 24 + x * 18, 107 + y * 18));
	    }
	}

	for (int x = 0; x < 9; x++)
	{
	    this.addSlot(new Slot(player, x, 24 + x * 18, 165));
	}

	this.tileentity = tileentity;
	this.fields = new int[this.tileentity.getAmountFields()];
	for (int i = 0; i < this.tileentity.getAmountFields(); i++)// forcing sync on opening GUI
	{
	    this.fields[i] = -1;
	}
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
	return this.tileentity.isUsableByPlayer(playerIn);
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
    }
}
