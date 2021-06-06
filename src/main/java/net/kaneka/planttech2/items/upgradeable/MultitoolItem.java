package net.kaneka.planttech2.items.upgradeable;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.kaneka.planttech2.utilities.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class MultitoolItem extends UpgradeableHandItem
{
	private static final Set<Block> EFFECTIVE_ON_SPADE = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK,
	        Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER,
	        Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER,
	        Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER,
	        Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);

	private static final Set<Block> EFFECTIVE_ON_PICKAXE = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK,
	        Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE,
	        Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE,
	        Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE,
	        Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB,
	        Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB,
	        Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);

	private static final Set<Block> EFFECTIVE_ON_AXE = Sets.newHashSet(Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS,
	        Blocks.DARK_OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD,
	        Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.CHEST, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN,
	        Blocks.JACK_O_LANTERN, Blocks.MELON, Blocks.LADDER, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON,
	        Blocks.ACACIA_BUTTON, Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE,
	        Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE);

	protected static final Map<Block, Block> BLOCK_STRIPPING_MAP = (new Builder<Block, Block>()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
	        .put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
	        .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD)
	        .put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
	        .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).build();

	protected static final Map<Block, BlockState> PATH_MAP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.defaultBlockState()));

	protected static final Map<Block, BlockState> FARMLAND_MAP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.defaultBlockState(), Blocks.GRASS_PATH,
	        Blocks.FARMLAND.defaultBlockState(), Blocks.DIRT, Blocks.FARMLAND.defaultBlockState(), Blocks.COARSE_DIRT, Blocks.DIRT.defaultBlockState()));

	protected static final Set<Material> MATERIAL_EFFECT_ON = Sets.newHashSet(Material.METAL, Material.HEAVY_METAL, Material.STONE, Material.WOOD, Material.PLANT);

	public MultitoolItem()
	{
		super(new Item.Properties().tab(ModCreativeTabs.TOOLS_AND_ARMOR), 10000, 10, 2F, 2.4F, UpgradeChipItem.TOOL);
	}

	@Override
	public boolean canHarvestBlock(ItemStack stack, BlockState state)
	{
		if (extractEnergy(stack, getEnergyCost(stack), true) >= getEnergyCost(stack))
		{
			Block block = state.getBlock();
			int i = getHarvestLevel(stack);
			if (state.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE)
			{
				return i >= state.getHarvestLevel();
			}
			Material material = state.getMaterial();
			return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK
			        || block.getBlock() == Blocks.COBWEB;
		}
		return false;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{
		if (extractEnergy(stack, getEnergyCost(stack), true) >= getEnergyCost(stack))
		{
			Material material = state.getMaterial();
			if (EFFECTIVE_ON_AXE.contains(state.getBlock()) || EFFECTIVE_ON_PICKAXE.contains(state.getBlock()) || EFFECTIVE_ON_SPADE.contains(state.getBlock()))
			{
				return getEfficiency(stack);
			}

			if (state.getBlock() == Blocks.COBWEB)
			{
				return 15.0F;
			}
			return !MATERIAL_EFFECT_ON.contains(material) ? super.getDestroySpeed(stack, state) : getEfficiency(stack);
		}
		return super.getDestroySpeed(stack, state);
	}

	@Override
	public ActionResultType useOn(ItemUseContext ctx)
	{
		if (extractEnergy(ctx.getItemInHand(), getEnergyCost(ctx.getItemInHand()), true) >= getEnergyCost(ctx.getItemInHand()))
		{
			World world = ctx.getLevel();
			BlockPos blockpos = ctx.getClickedPos();
			BlockState state = world.getBlockState(blockpos);
			BlockState state_for_spade = PATH_MAP.get(world.getBlockState(blockpos).getBlock());
			BlockState state_for_hoe = FARMLAND_MAP.get(world.getBlockState(blockpos).getBlock());
			Block block_for_strinping = BLOCK_STRIPPING_MAP.get(state.getBlock());
			PlayerEntity PlayerEntity = ctx.getPlayer();
			if (ctx.getClickedFace() != Direction.DOWN && world.getBlockState(blockpos.above()).isAir(world, blockpos.above()) && state_for_spade != null)
			{
				if (NBTHelper.getBoolean(ctx.getItemInHand(), "unlockshovel", false))
				{

					world.playSound(PlayerEntity, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!world.isClientSide)
					{
						world.setBlock(blockpos, state_for_spade, 11);
						if (PlayerEntity != null && !PlayerEntity.isCreative())
						{
							extractEnergy(ctx.getItemInHand(), getEnergyCost(ctx.getItemInHand()), false);
						}
					}
				}

				return ActionResultType.SUCCESS;
			} else if (block_for_strinping != null)
			{
				if (NBTHelper.getBoolean(ctx.getItemInHand(), "unlockaxe", false))
				{
					world.playSound(PlayerEntity, blockpos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!world.isClientSide)
					{
						world.setBlock(blockpos, block_for_strinping.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 11);
						if (PlayerEntity != null && !PlayerEntity.isCreative())
						{
							extractEnergy(ctx.getItemInHand(), getEnergyCost(ctx.getItemInHand()), false);
						}
					}
				}

				return ActionResultType.SUCCESS;
			} else if (ctx.getClickedFace() != Direction.DOWN && world.isEmptyBlock(blockpos.above()) && state_for_hoe != null)
			{
				if (NBTHelper.getBoolean(ctx.getItemInHand(), "unlockhoe", false))
				{
					world.playSound(PlayerEntity, blockpos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!world.isClientSide)
					{
						world.setBlock(blockpos, state_for_hoe, 11);
						if (PlayerEntity != null && !PlayerEntity.isCreative())
						{
							extractEnergy(ctx.getItemInHand(), getEnergyCost(ctx.getItemInHand()), false);
						}
					}

					return ActionResultType.SUCCESS;
				}
			}
		}

		return ActionResultType.PASS;
	}

	public int getHarvestLevel(ItemStack stack)
	{
		return NBTHelper.getInt(stack, "harvestlevel", 0);

	}

	public float getEfficiency(ItemStack stack)
	{
		return Math.min(NBTHelper.getFloat(stack, "breakdownrate", 4), UpgradeChipItem.getBreakdownRateMax());
	}

	@Override
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
	{

		return super.mineBlock(stack, worldIn, state, pos, entityLiving);
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand)
	{
		if (entity.level.isClientSide) return net.minecraft.util.ActionResultType.PASS;
	      if (entity instanceof net.minecraftforge.common.IForgeShearable) {
	          net.minecraftforge.common.IForgeShearable target = (net.minecraftforge.common.IForgeShearable)entity;
	         BlockPos pos = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
	         if (target.isShearable(stack, entity.level, pos)) {
	            java.util.List<ItemStack> drops = target.onSheared(player, stack, entity.level, pos,
	                    net.minecraft.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.BLOCK_FORTUNE, stack));
	            java.util.Random rand = new java.util.Random();
	            drops.forEach(d -> {
	               net.minecraft.entity.item.ItemEntity ent = entity.spawnAtLocation(d, 1.0F);
	               ent.setDeltaMovement(ent.getDeltaMovement().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
	            });
	            stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(hand));
	         }
	         return net.minecraft.util.ActionResultType.SUCCESS;
	      }
	      return net.minecraft.util.ActionResultType.PASS;
	}

}
