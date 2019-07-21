package net.kaneka.planttech2.container;

import net.kaneka.planttech2.registries.ModContainers;
import net.kaneka.planttech2.tileentity.machine.EnergyStorageTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EnergyStorageContainer extends BaseContainer
{
	public EnergyStorageContainer(int id, PlayerInventory inv)
	{
		this(id, inv, new EnergyStorageTileEntity()); 
	}

    public EnergyStorageContainer(int id, PlayerInventory player, EnergyStorageTileEntity tileentity)
    {
	super(id, ModContainers.ENERGYSTORAGE,player, tileentity, 3);
	IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
	this.addSlot(new ChangeCheckSlot(tileentity, handler, 0, 132, 64, "slot.util.energystorageupgrade"));
	this.addSlot(new SlotItemHandlerWithInfo(handler, tileentity.getEnergyInSlot(), 150, 86, "slot.util.energyin"));
	this.addSlot(new SlotItemHandlerWithInfo(handler, tileentity.getEnergyOutSlot(), 168, 86, "slot.util.energyout"));
    }
    
    class ChangeCheckSlot extends SlotItemHandlerWithInfo
    {
        private EnergyStorageTileEntity te; 

        public ChangeCheckSlot(EnergyStorageTileEntity te, IItemHandler itemHandler, int index, int xPosition, int yPosition, String usage)
        {
    	super(itemHandler, index, xPosition, yPosition, usage);
    	this.te = te; 
        }

        @Override
        public void onSlotChanged()
        {
    	te.onSlotContentChanged();
    	super.onSlotChanged();
        }
    }

}


