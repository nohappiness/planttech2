package net.kaneka.planttech2.blocks.machines;

import java.util.HashMap;
import javax.annotation.Nullable;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.BlockBase;
import net.kaneka.planttech2.registries.ModBlocks;
import net.kaneka.planttech2.registries.ModItems;
import net.kaneka.planttech2.tileentity.cable.TileEntityCable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IExtendedState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.energy.CapabilityEnergy;

public class BlockCable extends BlockBase
{
    public static final IUnlistedProperty<Integer> UP = new net.minecraftforge.common.property.Properties.PropertyAdapter<Integer>(IntegerProperty.create("up", 0, 4)), NORTH = new net.minecraftforge.common.property.Properties.PropertyAdapter<Integer>(IntegerProperty.create("north", 0, 4)),
	    WEST = new net.minecraftforge.common.property.Properties.PropertyAdapter<Integer>(IntegerProperty.create("west", 0, 4)), SOUTH = new net.minecraftforge.common.property.Properties.PropertyAdapter<Integer>(IntegerProperty.create("south", 0, 4)),
	    EAST = new net.minecraftforge.common.property.Properties.PropertyAdapter<Integer>(IntegerProperty.create("east", 0, 4)), DOWN = new net.minecraftforge.common.property.Properties.PropertyAdapter<Integer>(IntegerProperty.create("down", 0, 4));

    protected static final AxisAlignedBB[] AABB_CONNECTIONS =
    { new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.125D, 0.625D), // DOWN
	    new AxisAlignedBB(0.375D, 0.875D, 0.375D, 0.625D, 1D, 0.625D), // UP
	    new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 0.125D), // NORTH
	    new AxisAlignedBB(0.375D, 0.375D, 0.875D, 0.625D, 0.625D, 1.0D), // SOUTH
	    new AxisAlignedBB(0.0D, 0.375D, 0.375D, 0.125D, 0.625D, 0.625D), // WEST
	    new AxisAlignedBB(0.875D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D)// EAST
    };

    public BlockCable()
    {
	super(Block.Properties.create(Material.IRON), "cable", PlantTechMain.groupmachines, true);
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
	if (!worldIn.isRemote && hand.equals(EnumHand.MAIN_HAND) && player.getHeldItemMainhand().getItem().equals(ModItems.WRENCH))
	{
	    Integer result = getConnectionLookedOn(worldIn, pos, player.getPositionVector().add(0, player.getEyeHeight(), 0), player.getPositionVector().add(0f, player.getEyeHeight(), 0f).add(player.getLookVec().scale(5)));
	    if (result != -1)
	    {
		TileEntityCable te = getTECable(worldIn, pos);
		if (te != null)
		{
		    te.rotateConnection(result);
		}
	    }
	}
        return super.onBlockActivated(state, worldIn, pos, player, hand, side, hitX, hitY, hitZ);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune)
    {
	return Item.getItemFromBlock(ModBlocks.CABLE);
    }

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, IBlockState state)
    {
	return new ItemStack(ModBlocks.CABLE);
    }
    
    @Override
    public void onBlockAdded(IBlockState state, World worldIn, BlockPos pos, IBlockState oldState)
    {
	TileEntityCable te = getTECable(worldIn, pos);
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
    public boolean hasTileEntity(IBlockState state)
    {
	return true;
    }

    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world)
    {
	return new TileEntityCable();
    }

    public Item createItemBlock()
    {
	return new ItemBlock(this, new Item.Properties().group(PlantTechMain.groupmain)).setRegistryName("cable");
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onReplaced(IBlockState state, World worldIn, BlockPos pos, IBlockState newState, boolean isMoving)
    {
	TileEntity te = worldIn.getTileEntity(pos);
	if (te != null)
	{
	    if (te instanceof TileEntityCable)
	    {
		((TileEntityCable) te).deleteCable();
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
    public boolean isFullCube(IBlockState state)
    {
	return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
	return EnumBlockRenderType.MODEL;
    }

    public int getConnectionLookedOn(World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {
	HashMap<Integer, AxisAlignedBB> boxes = getCollisionBoxListConnectionsList(worldIn, pos);
	HashMap<Integer, RayTraceResult> rayTraces = rayTraceList(pos, start, end, boxes);

	double d1 = 0.0D;
	int returnval = -1;
	for (HashMap.Entry<Integer, RayTraceResult> entry : rayTraces.entrySet())
	{
	    double d0 = entry.getValue().hitVec.squareDistanceTo(end);

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
	TileEntityCable te = getTECable(world, pos);
	if (te != null)
	{
	    for (EnumFacing facing : EnumFacing.values())
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
	boxes.forEach((k, v) ->
	{
	    Vec3d vec3d = start.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
	    Vec3d vec3d1 = end.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
	    RayTraceResult raytraceresult = v.calculateIntercept(vec3d, vec3d1);
	    if (raytraceresult != null)
	    {
		list.put(k, new RayTraceResult(raytraceresult.hitVec.add((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()), raytraceresult.sideHit, pos));
	    }
	});
	return list;
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockReader reader, BlockPos pos)
    {
	if (state instanceof IExtendedBlockState)
	{
	    IExtendedState<IBlockState> extState = (IExtendedBlockState) state;
	    TileEntity te = reader.getTileEntity(pos);
	    if (te instanceof TileEntityCable)
	    {
		TileEntityCable tec = (TileEntityCable) te;
		return ((IExtendedState<IBlockState>) ((IExtendedState<IBlockState>) ((IExtendedState<IBlockState>) ((IExtendedState<IBlockState>) ((IExtendedState<IBlockState>) extState.withProperty(UP, ((TileEntityCable) te).getConnection(EnumFacing.UP))).withProperty(DOWN, ((TileEntityCable) te).getConnection(EnumFacing.DOWN)))
			.withProperty(EAST, ((TileEntityCable) te).getConnection(EnumFacing.EAST))).withProperty(WEST, ((TileEntityCable) te).getConnection(EnumFacing.WEST)))
			.withProperty(NORTH, ((TileEntityCable) te).getConnection(EnumFacing.NORTH))).withProperty(SOUTH, ((TileEntityCable) te).getConnection(EnumFacing.SOUTH));
	    }

	}
	return state;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
	EnumFacing neighborfacing = null;
	for (EnumFacing facing : EnumFacing.values())
	{
	    if (pos.offset(facing).equals(fromPos))
	    {
		neighborfacing = facing;
	    }
	}

	TileEntity te = world.getTileEntity(pos);
	if (te != null && neighborfacing != null)
	{
	    if (te instanceof TileEntityCable || te.getCapability(CapabilityEnergy.ENERGY, neighborfacing).isPresent())
	    {
		((TileEntityCable) te).checkConnections();
	    }
	}
	super.neighborChanged(state, world, pos, blockIn, fromPos);
    }
    

    private TileEntityCable getTECable(World world, BlockPos pos)
    {
	TileEntity te = world.getTileEntity(pos);
	if (te != null)
	{
	    if (te instanceof TileEntityCable)
	    {
		return (TileEntityCable) te;
	    }
	}
	return null;
    }
}
