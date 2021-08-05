package net.kaneka.planttech2.recipes.recipeclasses;

import com.google.gson.JsonObject;
import net.kaneka.planttech2.recipes.ModRecipeSerializers;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.utilities.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChipalyzerRecipe implements Recipe<Container>
{
	private final ResourceLocation id;
	private final ItemStack chip;
	private final ItemStack input;
	@Nullable
	private final Enchantment enchantment;
	private final ItemStack output;

	public ChipalyzerRecipe(ResourceLocation id, ItemStack chip, @Nullable ItemStack input, @Nullable Enchantment enchantment, ItemStack output)
	{
		this.id = id;
		this.chip = chip;
		this.input = input == null ? new ItemStack(Items.AIR) : input;
		this.enchantment = enchantment;
		this.output = output;
	}

	public ItemStack getChip() {return chip;}
	public ItemStack getInput() {return input;}
	@Nullable
	public Enchantment getEnchantment() {return enchantment;}
	public ItemStack getOutput() {return output;}

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
				return input.getItem() == stack.getItem();
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
					for (Tag nbt : EnchantedBookItem.getEnchantments(stack))
					{
						if (nbt instanceof CompoundTag compoundTag)
						{
							if (compoundTag.contains("id"))
							{
								if (ForgeRegistries.ENCHANTMENTS.containsKey(new ResourceLocation(compoundTag.getString("id"))))
								{
									list.add(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(compoundTag.getString("id"))));
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

	public boolean compareEnchantment(ListTag nbt)
	{
		if (input.getEnchantmentTags() == nbt)
		{
			return true;
		}
		return false;
	}

	public List<ItemStack> getComponents() {
		List<ItemStack> components = new ArrayList<>();
		components.add(chip);
		if (input == ItemStack.EMPTY) {
			ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
			book.enchant(enchantment, 1);
			components.add(book);
		} else {
			components.add(input);
		}
		return components;
	}

	@Override
	public boolean matches(Container inv, Level worldIn)
	{
		return input.getItem() == inv.getItem(0).getItem();
	}

	@Override
	public ItemStack assemble(Container inv)
	{
		return output;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		if (width == height && width == 1)
			return true;
		else
			return false;
	}

	@Override
	public ItemStack getResultItem() {return output;}

	@Override
	public ResourceLocation getId() {return id;}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return ModRecipeSerializers.CHIPALYZER;
	}

	@Override
	public RecipeType<?> getType() {return ModRecipeTypes.CHIPALYZER;}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ChipalyzerRecipe>
	{
		@Override
		public ChipalyzerRecipe fromJson(ResourceLocation recipeId, JsonObject json)
		{
			ItemStack chip = ItemStack.EMPTY;
			if (json.has("chip"))
			{
				JsonObject inputobject = json.getAsJsonObject("chip");

				if (inputobject.has("item"))
				{
					chip = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputobject.get("item").getAsString())));
				}
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
 
			if (result != null) {
				return new ChipalyzerRecipe(recipeId, chip, input, enchantment, new ItemStack(result));
			} else {
				throw new IllegalStateException("Item did not exist:" + recipeId.toString());
			}
		}

		@Override
		public ChipalyzerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
		{
			ItemStack chip = buffer.readItem();
			ItemStack input = buffer.readItem();
			String ench = buffer.readUtf();
			Enchantment enchantment = null;
			if(!ench.equals("null"))
			{
				enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(ench));
			}
			ItemStack result = buffer.readItem();
			return new ChipalyzerRecipe(recipeId, chip, input, enchantment, result);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, ChipalyzerRecipe recipe)
		{
			buffer.writeItem(recipe.chip);
			buffer.writeItem(recipe.input);
			if(recipe.enchantment != null)
			{
				buffer.writeUtf(recipe.enchantment.getRegistryName().toString());
			}
			else
			{
				buffer.writeUtf("null");
			}
			buffer.writeItem(recipe.output);
		}

	}

}
