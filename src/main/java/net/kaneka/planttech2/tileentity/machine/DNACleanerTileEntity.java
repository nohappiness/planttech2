package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.DNACleanerContainer;
import net.kaneka.planttech2.items.DNAContainerItem;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.ConvertEnergyInventoryTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;

public class DNACleanerTileEntity extends ConvertEnergyInventoryTileEntity
{
    protected final IIntArray field_array = new IIntArray()
	{
        public int get(int index) {
           switch(index) {
           case 0:
              return DNACleanerTileEntity.this.energystorage.getEnergyStored();
           case 1:
              return DNACleanerTileEntity.this.energystorage.getMaxEnergyStored();
           case 2:
              return DNACleanerTileEntity.this.ticksPassed;
           default:
              return 0;
           }
        }

        public void set(int index, int value) {
           switch(index) {
           case 0:
        	   DNACleanerTileEntity.this.energystorage.setEnergyStored(value);
              break;
           case 1:
        	   DNACleanerTileEntity.this.energystorage.setEnergyMaxStored(value);
              break;
           case 2:
        	   DNACleanerTileEntity.this.ticksPassed = value;;
              break;
           }

        }
        public int size() {
           return 3;
        }
	};

    public DNACleanerTileEntity()
    {
		super(ModTileEntities.DNACLEANER_TE,1000, 6, PlantTechConstants.MACHINETIER_DNA_CLEANER);
    }

	@Override
	protected boolean canProceed(ItemStack input, ItemStack output)
	{
		return input.getItem() instanceof DNAContainerItem;
	}

	@Override
	protected ItemStack getResult(ItemStack input, ItemStack output)
	{
		return new ItemStack(ModItems.DNA_CONTAINER_EMPTY);
	}

	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

    @Override
    public String getNameString()
    {
		return "dnacleaner";
    }

    @Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new DNACleanerContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 3;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 4;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 5;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 50;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 2;
	}
}
