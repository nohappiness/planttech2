package net.kaneka.planttech2.datagen.recipes;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

class RecipeBuilder
{
    protected final ResourceLocation id;

    protected RecipeBuilder(String name)
    {
        this.id = new ResourceLocation(PlantTechMain.MODID, name);
    }

    protected Item getItemFromString(String name)
    {
        if (!name.contains(":"))
            name = "planttech2:" + name;
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(name));
    }
}