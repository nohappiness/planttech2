package net.kaneka.planttech2.fluids;

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
	public static final ResourceLocation BIOMASS_STILL = new ResourceLocation("planttech2:blocks/fluid/biomass_still");
	public static final ResourceLocation BIOMASS_FLOWING = new ResourceLocation("planttech2:blocks/fluid/biomass_flow");
	public static final FluidAttributes ATTRIBUTE_STILL = FluidAttributes.builder(BIOMASS_STILL, BIOMASS_FLOWING).build(new Source());
	public static final FluidAttributes ATTRIBUTE_FLOWING = FluidAttributes.builder(BIOMASS_STILL, BIOMASS_FLOWING).build(new Flowing());

	@Override
	public Fluid getFlowingFluid()
	{
		return ModFluids.BIOMASS_FLOWING;
	}

	@Override
	public Fluid getStillFluid()
	{
		return ModFluids.BIOMASS;
	}

	@Override
	protected boolean canSourcesMultiply()
	{
		return false;
	}

	@Override
	protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state)
	{
		TileEntity tileentity = state.getBlock().hasTileEntity(state) ? worldIn.getTileEntity(pos) : null;
		Block.spawnDrops(state, worldIn , pos, tileentity);
	}

	@Override
	protected int getSlopeFindDistance(IWorldReader worldIn)
	{
		return 4;
	}

	@Override
	protected int getLevelDecreasePerBlock(IWorldReader worldIn)
	{
		return 1;
	}

	@Override
	public Item getFilledBucket()
	{
		return ModItems.BIOMASS_BUCKET;
	}

	@Override
	protected boolean canDisplace(FluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_)
	{
		return p_215665_5_ == Direction.DOWN && !p_215665_4_.isIn(FluidTags.WATER);
	}

	@Override
	public int getTickRate(IWorldReader p_205569_1_)
	{
		return 5;
	}

	@Override
	protected float getExplosionResistance()
	{
		return 100.0F;
	}

	@Override
	protected BlockState getBlockState(FluidState state)
	{
		return ModBlocks.BIOMASSFLUIDBLOCK.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
	}

	@Override
	public boolean isEquivalentTo(Fluid fluidIn)
	{
		return fluidIn == ModFluids.BIOMASS || fluidIn == ModFluids.BIOMASS_FLOWING;
	}

	public static class Flowing extends BiomassFluid
	{
		public Flowing()
		{
			setDefaultState(getStateContainer().getBaseState().with(LEVEL_1_8, 7));
		}

		protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder)
		{
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}

		public int getLevel(FluidState state)
		{
			return state.get(LEVEL_1_8);
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
		 public int getLevel(FluidState state) 
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
