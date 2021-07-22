package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.ElectricFenceGate;
import net.kaneka.planttech2.blocks.baseclasses.BaseElectricFence;
import net.kaneka.planttech2.blocks.machines.CableBlock;
import net.kaneka.planttech2.blocks.machines.MachineBaseBlock;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseContext;
import net.minecraft.util.InteractionResultHolderType;
import net.minecraft.util.Hand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import InteractionResultHolderType;

public class WrenchItem extends Item
{

	public WrenchItem()
	{
		super(new Item.Properties().stacksTo(1).tab(ModCreativeTabs.MAIN));
	}

	@Override
	public InteractionResultHolderType useOn(ItemUseContext ctx)
	{
		World world = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		PlayerEntity player = ctx.getPlayer();
		if (!world.isClientSide)
		{
			BlockState target = world.getBlockState(pos);
			ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);

			if (stack.getItem() instanceof WrenchItem && player.isCrouching())
			{
				Block block = target.getBlock();
				if (removeIfValid(block, world, pos))
				{
					if (block instanceof BaseElectricFence || block instanceof ElectricFenceGate)
					{
						if (!player.addItem(new ItemStack(block)))
						{
							Block.popResource(world, player.blockPosition(), new ItemStack(block));
						}
						return InteractionResultHolderType.SUCCESS;
					}
					Block.popResource(world, pos, new ItemStack(target.getBlock()));
					return InteractionResultHolderType.SUCCESS;
				}
			}
		}
		return super.useOn(ctx);
	}

	private boolean removeIfValid(Block block, World world, BlockPos pos)
	{
		if (block instanceof MachineBaseBlock || block instanceof CableBlock || block instanceof BaseElectricFence || block instanceof ElectricFenceGate)
		{
			world.removeBlock(pos, false);
			return true;
		}
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.wrench_cable").getString()));
		tooltip.add(new StringTextComponent(""));
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.wrench_dismantle").getString()));
	}

}
