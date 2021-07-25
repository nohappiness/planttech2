package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryBlockEntity;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.inventory.DNARemoverMenu;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DNARemoverBlockEntity extends ConvertEnergyInventoryBlockEntity
{
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return DNARemoverBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return DNARemoverBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return DNARemoverBlockEntity.this.ticksPassed;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				DNARemoverBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				DNARemoverBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				DNARemoverBlockEntity.this.ticksPassed = value;
				break;
			}
		}
		public int getCount()
		{
			return 3;
		}
	};

	public DNARemoverBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.DNAREMOVER_TE, pos, state, 1000, 6, PlantTechConstants.MACHINETIER_DNA_REMOVER);
	}

	@Override
	protected boolean canProceed(ItemStack input, ItemStack output)
	{
		return !input.isEmpty() && input.hasTag() && !getAvailableTraits(input).isEmpty();
	}

	@Override
	protected ItemStack getResult(ItemStack input, ItemStack output)
	{
		List<String> traitsList = getAvailableTraits(input);
		Collections.shuffle(traitsList);
		CompoundTag nbt = input.getTag().copy();
		nbt.remove(traitsList.get(0));
		ItemStack stack = new ItemStack(ModItems.DNA_CONTAINER);
		stack.setTag(nbt);
		return stack;
	}

	@Override
	public ContainerData getContainerData()
	{
		return field_array;
	}

	private List<String> getAvailableTraits(ItemStack stack)
	{
		List<String> list = new ArrayList<String>();
		CompoundTag nbt = stack.getOrCreateTag();
		for (String key : HashMapCropTraits.getTraitsKeyList())
			if (nbt.contains(key))
				list.add(key);
		return list;
	}

	@Override
	public String getNameString()
	{
		return "dnaremover";
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new DNARemoverMenu(id, inv, this);
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
