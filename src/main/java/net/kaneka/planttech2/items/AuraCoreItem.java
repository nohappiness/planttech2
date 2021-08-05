package net.kaneka.planttech2.items;

import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
        super(new Item.Properties().tab(ModCreativeTabs.CHIPS));
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
        return getTemperature(stack).isPresent();
    }

    public static boolean doesModifyLightValue(ItemStack stack)
    {
        return getLightValueDecrease(stack).isPresent();
    }

    public static boolean doesModifyWaterRange(ItemStack stack)
    {
        return getWaterRangeDecrease(stack).isPresent();
    }

    public static boolean doesModifySoil(ItemStack stack)
    {
        return getSoil(stack).isPresent();
    }

    public static boolean doesModifyFertility(ItemStack stack)
    {
        return getFertilityValueIncrease(stack).isPresent();
    }

    public static boolean doesModifyProductivity(ItemStack stack)
    {
        return getProductivityValueIncrease(stack).isPresent();
    }

    public static Optional<EnumTemperature> getTemperature(ItemStack stack)
    {
        return check(stack, (item) -> item.temperature, null);
    }

    public static Optional<Integer> getLightValueDecrease(ItemStack stack)
    {
        return check(stack, (item) -> item.lightValueDecrease, 0);
    }

    public static Optional<Integer> getWaterRangeDecrease(ItemStack stack)
    {
        return check(stack, (item) -> item.waterRangeDecrease, 0);
    }

    public static Optional<Block> getSoil(ItemStack stack)
    {
        return check(stack, (block) -> block.soil, Blocks.AIR);
    }

    public static Optional<Integer> getFertilityValueIncrease(ItemStack stack)
    {
        return check(stack, (item) -> item.fertility, 0);
    }

    public static Optional<Integer> getProductivityValueIncrease(ItemStack stack)
    {
        return check(stack, (item) -> item.productivity, 0);
    }

    public static Optional<Integer> getEnergyCostPerTick(ItemStack stack)
    {
        return check(stack, (item) -> item.energyCostPerTick, 0);
    }

    private static boolean checkExist(ItemStack stack, Function<AuraCoreItem, Boolean> exist)
    {
        return stack.getItem() instanceof AuraCoreItem && exist.apply((AuraCoreItem) stack.getItem());
    }

    private static <V> Optional<V> check(ItemStack stack, Function<AuraCoreItem, V> getter, V defaultV)
    {
        V value = null;
        if (stack.getItem() instanceof AuraCoreItem item)
            value = getter.apply(item);
        return value != null && value != defaultV ? Optional.of(value) : Optional.empty();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltips, TooltipFlag flagIn)
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

        public Builder setIncreaseFertility(int fertility)
        {
            this.fertility = fertility;
            return this;
        }

        public Builder setIncreaseProductivity(int productivity)
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
