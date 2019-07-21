package net.kaneka.planttech2.items;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.kaneka.planttech2.world.planttopia.ModDimensionPlantTopia;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraftforge.common.IPlantable;

public class TestItem extends BaseItem
{
	private final int logShape, leavesShape;
	private final BlockState log, leaf;

	public TestItem()
	{
		super("testitem", new Item.Properties().group(ModCreativeTabs.groupchips));
		logShape = 0;
		leavesShape = 0;
		log = Blocks.BIRCH_LOG.getDefaultState();
		leaf = Blocks.BIRCH_LEAVES.getDefaultState();

	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		//place(Sets.newHashSet(), ctx.getWorld(), new Random(), ctx.getPos().up(), MutableBoundingBox.getNewBoundingBox());
		System.out.println(ModDimensionPlantTopia.getDimensionType()); 
		return super.onItemUse(ctx);
	}

	public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader world, Random rand, BlockPos pos, MutableBoundingBox mutbounding)
	{
		int height = 10;
		int radius = 4;
		float leave_percentage = 0.60F;

		if (pos.getY() >= 1 && pos.getY() + height + 1 <= world.getMaxHeight())
		{

			if (!enoughSpace(world, pos, height, radius))
			{
				return false;
			} else if ((isSoil(world, pos.down(), getSapling())) && pos.getY() < world.getMaxHeight() - height - 1)
			{
				generateLogs(changedBlocks, world, pos, height, radius, mutbounding);
				generateLeaves(changedBlocks, world, pos, height, radius, leave_percentage, mutbounding);
				return true;
			}
		}

		return false;
	}

	protected boolean enoughSpace(IWorldGenerationReader world, BlockPos pos, int height, int radius)
	{
		boolean flag = true;
		for (int y = pos.getY(); y <= pos.getY() + 1 + height; ++y)
		{

			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (int x = pos.getX() - radius; x <= pos.getX() + radius && flag; ++x)
			{
				for (int z = pos.getZ() - radius; z <= pos.getZ() + radius && flag; ++z)
				{
					if (y >= 0 && y < world.getMaxHeight())
					{
						if (!func_214587_a(world, blockpos$mutableblockpos.setPos(x, y, z)))
						{
							System.out.println(x + ":" + y + ":" + z);
							flag = false;
						}
					} else
					{
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	protected void generateLeaves(Set<BlockPos> changedBlocks, IWorldGenerationReader world, BlockPos pos, int height, int radius, float leave_percentage,
	        MutableBoundingBox mutbounding)
	{
		
		int leaves_start = (int) (height * (1 - leave_percentage));
		int leaves_height = height - leaves_start;
		int sphereradius = leaves_height  / 2; 
		BlockPos spherecenter = pos.up(leaves_start + sphereradius); 
		BlockPos leavesStartPosCenter = pos.up(leaves_start); 
		for (int y = pos.getY() + leaves_start; y < pos.getY() + height; y++)
		{
			for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++)
			{
				for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++)
				{
					BlockPos actualpos = new BlockPos(x, y, z); 
					switch (7)
					{
					case 1: //sphere
						if(actualpos.distanceSq(spherecenter.getX(), spherecenter.getY(), spherecenter.getZ(), false) <= sphereradius*sphereradius)
						{
							if (isAirOrLeaves(world, actualpos))
							{
								this.setLogState(changedBlocks, world, actualpos, leaf, mutbounding);
							}
						}
						break; 
					case 2: //cylinder
						if(actualpos.distanceSq(spherecenter.getX(), actualpos.getY(), spherecenter.getZ(), false) <= sphereradius*sphereradius)
						{
							if (isAirOrLeaves(world, actualpos))
							{
								this.setLogState(changedBlocks, world, actualpos, leaf, mutbounding);
							}
						}
						break; 
						
					case 3: //hemisphere
						if(actualpos.distanceSq(leavesStartPosCenter.getX(), leavesStartPosCenter.getY(), leavesStartPosCenter.getZ(), false) <= sphereradius*2*sphereradius)
						{
							if (isAirOrLeaves(world, actualpos))
							{
								this.setLogState(changedBlocks, world, actualpos, leaf, mutbounding);
							}
						}
						break;
						
					case 4: //pyramid
						if((Math.abs(x - pos.getX()) + Math.abs(y - pos.getY()) + Math.abs(z - pos.getZ())) <= leaves_height + 1)
						{
							if (isAirOrLeaves(world, actualpos))
							{
								this.setLogState(changedBlocks, world, actualpos, leaf, mutbounding);
							}
						}
						break;
					case 5: //square
						if((Math.abs(x - spherecenter.getX()) + Math.abs(y - spherecenter.getY()) + Math.abs(z - spherecenter.getZ())) <= sphereradius + 1)
						{
							if (isAirOrLeaves(world, actualpos))
							{
								this.setLogState(changedBlocks, world, actualpos, leaf, mutbounding);
							}
						}
						break;
					case 6: //spikes
						if((Math.abs(x - spherecenter.getX()) + Math.abs(y - spherecenter.getY()) + Math.abs(z - spherecenter.getZ())) <= sphereradius + 1)
						{
							if(Math.abs(x - spherecenter.getX()) == 0 || Math.abs(z - spherecenter.getZ()) == 0)
							{
    							if (isAirOrLeaves(world, actualpos))
    							{
    								this.setLogState(changedBlocks, world, actualpos, leaf, mutbounding);
    							}
							}
						}
						break;
					case 7: //no leaves
						break;
					default:
						if (isAirOrLeaves(world, actualpos))
						{
							this.setLogState(changedBlocks, world, actualpos, leaf, mutbounding);
						}
					}
				}
			}
		}
	}

	protected void generateLogs(Set<BlockPos> changedBlocks, IWorldGenerationReader world, BlockPos pos, int height, int radius, MutableBoundingBox mutbounding)
	{

		int logsheight = (int) (height * 0.60F);
		BlockPos logsEndPos = pos.up(logsheight); 
		switch (8)
		{
		case 1: //v-shaped
			float m = (float)logsheight / (((float) radius) - 1);  
			
			int distance = 0; 
			for(int y = 0; y <= logsheight; y++)
			{
				for(int x = 0; x < radius; x++)
				{
					if(Math.floor(x*m) == y)
					{
						distance = x; 
					}
				}
				if(distance > 0)
				{
					if (isAirOrLeaves(world, pos.up(y).north(distance)))
					{
    					this.setLogState(changedBlocks, world, pos.up(y).north(distance), log, mutbounding);
					}
					if (isAirOrLeaves(world, pos.up(y).east(distance)))
					{
    					this.setLogState(changedBlocks, world, pos.up(y).east(distance), log, mutbounding);
					}
					if (isAirOrLeaves(world, pos.up(y).south(distance)))
					{
    					this.setLogState(changedBlocks, world, pos.up(y).south(distance), log, mutbounding);
					}
					if (isAirOrLeaves(world, pos.up(y).west(distance)))
					{
    					this.setLogState(changedBlocks, world, pos.up(y).west(distance), log, mutbounding);
					}
				}
				else
				{
					if (isAirOrLeaves(world, pos.up(y)))
					{
						this.setLogState(changedBlocks, world, pos.up(y), log, mutbounding);
					}
				}
			}
			
			
			break;
			
		case 2: //hashtag
			for (int y = pos.getY(); y <= pos.getY() + logsheight; y++)
			{
				for (int x = pos.getX() - radius + 1; x <= pos.getX() + radius - 1; x++)
				{
					for (int z = pos.getZ() - radius + 1; z <= pos.getZ() + radius - 1; z++)
					{
						if(Math.abs(x - pos.getX()) == 2 || Math.abs(z - pos.getZ()) == 2)
						{
							BlockPos actualpos = new BlockPos(x, y, z); 
							if (isAirOrLeaves(world, pos.up(y)))
							{
								this.setLogState(changedBlocks, world, actualpos, log, mutbounding);
							}
						}
					}
				}
			}
			
			break; 
			
		case 3: //5x5
			for (int y = pos.getY(); y <= pos.getY() + logsheight; y++)
			{
				for (int x = pos.getX() - radius + 1; x <= pos.getX() + radius - 1; x++)
				{
					for (int z = pos.getZ() - radius + 1; z <= pos.getZ() + radius - 1; z++)
					{
						if(Math.abs(x - pos.getX()) <= 2 && Math.abs(z - pos.getZ()) <= 2)
						{
							BlockPos actualpos = new BlockPos(x, y, z); 
							if (isAirOrLeaves(world, pos.up(y)))
							{
								this.setLogState(changedBlocks, world, actualpos, log, mutbounding);
							}
						}
					}
				}
			}
			
			break; 
		case 4: //3x3
			for (int y = pos.getY(); y <= pos.getY() + logsheight; y++)
			{
				for (int x = pos.getX() - radius + 1; x <= pos.getX() + radius - 1; x++)
				{
					for (int z = pos.getZ() - radius + 1; z <= pos.getZ() + radius - 1; z++)
					{
						if(Math.abs(x - pos.getX()) <= 1 && Math.abs(z - pos.getZ()) <= 1)
						{
							BlockPos actualpos = new BlockPos(x, y, z); 
							if (isAirOrLeaves(world, pos.up(y)))
							{
								this.setLogState(changedBlocks, world, actualpos, log, mutbounding);
							}
						}
					}
				}
			}
			
			break; 
		case 5: //X
			for (int y = pos.getY(); y <= pos.getY() + logsheight; y++)
			{
				for (int x = pos.getX() - radius + 1; x <= pos.getX() + radius - 1; x++)
				{
					for (int z = pos.getZ() - radius + 1; z <= pos.getZ() + radius - 1; z++)
					{
						if(Math.abs(x - pos.getX()) == 0  || Math.abs(z - pos.getZ()) == 0)
						{
							BlockPos actualpos = new BlockPos(x, y, z); 
							if (isAirOrLeaves(world, pos.up(y)))
							{
								this.setLogState(changedBlocks, world, actualpos, log, mutbounding);
							}
						}
					}
				}
			}
			
			break;
		case 6: //X-X
			for (int y = pos.getY(); y <= pos.getY() + logsheight; y++)
			{
				for (int x = pos.getX() - radius + 1; x <= pos.getX() + radius - 1; x++)
				{
					for (int z = pos.getZ() - radius + 1; z <= pos.getZ() + radius - 1; z++)
					{
						if(Math.abs(x - pos.getX()) == 0  || Math.abs(z - pos.getZ()) == 0)
						{
							if ((Math.abs(x - pos.getX()) + Math.abs(y - pos.getY()) + Math.abs(z - pos.getZ())) <= logsheight/2 || (Math.abs(x - logsEndPos.getX()) + Math.abs(y - logsEndPos.getY()) + Math.abs(z - logsEndPos.getZ())) <= logsheight/2)
							{
								BlockPos actualpos = new BlockPos(x, y, z); 
								this.setLogState(changedBlocks, world, actualpos, log, mutbounding);
							}
						}
					}
				}
			}
			
			break;
		case 7: //spiral
			int radius2 = radius/2; 
			for (int y = 0; y <=  + logsheight; y++)
			{
				int x = (int) (radius2 * Math.cos(y));
                int z = (int) (radius2 * Math.sin(y));
                BlockPos actualpos = new BlockPos(x + pos.getX(), y + pos.getY(), z + pos.getZ()); 
                if (isAirOrLeaves(world, actualpos))
				{
					this.setLogState(changedBlocks, world, actualpos, log, mutbounding);
				}
			}
			
			break;
			
		case 8: //helix
			int radius3 = radius/2; 
			for (int y = 0; y <=  + logsheight; y++)
			{
				int x = (int) (radius3 * Math.cos(y));
                int z = (int) (radius3 * Math.sin(y));
                BlockPos actualpos = new BlockPos(x + pos.getX(), y + pos.getY(), z + pos.getZ()); 
                if (isAirOrLeaves(world, actualpos))
				{
					this.setLogState(changedBlocks, world, actualpos, log, mutbounding);
				}
                
                BlockPos actualpos2 = new BlockPos((-x) + pos.getX(), y + pos.getY(), (-z) + pos.getZ()); 
                if (isAirOrLeaves(world, actualpos2))
				{
					this.setLogState(changedBlocks, world, actualpos2, log, mutbounding);
				}
			}
			
			break;
		default:
			for (int y = 0; y < logsheight; y++)
			{
				if (isAirOrLeaves(world, pos.up(y)))
				{
					this.setLogState(changedBlocks, world, pos.up(y), log, mutbounding);
				}
			}
		}

	}

	// DO NOT COPY, JUST FOR THIS CLASS
	protected void setLogState(Set<BlockPos> changedBlocks, IWorldWriter world, BlockPos pos, BlockState state, MutableBoundingBox mutbounding)
	{
		world.setBlockState(pos, state, 18);
		mutbounding.expandTo(new MutableBoundingBox(pos, pos));
		if (BlockTags.LOGS.contains(state.getBlock()))
		{
			changedBlocks.add(pos.toImmutable());
		}
	}

	protected static boolean func_214587_a(IWorldGenerationBaseReader reader, BlockPos pos)
	{
		if (!(reader instanceof net.minecraft.world.IWorldReader)) // FORGE: Redirect to state method when possible
			return reader.hasBlockState(pos, (p_214573_0_) -> {
				Block block = p_214573_0_.getBlock();
				return p_214573_0_.isAir() || p_214573_0_.isIn(BlockTags.LEAVES) || block == Blocks.GRASS_BLOCK || Block.isDirt(block) || block.isIn(BlockTags.LOGS)
				        || block.isIn(BlockTags.SAPLINGS) || block == Blocks.VINE;
			});
		else
			return reader.hasBlockState(pos, state -> state.canBeReplacedByLogs((net.minecraft.world.IWorldReader) reader, pos));
	}

	protected static boolean isSoil(IWorldGenerationBaseReader reader, BlockPos pos, net.minecraftforge.common.IPlantable sapling)
	{
		if (!(reader instanceof net.minecraft.world.IBlockReader) || sapling == null)
			return isDirtOrGrassBlock(reader, pos);
		return reader.hasBlockState(pos, state -> state.canSustainPlant((net.minecraft.world.IBlockReader) reader, pos, Direction.UP, sapling));
	}

	protected static boolean isDirtOrGrassBlock(IWorldGenerationBaseReader worldIn, BlockPos pos)
	{
		return worldIn.hasBlockState(pos, (p_214582_0_) -> {
			Block block = p_214582_0_.getBlock();
			return Block.isDirt(block) || block == Blocks.GRASS_BLOCK;
		});
	}

	protected IPlantable getSapling()
	{
		return (IPlantable) Blocks.BIRCH_SAPLING;
	}

	protected void func_214584_a(IWorldGenerationReader p_214584_1_, BlockPos p_214584_2_)
	{
		if (!isDirt(p_214584_1_, p_214584_2_))
		{
			;
			p_214584_1_.setBlockState(p_214584_2_, Blocks.DIRT.getDefaultState(), 18);
		}

	}

	protected void setDirtAt(IWorldGenerationReader reader, BlockPos pos, BlockPos origin)
	{
		if (!(reader instanceof IWorld))
		{
			func_214584_a(reader, pos);
			return;
		}
		((IWorld) reader).getBlockState(pos).onPlantGrow((IWorld) reader, pos, origin);
	}

	protected static boolean isDirt(IWorldGenerationBaseReader worldIn, BlockPos pos)
	{
		return worldIn.hasBlockState(pos, (p_214590_0_) -> {
			return Block.isDirt(p_214590_0_.getBlock());
		});
	}

	protected static boolean isAirOrLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos)
	{
		if (!(worldIn instanceof net.minecraft.world.IWorldReader)) // FORGE: Redirect to state method when possible
			return worldIn.hasBlockState(pos, (p_214581_0_) -> {
				return p_214581_0_.isAir() || p_214581_0_.isIn(BlockTags.LEAVES);
			});
		else
			return worldIn.hasBlockState(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader) worldIn, pos));
	}
}
