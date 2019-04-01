package net.kaneka.planttech2.handlers;

import net.kaneka.planttech2.PlantTechMain;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTableHandler
{
    static public void register()
    {
	LootTableList.register(new ResourceLocation(PlantTechMain.MODID,"guide"));
    }
}
