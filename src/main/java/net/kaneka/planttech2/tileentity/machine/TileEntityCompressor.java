package net.kaneka.planttech2.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import net.kaneka.planttech2.container.ContainerCompressor;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.TileEntityEnergyInventory;
import net.kaneka.planttech2.utilities.Constants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class TileEntityCompressor extends TileEntityEnergyInventory
{
    private int ticksPassed = 0;
    private int selectedId = -1;
    private HashMap<Integer, Pair<ItemStack, Integer>> recipeList = null;
    private ItemStack previousInput = null;

    public TileEntityCompressor()
    {
	super(ModTileEntities.COMPRESSOR_TE, 1000, 23);
    }

    @Override
    public void doUpdate()
    {

	if (this.energystorage.getEnergyStored() > energyPerTick() || true)
	{
	    ItemStack stack1 = itemhandler.getStackInSlot(0);
	    ItemStack stack2 = itemhandler.getStackInSlot(1);
	    if (!stack1.isEmpty() && selectedId >= 0)
	    {
		if (recipeList != null)
		{
		    if (!recipeList.isEmpty() && recipeList.size() > selectedId)
		    {
			Pair<ItemStack, Integer> recipe = recipeList.get(selectedId);
			int neededInput = recipe.getValue();
			ItemStack stackOutput = recipe.getKey().copy();
			if (neededInput <= stack1.getCount())
			{
			    if (ticksPassed < ticksPerItem())
			    {
				ticksPassed++;
				// energystorage.extractEnergy(energyPerTick(), false);
			    }
			    else
			    {
				if (stack2.isEmpty())
				{
				    itemhandler.setStackInSlot(1, stackOutput);
				    // energystorage.extractEnergy(energyPerTick(), false);
				    stack1.shrink(neededInput);
				    ticksPassed = 0;
				}
				else if (stack2.getItem() == stackOutput.getItem())
				{
				    if (stack2.getMaxStackSize() >= stack2.getCount() + stackOutput.getCount())
				    {
					stack2.grow(stackOutput.getCount());
					// energystorage.extractEnergy(energyPerTick(), false);
					stack1.shrink(neededInput);
					ticksPassed = 0;
				    }
				}
			    }
			}
		    }
		    else
		    {
			selectedId = -3;
		    }
		}
		else if (selectedId >= 0)
		{
		    initRecipeList();
		}
	    }
	}
    }

    public void setSelectedId(int selectedId)
    {
	this.selectedId = selectedId;
    }

    public void setRecipe()
    {

	if (previousInput == null)
	{
	    this.selectedId = -2;
	    initRecipeList();
	}
	else if (previousInput.getItem() != itemhandler.getStackInSlot(0).getItem())
	{
	    this.selectedId = -2;
	    initRecipeList();
	}

    }

    public void initRecipeList()
    {
	// reset old values
	for (int i = 0; i < 20; i++)
	{
	    itemhandler.setStackInSlot(i + 3, ItemStack.EMPTY);
	}

	// set new values
	HashMap<Integer, Pair<ItemStack, Integer>> temprecipeList = new HashMap<Integer, Pair<ItemStack, Integer>>();
	List<Integer> keys = new ArrayList<Integer>();
	recipeList = new HashMap<Integer, Pair<ItemStack, Integer>>();

	for (CompressorRecipe recipe : this.world.getRecipeManager().getRecipes(ModRecipeTypes.COMPRESSING))
	{
	    if (recipe.matches(this, world))
	    {
		temprecipeList.put(Item.getIdFromItem(recipe.getRecipeOutput().getItem()), Pair.of(recipe.getRecipeOutput(), recipe.getAmountInput()));
		keys.add(Item.getIdFromItem(recipe.getRecipeOutput().getItem()));
	    }
	}

	Collections.sort(keys);

	for (int i = 0; i < keys.size(); i++)
	{
	    recipeList.put(i, temprecipeList.get(keys.get(i)));
	    itemhandler.setStackInSlot(i + 3, temprecipeList.get(keys.get(i)).getLeft());
	}
	ticksPassed = 0;
    }

    public int energyPerTick()
    {
	return 4 + (getUpgradeTier(3, Constants.SPEEDUPGRADE_TYPE) * 4);
    }

    public int ticksPerItem()
    {
	return 200 - (getUpgradeTier(3, Constants.SPEEDUPGRADE_TYPE) * 35);
    }

    @Override
    public String getNameString()
    {
	return "compressor";
    }

    @Override
    public List<ItemStack> getInventoryContent()
    {
	List<ItemStack> stack = new ArrayList<ItemStack>();

	for (int i = 0; i < 3; i++)
	{
	    stack.add(itemhandler.getStackInSlot(i).copy());
	}
	return stack;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
	compound.putInt("tickspassed", ticksPassed);
	compound.putInt("selectedId", selectedId);
	super.write(compound);
	return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
	this.ticksPassed = compound.getInt("tickspassed");
	this.selectedId = compound.getInt("selectedId");
	super.read(compound);
    }

    @Override
    public int getField(int id)
    {
	switch (id)
	{
	case 0:
	case 1:
	    return super.getField(id);
	case 2:
	    return this.ticksPassed;
	case 3:
	    return this.selectedId;
	default:
	    return 0;
	}
    }

    @Override
    public void setField(int id, int value)
    {
	switch (id)
	{
	case 0:
	case 1:
	    super.setField(id, value);
	    break;
	case 2:
	    this.ticksPassed = value;
	    break;
	case 3:
	    this.selectedId = value;
	}
    }

    @Override
    public int getAmountFields()
    {
	return 4;
    }

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_)
	{
		return new ContainerCompressor(p_createMenu_2_, this);
	}

}
