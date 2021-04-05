package net.kaneka.planttech2.blocks.machines;

import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyInventoryTileEntity;
import net.kaneka.planttech2.tileentity.machine.baseclasses.EnergyTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class MachineBaseBlock extends Block
{
	private final Supplier<? extends TileEntity> teCreator;
	private final int tier;
	public MachineBaseBlock(Supplier<? extends TileEntity> teCreator, int tier)
	{
		super(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0f, 10.0f).notSolid());
		this.teCreator = teCreator;
		this.tier = tier;
	}

	public MachineBaseBlock(Supplier<? extends TileEntity> teCreator)
	{
		this(teCreator, 0);
	}

	public IItemProvider getItemDropped(BlockState state, World worldIn, BlockPos pos, int fortune)
	{
		return this;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		return new ItemStack(this);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray)
	{
		if (!world.isRemote)
		{
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof EnergyTileEntity)
			{
				EnergyTileEntity energyTileEntity = (EnergyTileEntity) te;
				player.openContainer(energyTileEntity);
				if (energyTileEntity.requireSyncOnOpen())
					world.notifyBlockUpdate(pos, state, state, 3);
			}
			return ActionResultType.CONSUME;
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return teCreator.get();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			if (worldIn.getTileEntity(pos) instanceof EnergyInventoryTileEntity)
			{
				EnergyInventoryTileEntity te = (EnergyInventoryTileEntity) worldIn.getTileEntity(pos);
				List<ItemStack> toSpawn = te.getInventoryContent();
				for (ItemStack stack : toSpawn)
				{
					worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
				}
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(new StringTextComponent(new TranslationTextComponent("info.tier").getString() + ": " + tier));
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
