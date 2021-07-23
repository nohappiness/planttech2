package net.kaneka.planttech2.datagen.recipes;

import com.google.gson.JsonObject;
import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import static net.kaneka.planttech2.datagen.recipes.ChipalyzerRecipesProvider.Builder.create;
import static net.kaneka.planttech2.registries.ModItems.*;
import static net.minecraft.enchantment.Enchantments.*;
import static net.minecraft.world.item.Items.*;

public class ChipalyzerRecipesProvider extends MachineRecipeProvider<ChipalyzerRecipe>
{
    public ChipalyzerRecipesProvider(DataGenerator generator)
    {
        super(generator, "chipalyzer");
    }

    @Override
    void putRecipes()
    {
        putAll(
                create("aqua_affinity_chip").chip("planttech2:empty_upgradechip_1").enchantment(AQUA_AFFINITY).out("planttech2:aqua_affinity_chip").build(),
                create("armorchip_tier_1").chip("planttech2:empty_upgradechip_1").in(IRON_CHESTPLATE).out("planttech2:armorchip_tier_1").build(),
                create("armorchip_tier_2").chip("planttech2:armorchip_tier_1").in(EMPTY_UPGRADECHIP_TIER_2).out("planttech2:armorchip_tier_2").build(),
                create("armorchip_tier_3").chip("planttech2:armorchip_tier_2").in(EMPTY_UPGRADECHIP_TIER_3).out("planttech2:armorchip_tier_3").build(),
                create("attackchip_tier_1").chip("planttech2:empty_upgradechip_1").in(IRON_SWORD).out("planttech2:attackchip_tier_1").build(),
                create("attackchip_tier_2").chip("planttech2:attackchip_tier_1").in(EMPTY_UPGRADECHIP_TIER_2).out("planttech2:attackchip_tier_2").build(),
                create("attackchip_tier_3").chip("planttech2:attackchip_tier_2").in(EMPTY_UPGRADECHIP_TIER_3).out("planttech2:attackchip_tier_3").build(),
                create("attackspeedchip_tier_1").chip("planttech2:empty_upgradechip_1").in(GOLDEN_SWORD).out("planttech2:attackspeedchip_tier_1").build(),
                create("attackspeedchip_tier_2").chip("planttech2:attackspeedchip_tier_1").in(EMPTY_UPGRADECHIP_TIER_2).out("planttech2:attackspeedchip_tier_2").build(),
                create("attackspeedchip_tier_3").chip("planttech2:attackspeedchip_tier_2").in(EMPTY_UPGRADECHIP_TIER_3).out("planttech2:attackspeedchip_tier_3").build(),
                create("bane_of_arthropods_chip").chip("planttech2:empty_upgradechip_1").enchantment(BANE_OF_ARTHROPODS).out("planttech2:bane_of_arthropods_chip").build(),
                create("blast_protection_chip").chip("planttech2:empty_upgradechip_1").enchantment(BLAST_PROTECTION).out("planttech2:blast_protection_chip").build(),
                create("breakdownratechip_tier_1").chip("planttech2:empty_upgradechip_1").in(GOLDEN_PICKAXE).out("planttech2:breakdownratechip_tier_1").build(),
                create("breakdownratechip_tier_2").chip("planttech2:breakdownratechip_tier_1").in(EMPTY_UPGRADECHIP_TIER_2).out("planttech2:breakdownratechip_tier_2").build(),
                create("breakdownratechip_tier_3").chip("planttech2:breakdownratechip_tier_2").in(EMPTY_UPGRADECHIP_TIER_3).out("planttech2:breakdownratechip_tier_3").build(),
                create("capacitychip_tier_1").chip("planttech2:empty_upgradechip_1").in(ENERGYSTORAGE_TIER_1).out("planttech2:capacitychip_tier_1").build(),
                create("capacitychip_tier_2").chip("planttech2:capacitychip_tier_1").in(CHIP_UPGRADEPACK_CAPACITY_1).out("planttech2:capacitychip_tier_2").build(),
                create("capacitychip_tier_3").chip("planttech2:capacitychip_tier_2").in(CHIP_UPGRADEPACK_CAPACITY_2).out("planttech2:capacitychip_tier_3").build(),
                create("depth_strider_chip").chip("planttech2:empty_upgradechip_1").enchantment(DEPTH_STRIDER).out("planttech2:depth_strider_chip").build(),
                create("efficiency_chip").chip("planttech2:empty_upgradechip_1").enchantment(BLOCK_EFFICIENCY).out("planttech2:efficiency_chip").build(),
                create("feather_falling_chip").chip("planttech2:empty_upgradechip_1").enchantment(FALL_PROTECTION).out("planttech2:feather_falling_chip").build(),
                create("fire_aspect_chip").chip("planttech2:empty_upgradechip_1").enchantment(FIRE_ASPECT).out("planttech2:fire_aspect_chip").build(),
                create("fire_protection_chip").chip("planttech2:empty_upgradechip_1").enchantment(FIRE_PROTECTION).out("planttech2:fire_protection_chip").build(),
                create("flame_chip").chip("planttech2:empty_upgradechip_1").enchantment(FLAMING_ARROWS).out("planttech2:flame_chip").build(),
                create("fortune_chip").chip("planttech2:empty_upgradechip_1").enchantment(BLOCK_FORTUNE).out("planttech2:fortune_chip").build(),
                create("frost_walker_chip").chip("planttech2:empty_upgradechip_1").enchantment(FROST_WALKER).out("planttech2:frost_walker_chip").build(),
                create("harvestlevelchip_tier_1").chip("planttech2:empty_upgradechip_1").in(STONE_PICKAXE).out("planttech2:harvestlevelchip_tier_1").build(),
                create("harvestlevelchip_tier_2").chip("planttech2:harvestlevelchip_tier_1").in(CHIP_UPGRADEPACK_HARVESTLEVEL_1).out("planttech2:harvestlevelchip_tier_2").build(),
                create("harvestlevelchip_tier_3").chip("planttech2:harvestlevelchip_tier_2").in(CHIP_UPGRADEPACK_HARVESTLEVEL_2).out("planttech2:harvestlevelchip_tier_3").build(),
                create("infinity_chip").chip("planttech2:empty_upgradechip_1").enchantment(INFINITY_ARROWS).out("planttech2:infinity_chip").build(),
                create("knockback_chip").chip("planttech2:empty_upgradechip_1").enchantment(KNOCKBACK).out("planttech2:knockback_chip").build(),
                create("looting_chip").chip("planttech2:empty_upgradechip_1").enchantment(MOB_LOOTING).out("planttech2:looting_chip").build(),
                create("power_chip").chip("planttech2:empty_upgradechip_1").enchantment(POWER_ARROWS).out("planttech2:power_chip").build(),
                create("projectile_protection_chip").chip("planttech2:empty_upgradechip_1").enchantment(PROJECTILE_PROTECTION).out("planttech2:projectile_protection_chip").build(),
                create("protection_chip").chip("planttech2:empty_upgradechip_1").enchantment(ALL_DAMAGE_PROTECTION).out("planttech2:protection_chip").build(),
                create("punch_chip").chip("planttech2:empty_upgradechip_1").enchantment(PUNCH_ARROWS).out("planttech2:punch_chip").build(),
                create("reactorchip_tier_1").chip("planttech2:empty_upgradechip_1").in(SOLARFOCUS_TIER_1).out("planttech2:reactorchip_tier_1").build(),
                create("reactorchip_tier_2").chip("planttech2:reactorchip_tier_1").in(CHIP_UPGRADEPACK_REACTOR_1).out("planttech2:reactorchip_tier_2").build(),
                create("reactorchip_tier_3").chip("planttech2:reactorchip_tier_2").in(CHIP_UPGRADEPACK_REACTOR_2).out("planttech2:reactorchip_tier_3").build(),
                create("respiration_chip").chip("planttech2:empty_upgradechip_1").enchantment(RESPIRATION).out("planttech2:respiration_chip").build(),
                create("sharpness_chip").chip("planttech2:empty_upgradechip_1").enchantment(SHARPNESS).out("planttech2:sharpness_chip").build(),
                create("silk_touch_chip").chip("planttech2:empty_upgradechip_1").enchantment(SILK_TOUCH).out("planttech2:silk_touch_chip").build(),
                create("smite_chip").chip("planttech2:empty_upgradechip_1").enchantment(SMITE).out("planttech2:smite_chip").build(),
                create("sweeping_chip").chip("planttech2:empty_upgradechip_1").enchantment(SWEEPING_EDGE).out("planttech2:sweeping_chip").build(),
                create("thorns_chip").chip("planttech2:empty_upgradechip_1").enchantment(THORNS).out("planttech2:thorns_chip").build(),
                create("toughnesschip_tier_1").chip("planttech2:empty_upgradechip_1").in(DIAMOND_CHESTPLATE).out("planttech2:toughnesschip_tier_1").build(),
                create("toughnesschip_tier_2").chip("planttech2:toughnesschip_tier_1").in(EMPTY_UPGRADECHIP_TIER_2).out("planttech2:toughnesschip_tier_2").build(),
                create("toughnesschip_tier_3").chip("planttech2:toughnesschip_tier_2").in(EMPTY_UPGRADECHIP_TIER_3).out("planttech2:toughnesschip_tier_3").build(),
                create("unbreaking_chip").chip("planttech2:empty_upgradechip_1").enchantment(UNBREAKING).out("planttech2:unbreaking_chip").build(),
                create("unlockchip_axe").chip("planttech2:empty_upgradechip_1").in(IRON_AXE).out("planttech2:unlockchip_axe").build(),
                create("unlockchip_hoe").chip("planttech2:empty_upgradechip_1").in(IRON_HOE).out("planttech2:unlockchip_hoe").build(),
                create("unlockchip_shears").chip("planttech2:empty_upgradechip_1").in(SHEARS).out("planttech2:unlockchip_shears").build(),
                create("unlockchip_shovel").chip("planttech2:empty_upgradechip_1").in(IRON_SHOVEL).out("planttech2:unlockchip_shovel").build()
        );
    }

