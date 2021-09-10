package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.MegaFurnaceBlockEntity;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class MegaFurnaceMenu extends BaseMenu
{
	public MegaFurnaceMenu(int id, Inventory inv)
	{
		this(id, inv, new MegaFurnaceBlockEntity());
	}
	public MegaFurnaceMenu(int id, Inventory player, MegaFurnaceBlockEntity tileentity)
	{
		super(id, ModContainers.MEGAFURNACE, player, tileentity, 15);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		for(int x = 0; x < 6; x++)
			this.addSlot(new SlotItemHandlerWithInfo(handler, x, 21 + x * 22 , 27, "slot.megafurnace.input"));
		for(int x = 0; x < 6; x++)
			this.addSlot(createOutoutSlot(handler, x + 6, 21 + x * 22 , 64));
		this.addSlot(createSpeedUpgradeSlot(handler, 12, 109, 85));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
	}

    public MegaFurnaceMenu(int i, Inventory inventory, BlockPos blockPos)
    {
		this(i, inventory, (MegaFurnaceBlockEntity) inventory.player.level.getBlockEntity(blockPos));
	}
}
