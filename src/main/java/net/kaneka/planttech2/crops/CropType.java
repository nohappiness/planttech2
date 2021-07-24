package net.kaneka.planttech2.crops;

public enum CropType
{
    ABYSSALNITE("abyssalnite", 0x45005f),
    ADAMANTINE("adamantine", 0xd53c00),
    ALLIUM("allium", 0xa65ee1, false),
    ALUMINUM("aluminum", 0xb4b4b4),
    ALUMINUM_BRASS("aluminum_brass", 0xc8b300),
    ALUMITE("alumite", 0xe789ff),
    AMBER("amber", 0xe09e00),
    APATITE("apatite", 0x00b3e0),
    AQUAMARINE("aquamarine", 0x00c3d4),
    ARDITE("ardite", 0x88471b),
    AWAKENED_DRACONIUM("awakened_draconium", 0xbf4c00),
    AZURE_BLUET("azure_bluet", 0xd6e8e8, false),
    BAMBOO("bamboo", 0x5d8824, false),
    BASALT("basalt", 0x424242),
    BEAST("beast", 0x6a6965),
    BEETROOTS("beetroots", 0xbf2529, false),
    BLACK_QUARTZ("black_quartz", 0x202020),
    BLAZE("blaze", 0xfc9600),
    BLITZ("blitz", 0xfffdcc),
    BLIZZ("blizz", 0xc3d3ff),
    BLUE_TOPAZ("blue_topaz", 0x6089ff),
    BLUE_ORCHID("blue_orchid", 0x3066ff, false),
    BRASS("brass", 0xeaeaea),
    BRONZE("bronze", 0x804500),
    CACTUS("cactus", 0x527d26, false),
    CARROT("carrot", 0xe38a1d, false),
    CERTUS_QUARTZ("certus_quartz", 0x9fc3ff),
    CHICKEN("chicken", 0xe2e2e2),
    CHIMERITE("chimerite", 0xaeffa6),
    CHORUS("chorus", 0x8f718f, false),
    CHROME("chrome", 0xFFFFFF),
    COAL("coal", 0x3f3f3f),
    COBALT("cobalt", 0x1d5791),
    COCOA_BEAN("cocoa_bean", 0xb97335, false),
    COLD_IRON("cold_iron", 0x72a7ff),
    COMPRESSED_IRON("compressed_iron", 0xbdbdbd),
    CONDUCTIVE_IRON("conductive_iron", 0x629dff),
    CONSTANTAN("constantan", 0xb1ab00),
    COPPER("copper", 0xb47800),
    CORALIUM("coralium", 0x00646a),
    CORNFLOWER("cornflower", 0x466aeb, false),
    COW("cow", 0x443626),
    CREEPER("creeper", 0x41b736),
    DANCIUM("dancium", 0xeb8c14),
    DANDELION("dandelion", 0xfed639, false),
    DARK_GEM("dark_gem", 0x4e4e4e),
    DARK_STEEL("dark_steel", 0x8d7aff),
    DESH("desh", 0x535353),
    DIAMOND("diamond", 0x77cefb),
    DIRT("dirt", 0x593d29),
    DRACONIUM("draconium", 0x7600ba),
    DREADIUM("dreadium", 0xba000d),
    DROWNED("drowned", 0x8ff1d7),
    ELECTRICAL_STEEL("electrical_steel", 0xb8b8b8),
    ELECTROTINE("electrotine", 0x005093),
    ELECTRUM("electrum", 0xfff158),
    ELEMENTIUM("elementium", 0xeb11ff),
    EMERALD("emerald", 0x17dd62),
    END_STEEL("end_steel", 0xfeffd6),
    ENDER_AMETHYST("ender_amethyst", 0xfd35ff),
    ENDER_BIOTITE("ender_biotite", 0x000000),
    ENDERDRAGON("enderdragon", 0x181818),
    ENDERIUM("enderium", 0x007b77),
    ENDERMAN("enderman", 0x181818),
    ENDSTONE("endstone", 0xf6fabd),
    ENERGETIC_ALLOY("energetic_alloy", 0x9cff32),
    FISH("fish", 0xbf841b),
    FLUIX_CRYSTAL("fluix_crystal", 0x6f0098),
    FLUXED_ELECTRUM("fluxed_electrum", 0xfffb87),
    GHAST("ghast", 0xf0f0f0),
    GLOWSTONE("glowstone", 0xfbda74),
    GLOWSTONE_INGOT("glowstone_ingot", 0xf6ed00),
    GOLD("gold", 0xf8af2b),
    GRAPHITE("graphite", 0x444444),
    GUARDIAN("guardian", 0x668980),
    HUSK("husk", 0x6a5d4a),
    ILLAGER("illager", 0x939999),
    INVAR("invar", 0xc3bc00),
    IRIDIUM("iridium", 0xcfcfcf),
    IRON("iron", 0xbc9980),
    KANEKIUM("kanekium", 0x572e8a),
    KELP("kelp", 0x5b8131, false),
    KINNOIUM("kinnoium", 0x246b2d),
    KNIGHTSLIME("knightslime", 0xfd5fff),
    LAPIS("lapis", 0x1044ac),
    LAVA("lava", 0xd14f0c),
    LEAD("lead", 0x326e99),
    LENTHURIUM("lenthurium", 0x2c8585),
    LILLY_OF_THE_VALLEY("lilly_of_the_valley", 0xe7e7e7, false),
    LITHIUM("lithium", 0xfffac4),
    LUMIUM("lumium", 0xfff282),
    MAGMA_CUBE("magma_cube", 0x330000),
    MAGNESIUM("magnesium", 0x615900),
    MALACHITE("malachite", 0x36bf53),
    MANASTEEL("manasteel", 0x3d8fff),
    MANYULLYN("manyullyn", 0x793393),
    MELON("melon", 0xa7ac1d, false),
    METEORIC_IRON("meteoric_iron", 0x8f845e),
    MITHRIL("mithril", 0xb7d7ff),
    MOONSTONE("moonstone", 0xeef6ff),
    MOOSHROOM("mooshroom", 0xa81012),
    MUSHROOM("mushroom", 0xe21212, false),
    MYCELIUM("mycelium", 0x736162),
    NETHER_WART("nether_wart", 0x831c20, false),
    NETHERRACK("netherrack", 0x652828),
    NEUTRONIUM("neutronium", 0x585858),
    NICKEL("nickel", 0x9f998c),
    OCTINE("octine", 0xffb400),
    ORANGE_TULIP("orange_tulip", 0xbd6a22, false),
    OSMIUM("osmium", 0xc6edff),
    OXEYE_DAISY("oxeye_daisy", 0xf5ba27, false),
    PANDA("panda", 0xf5ba27),
    PARROT("parrot", 0x18bdff),
    PERIDOT("peridot", 0x5fc859),
    PIG("pig", 0xf19e98),
    PINK_TULIP("pink_tulip", 0xe4aff4, false),
    PLANTIUM("plantium", 0x35a048),
    PLATINUM("platinum", 0xa2a2a2),
    POLARBEAR("polarbear", 0xf6f6f6),
    POPPY("poppy", 0xed302c, false),
    POTATO("potato", 0xc8a24b, false),
    PRISMARINE("prismarine", 0x5ea496),
    PULSATING_IRON("pulsating_iron", 0x75d970),
    PUMPKIN("pumpkin", 0xe38a1d, false),
    QUARTZ("quartz", 0xd4caba),
    QUICKSILVER("quicksilver", 0xd6ffff),
    RED_TULIP("red_tulip", 0xed302c, false),
    REDSTONE("redstone", 0xff0000),
    REDSTONE_ALLOY("redstone_alloy", 0xff0000),
    REFINED_OBSIDIAN("refined_obsidian", 0x62316d),
    ROCK_CRYSTAL("rock_crystal", 0xa6a6a6),
    RUBBER("rubber", 0x444444),
    RUBY("ruby", 0x980000),
    SALTPETER("saltpeter", 0x969696),
    SAND("sand", 0xdacfa3),
    SAPPHIRE("sapphire", 0x000a8e),
    SHEEP("sheep", 0xc09e86),
    SHULKER("shulker", 0x8e608e),
    SIGNALUM("signalum", 0x8e5700),
    SILICON("silicon", 0x777777),
    SILVER("silver", 0xdadada),
    SKELETON("skeleton", 0xbcbcbc),
    SKY_STONE("sky_stone", 0x383838),
    SLATE("slate", 0xFFFFFF),
    SLIME("slime", 0x59bd45),
    SLIMY_BONE("slimy_bone", 0x7b7b7b),
    SNOW("snow", 0xffffff),
    SOULARIUM("soularium", 0x443824),
    SOULSAND("soulsand", 0x49372c),
    SPIDER("spider", 0x605448),
    SPONGE("sponge", 0xcdce4a),
    SQUID("squid", 0xcdce4a),
    STAR_STEEL("star_steel", 0x171717),
    STARMETAL("starmetal", 0x22002f),
    STEEL("steel", 0x686868),
    STONE("stone", 0x616161),
    STRAY("stray", 0xacbabd),
    SUGARCANE("sugarcane", 0x82a859, false),
    SULFUR("sulfur", 0xb1ac27),
    SUNSTONE("sunstone", 0xc13b00),
    SYRMORITE("syrmorite", 0xc71eff),
    TANZANITE("tanzanite", 0xa951c6),
    TERRASTEEL("terrasteel", 0x32b100),
    THAUMIUM("thaumium", 0x8a00ff),
    TIN("tin", 0xaba78c),
    TITANIUM("titanium", 0xc6c6c6),
    TOPAZ("topaz", 0xffde29),
    TUNGSTEN("tungsten", 0x005a40),
    TURTLE("turtle", 0x388d3a),
    URANIUM("uranium", 0x3abd22),
    VALONITE("valonite", 0xcfa5d5),
    VIBRANT_ALLOY("vibrant_alloy", 0xbf7e00),
    VILLAGER("villager", 0xb57b67),
    VINE("vine", 0x1b5011, false),
    VINTEUM("vinteum", 0x5a81ff),
    VOID_METAL("void_metal", 0x000000),
    WATER("water", 0x2b5fff),
    WHEAT("wheat", 0xae19, false),
    WHITE_TULIP("white_tulip", 0xf7f7f7, false),
    WITCH("witch", 0xa39483),
    WITHER("wither", 0x343434),
    WITHER_ROSE("wither_rose", 0x000000, false),
    WITHER_SKELETON("wither_skeleton", 0x515353),
    WOOD("wood", 0x605e54),
    YELLORIUM("yellorium", 0xfffc00),
    ZINC("zinc", 0xb8b78b),
    ZOMBIE("zombie", 0x71955b),
    ZOMBIE_PIGMAN("zombie_pigman", 0xeea5a4),
    ZOMBIE_VILLAGER("zombie_villager", 0x3b622f);

    private final String name;
    private final int colour;
    private final boolean hasParticle;

    CropType(String name, int colour)
    {
        this(name, colour, true);
    }

    CropType(String name, int colour, boolean hasParticle)
    {
        this.name = name;
        this.colour = colour;
        this.hasParticle = hasParticle;
//        CropList.addEntry(this);
    }

    public String getName()
    {
        return name;
    }

    public int getColour()
    {
        return colour;
    }

    public boolean hasParticle()
    {
        return hasParticle;
    }
}