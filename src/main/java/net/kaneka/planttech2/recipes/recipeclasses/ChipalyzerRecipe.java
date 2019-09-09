package net.kaneka.planttech2.recipes.recipeclasses;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.TagUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
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
	
	public ChipalyzerRecipe(ResourceLocation id, int tier, ItemStack input, ItemStack output)
	{
		this.id = id; 
		this.tier = tier; 
		this.input = input;	 
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
	
	public ItemStack getOutput()
	{
		return output; 
	}
	
	public boolean compare(ItemStack chip, ItemStack stack)
	{
		if(!stack.isEmpty() && !input.isEmpty() && !chip.isEmpty())
		{
			if(stack.getItem()  == input.getItem())
			{
				Map<Enchantment, Integer> inputench = getEnchList(input), 
										  stackench = getEnchList(stack); 
				if(inputench.size() == stackench.size())
				{
					boolean bool = true; 
					for(Enchantment ench: inputench.keySet())
					{
						if(stackench.containsKey(ench))
						{
							if(!(stackench.get(ench) == inputench.get(ench)))
							{
								bool = false;  
							}
						}
						else
						{
							bool = false; 
						}
					}
					return bool; 
				}
			}
		}
		
		return false;
	}
	
	public Map<Enchantment, Integer> getEnchList(ItemStack stack)
	{
		Map<Enchantment, Integer> map = Maps.newLinkedHashMap();
		if(stack != null)
		{
			if(!stack.isEmpty())
			{
				if(stack.getItem() == Items.ENCHANTED_BOOK)
				{
					for(INBT nbt : EnchantedBookItem.getEnchantments(stack))
					{
						if(nbt instanceof CompoundNBT)
						{
							CompoundNBT compoundnbt = (CompoundNBT) nbt; 
							if(compoundnbt.contains("id") && compoundnbt.contains("lvl"))
							{
								if(ForgeRegistries.ENCHANTMENTS.containsKey(new ResourceLocation(compoundnbt.getString("id"))))
								{
									map.put(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(compoundnbt.getString("id"))), compoundnbt.getInt("lvl")); 
								}
							}
						}
					}
				}
				else
				{
					map = EnchantmentHelper.getEnchantments(input); 
				}
			}
		}
		return map; 
	}
	
	public boolean compareEnchantment(ListNBT nbt)
	{
		if(input.getEnchantmentTagList() == nbt)
		{
			return true;
		}
		return false; 
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
			
			
			ItemStack input = new ItemStack(Items.ENCHANTED_BOOK);
			if(json.has("input"))
			{
    			JsonObject inputobject = json.getAsJsonObject("input");
    			
    			if (inputobject.has("item"))
    			{
    				input = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputobject.get("item").getAsString())));
    			} 
    			else if (inputobject.has("block"))// Just in case
    			{
    				input = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputobject.get("block").getAsString())));
    			} else if (inputobject.has("tag"))
    			{
    				input = new ItemStack(TagUtils.getAnyTagItem(new ResourceLocation(inputobject.get("tag").getAsString())));
    			}
    			
    			if(inputobject.has("enchantment"))
    			{
        			JsonObject enchobject = inputobject.getAsJsonObject("enchantment");
        			
        			if (enchobject.has("namespaced_id") && enchobject.has("level"))
        			{
        				ResourceLocation namespaced_id = new ResourceLocation(enchobject.get("namespaced_id").getAsString()); 
        				if(ForgeRegistries.ENCHANTMENTS.containsKey(namespaced_id))
        				{
        					if(input.getItem() instanceof EnchantedBookItem)
        					{
        						EnchantedBookItem.addEnchantment(input, new EnchantmentData(ForgeRegistries.ENCHANTMENTS.getValue(namespaced_id), enchobject.get("level").getAsInt()));
        					}
        					else
        					{
        						input.addEnchantment(ForgeRegistries.ENCHANTMENTS.getValue(namespaced_id), enchobject.get("level").getAsInt()); 
        					}
        				}
        			}
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
			
			if (result != null)
			{
				return new ChipalyzerRecipe(recipeId, tier, input, new ItemStack(result));
			} else
			{
				throw new IllegalStateException("Item did not exist:" + recipeId.toString());
			}
		}

		@Override
		public ChipalyzerRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			int tier = buffer.readInt(); 
			ItemStack input = buffer.readItemStack();
			ItemStack result = buffer.readItemStack();
			return new ChipalyzerRecipe(recipeId, tier, input, result);
		}

		@Override
		public void write(PacketBuffer buffer, ChipalyzerRecipe recipe)
		{
			buffer.writeInt(recipe.tier); 
			buffer.writeItemStack(recipe.input);
			buffer.writeItemStack(recipe.output);
		}

	}

}
