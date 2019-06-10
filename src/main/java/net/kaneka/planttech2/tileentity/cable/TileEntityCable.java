package net.kaneka.planttech2.tileentity.cable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.rendering.cable.CableModel;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityCable extends TileEntity implements ITickableTileEntity
{
    private BlockPos masterPos = null;
    private boolean isMaster = false;
    private int[] connections = new int[]
    { 0, 0, 0, 0, 0, 0 };
    private int maxTransferRate = 20;

    private List<BlockPos> cables = new ArrayList<BlockPos>();
    private HashMap<Integer, List<Connection>> connectionsMaster = new HashMap<Integer, List<Connection>>()
    {
	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
	    put(0, new ArrayList<Connection>());
	    put(1, new ArrayList<Connection>());
	    put(2, new ArrayList<Connection>());
	}
    };

    HashMap<BlockPos, Direction> producer = new HashMap<BlockPos, Direction>(), consumer = new HashMap<BlockPos, Direction>(), storages = new HashMap<BlockPos, Direction>();
    private boolean connectionUpdate = true;

    public TileEntityCable()
    {
	super(ModTileEntities.CABLE_TE);
    }

    @Override
    public void tick()
    {

    	if (!world.isRemote && isMaster)
    	{
    	    transferEnergy();
    	}
    	//updateCable();
    }
    
    private void updateCable()
    {
    	ModelDataManager.requestModelDataRefresh(this);
        //world.markBlockRangeForRenderUpdate(getPos(), getPos());
    }

    private void transferEnergy()
    {
	IEnergyStorage cap;
	int maxNeeded = 0, maxSupplied = 0, maxStorragesNeeded = 0, maxStorragesSupplied = 0;
	int amountConsumer = 0, amountProducer = 0, amountStorages = 0;

	if (connectionUpdate)
	{
	    producer.clear();
	    consumer.clear();
	    storages.clear();


	    for (Connection con : this.connectionsMaster.get(0))
	    {
		cap = getEnergyCap(con.getConnectedPos(), con.getFacing().getOpposite());
		if (cap != null)
		{
		    if (!consumer.containsKey(con.getConnectedPos()))
		    {
			consumer.put(con.getConnectedPos(), con.getFacing().getOpposite());
		    }
		}
	    }

	    for (Connection con : this.connectionsMaster.get(1))
	    {
		cap = getEnergyCap(con.getConnectedPos(), con.getFacing().getOpposite());
		if (cap != null)
		{
		    if (!producer.containsKey(con.getConnectedPos()))
		    {
			producer.put(con.getConnectedPos(), con.getFacing().getOpposite());

		    }
		}
	    }

	    for (Connection con : this.connectionsMaster.get(2))
	    {
		cap = getEnergyCap(con.getConnectedPos(), con.getFacing().getOpposite());
		if (cap != null)
		{
		    if (!storages.containsKey(con.getConnectedPos()))
		    {
			storages.put(con.getConnectedPos(), con.getFacing().getOpposite());
		    }
		}
	    }

	    List<BlockPos> intersect_PC = producer.keySet().stream().filter(x -> consumer.containsKey(x)).collect(Collectors.toList()),
		    intersect_PS = producer.keySet().stream().filter(x -> storages.containsKey(x)).collect(Collectors.toList()), intersect_CS = consumer.keySet().stream().filter(x -> storages.containsKey(x)).collect(Collectors.toList());

	    intersect_PC.addAll(intersect_PS);
	    intersect_PC.addAll(intersect_CS);

	    producer.entrySet().removeIf(entry -> intersect_PC.contains(entry.getKey()));
	    consumer.entrySet().removeIf(entry -> intersect_PC.contains(entry.getKey()));
	    storages.entrySet().removeIf(entry -> intersect_PC.contains(entry.getKey()));

	    this.connectionUpdate = false;
	}

	for (Entry<BlockPos, Direction> entry : producer.entrySet())
	{
	    cap = getEnergyCap(entry.getKey(), entry.getValue());
	    if (cap != null)
	    {
		maxSupplied += cap.extractEnergy(maxTransferRate, true);
		amountProducer++;
	    }
	}

	for (Entry<BlockPos, Direction> entry : consumer.entrySet())
	{
	    cap = getEnergyCap(entry.getKey(), entry.getValue());
	    if (cap != null)
	    {
		maxNeeded += cap.receiveEnergy(maxTransferRate, true);
		amountConsumer++;
	    }
	}

	for (Entry<BlockPos, Direction> entry : storages.entrySet())
	{
	    cap = getEnergyCap(entry.getKey(), entry.getValue());
	    if (cap != null)
	    {
		maxStorragesSupplied += cap.extractEnergy(maxTransferRate, true);
		maxStorragesNeeded += cap.receiveEnergy(maxTransferRate, true);
		amountStorages++;
	    }
	}

	if ((maxNeeded + maxStorragesNeeded) > 0 && (maxSupplied + maxStorragesSupplied) > 0)
	{
	    int perMachineConsumer = 0, perMachineProducer = 0, perMachineStoragesIn = 0, perMachineStoragesOut = 0;
	    int leftoverConsumer = 0, leftoverProducer = 0, leftoverStorageIn = 0, leftoverStorageOut = 0;
	    if (maxNeeded > maxSupplied)
	    {
		int maxSuppliedBoth = maxSupplied + maxStorragesSupplied;
		if (maxNeeded > maxSuppliedBoth)
		{
		    perMachineProducer = maxTransferRate;
		    perMachineStoragesOut = maxTransferRate;
		    perMachineConsumer = maxSuppliedBoth / amountConsumer;
		    leftoverConsumer = maxSuppliedBoth - (perMachineConsumer * amountConsumer);
		}
		else
		{
		    perMachineProducer = maxTransferRate;
		    perMachineStoragesOut = (maxNeeded - maxSupplied) / amountStorages;
		    leftoverStorageOut = (maxNeeded - maxSupplied) - (perMachineStoragesOut * amountStorages);
		    perMachineConsumer = maxTransferRate;
		}
	    }
	    else if (maxNeeded < maxSupplied)
	    {
		int maxNeededBoth = maxNeeded + maxStorragesNeeded;
		if (maxNeededBoth < maxSupplied)
		{
		    perMachineProducer = maxNeededBoth / amountProducer;
		    leftoverProducer = maxNeededBoth - (perMachineProducer * amountProducer);
		    perMachineStoragesIn = maxTransferRate;
		    perMachineConsumer = maxTransferRate;
		}
		else
		{
		    perMachineProducer = maxTransferRate;
		    perMachineStoragesIn = (maxSupplied - maxNeeded) / amountStorages;
		    leftoverStorageIn = (maxSupplied - maxNeeded) - (perMachineStoragesIn * amountStorages);
		    perMachineConsumer = maxTransferRate;
		}
	    }
	    else
	    {
		perMachineProducer = maxTransferRate;
		perMachineConsumer = maxTransferRate;
	    }

	    for (Entry<BlockPos, Direction> entry : producer.entrySet())
	    {
		cap = getEnergyCap(entry.getKey(), entry.getValue());
		if (cap != null)
		{
		    if (leftoverProducer > 0)
		    {
			cap.extractEnergy(perMachineProducer + 1, false);
			leftoverProducer--;
		    }
		    else
		    {
			cap.extractEnergy(perMachineProducer, false);
		    }
		}
	    }

	    for (Entry<BlockPos, Direction> entry : consumer.entrySet())
	    {
		cap = getEnergyCap(entry.getKey(), entry.getValue());
		if (cap != null)
		{
		    if (leftoverConsumer > 0)
		    {
			cap.receiveEnergy(perMachineConsumer + 1, false);
			leftoverConsumer--;
		    }
		    else
		    {
			cap.receiveEnergy(perMachineConsumer, false);
		    }
		}
	    }

	    for (Entry<BlockPos, Direction> entry : storages.entrySet())
	    {
		cap = getEnergyCap(entry.getKey(), entry.getValue());
		if (cap != null)
		{
		    if (perMachineStoragesIn > 0)
		    {
			if (leftoverStorageIn > 0)
			{
			    cap.receiveEnergy(perMachineStoragesIn + 1, false);
			    leftoverStorageIn--;
			}
			else
			{
			    cap.receiveEnergy(perMachineStoragesIn, false);
			}
		    }
		    if (perMachineStoragesOut > 0)
		    {
			if (leftoverStorageOut > 0)
			{
			    cap.extractEnergy(perMachineStoragesOut + 1, false);
			    leftoverStorageOut--;
			}
			else
			{
			    cap.extractEnergy(perMachineStoragesOut, false);
			}
		    }
		}
	    }
	}
    }

    public BlockPos getMasterPos()
    {
	return this.masterPos;
    }

    public void setMasterPos(BlockPos pos)
    {
	this.masterPos = pos;
    }

    public int getConnection(Direction facing)
    {
	return this.connections[facing.getIndex()];
    }

    public void setConnection(Direction facing, int i)
    {
	if (0 <= i && i < 5)
	{
	    TileEntityCable te = this.getTECable(masterPos);
	    if (te != null)
	    {
		te.changeConnectionMaster(pos, facing, this.connections[facing.getIndex()], i);
	    }

	    this.connections[facing.getIndex()] = i;

	}
    }

    public void changeConnectionMaster(BlockPos pos, Direction facing, int before, int after)
    {
	if (before > 1)
	{
	    this.connectionsMaster.get(before - 2).removeIf(x -> x.areEqual(pos, facing));
	}
	if (after > 1)
	{
	    if (!this.connectionsMaster.get(after - 2).stream().anyMatch(x -> x.areEqual(pos, facing)))
	    {
		this.connectionsMaster.get(after - 2).add(new Connection(pos, facing));
	    }
	}
	this.connectionUpdate = true;
    }

    private void removeValues()
    {
	this.masterPos = null;
	if (this.isMaster)
	{
	    removeMaster(null);
	}
    }

    public void setAsMaster()
    {
	this.isMaster = true;
	this.masterPos = this.pos;
	this.cables.add(this.pos);
	this.connectionUpdate = true;
    }

    public void removeMaster(BlockPos newMaster)
    {
	this.isMaster = false;
	this.masterPos = newMaster;
	this.cables.clear();
	for (int i = 0; i < this.connectionsMaster.size(); i++)
	{
	    this.connectionsMaster.get(i).clear();
	}
    }

    public void addCable(BlockPos pos)
    {
	cables.add(pos);
    }

    public void removeCable(BlockPos pos)
    {
	cables.remove(pos);
    }

    public void initCable(BlockState state)
    {
	TileEntity te;
	List<BlockPos> neighborMaster = new ArrayList<BlockPos>();
	for (Direction facing : Direction.values())
	{
	    te = this.getWorld().getTileEntity(this.getPos().offset(facing));
	    if (te != null)
	    {
		if (te instanceof TileEntityCable)
		{
		    TileEntityCable cable = (TileEntityCable) te;
		    if (!neighborMaster.contains(cable.getMasterPos()))
		    {
			neighborMaster.add(cable.getMasterPos());
		    }

		}

		
	    }
	}

	switch (neighborMaster.size())
	{
	case 0:
	    this.setAsMaster();
	    this.masterPos = this.pos;
	    break;
	case 1:
	    te = this.getWorld().getTileEntity(neighborMaster.get(0));
	    if (te != null)
	    {
		if (te instanceof TileEntityCable)
		{
		    ((TileEntityCable) te).addCable(this.getPos());
		    this.setMasterPos(te.getPos());
		}
	    }
	    break;
	default:
	    this.combineAndAdd(neighborMaster);
	    break;
	}
	checkConnections();
	markDirty();

    }

    public void deleteCable()
    {
	TileEntity te;
	List<BlockPos> neighborCables = new ArrayList<BlockPos>();
	for (Direction facing : Direction.values())
	{
	    te = this.getWorld().getTileEntity(this.getPos().offset(facing));
	    if (te != null)
	    {
		if (te instanceof TileEntityCable)
		{
		    neighborCables.add(this.pos.offset(facing));
		}
	    }
	}

	if (this.isMaster)
	{
	    if (neighborCables.size() == 1)
	    {
		TileEntityCable newMaster = (TileEntityCable) this.world.getTileEntity(neighborCables.get(0));
		this.transferMastery(newMaster, this);
		newMaster.removeCable(this.pos);

	    }
	    else if (neighborCables.size() > 1)
	    {
		recreateNetworks(neighborCables, this.cables);
	    }
	}
	else
	{
	    if (neighborCables.size() == 1)
	    {
		((TileEntityCable) this.world.getTileEntity(neighborCables.get(0))).removeCable(this.pos);
	    }
	    else if (neighborCables.size() > 1)
	    {
		recreateNetworks(neighborCables, getTECable(this.masterPos).getCableList());
	    }
	}
    }

    public void recreateNetworks(List<BlockPos> neighborcables, List<BlockPos> cables)
    {
	List<BlockPos> cableCopy = cables.stream().collect(Collectors.toList());
	for (BlockPos cablePos : cableCopy)
	{
	    TileEntityCable te = getTECable(cablePos);
	    if (te != null)
	    {
		te.removeValues();
	    }
	}

	for (BlockPos neighborcable : neighborcables)
	{
	    TileEntityCable te = getTECable(neighborcable);
	    if (te.getMasterPos() == null)
	    {
		te.setAsMaster();
		te.sendConnections();
		te.spreadPos(this.pos);
	    }
	}
    }

    public void spreadPos(BlockPos exeption)
    {
	for (Direction facing : Direction.values())
	{
	    BlockPos nextPos = this.pos.offset(facing);
	    if (!nextPos.equals(exeption))
	    {
		TileEntityCable te = getTECable(nextPos);
		if (te != null)
		{
		    if (te.getMasterPos() == null)
		    {
			te.setMasterPos(this.masterPos);
			getTECable(te.getMasterPos()).addCable(te.getPos());
			te.sendConnections();
			te.spreadPos(exeption);
		    }
		}
	    }
	}
    }

    private void sendConnections()
    {
	TileEntityCable te = this.getTECable(masterPos);
	if (te != null)
	{
	    for (Direction facing : Direction.values())
	    {
		if (connections[facing.getIndex()] > 1)
		{
		    te.changeConnectionMaster(pos, facing, 0, connections[facing.getIndex()]);
		}
	    }
	}
    }

    public void combineAndAdd(List<BlockPos> list)
    {
	TileEntity te = this.world.getTileEntity(list.get(0));
	list.remove(0);
	if (te != null)
	{
	    if (te instanceof TileEntityCable)
	    {
		TileEntityCable newMaster = (TileEntityCable) te;
		TileEntity oldTE;
		for (BlockPos oldMasterPos : list)
		{
		    oldTE = world.getTileEntity(oldMasterPos);
		    if (oldTE != null)
		    {
			if (oldTE instanceof TileEntityCable)
			{
			    TileEntityCable oldMaster = (TileEntityCable) oldTE;
			    this.transferMastery(newMaster, oldMaster);
			}
		    }
		}
		newMaster.addCable(this.getPos());
		this.setMasterPos(newMaster.getPos());
	    }
	}
    }

    public HashMap<Integer, List> getLists()
    {
	HashMap<Integer, List> lists = new HashMap<Integer, List>();
	lists.put(0, this.cables);
	lists.put(1, this.connectionsMaster.get(0));
	lists.put(2, this.connectionsMaster.get(1));
	lists.put(3, this.connectionsMaster.get(2));
	return lists;
    }

    public void setLists(HashMap<Integer, List> lists)
    {
	this.cables.addAll(lists.get(0));
	this.connectionsMaster.get(0).addAll(lists.get(1));
	this.connectionsMaster.get(1).addAll(lists.get(2));
	this.connectionsMaster.get(2).addAll(lists.get(3));
    }

    public List<BlockPos> getCableList()
    {
	return this.cables;
    }

    private void setCableMasterPos(BlockPos pos)
    {
	for (BlockPos cablePos : this.cables)
	{
	    TileEntity te = world.getTileEntity(cablePos);
	    if (te != null)
	    {
		if (te instanceof TileEntityCable)
		{
		    ((TileEntityCable) te).setMasterPos(pos);
		}
	    }
	}
    }

    private void transferMastery(TileEntityCable newMaster, TileEntityCable oldMaster)
    {
	oldMaster.setCableMasterPos(newMaster.getMasterPos());
	newMaster.setAsMaster();
	newMaster.setLists(oldMaster.getLists());
	oldMaster.removeMaster(newMaster.getMasterPos());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
	compound.putBoolean("ismaster", this.isMaster);
	if (this.masterPos != null)
	{
	    compound.putInt("masterposx", this.masterPos.getX());
	    compound.putInt("masterposy", this.masterPos.getY());
	    compound.putInt("masterposz", this.masterPos.getZ());
	}

	if (!cables.isEmpty())
	{
	    ListNBT cableList = new ListNBT();
	    cables.stream().forEach(x -> cableList.add(NBTUtil.writeBlockPos(x)));
	    compound.put("cables", cableList);
	}

	if (this.connections.length == 6)
	{
	    compound.putIntArray("connections", this.connections);
	}

	ListNBT connectionlist;
	List<Connection> connections;
	for (int i = 0; i < 3; i++)
	{

	    if (connectionsMaster.get(i).size() > 0)
	    {
		connections = connectionsMaster.get(i);
		connectionlist = new ListNBT();
		for (Connection con : connections)
		{
		    connectionlist.add(con.serializeConnection());
		}
		compound.put("connections_" + i, connectionlist);
	    }
	}
	return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound)
    {
	super.read(compound);
	if (compound.contains("ismaster"))
	{
	    this.isMaster = compound.getBoolean("ismaster");
	}
	if (compound.contains("masterposx"))
	{
	    this.masterPos = new BlockPos(compound.getInt("masterposx"), compound.getInt("masterposy"), compound.getInt("masterposz"));
	}

	ListNBT cableList = compound.getList("cables", Constants.NBT.TAG_COMPOUND);
	if (cableList.size() > 0)
	{
	    this.cables.clear();
	    for (int i = 0; i < cableList.size(); i++)
	    {
		this.cables.add(NBTUtil.readBlockPos(cableList.getCompound(i)));
	    }
	}
	if (compound.contains("connections"))
	{
	    this.connections = compound.getIntArray("connections");
	}

	ListNBT list;
	for (int i = 0; i < 3; i++)
	{
	    if (compound.contains("connections_" + i))
	    {
		list = compound.getList("connections_" + i, Constants.NBT.TAG_COMPOUND);
		if (list.size() > 0)
		{
		    this.connectionsMaster.get(i).clear();
		    for (int k = 0; k < list.size(); k++)
		    {
			this.connectionsMaster.get(i).add(new Connection().deserializeConnection(list.getCompound(k)));
		    }
		}
	    }
	}
    }

    private IEnergyStorage getEnergyCap(BlockPos pos, Direction facing)
    {
	TileEntity te = this.world.getTileEntity(pos);
	if (te != null)
	{
	    if (te.getCapability(CapabilityEnergy.ENERGY, facing).isPresent())
	    {
		return (IEnergyStorage) te.getCapability(CapabilityEnergy.ENERGY, facing);
	    }
	}
	return null;
    }

    private TileEntityCable getTECable(BlockPos pos)
    {
	TileEntity te = this.world.getTileEntity(pos);
	if (te != null)
	{
	    if (te instanceof TileEntityCable)
	    {
		return (TileEntityCable) te;
	    }
	}
	return null;
    }

    public void checkConnections()
    {
	boolean shouldSendChanges = false;
	for (Direction facing : Direction.values())
	{
	    TileEntity te = world.getTileEntity(this.pos.offset(facing));
	    if (te != null)
	    {
		if (te instanceof TileEntityCable)
		{
		    if (getConnection(facing) != 1)
		    {
			this.setConnection(facing, 1);
			shouldSendChanges = true;
		    }
		}

		if (te.getCapability(CapabilityEnergy.ENERGY, facing).isPresent())
		{
		    if (getConnection(facing) < 2)
		    {
			this.setConnection(facing, 2);
			shouldSendChanges = true;
		    }
		}
	    }
	    else
	    {
		if (getConnection(facing) != 0)
		{
		    this.setConnection(facing, 0);
		    shouldSendChanges = true;
		}
	    }
	}

	if (shouldSendChanges && !world.isRemote)
	{
	    sendUpdates();
	}
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
	return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
	return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
	super.onDataPacket(net, pkt);
	handleUpdateTag(pkt.getNbtCompound());
	//world.markBlockRangeForRenderUpdate(pos, pos);
    }

    private void sendUpdates()
    {
	//world.markBlockRangeForRenderUpdate(pos, pos);
	world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	markDirty();
    }

    public void rotateConnection(int i)
    {
	int next = this.connections[i] + 1;
	if (next > 4)
	{
	    next = 2;
	}
	setConnection(Direction.byIndex(i), next);
	sendUpdates();
    }
    
    @Override
    public IModelData getModelData()
    {
    	return new ModelDataMap.Builder().withInitial(CableModel.DATA_DOWN, connections[Direction.DOWN.getIndex()])
    			.withInitial(CableModel.DATA_EAST, connections[Direction.EAST.getIndex()])
    			.withInitial(CableModel.DATA_NORTH, connections[Direction.NORTH.getIndex()])
    			.withInitial(CableModel.DATA_WEST, connections[Direction.WEST.getIndex()])
    			.withInitial(CableModel.DATA_SOUTH, connections[Direction.SOUTH.getIndex()])
    			.withInitial(CableModel.DATA_UP, connections[Direction.UP.getIndex()]).build();
    }
    
}
