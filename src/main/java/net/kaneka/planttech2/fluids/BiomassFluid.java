package net.kaneka.planttech2.fluids;

public abstract class BiomassFluid //extends FlowingFluid
{
	/*
	public static final ResourceLocation BIOMASS_STILL = new ResourceLocation("planttech2:blocks/fluid/biomass_still");
	public static final ResourceLocation BIOMASS_FLOWING = new ResourceLocation("planttech2:blocks/fluid/biomass_flow");
	public static final FluidAttributes ATTRIBUTES = FluidAttributes.builder(BIOMASS_STILL, BIOMASS_FLOWING).build();

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
		TileEntity tileentity = state.getBlock().hasTileEntity() ? worldIn.getTileEntity(pos) : null;
		Block.spawnDrops(state, worldIn.getWorld(), pos, tileentity);
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
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public Item getFilledBucket()
	{
		return ModItems.BIOMASSBUCKET;
	}

	@Override
	protected boolean func_215665_a(IFluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_)
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
		return 1;
	}

	@Override
	protected BlockState getBlockState(IFluidState state)
	{
		return ModBlocks.BIOMASSFLUIDBLOCK.getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
	}

	@Override
	public boolean isEquivalentTo(Fluid fluidIn)
	{
		return fluidIn == ModFluids.BIOMASS || fluidIn == ModFluids.BIOMASS_FLOWING;
	}

	@Override
	public FluidAttributes createAttributes(Fluid fluid)
	{
		return ATTRIBUTES;
	}

	public static class Flowing extends BiomassFluid
	{

		public Flowing()
		{
			setDefaultState(getStateContainer().getBaseState().with(LEVEL_1_8, 7));
		}

		protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder)
		{
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}

		public int getLevel(IFluidState state)
		{
			return state.get(LEVEL_1_8);
		}

		public boolean isSource(IFluidState state)
		{
			return false;
		}

	}

	public static class Source extends BiomassFluid
	{
		 public int getLevel(IFluidState state) 
		 {
             return 8;
         }

         public boolean isSource(IFluidState state) 
         {
             return true;
         }
	}
	*/

}
