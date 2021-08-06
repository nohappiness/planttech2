package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.blocks.ModBlockEntityBlock;
import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyBlockEntity;
import net.kaneka.planttech2.blocks.entity.machine.baseclasses.EnergyInventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiFunction;

public class MachineBaseBlock extends ModBlockEntityBlock
{
	private final BiFunction<BlockPos, BlockState, ? extends BlockEntity> teCreator;
	private final int tier;

	public MachineBaseBlock(BiFunction<BlockPos, BlockState, ? extends BlockEntity> teCreator)
	{
		this(teCreator, 0);
	}

	public MachineBaseBlock(BiFunction<BlockPos, BlockState, ? extends BlockEntity> teCreator, int tier)
	{
		super(Block.Properties.of(Material.METAL).strength(5.0f, 10.0f).noOcclusion());
		this.teCreator = teCreator;
		this.tier = tier;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player)
	{
		return new ItemStack(this);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray)
	{
		if (!level.isClientSide)
		{
			BlockEntity te = level.getBlockEntity(pos);
			if (te instanceof EnergyInventoryBlockEntity eibe)
			{
				player.openMenu(eibe);
				if (eibe.requireSyncUponOpen())
					level.sendBlockUpdated(pos, state, state, 3);
			}
			return InteractionResult.CONSUME;
		}
		return InteractionResult.SUCCESS;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if(!level.isClientSide) return EnergyBlockEntity::tick;
		return null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return teCreator.apply(pos, state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			if (level.getBlockEntity(pos) instanceof EnergyInventoryBlockEntity eibe)
			{
				List<ItemStack> toSpawn = eibe.getInventoryContent();
				for (ItemStack stack : toSpawn)
					level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flagIn)
	{
		tooltip.add(new TextComponent(new TranslatableComponent("info.tier").getString() + ": " + tier));
		
		super.appendHoverText(stack, level, tooltip, flagIn);
	}

}
