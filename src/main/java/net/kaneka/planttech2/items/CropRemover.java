package net.kaneka.planttech2.items;

import com.google.common.collect.Lists;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.blocks.entity.CropsBlockEntity;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CropRemover extends Item
{
	public CropRemover()
	{
		super(new Item.Properties().stacksTo(1).tab(ModCreativeTabs.MAIN).durability(1024));
		DispenserBlock.registerBehavior(this, new OptionalDispenseItemBehavior()
		{
			@Override
			protected ItemStack execute(BlockSource source, ItemStack stack)
			{
				Level level = source.getLevel();
				BlockPos target = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				this.setSuccess(applyCropRemove(level, target, stack));
				if (!level.isClientSide() && this.isSuccess())
				{
					stack.setDamageValue(stack.getDamageValue() + 1);
					if (stack.getDamageValue() >= stack.getMaxDamage())
						stack.shrink(1);
					level.levelEvent(2005, target, 0);
				}
				return stack;
			}
		});
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context)
	{
	    Level world = context.getLevel();
	    BlockPos pos = context.getClickedPos();
	    ItemStack stack = context.getItemInHand();
		Player player = context.getPlayer();
		if(!world.isClientSide && applyCropRemove(world, pos, stack))
		{
			if (player != null && !player.getAbilities().instabuild)
			{
				stack.hurtAndBreak(1, player, (player2) -> player2.broadcastBreakEvent(context.getHand()));
				if (stack.getDamageValue() >= stack.getMaxDamage())
					stack.shrink(1);
			}
			return InteractionResult.CONSUME;
		}
		return super.useOn(context);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
		tooltip.add(new TextComponent("Rightclick on cropbars to remove crop"));
		super.appendHoverText(stack, level, tooltip, flagIn);
	}

	public static boolean applyCropRemove(Level world, BlockPos pos, ItemStack stack)
	{
		BlockState state = world.getBlockState(pos);
		BlockEntity BlockEntity = world.getBlockEntity(pos);
		if(BlockEntity instanceof CropsBlockEntity cbe)
		{
			List<ItemStack> drops = Lists.newArrayList();
			int growstate = state.getValue(CropBaseBlock.GROWSTATE);
			cbe.addDrops(drops, growstate);
			for(ItemStack drop : drops)
				Block.popResource(world, pos, drop);
			world.setBlockAndUpdate(pos, ModBlocks.CROPBARS.defaultBlockState());
			return true;
		}
		return false;
	}
}
