package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryFluidBlockEntity;
import net.kaneka.planttech2.inventory.InfuserMenu;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.Optional;

import static net.kaneka.planttech2.utilities.PlantTechConstants.MACHINETIER_INFUSER;

public class InfuserBlockEntity extends ConvertEnergyInventoryFluidBlockEntity
{
	private int fluidTotal = 0;
	private Item output = null;
	
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
				return InfuserBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return InfuserBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return InfuserBlockEntity.this.biomassCap.getCurrentStorage();
			case 3:
				return InfuserBlockEntity.this.biomassCap.getMaxStorage();
			case 4: 
				return InfuserBlockEntity.this.ticksPassed;
			case 5:
				return InfuserBlockEntity.this.fluidTotal;
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				InfuserBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				InfuserBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				InfuserBlockEntity.this.biomassCap.setCurrentStorage(value);
			    break; 
			case 3: 
				InfuserBlockEntity.this.biomassCap.setMaxStorage(value);
				break;
			case 4: 
				InfuserBlockEntity.this.ticksPassed = value;
				break;
			case 5:
				InfuserBlockEntity.this.fluidTotal = value;
				break;
			}
		}
		public int getCount()
		{
			return 6;
		}
	};

	public InfuserBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.INFUSER_TE, pos, state, 1000, 8, 5000, MACHINETIER_INFUSER);
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
		return getOutputRecipe() == null ? ItemStack.EMPTY : getOutputRecipe().getResultItem();
	}

	@Override
	protected boolean shouldResetProgressIfNotProcessing()
	{
		return false;
	}

	@Override
	public ContainerData getContainerData()
	{
		return field_array;
	}

	@Nullable
	private InfuserRecipe getOutputRecipe()
	{
		
		if (level == null)
			return null;
		RecipeWrapper wrapper = new RecipeWrapper(itemhandler);
		Optional<InfuserRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.INFUSING, wrapper, level);
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
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
	{
		return new InfuserMenu(id, inv, this);
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
