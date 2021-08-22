package net.kaneka.planttech2.items;

import net.kaneka.planttech2.PlantTechClient;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GuideItem extends Item
{

    public GuideItem()
    {
	super(new Item.Properties().defaultDurability(1).tab(ModCreativeTabs.MAIN));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn)
    {
        if(level.isClientSide()) PlantTechClient.openGuideScreen(this);
        //DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> PlantTechClient.openGuideScreen(this));
        //NetworkHooks.openGui(playerIn, );
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
        return super.use(level, playerIn, handIn);
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
