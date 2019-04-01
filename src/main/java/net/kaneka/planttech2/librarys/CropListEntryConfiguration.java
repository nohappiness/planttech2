package net.kaneka.planttech2.librarys;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.librarys.utils.Parents;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.realms.RealmsAnvilLevelStorageSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.ForgeRegistries;

public class CropListEntryConfiguration
{
    private final boolean enabled;
    private final EnumTemperature temperature;
    private final List<Item> additional_seeds;
    private final List<Triple<Item, Integer, Integer>> additional_drops;
    private final List<Parents> parents;
    private final Block soil;

    public CropListEntryConfiguration(boolean enabled, EnumTemperature temperature, List<Item> additional_seeds, List<Triple<Item, Integer, Integer>> additional_drops, List<Parents> parents, Block soil)
    {
	this.enabled = enabled;
	this.temperature = temperature;
	this.additional_seeds = additional_seeds;
	this.additional_drops = additional_drops;
	this.parents = parents;
	this.soil = soil;
    }

    public static class Deserializer implements JsonDeserializer<CropListEntryConfiguration>
    {

	@Override
	public CropListEntryConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
	    final JsonObject jsonObject = json.getAsJsonObject();
	    final boolean enabled = jsonObject.get("enabled").getAsBoolean();
	    final EnumTemperature temperature = EnumTemperature.byId(jsonObject.get("temperature").getAsInt());
	    List<Item> additional_seeds = new ArrayList<Item>();
	    Item item = null; 
	    for(JsonElement element: jsonObject.get("additional_seeds").getAsJsonArray())
	    {
		item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(element.getAsString()));
		if(item != null)
		{
		    additional_seeds.add(item);
		}
		item = null; 
	    }
	    
	    List<Triple<Item, Integer, Integer>> additional_drops = new ArrayList<Triple<Item, Integer, Integer>>();
	    for(JsonElement element: jsonObject.get("additional_drops").getAsJsonArray())
	    {
		JsonObject object = element.getAsJsonObject();
		if(object.has("item"))
		{
		    item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(element.getAsJsonObject().get("item").getAsString()));
		}
		else if(object.has("block"))
		{
		    item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(element.getAsJsonObject().get("block").getAsString()));
		}
		    
		if(item != null)
		{
		    additional_drops.add(new ImmutableTriple<Item, Integer, Integer>(item, object.get("min").getAsInt(), object.get("max").getAsInt()));
		}
		item = null; 
	    }
	    
	    List<Parents> parents = new ArrayList<Parents>();
	    for(JsonElement element: jsonObject.get("parents").getAsJsonArray())
	    {
		JsonObject object = element.getAsJsonObject();
		parents.add(new Parents(object.get("partner_1").getAsString(), object.get("partner_2").getAsString()));
	    }
	    
	    Block soil = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(jsonObject.get("soil").getAsString()));
	    
	    return new CropListEntryConfiguration(enabled, temperature, additional_seeds, additional_drops, parents, soil);
	}
    }
}
