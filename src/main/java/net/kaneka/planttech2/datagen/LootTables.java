package net.kaneka.planttech2.datagen;

import com.mojang.datafixers.util.Pair;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTables extends LootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = new ArrayList<>();

    public LootTables(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables()
    {
        tables.clear();

        standardDropTable(ModBlocks.CABLE);
        standardDropTable(ModBlocks.CARVER);
        standardDropTable(ModBlocks.CHIPALYZER);
        standardDropTable(ModBlocks.COMPRESSOR);
        standardDropTable(ModBlocks.CROPBARS);
        standardDropTable(ModBlocks.DANCIUM_BLOCK);
        standardDropTable(ModBlocks.DNA_CLEANER);
        standardDropTable(ModBlocks.DNA_COMBINER);
        standardDropTable(ModBlocks.DNA_EXTRACTOR);
        standardDropTable(ModBlocks.DNA_REMOVER);
        //        standardDropTable(ModBlocks.ELECTRIC_FENCE);
        //        standardDropTable(ModBlocks.ELECTRIC_FENCE_TOP);
        //        standardDropTable(ModBlocks.ELECTRIC_FENCE_GATE);
        standardDropTable(ModBlocks.ENERGYSTORAGE);
        standardDropTable(ModBlocks.ENERGY_SUPPLIER);
        //        standardDropTable(ModBlocks.FIBRE_FENCE);
        standardDropTable(ModBlocks.IDENTIFIER);
        standardDropTable(ModBlocks.INFUSER);
        standardDropTable(ModBlocks.KANEKIUM_BLOCK);
        standardDropTable(ModBlocks.KINNOIUM_BLOCK);
        standardDropTable(ModBlocks.LENTHURIUM_BLOCK);
        standardDropTable(ModBlocks.MACHINEBULBREPROCESSOR);
        standardDropTable(ModBlocks.MACHINESHELL_IRON);
        standardDropTable(ModBlocks.MACHINESHELL_PLANTIUM);
        standardDropTable(ModBlocks.MEGAFURNACE);
        standardDropTable(ModBlocks.PLANTFARM);
        standardDropTable(ModBlocks.PLANTIUM_BLOCK);
        standardDropTable(ModBlocks.SEEDCONSTRUCTOR);
        standardDropTable(ModBlocks.SEEDSQUEEZER);
        standardDropTable(ModBlocks.SOLARGENERATOR);
        //        standardDropTable(ModBlocks.PLANTTOPIA_TELEPORTER);
        //        standardDropTable(ModBlocks.PLANTTOPIA_TELEPORTER_END);
        standardDropTable(ModBlocks.UNIVERSAL_SOIL);
        standardDropTable(ModBlocks.UNIVERSAL_SOIL_INFUSED);

        silkFortuneBlockTable(ModBlocks.DARK_CRYSTAL_ORE, ModItems.DARK_CRYSTAL);
        silkFortuneBlockTable(ModBlocks.WHITE_CRYSTAL_ORE, ModItems.WHITE_CRYSTAL);

        silkBlockTable(ModBlocks.INFUSED_ICE);
        silkBlockTable(ModBlocks.INFUSED_PACKED_ICE);
        silkBlockTable(ModBlocks.INFUSED_BLUE_ICE);
        silkBlockTable(ModBlocks.BLACK_ICE);

        standardDropTable(ModBlocks.INFUSED_STONE);
        standardDropTable(ModBlocks.INFUSED_COBBLESTONE);
        ModBlocks.HEDGE_BLOCKS.forEach(this::standardDropTable);
        return tables;
    }

    void silkBlockTable(Block b)
    {
        LootPool.Builder pool = LootPool.lootPool();
        pool.setRolls(ConstantValue.exactly(1));

        LootPoolSingletonContainer.Builder<?> silk = LootItem.lootTableItem(b)
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(
                        Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))));

        pool.add(AlternativesEntry.alternatives(silk));

        blockTable(b, LootTable.lootTable().withPool(pool));
    }

    void silkFortuneBlockTable(Block b, ItemLike item)
    {
        LootPool.Builder pool = LootPool.lootPool();
        pool.setRolls(ConstantValue.exactly(1));

        LootPoolSingletonContainer.Builder<?> silk = LootItem.lootTableItem(b)
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(
                        Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))));
        LootPoolSingletonContainer.Builder<?> fortune = LootItem.lootTableItem(item).apply(ApplyExplosionDecay.explosionDecay())
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE));

        pool.add(AlternativesEntry.alternatives(silk, fortune));

        blockTable(b, LootTable.lootTable().withPool(pool));
    }

    void standardDropTable(Block b)
    {
        blockTable(b, LootTable.lootTable().withPool(createStandardDrops(b)));
    }

    void blockTable(Block b, LootTable.Builder lootTable)
    {
        addTable(b.getLootTable(), lootTable, LootContextParamSets.BLOCK);
    }

    void addTable(ResourceLocation path, LootTable.Builder lootTable, LootContextParamSet paramSet)
    {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), paramSet));
    }

    LootPool.Builder createStandardDrops(ItemLike itemProvider)
    {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(itemProvider));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker)
    {
        map.forEach(
                (p_218436_2_, p_218436_3_) -> net.minecraft.world.level.storage.loot.LootTables.validate(validationtracker, p_218436_2_, p_218436_3_));
    }
}
