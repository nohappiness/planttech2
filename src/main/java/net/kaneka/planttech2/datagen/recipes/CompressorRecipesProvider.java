package net.kaneka.planttech2.datagen.recipes;

import com.google.gson.JsonObject;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import static net.kaneka.planttech2.datagen.recipes.CompressorRecipesProvider.Builder.create;
import static net.kaneka.planttech2.registries.ModItems.*;
import static net.minecraft.world.item.Items.*;

public class CompressorRecipesProvider extends MachineRecipeProvider<CompressorRecipe>
{
    public CompressorRecipesProvider(DataGenerator generator)
    {
        super(generator, "compressor");
    }

    @Override
    void putRecipes()
    {
        putAll(
                create("blaze-blaze_rod").in("planttech2:blaze_particles", 8).out(BLAZE_ROD).build(),
                create("chicken-chicken").in("planttech2:chicken_particles", 8).out(CHICKEN).build(),
                create("chicken-feather").in("planttech2:chicken_particles", 8).out(FEATHER).build(),
                create("coal-coal").in("planttech2:coal_particles", 8).out(COAL).build(),
                create("coal-coal_ore").in("planttech2:coal_particles", 8).out(COAL_ORE).build(),
                create("color-lapis_lazuli").in("planttech2:color_particles", 8).out(LAPIS_LAZULI).build(),
                create("color-brown_dye").in("planttech2:color_particles", 8).out(BROWN_DYE).build(),
                create("color-cyan_dye").in("planttech2:color_particles", 8).out(CYAN_DYE).build(),
                create("color-gray_dye").in("planttech2:color_particles", 8).out(GRAY_DYE).build(),
                create("color-green_dye").in("planttech2:color_particles", 8).out(GREEN_DYE).build(),
                create("color-light_blue_dye").in("planttech2:color_particles", 8).out(LIGHT_BLUE_DYE).build(),
                create("color-light_gray_dye").in("planttech2:color_particles", 8).out(LIGHT_GRAY_DYE).build(),
                create("color-lime_dye").in("planttech2:color_particles", 8).out(LIME_DYE).build(),
                create("color-magenta_dye").in("planttech2:color_particles", 8).out(MAGENTA_DYE).build(),
                create("color-orange_dye").in("planttech2:color_particles", 8).out(ORANGE_DYE).build(),
                create("color-pink_dye").in("planttech2:color_particles", 8).out(PINK_DYE).build(),
                create("color-purple_dye").in("planttech2:color_particles", 8).out(PURPLE_DYE).build(),
                create("color-red_dye").in("planttech2:color_particles", 8).out(RED_DYE).build(),
                create("color-bone_meal").in("planttech2:color_particles", 8).out(BONE_MEAL).build(),
                create("color-yellow_dye").in("planttech2:color_particles", 8).out(YELLOW_DYE).build(),
                create("cow-beef").in("planttech2:cow_particles", 8).out(BEEF).build(),
                create("cow-leather").in("planttech2:cow_particles", 8).out(LEATHER).build(),
                create("creeper-gunpowder").in("planttech2:creeper_particles", 8).out(GUNPOWDER).build(),
                create("dancium-dancium_ingot").in("planttech2:dancium_particles", 8).out(DANCIUM_INGOT).build(),
                create("diamond-diamond").in("planttech2:diamond_particles", 8).out(DIAMOND).build(),
                create("diamond-diamond_ore").in("planttech2:diamond_particles", 12).out(DIAMOND_ORE).build(),
                create("dirt-dirt").in("planttech2:dirt_particles", 8).out(DIRT).build(),
                create("dirt-grass_block").in("planttech2:dirt_particles", 8).out(GRASS_BLOCK).build(),
                create("emerald-emerald").in("planttech2:emerald_particles", 8).out(EMERALD).build(),
                create("emerald-emerald_ore").in("planttech2:emerald_particles", 12).out(EMERALD_ORE).build(),
                create("enderdragon-dragon_breath").in("planttech2:enderdragon_particles", 8).out(DRAGON_BREATH).build(),
                create("enderdragon-dragon_egg").in("planttech2:enderdragon_particles", 8).out(DRAGON_EGG).build(),
                create("enderman-ender_pearl").in("planttech2:enderman_particles", 8).out(ENDER_PEARL).build(),
                create("endstone-end_stone").in("planttech2:endstone_particles", 8).out(END_STONE).build(),
                create("fish-cod").in("planttech2:fish_particles", 8).out(COD).build(),
                create("fish-pufferfish").in("planttech2:fish_particles", 8).out(PUFFERFISH).build(),
                create("fish-salmon").in("planttech2:fish_particles", 8).out(SALMON).build(),
                create("fish-tropical_fish").in("planttech2:fish_particles", 8).out(TROPICAL_FISH).build(),
                create("ghast-ghast_tear").in("planttech2:ghast_particles", 8).out(GHAST_TEAR).build(),
                create("glowstone-glowstone").in("planttech2:glowstone_particles", 8).out(GLOWSTONE).build(),
                create("glowstone-glowstone_dust").in("planttech2:glowstone_particles", 2).out(GLOWSTONE_DUST).build(),
                create("gold-gold_ore").in("planttech2:gold_particles", 8).out(GOLD_ORE).build(),
                create("guardian-experience_bottle").in("planttech2:guardian_particles", 8).out(EXPERIENCE_BOTTLE).build(),
                create("husk-iron_ingot").in("planttech2:husk_particles", 8).out(IRON_INGOT).build(),
                create("illager-iron_axe").in("planttech2:illager_particles", 24).out(IRON_AXE).build(),
                create("illager-iron_boots").in("planttech2:illager_particles", 32).out(IRON_BOOTS).build(),
                create("illager-iron_chestplate").in("planttech2:illager_particles", 64).out(IRON_CHESTPLATE).build(),
                create("illager-iron_helmet").in("planttech2:illager_particles", 40).out(IRON_HELMET).build(),
                create("illager-iron_leggings").in("planttech2:illager_particles", 56).out(IRON_LEGGINGS).build(),
                create("illager-iron_pickaxe").in("planttech2:illager_particles", 24).out(IRON_PICKAXE).build(),
                create("illager-iron_shovel").in("planttech2:illager_particles", 8).out(IRON_SHOVEL).build(),
                create("illager-iron_sword").in("planttech2:illager_particles", 16).out(IRON_SWORD).build(),
                create("iron-iron_ore").in("planttech2:iron_particles", 8).out(IRON_ORE).build(),
                create("kanekium-kanekium_ingot").in("planttech2:kanekium_particles", 8).out(KANEKIUM_INGOT).build(),
                create("kinnoium-kinnoium_ingot").in("planttech2:kinnoium_particles", 8).out(KINNOIUM_INGOT).build(),
                create("lapis-lapis_lazuli").in("planttech2:lapis_particles", 8).out(LAPIS_LAZULI).build(),
                create("lapis-lapis_ore").in("planttech2:lapis_particles", 12).out(LAPIS_ORE).build(),
                create("lava-lava_bucket").in("planttech2:lava_particles", 8).out(LAVA_BUCKET).build(),
                create("lenthurium-lenthurium_ingot").in("planttech2:lenthurium_particles", 8).out(LENTHURIUM_INGOT).build(),
                create("magma_cube-magma_cream").in("planttech2:magma_cube_particles", 8).out(MAGMA_CREAM).build(),
                create("mycelium-mycelium").in("planttech2:mycelium_particles", 8).out(MYCELIUM).build(),
                create("netherrack-netherrack").in("planttech2:netherrack_particles", 8).out(NETHERRACK).build(),
                create("parrot-color_particles").in("planttech2:parrot_particles", 8).out(COLOR_PARTICLES, 8).build(),
                create("parrot-feather").in("planttech2:parrot_particles", 8).out(FEATHER).build(),
                create("pig-porkchop").in("planttech2:pig_particles", 8).out(PORKCHOP).build(),
                create("plantium-plantium_ingot").in("planttech2:plantium_particles", 8).out(PLANTIUM_INGOT).build(),
                create("prismarine-prismarine_crystals").in("planttech2:prismarine_particles", 8).out(PRISMARINE_CRYSTALS).build(),
                create("prismarine-prismarine_shard").in("planttech2:prismarine_particles", 8).out(PRISMARINE_SHARD).build(),
                create("quartz-nether_quartz_ore").in("planttech2:quartz_particles", 8).out(NETHER_QUARTZ_ORE).build(),
                create("redstone-redstone_ore").in("planttech2:redstone_particles", 8).out(REDSTONE_ORE).build(),
                create("sand-sand").in("planttech2:sand_particles", 8).out(SAND).build(),
                create("sand-sandstone").in("planttech2:sand_particles", 32).out(SANDSTONE).build(),
                create("sand-smooth_sandstone").in("planttech2:sand_particles", 32).out(SMOOTH_SANDSTONE).build(),
                create("sheep-mutton").in("planttech2:sheep_particles", 8).out(MUTTON).build(),
                create("sheep-white_wool").in("planttech2:sheep_particles", 8).out(WHITE_WOOL).build(),
                create("shulker-shulker_shell").in("planttech2:shulker_particles", 8).out(SHULKER_SHELL).build(),
                create("skeleton-arrow").in("planttech2:skeleton_particles", 8).out(ARROW).build(),
                create("skeleton-bone").in("planttech2:skeleton_particles", 8).out(BONE).build(),
                create("slime-slime_ball").in("planttech2:slime_particles", 8).out(SLIME_BALL).build(),
                create("snow-ice").in("planttech2:snow_particles", 8).out(ICE).build(),
                create("snow-snowball").in("planttech2:snow_particles", 8).out(SNOWBALL).build(),
                create("soulsand-soul_sand").in("planttech2:soulsand_particles", 8).out(SOUL_SAND).build(),
                create("spider-spider_eye").in("planttech2:spider_particles", 8).out(SPIDER_EYE).build(),
                create("spider-string").in("planttech2:spider_particles", 8).out(STRING).build(),
                create("sponge-sponge").in("planttech2:sponge_particles", 8).out(SPONGE).build(),
                create("squid-ink_sac").in("planttech2:squid_particles", 8).out(INK_SAC).build(),
                create("stone-stone").in("planttech2:stone_particles", 8).out(STONE).build(),
                create("stone-cobblestone").in("planttech2:stone_particles", 8).out(COBBLESTONE).build(),
                create("stone-smooth_stone").in("planttech2:stone_particles", 8).out(SMOOTH_STONE).build(),
                create("stray-tipped_arrow").in("planttech2:stray_particles", 8).out(TIPPED_ARROW).build(),
                create("stray-bone").in("planttech2:stray_particles", 8).out(BONE).build(),
                create("turtle-turtle_helmet").in("planttech2:turtle_particles", 64).out(TURTLE_HELMET).build(),
                create("turtle-scute").in("planttech2:turtle_particles", 8).out(SCUTE).build(),
                create("villager-emerald").in("planttech2:villager_particles", 8).out(EMERALD).build(),
                create("water-water_bucket").in("planttech2:water_particles", 8).out(WATER_BUCKET).build(),
                create("witch-glass_bottle").in("planttech2:witch_particles", 8).out(GLASS_BOTTLE).build(),
                create("witch-glowstone_dust").in("planttech2:witch_particles", 8).out(GLOWSTONE_DUST).build(),
                create("witch-redstone").in("planttech2:witch_particles", 8).out(REDSTONE).build(),
                create("wither-nether_star").in("planttech2:wither_particles", 8).out(NETHER_STAR).build(),
                create("wither_skeleton-wither_skeleton_skull").in("planttech2:wither_skeleton_particles", 8).out(WITHER_SKELETON_SKULL).build(),
                create("wood-acacia_log").in("planttech2:wood_particles", 8).out(ACACIA_LOG).build(),
                create("wood-birch_log").in("planttech2:wood_particles", 8).out(BIRCH_LOG).build(),
                create("wood-dark_oak_log").in("planttech2:wood_particles", 8).out(DARK_OAK_LOG).build(),
                create("wood-jungle_log").in("planttech2:wood_particles", 8).out(JUNGLE_LOG).build(),
                create("wood-oak_log").in("planttech2:wood_particles", 8).out(OAK_LOG).build(),
                create("wood-spruce_log").in("planttech2:wood_particles", 8).out(SPRUCE_LOG).build(),
                create("zombie-rotten_flesh").in("planttech2:zombie_particles", 8).out(ROTTEN_FLESH).build(),
                create("zombie_pigman-gold_nugget").in("planttech2:zombie_pigman_particles", 8).out(GOLD_NUGGET).build(),
                create("zombie_villager-iron_ingot").in("planttech2:zombie_villager_particles", 8).out(IRON_INGOT).build()
        );
    }

    @Override
    protected JsonObject write(CompressorRecipe recipe)
    {
        JsonObject json = super.write(recipe);
        addItemStack(json, "input", recipe.getInput());
        addItemStack(json, "result", recipe.getResultItem());
        return json;
    }

    @Override
    public String getName()
    {
        return "compressing";
    }

    static class Builder extends RecipeBuilder
    {
        private ItemStack input;
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

        public CompressorRecipe build()
        {
            return new CompressorRecipe(id, input, output);
        }
    }
}