    @Override
    protected JsonObject write(ChipalyzerRecipe recipe)
    {
        JsonObject json = super.write(recipe);
        addItem(json, "chip", recipe.getChip());
        boolean isEnchantment = recipe.getEnchantment() != null;
        add(json, "input", isEnchantment ? "enchantment" : "item", (isEnchantment ? recipe.getEnchantment() : recipe.getInput().getItem()).getRegistryName().toString());
        addItem(json, "result", recipe.getResultItem());
        return json;
    }

    @Override
    public String getName()
    {
        return "chipalyzer";
    }

    static class Builder extends RecipeBuilder
    {
        private ItemStack input;
        private ItemStack chip;
        private Enchantment enchantment;
        private ItemStack output;

        public static Builder create(String name)
        {
            return new Builder(name);
        }

        private Builder(String name)
        {
            super(name);
        }

        public Builder in(String input)
        {
            return in(input, 1);
        }

        public Builder in(String input, int count)
        {
            return in(getItemFromString(input), count);
        }

        public Builder in(Item input)
        {
            return in(input, 1);
        }

        public Builder in(Item input, int count)
        {
            return in(new ItemStack(input, count));
        }

        public Builder in(ItemStack input)
        {
            this.input = input;
            return this;
        }

        public Builder chip(String chip)
        {
            return chip(chip, 1);
        }

        public Builder chip(String chip, int count)
        {
            return chip(getItemFromString(chip), count);
        }

        public Builder chip(Item chip)
        {
            return chip(chip, 1);
        }

        public Builder chip(Item chip, int count)
        {
            return chip(new ItemStack(chip, count));
        }

        public Builder chip(ItemStack chip)
        {
            this.chip = chip;
            return this;
        }

        public Builder enchantment(String enchant)
        {
            return enchantment(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchant)));
        }

        public Builder enchantment(Enchantment enchantment)
        {
            this.enchantment = enchantment;
            return this;
        }

        public Builder out(String output)
        {
            return out(output, 1);
        }

        public Builder out(String output, int count)
        {
            return out(getItemFromString(output), count);
        }

        public Builder out(Item output)
        {
            return out(output, 1);
        }

        public Builder out(Item output, int count)
        {
            return out(new ItemStack(output, count));
        }

        public Builder out(ItemStack output)
        {
            this.output = output;
            return this;
        }

        public ChipalyzerRecipe build()
        {
            return new ChipalyzerRecipe(id, chip, input, enchantment, output);
        }
    }
}
