package net.kaneka.planttech2.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import net.kaneka.planttech2.container.CompressorContainer;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public class CompressorTileEntity extends EnergyInventoryTileEntity
{
	private int selectedId = -1;
	private HashMap<Integer, Pair<ItemStack, Integer>> recipeList = new HashMap<Integer, Pair<ItemStack, Integer>>();
	private ItemStack previousInput = null;
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
				return CompressorTileEntity.this.energystorage.getEnergyStored();
			case 1:
				return CompressorTileEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return CompressorTileEntity.this.ticksPassed;
			case 3:
				return CompressorTileEntity.this.selectedId;
			case 4: 
				return CompressorTileEntity.this.pos.getX(); 
			case 5: 
				return CompressorTileEntity.this.pos.getY(); 
			case 6: 
				return CompressorTileEntity.this.pos.getZ(); 
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				CompressorTileEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				CompressorTileEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				CompressorTileEntity.this.ticksPassed = value;
				break;
			case 3:
				CompressorTileEntity.this.selectedId = value;
				break; 
			case 4:
				CompressorTileEntity.this.pos = new BlockPos(value, CompressorTileEntity.this.pos.getY(), CompressorTileEntity.this.pos.getZ());
				break;
			case 5:
				CompressorTileEntity.this.pos = new BlockPos(CompressorTileEntity.this.pos.getX(), value, CompressorTileEntity.this.pos.getZ());
				break;
			case 6:
				CompressorTileEntity.this.pos = new BlockPos(CompressorTileEntity.this.pos.getX(), CompressorTileEntity.this.pos.getY(), value);
				break;
			}
		}
		public int size()
		{
			return 7;
		}
	};

	public CompressorTileEntity()
	{
		super(ModTileEntities.COMPRESSOR_TE, 1000, 26, PlantTechConstants.MACHINETIER_COMPRESSOR);
		inputs = new RangedWrapper(itemhandler, 0,1); 
		outputs = new RangedWrapper(itemhandler, 1,2); 
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
		if (this.energystorage.getEnergyStored() > energyPerTick())
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
							previousInput = stack1; 
							if (ticksPassed < ticksPerItem())
							{
								ticksPassed++;
								energystorage.extractEnergy(energyPerTick(), false);
							}
							else
							{
								if (stack2.isEmpty())
								{
									itemhandler.setStackInSlot(1, stackOutput);
									energystorage.extractEnergy(energyPerTick(), false);
									stack1.shrink(neededInput);
									ticksPassed = 0;
								}
								else if (stack2.getItem() == stackOutput.getItem())
								{
									if (stack2.getMaxStackSize() >= stack2.getCount() + stackOutput.getCount())
									{
										stack2.grow(stackOutput.getCount());
										energystorage.extractEnergy(energyPerTick(), false);
										stack1.shrink(neededInput);
										ticksPassed = 0;
										addKnowledge();
									}
								}
							}
						}
					}
					else selectedId = -1;
				}
				else initRecipeList();
			}
		}
	}
	
	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	public void setSelectedId(int selectedId)
	{ 
		this.selectedId = selectedId;
	}

	public void setRecipe()
	{
		if (previousInput == null || previousInput.getItem() != itemhandler.getStackInSlot(0).getItem())
		{
			this.selectedId = -2;
			initRecipeList();
		}
	}

	@SuppressWarnings("resource")
	public void initRecipeList()
	{
		// reset old values
		for (int i = 0; i < 20; i++)
			itemhandler.setStackInSlot(i + 3, ItemStack.EMPTY);

		// set new values
		HashMap<Integer, Pair<ItemStack, Integer>> temprecipeList = new HashMap<Integer, Pair<ItemStack, Integer>>();
		List<Integer> keys = new ArrayList<Integer>();
		recipeList = new HashMap<Integer, Pair<ItemStack, Integer>>();
		ItemStack particleStack = itemhandler.getStackInSlot(0);
		if(!particleStack.isEmpty())
		{
			Item particle = particleStack.getItem(); 
			if(world != null)
			{
        		 for (IRecipe<?> recipe :this.world.getRecipeManager().getRecipesForType(ModRecipeTypes.COMPRESSING))
        		 {
					 CompressorRecipe compRecipe = (CompressorRecipe) recipe;
					 if (compRecipe.getInput().getItem() == particle)
					 {
						 temprecipeList.put(Item.getIdFromItem(compRecipe.getRecipeOutput().getItem()),
						 Pair.of(compRecipe.getRecipeOutput(), compRecipe.getAmountInput()));
						 keys.add(Item.getIdFromItem(compRecipe.getRecipeOutput().getItem()));
					 }
        		 }
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

	@Override
	public String getNameString()
	{
		return "compressor";
	}

	@Override
	public List<ItemStack> getInventoryContent()
	{
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for (int i = 0; i < 3; i++)
			stacks.add(itemhandler.getStackInSlot(i).copy());
		return stacks;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		compound.putInt("selectedId", selectedId);
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		super.read(state, compound);
		this.selectedId = compound.getInt("selectedId");
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new CompressorContainer(id, inv, this);
	}

	@Override
	public int getEnergyInSlot()
	{
		return 23;
	}

	@Override
	public int getEnergyOutSlot()
	{
		return 24;
	}

	@Override
	public int getKnowledgeChipSlot()
	{
		return 25;
	}

	@Override
	public int getKnowledgePerAction()
	{
		return 2;
	}

	@Override
	public int getUpgradeSlot()
	{
		return 3;
	}
}
