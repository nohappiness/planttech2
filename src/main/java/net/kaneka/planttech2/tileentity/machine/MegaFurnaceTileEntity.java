package net.kaneka.planttech2.tileentity.machine;


import java.util.Optional;

import javax.annotation.Nullable;

import net.kaneka.planttech2.container.MegaFurnaceContainer;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MegaFurnaceTileEntity extends EnergyInventoryTileEntity
{
	public int[] ticksPassed = new int[6];
	boolean isSmelting;
	protected ItemStackHandler dummyitemhandler = new ItemStackHandler();
	
	private final RangedWrapper inputs;
	private final RangedWrapper outputs;
	private final LazyOptional<IItemHandler> inputs_provider;
	private final LazyOptional<IItemHandler> outputs_provider;
	
	protected final IIntArray field_array = new IIntArray()
	{
		public int get(int index)
		{
			switch (index)
			{
			case 0:
				return MegaFurnaceTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return MegaFurnaceTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return MegaFurnaceTileEntity.this.ticksPassed[0];
			case 3:
				return MegaFurnaceTileEntity.this.ticksPassed[1];
			case 4:
				return MegaFurnaceTileEntity.this.ticksPassed[2];
			case 5:
				return MegaFurnaceTileEntity.this.ticksPassed[3];
			case 6:
				return MegaFurnaceTileEntity.this.ticksPassed[4];
			case 7:
				return MegaFurnaceTileEntity.this.ticksPassed[5];
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				MegaFurnaceTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				MegaFurnaceTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				MegaFurnaceTileEntity.this.ticksPassed[0] = value;
				break;
			case 3:
				MegaFurnaceTileEntity.this.ticksPassed[1] = value;
				break;
			case 4:
				MegaFurnaceTileEntity.this.ticksPassed[2] = value;
				break;
			case 5:
				MegaFurnaceTileEntity.this.ticksPassed[3] = value;
				break;
			case 6:
				MegaFurnaceTileEntity.this.ticksPassed[4] = value;
				break;
			case 7:
				MegaFurnaceTileEntity.this.ticksPassed[5] = value;
				break;
			}
		}
		public int size()
		{
			return 8;
		}
	};

	public MegaFurnaceTileEntity()
	{
		super(ModTileEntities.MEGAFURNACE_TE, 10000, 16, PlantTechConstants.MACHINETIER_MEGAFURNACE);
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
	public IIntArray getIntArray()
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
				else if (!output.isItemEqual(outputslot))
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
		if (world == null)
			return ItemStack.EMPTY;
		dummyitemhandler.setStackInSlot(0, itemhandler.getStackInSlot(slot));
		RecipeWrapper wrapper = new RecipeWrapper(dummyitemhandler);
		Optional<FurnaceRecipe> recipeopt = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, wrapper, world);
		FurnaceRecipe recipe = recipeopt.orElse(null); 
		return recipe == null ? ItemStack.EMPTY : recipe.getRecipeOutput();
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
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putIntArray("cooktime", ticksPassed);
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		super.read(state, compound);
		ticksPassed = compound.getIntArray("cooktime");
	}

	@Override
	public String getNameString()
	{
		return "megafurnace";
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new MegaFurnaceContainer(id, inv, this);
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
