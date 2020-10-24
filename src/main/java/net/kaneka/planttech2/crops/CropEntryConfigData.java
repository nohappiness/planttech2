package net.kaneka.planttech2.crops;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import static net.minecraftforge.registries.ForgeRegistries.BLOCKS;
import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

public class CropEntryConfigData
{
	private final String cropEntryName;
	private final boolean enabled;
	private final EnumTemperature temperature;
	private final DropEntry primarySeed;
	private final List<Supplier<Item>> seeds;
	private final List<DropEntry> drops;
	private final List<ParentPair> parents;
	private final Supplier<Block> soil;

	private CropEntryConfigData(String cropEntryName, boolean enabled, EnumTemperature temperature, DropEntry primarySeed, List<Supplier<Item>> seeds,
	                            List<DropEntry> drops, List<ParentPair> parents, Supplier<Block> soil)
	{
		this.cropEntryName = cropEntryName;
		this.enabled = enabled;
		this.temperature = temperature;
		this.primarySeed = primarySeed;
		this.seeds = ImmutableList.copyOf(seeds);
		this.drops = ImmutableList.copyOf(drops);
		this.parents = ImmutableList.copyOf(parents);
		this.soil = soil;
	}

	public String getCropEntryName()
	{
		return cropEntryName;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public EnumTemperature getTemperature()
	{
		return temperature;
	}

	public DropEntry getPrimarySeed()
	{
		return primarySeed;
	}

	public List<Supplier<Item>> getSeeds()
	{
		return seeds;
	}

	public List<DropEntry> getDrops()
	{
		return drops;
	}

	public List<ParentPair> getParents()
	{
		return parents;
	}

	public Supplier<Block> getSoil()
	{
		return soil;
	}

	public static CropEntryConfigData create(String cropEntryName, CropConfiguration config)
	{
		return new CropEntryConfigData(cropEntryName, config.isEnabled(), config.getTemperature(), config.getPrimarySeed(), config.getSeeds(),
				config.getDrops(),
				config.getParents(), config.getSoil());
	}

	public static class Serializer implements JsonSerializer<CropEntryConfigData>, JsonDeserializer<CropEntryConfigData>
	{
		public static final Serializer INSTANCE = new Serializer();

		private Serializer()
		{
		}

		@Override
		public JsonElement serialize(CropEntryConfigData data, Type typeOfSrc, JsonSerializationContext context)
		{
			JsonObject obj = new JsonObject();
			obj.addProperty("crop", data.getCropEntryName());
			obj.addProperty("enabled", data.isEnabled());
			obj.addProperty("temperature", data.getTemperature().name().toLowerCase(Locale.ROOT));

			obj.add("primary_seed", context.serialize(data.getPrimarySeed(), DropEntry.class));

			JsonArray seeds = new JsonArray();
			data.getSeeds().stream()
					.map(Supplier::get)
					.map(IForgeRegistryEntry::getRegistryName).filter(Objects::nonNull)
					.map(ResourceLocation::toString)
					.map(JsonPrimitive::new)
					.forEach(seeds::add);
			obj.add("seeds", seeds);

			JsonArray drops = new JsonArray();
			data.getDrops().stream()
					.map(entry -> context.serialize(entry, DropEntry.class))
					.forEach(drops::add);
			obj.add("drops", drops);

			JsonArray parents = new JsonArray();
			data.getParents().stream()
					.map(pair -> context.serialize(pair, ParentPair.class))
					.forEach(parents::add);
			obj.add("parents", parents);

			JsonObject soil = new JsonObject();
			soil.addProperty("block", data.getSoil().get().getRegistryName().toString());
			obj.add("soil", soil);

			return obj;
		}

		@Override
		public CropEntryConfigData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			JsonObject obj = json.getAsJsonObject();

			String name = JSONUtils.getString(obj, "crop");
			boolean enabled = JSONUtils.getBoolean(obj, "enabled");

			EnumTemperature temp = getTemperature(obj.get("temperature"));
			DropEntry primarySeed = context.deserialize(obj.get("primary_seed"), DropEntry.class);
			List<Supplier<Item>> seeds = getSeeds(obj.get("seeds"));
			List<DropEntry> drops = getDropEntries(obj.get("drops"), context);
			List<ParentPair> parents = getParentPairs(obj.get("parents"), context);
			Supplier<Block> soil = getSoil(obj.get("soil"));

			return new CropEntryConfigData(name, enabled, temp, primarySeed, seeds, drops, parents, soil);
		}

		public void write(CropEntryConfigData data, PacketBuffer buf)
		{
			buf.writeString(data.getCropEntryName(), 64);
			buf.writeBoolean(data.isEnabled());
			buf.writeEnumValue(data.getTemperature());

			DropEntry.Serializer.INSTANCE.write(data.primarySeed, buf);
			buf.writeShort(data.getSeeds().size());
			data.getSeeds().stream().map(Supplier::get).map(IForgeRegistryEntry::getRegistryName).filter(Objects::nonNull)
					.forEach(buf::writeResourceLocation);

			buf.writeShort(data.getParents().size());
			data.getDrops().forEach(drop -> DropEntry.Serializer.INSTANCE.write(drop, buf));

			buf.writeShort(data.getParents().size());
			data.getParents().forEach(pair -> ParentPair.Serializer.INSTANCE.write(pair, buf));

			buf.writeResourceLocation(data.getSoil().get().getRegistryName());
		}

		public CropEntryConfigData read(PacketBuffer buf)
		{
			String cropEntryName = buf.readString(64);
			boolean enabled = buf.readBoolean();
			EnumTemperature temperature = buf.readEnumValue(EnumTemperature.class);

			DropEntry primarySeed = DropEntry.Serializer.INSTANCE.read(buf);

			List<Supplier<Item>> seeds = new LinkedList<>();
			short seedsAmount = buf.readShort();
			for (int i = 0; i < seedsAmount; i++)
			{
				seeds.add(RegistryObject.of(buf.readResourceLocation(), ITEMS));
			}

			short dropsAmount = buf.readShort();
			List<DropEntry> drops = new LinkedList<>();
			for (int i = 0; i < dropsAmount; i++)
			{
				drops.add(DropEntry.Serializer.INSTANCE.read(buf));
			}

			List<ParentPair> parentPairs = new LinkedList<>();
			short parentsAmount = buf.readShort();
			for (int i = 0; i < parentsAmount; i++)
			{
				parentPairs.add(ParentPair.Serializer.INSTANCE.read(buf));
			}

			Supplier<Block> soil = RegistryObject.of(buf.readResourceLocation(), BLOCKS);

			return new CropEntryConfigData(cropEntryName, enabled, temperature, primarySeed, seeds, drops, parentPairs, soil);
		}

		private EnumTemperature getTemperature(JsonElement element)
		{
			String tempStr = JSONUtils.getString(element, "temperature").toUpperCase(Locale.ROOT);
			EnumTemperature temp = EnumTemperature.byName(tempStr);
			if (temp == null)
			{
				throw new JsonSyntaxException(
						"Expected temperature to have a value of " + Arrays.toString(EnumTemperature.values()).toLowerCase(Locale.ROOT) + ", got " + JSONUtils
								.toString(element));
			}
			return temp;
		}

		private List<DropEntry> getDropEntries(JsonElement dropsElement, JsonDeserializationContext context)
		{
			List<DropEntry> drops = new ArrayList<>();
			if (dropsElement.isJsonPrimitive() || dropsElement.isJsonObject())
			{
				drops.add(context.deserialize(dropsElement, DropEntry.class));
			} else if (dropsElement.isJsonArray())
			{
				dropsElement.getAsJsonArray().forEach(el -> drops.add(context.deserialize(el, DropEntry.class)));
			} else
			{
				throw new JsonSyntaxException("Expected drops to be a string, JsonObject or JsonArray, was " + JSONUtils.toString(dropsElement));
			}
			return drops;
		}

		private List<ParentPair> getParentPairs(JsonElement pairElement, JsonDeserializationContext context)
		{
			List<ParentPair> parents = new ArrayList<>();
			if (pairElement.isJsonObject())
			{
				parents.add(context.deserialize(pairElement, ParentPair.class));
			} else if (pairElement.isJsonArray())
			{
				JsonArray array = pairElement.getAsJsonArray();
				array.forEach(el -> parents.add(context.deserialize(el, ParentPair.class)));
			} else
			{
				throw new JsonSyntaxException("Expected parents to be a JsonObject or JsonArray, was " + JSONUtils.toString(pairElement));
			}
			return parents;
		}

		private List<Supplier<Item>> getSeeds(JsonElement element)
		{
			JsonArray array = JSONUtils.getJsonArray(element, "seeds");
			return StreamSupport.stream(array.spliterator(), false)
					.map(JsonElement::getAsString)
					.map(ResourceLocation::new)
					.map(loc -> RegistryObject.of(loc, ITEMS))
					.collect(Collectors.toList());
		}

		private Supplier<Block> getSoil(JsonElement element)
		{
			if (element.isJsonPrimitive())
			{
//				return RegistryObject.of(new ResourceLocation(JSONUtils.getString(element, "soil")), BLOCKS);
				return () -> BLOCKS.getValue(new ResourceLocation(JSONUtils.getString(element, "soil")));
			} else if (element.isJsonObject())
			{
				JsonObject obj = element.getAsJsonObject();
//				return RegistryObject.of(new ResourceLocation(JSONUtils.getString(obj, "block")), BLOCKS);
				return () -> BLOCKS.getValue(new ResourceLocation(JSONUtils.getString(obj, "block")));
			} else
			{
				throw new JsonSyntaxException("Expected soil to be a string or JsonObject, was " + JSONUtils.toString(element));
			}
		}
	}
}
