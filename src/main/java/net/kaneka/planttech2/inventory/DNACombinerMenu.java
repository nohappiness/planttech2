package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.DNACombinerBlockEntity;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class DNACombinerMenu extends BaseMenu
{
	
	public DNACombinerMenu(int id, Inventory inv)
	{
		this(id, inv, new DNACombinerBlockEntity());
	}
	
	public DNACombinerMenu(int id, Inventory player, DNACombinerBlockEntity tileentity)
	{
		super(id, ModContainers.DNACOMBINER, player, tileentity, 7);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		
		this.addSlot(createDNAContainerSlot(handler, 0, 77, 37, "slot.dnacombiner.container", false));
		this.addSlot(createDNAContainerSlot(handler, 1, 113, 37, "slot.dnacombiner.container", false));
		this.addSlot(createDNAContainerSlot(handler, 2, 121, 56, "slot.dnacombiner.empty_container", true));
		this.addSlot(createOutoutSlot(handler, 3, 95, 73));
		this.addSlot(createSpeedUpgradeSlot(handler, 4, 54, 50));
		this.addSlot(createEnergyInSlot(handler, 167, 38));
		this.addSlot(createEnergyOutSlot(handler,167, 57));
		this.addSlot(createKnowledgeChipSlot(handler, 12, 9));
	}

    public DNACombinerMenu(int i, Inventory inventory, BlockPos blockPos)
    {
		this(i, inventory, (DNACombinerBlockEntity) inventory.player.level.getBlockEntity(blockPos));
	}
}
