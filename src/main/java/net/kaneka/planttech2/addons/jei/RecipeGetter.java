package net.kaneka.planttech2.addons.jei;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.addons.jei.crossbreeding.CrossbreedingRecipe;
import net.kaneka.planttech2.librarys.CropList;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.librarys.utils.Parents;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.ChipalyzerRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;

public class RecipeGetter
{

	public static List<CompressorRecipe> getCompressorRecipes()
	{
		List<CompressorRecipe> results = new ArrayList<CompressorRecipe>();
		ClientWorld world = Minecraft.getInstance().world;
		RecipeManager recipeManager = world.getRecipeManager();
		Iterator<IRecipe<?>> it = recipeManager.getRecipes().iterator();
		while (it.hasNext())
		{
			IRecipe<?> recipe = it.next();
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
		ClientWorld world = Minecraft.getInstance().world;
		RecipeManager recipeManager = world.getRecipeManager();
		Iterator<IRecipe<?>> it = recipeManager.getRecipes().iterator();
		while (it.hasNext())
		{
			IRecipe<?> recipe = it.next();
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
		CropList croplist = PlantTechMain.croplist;
		for (CropListEntry entry : croplist.getAllEntries())
		{
			if (!entry.isBlacklisted())
			{
				if (entry.hasParents())
				{
					ItemStack output = entry.getMainSeed();
					for (Parents parent : entry.getParents())
					{
						results.add(new CrossbreedingRecipe(output, croplist.getEntryByName(parent.getParent(0)).getMainSeed(),
						        croplist.getEntryByName(parent.getParent(1)).getMainSeed()));
					}
				}
			}
		}
		return results;
	}
	
	public static List<ChipalyzerRecipe> getChipalyzerRecipes()
	{
		List<ChipalyzerRecipe> results = new ArrayList<ChipalyzerRecipe>();
		ClientWorld world = Minecraft.getInstance().world;
		RecipeManager recipeManager = world.getRecipeManager();
		Iterator<IRecipe<?>> it = recipeManager.getRecipes().iterator();
		while (it.hasNext())
		{
			IRecipe<?> recipe = it.next();
			if (recipe.getType() == ModRecipeTypes.CHIPALYZER)
			{
				ChipalyzerRecipe chipalyzerrecipe = (ChipalyzerRecipe) recipe;
				results.add(chipalyzerrecipe);
			}
		}
		return results;
	}

}
