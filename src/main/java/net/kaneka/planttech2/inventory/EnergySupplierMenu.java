package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.EnergySupplierBlockEntity;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EnergySupplierContainer extends BaseContainer
{
	public EnergySupplierContainer(int id, Inventory inv)
	{
		this(id, inv, new EnergySupplierBlockEntity());
	}

	public EnergySupplierContainer(int id, Inventory player, EnergySupplierBlockEntity tileentity)
	{
		super(id, ModContainers.ENERGYSUPPLIER, player, tileentity, 2);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) 
	{
		return ItemStack.EMPTY;
	}
}
