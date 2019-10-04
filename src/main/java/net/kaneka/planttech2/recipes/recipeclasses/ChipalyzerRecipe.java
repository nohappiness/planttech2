package net.kaneka.planttech2.recipes.recipeclasses;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.TagUtils;
import net.minecraft.enchantment.Enchantment;
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
	private final Enchantment enchantment;
	private final ItemStack output;

	public ChipalyzerRecipe(ResourceLocation id, int tier, ItemStack input, Enchantment enchantment, ItemStack output)
	{
		this.id = id;
		this.tier = tier;
		this.input = input;
		this.enchantment = enchantment; 
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
		return enchantment; 
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public boolean compare(ItemStack chip, ItemStack stack)
	{
		if (!stack.isEmpty() && !chip.isEmpty())
		{
			if (enchantment != null)
			{
				
				List<Enchantment> stackench = getEnchList(stack); 
				if(stackench.contains(enchantment))
				{
					return true; 
				}
			}
			
			if(!input.isEmpty())
			{
				if(input.getItem() == stack.getItem())
				{
					return true; 
				}
			}
		}

		return false;
	}

	public List<Enchantment> getEnchList(ItemStack stack)
	{
		List<Enchantment> list = new ArrayList<Enchantment>();
		if (stack != null)
		{
			if (!stack.isEmpty())
			{
				if (stack.getItem() == Items.ENCHANTED_BOOK)
				{
					for (INBT nbt : EnchantedBookItem.getEnchantments(stack))
					{
						if (nbt instanceof CompoundNBT)
						{
							CompoundNBT compoundnbt = (CompoundNBT) nbt;
							if (compoundnbt.contains("id"))
							{
								if (ForgeRegistries.ENCHANTMENTS.containsKey(new ResourceLocation(compoundnbt.getString("id"))))
								{
									list.add(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(compoundnbt.getString("id"))));
								}
							}
						}
					}
				} 
				else
				{
					for(Enchantment entry: EnchantmentHelper.getEnchantments(stack).keySet())
					{
						list.add(entry);
					}
				}
			}
		}
		return list;
	}

	public boolean compareEnchantment(ListNBT nbt)
	{
		if (input.getEnchantmentTagList() == nbt)
		{
			return true;
		}
		return false;
	}

	public ItemStack getChip()
	{
		switch (tier)
		{
		case 1:
			return new ItemStack(ModItems.EMPTY_UPGRADECHIP_TIER_1);
		case 2:
			return new ItemStack(ModItems.EMPTY_UPGRADECHIP_TIER_2);
		case 3:
			return new ItemStack(ModItems.EMPTY_UPGRADECHIP_TIER_3);
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
		if (width == height && width == 1)
			return true;
		else
			return false;
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

			ItemStack input = ItemStack.EMPTY;
			Enchantment enchantment = null; 
			if (json.has("input"))
			{
				JsonObject inputobject = json.getAsJsonObject("input");

				if (inputobject.has("item"))
				{
					input = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputobject.get("item").getAsString())));
				} else if (inputobject.has("block"))// Just in case
				{
					input = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputobject.get("block").getAsString())));
				} else if (inputobject.has("tag"))
				{
					input = new ItemStack(TagUtils.getAnyTagItem(new ResourceLocation(inputobject.get("tag").getAsString())));
				}

				if (inputobject.has("enchantment"))
				{
					ResourceLocation namespaced_id = new ResourceLocation(inputobject.get("enchantment").getAsString());
					if (ForgeRegistries.ENCHANTMENTS.containsKey(namespaced_id))
					{
						enchantment = ForgeRegistries.ENCHANTMENTS.getValue(namespaced_id); 
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
				return new ChipalyzerRecipe(recipeId, tier, input, enchantment, new ItemStack(result));
				
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
			String ench = buffer.readString();
			Enchantment enchantment = null;
			if(!ench.equals("null"))
			{
				enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(ench));
			}
			ItemStack result = buffer.readItemStack();
			return new ChipalyzerRecipe(recipeId, tier, input, enchantment, result);
		}

		@Override
		public void write(PacketBuffer buffer, ChipalyzerRecipe recipe)
		{
			buffer.writeInt(recipe.tier);
			buffer.writeItemStack(recipe.input);
			if(recipe.enchantment != null)
			{
				buffer.writeString(recipe.enchantment.getRegistryName().toString());
			}
			else
			{
				buffer.writeString("null");
			}
			buffer.writeItemStack(recipe.output);
		}

	}

}
