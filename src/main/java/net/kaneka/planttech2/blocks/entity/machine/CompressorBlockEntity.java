package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.ConvertEnergyInventoryBlockEntity;
import net.kaneka.planttech2.inventory.CompressorContainer;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import org.antlr.runtime.misc.ContainerData;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CompressorBlockEntity extends ConvertEnergyInventoryBlockEntity
{
	private int selectedId = -1;
	private HashMap<Integer, Pair<ItemStack, Integer>> recipeList = new HashMap<Integer, Pair<ItemStack, Integer>>();
	private Item previousInput = null;
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
				return CompressorBlockEntity.this.energystorage.getEnergyStored();
			case 1:
				return CompressorBlockEntity.this.energystorage.getMaxEnergyStored();
			case 2:
				return CompressorBlockEntity.this.ticksPassed;
			case 3:
				return CompressorBlockEntity.this.selectedId;
			case 4: 
				return CompressorBlockEntity.this.worldPosition.getX();
			case 5: 
				return CompressorBlockEntity.this.worldPosition.getY();
			case 6: 
				return CompressorBlockEntity.this.worldPosition.getZ();
			default:
				return 0;
			}
		}

		public void set(int index, int value)
		{
			switch (index)
			{
			case 0:
				CompressorBlockEntity.this.energystorage.setEnergyStored(value);
				break;
			case 1:
				CompressorBlockEntity.this.energystorage.setEnergyMaxStored(value);
				break;
			case 2:
				CompressorBlockEntity.this.ticksPassed = value;
				break;
			case 3:
				CompressorBlockEntity.this.setSelectedId(value);
				break; 
			case 4:
				CompressorBlockEntity.this.worldPosition = new BlockPos(value, CompressorBlockEntity.this.worldPosition.getY(), CompressorBlockEntity.this.worldPosition.getZ());
				break;
			case 5:
				CompressorBlockEntity.this.worldPosition = new BlockPos(CompressorBlockEntity.this.worldPosition.getX(), value, CompressorBlockEntity.this.worldPosition.getZ());
				break;
			case 6:
				CompressorBlockEntity.this.worldPosition = new BlockPos(CompressorBlockEntity.this.worldPosition.getX(), CompressorBlockEntity.this.worldPosition.getY(), value);
				break;
			}
		}
		public int getCount()
		{
			return 7;
		}
	};

	public CompressorBlockEntity()
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
//		if (Level != null && world.getGameTime() % 20 == 0)
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
	public ContainerData getContainerData()
	{
		return field_array;
	}

	public void setSelectedId(int selectedId)
	{
//		if (Level != null)
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
		if (level != null && (previousInput == null || previousInput.getItem() != itemhandler.getStackInSlot(0).getItem()))
			initRecipeList();
	}

	@SuppressWarnings("resource")
	public void initRecipeList()
	{
		// reset old values
		if (level == null || level.isClientSide)
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
			if(level != null)
			{
        		 for (Recipe<?> recipe : this.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.COMPRESSING))
        		 {
					 CompressorRecipe compRecipe = (CompressorRecipe) recipe;
					 
					 if (compRecipe.getInput().getItem() == particle)
					 {
						 temprecipeList.put(Item.getId(compRecipe.getResultItem().getItem()),
						 Pair.of(compRecipe.getResultItem(), compRecipe.getAmountInput()));
						 keys.add(Item.getId(compRecipe.getResultItem().getItem()));
						 
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
	public CompoundTag getUpdateTag()
	{
		return save(new CompoundTag());
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(worldPosition, 7414, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
	{
		if (level == null)
			return;
		handleUpdateTag(level.getBlockState(worldPosition), pkt.getTag());
	}

	@Override
	public CompoundTag save(CompoundTag compound)
	{
		if (level != null && !level.isClientSide())
			compound.putInt("selectedId", getSelectedId());
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundTag compound)
	{
		super.load(state, compound);
		if (compound.contains("selectedId"))
			setSelectedId(compound.getInt("selectedId"));
	}

	@Override
	public Container createMenu(int id, Inventory inv, Player player)
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
