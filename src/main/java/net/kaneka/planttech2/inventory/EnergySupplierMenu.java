package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.EnergySupplierBlockEntity;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EnergySupplierMenu extends BaseMenu
{
	public EnergySupplierMenu(int id, Inventory inv)
	{
		this(id, inv, new EnergySupplierBlockEntity());
	}

	public EnergySupplierMenu(int id, Inventory player, EnergySupplierBlockEntity tileentity)
	{
		super(id, ModContainers.ENERGYSUPPLIER, player, tileentity, 2);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
	}

    public EnergySupplierMenu(int i, Inventory inventory, BlockPos blockPos)
    {
		this(i, inventory, (EnergySupplierBlockEntity) inventory.player.level.getBlockEntity(blockPos));
	}

    @Override
	public ItemStack quickMoveStack(Player playerIn, int index)
	{
		return ItemStack.EMPTY;
	}
}
