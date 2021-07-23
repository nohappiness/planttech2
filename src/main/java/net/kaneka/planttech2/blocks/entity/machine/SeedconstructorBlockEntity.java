package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryFluidBlockEntity;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.inventory.SeedConstructorContainer;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.antlr.runtime.misc.ContainerData;

public class SeedconstructorBlockEntity extends ConvertEnergyInventoryFluidBlockEntity
{
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return SeedconstructorBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return SeedconstructorBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
			    return SeedconstructorBlockEntity.this.biomassCap.getCurrentStorage();
			case 3:
			    return SeedconstructorBlockEntity.this.biomassCap.getMaxStorage();
			case 4: 
				return SeedconstructorBlockEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				SeedconstructorBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				SeedconstructorBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				SeedconstructorBlockEntity.this.biomassCap.setCurrentStorage(value);
			    break; 
			case 3: 
				SeedconstructorBlockEntity.this.biomassCap.setMaxStorage(value);
				break;
			case 4: 
				SeedconstructorBlockEntity.this.ticksPassed = value;
				break; 
			}
		}
		public int getCount()
		{
			return 5;
		}
	};

	public SeedconstructorBlockEntity()
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
	public ContainerData getContainerData()
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
	public Container createMenu(int id, Inventory inv, Player player)
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
