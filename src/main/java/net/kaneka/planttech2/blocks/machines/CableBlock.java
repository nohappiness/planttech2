package net.kaneka.planttech2.blocks.machines;

import java.util.HashMap;
import javax.annotation.Nullable;

import net.kaneka.planttech2.blocks.BaseBlock;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.tileentity.cable.CableTileEntity;
import net.kaneka.planttech2.utilities.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

public class CableBlock extends BaseBlock
{

    protected static final AxisAlignedBB[] AABB_CONNECTIONS =
    { new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.125D, 0.625D), // DOWN
	    new AxisAlignedBB(0.375D, 0.875D, 0.375D, 0.625D, 1D, 0.625D), // UP
	    new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 0.125D), // NORTH
	    new AxisAlignedBB(0.375D, 0.375D, 0.875D, 0.625D, 0.625D, 1.0D), // SOUTH
	    new AxisAlignedBB(0.0D, 0.375D, 0.375D, 0.125D, 0.625D, 0.625D), // WEST
	    new AxisAlignedBB(0.875D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D)// EAST
    };

    public CableBlock()
    {
	super(Block.Properties.create(Material.IRON), "cable", ModCreativeTabs.groupmachines, true);
    }

    
	@SuppressWarnings("deprecation")
	@Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray)
    {
	if (!worldIn.isRemote && hand.equals(Hand.MAIN_HAND) && player.getHeldItemMainhand().getItem().equals(ModItems.WRENCH))
	{
	    Integer result = getConnectionLookedOn(worldIn, pos, player.getPositionVector().add(0, player.getEyeHeight(), 0), player.getPositionVector().add(0f, player.getEyeHeight(), 0f).add(player.getLookVec().scale(5)));
	    if (result != -1)
	    {
		CableTileEntity te = getTECable(worldIn, pos);
		if (te != null)
		{
		    te.rotateConnection(result);
		}
	    }
	}
        return super.onBlockActivated(state, worldIn, pos, player, hand, ray);
    }
    

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state)
    {
	return new ItemStack(ModBlocks.CABLE);
    }
    
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldstate, boolean bool)
    {
	CableTileEntity te = getTECable(world, pos);
	if (te != null)
	{
	    te.initCable(state);
	}
    }

    @Override
    public boolean hasTileEntity()
    {
	return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
	return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
	return new CableTileEntity();
    }

    public Item createItemBlock()
    {
	return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.groupmain)).setRegistryName("cable");
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
	TileEntity te = worldIn.getTileEntity(pos);
	if (te != null)
	{
	    if (te instanceof CableTileEntity)
	    {
		((CableTileEntity) te).deleteCable();
	    }
	}
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
   

    @Override
    public BlockRenderLayer getRenderLayer()
    {
	return BlockRenderLayer.SOLID;
    }


    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
	return BlockRenderType.MODEL;
    }

    public int getConnectionLookedOn(World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {
	HashMap<Integer, AxisAlignedBB> boxes = getCollisionBoxListConnectionsList(worldIn, pos);
	HashMap<Integer, RayTraceResult> rayTraces = rayTraceList(pos, start, end, boxes);

	double d1 = 0.0D;
	int returnval = -1;
	for (HashMap.Entry<Integer, RayTraceResult> entry : rayTraces.entrySet())
	{
	    double d0 = entry.getValue().getHitVec().squareDistanceTo(end);

	    if (d0 > d1)
	    {
		d1 = d0;
		returnval = entry.getKey();

	    }
	}
	return returnval;
    }

    private HashMap<Integer, AxisAlignedBB> getCollisionBoxListConnectionsList(World world, BlockPos pos)
    {
	HashMap<Integer, AxisAlignedBB> list = new HashMap<Integer, AxisAlignedBB>();
	CableTileEntity te = getTECable(world, pos);
	if (te != null)
	{
	    for (Direction facing : Direction.values())
	    {
		if (te.getConnection(facing) > 1)
		{
		    list.put(facing.getIndex(), AABB_CONNECTIONS[facing.getIndex()]);
		}
	    }
	}
	return list;
    }

    @Nullable
    protected HashMap<Integer, RayTraceResult> rayTraceList(BlockPos pos, Vec3d start, Vec3d end, HashMap<Integer, AxisAlignedBB> boxes)
    {
	HashMap<Integer, RayTraceResult> list = new HashMap<Integer, RayTraceResult>();
	/*
    	boxes.forEach((k, v) ->
    	{
    	    Vec3d vec3d = start.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
    	    Vec3d vec3d1 = end.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
    	    RayTraceResult raytraceresult = v.intersects(vec3d, vec3d1);
    	    if (raytraceresult != null)
    	    {
    		list.put(k, new RayTraceResult(raytraceresult.getHitVec().add((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()), raytraceresult, pos));
    	    }
    	});
    	*/
	return list;
    }


	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor)
    {
	Direction neighborfacing = null;
	for (Direction facing : Direction.values())
	{
	    if (pos.offset(facing).equals(neighbor))
	    {
		neighborfacing = facing;
	    }
	}

	TileEntity te = world.getTileEntity(pos);
	if (te != null && neighborfacing != null)
	{
	    if (te instanceof CableTileEntity || te.getCapability(CapabilityEnergy.ENERGY, neighborfacing).isPresent())
	    {
		((CableTileEntity) te).checkConnections();
	    }
	}
	super.onNeighborChange(state, world, pos, neighbor);
    }
    

    private CableTileEntity getTECable(World world, BlockPos pos)
    {
	TileEntity te = world.getTileEntity(pos);
	if (te != null)
	{
	    if (te instanceof CableTileEntity)
	    {
		return (CableTileEntity) te;
	    }
	}
	return null;
    }
}
