package net.kaneka.planttech2.tileentity.machine;

import java.util.Optional;

import javax.annotation.Nullable;

import net.kaneka.planttech2.container.InfuserContainer;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryFluidTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class InfuserTileEntity extends EnergyInventoryFluidTileEntity
{
	private int fluidInfused = 0;
	private int fluidTotal = 0; 
	private Item output = null;
	
	private RangedWrapper inputs; 
	private RangedWrapper outputs; 
	private LazyOptional<IItemHandler> inputs_provider;
	private LazyOptional<IItemHandler> outputs_provider;
	
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
				return InfuserTileEntity.this.BIOMASS_CAP.getCurrentStorage();
			case 3:
				return InfuserTileEntity.this.BIOMASS_CAP.getMaxStorage();
			case 4: 
				return InfuserTileEntity.this.fluidInfused; 
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
				InfuserTileEntity.this.BIOMASS_CAP.setCurrentStorage(value);
			    break; 
			case 3: 
				InfuserTileEntity.this.BIOMASS_CAP.setMaxStorage(value);
				break;
			case 4: 
				InfuserTileEntity.this.fluidInfused = value; 
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
		super(ModTileEntities.INFUSER_TE, 1000, 8, 5000, PlantTechConstants.MACHINETIER_INFUSER);
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
	public void doUpdate()
	{
		if (energystorage.getEnergyStored() > energyPerTick())
		{
			ItemStack stack1 = itemhandler.getStackInSlot(0);
			ItemStack stack2 = itemhandler.getStackInSlot(1);

			if (!stack1.isEmpty())
			{
				InfuserRecipe recipe = getOutputRecipe();
				if (recipe != null)
				{
					if (recipe.getOutput() == output || output == null)
					{
						if (output == null)
						{
							output = recipe.getOutput();
							fluidTotal = recipe.getBiomass(); 
						}
						int fluidpertick = fluidPerTick();
						if (BIOMASS_CAP.getCurrentStorage() >= fluidpertick)
						{
							if (fluidInfused + fluidpertick < fluidTotal)
							{
								fluidInfused += fluidpertick;
								BIOMASS_CAP.extractBiomass(fluidpertick);
								energystorage.extractEnergy(energyPerTick(), false);
							} else
							{
								if (stack2.isEmpty())
								{
									itemhandler.setStackInSlot(1, recipe.getRecipeOutput());
									energystorage.extractEnergy(energyPerTick(), false);
									stack1.shrink(1);
									fluidInfused = 0;
									BIOMASS_CAP.extractBiomass(fluidTotal - fluidInfused);
									addKnowledge();
								} else if (stack2.getItem() == recipe.getOutput() && stack2.getCount() < stack2.getMaxStackSize())
								{
									stack2.grow(1);
									energystorage.extractEnergy(energyPerTick(), false);
									stack1.shrink(1);
									fluidInfused = 0;
									BIOMASS_CAP.extractBiomass(fluidTotal - fluidInfused);
									addKnowledge();
								}
							}
						}
					} else
					{
						fluidInfused = 0;
						fluidTotal = recipe.getBiomass(); 
						output = recipe.getOutput();
					}
				}
				
			}
		}
		doEnergyLoop();
		doFluidLoop();
	}
	
	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}
	
	private InfuserRecipe getOutputRecipe()
	{
		RecipeWrapper wrapper = new RecipeWrapper(itemhandler);
		Optional<InfuserRecipe> recipe = world.getRecipeManager().getRecipe(ModRecipeTypes.INFUSING, wrapper, world);
		return recipe.orElse(null);
	}
	

	public int energyPerTick()
	{
		return 4 + (getUpgradeTier(2, PlantTechConstants.SPEEDUPGRADE_TYPE) * 4);
	}

	public int fluidPerTick()
	{
		return 5 + (getUpgradeTier(2, PlantTechConstants.SPEEDUPGRADE_TYPE) * 3);
	}

	@Override
	public String getNameString()
	{
		return "infuser";
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("tickspassed", fluidInfused);
		super.write(compound);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound)
	{
		this.fluidInfused = compound.getInt("tickspassed");
		super.read(compound);
	}

	@Override
	protected int getFluidInSlot()
	{
		return 3;
	}

	@Override
	protected int getFluidOutSlot()
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
}
