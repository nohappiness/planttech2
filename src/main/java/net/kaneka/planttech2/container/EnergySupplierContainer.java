package net.kaneka.planttech2.container;

import net.kaneka.planttech2.container.BaseContainer.SlotItemHandlerWithInfo;
import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.EnergySupplierTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EnergySupplierContainer extends BaseContainer
{
	public EnergySupplierContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new EnergySupplierTileEntity());
	}

	public EnergySupplierContainer(int id, PlayerInventory player, EnergySupplierTileEntity tileentity)
	{
		super(id, ModContainers.ENERGYSUPPLIER, player, tileentity, 2);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);

		this.addSlot(new SlotItemHandlerWithInfo(handler, tileentity.getEnergyInSlot(), 167, 38, "slot.util.energyin"));
		this.addSlot(new SlotItemHandlerWithInfo(handler, tileentity.getEnergyOutSlot(), 167, 57, "slot.util.energyout"));
	}
	
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) 
	{
		return ItemStack.EMPTY;
	}
}
