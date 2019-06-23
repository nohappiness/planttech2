package net.kaneka.planttech2.items;

import java.util.List;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FertilizerItem extends BaseItem
{

    public FertilizerItem(String name, ItemGroup group)
    {
	super(name, new Item.Properties().group(group));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
	ItemStack stack = context.getItem();
	BlockPos pos = context.getPos();
	World world = context.getWorld();
	Block block = world.getBlockState(pos).getBlock();
	if (block instanceof CropBaseBlock)
	{
	    if(!context.getPlayer().isCreative())stack.shrink(1);
	    
	    if (random.nextFloat() < getIncreaseChance(stack.getItem()) && stack.getItem() != ModItems.FERTILIZER_CREATIVE)
	    {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof CropsTileEntity)
		{
		    ((CropBaseBlock) block).updateCrop(world, pos, ((CropsTileEntity) te).getTraits());
		}
	    }
	    else if(stack.getItem() == ModItems.FERTILIZER_CREATIVE)
	    {
		((CropBaseBlock) block).updateCreative(world, pos);
	    }
	}
	return super.onItemUse(context);
    }

    public float getIncreaseChance(Item item)
    {
	if (item == ModItems.FERTILIZER_TIER_1)
	{
	    return 0.05F;
	}
	else if (item == ModItems.FERTILIZER_TIER_2)
	{
	    return 0.15F;
	}
	else if (item == ModItems.FERTILIZER_TIER_3)
	{
	    return 0.35F;
	}
	else if (item == ModItems.FERTILIZER_TIER_4)
	{
	    return 0.75F;
	}
	else if (item == ModItems.FERTILIZER_CREATIVE)
	{
	    return 1F;
	}
	return 0F;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(new StringTextComponent(new TranslationTextComponent("info.fertilizer").getUnformattedComponentText() + ": " + (getIncreaseChance(stack.getItem()) * 100) + "%"));
	if(stack.getItem() == ModItems.FERTILIZER_CREATIVE)
	{
	    tooltip.add(new StringTextComponent(new TranslationTextComponent("info.fertilizer_creative").getUnformattedComponentText()));
		 
	}
    
    }

}
