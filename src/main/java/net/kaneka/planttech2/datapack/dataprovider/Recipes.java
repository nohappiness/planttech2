package net.kaneka.planttech2.datapack.dataprovider;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;

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
