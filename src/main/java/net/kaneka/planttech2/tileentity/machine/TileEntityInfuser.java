package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.ContainerInfuser;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventoryFluid;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class TileEntityInfuser extends TileEntityEnergyInventoryFluid
{
	private int fluidInfused = 0;
	private int fluidTotal = 0; 
	private Item output = null;

	public TileEntityInfuser()
	{
		super(ModTileEntities.INFUSER_TE, 1000, 5, 5000);
	}

	@Override
	public void doUpdate()
	{
		if (energystorage.getEnergyStored() > energyPerTick() || true)
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
						if (fluidtank.getBiomass() >= fluidpertick)
						{
							if (fluidInfused + fluidpertick < fluidTotal)
							{
								fluidInfused += fluidpertick;
								fluidtank.extract(fluidpertick);
								// energystorage.extractEnergy(energyPerTick(), false);
							} else
							{
								if (stack2.isEmpty())
								{
									itemhandler.setStackInSlot(1, recipe.getRecipeOutput());
									// energystorage.extractEnergy(energyPerTick(), false);
									stack1.shrink(1);
									fluidInfused = 0;
									fluidtank.extract(fluidTotal - fluidInfused);
								} else if (stack2.getItem() == recipe.getOutput() && stack2.getCount() < stack2.getMaxStackSize())
								{
									stack2.grow(1);
									// energystorage.extractEnergy(energyPerTick(), false);
									stack1.shrink(1);
									fluidInfused = 0;
									fluidtank.extract(fluidTotal - fluidInfused);
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

		doFluidLoop();
	}

	private InfuserRecipe getOutputRecipe()
	{
		RecipeWrapper wrapper = new RecipeWrapper(itemhandler, 1, 1);
		InfuserRecipe recipe = world.getRecipeManager().getRecipe(wrapper, world, ModRecipeTypes.INFUSING);
		return recipe;
	}

	public int energyPerTick()
	{
		return 4 + (getUpgradeTier(2, Constants.SPEEDUPGRADE_TYPE) * 4);
	}

	public int fluidPerTick()
	{
		return 5 + (getUpgradeTier(2, Constants.SPEEDUPGRADE_TYPE) * 3);
	}

	@Override
	public String getNameString()
	{
		return "infuser";
	}

	@Override
	public NBTTagCompound write(NBTTagCompound compound)
	{
		compound.setInt("tickspassed", fluidInfused);
		super.write(compound);
		return compound;
	}

	@Override
	public void read(NBTTagCompound compound)
	{
		this.fluidInfused = compound.getInt("tickspassed");
		super.read(compound);
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerInfuser(playerInventory, this);
	}

	@Override
	public int getField(int id)
	{
		if (id < 4)
		{
			return super.getField(id);
		} else
		{
			switch (id)
			{
			case 4:
				return fluidInfused;
			case 5: 
				return fluidTotal;
			default:
				return 0;
			}
		}
	}

	@Override
	public void setField(int id, int value)
	{
		if (id < 4)
		{
			super.setField(id, value);
		} else
		{
			switch (id)
			{
			case 4:
				fluidInfused = value;
				break;
			case 5: 
				fluidTotal = value; 
			}
		}
	}

	@Override
	public int getAmountFields()
	{
		return 6;
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

}
