package net.kaneka.planttech2.items;

import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AuraChipItem extends Item
{
    private final EnumTemperature temperature;
    private final int lightValueDecrease;
    private final int waterRangeDecrease;
    private final Block soil;
    private final int fertility;
    private final int productivity;
    private final int energyCostPerTick;

    public AuraChipItem(EnumTemperature temperature, int lightValueDecrease, int waterRangeDecrease, Block soil, int fertility, int productivity, int energyCostPerTick)
    {
        super(new Item.Properties().group(ModCreativeTabs.CHIPS));
        this.temperature = temperature;
        this.lightValueDecrease = lightValueDecrease;
        this.waterRangeDecrease = waterRangeDecrease;
        this.soil = soil == null ? Blocks.AIR : soil;
        this.fertility = fertility;
        this.productivity = productivity;
        this.energyCostPerTick = energyCostPerTick;
    }

    public static boolean doesModifyTemperature(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem && ((AuraChipItem) stack.getItem()).temperature != null;
    }

    public static boolean doesModifyLightValue(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem && ((AuraChipItem) stack.getItem()).lightValueDecrease != 0;
    }

    public static boolean doesModifyWaterRange(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem && ((AuraChipItem) stack.getItem()).waterRangeDecrease != 0;
    }

    public static boolean doesModifySoil(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem && ((AuraChipItem) stack.getItem()).soil != Blocks.AIR;
    }

    public static boolean doesModifyFertility(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem && ((AuraChipItem) stack.getItem()).fertility != 0;
    }

    public static boolean doesModifyProductivity(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem && ((AuraChipItem) stack.getItem()).productivity != 0;
    }

    public static EnumTemperature getTemperature(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem ? ((AuraChipItem) stack.getItem()).temperature : null;
    }

    public static int getLightValueDecrease(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem ? ((AuraChipItem) stack.getItem()).lightValueDecrease : 0;
    }

    public static int getWaterRangeDecrease(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem ? ((AuraChipItem) stack.getItem()).waterRangeDecrease : 0;
    }

    public static Block getSoil(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem ? ((AuraChipItem) stack.getItem()).soil : null;
    }

    public static int getFertilityValueIncrease(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem ? ((AuraChipItem) stack.getItem()).fertility : 0;
    }

    public static int getProductivityValueIncrease(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem ? ((AuraChipItem) stack.getItem()).productivity : 0;
    }

    public static int getEnergyCostPerTick(ItemStack stack)
    {
        return stack.getItem() instanceof AuraChipItem ? ((AuraChipItem) stack.getItem()).energyCostPerTick : 0;
    }
}
