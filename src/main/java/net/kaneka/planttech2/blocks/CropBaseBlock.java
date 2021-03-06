package net.kaneka.planttech2.blocks;

import com.google.common.collect.Lists;
import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.entity.CropsBlockEntity;
import net.kaneka.planttech2.blocks.entity.machine.CropAuraGeneratorBlockEntity;
import net.kaneka.planttech2.enums.EnumTemperature;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.items.AdvancedAnalyserItem;
import net.kaneka.planttech2.items.AnalyserItem;
import net.kaneka.planttech2.items.CropRemover;
import net.kaneka.planttech2.registries.ModBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class CropBaseBlock extends ModBlockEntityBlock
{
	public static final IntegerProperty GROWSTATE = IntegerProperty.create("growstate", 0, 7);
	private final String entryName;

	public CropBaseBlock(String entryName)
	{
		super(BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(0.5f));
		this.entryName = entryName;
		setRegistryName(entryName + "_crop");
	}

	public void updateCrop(Level level, BlockPos pos, HashMapCropTraits traits)
	{
		BlockState state = level.getBlockState(pos);
		int growstate = state.getValue(GROWSTATE);
		if (growstate < 7 && canGrow(level, pos, traits))
			level.setBlockAndUpdate(pos, state.setValue(GROWSTATE, growstate + 1));
		else
		{
			List<BlockPos> neighborpos = getNeighborBlockPosRandom(pos);
			for (BlockPos blockpos : neighborpos)
			{
				if (level.getBlockState(blockpos).getBlock() == ModBlocks.CROPBARS)
				{
					List<BlockPos> cropbarneighbors = getNeighborBlockPosRandomExeptOne(blockpos, pos);
					for (BlockPos possiblePartner : cropbarneighbors)
					{
						BlockState partnerState = level.getBlockState(possiblePartner);
						if (partnerState.getBlock() instanceof CropBaseBlock)
						{
							BlockEntity BlockEntity = level.getBlockEntity(possiblePartner);
							if (BlockEntity instanceof CropsBlockEntity)
							{
								HashMapCropTraits partnertraits = ((CropsBlockEntity) BlockEntity).getTraits();
								level.setBlockAndUpdate(blockpos, defaultBlockState());
								if (level.getBlockEntity(blockpos) instanceof CropsBlockEntity  cbe && level.getBlockEntity(pos) instanceof CropsBlockEntity cbe2)
								{
									cbe.setTraits(cbe2.getTraits().calculateNewTraits(partnertraits));
									break;
								}
							}
						}
					}
					if (!(level.getBlockState(blockpos).getBlock() instanceof CropBaseBlock))
					{
						level.setBlockAndUpdate(blockpos, defaultBlockState());
						if (level.getBlockEntity(blockpos) instanceof CropsBlockEntity  cbe && level.getBlockEntity(pos) instanceof CropsBlockEntity cbe2)
							cbe.setTraits(cbe2.getTraits().copy());
					}
					break;
				}
			}
		}
	}

	public void updateCreative(Level level, BlockPos pos)
	{
		BlockState state = level.getBlockState(pos);
		int growstate = state.getValue(GROWSTATE);
		if (growstate < 7)
			level.setBlockAndUpdate(pos, state.setValue(GROWSTATE, 7));
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

	private boolean canGrow(Level level, BlockPos pos, HashMapCropTraits traits)
	{
		List<CropAuraGeneratorBlockEntity> generators = getCropAuraGeneratorInRadius(level, pos, 8);
		if (!enoughLight(level, pos, traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY), generators))
			return false;
		if (!enoughWater(level, pos, traits.getTrait(EnumTraitsInt.WATERSENSITIVITY), generators))
			return false;
		if (!rightSoil(level, pos, traits.getType(), generators))
			return false;
		return rightTemperature(level, pos, traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE), generators);
	}

	public String[] canGrowString(Level level, BlockPos pos)
	{
		String[] messages = new String[10];
		if (level.getBlockEntity(pos) instanceof CropsBlockEntity cbe)
		{
			HashMapCropTraits traits = cbe.getTraits();
			List<CropAuraGeneratorBlockEntity> generators = getCropAuraGeneratorInRadius(level, pos, 8);
			if (!enoughLight(level, pos, traits.getTrait(EnumTraitsInt.LIGHTSENSITIVITY), generators))
				messages[1] = "Not enough light";
			if (!enoughWater(level, pos, traits.getTrait(EnumTraitsInt.WATERSENSITIVITY), generators))
				messages[2] = "Not enough water";
			if (!rightSoil(level, pos, traits.getType(), generators))
				messages[3] = "Not right soil";
			if (!rightTemperature(level, pos, traits.getType(), traits.getTrait(EnumTraitsInt.TEMPERATURETOLERANCE), generators))
				messages[4] = "Not right temperature";
		}
		else
			messages[0] = "error";
		return messages;
	}

	public boolean enoughLight(Level level, BlockPos pos, int lightsensitivity,  List<CropAuraGeneratorBlockEntity> generators)
	{
		if (!level.isAreaLoaded(pos, 1))// prevent loading unloaded chunks
			return false;
		int extraLightValueDecrease = getCropAuraInRadiusInt(generators, (generator) -> generator.lightValueDecrease);
		return level.getMaxLocalRawBrightness(pos, 0) >= 14 - lightsensitivity - extraLightValueDecrease;
	}

	public boolean enoughWater(Level level, BlockPos pos, int waterSensitivity, List<CropAuraGeneratorBlockEntity> generators)
	{
		int extraWaterRangeDecrease = getCropAuraInRadiusInt(generators, (generator) -> generator.waterRangeDecrease);
		waterSensitivity += extraWaterRangeDecrease + 1;
		for (BlockPos blockpos$mutableblockpos : BlockPos.betweenClosed(pos.offset(-waterSensitivity, 0, -waterSensitivity), pos.offset(waterSensitivity, -1, waterSensitivity)))
		{
			BlockState state = level.getBlockState(blockpos$mutableblockpos);
			if (state.getMaterial() == Material.WATER || (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)))
				return true;
		}
		return false;
	}

	public boolean rightSoil(Level level, BlockPos pos, String name, List<CropAuraGeneratorBlockEntity> generators)
	{
		Block block = PlantTechMain.getCropList().getByName(name).getConfiguration().getSoil().get();
		Block soil = level.getBlockState(pos.below()).getBlock();
		List<Block> extraSoils = getCropAuraInRadiusList(generators, (generator) -> generator.soil, (block2) -> block2 != Blocks.AIR && block2 != Blocks.CAVE_AIR && block2 != Blocks.VOID_AIR);
		return soil == block || soil == ModBlocks.UNIVERSAL_SOIL_INFUSED || extraSoils.contains(soil) || (soil == Blocks.GRASS_BLOCK && block == Blocks.DIRT);
	}

	public boolean rightTemperature(Level level, BlockPos pos, String name, int tolerance, List<CropAuraGeneratorBlockEntity> generators)
	{
		List<EnumTemperature> extraTemperatures = getCropAuraInRadiusNonnullList(generators, (generator) -> generator.temperature);
		extraTemperatures.add(PlantTechMain.getCropList().getByName(name).getConfiguration().getTemperature());
		float biomeTemp = level.getBiomeManager().getBiome(pos).getTemperature(pos);
		return EnumTemperature.inRange(biomeTemp, tolerance, extraTemperatures);
	}

	private List<CropAuraGeneratorBlockEntity> getCropAuraGeneratorInRadius(Level level, BlockPos centre, int radius)
	{
		List<CropAuraGeneratorBlockEntity> generators = new ArrayList<>();
		for (int x = centre.getX() - radius; x < centre.getX() + radius; x++)
			for (int y = centre.getY(); y < centre.getY() + 1; y++)
				for (int z = centre.getZ() - radius; z < centre.getZ() + radius; z++)
				{
					BlockEntity te = level.getBlockEntity(new BlockPos(x, y, z));
					if (te instanceof CropAuraGeneratorBlockEntity cagbe && cagbe.consumeEnergy(25))
						generators.add(cagbe);
				}
		return generators;
	}

	private int getCropAuraInRadiusInt(List<CropAuraGeneratorBlockEntity> generators, Function<CropAuraGeneratorBlockEntity, Integer> aura)
	{
		return getCropAuraInRadius(generators, aura, (present) -> true, (max, present) -> present > max).orElse(0);
	}

	private <T> Optional<T> getCropAuraInRadius(List<CropAuraGeneratorBlockEntity> generators, Function<CropAuraGeneratorBlockEntity, T> aura, Predicate<T> predicate, BiPredicate<T, T> compare)
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

	private <T> List<T> getCropAuraInRadiusNonnullList(List<CropAuraGeneratorBlockEntity> generators, Function<CropAuraGeneratorBlockEntity, T> aura)
	{
		return getCropAuraInRadiusList(generators, aura, Objects::nonNull);
	}

	private <T> List<T> getCropAuraInRadiusList(List<CropAuraGeneratorBlockEntity> generators, Function<CropAuraGeneratorBlockEntity, T> aura, Predicate<T> predicate)
	{
		List<T> auras = new ArrayList<>();
		for (CropAuraGeneratorBlockEntity generator : generators)
		{
			T present = aura.apply(generator);
			if (predicate.test(present))
				auras.add(present);
		}
		return auras;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(GROWSTATE);
	}
	
	@Override
	public boolean removedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
	{
		return willHarvest && !player.isCreative() || super.removedByPlayer(state, level, pos, player, willHarvest, fluid);
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, BlockEntity te, ItemStack stack)
	{
		super.playerDestroy(level, player, pos, state, te, stack);
		level.destroyBlock(pos, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray)
	{
		int growstate = state.getValue(GROWSTATE);
		if (growstate > 6 && hand.equals(InteractionHand.MAIN_HAND) && !levelIn.isClientSide)
		{
			ItemStack holdItem = player.getItemInHand(InteractionHand.MAIN_HAND);
			if (!holdItem.isEmpty())
			{
				if (holdItem.getItem() instanceof AnalyserItem || holdItem.getItem() instanceof AdvancedAnalyserItem || holdItem.getItem() instanceof CropRemover)
					return InteractionResult.PASS;
			}
			NonNullList<ItemStack> drops = NonNullList.create();
			if (levelIn.getBlockEntity(pos) instanceof CropsBlockEntity cbe)
			{
				cbe.dropsRemoveOneSeed(drops, growstate);
				for (ItemStack stack : drops)
					popResource(levelIn, pos, stack);
				levelIn.setBlockAndUpdate(pos, state.setValue(GROWSTATE, 0));
			}
		}
		return super.use(state, levelIn, pos, player, hand, ray);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
	{
		List<ItemStack> drops = Lists.newArrayList();
		int growstate = state.getValue(GROWSTATE);
		Vec3 vec3d = builder.getOptionalParameter(LootContextParams.ORIGIN);
		if (vec3d != null)
		{
			BlockPos pos = new BlockPos(vec3d);
			if (builder.getLevel().getBlockEntity(pos) instanceof CropsBlockEntity cbe)
			{
				cbe.addDrops(drops, growstate);
				drops.add(new ItemStack(ModBlocks.CROPBARS));
			}
		}
		return drops; 
	}

	/*----------------------RENDERING------------------*/

	@Override
	public RenderShape getRenderShape(BlockState state)
	{
		return RenderShape.MODEL;
	}

	public String getEntryName()
	{
		return entryName;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new CropsBlockEntity(blockPos, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState p_153213_, BlockEntityType<T> p_153214_)
	{
		return level.isClientSide() ? null : CropsBlockEntity::tick;
	}

	public static class ColorHandler implements BlockColor
	{
		@Override
		public int getColor(BlockState state, BlockAndTintGetter blockDisplayReader, BlockPos pos, int tintindex)
		{
			if (tintindex == 0)
				return PlantTechMain.getCropList().getByName(((CropBaseBlock) state.getBlock()).getEntryName()).getSeedColor();
			return 0xFFFFFFFF;
		}
	}
}
