package net.kaneka.planttech2.blocks.entity.machine.baseclasses;

import net.kaneka.planttech2.energy.IItemChargeable;
import net.kaneka.planttech2.items.KnowledgeChip;
import net.kaneka.planttech2.items.TierItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

abstract public class EnergyInventoryBlockEntity extends EnergyBlockEntity
{
	protected ItemStackHandler itemhandler;
	protected LazyOptional<IItemHandler> inventoryCap;
	protected int ticksPassed = 0;
	protected int tier;

	public EnergyInventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int energyStorage,  int invSize, int tier)
	{
		super(type,pos, state, energyStorage);
		itemhandler = new ItemStackHandler(invSize);
		inventoryCap = LazyOptional.of(() -> itemhandler);
		this.tier = tier;
	}

	/**
	 * Get Items that will be spawned in the Level when the block is destroyed
	 *
	 * @return list of items to spawn
	 */
	public List<ItemStack> getInventoryContent()
	{
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for (int i = 0; i < itemhandler.getSlots(); i++)
			stacks.add(itemhandler.getStackInSlot(i).copy());
		return stacks;
	}

	@Override
	public void doUpdate()
	{
		doEnergyLoop();
	}

	protected void resetProgress(boolean forced)
	{
		ticksPassed = 0;
	}

	public void doEnergyLoop()
	{
		ItemStack stack = itemhandler.getStackInSlot(getEnergyInSlot());
		ItemStack stack2 = itemhandler.getStackInSlot(getEnergyOutSlot());
		if (stack.getItem() instanceof IItemChargeable)
			if (energystorage.getEnergyStored() < energystorage.getMaxEnergyStored())
				energystorage.receiveEnergy(((IItemChargeable) stack.getItem()).extractEnergy(stack, 1, false));
		if (stack2.getItem() instanceof IItemChargeable)
			if (energystorage.getEnergyStored() >= 1)
				energystorage.extractEnergy(((IItemChargeable) stack2.getItem()).receiveEnergy(stack2, 1, false));
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return inventoryCap.cast();
		return super.getCapability(capability, facing);
	}

	@Override
	public CompoundTag save(CompoundTag compound)
	{
		compound.put("inventory", itemhandler.serializeNBT());
		compound.putInt("tickspassed", ticksPassed);
		return super.save(compound);
	}


	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		int slotamount = itemhandler.getSlots();// prevent crash
		itemhandler.deserializeNBT(compound.getCompound("inventory"));
		if (itemhandler.getSlots() != slotamount)
			itemhandler.setSize(slotamount);// prevent crash when invsize changed while update
		this.ticksPassed = compound.getInt("tickspassed");
	}

	public static void spawnAsEntity(Level level, BlockPos pos, ItemStack stack)
	{
		if (level != null && !level.isClientSide && !stack.isEmpty() && !level.restoringBlockSnapshots && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) // do not drop items while restoring
		// blockstates, prevents item dupe
		{
			double d0 = (double) (level.random.nextFloat() * 0.5F) + 0.25D;
			double d1 = (double) (level.random.nextFloat() * 0.5F) + 0.25D;
			double d2 = (double) (level.random.nextFloat() * 0.5F) + 0.25D;
			ItemEntity entityitem = new ItemEntity(level, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
			entityitem.setDefaultPickUpDelay();
			level.addFreshEntity(entityitem);
		}
	}

	/**
	 * Gets energy needed per action, normally used for:
	 * -> energy required per tick
	 * -> energy required per process
	 * @return amount of energy
	 */
	public int energyPerAction()
	{
		return 4 + getUpgradeTier(SPEED_UPGRADE) * 4;
	}

	public int ticksPerItem()
	{
		return 200 - getUpgradeTier(SPEED_UPGRADE) * 35;
	}

	protected EnergyConsumptionType getEnergyConsumptionType()
	{
		return EnergyConsumptionType.PER_TICK;
	}

	public int getCapacityUpgrade()
	{
		return getUpgradeTier(TierItem.ItemType.CAPACITY_UPGRADE);
	}

	protected void increaseProgress()
	{
		ticksPassed++;
	}

	public int getUpgradeTier(TierItem.ItemType itemType)
	{
		return getUpgradeTier(getUpgradeSlot(), itemType);
	}

	public int getUpgradeTier(int slot, TierItem.ItemType itemType)
	{
		if (slot == -1)
			return 0;
		ItemStack stack = itemhandler.getStackInSlot(slot);
		if (!stack.isEmpty() && stack.getItem() instanceof TierItem)
		{
			TierItem item = (TierItem) stack.getItem();
			if (item.getItemType() == itemType)
				return item.getTier();
		}
		return 0;
	}

	public void onContainerUpdated(int slotIndex)
	{

	}

	public abstract int getEnergyInSlot();

	public abstract int getEnergyOutSlot();

	protected ItemStack getKnowledgeChip()
	{
		return itemhandler.getStackInSlot(getKnowledgeChipSlot());
	}

	protected void addKnowledge()
	{
		ItemStack chip = getKnowledgeChip();
		ItemStack newChip = KnowledgeChip.addKnowledge(chip, getKnowledgePerAction(), tier);
		if (!chip.isEmpty() && !newChip.isEmpty())
			if (!chip.getItem().equals(newChip.getItem()))
				itemhandler.setStackInSlot(getKnowledgeChipSlot(), newChip);
	}

	public abstract int getKnowledgeChipSlot();

	public abstract int getKnowledgePerAction();

	public int getTotalCapacity()
	{
		return getTotalCapacity(getUpgradeSlot());
	}

	public int getTotalCapacity(int capacityChipSlot)
	{
		return (int) (1000 * Math.pow(10, getUpgradeTier(capacityChipSlot, TierItem.ItemType.CAPACITY_UPGRADE)));
	}

	public enum EnergyConsumptionType
	{
		PER_TICK,
		PER_PROCESS,
		NONE
	}

	public enum FluidConsumptionType
	{
		PER_TICK,
		PER_PROCESS,
		NONE
	}
}
