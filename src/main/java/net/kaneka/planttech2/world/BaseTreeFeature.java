package net.kaneka.planttech2.world;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BaseTreeFeature extends AbstractTreeFeature<NoFeatureConfig>
{
	private final int logShape, leafesShape, minHeight, maxHeight, minRadius, maxRadius;
	private final BlockState log, leaf;
	private final float leave_percentage; 

	public BaseTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config, BlockState log, int logShape, BlockState leaf,  int leafesShape, int minHeight, int maxHeight, int minRadius, int maxRadius, float leave_percentage)
	{
		super(config, false);
		this.log = log; 
		this.logShape = logShape; 
		this.leaf = leaf; 
		this.leafesShape = leafesShape; 
		this.minHeight = minHeight; 
		this.maxHeight = maxHeight;
		this.minRadius = minRadius; 
		this.maxRadius = maxRadius; 
		this.leave_percentage = leave_percentage; 
		
	}

	public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader world, Random rand, BlockPos pos, MutableBoundingBox mutbounding)
	{
		int height = minHeight + rand.nextInt(maxHeight - minHeight);
		int radius = minRadius + rand.nextInt(maxRadius - minRadius);

		if (pos.getY() >= 1 && pos.getY() + height + 1 <= world.getMaxHeight())
		{

			if (!enoughSpace(world, pos, height, radius))
			{
				return false;
			} else if ((isSoil(world, pos.down(), getSapling())) && pos.getY() < world.getMaxHeight() - height - 1)
			{
				generateLogs(changedBlocks, world, pos, height, radius, mutbounding);
				generateLeafes(changedBlocks, world, pos, height, radius, leave_percentage, mutbounding);
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

	protected void generateLeafes(Set<BlockPos> changedBlocks, IWorldGenerationReader world, BlockPos pos, int height, int radius, float leave_percentage,
	        MutableBoundingBox mutbounding)
	{
		int leafes_start = (int) (height * (1 - leave_percentage));
		int leafes_height = height - leafes_start;
		int sphereradius = leafes_height  / 2; 
		BlockPos spherecenter = pos.up(leafes_start + sphereradius); 
		BlockPos leavesStartPosCenter = pos.up(leafes_start); 
		for (int y = pos.getY() + leafes_start; y < pos.getY() + height; y++)
		{
			for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++)
			{
				for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++)
				{
					BlockPos actualpos = new BlockPos(x, y, z); 
					switch (leafesShape)
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
						if((Math.abs(x - pos.getX()) + Math.abs(y - pos.getY()) + Math.abs(z - pos.getZ())) <= leafes_height + 1)
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
		switch (logShape)
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

}
