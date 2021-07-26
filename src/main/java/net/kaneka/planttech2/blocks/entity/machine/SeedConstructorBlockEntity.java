package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryFluidBlockEntity;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.inventory.SeedConstructorMenu;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class SeedConstructorBlockEntity extends ConvertEnergyInventoryFluidBlockEntity
{
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return SeedConstructorBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return SeedConstructorBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
			    return SeedConstructorBlockEntity.this.biomassCap.getCurrentStorage();
			case 3:
			    return SeedConstructorBlockEntity.this.biomassCap.getMaxStorage();
			case 4: 
				return SeedConstructorBlockEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				SeedConstructorBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				SeedConstructorBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				SeedConstructorBlockEntity.this.biomassCap.setCurrentStorage(value);
			    break; 
			case 3: 
				SeedConstructorBlockEntity.this.biomassCap.setMaxStorage(value);
				break;
			case 4: 
				SeedConstructorBlockEntity.this.ticksPassed = value;
				break; 
			}
		}
		public int getCount()
		{
			return 5;
		}
	};

	public SeedConstructorBlockEntity()
	{
		this(BlockPos.ZERO, ModBlocks.SEEDCONSTRUCTOR.defaultBlockState());
	}

	public SeedConstructorBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.SEEDCONSTRUCTOR_TE, pos, state, 1000, 8, 5000, PlantTechConstants.MACHINETIER_SEEDCONSTRUCTOR);
	}

	@Override
	protected boolean canProceed(ItemStack input, ItemStack output)
	{
		return (input.hasTag() && input.getItem() == ModItems.DNA_CONTAINER);
	}

	@Override
	protected boolean onProcessFinished(ItemStack input, ItemStack output)
	{
		return itemhandler.insertItem(getOutputSlotIndex(), getResult(input, output), false).isEmpty();
	}

	@Override
	protected FluidConsumptionType getFluidConsumptionType()
	{
		return FluidConsumptionType.PER_PROCESS;
	}

	@Override
	protected ItemStack getResult(ItemStack input, ItemStack output)
	{
		CompoundTag nbt = input.getTag();
		if (nbt == null)
			return ItemStack.EMPTY;
		HashMapCropTraits traits = new HashMapCropTraits();
		traits.setAnalysed(true);
		if (nbt.contains("type"))
			traits.setType(nbt.getString("type"));
		for (String key : HashMapCropTraits.getTraitsKeyList())
			if (nbt.contains(key))
				if (!key.equals("type"))
					traits.setTrait(EnumTraitsInt.getByName(key), nbt.getInt(key));
		ItemStack stack = new ItemStack(ModItems.SEEDS.get(traits.getType()));
		return traits.addToItemStack(stack);
	}

	@Override
	public ContainerData getIntArray()
	{
		return field_array;
	}

	@Override
	public int fluidPerAction()
	{
		return 500;
	}

	@Override
	public String getNameString()
	{
		return "seedconstructor";
	}

	@Override
	public int getFluidInSlot()
	{
		return 3;
	}

	@Override
	public int getFluidOutSlot()
	{
		return 4;
	}
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new SeedConstructorMenu(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 5;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 6;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 7;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 250;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 2;
	}
}
