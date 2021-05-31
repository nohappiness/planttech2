package net.kaneka.planttech2.tileentity;

import java.util.List;

import javax.annotation.Nullable;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class CropsTileEntity extends TileEntity implements ITickableTileEntity
{
	private long startTick = 0;
	private HashMapCropTraits traits = new HashMapCropTraits();

	public CropsTileEntity()
	{
		super(ModTileEntities.CROPS_TE);
	}

	@Override
	public void tick()
	{
		if (this.world != null && !this.world.isRemote)
		{
			if ((world.getGameTime() - this.startTick) % ((90L - traits.getTrait(EnumTraitsInt.GROWSPEED) * 6L) * 20L) == 0L)
			{
				Block block = world.getBlockState(pos).getBlock();
				if (block instanceof CropBaseBlock)
					((CropBaseBlock) block).updateCrop(this.world, this.pos, this.traits);
			}
		}

	}

	public void setTraits(HashMapCropTraits traits)
	{
		this.traits = traits;
	}
	
	public boolean isAnalysed()
	{
		return traits.isAnalysed(); 
	}
	
	public HashMapCropTraits setAnalysedAndGetTraits()
	{
		traits.setAnalysed(true);
		return getTraits(); 
	}

	public HashMapCropTraits getTraits()
	{
		return this.traits;
	}

	public String getCropType()
	{
		return this.traits.getType();
	}

	public void setStartTick()
	{
		this.startTick = world.getGameTime();
	}

	public List<ItemStack> addDrops(List<ItemStack> drops, int growstate)
	{
		PlantTechMain.getCropList().getByName(this.traits.getType()).calculateDrops(drops, this.traits, growstate, world.rand);
		return drops;
	}

	public void dropsRemoveOneSeed(NonNullList<ItemStack> drops, int growstate)
	{
		PlantTechMain.getCropList().getByName(this.traits.getType()).calculateDropsReduced(drops, this.traits, growstate, world.rand);
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT nbtTagCompound = new CompoundNBT();
		write(nbtTagCompound);
		return new SUpdateTileEntityPacket(this.pos, 1, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
	{
		read(world.getBlockState(packet.getPos()), packet.getNbtCompound());
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		CompoundNBT nbtTagCompound = new CompoundNBT();
		write(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag)
	{
		super.read(state, tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		compound.putLong("starttick", this.startTick);
		compound = traits.addToNBT(compound);
		return compound;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT compound)
	{
		super.read(state, compound);
		this.startTick = compound.getLong("starttick");
		this.traits.fromNBT(compound);
	}
}
