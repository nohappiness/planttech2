package net.kaneka.planttech2.datapack.dataprovider;

import java.util.function.Consumer;

import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
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
	}

}
