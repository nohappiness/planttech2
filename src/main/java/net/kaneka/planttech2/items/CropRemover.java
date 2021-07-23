package net.kaneka.planttech2.items;

import com.google.common.collect.Lists;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.BlockEntity.CropsTileEntity;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.InteractionResultHolderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

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
	public InteractionResultHolderType useOn(ItemUseContext context)
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
			return InteractionResultHolderType.CONSUME;
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
		BlockEntity BlockEntity = world.getBlockEntity(pos);
		if(BlockEntity instanceof CropsTileEntity)
		{
			List<ItemStack> drops = Lists.newArrayList();
			int growstate = state.getValue(CropBaseBlock.GROWSTATE);
			((CropsTileEntity) BlockEntity).addDrops(drops, growstate);
			for(ItemStack drop : drops)
				Block.popResource(world, pos, drop);
			world.setBlockAndUpdate(pos, ModBlocks.CROPBARS.defaultBlockState());
			return true;
		}
		return false;
	}
}
