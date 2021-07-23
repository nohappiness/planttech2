package net.kaneka.planttech2.datagen.recipes;

import java.util.function.Consumer;

import net.kaneka.planttech2.blocks.Hedge;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.util.IItemProvider;

import static net.kaneka.planttech2.registries.ModItems.*;
import static net.minecraft.world.item.Items.*;

public class ItemRecipeProvider extends RecipeProvider
{
	public ItemRecipeProvider(DataGenerator gen)
	{
		super(gen);
	}

	private Consumer<IFinishedRecipe> cons;

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> cons)
	{
		this.cons = cons;
		/*ShapedRecipeBuilder
		.shapedRecipe(ModBlocks.DARK_CRYSTAL_DOOR)
		.patternLine("XXX")
		.patternLine(" X ")
		.patternLine("XXX")
		.key('X', ModBlocks.CABLE)
		.addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
		.build(cons);*/
		registerHedgeRecipes(cons);
		registerAuraCoreRecipes();
	}
	
	private void registerHedgeRecipes(Consumer<IFinishedRecipe> cons)
	{
		for(Hedge block: ModBlocks.HEDGE_BLOCKS)
		{
			ShapedRecipeBuilder.shaped(block, 6)
			.pattern(" A ")
			.pattern(" A ")
			.pattern("BCB")
			.define('A', block.getLeaves())
			.define('B', block.getWood())
			.define('C', block.getSoil())
			.unlockedBy("leaves", InventoryChangeTrigger.Instance.hasItems(block.getLeaves()))
			.save(cons);
		}
	}

	private void registerAuraCoreRecipes()
	{
		makeTieredAuraCore(GLOWSTONE, AURA_CORE_LIGHT_DECREASE_I, AURA_CORE_LIGHT_DECREASE_II, AURA_CORE_LIGHT_DECREASE_III);
		makeTieredAuraCore(WATER_BUCKET, AURA_CORE_WATER_RANGE_DECREASE_I, AURA_CORE_WATER_RANGE_DECREASE_II, AURA_CORE_WATER_RANGE_DECREASE_III);
		makeAuraCore(AURA_CORE_TEMPERATURE_EXTREME_COLD, BLUE_ICE, PLANTIUM_INGOT);
		makeAuraCore(AURA_CORE_TEMPERATURE_COLD, SNOW_BLOCK, PLANTIUM_INGOT);
		makeAuraCore(AURA_CORE_TEMPERATURE_NORMAL, GRASS_BLOCK, PLANTIUM_INGOT);
		makeAuraCore(AURA_CORE_TEMPERATURE_WARM, NETHERRACK, PLANTIUM_INGOT);
		makeAuraCore(AURA_CORE_TEMPERATURE_EXTREME_WARM, LAVA_BUCKET, PLANTIUM_INGOT);
		// These two are not yet implemented and not yet decided to remove or keep
		//		makeTieredAuraCore(Items.BONE_MEAL, ModItems.AURA_CORE_FERTILITY_INCREASE_I, ModItems.AURA_CORE_FERTILITY_INCREASE_II, ModItems.AURA_CORE_FERTILITY_INCREASE_III);
		//		makeTieredAuraCore(ModItems.FERTILIZER_TIER_1, ModItems.AURA_CORE_PRODUCTIVITY_INCREASE_I, ModItems.AURA_CORE_PRODUCTIVITY_INCREASE_II, ModItems.AURA_CORE_PRODUCTIVITY_INCREASE_III);
	}

	private void makeTieredAuraCore(IItemProvider ingredient, IItemProvider... cores)
	{
		makeAuraCore(cores[0], ingredient, PLANTIUM_INGOT, PLANTIUM_INGOT);
		for (int i = 1; i < cores.length; i++)
			makeAuraCore(cores[i], cores[i - 1], ingredient, cores[i - 1]);
	}

	private void makeAuraCore(IItemProvider product, IItemProvider middle, IItemProvider side)
	{
		makeAuraCore(product, middle, side, middle);
	}

	private void makeAuraCore(IItemProvider product, IItemProvider middle, IItemProvider side, IItemProvider criterion)
	{
		ShapedRecipeBuilder.shaped(product)
				.pattern(" S ")
				.pattern("SMS")
				.pattern(" S ")
				.define('S', side)
				.define('M', middle)
				.unlockedBy("plantium", has(criterion))
				.save(cons);
	}
}
