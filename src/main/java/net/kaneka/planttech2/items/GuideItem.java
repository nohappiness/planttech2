package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechClient;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class GuideItem extends Item
{

    public GuideItem()
    {
	super(new Item.Properties().defaultMaxDamage(1).group(ModCreativeTabs.MAIN));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> PlantTechClient.openGuideScreen(this));
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
