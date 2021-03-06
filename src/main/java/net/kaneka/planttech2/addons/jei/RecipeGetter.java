package net.kaneka.planttech2.addons.jei;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.addons.jei.carver.CarverRecipe;
import net.kaneka.planttech2.addons.jei.crossbreeding.CrossbreedingRecipe;
import net.kaneka.planttech2.addons.jei.machine_growing.MachineGrowingRecipe;
import net.kaneka.planttech2.addons.jei.machinebulbreprocessor.MachinebulbReprocessorRecipe;
import net.kaneka.planttech2.blocks.GrowingBlock;
import net.kaneka.planttech2.crops.CropEntry;
import net.kaneka.planttech2.crops.CropList;
import net.kaneka.planttech2.crops.ParentPair;
import net.kaneka.planttech2.items.MachineBulbItem;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class RecipeGetter
{
	public static List<ChipalyzerRecipe> getChipalyzerRecipes() {
		List<ChipalyzerRecipe> results = new ArrayList<>();
		ClientLevel level = Minecraft.getInstance().level;
		RecipeManager recipeManager = level.getRecipeManager();
		Iterator<Recipe<?>> it = recipeManager.getRecipes().iterator();
		while (it.hasNext()) {
			Recipe<?> recipe = it.next();
			if (recipe.getType() == ModRecipeTypes.CHIPALYZER) {
				ChipalyzerRecipe chipalyzerrecipe = (ChipalyzerRecipe) recipe;
				results.add(chipalyzerrecipe);
			}
		}
		return results;
	}

	public static List<CompressorRecipe> getCompressorRecipes()
	{
		List<CompressorRecipe> results = new ArrayList<CompressorRecipe>();
		ClientLevel level = Minecraft.getInstance().level;
		RecipeManager recipeManager = level.getRecipeManager();
		Iterator<Recipe<?>> it = recipeManager.getRecipes().iterator();
		while (it.hasNext())
		{
			Recipe<?> recipe = it.next();
			if (recipe.getType() == ModRecipeTypes.COMPRESSING)
			{
				CompressorRecipe compressorrecipe = (CompressorRecipe) recipe;
				results.add(compressorrecipe);
			}
		}
		return results;
	}
	
	public static List<InfuserRecipe> getInfuserRecipes()
	{
		List<InfuserRecipe> results = new ArrayList<InfuserRecipe>();
		ClientLevel level = Minecraft.getInstance().level;
		RecipeManager recipeManager = level.getRecipeManager();
		Iterator<Recipe<?>> it = recipeManager.getRecipes().iterator();
		while (it.hasNext())
		{
			Recipe<?> recipe = it.next();
			if (recipe.getType() == ModRecipeTypes.INFUSING)
			{
				InfuserRecipe compressorrecipe = (InfuserRecipe) recipe;
				results.add(compressorrecipe);
			}
		}
		return results;
	}

	public static List<CrossbreedingRecipe> getCrossbreedingRecipes()
	{
		List<CrossbreedingRecipe> results = new ArrayList<CrossbreedingRecipe>();
		CropList croplist = PlantTechMain.getCropList();
		for (CropEntry entry : croplist.values())
		{
			if (entry.getConfiguration().isEnabled())
			{
				if (!entry.getParents().isEmpty())
				{
					ItemStack output = entry.getPrimarySeed().getItemStack();
					for (ParentPair parent : entry.getParents())
					{
						results.add(new CrossbreedingRecipe(output, croplist.getByName(parent.getFirstParent()).getPrimarySeed().getItemStack(),
						        croplist.getByName(parent.getSecondParent()).getPrimarySeed().getItemStack()));
					}
				}
			}
		}
		return results;
	}
	
	public static List<CarverRecipe> getCarverRecipes()
	{
		List<CarverRecipe> results = new ArrayList<CarverRecipe>();
		results.add(new CarverRecipe(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(ModBlocks.MACHINESHELL_IRON)));
		results.add(new CarverRecipe(new ItemStack(ModBlocks.PLANTIUM_BLOCK), new ItemStack(ModBlocks.MACHINESHELL_PLANTIUM)));
		return results;
	}
	
	public static List<MachinebulbReprocessorRecipe> getMachinebulbReprocessorRecipes()
	{
		List<MachinebulbReprocessorRecipe> results = new ArrayList<MachinebulbReprocessorRecipe>();
		for(Supplier<MachineBulbItem> bulb: ModItems.MACHINE_BULBS)
		{
			results.add(new MachinebulbReprocessorRecipe(bulb.get().getTier(), new ItemStack(bulb.get()), bulb.get().getNeededBiomass()));
		}
		return results;
	}

	public static List<MachineGrowingRecipe> getMachineGrowingRecipes()
	{
		List<MachineGrowingRecipe> results = new ArrayList<MachineGrowingRecipe>();
		for(Supplier<MachineBulbItem> bulb: ModItems.MACHINE_BULBS)
		{
			results.add(new MachineGrowingRecipe(new ItemStack(bulb.get()), new ItemStack(bulb.get().getHull()), new ItemStack(((GrowingBlock) bulb.get().getMachine()).blockSupplier.get())));
		}
		return results;
	}

}
