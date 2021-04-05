package net.kaneka.planttech2.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import net.kaneka.planttech2.tileentity.machine.baseclasses.ConvertEnergyInventoryTileEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import org.apache.commons.lang3.tuple.Pair;

import net.kaneka.planttech2.container.CompressorContainer;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.BlockState;
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

public class CompressorTileEntity extends ConvertEnergyInventoryTileEntity
{
	private int selectedId = -1;
	private HashMap<Integer, Pair<ItemStack, Integer>> recipeList = new HashMap<Integer, Pair<ItemStack, Integer>>();
	private Item previousInput = null;
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
				CompressorTileEntity.this.setSelectedId(value);
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

//	@Override
//	public void tick()
//	{
//		super.tick();
//		if (world != null && world.getGameTime() % 20 == 0)
//		{
//			System.out.println(world.isRemote);
//			System.out.println(selectedId);
//		}
//	}

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
		boolean recipeDone = false;
		boolean inputDone = false;
		
		if (recipeList == null || previousInput != input.getItem())
		{
			initRecipeList();
		}
		
		if (getSelectedId() >= 0)
		{
			if (!recipeList.isEmpty() && recipeList.size() > getSelectedId())
			{
				recipeDone = true;
				inputDone = recipeList.get(getSelectedId()).getValue() <= input.getCount();
				if (inputDone)
					previousInput = input.getItem();
			}
			else setSelectedId(-1);
		}
		return getSelectedId() >= 0 && recipeDone && inputDone;
	}

	private int getSelectedId()
	{
		return selectedId - 1;
	}

	@Override
	protected boolean onProcessFinished(ItemStack input, ItemStack output)
	{
		ItemStack result = getResult(input, output);
		if (itemhandler.insertItem(getOutputSlotIndex(), result, false).isEmpty())
		{
			input.shrink(recipeList.get(getSelectedId()).getValue());
			return true;
		}
		return false;
	}

	@Override
	protected ItemStack getResult(ItemStack input, ItemStack output)
	{
		return recipeList.get(getSelectedId()).getKey().copy();
	}

	@Override
	public IIntArray getIntArray()
	{
		return field_array;
	}

	public void setSelectedId(int selectedId)
	{
//		if (world != null)
//		{
//			System.out.println(world.isRemote);
//			System.out.println("set" + selectedId);
//		}
		this.selectedId = selectedId + 1;
		notifyClient();
	}

	@Override
	public void onContainerUpdated(int slotIndex)
	{
		if (world != null && (previousInput == null || previousInput.getItem() != itemhandler.getStackInSlot(0).getItem()))
			initRecipeList();
	}

	@SuppressWarnings("resource")
	public void initRecipeList()
	{
		// reset old values
		if (world == null || world.isRemote)
			return;
		for (int i = 0; i < 20; i++)
			itemhandler.setStackInSlot(i + 3, ItemStack.EMPTY);
		selectedId = -1; 
		previousInput = null; 

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
        		 for (IRecipe<?> recipe : this.world.getRecipeManager().getRecipesForType(ModRecipeTypes.COMPRESSING))
        		 {
					 CompressorRecipe compRecipe = (CompressorRecipe) recipe;
					 
					 if (compRecipe.getInput().getItem() == particle)
					 {
						 temprecipeList.put(Item.getIdFromItem(compRecipe.getRecipeOutput().getItem()),
						 Pair.of(compRecipe.getRecipeOutput(), compRecipe.getAmountInput()));
						 keys.add(Item.getIdFromItem(compRecipe.getRecipeOutput().getItem()));
						 
						 previousInput = particle;
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
		resetProgress(false);
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
	public CompoundNBT getUpdateTag()
	{
		return write(new CompoundNBT());
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(pos, 7414, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
	{
		if (world == null)
			return;
		handleUpdateTag(world.getBlockState(pos), pkt.getNbtCompound());
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		if (world != null && !world.isRemote())
			compound.putInt("selectedId", getSelectedId());
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		super.read(state, compound);
		if (compound.contains("selectedId"))
			setSelectedId(compound.getInt("selectedId"));
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
		return 2;
	}

	@Override
	public boolean requireSyncOnOpen()
	{
		return true;
	}
}
