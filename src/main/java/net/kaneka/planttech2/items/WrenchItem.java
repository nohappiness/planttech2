package net.kaneka.planttech2.items;

import net.kaneka.planttech2.blocks.ElectricFenceGate;
import net.kaneka.planttech2.blocks.baseclasses.AbstractElectricFence;
import net.kaneka.planttech2.blocks.machines.MachineBaseBlock;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class WrenchItem extends BaseItem
{

	public WrenchItem()
	{
		super("wrench", new Item.Properties().maxStackSize(1).group(ModCreativeTabs.groupmain));
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		World world = ctx.getWorld();
		BlockPos pos = ctx.getPos();
		PlayerEntity player = ctx.getPlayer();
		if (!world.isRemote)
		{
			BlockState target = world.getBlockState(pos);
			ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);

			if (stack.getItem() instanceof WrenchItem && player.isCrouching())
			{
				Block block = target.getBlock();
				if (removeIfValid(block, world, pos))
				{
					if (block instanceof AbstractElectricFence || block instanceof ElectricFenceGate)
					{
						if (!player.addItemStackToInventory(new ItemStack(block)))
						{
							Block.spawnAsEntity(world, player.getPosition(), new ItemStack(block));
						}
						return ActionResultType.SUCCESS;
					}
					Block.spawnAsEntity(world, pos, new ItemStack(target.getBlock()));
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.onItemUse(ctx);
	}

	private boolean removeIfValid(Block block, World world, BlockPos pos)
	{
		if (block instanceof MachineBaseBlock || block instanceof AbstractElectricFence || block instanceof ElectricFenceGate)
		{
			world.removeBlock(pos, false);
			return true;
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new StringTextComponent("rightclick on cable connections to rotate"));
		tooltip.add(new StringTextComponent("shift-rightclick on pt2 machines to dismantle"));
	}

}
