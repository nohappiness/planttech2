package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.SolarGeneratorBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.TierItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class SolarGeneratorMenu extends BaseMenu
{
	public SolarGeneratorMenu(int id, Inventory inv)
	{
		this(id, inv, new SolarGeneratorBlockEntity());
	}

	public SolarGeneratorMenu(int id, Inventory player, SolarGeneratorBlockEntity tileentity)
	{
		super(id, ModContainers.SOLARGENERATOR, player, tileentity, 4);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(new LimitedItemInfoSlot(handler, 0, 95, 60, "slot.solargenerator.focus").setLimited().setConditions(TierItem.ItemType.SOLAR_FOCUS));
		this.addSlot(createSpeedUpgradeSlot(handler, 1, 95, 31));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler, 167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
	}

	public SolarGeneratorMenu(int i, Inventory inventory, BlockPos blockPos)
	{
		this(i, inventory, (SolarGeneratorBlockEntity) inventory.player.level.getBlockEntity(blockPos));
	}
}
