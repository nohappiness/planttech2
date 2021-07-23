package net.kaneka.planttech2.blocks.entity.cable;

import net.kaneka.planttech2.blocks.entity.cable.CableInfo.Connection;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class TestCableTileEntity extends BlockEntity implements ITickableTileEntity
{
    public CableInfo cableInfo = new CableInfo();
    public int maxTransferRate = 20;

    public TestCableTileEntity()
    {
        super(ModTileEntities.CABLE_TE);
    }

    @Override
    public void tick()
    {
        if (level != null && !level.isClientSide() && isMaster())
            transferEnergy();
//        if (Level != null && !world.isRemote() && !isMaster())
//        {
//            System.out.println(getMasterPos());
//        }
    }

    private void transferEnergy()
    {
        //get all available energy required/supplied
        final AtomicInteger energyRequired = new AtomicInteger();
        final AtomicInteger energySupplied = new AtomicInteger();
        for (Connection consumer : info().consumers)
            receiveEnergy(consumer, (capability) -> energyRequired.addAndGet(capability.receiveEnergy(getMaxTransferRate(), true)));
        for (Connection producer : info().producers)
            extractEnergy(producer, (capability) -> energySupplied.addAndGet(capability.extractEnergy(getMaxTransferRate(), true)));
        //get the energy to transfer extract/receive
        final AtomicInteger energyAvailable = new AtomicInteger(Math.min(energySupplied.get(), energyRequired.get()));
        final AtomicInteger energyAvailable2 = new AtomicInteger(energyAvailable.get());
        //transfer the energy
        for (Connection consumer : info().consumers)
            receiveEnergy(consumer, (capability) -> energyAvailable.addAndGet(-capability.receiveEnergy(Math.min(energyAvailable.get(), getMaxTransferRate()), false)));
        for (Connection producer : info().producers)
            extractEnergy(producer, (capability) -> energyAvailable2.addAndGet(-capability.extractEnergy(Math.min(energyAvailable2.get(), getMaxTransferRate()), false)));
    }

    private void receiveEnergy(Connection consumer, Consumer<IEnergyStorage> transfer)
    {
        IEnergyStorage capability = getEnergyCap(consumer.blockPos, consumer.direction.getOpposite());
        if (capability != null && capability.canReceive())
            transfer.accept(capability);
    }

    private void extractEnergy(Connection producer, Consumer<IEnergyStorage> transfer)
    {
        IEnergyStorage capability = getEnergyCap(producer.blockPos, producer.direction.getOpposite());
        if (capability != null && capability.canExtract())
            transfer.accept(capability);
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        compound.put("cableinfo", this.cableInfo.write());
        return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundTag nbt)
    {
        super.load(state, nbt);
        this.cableInfo = new CableInfo(nbt.getCompound("cableinfo"));
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(getBlockPos(), 7414, getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.save(new CompoundTag());
    }

    @Override
    public void onDataPacket(NetworkManager manager, SUpdateTileEntityPacket packet)
    {
        if (level != null)
            handleUpdateTag(level.getBlockState(packet.getPos()), packet.getTag());
    }

    public void initCable()
    {
        checkConnections();
        //get a list of nearby networks
        ArrayList<BlockPos> masters = new ArrayList<>();
        getAllConnected((cable) -> {
            BlockPos masterPos = cable.getMasterPos();
            if (!masters.contains(masterPos))
                masters.add(masterPos);
        });
        if (!masters.isEmpty())
        {
            //if there's only a network, add this cable to the network
            if (masters.size() == 1)
            {
                TestCableTileEntity master = getCableTE(masters.get(0));
                if (master != null)
                    master.addCableToNetwork(this);
                return;
            }
            //else merge all neighbor networks
            TestCableTileEntity largestNetwork = getLargestNetwork(masters);
            if (largestNetwork != null)
            {
                largestNetwork.addCableToNetwork(this);
                masters.remove(largestNetwork.getBlockPos());
                masters.forEach((pos) -> largestNetwork.mergeNetworks(getCableTE(pos)));
                return;
            }
        }
        //if there are no neighbor networks, create one instead
        createNetwork();
        getMasterCable().addMachinesFrom(this);
        setChanged();
    }

    /**
     * Adds cable to the network
     * @param cable cable to add to the network
     */
    public void addCableToNetwork(TestCableTileEntity cable)
    {
        if (!isMaster())
            return;
        info().slaves.add(cable.getBlockPos());
        cable.updateMaster(this);
        addMachinesFrom(cable);
        setChanged();
    }

    public List<BlockPos> getAllConnections(BlockPos master)
    {
        ArrayList<BlockPos> positions = new ArrayList<>();
        positions.add(master);
        return getAllConnections(positions);
    }

    public List<BlockPos> getAllConnections(HashSet<BlockPos> connected)
    {
        return getAllConnections(new ArrayList<>(connected));
    }

    /**
     * Scans all the network
     * @param connected list of checked positions
     * @return updated list of checked positions
     */
    public List<BlockPos> getAllConnections(ArrayList<BlockPos> connected)
    {
        for (Direction direction : Direction.values())
        {
            BlockPos pos = getBlockPos().relative(direction);
            if (connected.contains(pos))
                continue;
            TestCableTileEntity cable = getCableTE(pos);
            if (cable != null)
            {
                connected.add(pos);
                cable.getAllConnections(connected);
            }
        }
        return connected;
    }

    /**
     * Rebuild all the info, include the slaves
     */
    public void refresh()
    {
        if (level == null || !isMaster())
            return;
        clear(true);
        setIsMaster(true);
        getAllConnections(getBlockPos()).forEach((pos) -> {
            if (!pos.equals(getBlockPos()))
            {
                TestCableTileEntity cable = getCableTE(pos);
                if (cable != null)
                {
                    info().slaves.add(pos);
                    cable.updateMaster(this);
                    addMachinesFrom(cable);
                }
            }
        });
        addMachinesFrom(this);
        setChanged();
    }

    /**
     * Adds all machines to the network the cable is connected to
     * @param cable target cable
     */
    public void addMachinesFrom(TestCableTileEntity cable)
    {
        int[] connections = cable.getConnections();
        for (int i=0;i<connections.length;i++)
        {
            Connection connection = new Connection(cable.getBlockPos().relative(Direction.from3DDataValue(i)), Direction.from3DDataValue(i));
            if (connections[i] < 2)
            {
                removeConsumer(connection);
                removeProducer(connection);
            }
            if (connections[i] == 2)
                addConsumer(connection);
            if (connections[i] == 3)
                addProducer(connection);
        }
    }

    /**
     * Create a new network, set this as master
     */
    public void createNetwork()
    {
        if (level == null)
            return;
        setIsMaster(true);
        refresh();
    }

    /**
     * Get the largest network from the list
     * @param masterPositions list of master positions
     * @return the master of the largest network
     */
    @Nullable
    public TestCableTileEntity getLargestNetwork(ArrayList<BlockPos> masterPositions)
    {
        BlockPos largest = BlockPos.ZERO;
        for (BlockPos pos : masterPositions)
        {
            TestCableTileEntity largestCable = getCableTE(largest);
            TestCableTileEntity targetCable = getCableTE(pos);
            if (largestCable != null)
            {
                if (targetCable != null)
                {
                    if (largestCable.info().slaves.size() < targetCable.info().slaves.size())
                        largest = pos;
                }
            }
            else if (targetCable != null)
                largest = pos;
        }
        return largest.equals(BlockPos.ZERO) ? null : getCableTE(largest);
    }

    /**
     * Updates cable connections according to its neighbors
     */
    public void checkConnections()
    {
        if (level == null)
            return;
        for (Direction direction : Direction.values())
        {
            TileEntity te = level.getBlockEntity(getBlockPos().relative(direction));
            if (te != null)
            {
                if (te instanceof TestCableTileEntity)
                {
                    if (getConnection(direction) != 1)
                        setConnection(direction, 1);
                }
                else if (te.getCapability(CapabilityEnergy.ENERGY, direction).isPresent())
                {
                    if (getConnection(direction) < 2)
                        setConnection(direction, 2);
                }
            }
            else if (getConnection(direction) != 0)
                setConnection(direction, 0);
        }
        setChanged();
    }

    /**
     * Remove this cable and force neighbor cables to update
     */
    public void setRemoved()
    {
        if (getMasterCable() != null)
        {
            getMasterCable().forceReplaceMaster();
            getMasterCable().getSlaves().remove(getBlockPos());
        }
    }

    /***
     * Create a new network even if there's a master already
     */
    public void forceReplaceMaster()
    {
        if (level == null)
            return;
        if (info().slaves.isEmpty())
        {
            return;
        }
        else
        {
            for (BlockPos slave : info().slaves)
            {
                TestCableTileEntity cable = getCableTE(slave);
                if (cable != null)
                    cable.clear(true);
            }
        }
        getAllConnected((cable) -> {
            if (cable.getMasterPos().equals(BlockPos.ZERO))
                cable.createNetwork();
        });
        setChanged();
    }

    /**
     * Assign one of the slave in slaves master if slaves is not empty
     */
    public void findNewMaster()
    {
        if (level == null || !this.isMaster())
            return;
        ArrayList<BlockPos> slaves = new ArrayList<>(info().slaves);
        if (!slaves.isEmpty())
        {
            TestCableTileEntity cable = getCableTE(slaves.get(0));
            if (cable != null)
                cable.setAsMaster(this);
        }
    }

    /**
     * Merges two masters' info together, this removes the other master
     * @param otherMaster other master to merge
     */
    public void mergeNetworks(TestCableTileEntity otherMaster)
    {
        if (level == null || !info().isMaster || otherMaster == null)
            return;
        CableInfo i = otherMaster.info();
        info().slaves.addAll(i.slaves);
        info().producers.addAll(i.producers);
        info().consumers.addAll(i.consumers);
        info().storages.addAll(i.storages);
        otherMaster.clear(true);
        info().slaves.add(otherMaster.getBlockPos());
        updateSlavesMasterInfo();
    }

    /**
     * Inherits info from the old master and updates all slave's master pos
     * @param oldMaster old master that new master inherits from
     */
    public void setAsMaster(@Nullable TestCableTileEntity oldMaster)
    {
        if (level == null)
            return;
        this.setIsMaster(true);
        if (oldMaster != null)
        {
            CableInfo i = oldMaster.info();
            info().masterPos = oldMaster.getBlockPos();
            info().slaves = i.slaves;
            info().producers = i.producers;
            info().consumers = i.consumers;
            info().storages = i.storages;
            updateSlavesMasterInfo();
        }
    }

    public void updateMaster(TestCableTileEntity master)
    {
        updateMaster(master.getBlockPos());
    }

    public void updateMaster(BlockPos master)
    {
        info().masterPos = master;
        info().isMaster = false;
    }

    /**
     * Sets all slave's master pos to this pos
     */
    public void updateSlavesMasterInfo()
    {
        if (!isMaster())
            return;
        info().slaves.forEach((slave) -> {
            TestCableTileEntity cable = getCableTE(slave);
            if (cable != null)
                cable.updateMaster(this);
        });
        setChanged();
    }

    /**
     * Make changes to all connected cables
     * @param message changes to the cable
     * @return if there's any changes made
     */
    public boolean getAllConnected(Consumer<TestCableTileEntity> message)
    {
        boolean changed = false;
        for (Direction direction : Direction.values())
        {
            TestCableTileEntity cable = getCableTE(getBlockPos().relative(direction));
            if (cable != null)
            {
                message.accept(cable);
                changed = true;
            }
        }
        return changed;
    }

    private IEnergyStorage getEnergyCap(BlockPos pos, Direction facing)
    {
        if (level == null)
            return null;
        TileEntity tileentity = level.getBlockEntity(pos);
        if (tileentity != null)
        {
            LazyOptional<IEnergyStorage> capability = tileentity.getCapability(CapabilityEnergy.ENERGY, facing);
            if (capability.isPresent())
                return capability.orElseThrow(() -> new NullPointerException("Trying to get null energy capability for block " + tileentity.getBlockState().getBlock().getRegistryName().toString() + " at " + pos));
        }
        return null;
    }

    public int getMaxTransferRate()
    {
        return maxTransferRate;
    }

    public ArrayList<BlockPos> getPositionsOf(HashSet<Connection> connections)
    {
        ArrayList<BlockPos> positions = new ArrayList<>();
        connections.forEach((connection) -> positions.add(connection.blockPos));
        return positions;
    }

    public void setConnection(Direction facing, int i)
    {
        getConnections()[facing.get3DDataValue()] = i;
        if (getMasterCable() != null)
            getMasterCable().addMachinesFrom(this);
    }

    public int getConnection(Direction direction)
    {
        return getConnections()[direction.get3DDataValue()];
    }

    public void rotateConnection(Direction direction)
    {
        rotateConnection(direction.get3DDataValue());
    }

    public void rotateConnection(int direction)
    {
        int next = getConnections()[direction] + 1;
        if (next > 3)
            next = 2;
        setConnection(Direction.from3DDataValue(direction), next);
    }

    public ArrayList<BlockPos> getAllCables()
    {
        ArrayList<BlockPos> cables = new ArrayList<>(info().slaves);
        cables.add(info().masterPos);
        return cables;
    }

    public void addConsumer(Connection connection)
    {
        if (!isMaster())
            return;
        if (!contains(info().consumers, connection))
        {
            info().consumers.add(connection);
            removeProducer(connection);
        }
    }

    public void addProducer(Connection connection)
    {
        if (!isMaster())
            return;
        if (!contains(info().producers, connection))
        {
            info().producers.add(connection);
            removeConsumer(connection);
        }
    }

    public void removeConsumer(Connection connection)
    {
        if (!isMaster())
            return;
        remove(info().consumers, connection);
    }

    public void removeProducer(Connection connection)
    {
        if (!isMaster())
            return;
        remove(info().producers, connection);
    }

    public boolean contains(Collection<Connection> connections, Connection newConnection)
    {
        return connections.stream().anyMatch((connection) -> equals(connection, newConnection));
    }

    public void remove(Collection<Connection> connections, Connection newConnection)
    {
        connections.removeIf((connection) -> equals(connection, newConnection));
    }

    public boolean equals(Connection connection, Connection connection2)
    {
        return connection.blockPos.equals(connection2.blockPos) && connection.direction == connection2.direction;
    }

    public CableInfo info()
    {
        return this.cableInfo;
    }

    public void setIsMaster(boolean value)
    {
        info().isMaster = value;
        info().masterPos = getBlockPos();
        info().slaves.remove(getBlockPos());
    }

    public boolean isMaster()
    {
        return info().isMaster;
    }

    public BlockPos getMasterPos()
    {
        return info().masterPos;
    }

    public HashSet<BlockPos> getSlaves()
    {
        return info().slaves;
    }

    public HashSet<Connection> getConsumers()
    {
        return info().consumers;
    }

    public HashSet<Connection> getProducers()
    {
        return info().producers;
    }

    public HashSet<Connection> getStorages()
    {
        return info().storages;
    }

    public int[] getConnections()
    {
        return info().connections;
    }

    /**
     * renew the cable info
     */
    public void clear(boolean keepConnection)
    {
        cableInfo = cableInfo.clear(keepConnection);
        checkConnections();
    }

    @Nullable
    public TestCableTileEntity getMasterCable()
    {
        return getCableTE(getMasterPos());
    }

    @Nullable
    public TestCableTileEntity getCableTE(BlockPos pos)
    {
        if (level == null)
            return null;
        TileEntity tileEntity = level.getBlockEntity(pos);
        return tileEntity instanceof TestCableTileEntity ? (TestCableTileEntity) tileEntity : null;
    }
}
