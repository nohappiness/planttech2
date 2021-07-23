package net.kaneka.planttech2.recipes.recipeclasses;

import com.google.gson.JsonObject;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.recipes.ModRecipeSerializers;
import net.kaneka.planttech2.recipes.ModRecipeTypes;
import net.kaneka.planttech2.utilities.TagUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.IRecipeSerializer;
import net.minecraft.world.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class InfuserRecipe implements IRecipe<IInventory>
{
	private final ResourceLocation id; 
	private final Item input; 
	private final Item output; 
	private final int biomass; 
	
	public InfuserRecipe(ResourceLocation id, Item input, Item output, int biomass)
	{
		this.id = id; 
		this.input = input;
		this.output = output; 
		this.biomass = biomass; 
	}
	
	public Item getInput()
	{
		return input; 
	}
	
	public ItemStack getInputStack()
	{
		return new ItemStack(input); 
	}
	
	public Item getOutput()
	{
		return output; 
	}
	
	public int getBiomass()
	{
		return biomass; 
	}

	@Override
	public boolean matches(IInventory inv, World worldIn)
	{
		return input.getItem() == inv.getItem(0).getItem();
	}

	@Override
	public ItemStack assemble(IInventory inv)
	{
		return new ItemStack(output);
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		if(width == height && width == 1) return true; 
		else return false; 
	}

	@Override
	public ItemStack getResultItem()
	{
		return new ItemStack(output);
	}

	@Override
	public ResourceLocation getId()
	{
		return id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer()
	{
		return ModRecipeSerializers.INFUSER;
	}

	@Override
	public IRecipeType<?> getType()
	{
		return ModRecipeTypes.INFUSING;
	}

	
	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<InfuserRecipe>
	{
		//private static ResourceLocation NAME = new ResourceLocation(PlantTechMain.MODID, "infusing");

		@Override
		public InfuserRecipe fromJson(ResourceLocation recipeId, JsonObject json)
		{

			JsonObject inputobject = json.getAsJsonObject("input");
			Item input = null;
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
			
			int biomass = inputobject.get("biomass").getAsInt(); 

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

			if (input != null && result != null)
			{
				//System.out.println(recipeId);
				return new InfuserRecipe(recipeId, input, result, biomass);
			} else
			{
				throw new IllegalStateException("Item did not exist:" + recipeId.toString());
			}
		}

		@Override
		public InfuserRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer)
		{
			Item input = buffer.readItem().getItem();
			Item result = buffer.readItem().getItem();
			int biomass = buffer.readInt();
			return new InfuserRecipe(recipeId, input, result, biomass);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, InfuserRecipe recipe)
		{
			buffer.writeItem(new ItemStack(recipe.input));
			buffer.writeItem(new ItemStack(recipe.output));
			buffer.writeInt(recipe.biomass);
		}

	}

}
