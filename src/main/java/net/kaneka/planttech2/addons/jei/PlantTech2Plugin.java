package net.kaneka.planttech2.addons.jei;

import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.addons.jei.carver.CarverCategory;
import net.kaneka.planttech2.addons.jei.compressor.CompressorCategory;
import net.kaneka.planttech2.addons.jei.crossbreeding.CrossbreedingCategory;
import net.kaneka.planttech2.addons.jei.infuser.InfuserCategory;
import net.kaneka.planttech2.addons.jei.machine_growing.MachineGrowingCategory;
import net.kaneka.planttech2.addons.jei.machinebulbreprocessor.MachinebulbReprocessorCategory;
import net.minecraft.util.ResourceLocation;
import mezz.jei.api.IModPlugin;

@JeiPlugin
public class PlantTech2Plugin implements IModPlugin
{
	private CompressorCategory compressorCategory;
	private CrossbreedingCategory crossbreedingCategory; 
	private InfuserCategory infuserCategory; 
	private CarverCategory carverCategory; 
	private MachinebulbReprocessorCategory machinebulbReprocessorCategory; 
	private MachineGrowingCategory machineGrowingCategory; 
	
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
		carverCategory = new CarverCategory(guiHelper); 
		machinebulbReprocessorCategory = new MachinebulbReprocessorCategory(guiHelper); 
		machineGrowingCategory = new MachineGrowingCategory(guiHelper); 
		registration.addRecipeCategories(
			compressorCategory, 
			crossbreedingCategory, 
			infuserCategory, 
			carverCategory,
			machinebulbReprocessorCategory,
			machineGrowingCategory 
		);
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(RecipeGetter.getCompressorRecipes(), new ResourceLocation(PlantTechMain.MODID, "compressor"));
		registration.addRecipes(RecipeGetter.getCrossbreedingRecipes(), new ResourceLocation(PlantTechMain.MODID, "crossbreeding"));
		registration.addRecipes(RecipeGetter.getInfuserRecipes(), new ResourceLocation(PlantTechMain.MODID, "infuser"));
		registration.addRecipes(RecipeGetter.getCarverRecipes(), new ResourceLocation(PlantTechMain.MODID, "carver"));
		registration.addRecipes(RecipeGetter.getMachinebulbReprocessorRecipes(), new ResourceLocation(PlantTechMain.MODID, "machinebulbreprocessor"));
		registration.addRecipes(RecipeGetter.getMachineGrowingRecipes(), new ResourceLocation(PlantTechMain.MODID, "machine_growing"));
	}
}
