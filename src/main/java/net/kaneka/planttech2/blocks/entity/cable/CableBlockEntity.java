package net.kaneka.planttech2.blocks.entity.cable;

import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CableBlockEntity extends BlockEntity
{
	private BlockPos masterPos = null;
	private boolean isMaster = false;
	private int[] connections = new int[] { 0, 0, 0, 0, 0, 0 };
	private int maxTransferRate = 20;
	private boolean connectionUpdated = true;
	private final List<BlockPos> cables = new ArrayList<>();
	private final HashMap<Integer, List<Connection>> connectionsMaster = new HashMap<Integer, List<Connection>>()
	{
		private static final long serialVersionUID = 1L;
		{
			//0 as consumers, 1 as producers
			put(0, new ArrayList<>());
			put(1, new ArrayList<>());
		}
	};

	HashMap<BlockPos, Direction> producer = new HashMap<>(), consumer = new HashMap<>(), storages = new HashMap<>();

	public CableBlockEntity(BlockPos pos, BlockState state)
	{
		super(ModTileEntities.CABLE_TE, pos, state);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity be)
	{
		if (level != null && !level.isClientSide)
		{
			if(be instanceof CableBlockEntity cbe) {
				if(cbe.connectionUpdated) cbe.transferEnergy();
			}
		}
	}

	private void transferEnergy()
	{
		IEnergyStorage cap;
		int maxNeeded = 0, maxSupplied = 0, maxStorragesNeeded = 0, maxStorragesSupplied = 0;
		int amountConsumer = 0, amountProducer = 0, amountStorages = 0;

		if (connectionUpdated)
		{
			producer.clear();
			consumer.clear();
			storages.clear();

			//updating consumers and producers
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

			//check for duplicated producers and consumers
			List<BlockPos> intersect_PC = producer.keySet().stream().filter(x -> consumer.containsKey(x)).collect(Collectors.toList());
			producer.entrySet().removeIf(entry -> intersect_PC.contains(entry.getKey()));
			consumer.entrySet().removeIf(entry -> intersect_PC.contains(entry.getKey()));
			for (Connection con : this.connectionsMaster.get(0))
			{
				cap = getEnergyCap(con.getConnectedPos(), con.getFacing().getOpposite());
				if (cap != null)
				{
					if (!storages.containsKey(con.getConnectedPos()) && intersect_PC.contains(con.getConnectedPos()))
					{
						storages.put(con.getConnectedPos(), con.getFacing().getOpposite());
					}
				}
			}
			connectionUpdated = false;
		}

		//do energy transfer
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
				perMachineProducer = maxTransferRate;
				if (maxNeeded > maxSuppliedBoth)
				{
					perMachineStoragesOut = maxTransferRate;
					perMachineConsumer = maxSuppliedBoth / amountConsumer;
					leftoverConsumer = maxSuppliedBoth - (perMachineConsumer * amountConsumer);
				} else
				{
					perMachineStoragesOut = (maxNeeded - maxSupplied) / amountStorages;
					leftoverStorageOut = (maxNeeded - maxSupplied) - (perMachineStoragesOut * amountStorages);
					perMachineConsumer = maxTransferRate;
				}
			} else if (maxNeeded < maxSupplied)
			{
				int maxNeededBoth = maxNeeded + maxStorragesNeeded;
				if (maxNeededBoth < maxSupplied)
				{
					perMachineProducer = maxNeededBoth / amountProducer;
					leftoverProducer = maxNeededBoth - (perMachineProducer * amountProducer);
					perMachineStoragesIn = maxTransferRate;
				}
				else
				{
					perMachineProducer = maxTransferRate;
					perMachineStoragesIn = (maxSupplied - maxNeeded) / amountStorages;
					leftoverStorageIn = (maxSupplied - maxNeeded) - (perMachineStoragesIn * amountStorages);
				}
				perMachineConsumer = maxTransferRate;
			} else
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
					} else
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
					} else
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
						} else
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
						} else
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
		return this.connections[facing.get3DDataValue()];
	}

	public void setConnection(Direction facing, int i)
	{
		if (0 <= i && i < 5)
		{
			CableBlockEntity te = getTECable(masterPos);
			if (te != null)
			{
				te.changeConnectionMaster(worldPosition, facing, this.connections[facing.get3DDataValue()], i);
			}
			this.connections[facing.get3DDataValue()] = i;
		}
	}

	public void changeConnectionMaster(BlockPos pos, Direction facing, int fromState, int toState)
	{
		if (fromState > 1)
		{
			//-2 here so 2->0, 3->1 to get corresponding lists (0 as consumers, 1 as producers)
			this.connectionsMaster.get(fromState - 2).removeIf(x -> x.areEqual(pos, facing));
		}
		if (toState > 1)
		{
			if (this.connectionsMaster.get(toState - 2).stream().noneMatch(x -> x.areEqual(pos, facing)))
			{
				this.connectionsMaster.get(toState - 2).add(new Connection(pos, facing));
			}
		}
		connectionUpdated = true;
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
		this.masterPos = this.worldPosition;
		this.cables.add(this.worldPosition);
		this.connectionUpdated = true;
	}

	public void removeMaster(@Nullable BlockPos newMaster)
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
		if(level != null)
		{
			List<BlockPos> neighborMaster = new ArrayList<BlockPos>();
			for (Direction facing : Direction.values())
			{
				CableBlockEntity te = getTECable(worldPosition.relative(facing));
				if (te != null)
				{
					if (!neighborMaster.contains(te.getMasterPos()))
					{
						neighborMaster.add(te.getMasterPos());
					}
				}
			}

			switch (neighborMaster.size())
			{
				//no other master found
				case 0:
					this.setAsMaster();
					this.masterPos = this.worldPosition;
					break;
				//connected to a system with master
				case 1:
					CableBlockEntity te = getTECable(neighborMaster.get(0));
					if (te != null)
					{
						te.addCable(worldPosition);
						this.setMasterPos(te.getBlockPos());
					}
					break;
				//connected to systems with multiple masters
				default:
					this.combineAndAdd(neighborMaster);
					break;
			}
			checkConnections(false);
			setChanged();
		}
	}

	public void deleteCable()
	{
		BlockEntity te;
		List<BlockPos> neighborCables = new ArrayList<BlockPos>();
		if (level == null)
		{
			return;
		}
		for (Direction facing : Direction.values())
		{
			te = level.getBlockEntity(this.getBlockPos().relative(facing));
			if (te instanceof CableBlockEntity)
			{
				neighborCables.add(this.worldPosition.relative(facing));
			}
		}

		if (this.isMaster)
		{
			if (neighborCables.size() == 1)
			{
				CableBlockEntity newMaster = (CableBlockEntity) level.getBlockEntity(neighborCables.get(0));
				if (newMaster != null)
				{
					this.transferMastery(newMaster, this);
					newMaster.removeCable(this.worldPosition);
				}
			} else if (neighborCables.size() > 1)
			{
				recreateNetworks(neighborCables, this.cables);
			}
		} else
		{
			CableBlockEntity cable;
			if (neighborCables.size() == 1)
			{
				cable = getTECable(neighborCables.get(0));
				if (cable != null)
				{
					cable.removeCable(this.worldPosition);
				}
			} else if (neighborCables.size() > 1)
			{
				cable = getTECable(neighborCables.get(0));
				if (cable != null)
				{
					recreateNetworks(neighborCables, cable.getCableList());
				}
			}
		}
	}

	public void recreateNetworks(List<BlockPos> neighborCables, List<BlockPos> cables)
	{
		List<BlockPos> cableCopy = new ArrayList<>(cables);
		for (BlockPos cablePos : cableCopy)
		{
			CableBlockEntity te = getTECable(cablePos);
			if (te != null)
			{
				te.removeValues();
			}
		}

		for (BlockPos neighborCable : neighborCables)
		{
			CableBlockEntity te = getTECable(neighborCable);
			if (te != null && te.getMasterPos() == null)
			{
				te.setAsMaster();
				te.sendConnections();
				te.spreadPos(this.worldPosition);
			}
		}
	}

	//----------------------
	public void spreadPos(BlockPos exception)
	{
		for (Direction facing : Direction.values())
		{
			BlockPos nextPos = this.worldPosition.relative(facing);
			if (!nextPos.equals(exception))
			{
				CableBlockEntity te = getTECable(nextPos);
				if (te != null)
				{
					if (te.getMasterPos() == null)
					{
						te.setMasterPos(this.masterPos);
						CableBlockEntity cable = getTECable(te.getMasterPos());
						if (cable != null)
						{
							cable.addCable(te.getBlockPos());
						}
						te.sendConnections();
						te.spreadPos(exception);
					}
				}
			}
		}
	}

	private void sendConnections()
	{
		CableBlockEntity te = this.getTECable(masterPos);
		if (te != null)
		{
			for (Direction facing : Direction.values())
			{
				if (connections[facing.get3DDataValue()] > 1)
				{
					te.changeConnectionMaster(worldPosition, facing, 0, connections[facing.get3DDataValue()]);
				}
			}
		}
	}

	/***
	 * use to combine multiple systems with master
	 * @param list list of all masters that needs to merge
	 */
	public void combineAndAdd(List<BlockPos> list)
	{
		CableBlockEntity newMaster = getTECable(list.get(0));
		list.remove(0);
		if (newMaster != null)
		{
			for (BlockPos oldMasterPos : list)
			{
				CableBlockEntity oldMaster = getTECable(oldMasterPos);
				if (oldMaster != null)
				{
					this.transferMastery(newMaster, oldMaster);
				}
			}
			newMaster.addCable(this.getBlockPos());
			setMasterPos(newMaster.getBlockPos());
		}
	}

	/***
	 *  returns a list with keys: 0 as the cables list,
	 *  1 as the first element of connectionMaster,
	 *  2 as the second element of connectionMaster
	 */
	public HashMap<Integer, List> getLists()
	{
		HashMap<Integer, List> lists = new HashMap<>();
		lists.put(0, this.cables);
		lists.put(1, this.connectionsMaster.get(0));
		lists.put(2, this.connectionsMaster.get(1));
		return lists;
	}

	/***
	 * set the values of lists: cables and connectionsMaster
	 * @param lists list combination of 0: cables, 1: consumers, 2: producers
	 */
	public void setLists(HashMap<Integer, List> lists)
	{
		this.cables.addAll(lists.get(0));
		this.connectionsMaster.get(0).addAll(lists.get(1));
		this.connectionsMaster.get(1).addAll(lists.get(2));
	}

	public List<BlockPos> getCableList()
	{
		return this.cables;
	}

	private void setCableMasterPos(BlockPos pos)
	{
		for (BlockPos cablePos : this.cables)
		{
			CableBlockEntity cable = getTECable(cablePos);
			if (cable != null)
			{
				cable.setMasterPos(pos);
			}
		}
	}

	private void transferMastery(CableBlockEntity newMaster, CableBlockEntity oldMaster)
	{
		oldMaster.setCableMasterPos(newMaster.getMasterPos());
		newMaster.setAsMaster();
		newMaster.setLists(oldMaster.getLists());
		oldMaster.removeMaster(newMaster.getMasterPos());
		setChanged();
	}

	@Override
	public CompoundTag save(CompoundTag compound)
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
			ListTag cableList = new ListTag();
			cables.stream().forEach(x -> cableList.add(NbtUtils.writeBlockPos(x)));
			compound.put("cables", cableList);
		}

		if (this.connections.length == 6)
		{
			compound.putContainerData("connections", this.connections);
		}

		ListTag connectionlist;
		List<Connection> connections;
		for (int i = 0; i < 2; i++)
		{
			if (connectionsMaster.get(i).size() > 0)
			{
				connections = connectionsMaster.get(i);
				connectionlist = new ListTag();
				for (Connection con : connections)
				{
					connectionlist.add(con.serializeConnection());
				}
				compound.put("connections_" + i, connectionlist);
			}
		}
		return super.save(compound);
	}

	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);
		if (compound.contains("ismaster"))
		{
			this.isMaster = compound.getBoolean("ismaster");
		}
		if (compound.contains("masterposx"))
		{
			this.masterPos = new BlockPos(compound.getInt("masterposx"), compound.getInt("masterposy"), compound.getInt("masterposz"));
		}

		ListTag cableList = compound.getList("cables", Constants.NBT.TAG_COMPOUND);
		if (cableList.size() > 0)
		{
			this.cables.clear();
			for (int i = 0; i < cableList.size(); i++)
			{
				this.cables.add(NbtUtils.readBlockPos(cableList.getCompound(i)));
			}
		}
		if (compound.contains("connections"))
		{
			this.connections = compound.getContainerData("connections");
		}

		ListTag list;
		for (int i = 0; i < 2; i++)
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
		if (level == null)
		{
			return null;
		}
		BlockEntity te = level.getBlockEntity(pos);
		if (te != null)
		{
			if (te.getCapability(CapabilityEnergy.ENERGY, facing).isPresent())
			{
				return te.getCapability(CapabilityEnergy.ENERGY, facing).orElse(null);
			}
		}
		return null;
	}

	// calls sendUpdates() if there's any connections to other TE that has
	// energy and cause state change
	public void checkConnections(boolean neighborChanged)
	{
		boolean shouldSendChanges = false;
		if (level == null)
			return;
		for (Direction facing : Direction.values())
		{
			BlockEntity te = level.getBlockEntity(this.worldPosition.relative(facing));
			if (te != null)
			{
				if (te instanceof CableBlockEntity)
				{
					if (getConnection(facing) != 1)
					{
						this.setConnection(facing, 1);
						shouldSendChanges = true;
					}
				}
				else if (te.getCapability(CapabilityEnergy.ENERGY, facing).isPresent())
				{
					if (getConnection(facing) < 2)
					{
						this.setConnection(facing, 2);
						shouldSendChanges = true;
					}
				}
			}
			else if (getConnection(facing) != 0)
			{
				this.setConnection(facing, 0);
				shouldSendChanges = true;
			}
		}
		if (!level.isClientSide)
		{
			if (shouldSendChanges)
			{
				sendUpdates();
			}
			if (neighborChanged)
			{
				connectionUpdated = true;
			}
		}
	}

	private void sendUpdates()
	{
		//world.markForRerender(pos);
		if (level != null)
		{
			level.markAndNotifyBlock(worldPosition, level.getChunkAt(worldPosition), level.getBlockState(worldPosition), level.getBlockState(worldPosition), 0, 512);
		}
	}

	public void rotateConnection(Direction dir)
	{
		rotateConnection(dir.get3DDataValue());
	}

	public void rotateConnection(int i)
	{
		int next = this.connections[i] + 1;
		if (next > 3)
		{
			next = 2;
		}
		setConnection(Direction.from3DDataValue(i), next);
	}

	private CableBlockEntity getTECable(BlockPos pos)
	{
		if(level != null && !level.isClientSide)
		{
			BlockEntity te = level.getBlockEntity(pos);
			if (te instanceof CableBlockEntity cbe)
			{
				return cbe;
			}
		}
		return null;
	}


}
