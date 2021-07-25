package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.blocks.entity.CropsBlockEntity;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Random;

public class FertilizerItem extends Item
{

    public FertilizerItem(CreativeModeTab group)
    {
		super(new Item.Properties().tab(group));
		DispenserBlock.registerBehavior(this, new OptionalDispenseItemBehavior()
		{
			@Override
			protected ItemStack execute(BlockSource source, ItemStack stack)
			{
				Level level = source.getLevel();
				BlockPos target = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				this.setSuccess(applyFertillizer(level, target, stack));
				if (!level.isClientSide() && this.isSuccess())
					level.levelEvent(2005, target, 0);
				return stack;
			}
		});
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
		ItemStack stack = context.getItemInHand();
		BlockPos pos = context.getClickedPos();
		Level level = context.getLevel();
		if (!level.isClientSide())
			if (applyFertillizer(level, pos, stack))
			{
				if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild)
					stack.shrink(1);
				return InteractionResult.CONSUME;
			}
		return super.useOn(context);
    }

    public static float getIncreaseChance(Item item)
    {
		if (item == ModItems.FERTILIZER_TIER_1)
			return 0.05F;
		else if (item == ModItems.FERTILIZER_TIER_2)
			return 0.15F;
		else if (item == ModItems.FERTILIZER_TIER_3)
			return 0.35F;
		else if (item == ModItems.FERTILIZER_TIER_4)
			return 0.75F;
		else if (item == ModItems.FERTILIZER_CREATIVE)
			return 1F;
		return 0F;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
    {
		tooltip.add(new TextComponent(new TranslatableComponent("info.fertilizer").getString() + ": " + (int) (getIncreaseChance(stack.getItem()) * 100) + "%"));
		if(stack.getItem() == ModItems.FERTILIZER_CREATIVE)
			tooltip.add(new TextComponent(new TranslatableComponent("info.fertilizer_creative").getString()));
    }

    public static boolean applyFertillizer(Level world, BlockPos pos, ItemStack stack)
	{
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof CropBaseBlock)
		{
			Random rand = new Random();
			if (rand.nextFloat() < getIncreaseChance(stack.getItem()))
			{
				if (stack.getItem() != ModItems.FERTILIZER_CREATIVE)
				{
					BlockEntity te = world.getBlockEntity(pos);
					if (te instanceof CropsBlockEntity)
						((CropBaseBlock) block).updateCrop(world, pos, ((CropsBlockEntity) te).getTraits());
				}
				else
					((CropBaseBlock) block).updateCreative(world, pos);
			}
			return true;
		}
		return false;
	}

}
