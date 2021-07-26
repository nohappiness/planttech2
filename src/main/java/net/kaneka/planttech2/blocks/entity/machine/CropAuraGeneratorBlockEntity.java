package net.kaneka.planttech2.blocks.entity.machine;

import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.inventory.CropAuraGeneratorMenu;
import net.kaneka.planttech2.items.AuraCoreItem;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.utilities.PlantTechConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CropAuraGeneratorBlockEntity extends EnergyInventoryBlockEntity
{
    public int lightValueDecrease;
    public int waterRangeDecrease;
    public EnumTemperature temperature;
    public Block soil;
    public int fertility;
    public int productivity;
    private int energyPerTick;

    protected final ContainerData field_array = new ContainerData()
    {
        @Override
        public int get(int index)
        {
            switch (index)
            {
                case 0:
                    return CropAuraGeneratorBlockEntity.this.energystorage.getEnergyStored();
                case 1:
                    return CropAuraGeneratorBlockEntity.this.energystorage.getMaxEnergyStored();
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
                    CropAuraGeneratorBlockEntity.this.energystorage.setEnergyStored(value);
                    break;
                case 1:
                    CropAuraGeneratorBlockEntity.this.energystorage.setEnergyMaxStored(value);
                    break;
            }
        }

        @Override
        public int getCount()
        {
            return 2;
        }
    };

    public CropAuraGeneratorBlockEntity()
    {
        this(BlockPos.ZERO, ModBlocks.CROP_AURA_GENERATOR.defaultBlockState());
    }

    public CropAuraGeneratorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModTileEntities.CROP_AURA_GENERATOR_TE, pos, state, 10000, 9, PlantTechConstants.MACHINETIER_CROP_AURA_GENERATOR);
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
    public ContainerData getIntArray()
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

    public EnumTemperature getTemperature()
    {
        temperature = AuraCoreItem.getTemperature(itemhandler.getStackInSlot(0)).orElse(null);
        return temperature;
    }

    public int getLightValueDecrease()
    {
        lightValueDecrease = AuraCoreItem.getLightValueDecrease(itemhandler.getStackInSlot(1)).orElse(0);
        return lightValueDecrease;
    }

    public int getWaterRangeDecrease()
    {
        waterRangeDecrease = AuraCoreItem.getWaterRangeDecrease(itemhandler.getStackInSlot(2)).orElse(0);
        return waterRangeDecrease;
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
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new CropAuraGeneratorMenu(id, inventory, this);
    }

    @Override
    public String getNameString()
    {
        return "cropauragenerator";
    }
}
