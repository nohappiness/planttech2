package net.kaneka.planttech2.registries;

import net.minecraft.resources.ResourceLocation;

import static net.kaneka.planttech2.PlantTechMain.MODID;

public class ModReferences
{
	public static final String COMPRESSORCONTAINER = MODID + ":compressorcontainer"; 
	public static final String DNACLEANERCONTAINER = MODID + ":dnacleanercontainer"; 
	public static final String DNACOMBINERCONTAINER = MODID + ":dnacombinercontainer"; 
	public static final String DNAEXTRACTORCONTAINER = MODID + ":dnaextractorcontainer";
	public static final String DNAREMOVERCONTAINER = MODID + ":dnaremovercontainer";
	public static final String ENERGYSTORAGECONTAINER = MODID + ":energystoragecontainer";
	public static final String ENERGYSUPPLIERCONTAINER = MODID + ":energysuppliercontainer";
	public static final String IDENTIFIERCONTAINER = MODID + ":identifiercontainer";
	public static final String INFUSERCONTAINER = MODID + ":infusercontainer";
	public static final String UPGRADEABLEITEMCONTAINER = MODID + ":upgradeableitemcontainer";
	public static final String MEGAFURNACECONTAINER = MODID + ":megafurnacecontainer";
	public static final String PLANTFARMCONTAINER = MODID + ":plantfarmcontainer";
	public static final String SEEDCONSTRUCTORCONTAINER = MODID + ":seedconstructorcontainer";
	public static final String SEEDQUEEZERCONTAINER = MODID + ":seedsqueezercontainer";
	public static final String SOLARGENERATORCONTAINER = MODID + ":solargeneratorcontainer"; 
	public static final String CHIPALYZERCONTAINER = MODID + ":chipalyzercontainer"; 
	public static final String MACHINEBULBREPROCESSORCONTAINER = MODID + ":machinebulbreprocessorcontainer"; 
	public static final String TELEPORTERBLOCKCONTAINER = MODID + ":teleporterblockcontainer";
	public static final String TELEPORTERITEMCONTAINER = MODID + ":teleporteritemcontainer";
	public static final String TECHVILLAGERCONTAINER = MODID + ":techvillagercontainer";
	public static final String CROPAURAGENERATORCONTAINER = MODID + ":cropaurageneratorcontainer";

	//PLANTTOPIA
	public static final String PLANTTOPIA = MODID + ":planttopia"; 
	public static final ResourceLocation PLANTTOPIA_RESLOC = new ResourceLocation(PLANTTOPIA);
	//BIOMES
	public static final String BEE_FOREST = "bee_forest";
	public static final String CHORUS_FOREST = "chorus_forest";
	public static final String DARK_WETLANDS = "dark_wetlands";
	public static final String DEAD_FOREST = "dead_forest";
	public static final String DREAM_FOREST = "dream_forest";
	public static final String DRIED_LAKE = "dried_lake";
	public static final String ENERGIZED_FOREST = "energized_forest";
	public static final String FLOWER_HILLS = "flower_hills";
	public static final String FLOWER_MEADOWS = "flower_meadows";
	public static final String FLOWER_MOUNTAINS = "flower_mountains";
	public static final String ICY_CLIFFS = "icy_cliffs";
	public static final String ICY_MEADOWS = "icy_meadows";
	public static final String LAKE = "lake";
	public static final String LLAMA_MEADOW = "llama_meadow";
	public static final String MEADOWS = "meadows";
	public static final String MUSHROOM_FOREST = "mushroom_forest";
	public static final String MUSHROOM_HILLS = "mushroom_hills";
	public static final String NIGHTMARE_FOREST = "nightmare_forest";
	public static final String PUMPKIN_FOREST = "pumpkin_forest";
	public static final String RADIATED_WASTELANDS = "radiated_wastelands";
	public static final String RADIATED_WETLANDS = "radiated_wetlands";
	public static final String RIVER = "river";
	public static final String VULCANO = "vulcano";
	public static final String WASTELAND_MESA = "wasteland_mesa";
	public static final String WETLANDS = "wetlands";



	public static final String TECHVILLAGE = MODID + ":techvillage"; 
	
	public static final String TECHVILLAGER = MODID + ":techvillager"; 
	public static final String TECHVILLAGERTRADESERIALIZER = MODID + ":techvillagertradeserializer";
	public static final String TECHGHOUL = MODID + ":techghoul";
	public static final String TECHPENGUIN = MODID + ":techpenguin";

	public static final ResourceLocation TECHVILLAGERTRUSTCAP = new ResourceLocation(MODID, "techvillagertrustcap");
	public static final ResourceLocation RADIATIONEFFECTCAP = new ResourceLocation(MODID, "radiationeffectcap");
	public static final ResourceLocation PLAYERRENDERRGBCAP = new ResourceLocation(MODID, "playerrenderrgbcap");
	public static final ResourceLocation BIOMASSFLUIDENERGYCAP = new ResourceLocation(MODID, "biomassfluidenergycap");
	
	
	public static ResourceLocation prefix(String name)
	{
		return new ResourceLocation(MODID, name);
	}

}
