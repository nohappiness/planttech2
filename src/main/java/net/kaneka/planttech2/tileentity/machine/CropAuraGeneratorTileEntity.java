package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.CropAuraGeneratorContainer;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.items.AuraChipItem;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IIntArray;

public class CropAuraGeneratorTileEntity extends EnergyInventoryTileEntity
{
    public int light;
    public int water;
    public EnumTemperature temperature;
    public Block soil;
    public int fertility;
    public int productivity;
    private int energyPerTick;

    protected final IIntArray field_array = new IIntArray()
    {
        @Override
        public int get(int index)
        {
            switch (index)
            {
                case 0:
                    return CropAuraGeneratorTileEntity.this.energystorage.getEnergyStored();
                case 1:
                    return CropAuraGeneratorTileEntity.this.energystorage.getMaxEnergyStored();
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value)
        {
            switch (index)
            {
                case 0:
                    CropAuraGeneratorTileEntity.this.energystorage.setEnergyStored(value);
                    break;
                case 1:
                    CropAuraGeneratorTileEntity.this.energystorage.setEnergyMaxStored(value);
                    break;
            }
        }

        @Override
        public int size()
        {
            return 2;
        }
    };

    public CropAuraGeneratorTileEntity()
    {
        super(ModTileEntities.CROP_AURA_GENERATOR_TE, 10000, 6, PlantTechConstants.MACHINETIER_CROP_AURA_GENERATOR);
    }

    @Override
    public void doUpdate()
    {
        super.doUpdate();
        energystorage.extractEnergy(energyPerTick);
    }

    public boolean canApplyEffect()
    {
        return energystorage.getEnergyStored() >= energyPerTick;
    }

    @Override
    public int getEnergyInSlot()
    {
        return 7;
    }

    @Override
    public int getEnergyOutSlot()
    {
        return 8;
    }

    @Override
    public int getKnowledgeChipSlot()
    {
        return 6;
    }

    @Override
    public int getKnowledgePerAction()
    {
        return 0;
    }

    @Override
    public IIntArray getIntArray()
    {
        return field_array;
    }

    @Override
    public void onContainerUpdated(int slotIndex)
    {
        if (slotIndex == 0)
            getLight();
        else if (slotIndex == 1)
            getWater();
        else if (slotIndex == 2)
            getTemperature();
        else if (slotIndex == 3)
            getSoil();
        else if (slotIndex == 4)
            getFertility();
        else if (slotIndex == 5)
            getProductivity();
        getEnergyPerTick();
    }

//----------------------------------------------------------------------------------------------------------------------
    /**
     * Updates and gets the value, access the fields
     * directly if update is not needed
     */
    public int getLight()
    {
        light = AuraChipItem.getLightValueDecrease(itemhandler.getStackInSlot(0));
        return light;
    }

    public int getWater()
    {
        water = AuraChipItem.getWaterRangeDecrease(itemhandler.getStackInSlot(1));
        return water;
    }

    public EnumTemperature getTemperature()
    {
        temperature = AuraChipItem.getTemperature(itemhandler.getStackInSlot(2));
        return temperature;
    }

    public Block getSoil()
    {
        soil = AuraChipItem.getSoil(itemhandler.getStackInSlot(3));
        return soil;
    }

    public int getFertility()
    {
        fertility = AuraChipItem.getFertilityValueIncrease(itemhandler.getStackInSlot(4));
        return fertility;
    }

    public int getProductivity()
    {
        productivity = AuraChipItem.getProductivityValueIncrease(itemhandler.getStackInSlot(5));
        return productivity;
    }

    public int getEnergyPerTick()
    {
        energyPerTick = 0;
        for (int i=0;i<6;i++)
            energyPerTick += AuraChipItem.getEnergyCostPerTick(itemhandler.getStackInSlot(i));
        return energyPerTick;
    }
    //----------------------------------------------------------------------------------------------------------------------

    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_)
    {
        return new CropAuraGeneratorContainer(p_createMenu_1_, p_createMenu_2_, this);
    }
}
