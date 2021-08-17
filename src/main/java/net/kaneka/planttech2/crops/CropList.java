package net.kaneka.planttech2.crops;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.ISerializable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public class CropList implements ISerializable
{
	public static final Logger LOGGER = LogManager.getLogger();
	// Crop name -> CropEntry
	private final HashMap<String, CropEntry> internalMap = new HashMap<>();

	@Override
	public CompoundTag save()
	{
		CompoundTag compound = new CompoundTag();
		try
		{
			ListTag keyList = new ListTag();
			for (String key : internalMap.keySet())
			{
				keyList.add(StringTag.valueOf(key));
				compound.put(key, internalMap.get(key).save());
			}
			compound.put("keys", keyList);
		}
		catch (Exception e)
		{
			PlantTechMain.LOGGER.error("Crop list is broken, cannot write the data");
			return new CompoundTag();
		}
		return compound;
	}

    @Override
    public void load(CompoundTag compound)
    {
        internalMap.clear();
        ListTag list = compound.getList("keys", Constants.NBT.TAG_STRING);
        for (int i=0;i<list.size();i++)
        {
			String key = list.getString(i);
            internalMap.put(key, new CropEntry(compound.getCompound(key)));
        }
    }

    public List<CropEntry> values(boolean includeDisabled)
	{
		return internalMap.values().stream().filter(entry -> includeDisabled || entry.getConfiguration().isEnabled())
				.sorted().collect(ImmutableList.toImmutableList());
	}

	public List<CropEntry> values()
	{
		return values(false);
	}

	public Set<String> keySet(boolean includeDisabled, boolean onlyWithParticles)
	{
		return internalMap.keySet().stream().filter(str -> includeDisabled || internalMap.get(str).getConfiguration().isEnabled())
				.filter(str -> !onlyWithParticles || internalMap.get(str).hasParticle()).sorted().collect(ImmutableSet.toImmutableSet());
	}

	public Set<String> keySet(boolean includeDisabled)
	{
		return keySet(includeDisabled, false);
	}

	public Set<String> keySet()
	{
		return keySet(false);
	}

	public void addEntry(String name, int seedColor, boolean hasParticle, CropConfiguration defaultConfig)
	{
		CropEntry prev = internalMap.put(name, new CropEntry(name, seedColor, hasParticle, defaultConfig));
		if (prev != null)
			PlantTechMain.LOGGER.warn("A duplicate crop entry for {} was registered; this may be a coding mistake", prev.getName());
	}

	public void removeEntry(String name)
	{
		internalMap.remove(name);
	}

	@Nullable
	public CropEntry getByName(String name)
	{
		return internalMap.get(name);
	}

	@Nullable
	public CropEntry getBySeed(Item item)
	{
		return this.internalMap.values().stream().filter(entry -> entry.isSeed(item)).findFirst().orElse(null);
	}

	public Set<CropEntry> getByParents(String parent1, String parent2)
	{
		Set<CropEntry> result = new HashSet<>();
		for (CropEntry entry : this.internalMap.values())
		{
			if (entry.isChild(parent1, parent2))
			{
				result.add(entry);
			}
		}
		return result;
	}

	public int getLength()
	{
		return internalMap.size();
	}

	public int getLengthEnabledOnly()
	{
		return values(false).size();
	}

	public void configureFromConfigData(Collection<CropEntryConfigData> configData)
	{
		for (CropEntryConfigData data : configData)
		{
			CropEntry entry = PlantTechMain.getCropList().getByName(data.getCropEntryName());
			if (entry != null)
			{
				entry.setConfiguration(CropConfiguration.fromConfigData(data));
			} else
			{
				LOGGER.error("Attempted to configure crop configuration for non-existent crop {}", data.getCropEntryName());
			}
		}
	}

	public static CropList addDefaultEntries(CropList cropList)
	{
		addEntry(cropList, "abyssalnite", 0x45005f);
		addEntry(cropList, "adamantine", 0xd53c00);
		addEntry(cropList, "allium", 0xa65ee1, false);
		addEntry(cropList, "aluminum", 0xb4b4b4);
		addEntry(cropList, "aluminum_brass", 0xc8b300);
		addEntry(cropList, "alumite", 0xe789ff);
		addEntry(cropList, "amber", 0xe09e00);
		addEntry(cropList, "apatite", 0x00b3e0);
		addEntry(cropList, "aquamarine", 0x00c3d4);
		addEntry(cropList, "ardite", 0x88471b);
		addEntry(cropList, "awakened_draconium", 0xbf4c00);
		addEntry(cropList, "azure_bluet", 0xd6e8e8, false);
		addEntry(cropList, "bamboo", 0x5d8824, false);
		addEntry(cropList, "basalt", 0x424242);
		addEntry(cropList, "beast", 0x6a6965);
		addEntry(cropList, "beetroots", 0xbf2529, false);
		addEntry(cropList, "black_quartz", 0x202020);
		addEntry(cropList, "blaze", 0xfc9600);
		addEntry(cropList, "blitz", 0xfffdcc);
		addEntry(cropList, "blizz", 0xc3d3ff);
		addEntry(cropList, "blue_topaz", 0x6089ff);
		addEntry(cropList, "blue_orchid", 0x3066ff, false);
		addEntry(cropList, "brass", 0xeaeaea);
		addEntry(cropList, "bronze", 0x804500);
		addEntry(cropList, "cactus", 0x527d26, false);
		addEntry(cropList, "carrot", 0xe38a1d, false);
		addEntry(cropList, "certus_quartz", 0x9fc3ff);
		addEntry(cropList, "chicken", 0xe2e2e2);
		addEntry(cropList, "chimerite", 0xaeffa6);
		addEntry(cropList, "chorus", 0x8f718f, false);
		addEntry(cropList, "chrome", 0xFFFFFF);
		addEntry(cropList, "coal", 0x3f3f3f);
		addEntry(cropList, "cobalt", 0x1d5791);
		addEntry(cropList, "cocoa_bean", 0xb97335, false);
		addEntry(cropList, "cold_iron", 0x72a7ff);
		addEntry(cropList, "compressed_iron", 0xbdbdbd);
		addEntry(cropList, "conductive_iron", 0x629dff);
		addEntry(cropList, "constantan", 0xb1ab00);
		addEntry(cropList, "copper", 0xb47800);
		addEntry(cropList, "coralium", 0x00646a);
		addEntry(cropList, "cornflower", 0x466aeb, false);
		addEntry(cropList, "cow", 0x443626);
		addEntry(cropList, "creeper", 0x41b736);
		addEntry(cropList, "dancium", 0xeb8c14);
		addEntry(cropList, "dandelion", 0xfed639, false);
		addEntry(cropList, "dark_gem", 0x4e4e4e);
		addEntry(cropList, "dark_steel", 0x8d7aff);
		addEntry(cropList, "desh", 0x535353);
		addEntry(cropList, "diamond", 0x77cefb);
		addEntry(cropList, "dirt", 0x593d29);
		addEntry(cropList, "draconium", 0x7600ba);
		addEntry(cropList, "dreadium", 0xba000d);
		addEntry(cropList, "drowned", 0x8ff1d7);
		addEntry(cropList, "electrical_steel", 0xb8b8b8);
		addEntry(cropList, "electrotine", 0x005093);
		addEntry(cropList, "electrum", 0xfff158);
		addEntry(cropList, "elementium", 0xeb11ff);
		addEntry(cropList, "emerald", 0x17dd62);
		addEntry(cropList, "end_steel", 0xfeffd6);
		addEntry(cropList, "ender_amethyst", 0xfd35ff);
		addEntry(cropList, "ender_biotite", 0x000000);
		addEntry(cropList, "enderdragon", 0x181818);
		addEntry(cropList, "enderium", 0x007b77);
		addEntry(cropList, "enderman", 0x181818);
		addEntry(cropList, "endstone", 0xf6fabd);
		addEntry(cropList, "energetic_alloy", 0x9cff32);
		addEntry(cropList, "fish", 0xbf841b);
		addEntry(cropList, "fluix_crystal", 0x6f0098);
		addEntry(cropList, "fluxed_electrum", 0xfffb87);
		addEntry(cropList, "ghast", 0xf0f0f0);
		addEntry(cropList, "glowstone", 0xfbda74);
		addEntry(cropList, "glowstone_ingot", 0xf6ed00);
		addEntry(cropList, "gold", 0xf8af2b);
		addEntry(cropList, "graphite", 0x444444);
		addEntry(cropList, "guardian", 0x668980);
		addEntry(cropList, "husk", 0x6a5d4a);
		addEntry(cropList, "illager", 0x939999);
		addEntry(cropList, "invar", 0xc3bc00);
		addEntry(cropList, "iridium", 0xcfcfcf);
		addEntry(cropList, "iron", 0xbc9980);
		addEntry(cropList, "kanekium", 0x572e8a);
		addEntry(cropList, "kelp", 0x5b8131, false);
		addEntry(cropList, "kinnoium", 0x246b2d);
		addEntry(cropList, "knightslime", 0xfd5fff);
		addEntry(cropList, "lapis", 0x1044ac);
		addEntry(cropList, "lava", 0xd14f0c);
		addEntry(cropList, "lead", 0x326e99);
		addEntry(cropList, "lenthurium", 0x2c8585);
		addEntry(cropList, "lilly_of_the_valley", 0xe7e7e7, false);
		addEntry(cropList, "lithium", 0xfffac4);
		addEntry(cropList, "lumium", 0xfff282);
		addEntry(cropList, "magma_cube", 0x330000);
		addEntry(cropList, "magnesium", 0x615900);
		addEntry(cropList, "malachite", 0x36bf53);
		addEntry(cropList, "manasteel", 0x3d8fff);
		addEntry(cropList, "manyullyn", 0x793393);
		addEntry(cropList, "melon", 0xa7ac1d, false);
		addEntry(cropList, "meteoric_iron", 0x8f845e);
		addEntry(cropList, "mithril", 0xb7d7ff);
		addEntry(cropList, "moonstone", 0xeef6ff);
		addEntry(cropList, "mooshroom", 0xa81012);
		addEntry(cropList, "mushroom", 0xe21212, false);
		addEntry(cropList, "mycelium", 0x736162);
		addEntry(cropList, "nether_wart", 0x831c20, false);
		addEntry(cropList, "netherrack", 0x652828);
		addEntry(cropList, "neutronium", 0x585858);
		addEntry(cropList, "nickel", 0x9f998c);
		addEntry(cropList, "octine", 0xffb400);
		addEntry(cropList, "orange_tulip", 0xbd6a22, false);
		addEntry(cropList, "osmium", 0xc6edff);
		addEntry(cropList, "oxeye_daisy", 0xf5ba27, false);
		addEntry(cropList, "panda", 0xf5ba27);
		addEntry(cropList, "parrot", 0x18bdff);
		addEntry(cropList, "peridot", 0x5fc859);
		addEntry(cropList, "pig", 0xf19e98);
		addEntry(cropList, "pink_tulip", 0xe4aff4, false);
		addEntry(cropList, "plantium", 0x35a048, true);
		addEntry(cropList, "platinum", 0xa2a2a2);
		addEntry(cropList, "polarbear", 0xf6f6f6);
		addEntry(cropList, "poppy", 0xed302c, false);
		addEntry(cropList, "potato", 0xc8a24b, false);
		addEntry(cropList, "prismarine", 0x5ea496);
		addEntry(cropList, "pulsating_iron", 0x75d970);
		addEntry(cropList, "pumpkin", 0xe38a1d, false);
		addEntry(cropList, "quartz", 0xd4caba);
		addEntry(cropList, "quicksilver", 0xd6ffff);
		addEntry(cropList, "red_tulip", 0xed302c, false);
		addEntry(cropList, "redstone", 0xff0000);
		addEntry(cropList, "redstone_alloy", 0xff0000);
		addEntry(cropList, "refined_obsidian", 0x62316d);
		addEntry(cropList, "rock_crystal", 0xa6a6a6);
		addEntry(cropList, "rubber", 0x444444);
		addEntry(cropList, "ruby", 0x980000);
		addEntry(cropList, "saltpeter", 0x969696);
		addEntry(cropList, "sand", 0xdacfa3);
		addEntry(cropList, "sapphire", 0x000a8e);
		addEntry(cropList, "sheep", 0xc09e86);
		addEntry(cropList, "shulker", 0x8e608e);
		addEntry(cropList, "signalum", 0x8e5700);
		addEntry(cropList, "silicon", 0x777777);
		addEntry(cropList, "silver", 0xdadada);
		addEntry(cropList, "skeleton", 0xbcbcbc);
		addEntry(cropList, "sky_stone", 0x383838);
		addEntry(cropList, "slate", 0xFFFFFF);
		addEntry(cropList, "slime", 0x59bd45);
		addEntry(cropList, "slimy_bone", 0x7b7b7b);
		addEntry(cropList, "snow", 0xffffff);
		addEntry(cropList, "soularium", 0x443824);
		addEntry(cropList, "soulsand", 0x49372c);
		addEntry(cropList, "spider", 0x605448);
		addEntry(cropList, "sponge", 0xcdce4a);
		addEntry(cropList, "squid", 0xcdce4a);
		addEntry(cropList, "star_steel", 0x171717);
		addEntry(cropList, "starmetal", 0x22002f);
		addEntry(cropList, "steel", 0x686868);
		addEntry(cropList, "stone", 0x616161);
		addEntry(cropList, "stray", 0xacbabd);
		addEntry(cropList, "sugarcane", 0x82a859, false);
		addEntry(cropList, "sulfur", 0xb1ac27);
		addEntry(cropList, "sunstone", 0xc13b00);
		addEntry(cropList, "syrmorite", 0xc71eff);
		addEntry(cropList, "tanzanite", 0xa951c6);
		addEntry(cropList, "terrasteel", 0x32b100);
		addEntry(cropList, "thaumium", 0x8a00ff);
		addEntry(cropList, "tin", 0xaba78c);
		addEntry(cropList, "titanium", 0xc6c6c6);
		addEntry(cropList, "topaz", 0xffde29);
		addEntry(cropList, "tungsten", 0x005a40);
		addEntry(cropList, "turtle", 0x388d3a);
		addEntry(cropList, "uranium", 0x3abd22);
		addEntry(cropList, "valonite", 0xcfa5d5);
		addEntry(cropList, "vibrant_alloy", 0xbf7e00);
		addEntry(cropList, "villager", 0xb57b67);
		addEntry(cropList, "vine", 0x1b5011, false);
		addEntry(cropList, "vinteum", 0x5a81ff);
		addEntry(cropList, "void_metal", 0x000000);
		addEntry(cropList, "water", 0x2b5fff);
		addEntry(cropList, "wheat", 0xae19, false);
		addEntry(cropList, "white_tulip", 0xf7f7f7, false);
		addEntry(cropList, "witch", 0xa39483);
		addEntry(cropList, "wither", 0x343434);
		addEntry(cropList, "wither_rose", 0x000000, false);
		addEntry(cropList, "wither_skeleton", 0x515353);
		addEntry(cropList, "wood", 0x605e54);
		addEntry(cropList, "yellorium", 0xfffc00);
		addEntry(cropList, "zinc", 0xb8b78b);
		addEntry(cropList, "zombie", 0x71955b);
		addEntry(cropList, "zombie_pigman", 0xeea5a4);
		addEntry(cropList, "zombie_villager", 0x3b622f);

		// cropList.addEntry("", PlanttechMain.modId + ":plants/plant_", PlanttechMain.modId +
		// ":textures/blocks/plants/plant_.png", Integer.parseInt("",16));

		return cropList;
	}

	static void addEntry(final CropList list, final String name, final int seedColor, final boolean hasParticle,
	                     final Consumer<CropConfiguration.Builder> config)
	{
		CropConfiguration.Builder builder = CropConfiguration.builder(DropEntry.of(() -> ModItems.SEEDS.get(name), 1, 4));
		if (hasParticle)
			builder.drop(() -> ModItems.PARTICLES.get(name), 0, 8);
		config.accept(builder);
		list.addEntry(name, seedColor, hasParticle, builder.build());
	}

	static void addEntry(final CropList list, final String name, final int seedColor, final boolean hasParticle)
	{
		addEntry(list, name, seedColor, hasParticle, b -> {});
	}

	static void addEntry(final CropList list, final String name, final int seedColor)
	{
		addEntry(list, name, seedColor, true);
	}
}
