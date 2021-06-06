package net.kaneka.planttech2.fluids;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModFluids;
import net.kaneka.planttech2.registries.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;

public abstract class BiomassFluid extends FlowingFluid
{
	public static final ResourceLocation BIOMASS_STILL = new ResourceLocation(PlantTechMain.MODID, "blocks/fluid/biomass_still");
	public static final ResourceLocation BIOMASS_FLOWING = new ResourceLocation(PlantTechMain.MODID, "blocks/fluid/biomass_flow");
	public static final FluidAttributes ATTRIBUTE_STILL = FluidAttributes.builder(BIOMASS_STILL, BIOMASS_FLOWING).build(new Source());
	public static final FluidAttributes ATTRIBUTE_FLOWING = FluidAttributes.builder(BIOMASS_STILL, BIOMASS_FLOWING).build(new Flowing());

	@Override
	public Fluid getFlowing()
	{
		return ModFluids.BIOMASS_FLOWING;
	}

	@Override
	public Fluid getSource()
	{
		return ModFluids.BIOMASS;
	}

	@Override
	protected boolean canConvertToSource()
	{
		return false;
	}

	@Override
	protected void beforeDestroyingBlock(IWorld worldIn, BlockPos pos, BlockState state)
	{
		TileEntity tileentity = state.getBlock().hasTileEntity(state) ? worldIn.getBlockEntity(pos) : null;
		Block.dropResources(state, worldIn , pos, tileentity);
	}

	@Override
	protected int getSlopeFindDistance(IWorldReader worldIn)
	{
		return 4;
	}

	@Override
	protected int getDropOff(IWorldReader worldIn)
	{
		return 1;
	}

	@Override
	public Item getBucket()
	{
		return ModItems.BIOMASS_BUCKET;
	}

	@Override
	protected boolean canBeReplacedWith(FluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluid, Direction direction)
	{
		return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
	}

	@Override
	public int getTickDelay(IWorldReader p_205569_1_)
	{
		return 5;
	}

	@Override
	protected float getExplosionResistance()
	{
		return 100.0F;
	}

	@Override
	protected BlockState createLegacyBlock(FluidState state)
	{
		return ModBlocks.BIOMASSFLUIDBLOCK.defaultBlockState().setValue(FlowingFluidBlock.LEVEL, getLegacyLevel(state));
	}

	@Override
	public boolean isSame(Fluid fluidIn)
	{
		return fluidIn == ModFluids.BIOMASS || fluidIn == ModFluids.BIOMASS_FLOWING;
	}

	public static class Flowing extends BiomassFluid
	{
		public Flowing()
		{
			registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
		}

		protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder)
		{
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state)
		{
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state)
		{
			return false;
		}

		@Override
		protected FluidAttributes createAttributes()
		{
			return ATTRIBUTE_STILL;
		}
	}

	public static class Source extends BiomassFluid
	{
		 public int getAmount(FluidState state) 
		 {
             return 8;
         }

         public boolean isSource(FluidState state)
         {
             return true;
         }

		@Override
		protected FluidAttributes createAttributes()
		{
			return ATTRIBUTE_FLOWING;
		}
	}
}
