package net.kaneka.planttech2.blocks.entity.machine.baseclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public abstract class ConvertEnergyInventoryFluidBlockEntity extends EnergyInventoryFluidBlockEntity
{
    public ConvertEnergyInventoryFluidBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int energyStorage, int invSize, int maxBiomassStorage, int tier)
    {
        super(type, pos, state, energyStorage, invSize, maxBiomassStorage, tier);
    }

    @Override
    public void doUpdate()
    {
        super.doUpdate();
        if (level == null || level.isClientSide) return;
        if (energystorage.getEnergyStored() >= energyPerAction() && biomassCap.getCurrentStorage() >= fluidPerAction())
        {
            ItemStack input = getInput();
            ItemStack output = getOutput();
            if (!input.isEmpty() && canProceed(input, output))
            {
                if (ticksPassed < ticksPerItem())
                {
                    increaseProgress();
                    if (getEnergyConsumptionType() == EnergyInventoryBlockEntity.EnergyConsumptionType.PER_TICK)
                        energystorage.extractEnergy(energyPerAction(), false);
                    if (getFluidConsumptionType() == FluidConsumptionType.PER_TICK)
                        biomassCap.extractBiomass(fluidPerAction());
                }
                else if (onProcessFinished(input, output))
                {
                    if (getEnergyConsumptionType() != EnergyConsumptionType.NONE)
                        energystorage.extractEnergy(energyPerAction(), false);
                    if (getFluidConsumptionType() != FluidConsumptionType.NONE)
                        biomassCap.extractBiomass(remainingFluid());
                    resetProgress(true);
                    addKnowledge();
                }
            }
            else resetProgress(false);
        }
    }

    protected boolean hasEnoughFluid()
    {
        return biomassCap.getCurrentStorage() >= fluidPerAction();
    }

    protected abstract boolean canProceed(ItemStack input, ItemStack output);

    protected abstract ItemStack getResult(ItemStack input, ItemStack output);
    
    protected int remainingFluid()
    {
    	return fluidPerAction(); 
    }

    protected boolean onProcessFinished(ItemStack input, ItemStack output)
    {
        ItemStack result = getResult(input, output);
        if (itemhandler.insertItem(getOutputSlotIndex(), result, false).isEmpty())
        {
            input.shrink(1);
            return true;
        }
        return false;
    }

    @Override
    protected void resetProgress(boolean forced)
    {
        if (shouldResetProgressIfNotProcessing() || forced)
            super.resetProgress(forced);
    }

    protected boolean shouldResetProgressIfNotProcessing()
    {
        return true;
    }

    public int getInputSlotIndex()
    {
        return 0;
    }

    public int getOutputSlotIndex()
    {
        return 1;
    }

    public ItemStack getInput()
    {
        return itemhandler.getStackInSlot(getInputSlotIndex());
    }
    public ItemStack getOutput()
    {
        return itemhandler.getStackInSlot(getOutputSlotIndex());
    }

    public int fluidPerAction()
    {
        return 5 + getUpgradeTier(SPEED_UPGRADE) * 3;
    }

    protected FluidConsumptionType getFluidConsumptionType()
    {
        return FluidConsumptionType.PER_TICK;
    }
}
