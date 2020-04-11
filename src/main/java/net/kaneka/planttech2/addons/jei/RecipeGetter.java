package net.kaneka.planttech2.addons.jei;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.addons.jei.carver.CarverRecipe;
import net.kaneka.planttech2.addons.jei.crossbreeding.CrossbreedingRecipe;
import net.kaneka.planttech2.addons.jei.machine_growing.MachineGrowingRecipe;
import net.kaneka.planttech2.addons.jei.machinebulbreprocessor.MachinebulbReprocessorRecipe;
import net.kaneka.planttech2.items.MachineBulbItem;
import net.kaneka.planttech2.librarys.CropList;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.librarys.utils.Parents;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.recipes.recipeclasses.CompressorRecipe;
import net.kaneka.planttech2.recipes.recipeclasses.InfuserRecipe;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.block.Blocks;
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
		for(MachineBulbItem bulb: ModItems.MACHINEBULBS)
		{
			results.add(new MachinebulbReprocessorRecipe(bulb.getTier(), new ItemStack(bulb), bulb.getNeededBiomass()));
		}
		return results;
	}
	
	public static List<MachineGrowingRecipe> getMachineGrowingRecipes()
	{
		List<MachineGrowingRecipe> results = new ArrayList<MachineGrowingRecipe>();
		for(MachineBulbItem bulb: ModItems.MACHINEBULBS)
		{
			results.add(new MachineGrowingRecipe(new ItemStack(bulb), new ItemStack(bulb.getHull()), new ItemStack(bulb.getMachine())));
		}
		return results;
	}

}
