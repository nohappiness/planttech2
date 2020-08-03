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

public class CropRemover extends BaseItem
{
	public CropRemover()
	{
		super("cropremover", new Item.Properties().maxStackSize(1).group(ModCreativeTabs.groupmain).maxDamage(1024));
		DispenserBlock.registerDispenseBehavior(this, new OptionalDispenseBehavior()
		{
			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
			{
				World world = source.getWorld();
				BlockPos target = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
				this.setSuccessful(applyCropRemove(world, target, stack));
				if (!world.isRemote() && this.isSuccessful())
				{
					stack.setDamage(stack.getDamage() + 1);
					if (stack.getDamage() >= stack.getMaxDamage())
						stack.shrink(1);
					world.playEvent(2005, target, 0);
				}
				return stack;
			}
		});
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
	    World world = context.getWorld();
	    BlockPos pos = context.getPos();
	    ItemStack stack = context.getItem();
		PlayerEntity player = context.getPlayer();
		if(!world.isRemote && applyCropRemove(world, pos, stack))
		{
			if (player != null && !player.abilities.isCreativeMode)
			{
				stack.damageItem(1, player, (player2) -> player2.sendBreakAnimation(context.getHand()));
				if (stack.getDamage() >= stack.getMaxDamage())
					stack.shrink(1);
			}
			return ActionResultType.CONSUME;
		}
		return super.onItemUse(context);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new StringTextComponent("Rightclick on cropbars to remove crop"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	public static boolean applyCropRemove(World world, BlockPos pos, ItemStack stack)
	{
		BlockState state = world.getBlockState(pos);
		TileEntity tileentity = world.getTileEntity(pos);
		if(tileentity instanceof CropsTileEntity)
		{
			List<ItemStack> drops = Lists.newArrayList();
			int growstate = state.get(CropBaseBlock.GROWSTATE);
			((CropsTileEntity) tileentity).addDrops(drops, growstate);
			for(ItemStack drop : drops)
				Block.spawnAsEntity(world, pos, drop);
			world.setBlockState(pos, ModBlocks.CROPBARS.getDefaultState());
			return true;
		}
		return false;
	}
}
