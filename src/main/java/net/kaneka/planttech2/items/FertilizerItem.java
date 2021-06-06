package net.kaneka.planttech2.items;

import java.util.List;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FertilizerItem extends Item
{

    public FertilizerItem(ItemGroup group)
    {
		super(new Item.Properties().tab(group));
		DispenserBlock.registerBehavior(this, new OptionalDispenseBehavior()
		{
			@Override
			protected ItemStack execute(IBlockSource source, ItemStack stack)
			{
				World world = source.getLevel();
				BlockPos target = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				this.setSuccess(applyFertillizer(world, target, stack));
				if (!world.isClientSide() && this.isSuccess())
					world.levelEvent(2005, target, 0);
				return stack;
			}
		});
    }

    @Override
    public ActionResultType useOn(ItemUseContext context)
    {
		ItemStack stack = context.getItemInHand();
		BlockPos pos = context.getClickedPos();
		World world = context.getLevel();
		if (!world.isClientSide())
			if (applyFertillizer(world, pos, stack))
			{
				if (context.getPlayer() != null && !context.getPlayer().abilities.instabuild)
					stack.shrink(1);
				return ActionResultType.CONSUME;
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
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.fertilizer").getString() + ": " + (int) (getIncreaseChance(stack.getItem()) * 100) + "%"));
		if(stack.getItem() == ModItems.FERTILIZER_CREATIVE)
			tooltip.add(new StringTextComponent(new TranslationTextComponent("info.fertilizer_creative").getString()));
    }

    public static boolean applyFertillizer(World world, BlockPos pos, ItemStack stack)
	{
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof CropBaseBlock)
		{
			if (random.nextFloat() < getIncreaseChance(stack.getItem()))
			{
				if (stack.getItem() != ModItems.FERTILIZER_CREATIVE)
				{
					TileEntity te = world.getBlockEntity(pos);
					if (te instanceof CropsTileEntity)
						((CropBaseBlock) block).updateCrop(world, pos, ((CropsTileEntity) te).getTraits());
				}
				else
					((CropBaseBlock) block).updateCreative(world, pos);
			}
			return true;
		}
		return false;
	}

}
