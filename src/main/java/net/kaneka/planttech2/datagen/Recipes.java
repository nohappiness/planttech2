package net.kaneka.planttech2.datagen;

import java.util.function.Consumer;

import net.kaneka.planttech2.blocks.Hedge;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;

public class Recipes extends RecipeProvider
{
	public Recipes(DataGenerator gen)
	{
		super(gen);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> cons)
	{
		/*ShapedRecipeBuilder
		.shapedRecipe(ModBlocks.DARK_CRYSTAL_DOOR)
		.patternLine("XXX")
		.patternLine(" X ")
		.patternLine("XXX")
		.key('X', ModBlocks.CABLE)
		.addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
		.build(cons);*/
		registerHedgeRecipes(cons);
	}
	
	private void registerHedgeRecipes(Consumer<IFinishedRecipe> cons)
	{
		for(Hedge block: ModBlocks.HEDGE_BLOCKS)
		{
			ShapedRecipeBuilder.shapedRecipe(block, 6)
			.patternLine(" A ")
			.patternLine(" A ")
			.patternLine("BCB")
			.key('A', block.getLeaves())
			.key('B', block.getWood())
			.key('C', block.getSoil())
			.addCriterion("leaves", InventoryChangeTrigger.Instance.forItems(block.getLeaves()))
			.build(cons);
		}
	}
}
