package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryFluidBlockEntity;
import net.kaneka.planttech2.inventory.MachineBulbReprocessorContainer;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.KnowledgeChip;
import net.kaneka.planttech2.items.MachineBulbItem;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags.Items;
import org.antlr.runtime.misc.ContainerData;

public class MachineBulbReprocessorBlockEntity extends ConvertEnergyInventoryFluidBlockEntity
{
	private int selectedId = 0;
	private int actualTier = 0; 
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return MachineBulbReprocessorBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return MachineBulbReprocessorBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
			    return MachineBulbReprocessorBlockEntity.this.biomassCap.getCurrentStorage();
			case 3:
			    return MachineBulbReprocessorBlockEntity.this.biomassCap.getMaxStorage();
			case 4: 
				return MachineBulbReprocessorBlockEntity.this.ticksPassed;
			case 5: 
				return MachineBulbReprocessorBlockEntity.this.selectedId;
			case 6: 
				return MachineBulbReprocessorBlockEntity.this.actualTier;
			case 7: 
				return MachineBulbReprocessorBlockEntity.this.worldPosition.getX();
			case 8: 
				return MachineBulbReprocessorBlockEntity.this.worldPosition.getY();
			case 9: 
				return MachineBulbReprocessorBlockEntity.this.worldPosition.getZ();
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				MachineBulbReprocessorBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				MachineBulbReprocessorBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				MachineBulbReprocessorBlockEntity.this.biomassCap.setCurrentStorage(value);
			    break; 
			case 3: 
				MachineBulbReprocessorBlockEntity.this.biomassCap.setMaxStorage(value);
				break;
			case 4: 
				MachineBulbReprocessorBlockEntity.this.ticksPassed = value;
				break; 
			case 5: 
				MachineBulbReprocessorBlockEntity.this.selectedId = value;
				break; 
			case 6: 
				MachineBulbReprocessorBlockEntity.this.actualTier = value;
				break;
			case 7:
				MachineBulbReprocessorBlockEntity.this.worldPosition = new BlockPos(value, MachineBulbReprocessorBlockEntity.this.worldPosition.getY(), MachineBulbReprocessorBlockEntity.this.worldPosition.getZ());
				break;
			case 8:
				MachineBulbReprocessorBlockEntity.this.worldPosition = new BlockPos(MachineBulbReprocessorBlockEntity.this.worldPosition.getX(), value, MachineBulbReprocessorBlockEntity.this.worldPosition.getZ());
				break;
			case 9:
				MachineBulbReprocessorBlockEntity.this.worldPosition = new BlockPos(MachineBulbReprocessorBlockEntity.this.worldPosition.getX(), MachineBulbReprocessorBlockEntity.this.worldPosition.getY(), value);
				break;
			}
		}
		public int getCount()
		{
			return 10;
		}
	};

	public MachineBulbReprocessorBlockEntity()
	{
		super(ModTileEntities.MACHINEBULBREPROCESSOR_TE, 5000, 8, 5000, PlantTechConstants.MACHINETIER_MACHINEBULBREPROCESSOR);
	}

	@Override
	protected boolean canProceed(ItemStack input, ItemStack output)
	{
		if (selectedId < 1 || selectedId > ModItems.MACHINE_BULBS.size())
			return false;
		MachineBulbItem bulb = ModItems.MACHINE_BULBS.get(selectedId - 1).get();
		if (bulb.getTier() > actualTier)
			return false;
		return Items.SEEDS.contains(input.getItem()) || input.getItem() instanceof CropSeedItem;
	}

	@Override
	protected EnergyConsumptionType getEnergyConsumptionType()
	{
		return EnergyConsumptionType.PER_PROCESS;
	}

	@Override
	protected FluidConsumptionType getFluidConsumptionType()
	{
		return FluidConsumptionType.PER_PROCESS;
	}

	@Override
	public int fluidPerAction()
	{
		if (selectedId < 1)
			return 0;
		return ModItems.MACHINE_BULBS.get(selectedId - 1).get().getNeededBiomass();
	}

	@Override
	protected ItemStack getResult(ItemStack input, ItemStack output)
	{
		if (selectedId < 1)
			return ItemStack.EMPTY;
		return new ItemStack(ModItems.MACHINE_BULBS.get(selectedId - 1).get());
	}

	@Override
	public void onContainerUpdated(int slotIndex)
	{
		checkTier();
	}

	public void checkTier()
	{
		ItemStack stack = itemhandler.getStackInSlot(getKnowledgeChipSlot()); 
		if(!stack.isEmpty())
		{
			Item item = stack.getItem(); 
			if (item instanceof KnowledgeChip)
			{
				actualTier = ((KnowledgeChip) item).getTier();
				return;
			}
		}
		actualTier = 0; 
	}
	
	@Override
	public ContainerData getContainerData()
	{
		return field_array;
	}

	@Override
	public int energyPerAction()
	{
		return 1000;
	}

	@Override
	public int ticksPerItem()
	{
		return 300;
	}

	@Override
	public String getNameString()
	{
		return "machinebulbreprocessor";
	}

	@Override
	public int getFluidInSlot()
	{
		return 2;
	}

	@Override
	public int getFluidOutSlot()
	{
		return 3;
	}

	@Override
	public int getEnergyInSlot()
	{
		return 4;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 5;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 6; 
	}
	
	@Override
	public Container createMenu(int id, Inventory inv, Player player)
	{
		return new MachineBulbReprocessorContainer(id, inv, this);
	}

	public void setSelectedId(int buttonId)
	{
		selectedId = buttonId; 
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 10;
	}

}
