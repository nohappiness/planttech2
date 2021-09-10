package net.kaneka.planttech2.inventory;

import net.kaneka.planttech2.blocks.entity.machine.PlantTopiaTeleporterBlockEntity;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.registries.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class PlantTopiaTeleporterMenu extends BaseMenu
{
	public PlantTopiaTeleporterMenu(int id, Inventory inv)
	{
		this(id, inv, new PlantTopiaTeleporterBlockEntity());
	}
	
	public PlantTopiaTeleporterMenu(int id, Inventory player, PlantTopiaTeleporterBlockEntity tileentity)
	{
		super(id, ModContainers.PLANTTOPIATELEPORTER, player, tileentity, 2);
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		this.addSlot(createEnergyInSlot(handler, 150, 86));
		this.addSlot(createEnergyOutSlot(handler, 168, 86));
	}

    public PlantTopiaTeleporterMenu(int i, Inventory inventory, BlockPos blockPos)
    {
		this(i, inventory, (PlantTopiaTeleporterBlockEntity) inventory.player.level.getBlockEntity(blockPos));
	}
}
