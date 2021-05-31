package net.kaneka.planttech2.tileentity.machine;

import net.kaneka.planttech2.container.CropAuraGeneratorContainer;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.items.AuraCoreItem;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IIntArray;

public class CropAuraGeneratorTileEntity extends EnergyInventoryTileEntity
{
    public int lightValueDecrease;
    public int waterRangeDecrease;
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
        super(ModTileEntities.CROP_AURA_GENERATOR_TE, 10000, 9, PlantTechConstants.MACHINETIER_CROP_AURA_GENERATOR);
    }

    public boolean consumeEnergy(int requiredEnergy)
    {
        if (energystorage.getEnergyStored() >= requiredEnergy)
        {
            energystorage.extractEnergy(requiredEnergy);
            return true;
        }
        return false;
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
        switch (slotIndex)
        {
            case 0:
                getTemperature();
                break;
            case 1:
                getLightValueDecrease();
                break;
            case 2:
                getWaterRangeDecrease();
                break;
            case 3:
                getSoil();
                break;
            case 4:
                getFertility();
                break;
            case 5:
                getProductivity();
                break;
        }
    }

//----------------------------------------------------------------------------------------------------------------------
    /**
     * Updates and gets the value, access the fields
     * directly if update is not needed
     */
    public int getLightValueDecrease()
    {
        lightValueDecrease = AuraCoreItem.getLightValueDecrease(itemhandler.getStackInSlot(0)).orElse(0);
        return lightValueDecrease;
    }

    public int getWaterRangeDecrease()
    {
        waterRangeDecrease = AuraCoreItem.getWaterRangeDecrease(itemhandler.getStackInSlot(1)).orElse(0);
        return waterRangeDecrease;
    }

    public EnumTemperature getTemperature()
    {
        temperature = AuraCoreItem.getTemperature(itemhandler.getStackInSlot(2)).orElse(null);
        return temperature;
    }

    public Block getSoil()
    {
        soil = AuraCoreItem.getSoil(itemhandler.getStackInSlot(3)).orElse(Blocks.AIR);
        return soil;
    }

    public int getFertility()
    {
        fertility = AuraCoreItem.getFertilityValueIncrease(itemhandler.getStackInSlot(4)).orElse(0);
        return fertility;
    }

    public int getProductivity()
    {
        productivity = AuraCoreItem.getProductivityValueIncrease(itemhandler.getStackInSlot(5)).orElse(0);
        return productivity;
    }
    //----------------------------------------------------------------------------------------------------------------------

    @Override
    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player)
    {
        return new CropAuraGeneratorContainer(id, inventory, this);
    }
}
