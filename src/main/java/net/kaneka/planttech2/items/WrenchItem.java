package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.ElectricFenceGate;
import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.kaneka.planttech2.blocks.machines.CableBlock;
import net.kaneka.planttech2.blocks.machines.MachineBaseBlock;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class WrenchItem extends Item
{

	public WrenchItem()
	{
		super(new Item.Properties().stacksTo(1).tab(ModCreativeTabs.MAIN));
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx)
	{
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		Player player = ctx.getPlayer();
		if (!level.isClientSide)
		{
			BlockState target = level.getBlockState(pos);
			ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

			if (stack.getItem() instanceof WrenchItem && player.isCrouching())
			{
				Block block = target.getBlock();
				if (removeIfValid(block, level, pos))
				{
					if (block instanceof BaseElectricFence || block instanceof ElectricFenceGate)
					{
						if (!player.addItem(new ItemStack(block)))
						{
							Block.popResource(level, player.blockPosition(), new ItemStack(block));
						}
						return InteractionResult.SUCCESS;
					}
					Block.popResource(level, pos, new ItemStack(target.getBlock()));
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.useOn(ctx);
	}

	private boolean removeIfValid(Block block, Level world, BlockPos pos)
	{
		if (block instanceof MachineBaseBlock || block instanceof CableBlock || block instanceof BaseElectricFence || block instanceof ElectricFenceGate)
		{
			world.removeBlock(pos, false);
			return true;
		}
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn)
	{
		tooltip.add(new TextComponent(new TranslatableComponent("info.wrench_cable").getString()));
		tooltip.add(new TextComponent(""));
		tooltip.add(new TextComponent(new TranslatableComponent("info.wrench_dismantle").getString()));
	}

}
