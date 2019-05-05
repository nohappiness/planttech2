package net.kaneka.planttech2.items.upgradeable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemMultitool extends ItemBase
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

	protected static final Map<Block, IBlockState> PATH_MAP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));

	protected static final Set<Material> MATERIAL_EFFECT_ON = Sets.newHashSet(Material.IRON, Material.ANVIL, Material.ROCK, Material.WOOD, Material.PLANTS, Material.VINE);

	public ItemMultitool()
	{
		super("multitool", new Item.Properties().group(PlantTechMain.groupToolsAndArmor));
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
	{
		return new InventoryEnergyProvider(10000, 10);
	}

	@Override
	public boolean canHarvestBlock(ItemStack stack, IBlockState state)
	{
		Block block = state.getBlock();
		int i = getHarvestLevel();
		if (state.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE)
		{
			return i >= state.getHarvestLevel();
		}
		Material material = state.getMaterial();
		return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		Material material = state.getMaterial();
		return !MATERIAL_EFFECT_ON.contains(material) ? super.getDestroySpeed(stack, state) : getEfficiency();
	}

	@Override
	public EnumActionResult onItemUse(ItemUseContext ctx)
	{
		World world = ctx.getWorld();
		BlockPos blockpos = ctx.getPos();
		IBlockState state = world.getBlockState(blockpos);
		IBlockState state_for_spade = PATH_MAP.get(world.getBlockState(blockpos).getBlock());
		Block block = BLOCK_STRIPPING_MAP.get(state.getBlock());
		if (ctx.getFace() != EnumFacing.DOWN && world.getBlockState(blockpos.up()).isAir(world, blockpos.up()) && state_for_spade != null)
		{
			EntityPlayer entityplayer = ctx.getPlayer();
			world.playSound(entityplayer, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isRemote)
			{
				world.setBlockState(blockpos, state_for_spade, 11);
				if (entityplayer != null)
				{
					extractEnergy(ctx.getItem(), 1);
				}
			}

			return EnumActionResult.SUCCESS;
		} else if (block != null)
		{
			EntityPlayer entityplayer = ctx.getPlayer();
			world.playSound(entityplayer, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isRemote)
			{
				world.setBlockState(blockpos, block.getDefaultState().with(BlockRotatedPillar.AXIS, state.get(BlockRotatedPillar.AXIS)), 11);
				if (entityplayer != null)
				{
					extractEnergy(ctx.getItem(), 1);
				}
			}

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.PASS;
	}

	public int extractEnergy(ItemStack stack, int amount)
	{
		// TODO
		return 0;
	}

	public int receiveEnergy(ItemStack stack, int amount)
	{
		return 0;
	}

	public int getHarvestLevel()
	{
		return 1;

	}

	public float getEfficiency()
	{
		return 1.5F;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		LazyOptional<IEnergyStorage> provider = stack.getCapability(CapabilityEnergy.ENERGY);
		if(provider != null)
		{
			IEnergyStorage storage = provider.orElse(null); 
			if(storage != null)
			{
				{
					tooltip.add(new TextComponentString(storage.getEnergyStored() + "/" +storage.getMaxEnergyStored())); 
				}
			}
	}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		LazyOptional<IEnergyStorage> provider = stack.getCapability(CapabilityEnergy.ENERGY);
		if(provider != null)
		{
			IEnergyStorage storage = provider.orElse(null); 
			if(storage != null)
			{
				{
					storage.receiveEnergy(1, false); 
				}
			}
	}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}
}
