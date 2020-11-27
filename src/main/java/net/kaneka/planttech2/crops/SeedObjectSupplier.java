package net.kaneka.planttech2.crops;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SeedObjectSupplier<T extends IForgeRegistryEntry<T>> implements Supplier<T>
{
    private T object;
    private final ResourceLocation name;
    private final IForgeRegistry<T> registry;

    public SeedObjectSupplier(String name, IForgeRegistry<T> registry)
    {
        this(new ResourceLocation(PlantTechMain.MODID, name), registry);
    }

    public SeedObjectSupplier(ResourceLocation name, IForgeRegistry<T> registry)
    {
        this.name = name;
        this.registry = registry;
    }

    public static <R extends IForgeRegistryEntry<R>> SeedObjectSupplier<R> of(String name, IForgeRegistry<R> registry)
    {
        return of(new ResourceLocation(PlantTechMain.MODID, name), registry);
    }

    public static <R extends IForgeRegistryEntry<R>> SeedObjectSupplier<R> of(ResourceLocation name, IForgeRegistry<R> registry)
    {
        return new SeedObjectSupplier<>(name, registry);
    }

    @Nullable
    @Override
    public T get()
    {
        if (object == null)
            object = registry.getValue(name);
        return object;
    }
}
