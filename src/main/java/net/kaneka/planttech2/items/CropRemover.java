package net.kaneka.planttech2.items;

import java.util.List;

import com.google.common.collect.Lists;

import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class CropRemover extends Item
{
	public CropRemover()
	{
		super(new Item.Properties().stacksTo(1).tab(ModCreativeTabs.MAIN).durability(1024));
		DispenserBlock.registerBehavior(this, new OptionalDispenseBehavior()
		{
			@Override
			protected ItemStack execute(IBlockSource source, ItemStack stack)
			{
				World world = source.getLevel();
				BlockPos target = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				this.setSuccess(applyCropRemove(world, target, stack));
				if (!world.isClientSide() && this.isSuccess())
				{
					stack.setDamageValue(stack.getDamageValue() + 1);
					if (stack.getDamageValue() >= stack.getMaxDamage())
						stack.shrink(1);
					world.levelEvent(2005, target, 0);
				}
				return stack;
			}
		});
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context)
	{
	    World world = context.getLevel();
	    BlockPos pos = context.getClickedPos();
	    ItemStack stack = context.getItemInHand();
		PlayerEntity player = context.getPlayer();
		if(!world.isClientSide && applyCropRemove(world, pos, stack))
		{
			if (player != null && !player.abilities.instabuild)
			{
				stack.hurtAndBreak(1, player, (player2) -> player2.broadcastBreakEvent(context.getHand()));
				if (stack.getDamageValue() >= stack.getMaxDamage())
					stack.shrink(1);
			}
			return ActionResultType.CONSUME;
		}
		return super.useOn(context);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new StringTextComponent("Rightclick on cropbars to remove crop"));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	public static boolean applyCropRemove(World world, BlockPos pos, ItemStack stack)
	{
		BlockState state = world.getBlockState(pos);
		TileEntity tileentity = world.getBlockEntity(pos);
		if(tileentity instanceof CropsTileEntity)
		{
			List<ItemStack> drops = Lists.newArrayList();
			int growstate = state.getValue(CropBaseBlock.GROWSTATE);
			((CropsTileEntity) tileentity).addDrops(drops, growstate);
			for(ItemStack drop : drops)
				Block.popResource(world, pos, drop);
			world.setBlockAndUpdate(pos, ModBlocks.CROPBARS.defaultBlockState());
			return true;
		}
		return false;
	}
}
