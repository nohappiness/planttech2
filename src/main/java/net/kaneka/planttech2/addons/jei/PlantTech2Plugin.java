package net.kaneka.planttech2.addons.jei;

import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.addons.jei.chipalyzer.ChipalyzerCategory;
import net.kaneka.planttech2.addons.jei.compressor.CompressorCategory;
import net.kaneka.planttech2.addons.jei.crossbreeding.CrossbreedingCategory;
import net.kaneka.planttech2.addons.jei.infuser.InfuserCategory;
import net.minecraft.util.ResourceLocation;
import mezz.jei.api.IModPlugin;

@JeiPlugin
public class PlantTech2Plugin implements IModPlugin
{
	private CompressorCategory compressorCategory;
	private CrossbreedingCategory crossbreedingCategory; 
	private InfuserCategory infuserCategory; 
	private ChipalyzerCategory chipalyzerCategory; 
	
	@Override
	public ResourceLocation getPluginUid() 
	{
		return new ResourceLocation(ModIds.JEI_ID, "planttech2");
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		compressorCategory = new CompressorCategory(guiHelper);
		crossbreedingCategory = new CrossbreedingCategory(guiHelper); 
		infuserCategory = new InfuserCategory(guiHelper); 
		chipalyzerCategory = new ChipalyzerCategory(guiHelper); 
		registration.addRecipeCategories(
			compressorCategory, 
			crossbreedingCategory, 
			infuserCategory, 
			chipalyzerCategory
		);
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(RecipeGetter.getCompressorRecipes(), new ResourceLocation(PlantTechMain.MODID, "compressor"));
		registration.addRecipes(RecipeGetter.getCrossbreedingRecipes(), new ResourceLocation(PlantTechMain.MODID, "crossbreeding"));
		registration.addRecipes(RecipeGetter.getInfuserRecipes(), new ResourceLocation(PlantTechMain.MODID, "infuser"));
		registration.addRecipes(RecipeGetter.getChipalyzerRecipes(), new ResourceLocation(PlantTechMain.MODID, "chipalyzer"));
	}
}
