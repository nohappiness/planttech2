package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.SeedConstructorContainer;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.ConvertEnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity.FluidConsumptionType;

public class SeedconstructorTileEntity extends ConvertEnergyInventoryFluidTileEntity
{
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return SeedconstructorTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return SeedconstructorTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
			    return SeedconstructorTileEntity.this.biomassCap.getCurrentStorage();
			case 3:
			    return SeedconstructorTileEntity.this.biomassCap.getMaxStorage();
			case 4: 
				return SeedconstructorTileEntity.this.ticksPassed; 
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				SeedconstructorTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				SeedconstructorTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				SeedconstructorTileEntity.this.biomassCap.setCurrentStorage(value);
			    break; 
			case 3: 
				SeedconstructorTileEntity.this.biomassCap.setMaxStorage(value);
				break;
			case 4: 
				SeedconstructorTileEntity.this.ticksPassed = value; 
				break; 
			}
		}
		public int size()
		{
			return 5;
		}
	};

	public SeedconstructorTileEntity()
	{
		super(ModTileEntities.SEEDCONSTRUCTOR_TE, 1000, 8, 5000, PlantTechConstants.MACHINETIER_SEEDCONSTRUCTOR);
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
		CompoundNBT nbt = input.getTag();
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
	public IIntArray getIntArray()
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
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new SeedConstructorContainer(id, inv, this);
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
