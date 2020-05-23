package net.kaneka.planttech2.recipes.recipeclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private final ItemStack chip;
	private final ItemStack input;
	private final Enchantment enchantment;
	private final ItemStack output;

	public ChipalyzerRecipe(ResourceLocation id, ItemStack chip, ItemStack input, Enchantment enchantment, ItemStack output)
	{
		this.id = id;
		this.chip = chip;
		this.input = input;
		this.enchantment = enchantment;
		this.output = output;
	}
	public ItemStack getChip() {return chip;}
	public ItemStack getInput() {return input;}
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

	public List<ItemStack> getComponents() {
		List<ItemStack> components = new ArrayList<>();
		components.add(chip);
		if (input == ItemStack.EMPTY) {
			ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
			book.addEnchantment(enchantment, 1);
			components.add(book);
		} else {
			components.add(input);
		}
		return components;
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
	public ItemStack getRecipeOutput() {return output;}

	@Override
	public ResourceLocation getId() {return id;}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ForgeRegistries.RECIPE_SERIALIZERS.getValue(new ResourceLocation(PlantTechMain.MODID, "chipalyzer"));
	}

	@Override
	public IRecipeType<?> getType() {return ModRecipeTypes.CHIPALYZER;}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ChipalyzerRecipe>
	{
		@Override
		public ChipalyzerRecipe read(ResourceLocation recipeId, JsonObject json)
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
		public ChipalyzerRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			ItemStack chip = buffer.readItemStack();
			ItemStack input = buffer.readItemStack();
			String ench = buffer.readString();
			Enchantment enchantment = null;
			if(!ench.equals("null"))
			{
				enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(ench));
			}
			ItemStack result = buffer.readItemStack();
			return new ChipalyzerRecipe(recipeId, chip, input, enchantment, result);
		}

		@Override
		public void write(PacketBuffer buffer, ChipalyzerRecipe recipe)
		{
			buffer.writeItemStack(recipe.chip);
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
