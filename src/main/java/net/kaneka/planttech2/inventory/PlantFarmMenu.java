package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.DNAExtractorBlockEntity;
import net.kaneka.planttech2.blocks.entity.machine.PlantFarmBlockEntity;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class PlantFarmMenu extends BaseMenu
{
	public PlantFarmMenu(int id, Inventory inv)
	{
		this(id, inv, new PlantFarmBlockEntity());
	}
	
	public PlantFarmMenu(int id, Inventory player, PlantFarmBlockEntity tileentity)
	{
		super(id, ModContainers.PLANTFARM, player, tileentity, 17);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		this.addSlot(new SlotItemHandlerWithInfo(handler, 0, 84, 41, "slot.plantfarm.seed"));
		for(int y = 0; y < 2; y++)
			for(int x = 0; x < 5; x++)
				this.addSlot(new LimitedItemInfoSlot(handler, 1 + x + y * 5, 59 + x * 18, 67 + y * 18, "slot.plantfarm.storage").setConditions(false));
		this.addSlot(createSpeedUpgradeSlot(handler, 11, 59, 41));
		this.addSlot(createRangeUpgradeSlot(handler, 12, 131, 41));
		this.addSlot(createFluidInSlot(handler, 23, 38));
		this.addSlot(createFluidOutSlot(handler, 23, 57));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
		
		
	}

    public PlantFarmMenu(int i, Inventory inventory, BlockPos blockPos)
    {
		this(i, inventory, (PlantFarmBlockEntity) inventory.player.level.getBlockEntity(blockPos));
	}
}
