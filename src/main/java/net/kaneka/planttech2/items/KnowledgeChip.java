package net.kaneka.planttech2.items;

import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class KnowledgeChip extends Item
{		
	private final int tier;
	private final int maxKnowledge;
	
	public KnowledgeChip(int tier, int maxKnowledge)
	{
		super(new Item.Properties().stacksTo(1).tab(ModCreativeTabs.MAIN));
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
			if(item instanceof KnowledgeChip chip)
			{
				if(chip.getTier() <= maxTier)
				{
					CompoundTag nbt = stack.getOrCreateTag();
					int newknowledge;
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
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
		CompoundTag nbt = stack.getOrCreateTag();
		if(nbt.contains("knowledge"))
		{
			tooltip.add(new TextComponent(new TranslatableComponent("info.knowledge").getString() + ": " + nbt.getInt("knowledge") + "/" + getMaxKnowledge()));
		}
		else
		{
			tooltip.add(new TextComponent(new TranslatableComponent("info.knowledge").getString() + ": 0/" + getMaxKnowledge()));
		}
	}

}
