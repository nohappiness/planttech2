package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.BlockEntity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.kaneka.planttech2.BlockEntity.machine.baseclasses.EnergyBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockRenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.BlockEntity.BlockEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BlockGetter;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class MachineBaseBlock extends Block
{
	private final Supplier<? extends BlockEntity> teCreator;
	private final int tier;
	public MachineBaseBlock(Supplier<? extends BlockEntity> teCreator, int tier)
	{
		super(Block.Properties.of(Material.METAL).strength(5.0f, 10.0f).noOcclusion());
		this.teCreator = teCreator;
		this.tier = tier;
	}

	public MachineBaseBlock(Supplier<? extends BlockEntity> teCreator)
	{
		this(teCreator, 0);
	}

	public IItemProvider getItemDropped(BlockState state, World worldIn, BlockPos pos, int fortune)
	{
		return this;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, BlockGetter world, BlockPos pos, PlayerEntity player)
	{
		return new ItemStack(this);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult ray)
	{
		if (!world.isClientSide)
		{
			BlockEntity te = world.getBlockEntity(pos);
			if (te instanceof EnergyBlockEntity)
			{
				EnergyBlockEntity energyBlockEntity = (EnergyBlockEntity) te;
				player.openMenu(energyBlockEntity);
				if (energyBlockEntity.requireSyncOnOpen())
					world.sendBlockUpdated(pos, state, state, 3);
			}
			return ActionResultType.CONSUME;
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public boolean hasBlockEntity(BlockState state)
	{
		return true;
	}

	@Override
	public BlockEntity createBlockEntity(BlockState state, BlockGetter world)
	{
		return teCreator.get();
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			if (worldIn.getBlockEntity(pos) instanceof EnergyInventoryBlockEntity)
			{
				EnergyInventoryBlockEntity te = (EnergyInventoryBlockEntity) worldIn.getBlockEntity(pos);
				List<ItemStack> toSpawn = te.getInventoryContent();
				for (ItemStack stack : toSpawn)
				{
					worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
				}
			}
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, BlockGetter worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.tier").getString() + ": " + tier));
		
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

}
