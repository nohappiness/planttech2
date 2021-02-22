package net.kaneka.planttech2.items;

import net.kaneka.planttech2.crops.CropConfiguration;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class AuraCoreItem extends Item
{
    private final int tier;
    private final EnumTemperature temperature;
    private final int lightValueDecrease;
    private final int waterRangeDecrease;
    private final Block soil;
    private final int fertility;
    private final int productivity;
    private final int energyCostPerTick;

    public AuraCoreItem(Builder builder)
    {
        super(new Item.Properties().group(ModCreativeTabs.CHIPS));
        tier = builder.tier;
        temperature = builder.temperature;
        lightValueDecrease = builder.lightValueDecrease;
        waterRangeDecrease = builder.waterRangeDecrease;
        soil = builder.soil;
        fertility = builder.fertility;
        productivity = builder.productivity;
        energyCostPerTick = builder.energyCostPerTick == 0 ? tier * 1000 : builder.energyCostPerTick;
    }

    public static boolean doesModifyTemperature(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem && ((AuraCoreItem) stack.getItem()).temperature != null;
    }

    public static boolean doesModifyLightValue(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem && ((AuraCoreItem) stack.getItem()).lightValueDecrease != 0;
    }

    public static boolean doesModifyWaterRange(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem && ((AuraCoreItem) stack.getItem()).waterRangeDecrease != 0;
    }

    public static boolean doesModifySoil(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem && ((AuraCoreItem) stack.getItem()).soil != Blocks.AIR;
    }

    public static boolean doesModifyFertility(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem && ((AuraCoreItem) stack.getItem()).fertility != 0;
    }

    public static boolean doesModifyProductivity(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem && ((AuraCoreItem) stack.getItem()).productivity != 0;
    }

    public static EnumTemperature getTemperature(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem ? ((AuraCoreItem) stack.getItem()).temperature : null;
    }

    public static int getLightValueDecrease(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem ? ((AuraCoreItem) stack.getItem()).lightValueDecrease : 0;
    }

    public static int getWaterRangeDecrease(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem ? ((AuraCoreItem) stack.getItem()).waterRangeDecrease : 0;
    }

    public static Block getSoil(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem ? ((AuraCoreItem) stack.getItem()).soil : null;
    }

    public static int getFertilityValueIncrease(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem ? ((AuraCoreItem) stack.getItem()).fertility : 0;
    }

    public static int getProductivityValueIncrease(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem ? ((AuraCoreItem) stack.getItem()).productivity : 0;
    }

    public static int getEnergyCostPerTick(ItemStack stack)
    {
        return stack.getItem() instanceof AuraCoreItem ? ((AuraCoreItem) stack.getItem()).energyCostPerTick : 0;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltips, ITooltipFlag flagIn)
    {
        String tooltip = "";
        if (doesModifyTemperature(stack))
            tooltip = "Change the temperature";
    }

    public static class Builder
    {
        private final int tier;
        private EnumTemperature temperature;
        private int lightValueDecrease;
        private int waterRangeDecrease;
        private Block soil;
        private int fertility;
        private int productivity;
        private int energyCostPerTick = 0;

        public Builder(int tier)
        {
            this.tier = tier;
        }

        public Builder setTemperature(EnumTemperature temperature)
        {
            this.temperature = temperature;
            return this;
        }

        public Builder setLightValueDecrease(int lightValueDecrease)
        {
            this.lightValueDecrease = lightValueDecrease;
            return this;
        }

        public Builder setWaterRangeDecrease(int waterRangeDecrease)
        {
            this.waterRangeDecrease = waterRangeDecrease;
            return this;
        }

        public Builder setSoil(Block soil)
        {
            this.soil = soil;
            return this;
        }

        public Builder setFertility(int fertility)
        {
            this.fertility = fertility;
            return this;
        }

        public Builder setProductivity(int productivity)
        {
            this.productivity = productivity;
            return this;
        }

        public Builder setEnergyCostPerTick(int energyCostPerTick)
        {
            this.energyCostPerTick = energyCostPerTick;
            return this;
        }
    }
}
