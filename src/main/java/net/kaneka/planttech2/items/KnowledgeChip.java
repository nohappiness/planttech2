package net.kaneka.planttech2.items;

import java.util.List;

import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class KnowledgeChip extends BaseItem
{		
	private int tier; 
	private int maxKnowledge;
	
	public KnowledgeChip(int tier, int maxKnowledge)
	{
		super("knowledgechip_" + String.valueOf(tier), new Item.Properties().maxStackSize(1).group(ModCreativeTabs.groupmain));
		this.tier = tier;
		this.maxKnowledge = maxKnowledge; 
	}
	
	public int getTier()
	{
		return tier; 
	}
	
	public int getMaxKnowledge()
	{
		return maxKnowledge; 
	}
	
	public static int getMaxTier()
	{
		return 5; 
	}
	
	public static ItemStack addKnowledge(ItemStack stack, int amount, int maxTier)
	{
		if(!stack.isEmpty())
		{
			Item item = stack.getItem(); 
			if(item instanceof KnowledgeChip)
			{
				KnowledgeChip chip = (KnowledgeChip) item; 
				if(chip.getTier() <= maxTier)
				{
					CompoundNBT nbt = stack.getOrCreateTag();
					int newknowledge = 0; 
					if(nbt.contains("knowledge"))
					{
						newknowledge = nbt.getInt("knowledge") + amount; 
					}
					else
					{
						newknowledge = amount; 
					}
					
					if(newknowledge < chip.getMaxKnowledge())
					{
						nbt.putInt("knowledge", newknowledge);
						stack.setTag(nbt);
					}
					else
					{
						ItemStack nextChip = getByTier(chip.tier + 1);
						if(!nextChip.isEmpty())
						{
							
							return nextChip; 
						}
						else
						{
							nbt.putInt("knowledge", chip.getMaxKnowledge());
							stack.setTag(nbt);
						}
					}
				}
			}
		}
		return stack; 
	}
	
	public static ItemStack getByTier(int tier)
	{
		switch(tier)
		{
		case 0: return new ItemStack(ModItems.KNOWLEDGECHIP_TIER_0);  
		case 1: return new ItemStack(ModItems.KNOWLEDGECHIP_TIER_1);  
		case 2: return new ItemStack(ModItems.KNOWLEDGECHIP_TIER_2);  
		case 3: return new ItemStack(ModItems.KNOWLEDGECHIP_TIER_3);  
		case 4: return new ItemStack(ModItems.KNOWLEDGECHIP_TIER_4);  
		case 5: return new ItemStack(ModItems.KNOWLEDGECHIP_TIER_5);  
		}
		return ItemStack.EMPTY; 
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		CompoundNBT nbt = stack.getOrCreateTag();
		if(nbt.contains("knowledge"))
		{
			tooltip.add(new StringTextComponent(new TranslationTextComponent("info.knowledge").getUnformattedComponentText() + ": " + nbt.getInt("knowledge") + "/" + getMaxKnowledge()));
		}
		else
		{
			tooltip.add(new StringTextComponent(new TranslationTextComponent("info.knowledge").getUnformattedComponentText() + ": 0/" + getMaxKnowledge()));
		}
	}

}
