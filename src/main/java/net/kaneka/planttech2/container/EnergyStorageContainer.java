package net.kaneka.planttech2.container;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.EnergyStorageTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EnergyStorageContainer extends BaseContainer
{
	public EnergyStorageContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new EnergyStorageTileEntity()); 
	}

    public EnergyStorageContainer(int id, PlayerInventory player, EnergyTileEntity tileentity)
    {
	super(id, ModContainers.ENERGYSTORAGE,player, tileentity, 1);
	IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
	this.addSlot(new ChangeCheckSlot(tileentity, handler, 0, 135, 85));
    }

}


