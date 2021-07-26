package net.kaneka.planttech2.datagen.recipes;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public abstract class MachineRecipeProvider<R extends Recipe<Container>> implements DataProvider
{
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;
    protected final String folder;
    protected HashMap<ResourceLocation, R> recipes = new HashMap<>();

    protected MachineRecipeProvider(DataGenerator generator, String folder)
    {
        this.generator = generator;
        this.folder = "recipes/" + folder;
    }

    @Override
    public void run(HashCache cache) throws IOException
    {
        Path resourceRoot = generator.getOutputFolder();

        putRecipes();

        recipes.forEach((key, recipe) -> {
            Path target = getPath(resourceRoot, key);

            try
            {
                if (!Files.exists(target.getParent()))
                    Files.createDirectory(target.getParent());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            String data = GSON.toJson(write(recipe));
            try (BufferedWriter writer = Files.newBufferedWriter(target))
            {
                writer.write(data);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            cache.putNew(target, DataProvider.SHA1.hashUnencodedChars(data).toString());
        });
    }

    private Path getPath(Path pathIn, ResourceLocation id)
    {
        return pathIn.resolve("data/" + id.getNamespace() + "/" + folder + "/" + id.getPath() + ".json");
    }

    protected JsonObject write(R recipe)
    {
        JsonObject json = new JsonObject();
        json.addProperty("type", PlantTechMain.MODID + ":" + getName());
        return json;
    }

    abstract void putRecipes();

    void putAll(R... recipes)
    {
        for (R r : recipes)
            put(r);
    }

    void put(R recipe)
    {
        recipes.put(recipe.getId(), recipe);
    }

    protected void addItem(JsonObject json, String key, ItemStack stack)
    {
        addItem(json, key, stack.getItem());
    }

    protected void addItem(JsonObject json, String key, Item item)
    {
        add(json, key, "item", item.getRegistryName().toString());
    }

    protected void addItemStack(JsonObject json, String key, ItemStack stack)
    {
        json.add(key, new JsonParser().parse(GSON.toJson(ImmutableMap.of(
                "item", stack.getItem().getRegistryName().toString(),
                "count", String.valueOf(stack.getCount())
        ))));
    }

    protected void addWithBiomass(JsonObject json, String key, Item stack, int biomass)
    {
        json.add(key, new JsonParser().parse(GSON.toJson(ImmutableMap.of(
                "item", stack.asItem().getRegistryName().toString(),
                "biomass", biomass
        ))));
    }

    protected void add(JsonObject json, String key, String subKey, String subValue)
    {
        json.add(key, new JsonParser().parse(GSON.toJson(ImmutableMap.of(
                subKey, subValue
        ))));
    }
}
