package net.kaneka.planttech2.datagen;

import com.mojang.datafixers.util.Pair;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.AlternativesLootEntry;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTables extends LootTableProvider
{
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();

    public LootTables(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
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

        ModBlocks.HEDGE_BLOCKS.forEach(this::standardDropTable);
        return tables;
    }

    void silkFortuneBlockTable(Block b, IItemProvider item)
    {
        LootPool.Builder pool = LootPool.lootPool();
        pool.setRolls(ConstantRange.exactly(1));

        StandaloneLootEntry.Builder<?> silk = ItemLootEntry.lootTableItem(b)
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(
                        Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))));
        StandaloneLootEntry.Builder<?> fortune = ItemLootEntry.lootTableItem(item).apply(ExplosionDecay.explosionDecay())
                .apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE));

        pool.add(AlternativesLootEntry.alternatives(silk, fortune));

        blockTable(b, LootTable.lootTable().withPool(pool));
    }

    void standardDropTable(Block b)
    {
        blockTable(b, LootTable.lootTable().withPool(createStandardDrops(b)));
    }

    void blockTable(Block b, LootTable.Builder lootTable)
    {
        addTable(b.getLootTable(), lootTable, LootParameterSets.BLOCK);
    }

    void addTable(ResourceLocation path, LootTable.Builder lootTable, LootParameterSet paramSet)
    {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), paramSet));
    }

    LootPool.Builder createStandardDrops(IItemProvider itemProvider)
    {
        return LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(SurvivesExplosion.survivesExplosion())
                .add(ItemLootEntry.lootTableItem(itemProvider));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker)
    {
        map.forEach(
                (p_218436_2_, p_218436_3_) -> LootTableManager.validate(validationtracker, p_218436_2_, p_218436_3_));
    }
}
