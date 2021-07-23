package net.kaneka.planttech2.datagen.recipes;

import com.google.gson.JsonObject;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;

import static net.kaneka.planttech2.datagen.recipes.InfuserRecipesProvider.Builder.create;
import static net.kaneka.planttech2.registries.ModItems.*;
import static net.minecraft.world.item.Items.BONE_MEAL;
import static net.minecraft.world.item.Items.REDSTONE;

public class InfuserRecipesProvider extends MachineRecipeProvider<InfuserRecipe>
{
    public InfuserRecipesProvider(DataGenerator generator)
    {
        super(generator, "infuser");
    }

    @Override
    void putRecipes()
    {
        putAll(
                create("fertilizer_tier_1").in(BONE_MEAL).biomass(100).out("planttech2:fertilizer_tier_1").build(),
                create("fertilizer_tier_2").in(FERTILIZER_TIER_1).biomass(200).out("planttech2:fertilizer_tier_2").build(),
                create("fertilizer_tier_3").in(FERTILIZER_TIER_2).biomass(300).out("planttech2:fertilizer_tier_3").build(),
                create("fertilizer_tier_4").in(FERTILIZER_TIER_3).biomass(400).out("planttech2:fertilizer_tier_4").build(),
                create("gear_dancium_infused").in(GEAR_DANCIUM).biomass(200).out("planttech2:gear_dancium_infused").build(),
                create("gear_iron_infused").in(GEAR_IRON).biomass(200).out("planttech2:gear_iron_infused").build(),
                create("gear_kanekium_infused").in(GEAR_KANEKIUM).biomass(200).out("planttech2:gear_kanekium_infused").build(),
                create("gear_kinnoium_infused").in(GEAR_KINNOIUM).biomass(200).out("planttech2:gear_kinnoium_infused").build(),
                create("gear_lenthurium_infused").in(GEAR_LENTHURIUM).biomass(200).out("planttech2:gear_lenthurium_infused").build(),
                create("gear_plantium_infused").in(GEAR_PLANTIUM).biomass(200).out("planttech2:gear_plantium_infused").build(),
                create("redstone_infused").in(REDSTONE).biomass(250).out("planttech2:redstone_infused").build()
        );
    }

    @Override
    protected JsonObject write(InfuserRecipe recipe)
    {
        JsonObject json = super.write(recipe);
        addWithBiomass(json, "input", recipe.getInput(), recipe.getBiomass());
        addItem(json, "result", recipe.getResultItem());
        return json;
    }

    @Override
    public String getName()
    {
        return "infusing";
    }

    static class Builder extends RecipeBuilder
    {
        private Item input;
        private int biomass;
        private Item output;

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
            return in(getItemFromString(input));
        }

        public Builder in(Item input)
        {
            this.input = input;
            return this;
        }

        public Builder biomass(int biomass)
        {
            this.biomass = biomass;
            return this;
        }

        public Builder out(String output)
        {
            return out(getItemFromString(output));
        }

        public Builder out(Item output)
        {
            this.output = output;
            return this;
        }

        public InfuserRecipe build()
        {
            return new InfuserRecipe(id, input, output, biomass);
        }
    }
}
