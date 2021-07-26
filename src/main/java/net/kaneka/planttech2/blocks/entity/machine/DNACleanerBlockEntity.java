package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryBlockEntity;
import net.kaneka.planttech2.inventory.DNACleanerMenu;
import net.kaneka.planttech2.items.DNAContainerItem;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DNACleanerBlockEntity extends ConvertEnergyInventoryBlockEntity
{
    protected final ContainerData field_array = new ContainerData()
	{
        public int get(int index) {
           switch(index) {
           case 0:
              return DNACleanerBlockEntity.this.energystorage.getEnergyStored();
           case 1:
              return DNACleanerBlockEntity.this.energystorage.getMaxEnergyStored();
           case 2:
              return DNACleanerBlockEntity.this.ticksPassed;
           default:
              return 0;
           }
        }

        public void set(int index, int value) {
           switch(index) {
           case 0:
        	   DNACleanerBlockEntity.this.energystorage.setEnergyStored(value);
              break;
           case 1:
        	   DNACleanerBlockEntity.this.energystorage.setEnergyMaxStored(value);
              break;
           case 2:
        	   DNACleanerBlockEntity.this.ticksPassed = value;;
              break;
           }

        }
        public int getCount() {
           return 3;
        }
	};

    public DNACleanerBlockEntity(BlockPos pos, BlockState state)
    {
		super(ModTileEntities.DNACLEANER_TE, pos, state,1000, 6, PlantTechConstants.MACHINETIER_DNA_CLEANER);
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
	public ContainerData getIntArray()
	{
		return field_array;
	}

    @Override
    public String getNameString()
    {
		return "dnacleaner";
    }

    @Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new DNACleanerMenu(id, inv, this);
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
