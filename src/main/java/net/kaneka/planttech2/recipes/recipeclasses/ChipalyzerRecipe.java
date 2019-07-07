package net.kaneka.planttech2.recipes.recipeclasses;

import com.google.gson.JsonObject;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.TagUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ChipalyzerRecipe implements IRecipe<IInventory>
{
	private final ResourceLocation id; 
	private final int tier; 
	private final ItemStack input; 
	private final ItemStack output; 
	private final Enchantment ench; 
	private final int level; 
	
	public ChipalyzerRecipe(ResourceLocation id, int tier, ItemStack input, Enchantment ench, int level, ItemStack output)
	{
		this.id = id; 
		this.tier = tier; 
		this.input = input;	
		this.ench = ench; 
		this.level = level; 
		this.output = output;
	}
	
	public int getTier()
	{
		return tier; 
	}
	
	public ItemStack getInput()
	{
		return input; 
	}
	
	public Enchantment getEnchantment()
	{
		return ench; 
	}
	
	public ItemStack getOutput()
	{
		return output; 
	}
	
	public boolean compareEnchantment(ListNBT nbt)
	{
		if(nbt != null)
		{
			for(int i = 0; i < nbt.size(); i++)
			{
				INBT nbt2 = nbt.get(i); 
				if(nbt2 instanceof CompoundNBT)
				{
					CompoundNBT nbt3 = (CompoundNBT) nbt2; 
					if(nbt3.contains("id") && nbt3.contains("lvl"))
					{
						if(ench == ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(nbt3.getString("id"))) && level == nbt3.getInt("lvl"))
						{
							return true; 
						}
					}
				}
			}
		}
		return false; 
	}
	
	public ItemStack getJeiInput()
	{
		if(ench != null)
		{
			ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK); 
			EnchantedBookItem.addEnchantment(stack, new EnchantmentData(ench, level));
			return stack; 
		}
		else if(input != null)
		{
			return input; 
		}
			
		return ItemStack.EMPTY; 
	}
	
	public ItemStack getChip()
	{
		switch(tier)
		{
			case 1: return new ItemStack(ModItems.EMPTY_UPGRADECHIP_TIER_1); 
			case 2: return new ItemStack(ModItems.EMPTY_UPGRADECHIP_TIER_2); 
			case 3: return new ItemStack(ModItems.EMPTY_UPGRADECHIP_TIER_3); 
		}
		
		return ItemStack.EMPTY; 
	}

	@Override
	public boolean matches(IInventory inv, World worldIn)
	{
		return input.getItem() == inv.getStackInSlot(0).getItem();
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv)
	{
		return output;
	}

	@Override
	public boolean canFit(int width, int height)
	{
		if(width == height && width == 1) return true; 
		else return false; 
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return output;
	}

	@Override
	public ResourceLocation getId()
	{
		return id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ForgeRegistries.RECIPE_SERIALIZERS.getValue(new ResourceLocation(PlantTechMain.MODID, "chipalyzer"));
	}

	@Override
	public IRecipeType<?> getType()
	{
		return ModRecipeTypes.CHIPALYZER;
	}

	
	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ChipalyzerRecipe>
	{
		@Override
		public ChipalyzerRecipe read(ResourceLocation recipeId, JsonObject json)
		{
			int tier = 0; 
			if (json.has("tier"))
			{
				tier = json.get("tier").getAsInt();
			}
			
			
			Item input = null;
			if(json.has("input"))
			{
    			JsonObject inputobject = json.getAsJsonObject("input");
    			
    			if (inputobject.has("item"))
    			{
    				input = ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputobject.get("item").getAsString()));
    			} else if (inputobject.has("block"))// Just in case
    			{
    				input = ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputobject.get("block").getAsString()));
    			} else if (inputobject.has("tag"))
    			{
    				input = TagUtils.getAnyTagItem(new ResourceLocation(inputobject.get("tag").getAsString()));
    			}
			}
			
			Enchantment ench = null;
			int level = 1; 
			if(json.has("enchantment"))
			{
    			JsonObject enchobject = json.getAsJsonObject("enchantment");
    			
    			if (enchobject.has("namespaced_id"))
    			{
    				ResourceLocation namespaced_id = new ResourceLocation(enchobject.get("namespaced_id").getAsString()); 
    				if(ForgeRegistries.ENCHANTMENTS.containsKey(namespaced_id))
    				{
    					ench = ForgeRegistries.ENCHANTMENTS.getValue(namespaced_id); 
    				}
    			}
    			if(enchobject.has("level"))
    			{
    				level = enchobject.get("level").getAsInt(); 
    			}
			}

			JsonObject resultobject = json.getAsJsonObject("result");
			Item result = null;
			if (resultobject.has("item"))
			{
				result = ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultobject.get("item").getAsString()));
			} else if (resultobject.has("block"))// Just in case
			{
				result = ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultobject.get("block").getAsString()));
			} else if (resultobject.has("tag"))
			{
				result = TagUtils.getAnyTagItem(new ResourceLocation(resultobject.get("tag").getAsString()));
			}

			if ((input != null || ench != null) && result != null)
			{
				return new ChipalyzerRecipe(recipeId, tier, new ItemStack(input), ench, level ,new ItemStack(result));
			} else
			{
				throw new IllegalStateException("Item did not exist:" + recipeId.toString() + input + ench + result);
			}
		}

		@Override
		public ChipalyzerRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			int tier = buffer.readInt(); 
			ItemStack input = buffer.readItemStack();
			ItemStack result = buffer.readItemStack();
			Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(buffer.readResourceLocation()); 
			int level = buffer.readInt(); 
			return new ChipalyzerRecipe(recipeId, tier, input, ench, level, result);
		}

		@Override
		public void write(PacketBuffer buffer, ChipalyzerRecipe recipe)
		{
			buffer.writeInt(recipe.tier); 
			buffer.writeItemStack(recipe.input);
			buffer.writeItemStack(recipe.output);
			buffer.writeResourceLocation(ForgeRegistries.ENCHANTMENTS.getKey(recipe.ench)); 
			buffer.writeInt(recipe.level);
		}

	}

}
