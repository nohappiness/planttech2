package net.kaneka.planttech2.items;

import net.kaneka.planttech2.gui.GuiGuideGeneticEngineering;
import net.kaneka.planttech2.gui.GuiGuideOverview;
import net.kaneka.planttech2.gui.GuiGuidePlants;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGuide extends ItemBase
{

    public ItemGuide(String name)
    {
	super("guide_" + name, new Item.Properties().defaultMaxDamage(1).group(ModCreativeTabs.groupmain));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
	if (worldIn.isRemote)
	{
	    Screen guiscreen = new GuiGuideOverview(); 
	    if(this == ModItems.GUIDE_PLANTS)
	    {
		guiscreen = new GuiGuidePlants();
	    }
	    else if(this == ModItems.GUIDE_GENETIC_ENGINEERING)
	    {
		guiscreen = new GuiGuideGeneticEngineering();
	    }
	    Minecraft.getInstance().displayGuiScreen(guiscreen);
	}
	/*
	if(!worldIn.isRemote)
	{
        	IResourceManager rm = worldIn.getServer().getResourceManager(); 
        	for(ResourceLocation file : rm.getAllResourceLocations("pt2_crops", f -> f.endsWith(".json")))
        	{
        	   System.out.println(file.toString());
        	}
        	PlantTechMain.LOGGER.info("end");
	}
	*/
		
		//Gson gson = new GsonBuilder().registerTypeAdapter(CropListEntryConfiguration.class, new CropListEntryConfiguration.Deserializer()).create();
		//CropListEntryConfiguration test = gson.fromJson(new InputStreamReader(rm.getResource(new ResourceLocation("example", "pt2_crops")).getInputStream()), CropListEntryConfiguration.class);
		
		//CropListEntryConfiguration.Serializer.readFromJson(new ResourceLocation("planttech2","example"),  gson.)
	   
	
	return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count)
    {
	super.onUsingTick(stack, player, count);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
	return false;
    }

}
