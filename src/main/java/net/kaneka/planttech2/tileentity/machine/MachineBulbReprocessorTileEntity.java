package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.MachineBulbReprocessorContainer;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.KnowledgeChip;
import net.kaneka.planttech2.items.MachineBulbItem;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.ConvertEnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.Tags.Items;

public class MachineBulbReprocessorTileEntity extends ConvertEnergyInventoryFluidTileEntity
{
	private int selectedId = 0;
	private int actualTier = 0; 
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return MachineBulbReprocessorTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return MachineBulbReprocessorTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
			    return MachineBulbReprocessorTileEntity.this.biomassCap.getCurrentStorage();
			case 3:
			    return MachineBulbReprocessorTileEntity.this.biomassCap.getMaxStorage();
			case 4: 
				return MachineBulbReprocessorTileEntity.this.ticksPassed; 
			case 5: 
				return MachineBulbReprocessorTileEntity.this.selectedId; 
			case 6: 
				return MachineBulbReprocessorTileEntity.this.actualTier; 
			case 7: 
				return MachineBulbReprocessorTileEntity.this.pos.getX(); 
			case 8: 
				return MachineBulbReprocessorTileEntity.this.pos.getY(); 
			case 9: 
				return MachineBulbReprocessorTileEntity.this.pos.getZ(); 
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				MachineBulbReprocessorTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				MachineBulbReprocessorTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				MachineBulbReprocessorTileEntity.this.biomassCap.setCurrentStorage(value);
			    break; 
			case 3: 
				MachineBulbReprocessorTileEntity.this.biomassCap.setMaxStorage(value);
				break;
			case 4: 
				MachineBulbReprocessorTileEntity.this.ticksPassed = value; 
				break; 
			case 5: 
				MachineBulbReprocessorTileEntity.this.selectedId = value; 
				break; 
			case 6: 
				MachineBulbReprocessorTileEntity.this.actualTier = value; 
				break;
			case 7:
				MachineBulbReprocessorTileEntity.this.pos = new BlockPos(value, MachineBulbReprocessorTileEntity.this.pos.getY(), MachineBulbReprocessorTileEntity.this.pos.getZ());
				break;
			case 8:
				MachineBulbReprocessorTileEntity.this.pos = new BlockPos(MachineBulbReprocessorTileEntity.this.pos.getX(), value, MachineBulbReprocessorTileEntity.this.pos.getZ());
				break;
			case 9:
				MachineBulbReprocessorTileEntity.this.pos = new BlockPos(MachineBulbReprocessorTileEntity.this.pos.getX(), MachineBulbReprocessorTileEntity.this.pos.getY(), value);
				break;
			}
		}
		public int size()
		{
			return 10;
		}
	};

	public MachineBulbReprocessorTileEntity()
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
	public void onContainerUpdated()
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
	public IIntArray getIntArray()
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
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
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
