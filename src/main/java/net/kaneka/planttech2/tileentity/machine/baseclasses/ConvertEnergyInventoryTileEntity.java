package net.kaneka.planttech2.tileentity.machine.baseclasses;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

public abstract class ConvertEnergyInventoryTileEntity extends EnergyInventoryTileEntity
{
    public ConvertEnergyInventoryTileEntity(TileEntityType<?> type, int energyStorage, int invSize, int tier)
    {
        super(type, energyStorage, invSize, tier);
    }

    @Override
    public void doUpdate()
    {
        super.doUpdate();
        if (world == null || world.isRemote) return;
        if (energystorage.getEnergyStored() >= energyPerAction())
        {
            ItemStack input = getInput();
            ItemStack output = getOutput();
            if (!input.isEmpty() && canProceed(input, output))
            {
                if (ticksPassed < ticksPerItem())
                {
                    increaseProgress();
                    if (getEnergyConsumptionType() == EnergyConsumptionType.PER_TICK)
                        energystorage.extractEnergy(energyPerAction(), false);
                }
                else if (onProcessFinished(input, output))
                {
                    if (getEnergyConsumptionType() != EnergyConsumptionType.NONE)
                        energystorage.extractEnergy(energyPerAction(), false);
                    resetProgress(true);
                    addKnowledge();
                }
            }
            else resetProgress(false);
        }
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

}
