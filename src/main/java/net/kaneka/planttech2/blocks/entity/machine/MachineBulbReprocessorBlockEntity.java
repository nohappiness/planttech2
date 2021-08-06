package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryFluidBlockEntity;
import net.kaneka.planttech2.inventory.MachineBulbReprocessorMenu;
import net.kaneka.planttech2.items.CropSeedItem;
import net.kaneka.planttech2.items.KnowledgeChip;
import net.kaneka.planttech2.items.MachineBulbItem;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags.Items;

public class MachineBulbReprocessorBlockEntity extends ConvertEnergyInventoryFluidBlockEntity
{
	private int selectedId = 0;
	private int actualTier = 0; 
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			return switch (index)
					{
						case 0 -> MachineBulbReprocessorBlockEntity.this.energystorage.getEnergyStored();
						case 1 -> MachineBulbReprocessorBlockEntity.this.energystorage.getMaxEnergyStored();
						case 2 -> MachineBulbReprocessorBlockEntity.this.biomassCap.getCurrentStorage();
						case 3 -> MachineBulbReprocessorBlockEntity.this.biomassCap.getMaxStorage();
						case 4 -> MachineBulbReprocessorBlockEntity.this.ticksPassed;
						case 5 -> MachineBulbReprocessorBlockEntity.this.selectedId;
						case 6 -> MachineBulbReprocessorBlockEntity.this.actualTier;
						default -> 0;
					};
		}

		public void set(int index, int value)
		{
			switch (index)
			{
				case 0 -> MachineBulbReprocessorBlockEntity.this.energystorage.setEnergyStored(value);
				case 1 -> MachineBulbReprocessorBlockEntity.this.energystorage.setEnergyMaxStored(value);
				case 2 -> MachineBulbReprocessorBlockEntity.this.biomassCap.setCurrentStorage(value);
				case 3 -> MachineBulbReprocessorBlockEntity.this.biomassCap.setMaxStorage(value);
				case 4 -> MachineBulbReprocessorBlockEntity.this.ticksPassed = value;
				case 5 -> MachineBulbReprocessorBlockEntity.this.selectedId = value;
				case 6 -> MachineBulbReprocessorBlockEntity.this.actualTier = value;
			}
		}
		public int getCount()
		{
			return 10;
		}
	};

	public MachineBulbReprocessorBlockEntity()
	{
		this(BlockPos.ZERO, ModBlocks.MACHINEBULBREPROCESSOR.defaultBlockState());
	}

	public MachineBulbReprocessorBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.MACHINEBULBREPROCESSOR_TE, pos, state, 5000, 8, 5000, PlantTechConstants.MACHINETIER_MACHINEBULBREPROCESSOR);
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
			if (item instanceof KnowledgeChip kc)
			{
				actualTier = kc.getTier();
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
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new MachineBulbReprocessorMenu(id, inv, this);
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
