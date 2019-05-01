package net.kaneka.planttech2.datapack;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.librarys.CropListEntry;
import net.kaneka.planttech2.librarys.utils.Parents;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class CropListEntryConfiguration
{
	private String croplistentry;
	private boolean enabled;
	private EnumTemperature temperature;
	private List<Item> seeds;
	private List<Triple<Item, Integer, Integer>> drops;
	private List<Parents> parents;
	private Block soil;

	public CropListEntryConfiguration(String croplistentry, boolean enabled, EnumTemperature temperature, List<Item> seeds, List<Triple<Item, Integer, Integer>> additional_drops, List<Parents> parents, Block soil)
	{
		this.croplistentry = croplistentry;
		this.enabled = enabled;
		this.temperature = temperature;
		this.seeds = seeds;
		this.drops = additional_drops;
		this.parents = parents;
		this.soil = soil;
	}
	
	public void applyToEntry()
	{
		CropListEntry entry = PlantTechMain.croplist.getEntryByName(croplistentry);
		if(entry != null)
		{
			entry.setBlacklisted(!enabled);
			if(temperature != null)
			{
				entry.alternateTemperature(temperature);
			}
			
			if(seeds != null) 
			{
				entry.clearSeeds();
    			for(Item item : seeds)
    			{
    				entry.addSeeds(new ItemStack(item));
    			}
			}
			
			if(drops != null)
			{
				entry.clearDrops();
    			for(Triple<Item, Integer, Integer> drop: drops)
    			{
    				entry.addDrop(new ItemStack(drop.getLeft()), drop.getMiddle(), drop.getRight());
    			}
			}
			
			if(parents != null)
			{
				entry.clearParents();
    			for(Parents parents_pair: parents)
    			{
    				entry.addParents(parents_pair.getParent(0), parents_pair.getParent(1), parents_pair.getMutateChance());
    			}
			}
			
			if(soil != null)
			{
				entry.setSoil(new ItemStack(soil)); 
			}
			else
			{
				entry.setSoil(ItemStack.EMPTY);
			}
		}
	}
	
	public void merge(CropListEntryConfiguration changes)
	{
		enabled = changes.enabled; 
		if(changes.temperature != null)
		{
			temperature = changes.temperature; 
		}
		
		if(changes.seeds != null)
		{
			seeds = changes.seeds;
		}
		
		if(changes.drops != null)
		{
			drops = changes.drops;
		}
		
		if(changes.parents != null)
		{
			parents = changes.parents; 
		}
		
		if(changes.soil != null)
		{
			soil = changes.soil; 
		}
		
	}

	public static class Deserializer
	{
		public static CropListEntryConfiguration read(String croplistentry, JsonObject jsonObject)
		{
			boolean enabled = true;
			if (jsonObject.has("enabled"))
			{
				enabled = jsonObject.get("enabled").getAsBoolean();
			}
			EnumTemperature temperature = null;
			if (jsonObject.has("temperature"))
			{
				temperature = EnumTemperature.byId(jsonObject.get("temperature").getAsInt());
			}
			List<Item> additional_seeds = null;
			Item item = null;
			if (jsonObject.has("seeds"))
			{
				additional_seeds = new ArrayList<Item>();
				for (JsonElement element : jsonObject.get("seeds").getAsJsonArray())
				{
					item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(element.getAsString()));
					if (item != null)
					{
						additional_seeds.add(item);
					}
					item = null;
				}
			}

			List<Triple<Item, Integer, Integer>> additional_drops = null;
			if (jsonObject.has("drops"))
			{
				additional_drops = new ArrayList<Triple<Item, Integer, Integer>>();
				for (JsonElement element : jsonObject.get("drops").getAsJsonArray())
				{
					JsonObject object = element.getAsJsonObject();
					if (object.has("item"))
					{
						item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(element.getAsJsonObject().get("item").getAsString()));
					} else if (object.has("block"))
					{
						item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(element.getAsJsonObject().get("block").getAsString()));
					}

					if (item != null)
					{
						if(object.has("min") && object.has("max"))
						additional_drops.add(new ImmutableTriple<Item, Integer, Integer>(item, object.get("min").getAsInt(), object.get("max").getAsInt()));
					}
					item = null;
				}
			}

			List<Parents> parents = null;
			if (jsonObject.has("parents"))
			{
				parents = new ArrayList<Parents>();
				for (JsonElement element : jsonObject.get("parents").getAsJsonArray())
				{
					JsonObject object = element.getAsJsonObject();
					if (object.has("partner_1") && object.has("partner_2") && object.has("chance"))
					{
						parents.add(new Parents(object.get("partner_1").getAsString(), object.get("partner_2").getAsString(), object.get("chance").getAsFloat()));
					}
				}
			}

			

			Block soil = null;
			if(jsonObject.has("soil"))
			{
				JsonObject soilobject = jsonObject.get("soil").getAsJsonObject();
    			if (soilobject.has("item"))
    			{
    				soil = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(soilobject.get("item").getAsString()));
    			} else if (soilobject.has("block"))
    			{
    				soil = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(soilobject.get("block").getAsString()));
    			}
			}

			return new CropListEntryConfiguration(croplistentry, enabled, temperature, additional_seeds, additional_drops, parents, soil);
		}

		public static CropListEntryConfiguration read(String name, PacketBuffer buf)
		{
			boolean enabled = buf.readBoolean();
			EnumTemperature temp = EnumTemperature.byId(buf.readByte()); 
			List<Item> seeds = null;
			int sizeSeeds = buf.readVarInt();
			if(sizeSeeds > 0)
			{
				seeds = new ArrayList<Item>(); 
				for(int i = 0; i < sizeSeeds; i++)
				{
					seeds.add(Item.getItemById(buf.readVarInt()));
				}
			}
			
			List<Triple<Item, Integer, Integer>> drops = null; 
			int sizeDrops = buf.readVarInt();
			if(sizeDrops > 0)
			{
				drops = new ArrayList<Triple<Item, Integer, Integer>>(); 
				for(int i = 0; i < sizeDrops; i++)
				{
					drops.add(new ImmutableTriple<Item, Integer, Integer>(Item.getItemById(buf.readVarInt()), buf.readVarInt(), buf.readVarInt()));
				}
			}
			
			List<Parents> parents = null; 
			int sizeParents = buf.readVarInt();
			if(sizeParents > 0)
			{
				parents = new ArrayList<Parents>(); 
				for(int i = 0; i < sizeParents; i++)
				{
					parents.add(new Parents(buf.readString(1024), buf.readString(1024), buf.readFloat()));
				}
			}
			
			String soilString = buf.readString(1024); 
			Block soil = null; 
			if(soilString != "null")
			{
    			soil = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(soilString));
			}
				
			
			return new CropListEntryConfiguration(name, enabled, temp, seeds, drops, parents, soil);
		}

		public static void write(PacketBuffer buf, CropListEntryConfiguration cfg)
		{
			buf.writeBoolean(cfg.enabled);
			buf.writeByte(cfg.temperature.getId());
			if(cfg.seeds != null)
			{
    			buf.writeVarInt(cfg.seeds.size()); 
    			for(Item seed :cfg.seeds)
    			{
    				buf.writeVarInt(Item.getIdFromItem(seed));
    			}
			}
			else
			{
				buf.writeVarInt(0); 
			}
			
			if(cfg.drops != null)
			{
    			buf.writeVarInt(cfg.drops.size()); 
    			for(Triple<Item, Integer, Integer> drop: cfg.drops)
    			{
    				buf.writeVarInt(Item.getIdFromItem(drop.getLeft()));
    				buf.writeVarInt(drop.getMiddle());
    				buf.writeVarInt(drop.getRight());
    			}
			}
			else
			{
				buf.writeVarInt(0); 
			}
			
			if(cfg.parents != null)
			{
    			buf.writeVarInt(cfg.parents.size()); 
    			for(Parents parent_pair: cfg.parents)
    			{
    				buf.writeString(parent_pair.getParent(0));
    				buf.writeString(parent_pair.getParent(1));
    				buf.writeFloat(parent_pair.getMutateChance());
    			}
			}
			else
			{
				buf.writeVarInt(0); 
			}
			if(cfg.soil != null)
			{
				buf.writeString(cfg.soil.getRegistryName().toString());
			}
			else
			{
				buf.writeString("null"); 
			}
		}
	}
}
