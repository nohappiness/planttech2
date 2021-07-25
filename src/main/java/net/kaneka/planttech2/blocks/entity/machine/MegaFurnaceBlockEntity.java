package net.kaneka.planttech2.blocks.entity.machine;


import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.inventory.MegaFurnaceMenu;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.FurnaceRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.Optional;

public class MegaFurnaceBlockEntity extends EnergyInventoryBlockEntity
{
	public int[] ticksPassed = new int[6];
	boolean isSmelting;
	protected ItemStackHandler dummyitemhandler = new ItemStackHandler();
	
	private final RangedWrapper inputs;
	private final RangedWrapper outputs;
	private final LazyOptional<IItemHandler> inputs_provider;
	private final LazyOptional<IItemHandler> outputs_provider;
	
	protected final ContainerData field_array = new ContainerData()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return MegaFurnaceBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return MegaFurnaceBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return MegaFurnaceBlockEntity.this.ticksPassed[0];
			case 3:
				return MegaFurnaceBlockEntity.this.ticksPassed[1];
			case 4:
				return MegaFurnaceBlockEntity.this.ticksPassed[2];
			case 5:
				return MegaFurnaceBlockEntity.this.ticksPassed[3];
			case 6:
				return MegaFurnaceBlockEntity.this.ticksPassed[4];
			case 7:
				return MegaFurnaceBlockEntity.this.ticksPassed[5];
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				MegaFurnaceBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				MegaFurnaceBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				MegaFurnaceBlockEntity.this.ticksPassed[0] = value;
				break;
			case 3:
				MegaFurnaceBlockEntity.this.ticksPassed[1] = value;
				break;
			case 4:
				MegaFurnaceBlockEntity.this.ticksPassed[2] = value;
				break;
			case 5:
				MegaFurnaceBlockEntity.this.ticksPassed[3] = value;
				break;
			case 6:
				MegaFurnaceBlockEntity.this.ticksPassed[4] = value;
				break;
			case 7:
				MegaFurnaceBlockEntity.this.ticksPassed[5] = value;
				break;
			}
		}
		public int getCount()
		{
			return 8;
		}
	};

	public MegaFurnaceBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.MEGAFURNACE_TE, pos, state, 10000, 16, PlantTechConstants.MACHINETIER_MEGAFURNACE);
		inputs = new RangedWrapper(itemhandler, 0,6); 
		outputs = new RangedWrapper(itemhandler, 6, 12); 
		inputs_provider = LazyOptional.of(() -> inputs);
		outputs_provider = LazyOptional.of(() -> outputs);
	}
	
	@Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (facing == Direction.DOWN) return outputs_provider.cast();
            if (facing != null) return inputs_provider.cast();
            return inventoryCap.cast();
        }
        return super.getCapability(capability, facing);
    }

	@Override
	public void doUpdate()
	{
		super.doUpdate();
		isSmelting = false;
		for (int i = 0; i < 6; i++)
		{
			if (this.energystorage.getEnergyStored() > this.energyPerAction())
			{
				if (this.canSmelt(i))
				{
					isSmelting = true;
					ticksPassed[i]++;
					if (ticksPassed[i] >= this.ticksPerItem())
					{
						this.smeltItem(i);
						ticksPassed[i] = 0;
						addKnowledge();
					}
				}
				else if (ticksPassed[i] > 0)
					ticksPassed[i] = 0;
			}
			else
			{
				if (!this.canSmelt(i) && ticksPassed[i] > 0)
					ticksPassed[i] = 0;
				break;
			}
		}
		if (isSmelting)
			this.energystorage.extractEnergy(energyPerAction(), false);
	}
	
	@Override
	public ContainerData getContainerData()
	{
		return field_array;
	}

	private boolean canSmelt(int slot)
	{
		ItemStack itemstack = itemhandler.getStackInSlot(slot);
		if (itemstack.isEmpty())
			return false;
		else
		{
			
			ItemStack output = getOutput(slot);
			if (output.isEmpty())
				return false;
			else
			{
				ItemStack outputslot = itemhandler.getStackInSlot(slot + 6);
				if (outputslot.isEmpty())
					return true;
				else if (!output.sameItem(outputslot))
					return false;
				else if (outputslot.getCount() + output.getCount() <= 64 && outputslot.getCount() + output.getCount() <= outputslot.getMaxStackSize())
					return true;
				else
					return outputslot.getCount() + output.getCount() <= output.getMaxStackSize();

			}
		}
	}
	
	public ItemStack getOutput(int slot)
	{
		if (level == null)
			return ItemStack.EMPTY;
		dummyitemhandler.setStackInSlot(0, itemhandler.getStackInSlot(slot));
		RecipeWrapper wrapper = new RecipeWrapper(dummyitemhandler);
		Optional<SmeltingRecipe> recipeopt = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, wrapper, level);
		SmeltingRecipe recipe = recipeopt.orElse(null);
		return recipe == null ? ItemStack.EMPTY : recipe.getResultItem();
	}

	public void smeltItem(int slot)
	{
		if (this.canSmelt(slot))
		{
			ItemStack itemstack = this.itemhandler.getStackInSlot(slot);
			ItemStack itemstack1 = getOutput(slot); 
			ItemStack itemstack2 = this.itemhandler.getStackInSlot(slot + 6);
			if (itemstack2.isEmpty())
				this.itemhandler.setStackInSlot(slot + 6, itemstack1.copy());
			else if (itemstack2.getItem() == itemstack1.getItem())
				itemstack2.grow(itemstack1.getCount());
			itemstack.shrink(1);
		}
	}

	@Override
	public CompoundTag save(CompoundTag compound)
	{
		compound.putContainerData("cooktime", ticksPassed);
		return super.save(compound);
	}

	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		ticksPassed = compound.getContainerData("cooktime");
	}

	@Override
	public String getNameString()
	{
		return "megafurnace";
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new MegaFurnaceMenu(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 13;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 14;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 15;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 50;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 12;
	}
}
