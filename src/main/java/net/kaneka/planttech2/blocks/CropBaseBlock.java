package net.kaneka.planttech2.blocks;

import com.google.common.collect.Lists;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.items.AdvancedAnalyserItem;
import net.kaneka.planttech2.items.AnalyserItem;
import net.kaneka.planttech2.items.CropRemover;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.tileentity.CropsTileEntity;
import net.kaneka.planttech2.tileentity.machine.CropAuraGeneratorTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class CropBaseBlock extends ContainerBlock
{
	public static final IntegerProperty GROWSTATE = IntegerProperty.create("growstate", 0, 7);
	private final String entryName;

	public CropBaseBlock(String entryName)
	{
		super(Block.Properties.of(Material.WOOD).noCollission().strength(0.5f));
		this.entryName = entryName;
		setRegistryName(entryName + "_crop");
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn)
	{
		return new CropsTileEntity();
	}

	public void updateCrop(World world, BlockPos pos, HashMapCropTraits traits)
	{
		BlockState state = world.getBlockState(pos);
		int growstate = state.getValue(GROWSTATE);
		if (growstate < 7 && canGrow(world, pos, traits))
			world.setBlockAndUpdate(pos, state.setValue(GROWSTATE, growstate + 1));
		else
		{
			List<BlockPos> neighborpos = getNeighborBlockPosRandom(pos);
			for (BlockPos blockpos : neighborpos)
			{
				if (world.getBlockState(blockpos).getBlock() == ModBlocks.CROPBARS)
				{
					List<BlockPos> cropbarneighbors = getNeighborBlockPosRandomExeptOne(blockpos, pos);
					for (BlockPos possiblePartner : cropbarneighbors)
					{
						BlockState partnerState = world.getBlockState(possiblePartner);
						if (partnerState.getBlock() instanceof CropBaseBlock)
						{
							TileEntity tileEntity = world.getBlockEntity(possiblePartner);
							if (tileEntity instanceof CropsTileEntity)
							{
								HashMapCropTraits partnertraits = ((CropsTileEntity) tileEntity).getTraits();
								world.setBlockAndUpdate(blockpos, defaultBlockState());
								if (world.getBlockEntity(blockpos) instanceof CropsTileEntity && world.getBlockEntity(pos) instanceof CropsTileEntity)
								{
									((CropsTileEntity) world.getBlockEntity(blockpos))
									        .setTraits(((CropsTileEntity) world.getBlockEntity(pos)).getTraits().calculateNewTraits(partnertraits));
									break;
								}
							}
						}
					}
					if (!(world.getBlockState(blockpos).getBlock() instanceof CropBaseBlock))
					{
						world.setBlockAndUpdate(blockpos, defaultBlockState());
						if (world.getBlockEntity(blockpos) instanceof CropsTileEntity && world.getBlockEntity(pos) instanceof CropsTileEntity)
							((CropsTileEntity) world.getBlockEntity(blockpos)).setTraits(((CropsTileEntity) world.getBlockEntity(pos)).getTraits().copy());
					}
					break;
				}
			}
		}
	}

	public void updateCreative(World world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos);
		int growstate = state.getValue(GROWSTATE);
		if (growstate < 7)
			world.setBlockAndUpdate(pos, state.setValue(GROWSTATE, 7));
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
		List<CropAuraGeneratorTileEntity> generators = getCropAuraGeneratorInRadius(world, pos, 8);
		if (!enoughLight(world, pos, traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY), generators))
			return false;
		if (!enoughWater(world, pos, traits.getTrait(EnumTraitsInt.WATERSENSITIVITY), generators))
			return false;
		if (!rightSoil(world, pos, traits.getType(), generators))
			return false;
		return rightTemperature(world, pos, traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE), generators);
	}

	public String[] canGrowString(World world, BlockPos pos)
	{
		TileEntity te = world.getBlockEntity(pos);
		String[] messages = new String[10];
		if (te instanceof CropsTileEntity)
		{
			HashMapCropTraits traits = ((CropsTileEntity) te).getTraits();
			List<CropAuraGeneratorTileEntity> generators = getCropAuraGeneratorInRadius(world, pos, 8);
			if (!enoughLight(world, pos, traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY), generators))
				messages[1] = "Not enough light";
			if (!enoughWater(world, pos, traits.getTrait(EnumTraitsInt.WATERSENSITIVITY), generators))
				messages[2] = "Not enough water";
			if (!rightSoil(world, pos, traits.getType(), generators))
				messages[3] = "Not right soil";
			if (!rightTemperature(world, pos, traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE), generators))
				messages[4] = "Not right temperature";
		}
		else
			messages[0] = "error";
		return messages;
	}

	public boolean enoughLight(World world, BlockPos pos, int lightsensitivity,  List<CropAuraGeneratorTileEntity> generators)
	{
		if (!world.isAreaLoaded(pos, 1))// prevent loading unloaded chunks
			return false;
		int extraLightValueDecrease = getCropAuraInRadiusInt(generators, (generator) -> generator.lightValueDecrease);
		return world.getMaxLocalRawBrightness(pos, 0) >= 14 - lightsensitivity - extraLightValueDecrease;
	}

	public boolean enoughWater(World world, BlockPos pos, int waterSensitivity, List<CropAuraGeneratorTileEntity> generators)
	{
		int extraWaterRangeDecrease = getCropAuraInRadiusInt(generators, (generator) -> generator.waterRangeDecrease);
		waterSensitivity += extraWaterRangeDecrease + 1;
		for (BlockPos blockpos$mutableblockpos : BlockPos.betweenClosed(pos.offset(-waterSensitivity, 0, -waterSensitivity), pos.offset(waterSensitivity, -1, waterSensitivity)))
		{
			BlockState state = world.getBlockState(blockpos$mutableblockpos);
			if (state.getMaterial() == Material.WATER || (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)))
				return true;
		}
		return false;
	}

	public boolean rightSoil(World world, BlockPos pos, String name, List<CropAuraGeneratorTileEntity> generators)
	{
		Block block = PlantTechMain.getCropList().getByName(name).getConfiguration().getSoil().get();
		Block soil = world.getBlockState(pos.below()).getBlock();
		List<Block> extraSoils = getCropAuraInRadiusList(generators, (generator) -> generator.soil, (block2) -> block2 != Blocks.AIR && block2 != Blocks.CAVE_AIR && block2 != Blocks.VOID_AIR);
		return soil == block || soil == ModBlocks.UNIVERSAL_SOIL_INFUSED || extraSoils.contains(soil);
	}

	public boolean rightTemperature(World world, BlockPos pos, String name, int tolerance, List<CropAuraGeneratorTileEntity> generators)
	{
		List<EnumTemperature> extraTemperatures = getCropAuraInRadiusNonnullList(generators, (generator) -> generator.temperature);
		extraTemperatures.add(PlantTechMain.getCropList().getByName(name).getConfiguration().getTemperature());
		float biomeTemp = world.getBiomeManager().getBiome(pos).getTemperature(pos);
		return EnumTemperature.inRange(biomeTemp, tolerance, extraTemperatures);
	}

	private List<CropAuraGeneratorTileEntity> getCropAuraGeneratorInRadius(World world, BlockPos centre, int radius)
	{
		List<CropAuraGeneratorTileEntity> generators = new ArrayList<>();
		for (int x = centre.getX() - radius; x < centre.getX() + radius; x++)
			for (int y = centre.getY(); y < centre.getY() + 1; y++)
				for (int z = centre.getZ() - radius; z < centre.getZ() + radius; z++)
				{
					TileEntity te = world.getBlockEntity(new BlockPos(x, y, z));
					if (te instanceof CropAuraGeneratorTileEntity && ((CropAuraGeneratorTileEntity) te).consumeEnergy(25))
						generators.add((CropAuraGeneratorTileEntity) te);
				}
		return generators;
	}

	private int getCropAuraInRadiusInt(List<CropAuraGeneratorTileEntity> generators, Function<CropAuraGeneratorTileEntity, Integer> aura)
	{
		return getCropAuraInRadius(generators, aura, (present) -> true, (max, present) -> present > max).orElse(0);
	}

	private <T> Optional<T> getCropAuraInRadius(List<CropAuraGeneratorTileEntity> generators, Function<CropAuraGeneratorTileEntity, T> aura, Predicate<T> predicate, BiPredicate<T, T> compare)
	{
		Optional<T> highestAura = Optional.empty();
		for (T machineAura : getCropAuraInRadiusList(generators, aura, predicate))
		{
			if (highestAura.isPresent())
			{
				if (compare.test(highestAura.get(), machineAura))
					highestAura = Optional.of(machineAura);
			}
			else highestAura = Optional.of(machineAura);
		}
		return highestAura;
	}

	private <T> List<T> getCropAuraInRadiusNonnullList(List<CropAuraGeneratorTileEntity> generators, Function<CropAuraGeneratorTileEntity, T> aura)
	{
		return getCropAuraInRadiusList(generators, aura, Objects::nonNull);
	}

	private <T> List<T> getCropAuraInRadiusList(List<CropAuraGeneratorTileEntity> generators, Function<CropAuraGeneratorTileEntity, T> aura, Predicate<T> predicate)
	{
		List<T> auras = new ArrayList<>();
		for (CropAuraGeneratorTileEntity generator : generators)
		{
			T present = aura.apply(generator);
			if (predicate.test(present))
				auras.add(present);
		}
		return auras;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(GROWSTATE);
	}
	
	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid)
	{
		return willHarvest && !player.isCreative() || super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}

	@Override
	public void playerDestroy(World world, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack)
	{
		super.playerDestroy(world, player, pos, state, te, stack);
		world.destroyBlock(pos, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray)
	{
		int growstate = state.getValue(GROWSTATE);
		if (growstate > 6 && hand.equals(Hand.MAIN_HAND) && !worldIn.isClientSide)
		{
			ItemStack holdItem = player.getItemInHand(Hand.MAIN_HAND);
			if (!holdItem.isEmpty())
			{
				if (holdItem.getItem() instanceof AnalyserItem || holdItem.getItem() instanceof AdvancedAnalyserItem || holdItem.getItem() instanceof CropRemover)
					return ActionResultType.PASS;
			}
			NonNullList<ItemStack> drops = NonNullList.create();
			TileEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof CropsTileEntity)
			{
				((CropsTileEntity) te).dropsRemoveOneSeed(drops, growstate);
				for (ItemStack stack : drops)
					popResource(worldIn, pos, stack);
				worldIn.setBlockAndUpdate(pos, state.setValue(GROWSTATE, 0));
			}
		}
		return super.use(state, worldIn, pos, player, hand, ray);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
	{
		List<ItemStack> drops = Lists.newArrayList();
		int growstate = state.getValue(GROWSTATE);
		Vector3d vec3d = builder.getOptionalParameter(LootParameters.ORIGIN);
		if (vec3d != null)
		{
			BlockPos pos = new BlockPos(vec3d);
			TileEntity te = builder.getLevel().getBlockEntity(pos);
			if (te instanceof CropsTileEntity)
			{
				((CropsTileEntity) te).addDrops(drops, growstate);
				drops.add(new ItemStack(ModBlocks.CROPBARS));
			}
		}
		return drops; 
	}

	/*----------------------RENDERING------------------*/

	@Override
	public BlockRenderType getRenderShape(BlockState iBlockState)
	{
		return BlockRenderType.MODEL;
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
		public int getColor(BlockState state, IBlockDisplayReader blockDisplayReader, BlockPos pos, int tintindex)
		{
			if (tintindex == 0)
				return PlantTechMain.getCropList().getByName(((CropBaseBlock) state.getBlock()).getEntryName()).getSeedColor();
			return 0xFFFFFFFF;
		}

	}
}
