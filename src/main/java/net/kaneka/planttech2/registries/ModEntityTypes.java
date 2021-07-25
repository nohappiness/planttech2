package net.kaneka.planttech2.registries;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(PlantTechMain.MODID)
public class ModEntityTypes {
    /*
    @ObjectHolder(ModReferences.TECHVILLAGER) public static EntityType<TechVillagerEntity> TECHVILLAGERENTITY;
    @ObjectHolder(ModReferences.TECHGHOUL) public static EntityType<TechGhoulEntity> TECHGHOULENTITY;
    @ObjectHolder(ModReferences.TECHPENGUIN) public static EntityType<TechPenguinEntity> TECHPENGUINENTITY;

    public static void registerAll(IForgeRegistry<EntityType<?>> registry)
    {
        registry.register(make(ModReferences.TECHVILLAGER, TechVillagerEntity::new, EntityClassification.MISC, factory ->
                factory.sized(0.6F, 1.95F)
                        .setUpdateInterval(3)
                        .setTrackingRange(16)
                        .setShouldReceiveVelocityUpdates(true)));

        registry.register(make(ModReferences.TECHGHOUL, TechGhoulEntity::new, EntityClassification.MISC, factory ->
                factory.sized(0.9F, 1.8F)));

        registry.register(make(ModReferences.TECHPENGUIN, TechPenguinEntity::new, EntityClassification.MISC, factory ->
                factory.sized(0.5F, 1.5F)));
    }

    static <T extends Entity> EntityType<T> make(String registryName, EntityType.IFactory<T> factory,
            EntityClassification classification, Consumer<EntityType.Builder<T>> customizer)
    {
        EntityType.Builder<T> builder = EntityType.Builder.of(factory, classification);
        customizer.accept(builder);
        EntityType<T> type = builder.build(registryName);
        type.setRegistryName(registryName);
        return type;
    }
    */
}
