package net.kaneka.planttech2.recipes.recipeclasses;

import com.google.gson.JsonObject;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.recipes.ModRecipeSerializers;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.utilities.TagUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;

public class CompressorRecipe<CompressorRec> implements IRecipe
{
	private final ResourceLocation id;
	private final ItemStack input;
	private final ItemStack output;

	public CompressorRecipe(ResourceLocation id, ItemStack input, ItemStack output) 
	{
		this.id = id;
		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn)
	{
		return input.getItem() == inv.getStackInSlot(0).getItem();
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv)
	{
		return output.copy();
	}

	@Override
	public boolean canFit(int width, int height)
	{
		return true;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return output.copy();
	}

	public int getAmountInput()
	{
		return input.getCount();
	}

	@Override
	public ResourceLocation getId()
	{
		return id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ModRecipeSerializers.COMPRESSING;
	}

	@Override
	public IRecipeType func_222127_g()
	{
		//TODO
		return null;
	}

	public static class Serializer implements IRecipeSerializer<CompressorRecipe>
	{
		private static ResourceLocation NAME = new ResourceLocation(PlantTechMain.MODID, "compressing");

		@Override
		public CompressorRecipe read(ResourceLocation recipeId, JsonObject json)
		{

			JsonObject inputobject = json.getAsJsonObject("input");
			Item inputitem = null;
			if (inputobject.has("item"))
			{
				inputitem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputobject.get("item").getAsString()));
			} else if (inputobject.has("block"))// Just in case
			{
				inputitem = ForgeRegistries.ITEMS
				        .getValue(new ResourceLocation(inputobject.get("block").getAsString()));
			} else if (inputobject.has("tag"))
			{
				inputitem = TagUtils.getAnyTagItem(new ResourceLocation(inputobject.get("tag").getAsString()));
			}
			ItemStack inputstack = null;
			if (inputitem != null)
			{
				inputstack = new ItemStack(inputitem, JSONUtils.getInt(inputobject, "amount", 1));
			}

			JsonObject resultobject = json.getAsJsonObject("result");
			Item resultitem = null;
			if (resultobject.has("item"))
			{
				resultitem = ForgeRegistries.ITEMS
				        .getValue(new ResourceLocation(resultobject.get("item").getAsString()));
			} else if (resultobject.has("block"))// Just in case
			{
				resultitem = ForgeRegistries.ITEMS
				        .getValue(new ResourceLocation(resultobject.get("block").getAsString()));
			} else if (resultobject.has("tag"))
			{
				resultitem = TagUtils.getAnyTagItem(new ResourceLocation(resultobject.get("tag").getAsString()));
			}

			ItemStack resultstack = null;
			if (resultitem != null)
			{
				resultstack = new ItemStack(resultitem, JSONUtils.getInt(resultobject, "amount", 1));
			}

			if (inputstack != null && resultstack != null)
			{
				return new CompressorRecipe(recipeId, inputstack, resultstack);
			} else
			{
				throw new IllegalStateException("Item did not exist:" + recipeId.toString());
			}
		}

		@Override
		public CompressorRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
		{
			ItemStack input = buffer.readItemStack();
			ItemStack result = buffer.readItemStack();
			return new CompressorRecipe(recipeId, input, result);
		}

		@Override
		public void write(PacketBuffer buffer, CompressorRecipe recipe)
		{
			buffer.writeItemStack(recipe.input);
			buffer.writeItemStack(recipe.output);
		}
		
		public ResourceLocation getName()
		{
			return NAME;
		}


		

	}
}
