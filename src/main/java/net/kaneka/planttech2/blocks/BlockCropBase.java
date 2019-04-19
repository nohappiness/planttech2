package net.kaneka.planttech2.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.items.ItemAnalyser;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.TileEntityCrops;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockCropBase extends BlockContainer
{
	public static final IntegerProperty GROWSTATE = IntegerProperty.create("growstate", 0, 7);
	private String entryName;

	public BlockCropBase(String entryName)
	{
		super(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(0.5f));
		this.entryName = entryName;
		setRegistryName(entryName + "_crop");
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new TileEntityCrops();
	}

	public void updateCrop(World world, BlockPos pos, HashMapCropTraits traits)
	{
		IBlockState state = world.getBlockState(pos);
		int growstate = state.get(GROWSTATE).intValue();
		if (growstate < 7)
		{
			if (canGrow(world, pos, traits))
			{
				world.setBlockState(pos, state.with(GROWSTATE, Integer.valueOf(growstate) + 1));
				// world.setBlockState(pos, state.withProperty(GROWSTATE,
				// 7).withProperty(PLANTTYPE,
				// PlanttechMain.instance.croplist.getEntryByName(traits.getType())));
			}
		} else
		{
			List<BlockPos> neighborpos = getNeighborBlockPosRandom(pos);
			for (BlockPos blockpos : neighborpos)
			{
				if (world.getBlockState(blockpos).getBlock() == ModBlocks.CROPBARS)
				{
					List<BlockPos> cropbarneighbors = getNeighborBlockPosRandomExeptOne(blockpos, pos);
					for (BlockPos possiblePartner : cropbarneighbors)
					{
						if (world.getBlockState(possiblePartner).getBlock() instanceof BlockCropBase)
						{
							if (world.getTileEntity(possiblePartner) instanceof TileEntityCrops)
							{
								HashMapCropTraits partnertraits = ((TileEntityCrops) world.getTileEntity(possiblePartner)).getTraits();
								world.setBlockState(blockpos, getDefaultState());
								if (world.getTileEntity(blockpos) instanceof TileEntityCrops && world.getTileEntity(pos) instanceof TileEntityCrops)
								{
									((TileEntityCrops) world.getTileEntity(blockpos))
									        .setTraits(((TileEntityCrops) world.getTileEntity(pos)).getTraits().calculateNewTraits(partnertraits));
									break;
								}
							}
						}
					}
					if (!(world.getBlockState(blockpos).getBlock() instanceof BlockCropBase))
					{
						world.setBlockState(blockpos, getDefaultState());
						if (world.getTileEntity(blockpos) instanceof TileEntityCrops && world.getTileEntity(pos) instanceof TileEntityCrops)
						{
							((TileEntityCrops) world.getTileEntity(blockpos)).setTraits(((TileEntityCrops) world.getTileEntity(pos)).getTraits().copy());
						}
					}
					break;
				}
			}

		}
		// world.setBlockState(pos, state.withProperty(GROWSTATE,
		// 7).withProperty(PLANTTYPE,
		// PlanttechMain.instance.croplist.getEntryByName(traits.getType())));

	}

	public void updateCreative(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		int growstate = state.get(GROWSTATE).intValue();
		if (growstate < 7)
		{
			world.setBlockState(pos, state.with(GROWSTATE, 7));
		}
	}

	private List<BlockPos> getNeighborBlockPosRandom(BlockPos pos)
	{
		List<BlockPos> neighbors = new ArrayList<BlockPos>();
		neighbors.add(pos.north());
		neighbors.add(pos.east());
		neighbors.add(pos.south());
		neighbors.add(pos.west());
		Collections.shuffle(neighbors);
		return neighbors;
	}

	private List<BlockPos> getNeighborBlockPosRandomExeptOne(BlockPos pos, BlockPos exept)
	{
		List<BlockPos> neighbors = getNeighborBlockPosRandom(pos);
		neighbors.remove(exept);
		return neighbors;
	}

	private boolean canGrow(World world, BlockPos pos, HashMapCropTraits traits)
	{
		if (!enoughtLight(world, pos, traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY)))
			return false;
		if (!enoughtWater(world, pos, traits.getTrait(EnumTraitsInt.WATERSENSITIVITY)))
			return false;
		if (!rightSoil(world, pos, traits.getType()))
			return false;
		if (!rightTemperature(world, pos, traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE)))
			return false;
		return true;
	}

	public String[] canGrowString(World world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		String[] messages = new String[10];
		if (te instanceof TileEntityCrops)
		{
			HashMapCropTraits traits = ((TileEntityCrops) te).getTraits();
			if (!enoughtLight(world, pos, traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY)))
				messages[1] = "Not enought light";
			if (!enoughtWater(world, pos, traits.getTrait(EnumTraitsInt.WATERSENSITIVITY)))
				messages[2] = "Not enought water";
			if (!rightSoil(world, pos, traits.getType()))
				messages[3] = "Not right soil";
			if (!rightTemperature(world, pos, traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE)))
				messages[4] = "Not right temperature";
		} else
		{
			messages[0] = "error";
		}
		return messages;
	}

	public boolean enoughtLight(World world, BlockPos pos, int lightsensitivity)
	{
		if (!world.isAreaLoaded(pos, 1))// prevent loading unloaded chunks
		{
			return false;
		}
		if (world.getLightSubtracted(pos, 0) >= (14 - lightsensitivity))
		{
			return true;
		}
		return false;
	}

	public boolean enoughtWater(World world, BlockPos pos, int waterSensitivity)
	{
		for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(((-1) * (waterSensitivity + 1)), 0, ((-1) * (waterSensitivity + 1))),
		        pos.add((waterSensitivity + 1), -1, (waterSensitivity + 1))))
		{
			if (world.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.WATER)
			{
				return true;
			}
		}

		return false;
	}

	public boolean rightSoil(World world, BlockPos pos, String name)
	{
		ItemStack stack = PlantTechMain.croplist.getEntryByName(name).getSoil();
		if (stack.isEmpty())
		{
			return true;
		} else if (stack.getItem() instanceof ItemBlock)
		{
			Block block = ((ItemBlock) stack.getItem()).getBlock();
			IBlockState state = world.getBlockState(pos.down());
			if (state.getBlock() == block)
			{
				return true;
			}
			return false;
		}
		return false;

	}

	public boolean rightTemperature(World world, BlockPos pos, String name, int tolerance)
	{
		EnumTemperature temp = PlantTechMain.croplist.getEntryByName(name).getTemperature();
		if (temp.inRange(world.getBiome(pos).getDefaultTemperature(), tolerance))
		{
			return true;
		}
		return false;
	}

	@Override
	protected void fillStateContainer(Builder<Block, IBlockState> builder)
	{
		builder.add(GROWSTATE);
	}

	/*----------------------DROPS---------------------------*/

	@Override
	public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int fortune)
	{
		int growstate = state.get(GROWSTATE).intValue();
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityCrops)
		{
			((TileEntityCrops) te).addDrops(drops, growstate);
			drops.add(new ItemStack(ModBlocks.CROPBARS));
		} else
		{
			super.getDrops(state, drops, world, pos, fortune);
		}

	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest, IFluidState fluid)
	{
		if (willHarvest && !player.isCreative())
		{
			return true;
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		super.harvestBlock(world, player, pos, state, te, stack);
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 1);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		int growstate = state.get(GROWSTATE).intValue();
		if (growstate > 6 && hand.equals(EnumHand.MAIN_HAND) && !worldIn.isRemote)
		{
			ItemStack holdItem = player.getHeldItem(EnumHand.MAIN_HAND);
			if (!holdItem.isEmpty())
			{
				if (holdItem.getItem() instanceof ItemAnalyser)
				{
					return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
				}
			}
			NonNullList<ItemStack> drops = NonNullList.create();
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityCrops)
			{
				((TileEntityCrops) te).dropsRemoveOneSeed(drops, growstate);
				for (ItemStack stack : drops)
				{
					spawnAsEntity(worldIn, pos, stack);
				}
				worldIn.setBlockState(pos, state.with(GROWSTATE, 0));
			}
		}
		return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
	}

	/*----------------------RENDERING------------------*/
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isSolid(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockReader world, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState iBlockState)
	{
		return EnumBlockRenderType.MODEL;
	}

	public int getOpacity(IBlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return 0;
	}

	public String getEntryName()
	{
		return entryName;
	}

	public static boolean isOpaque(VoxelShape shape)
	{
		return true;
	}

	public static class ColorHandler implements IBlockColor
	{

		@Override
		public int getColor(IBlockState state, IWorldReaderBase world, BlockPos pos, int tintindex)
		{
			if (tintindex == 0)
			{
				return PlantTechMain.croplist.getEntryByName(((BlockCropBase) state.getBlock()).getEntryName()).getSeedColor();
			}
			return 0xFFFFFFFF;

		}

	}
}
