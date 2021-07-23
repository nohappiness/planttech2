package net.kaneka.planttech2.blocks.entity;

import net.kaneka.planttech2.PlantTechMain;
import net.kaneka.planttech2.blocks.CropBaseBlock;
import net.kaneka.planttech2.enums.EnumTraitsInt;
import net.kaneka.planttech2.hashmaps.HashMapCropTraits;
import net.kaneka.planttech2.registries.ModTileEntities;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class CropsBlockEntity extends BlockEntity implements TickingBlockEntity
{
	private long startTick = 0;
	private HashMapCropTraits traits = new HashMapCropTraits();

	public CropsBlockEntity()
	{
		super(ModTileEntities.CROPS_TE);
	}

	@Override
	public void tick()
	{
		if (this.level != null && !this.level.isClientSide)
		{
			if ((level.getGameTime() - this.startTick) % ((90L - traits.getTrait(EnumTraitsInt.GROWSPEED) * 6L) * 20L) == 0L)
			{
				Block block = level.getBlockState(worldPosition).getBlock();
				if (block instanceof CropBaseBlock)
					((CropBaseBlock) block).updateCrop(this.level, this.worldPosition, this.traits);
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
		this.startTick = level.getGameTime();
	}

	public List<ItemStack> addDrops(List<ItemStack> drops, int growstate)
	{
		PlantTechMain.getCropList().getByName(this.traits.getType()).calculateDrops(drops, this.traits, growstate, level.random);
		return drops;
	}

	public void dropsRemoveOneSeed(NonNullList<ItemStack> drops, int growstate)
	{
		PlantTechMain.getCropList().getByName(this.traits.getType()).calculateDropsReduced(drops, this.traits, growstate, level.random);
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundTag nbtTagCompound = new CompoundTag();
		save(nbtTagCompound);
		return new SUpdateTileEntityPacket(this.worldPosition, 1, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
	{
		load(level.getBlockState(packet.getPos()), packet.getTag());
	}

	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag nbtTagCompound = new CompoundTag();
		save(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundTag tag)
	{
		super.load(state, tag);
	}

	@Override
	public CompoundTag save(CompoundTag compound)
	{
		super.save(compound);
		compound.putLong("starttick", this.startTick);
		compound = traits.addToNBT(compound);
		return compound;
	}
	
	@Override
	public void load(BlockState state, CompoundTag compound)
	{
		super.load(state, compound);
		this.startTick = compound.getLong("starttick");
		this.traits.fromNBT(compound);
	}
}
