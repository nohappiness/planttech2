package net.kaneka.planttech2.tileentity.cable;

import net.kaneka.planttech2.registries.ModTileEntities;
import net.kaneka.planttech2.tileentity.cable.CableInfo.Connection;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class TestCableTileEntity extends TileEntity implements ITickableTileEntity
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
        if (world != null && !world.isRemote() && isMaster())
            transferEnergy();
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
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.put("cableinfo", this.cableInfo.write());
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt)
    {
        super.read(state, nbt);
        this.cableInfo = new CableInfo(nbt.getCompound("cableinfo"));
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(getPos(), 3, getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager manager, SUpdateTileEntityPacket packet)
    {
        if (world != null)
            handleUpdateTag(world.getBlockState(packet.getPos()), packet.getNbtCompound());
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
                masters.remove(largestNetwork.getPos());
                masters.forEach((pos) -> largestNetwork.mergeNetworks(getCableTE(pos)));
                return;
            }
        }
        //if there are no neighbor networks, create one instead
        createNetwork();
    }

    /**
     * Adds cable to the network
     * @param cable cable to add to the network
     */
    public void addCableToNetwork(TestCableTileEntity cable)
    {
        if (!isMaster())
            return;
        info().slaves.add(cable.getPos());
        cable.updateMaster(this);
        addMachinesFrom(cable);
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
            BlockPos pos = getPos().offset(direction);
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
        if (world == null || !isMaster())
            return;
        clear(true);
        setIsMaster(true);
        getAllConnections(getPos()).forEach((pos) -> {
            if (!pos.equals(getPos()))
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
            Connection connection = new Connection(cable.getPos().offset(Direction.byIndex(i)), Direction.byIndex(i));
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
        if (world == null)
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
        if (world == null)
            return;
        for (Direction direction : Direction.values())
        {
            TileEntity te = world.getTileEntity(getPos().offset(direction));
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
    }

    /**
     * Remove this cable and force neighbor cables to update
     */
    public void remove()
    {
        forceReplaceMaster();
    }

    /***
     * Create a new network even if there's a master already
     */
    public void forceReplaceMaster()
    {
        if (world == null)
            return;
        for (BlockPos slave : info().slaves)
        {
            TestCableTileEntity cable = getCableTE(slave);
            if (cable != null)
                cable.clear(true);
        }
        getAllConnected((cable) -> {
            if (cable.getMasterPos().equals(BlockPos.ZERO))
                cable.createNetwork();
        });
    }

    /**
     * Assign one of the slave in slaves master if slaves is not empty
     */
    public void findNewMaster()
    {
        if (world == null || !this.isMaster())
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
        if (world == null || !info().isMaster || otherMaster == null)
            return;
        CableInfo i = otherMaster.info();
        info().slaves.addAll(i.slaves);
        info().producers.addAll(i.producers);
        info().consumers.addAll(i.consumers);
        info().storages.addAll(i.storages);
        otherMaster.clear(true);
        info().slaves.add(otherMaster.getPos());
        updateSlavesMasterInfo();
    }

    /**
     * Inherits info from the old master and updates all slave's master pos
     * @param oldMaster old master that new master inherits from
     */
    public void setAsMaster(@Nullable TestCableTileEntity oldMaster)
    {
        if (world == null)
            return;
        this.setIsMaster(true);
        if (oldMaster != null)
        {
            CableInfo i = oldMaster.info();
            info().masterPos = oldMaster.getPos();
            info().slaves = i.slaves;
            info().producers = i.producers;
            info().consumers = i.consumers;
            info().storages = i.storages;
            updateSlavesMasterInfo();
        }
    }

    public void updateMaster(TestCableTileEntity master)
    {
        updateMaster(master.getPos());
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
            TestCableTileEntity cable = getCableTE(getPos().offset(direction));
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
        if (world == null)
            return null;
        TileEntity tileentity = world.getTileEntity(pos);
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
        getConnections()[facing.getIndex()] = i;
        if (getMasterCable() != null)
            getMasterCable().addMachinesFrom(this);
    }

    public int getConnection(Direction direction)
    {
        return getConnections()[direction.getIndex()];
    }

    public void rotateConnection(Direction direction)
    {
        rotateConnection(direction.getIndex());
    }

    public void rotateConnection(int direction)
    {
        int next = getConnections()[direction] + 1;
        if (next > 3)
            next = 2;
        setConnection(Direction.byIndex(direction), next);
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
        info().masterPos = getPos();
        info().slaves.remove(getPos());
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

    public TestCableTileEntity getMasterCable()
    {
        return getCableTE(getMasterPos());
    }

    public TestCableTileEntity getCableTE(BlockPos pos)
    {
        if (world == null)
            return null;
        TileEntity tileEntity = world.getTileEntity(pos);
        return tileEntity instanceof TestCableTileEntity ? (TestCableTileEntity) tileEntity : null;
    }
}
