package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

import static net.kaneka.planttech2.items.TierItem.ItemType.SPEED_UPGRADE;

public abstract class ConvertEnergyInventoryFluidTileEntity extends EnergyInventoryFluidTileEntity
{
    public ConvertEnergyInventoryFluidTileEntity(TileEntityType<?> type, int energyStorage, int invSize, int maxBiomassStorage, int tier)
    {
        super(type, energyStorage, invSize, maxBiomassStorage, tier);
    }

    @Override
    public void doUpdate()
    {
        super.doUpdate();
        if (world == null || world.isRemote) return;
        if (energystorage.getEnergyStored() >= energyPerAction() && biomassCap.getCurrentStorage() >= fluidPerAction())
        {
            ItemStack input = getInput();
            ItemStack output = getOutput();
            if (!input.isEmpty() && canProceed(input, output))
            {
                if (ticksPassed < ticksPerItem())
                {
                    increaseProgress();
                    if (getEnergyConsumptionType() == EnergyInventoryTileEntity.EnergyConsumptionType.PER_TICK)
                        energystorage.extractEnergy(energyPerAction(), false);
                    if (getFluidConsumptionType() == FluidConsumptionType.PER_TICK)
                        biomassCap.extractBiomass(fluidPerAction());
                }
                else if (onProcessFinished(input, output))
                {
                    if (getEnergyConsumptionType() != EnergyConsumptionType.NONE)
                        energystorage.extractEnergy(energyPerAction(), false);
                    if (getFluidConsumptionType() != FluidConsumptionType.NONE)
                        biomassCap.extractBiomass(fluidPerAction());
                    resetProgress();
                    addKnowledge();
                }
            }
            else resetProgress();
        }
    }

    protected boolean hasEnoughFluid()
    {
        return biomassCap.getCurrentStorage() >= fluidPerAction();
    }

    protected abstract boolean canProceed(ItemStack input, ItemStack output);

    protected abstract ItemStack getResult(ItemStack input, ItemStack output);

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
    protected void resetProgress()
    {
        if (shouldResetProgressIfNotProcessing())
            super.resetProgress();
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
