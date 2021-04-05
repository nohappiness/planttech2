package net.kaneka.planttech2.tileentity.machine;

import java.util.Optional;

import javax.annotation.Nullable;

import net.kaneka.planttech2.container.InfuserContainer;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.ConvertEnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryFluidTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import static net.kaneka.planttech2.utilities.PlantTechConstants.*;

public class InfuserTileEntity extends ConvertEnergyInventoryFluidTileEntity
{
	private int fluidTotal = 0;
	private Item output = null;
	
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
				return InfuserTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return InfuserTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return InfuserTileEntity.this.biomassCap.getCurrentStorage();
			case 3:
				return InfuserTileEntity.this.biomassCap.getMaxStorage();
			case 4: 
				return InfuserTileEntity.this.ticksPassed;
			case 5:
				return InfuserTileEntity.this.fluidTotal;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				InfuserTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				InfuserTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				InfuserTileEntity.this.biomassCap.setCurrentStorage(value);
			    break; 
			case 3: 
				InfuserTileEntity.this.biomassCap.setMaxStorage(value);
				break;
			case 4: 
				InfuserTileEntity.this.ticksPassed = value;
				break;
			case 5:
				InfuserTileEntity.this.fluidTotal = value;
				break;
			}
		}
		public int size()
		{
			return 6;
		}
	};

	public InfuserTileEntity()
	{
		super(ModTileEntities.INFUSER_TE, 1000, 8, 5000, MACHINETIER_INFUSER);
		inputs = new RangedWrapper(itemhandler, 0,1); 
		outputs = new RangedWrapper(itemhandler, 1, 2); 
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
	protected boolean canProceed(ItemStack input, ItemStack output)
	{
		if (input.isEmpty())
			return false;
		InfuserRecipe recipe = getOutputRecipe();
		if (recipe == null)
			return false;
		if (recipe.getOutput() == this.output || this.output == null)
		{
			if (this.output == null)
			{
				this.output = recipe.getOutput();
				fluidTotal = recipe.getBiomass();
			} 
			
			return true;
		}
		else
		{
			fluidTotal = recipe.getBiomass();
			this.output = recipe.getOutput();
		}
		return false;
	}
	
	@Override
	public int ticksPerItem()
	{
		
		return fluidTotal > 0 ? fluidTotal: 100;
	}

	@Override
	protected void increaseProgress()
	{
		ticksPassed += fluidPerAction();
	}
	
	@Override
	protected int remainingFluid()
	{
		return fluidTotal - ticksPassed;
	}

	@Override
	protected ItemStack getResult(ItemStack input, ItemStack output)
	{
		return getOutputRecipe() == null ? ItemStack.EMPTY : getOutputRecipe().getRecipeOutput();
	}

	@Override
	protected boolean shouldResetProgressIfNotProcessing()
	{
		return false;
	}

	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	@Nullable
	private InfuserRecipe getOutputRecipe()
	{
		
		if (world == null)
			return null;
		RecipeWrapper wrapper = new RecipeWrapper(itemhandler);
		Optional<InfuserRecipe> recipe = world.getRecipeManager().getRecipe(ModRecipeTypes.INFUSING, wrapper, world);
		return recipe.orElse(null);
	}

	@Override
	public String getNameString()
	{
		return "infuser";
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
		return new InfuserContainer(id, inv, this);
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
		return 150;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 2;
	}
}
